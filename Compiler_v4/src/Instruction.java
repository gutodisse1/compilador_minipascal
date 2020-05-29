/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 23_03_2019
 */
 public class Instruction {
 	public byte op;
 	public byte r;
 	public byte n;
 	public String d;
 	
 	public final static String[] spellings = {
 	    "LOAD",
 	    "LOADA",
 		"LOADI",
 		"LOADL",
 		"STORE",
 		"STOREI",
 		"CALL",
 		"CALLI",
 		"RETURN",
 		"",
 		"PUSH",
 		"POP",
 		"JUMP",
 		"JUMPI",
 		"JUMPIF", 
 		"HALT"		
 	};
 	
 	public static final byte 
 		LOADop		=	0,	LOADAop		=	1,
 		LOADIop		=	2,	LOADLop		=	3,
 		STOREop		=	4,	STOREIop	=	5,
 		CALLop		=	6,	CALLIop		=	7,
 		RETURNop	=	8,
 		PUSHop		=	10,	POPop		=	11,
 		JUMPop		=	12,	JUMPIop		=	13,
 		JUMPIFop	=	14,	HALTop		=	15;		
 			
 	public static final byte
 		CBr		=	0,	CTr	=	1,	PBr	=	2,	PTr	=	3,
 		SBr		=	0,	STr	=	5,	HBr	=	6,	HTr	=	7,
 		LBr		=	8,	L1r	=	9,	L2r	=	10,	L3r	=	11,
 		L4r		=	12,	L5r	=	13,	L6r	=	14,	CPr	=	15;
 		
 	public Instruction (byte op, byte r, byte n, String d)
 	{
 		this.op = op;
 		this.r  = r;
 		this.n  = n;
 		this.d  = d;
 	}
 
 }
