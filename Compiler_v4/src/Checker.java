/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 13_03_2019
 */
 
import minipascal.ast.*;
import minipascal.syntatic_analyser.Token;

public final class Checker implements Visitor {
 	private identificationTable mapTable;
 	private byte phase;
 	
 	/**
     * Method to handle all error and print more familiar errors
     *
     * @param  token_expected  
     * @return  void
     */
    private void handle_error(String msg_error)
    {
        System.out.println("\n\t[CONTEXT ERROR]");
        
        System.out.println(msg_error+"\n");
                
        System.exit(0);
        
    }
 	
 	// Program
 	public Object visitProgram (Program prog, Object arg)
 	{
 		prog.I.visit(this, null);
 		prog.Cr.visit(this, null);
 		return null;
 	}
 	
 	// Identification
	public Object visitSimpleId(Id_simples com, Object arg)
	{
		if(phase == 0)
			mapTable.enter(com.spelling);
		else
			mapTable.retrieve(com.spelling);
			
		return com.spelling;
	}
	public Object visitCompoundId(Id_seq com, Object arg)
	{
		com.I1.visit(this, null);
		com.I2.visit(this, null);
		
		return null;
	}
 	
 	// Body
	public Object visitCorpo(Corpo com, Object arg)
	{
		phase = 0;
		System.out.print("\tChecking Declarations");
		com.D.visit(this, null);
		System.out.println("\tOK");
		
		phase = 1;
		System.out.print("\tChecking Commands");
		if(com.C != null)
			com.C.visit(this, null);
		System.out.println("\tOK");
		
		return null;
	}
 	
 	// Declaration
	public Object visitSimpleDeclaration(Declaracao_simples com, Object arg)
	{
		mapTable.setTipo(com.T);
		
		com.I.visit(this, null);
		com.T.visit(this, null);
		
		return null;
	}
	public Object visitCompoundDeclaration(Declaracao_seq com, Object arg)
	{
		com.D1.visit(this, null);
		com.D2.visit(this, null);
		
		return null;
	}
	
	// Term
	public Object visitSimpleTerm(Termo_unico com, Object arg)
	{
		Type t = (Type) com.F.visit(this, null);
		
		return t;		
	}
	public Object visitCompoundTerm(Termo_composto com, Object arg)
	{
		Type t = null;
		Type T1 = (Type) com.F1.visit(this, null);
		Type T2 = (Type) com.F2.visit(this, null);
		String Operador = com.OP_MUL;  
		
		// <op-mul> ::= ( * | / | and )
		//  ( * | / ) 	ONLY WITH INTEGER, REAL, LITERAL
		// and 			ONLY WITH BOOLEAN
 		if(Operador.equals("*") || Operador.equals("/"))
		{
			if(T1.getKind() == Type.BOOL || T2.getKind() == Type.BOOL)
			{
				t = Type.error;
			}
			else
			{
				if(T1.getKind() == Type.REAL || T2.getKind() == Type.REAL)
					t = Type.real;
				else
					t = Type.integer;
			}
		}
		else if(Operador.equals("and"))
		{
			if(T1.getKind() != Type.BOOL || T2.getKind() != Type.BOOL)
			{
				t = Type.error;
			}
			else
			{
				t = Type.bool;
			}
		}
		
		return t;
	}
	
	// Fator 
	public Object visitFatorVar(Fator_VAR com, Object arg)
	{
		Type t = (Type) com.V.visit(this, null);
		com.type = t;								// DECORATION
		
		return t;
	}
	public Object visitFatorLit(Fator_LIT com, Object arg)
	{
		if(com.kind == Token.TRUE || com.kind == Token.FALSE)
			com.type = Type.bool;
		else if(com.kind == Token.INTLITERAL)
			com.type = Type.integer;
		else if(com.kind == Token.FLOATLIT)
			com.type = Type.real;
		else
			com.type = Type.lit;						// DECORATION 
		
		return com.type;
	}
	public Object visitFatorExp(Fator_EXP com, Object arg)
	{
		com.type = (Type) com.E.visit(this, null);
		
		return com.type;							// DECORATION
	}
	
	// Variable
	public Object visitSimpleVar(Var_simples com, Object arg)
	{
		String Id = (String) com.I.visit(this, null);
		Type Tipo = (Type) mapTable.retrieve(Id);
		
		com.type = Tipo;			// DECORATION
		
		// TODO:
		// CONTEXTUAL ISN'T WORKING WITH [EXPRESSAO]
		if(com.E != null)
			com.E.visit(this, null);
		
		return com.type;
	}
	
	// Expressions
	public Object visitSimpleExpression(Expressao_s com, Object arg)
	{
		Type t = (Type) com.E.visit(this, null);
		
		return t;
	}
	public Object visitCompoundExpression(Expressao_composta com, Object arg)
	{
		Type t = null;
		Type T1 = (Type) com.E1.visit(this, null);
		Type T2 = (Type) com.E2.visit(this, null);
		String Operador = com.OP_REL;  
		
		// TODO:
		// <op-rel> ::= ( <(=|>|<vazio>) | >(=|<vazio>) | = )
		// <(=|>|<vazio>) | >(=|<vazio>) ONLY WITH NUMBERS
		// = ONLY NUMBER WITH NUMBERS OR BOOLEAN WITH BOOLEAN
		if(Operador.equals("<") || Operador.equals("<=")
			|| Operador.equals(">") || Operador.equals(">="))
		{
			if(T1.getKind() == Type.BOOL || T2.getKind() == Type.BOOL)
			{
				t = Type.error;
			}
			else
			{
				t = Type.bool;
			}
		}
		else if(Operador.equals("=") || Operador.equals("<>"))
		{
			if((T1.getKind() == Type.BOOL && T2.getKind() != Type.BOOL) 
				|| (T1.getKind() != Type.BOOL && T2.getKind() == Type.BOOL))
			{
				t = Type.error;
			}
			else
			{
				t = Type.bool;
			}
		}
		
		return t;
	}
	
