/**
 * Write a description of class Scanner here.
 *
 * @author (Gustavo Marques @gutodisse)
 * @version 26_DEC_2018
 */
package minipascal.syntatic_analyser;

public class Token
{
    // instance variables - replace the example below with your own
    public byte kind;
    public String spelling;
	public int col;
	public int line;
	
    /**
     * Constructor for objects of class Token
     */
    public Token(byte kind, String spelling, int col, int line)
    {
        // initialise instance variables
        this.kind 		= kind;
        this.spelling	= spelling;
        this.col 		= col;
        this.line 		= line;
        
        if(kind == IDENTIFIER) {
            
            for(int k = PROGRAM; k<= FALSE; k++){
                if(spelling.equals(spellings[k])) {
                    this.kind =(byte) k; break;
                }
            }
        }
    }
    
     public void print() {
        System.out.println("[TOKEN]:\t"+spellings[kind].toUpperCase());
        System.out.println("\tKind:\t"+kind);
        System.out.println("\tSpell:\t"+spelling);
    }   
    
    // Constants denoting different kings of token
    public final static byte
        IDENTIFIER  = 0,
        INTLITERAL  = 1,
        OPERADOR    = 2,
        FLOATLIT    = 3,
        PROGRAM     = 4,
        BEGIN       = 5,
        END         = 6,
        VAR         = 7,
        ARRAY       = 8,
        INTEGER     = 9,
        REAL        = 10,
        BOOLEAN     = 11,
        IF          = 12,
        ELSE        = 13,
        THEN        = 14,
        WHILE       = 15,
        DO          = 16,
        OR          = 17,
        AND         = 18,
        OF          = 19,
        EOT         = 20,
        SEMICOLON   = 21,
        OP_AD       = 22,
        OP_MUL      = 23,
        OP_REL      = 24,
        COLON       = 25,
        COMMA       = 26,
        DOT         = 27,
        IS          = 28,
        TRUE        = 29,
        FALSE       = 30,
        L_SQUARE     = 31,
        R_SQUARE     = 32,
        L_PAREN    = 33,
        R_PAREN    = 34;
    // 
    public final static String[] spellings = {
        "<IDENTIFIER>",
        "<INTLITERAL>",
        "<OPERADOR>",
        "<FLOATLIT>",
        "program",
        "begin",
        "end",
        "var",
        "array",
        "integer",
        "real",
        "boolean",
        "if",
        "else",
        "then",
        "while",
        "do",
        "or",
        "and",
        "of",
        "<EOT>",
        "<SEMICOLON>",
        "<OP_AD>",
        "<OP_MUL>",
        "<OP_REL>",
        "<COLON>",
        "<COMMA>",
        "<DOT>",
        "<IS>",
        "true",
        "false",
        "]",
        "[",
        ")",
        "("
    };
}
