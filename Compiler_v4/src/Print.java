/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 13_03_2019
 */
package minipascal.syntatic_analyser;

import java.util.Stack; 
import minipascal.ast.*;
import minipascal.syntatic_analyser.Token;

public final class Print implements Visitor {
 	private byte phase;
 	private int level = 0;
    private int current_level = 0;
    
    private int begin_activate = 0;
    Stack<Integer> stackOfBegins = new Stack<>();
    
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
 	
 	private void decrease_level()
 	{
 		level = level - 1;
 	}
 	private void increase_level()
 	{
 		level = level + 1;
 	}
 	
 	private void print_NewLine () 
    {
        print_It("\n");
        current_level = 0;
    } 
    
    private void print (String text) 
    {
        for(; current_level < level; current_level++){
            print_It("\t");
        }
        print_It(text);
    }
    
    private void print_It (String text) 
    {
        System.out.print(text);
    }
 	
 	// Program
 	public Object visitProgram (Program prog, Object arg) 
 	{
 		print("program ");
 		prog.I.visit(this, null);
 		print(";");
 		
 		print_NewLine();
 		
 		prog.Cr.visit(this, null);
 		print(".");
		print_NewLine();
 		return null;
 	}
 	
 	// Identification
	public Object visitSimpleId(Id_simples com, Object arg)
	{
		print(com.spelling);
		/*
		if(phase == 0)
			//
		else
		*/	
			
		return com.spelling;
	}
	public Object visitCompoundId(Id_seq com, Object arg)
	{
		com.I1.visit(this, null);
		print(", ");
		com.I2.visit(this, null);
		
		return null;
	}
 	
 	// Body
	public Object visitCorpo(Corpo com, Object arg)
	{
		phase = 0;
		//System.out.print("\tChecking Declarations");
		com.D.visit(this, null);
		print_NewLine();
		print("begin");
		print_NewLine();
		increase_level();
		
		phase = 1;
		//System.out.print("\tChecking Commands");
		if(com.C != null)
			com.C.visit(this, null);
		decrease_level();
		print("end");
		return null;
	}
 	
 	// Declaration
	public Object visitSimpleDeclaration(Declaracao_simples com, Object arg)
	{
		print("var ");
		com.I.visit(this, null);
		print(" : ");
		com.T.visit(this, null);
		
		return null;
	}
	public Object visitCompoundDeclaration(Declaracao_seq com, Object arg)
	{
		com.D1.visit(this, null);
		print_NewLine();
		com.D2.visit(this, null);
		print(";");
				
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
		print(" "+com.OP_MUL+" ");
		Type T2 = (Type) com.F2.visit(this, null);
		String Operador = com.OP_MUL;  
				
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
		
		print(com.Lit);
		
		return com.type;
	}
	public Object visitFatorExp(Fator_EXP com, Object arg)
	{
		print(" ( ");
		com.type = (Type) com.E.visit(this, null);
		print(" ) ");
		return com.type;							// DECORATION
	}
	
	// Variable
	public Object visitSimpleVar(Var_simples com, Object arg)
	{
		String Id = (String) com.I.visit(this, null);
		
		// TODO:
		// CONTEXTUAL ISN'T WORKING WITH [EXPRESSAO]
		if(com.E != null)
		{
			print(" [ ");
			com.E.visit(this, null);
			print(" ] ");
		}
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
		print(" "+com.OP_REL+" ");
		Type T2 = (Type) com.E2.visit(this, null);
		String Operador = com.OP_REL;  
		
			
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
		print(" "+com.OP_AD+" ");
		Type T2 = (Type) com.T2.visit(this, null);
		String Operador = com.OP_AD;  
		
				
		return t;
	}
	
	// Type
	public Object visitSimpleType(Tipo_simples com, Object arg)
	{
		print(com.spelling);
		return null;
	}
	
	public Object visitArrayType(Tipo_array com, Object arg)
	{
		print("array[ "+com.L1+" .. "+com.L2+" ] of ");
		com.T1.visit(this, null);
		return null; 
	}
	
	
 	// Command
	public Object visitIfCommand(Comando_IF com, Object arg)
	{
		print("if ");
		Type T1 = (Type) com.E1.visit(this, null);
		print(" then ");
		com.C1.visit(this, null);
			
		return null;
	}
	
	public Object visitIfElseCommand(Comando_IF_composto com, Object arg)
	{
		print("if ");
		Type T1 = (Type) com.E1.visit(this, null);
		print(" then ");
		print_NewLine();
		increase_level();
		com.C1.visit(this, null);
		decrease_level();
		print(" else ");
		print_NewLine();
		increase_level();
		com.C2.visit(this, null);
		decrease_level();
				
		return null;
	}
	
	public Object visitIfSequentialCommand(Comando_Seq com, Object arg)
	{
		if(com.begin_type > 0 ){
			print("begin ");
			print_NewLine();
			increase_level();
			stackOfBegins.push(com.begin_type);
		}
		
		com.C1.visit(this, null);
		if(com.C2 != null)
			com.C2.visit(this, null);
		
		if(stackOfBegins.size() > 0  )
		{
			stackOfBegins.pop();
			decrease_level();
			print("end ");
			print_NewLine();
		}
		
		return null;
	}
	
	public Object visitVarCommand(Comando_VAR com, Object arg)
	{
		Type T1 = (Type) com.V1.visit(this, null);
		print(" := ");
		Type T2 = (Type) com.E1.visit(this, null);
		print(";");
		print_NewLine();
				
		return null;
	}
	
	public Object visitWhileCommand(Comando_WHILE com, Object arg)
	{		
		print("while ");
		Type T1 = (Type) com.E1.visit(this, null);
		print(" do ");
		print_NewLine();
		increase_level();
		com.C1.visit(this, null);
		decrease_level();
				
		return null;
	}
	
 	
 	public Print (Program prog)
 	{
 		prog.visit(this,null);
 	}
 }
