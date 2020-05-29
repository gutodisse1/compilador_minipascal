/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 13_03_2019
 */
 package minipascal.ast;
 
 public class Type {
 	private byte kind;
 	
 	public static final byte BOOL = 0, INTEGER = 1, REAL = 2, ARRAY = 3, LITERAL = 4, ERROR = 5;
 	
 	public Type (byte kind) {
 		this.kind = kind;
 	}
 	public byte getKind()
 	{
 		return kind;
 	}
 	public boolean equals (Object other) {
 		// Test whether this type is equivalent to other.
 		Type otherType = (Type) other;
 		return (this.kind == otherType.kind
 					|| this.kind == ERROR
 					|| otherType.kind == ERROR );
 	}
 	public static Type bool = new Type(BOOL);
 	public static Type integer = new Type(INTEGER);
 	public static Type real = new Type(REAL);
 	public static Type array = new Type(ARRAY);
 	public static Type lit = new Type(LITERAL);
 	public static Type error = new Type(ERROR);
 	
 	public final static String[] spellings = {
 												"Boolean",
 												"Integer",
 												"Real",
 												"Array",
 												"Literal",
 												"ERROR" };

 }
