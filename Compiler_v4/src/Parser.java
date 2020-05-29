/**
 * This class parser the file set in construct method.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 05_01_2019
 */
package minipascal.syntatic_analyser;


import minipascal.ast.*;
import java.io.*;

public class Parser
{
    private Token currentToken;
    private scanner current_scanner;
    public Program P;
    public int Program_line;
    
    /**
     * Method to handle all error and print more familiar errors
     *
     * @param  token_expected  
     * @return  void
     */
    
    private void handle_error(int token_expected)
    {
        String msg_error;
        System.out.println("\n\t[SYNTACTIC ERROR]");
        
        switch(token_expected)
        {
        
            case 1000:
                msg_error = new String("EXPECTED:\tLITERAL");
            break;
            case 1001:
                msg_error = new String("EXPECTED:\tTIPO");
            break;
            case 1002:
                msg_error = new String("EXPECTED:\tFATOR");
            break;
            case 1003:
                msg_error = new String("EXPECTED:\tCOMANDO");
            break;
            default:
                msg_error = new String("EXPECTED:\t"+currentToken.spellings[token_expected]);
            break;
        } 
        System.out.println("\t\t"+msg_error);
        System.out.println("\t\tFOUND:\t\t"+currentToken.spellings[currentToken.kind] );
        current_scanner.errorHandler(true);
                
        System.exit(0);
        
    }
     
    private void acceptIt() throws Exception 
    {
        currentToken = current_scanner.scan();
        //currentToken.print();
    }

    private void accept (byte expectedKind) throws Exception
    {
        if(currentToken.kind == expectedKind)
            acceptIt();
        else
        {
            handle_error(expectedKind);
        }
    }
    
    //<id> ::= <letra>(<letra>|<dígito>)*
    private Id parserIdentifier() throws Exception
    {
    	Id_simples idAST = null;
        if(currentToken.kind == Token.IDENTIFIER)
        {
        	idAST = new Id_simples(currentToken.spelling);
            acceptIt();
        }
        else
        {
            handle_error(Token.IDENTIFIER);
        }
        return idAST;
    }
    
    /*
		<literal> ::= 
					true 
					| false
					| <int-lit> (.( <int-lit> | <vazio>) | <vazio> )
					| .<int-lit>
    */
    private String parseLiteral() throws Exception
    {
    	String spelling = null;
        switch(currentToken.kind)
        {
            case Token.INTLITERAL: case Token.FLOATLIT: case Token.TRUE:
            case Token.FALSE:
            		 spelling = new String(currentToken.spelling);
                     acceptIt();
            break;
            default:
                handle_error(1000);
            break;
        }    
        
    	return spelling;
    }
    
    /*
    <tipo> ::= 
    			array [ <literal> .. <literal> ] of <tipo>
    			| integer 
				| real 
				| boolean
    */
    private Tipo parserTipo() throws Exception
    {
    	Tipo T = null;
    	String L1, L2;
    	
        switch(currentToken.kind)
        {
            case Token.ARRAY:
            	accept(Token.ARRAY);
                accept(Token.R_SQUARE);
                
                L1 = parseLiteral();
                
                accept(Token.DOT);
                accept(Token.DOT);
                
                L2 = parseLiteral();
                
                accept(Token.L_SQUARE);
                accept(Token.OF);
                T = parserTipo();
                
                T = new Tipo_array(L1, T, L2);
                
            break;
            case Token.INTEGER: case Token.REAL: case Token.BOOLEAN:
            	 T = new Tipo_simples(currentToken.spelling);
                 acceptIt();
            break;
            default:
                handle_error(1001);
            break;
        }
        
        return T;
    
    }
    
    // <declaração> ::= var <id> ( , <id> )* : <tipo>
    private Declaracao parseDeclaracao () throws Exception
    {
    	Id Identifier;
    	Tipo T;
    	Declaracao D = null;
    	
    	
        accept(Token.VAR);
        Identifier = parserIdentifier();
        
        while(currentToken.kind != Token.COLON)
        {
            accept(Token.COMMA);
            Id Identifier2 = parserIdentifier();
            Identifier = new Id_seq(Identifier,Identifier2);
        }
        
        accept(Token.COLON);
        
        T = parserTipo();
        
        D = new Declaracao_simples(Identifier, T);
        
        return D;
    }
    