	// Expression Simple
	public Object visitSimpleExpressionSimple(Expressao_simples_simples com, Object arg)
	{
		Type t = (Type) com.T.visit(this, null);
		
		return t;
	}
	public Object visitCompoundExpressionSimple(Expressao_simples_composta com, Object arg)
	{
		Type t = null;
		Type T1 = (Type) com.T1.visit(this, null);
		Type T2 = (Type) com.T2.visit(this, null);
		String Operador = com.OP_AD;  
		
		// TODO:
		// <op-ad> ::= (+ | - | or )
		//  ( + | - ) 	ONLY WITH INTEGER, REAL, LITERAL
		// or 			ONLY WITH BOOLEAN
 		
		if(Operador.equals("+") || Operador.equals("-"))
		{
			if(T1.getKind() == Type.BOOL || T2.getKind() == Type.BOOL)
			{
				t = Type.error;
			}
			else
			{
				if(T1.getKind() == Type.REAL || T2.getKind() == Type.REAL)
					t = Type.real;
				else
					t = Type.integer;
			}
		}
		else if(Operador.equals("or"))
		{
			if(T1.getKind() != Type.BOOL || T2.getKind() != Type.BOOL)
			{
				t = Type.error;
			}
			else
			{
				t = Type.bool;
			}
		}
		
		return t;
	}
	
	// Type
	public Object visitSimpleType(Tipo_simples com, Object arg)
	{
		return null;
	}
	
	public Object visitArrayType(Tipo_array com, Object arg)
	{
		com.T1.visit(this, null);
		return null; 
	}
	
	
 	// Command
	public Object visitIfCommand(Comando_IF com, Object arg)
	{
		Type T1 = (Type) com.E1.visit(this, null);
		com.C1.visit(this, null);
		
		String msg1 = new String("\t\tThe line "+com.line+" contain a context error.");
		String msg2 = new String("\t\tThe type of Expression is "+Type.spellings[T1.getKind()]+
							" but the compiler expect Bolean.");
		String msg = new String(msg1+"\n"+msg2);
		if( T1.getKind() != Type.BOOL )
		{
			handle_error(msg);
		}
		
		return null;
	}
	
	public Object visitIfElseCommand(Comando_IF_composto com, Object arg)
	{
		Type T1 = (Type) com.E1.visit(this, null);
		com.C1.visit(this, null);
		com.C2.visit(this, null);
		
		String msg1 = new String("\t\tThe line "+com.line+" contain a context error.");
		String msg2 = new String("\t\tThe type of Expression is "+Type.spellings[T1.getKind()]+
							" but the compiler expect Bolean.");
		String msg = new String(msg1+"\n"+msg2);
		if(T1.getKind() != Type.BOOL)
		{
			handle_error(msg);
		}
		
		return null;
	}
	
	public Object visitIfSequentialCommand(Comando_Seq com, Object arg)
	{
		com.C1.visit(this, null);
		
		if(com.C2 != null)
			com.C2.visit(this, null);
		
		return null;
	}
	
	public Object visitVarCommand(Comando_VAR com, Object arg)
	{
		Type T1 = (Type) com.V1.visit(this, null);
		Type T2 = (Type) com.E1.visit(this, null);
		
		String msg1 = new String("\t\tThe line "+com.line+" contain a context error.");
		String msg2 = new String("\t\tThe type of Variable are "+Type.spellings[T1.getKind()]+
							" and the value are "+Type.spellings[T2.getKind()]);
		String msg = new String(msg1+"\n"+msg2);
				
		// BOOL = BOOL
		if(T1.getKind() == Type.BOOL && T2.getKind() != Type.BOOL)
		{
			handle_error(msg);
		}
		
		// INTEGER = INTEGER | LITERAL
		if(T1.getKind() == Type.INTEGER)
		{
			if( (T2.getKind() != Type.INTEGER) && (T2.getKind() != Type.LITERAL) )
			{ 
				handle_error(msg);
			}
		}
		
		// REAL = INTEGER | REAL | LITERAL
		if(T1.getKind() == Type.REAL && T2.getKind() == Type.BOOL)
		{
			handle_error(msg);
		}
		
		return null;
	}
	
	public Object visitWhileCommand(Comando_WHILE com, Object arg)
	{		
		Type T1 = (Type) com.E1.visit(this, null);
		com.C1.visit(this, null);
		
		String msg1 = new String("\t\tThe line "+com.line+" contain a context error.");
		String msg2 = new String("\t\tThe type of Expression is "+Type.spellings[T1.getKind()]+
							" but the compiler expect Bolean.");
		String msg = new String(msg1+"\n"+msg2);
		
		if(T1.getKind() != Type.BOOL)
		{
			handle_error(msg);
		}
		
		return null;
	}
	
 	
 	public Checker (Program prog)
 	{
 		mapTable = new identificationTable();
		prog.visit(this,null);
 	}
 }
