/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Comando_WHILE extends Comando {
    public Expressao E1;
    public Comando C1;
    public int line;
    
    public Comando_WHILE(Expressao E1, Comando C1, int line)
    {
        this.E1 = E1;
        this.C1 = C1;
        this.line = line;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitWhileCommand(this, arg);
    } 
};