    // <variável> ::= <id> ( [ <expressão> ] )*
    private Variavel parserVariavel () throws Exception
    {
    	Variavel V = null;
    	Id identifier = null;
    	Expressao E = null;
    	
        identifier = parserIdentifier();
        
        // TO-DO: ADD THIS TO AST
        while(currentToken.kind == Token.R_SQUARE)
        {
            accept(Token.R_SQUARE);
            E = parserExpressao();
            accept(Token.L_SQUARE);
        }
        V = new Var_simples(identifier, E);
        
        return V;
        
    }
    
    /*
        <fator> ::= 
                    <variável> 
                    | <literal> 
                    | ( <expressão> )
    */
    private Fator parserFator () throws Exception
    {
    	Fator F = null;
    	byte kind;
    	
        switch(currentToken.kind)
        {
            case Token.IDENTIFIER:
            	Variavel VAR = parserVariavel();
            	F = new Fator_VAR(VAR);
            break;
            case Token.INTLITERAL: case Token.FLOATLIT: case Token.TRUE:
            case Token.FALSE:
            	 kind = currentToken.kind;
            	 String L1 = parseLiteral();
            	 F = new Fator_LIT(L1, kind);   
            break;
            case Token.R_PAREN:
                accept(Token.R_PAREN);
                Expressao E = parserExpressao();
				F = new Fator_EXP(E);
                accept(Token.L_PAREN);
            break;
            default:
                handle_error(1002);
            break;    
        }
        
    	return F;
    }
   
    
    //<termo> ::= <fator>( (<op-mul> <fator>)* | <vazio> )
    private Termo parserTermo () throws Exception
    {
    	Termo T = null;
    	Termo T1 = null;
    	Fator F1 = null;
    	String OP_MUL;
    	 
        F1 = parserFator();
        T = new Termo_unico(F1);
        
        if(currentToken.kind == Token.OP_MUL || currentToken.kind == Token.AND)
        {
        	OP_MUL = new String(currentToken.spelling);
        	if(currentToken.kind == Token.AND)
        		accept(Token.AND);
        	else
            	accept(Token.OP_MUL);
            F1 = parserFator();
            T1 = new Termo_unico(F1);
            T  = new Termo_composto(T,OP_MUL,T1);
        }
        
        while(currentToken.kind == Token.OP_MUL || currentToken.kind == Token.AND)
        {
        	OP_MUL = new String(currentToken.spelling);
        	if(currentToken.kind == Token.AND)
        		accept(Token.AND);
        	else
            	accept(Token.OP_MUL);
            F1 = parserFator();
            T1 = new Termo_unico(F1);
            T  = new Termo_composto(T,OP_MUL,T1);
        };
    	
    	return T;    	
    }
    
    //<expressão-simples> ::= <termo> (( <op-ad><termo> )* | <vazio ) 
    private Expressao_simples parserExpressaoSimples () throws Exception
    {
    	String OP_AD;
    	Expressao_simples E_s = null;
    	Expressao_simples E_s2 = null;
    	
        Termo T1 = parserTermo();
        E_s = new Expressao_simples_simples(T1);
        
        if(currentToken.kind == Token.OP_AD || currentToken.kind == Token.OR)
        {
        	OP_AD = new String(currentToken.spelling);
            if(currentToken.kind == Token.OR)
        		accept(Token.OR);
        	else
        		accept(Token.OP_AD);
            Termo T2 = parserTermo();
            E_s2 = new Expressao_simples_simples(T2);
            
            E_s = new Expressao_simples_composta(E_s, OP_AD, E_s2);
        }
        
        while(currentToken.kind == Token.OP_AD || currentToken.kind == Token.OR)
        {
        	OP_AD = new String(currentToken.spelling);
            if(currentToken.kind == Token.OR)
        		accept(Token.OR);
        	else
        		accept(Token.OP_AD);
            Termo T2 = parserTermo();
            E_s2 = new Expressao_simples_simples(T2);
            
            E_s = new Expressao_simples_composta(E_s, OP_AD, E_s2);
        };
        
        return E_s;
    }
    
