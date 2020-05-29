/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 23_03_2019
 */
import java.io.*;
import minipascal.ast.*;
import minipascal.syntatic_analyser.*;
import minipascal.runtime.*;

public class Encoder implements Visitor {
  
    private byte phase;
    private Instruction[] code = new Instruction[1024];
    private short nextInstrAddr = 0;
    
    private varTable varTable;
    
    public void salve_code(String fileName) {
        int i;
        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            for(i=0;i<nextInstrAddr;i++)
            {
                bufferedWriter.write(codeLine(i)+"\n");
            }
        
            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println("[ERROR]");
            System.out.println("\tPlease, verify object path. (-f param);");
            System.out.println(
                "\tError writing to file '"
                + fileName + "'");
            System.exit(0); 
            
            // Or we could just do this:
            // ex.printStackTrace();
        }    
    }
    
    public void print_code() {
        int i;
        for(i=0;i<nextInstrAddr;i++)
        {
            System.out.println(codeLine(i));
        }
    }
    private String codeLine(int currentLine) {
        String msg1;
        byte op = code[currentLine].op;
        byte n = code[currentLine].n;
        byte r = code[currentLine].r;
        String d = code[currentLine].d;
        
        String msg = new String("\t"+currentLine+" : \t"+Instruction.spellings[op]+" ");
        
        switch(code[currentLine].op) {
            case Instruction.LOADop:
                    msg1 = new String("("+n+") "+d+"["+r+"]");
            break;
            case Instruction.LOADAop:
                    msg1 = new String(d+"["+r+"]");
            break;
            case Instruction.LOADLop:
                    msg1 = new String(d);
            break;
            case Instruction.STOREop:
                    msg1 = new String("("+n+") "+d+"["+r+"]");
            break;     
            case Instruction.PUSHop:
                    msg1 = new String(d);
            break;
            case Instruction.CALLop:
                    msg1 = new String("("+n+") "+d+"["+r+"]");
            break;
            case Instruction.JUMPop:
                    msg1 = new String(d+"["+r+"]");
            break;
            case Instruction.JUMPIFop:
                    msg1 = new String("("+n+") "+d+"["+r+"]");
            break;
            case Instruction.POPop:
                    msg1 = new String("("+n+") "+d);
            break;
            case Instruction.STOREIop:
                    msg1 = new String("("+n+") ");
            break;       
            case Instruction.HALTop:
                    msg1 = new String("");
            break;  
            default:
                     msg1 = new String("\nCOMMAND NOT IMPLEMENTED");
                    //System.out.println(" ("+n+") "+d+"["+r+"]");
            break;
        
        }
        
        return new String(msg+msg1);
        
    }
    
    private void emit(byte op, byte n, byte r, String d) {
        code[nextInstrAddr++] = new Instruction(op, r, n, d);
  	}
  	
  	private static short shortValueOf( Object obj) {
  	    //return 0;
  	    return ((Short) obj).shortValue();
  	}
  	
  	private void patch( short addr, String d) {
  	    code[addr].d = d;
  	} 
  	    
  	// Program
 	public Object visitProgram (Program prog, Object arg) 
 	{
 	    Short LB;
 	    phase = -1;
 		prog.I.visit(this, arg);
 		phase = 0;
 		LB = (Short) prog.Cr.visit(this, arg);
 		
 		emit(Instruction.POPop,(byte) ((Short) LB).shortValue(), (byte) 0, "0");
 		emit(Instruction.HALTop,(byte) 0, (byte) 0, "0");
 		
 		return null;
 	}
 	
 	
	public Object visitSimpleId(Id_simples com, Object arg)
	{
		short s  = 0;
		short gs;
		
		if(phase == 0)
	    {
            gs = shortValueOf(arg);
            s  = (short) 1;
            
            
            com.entity = new KnowAddress((short)1, gs);
            varTable.enter(com.spelling,gs);
            
            System.out.println("\t["+varTable.retrieve(com.spelling)+"]\t"+com.spelling);
            
	    }
		
			
		return s;
	}
	public Object visitCompoundId(Id_seq com, Object arg)
	{
	    short gs = shortValueOf(arg);
		short s1 = (short) com.I1.visit(this, arg);
		short s2 = (short) com.I2.visit(this, new Short((short)(gs+s1)));
		
		return (short)(s1+s2) ;
	}
 	
 	// Body
	public Object visitCorpo(Corpo com, Object arg)
	{
		short LB;
		phase = 0;
		System.out.println("MEMORY MAP");
		System.out.println("- - - - - - - - - - - - - - - - - - - ");
		System.out.println("\t[ADDR]\tIdentifier");
		LB = new Short((short) com.D.visit(this, arg));
				
		phase = 1;
		System.out.println("\nMACHINE CODE");
		System.out.println("- - - - - - - - - - - - - - - - - - - ");
		if(com.C != null)
			com.C.visit(this, arg);
		return LB;
	}
 	
 	// Declaration
	public Object visitSimpleDeclaration(Declaracao_simples com, Object arg)
	{
		short s = (short) com.I.visit(this, arg);
		short t = shortValueOf( com.T.visit(this, arg) );
		
		emit(Instruction.PUSHop, (byte) 0, (byte) 0, String.valueOf(s*t));
						
		return new Short((short)(s*t));
	}
	public Object visitCompoundDeclaration(Declaracao_seq com, Object arg)
	{
	    short gs = shortValueOf(arg);
				
		short s1 = shortValueOf(com.D1.visit(this, arg));
		short s2 = shortValueOf(com.D2.visit(this, new Short((short)(gs+s1))));
						
		return new Short((short)(s1+s2));
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
		
		if(Operador.equals("*")) {
		    Operador = "mult";
		}
		else if(Operador.equals("/")) {
		    Operador = "div";
		} 
		
		emit(Instruction.CALLop, (byte) 0, (byte) 0, Operador);
				
		return t;
	}
	
	// Fator 
	public Object visitFatorVar(Fator_VAR com, Object arg)
	{
	    RuntimeEntity entity = (RuntimeEntity) com.V.visit(this, arg);
	    
		short d = ((KnowAddress) entity).address;
		emit(Instruction.LOADAop, (byte) 0, Instruction.SBr, String.valueOf(d));
		
		return null;
	}
	public Object visitFatorLit(Fator_LIT com, Object arg)
	{
	    short d;
		// DECORATION 
		
		emit(Instruction.LOADLop, (byte) 0, Instruction.SBr, com.Lit);
		
		return com.type;
	}
	public Object visitFatorExp(Fator_EXP com, Object arg)
	{
	    byte temp;
	    temp = phase;
	    phase = 3;
		com.type = (Type) com.E.visit(this, arg);
		phase = temp;
		return com.type;							// DECORATION
	}
	
	// Variable
	public Object visitSimpleVar(Var_simples com, Object arg)
	{
	    short E = (short) 0;
		com.I.visit(this, arg);
		
		short addr = varTable.retrieve(((Id_simples) com.I).spelling);
        //System.out.println(((KnowAddress) com.entity).address);
		
		// TODO:
		// CONTEXTUAL ISN'T WORKING WITH [EXPRESSAO]
		if(com.E != null)
		{
			com.E.visit(this, arg);
		    E = (short) 1;
		}
		
	    com.entity = new KnowAddress(E, addr);
	    return com.entity;
		
	}
	
	// Expressions
	public Object visitSimpleExpression(Expressao_s com, Object arg)
	{
		Short s = (Short) com.E.visit(this, null);
		return s;
	}
	public Object visitCompoundExpression(Expressao_composta com, Object arg)
	{
		Type t = null;
		Type T1 = (Type) com.E1.visit(this, null);
		Type T2 = (Type) com.E2.visit(this, null);
		String Operador = com.OP_REL;  
		
		if(Operador.equals("<=")) {
		    Operador = "le";
		}
		else if(Operador.equals("<")) {
		    Operador = "lt";
		}
		else if(Operador.equals(">=")) {
		    Operador = "ge";
		}
		else if(Operador.equals(">")) {
		    Operador = "gt";
		}   
		
		emit(Instruction.CALLop, (byte) 0, (byte) 0, Operador);
			
		return t;
	}
	
	// Expression Simple
	public Object visitSimpleExpressionSimple(Expressao_simples_simples com, Object arg)
	{
		com.T.visit(this, null);
		return null;
	}
	public Object visitCompoundExpressionSimple(Expressao_simples_composta com, Object arg)
	{
		Type t = null;
		Type T1 = (Type) com.T1.visit(this, null);
		Type T2 = (Type) com.T2.visit(this, null);
		String Operador = com.OP_AD;  
		
		if(Operador.equals("+")) {
		    Operador = "add";
		}
		else if(Operador.equals("-")) {
		    Operador = "sub";
		} 
		
		emit(Instruction.CALLop, (byte) 0, (byte) 0, Operador);
				
		return t;
	}
	
	// Type
	public Object visitSimpleType(Tipo_simples com, Object arg)
	{
		return new Short((short)1);
	}
	
	public Object visitArrayType(Tipo_array com, Object arg)
	{
		com.T1.visit(this, null);
		
		Short s1 = Short.valueOf(com.L1); 
		Short s2 = Short.valueOf(com.L2);
		
		return new Short((short)(s2-s1+1)); 
	}
	
	
 	// Command
	public Object visitIfCommand(Comando_IF com, Object arg)
	{
	    short i;
	    
		Type T1 = (Type) com.E1.visit(this, arg);
		
		i = nextInstrAddr;
		emit(Instruction.JUMPIFop, (byte) 0, Instruction.CBr, "0");
		
		com.C1.visit(this, arg);
		
		emit(Instruction.JUMPop, (byte) 0, Instruction.CBr, "0");
		
		patch(i,String.valueOf(nextInstrAddr));
			
		return null;
	}
	
	public Object visitIfElseCommand(Comando_IF_composto com, Object arg)
	{
	    short i, j, g;
		Type T1 = (Type) com.E1.visit(this, null);
		
		i = nextInstrAddr;
		emit(Instruction.JUMPIFop, (byte) 0, Instruction.CBr, "0"); // jump g
		com.C1.visit(this, arg);
		
		j = nextInstrAddr;
		emit(Instruction.JUMPop, (byte) 0, Instruction.CBr, "0"); // jump h
		
		g = nextInstrAddr;
		com.C2.visit(this, arg);
		
		patch(i,String.valueOf(g));
		patch(j,String.valueOf(nextInstrAddr));
				
		return null;
	}
	
	public Object visitIfSequentialCommand(Comando_Seq com, Object arg)
	{
		
		com.C1.visit(this, arg);
		if(com.C2 != null)
			com.C2.visit(this, arg);
				
		return null;
	}
	
	public Object visitVarCommand(Comando_VAR com, Object arg)
	{
	    
	    short gs = shortValueOf(arg);
	    short s = (short) 1;
	    
		RuntimeEntity entity = (RuntimeEntity) com.V1.visit(this, arg);
		short d = ((KnowAddress) entity).address;
		if( entity.size != 0)
		{
		    emit(Instruction.LOADLop, (byte) 0, Instruction.SBr, String.valueOf(d));
		    emit(Instruction.CALLop, (byte) 0, (byte) 0, "add");
		}
		
		
		com.E1.visit(this, arg);
		
		
		if( entity.size == 0)
		{    
		    emit(Instruction.STOREop, (byte) s, Instruction.SBr, String.valueOf(d));
		}	
		else
		{
		    emit(Instruction.STOREIop, (byte) s, Instruction.SBr, "0");
		}		
		return null;
	}
	
	public Object visitWhileCommand(Comando_WHILE com, Object arg)
	{		
		/* J: JUMP H
         * G: EXECUTE C
         * H: EVALUATE E
         *    JUMPIF(1) G
         */
          		
		short j,g,h;
		
		// J: JUMP H
		j = nextInstrAddr;
		emit(Instruction.JUMPIFop, (byte) 0, Instruction.CBr, "0"); // jump g
		g = nextInstrAddr;
		com.C1.visit(this, arg);
		
		h = nextInstrAddr;
		com.E1.visit(this, arg);
		emit(Instruction.JUMPIFop, (byte) 1, Instruction.CBr, String.valueOf(g)); // jump g
		
		patch(j,String.valueOf(h));
				
		return null;
	}
  	
    public Encoder (Program prog) {
        varTable = new varTable();
        
        prog.visit(this, new Short((short)0));
                
    }
  }