    // <expressão> ::= <expressão-simples> (  <op-rel> <expressão-simples> | <vazio> )
    private Expressao parserExpressao () throws Exception
    {
    	Expressao E = null;
    	Expressao E_2 = null;
    	
    	Expressao_simples E_s1 = null;
    	String OP_REL = null;
    	
        E_s1 = parserExpressaoSimples();
        E = new Expressao_s(E_s1);
        
        if(currentToken.kind == Token.OP_REL)
        {
        	OP_REL = new String(currentToken.spelling);
            accept(Token.OP_REL);
            
            E_s1 = parserExpressaoSimples();
        	E_2 = new Expressao_s(E_s1);
            
            
            E = new Expressao_composta(E,OP_REL,E_2);
        }
        
        while(currentToken.kind == Token.OP_REL)
        {
        	OP_REL = new String(currentToken.spelling);
            accept(Token.OP_REL);
            E_s1 = parserExpressaoSimples();
        	E_2 = new Expressao_s(E_s1);
            
            E = new Expressao_composta(E,OP_REL,E_2);
        };
        
        return E;
    }
    
    /*
        <comando> ::= 
        <variável> := <expressão>
                    | if <expressão> then <comando> ( else <comando> | <vazio> )
                    | while <expressão> do <comando>
                    | begin (<comando> ;)* end

    */
    private Comando parseComando () throws Exception
    {
    	Comando C = null;
    	Comando C1 = null;
    	Variavel V = null;
    	Expressao E = null;
    	int Begin_toogle;
    	
        switch(currentToken.kind)
        {
            case Token.IDENTIFIER:
                V = parserVariavel();
                accept(Token.IS);
                E = parserExpressao();
                C = new Comando_VAR(V,E,currentToken.line);
            break;
            
            case Token.IF:
                accept(Token.IF);
                E = parserExpressao();
                accept(Token.THEN);
                C1 = parseComando();
                
                C = new Comando_IF(E,C1,currentToken.line);
                
                if( currentToken.kind == Token.ELSE)
                {
                    accept(Token.ELSE);
                    Comando C2 = parseComando();
                    
                    C = new Comando_IF_composto(E,C1,C2,currentToken.line);
                }
            break;
            
            case Token.WHILE:
                accept(Token.WHILE);
                E = parserExpressao();
                accept(Token.DO);
                C1 = parseComando ();
                C = new Comando_WHILE(E,C1,currentToken.line);
            break;
            
            case Token.BEGIN:
                accept(Token.BEGIN);
                if(currentToken.kind != Token.END)
                {
                	C = parseComando();
                	accept(Token.SEMICOLON);
                }
                Begin_toogle = currentToken.line;
                while(currentToken.kind != Token.END)
                {
                    Comando C2 = parseComando();
                    accept(Token.SEMICOLON);
                    C = new Comando_Seq(C,C2,Begin_toogle);
                }
                Begin_toogle = currentToken.line;
                accept(Token.END);
            break;
            default:
                handle_error(1003);
            break;
        }
    	
    	return C;
    	
    }
    
    // <corpo> ::= (<declaração> ;)*  begin  ( <comando> ; )* end
    private Corpo parseCorpo () throws Exception
    {
    	Declaracao D = null;
    	Comando C = null;
    	
    	
        // (<declaração> ;)*
        if(currentToken.kind == Token.VAR)
        {
        	D = parseDeclaracao();
            accept(Token.SEMICOLON);
        }
        while(currentToken.kind == Token.VAR)
        {
            Declaracao D1 = parseDeclaracao();
            accept(Token.SEMICOLON);
            D = new Declaracao_seq(D,D1);
        }
        
        accept(Token.BEGIN);
        
        if(currentToken.kind != Token.END)
        {
        	C = parseComando();
        	accept(Token.SEMICOLON);
        }
        while(currentToken.kind != Token.END)
        {
            Comando C1 = parseComando();
            accept(Token.SEMICOLON);
            C = new Comando_Seq(C,C1,0);
        }
               
        accept(Token.END);
        
        Corpo Cr = new Corpo(D,C);
        return Cr;
    }
    
    
    // <programa> ::= program <id> ; <corpo> .
    private Program parseProgram () throws Exception
    {
    	Id identifier;
    	Corpo Cr;
    	
    	Program_line = currentToken.line;
        accept(Token.PROGRAM);
        
        identifier = parserIdentifier();
        
        accept(Token.SEMICOLON);
        
        Cr = parseCorpo();
        
        accept(Token.DOT);
        accept(Token.EOT);
        
        
        Program P = new Program(identifier, Cr);
        return P;
    }


    /**
    * Constructor for objects of class Parser
    */
    public Parser(File file_object) throws Exception 
    {
    	P = null;
        current_scanner = new scanner(file_object);
        
        currentToken = current_scanner.scan();
        //currentToken.print();
        P = parseProgram();
        
    }
}
