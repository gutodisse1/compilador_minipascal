/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Comando_IF_composto extends Comando {
    public Expressao E1;
    public Comando C1, C2;
    public int line;
    
    public Comando_IF_composto(Expressao E1, Comando C1, Comando C2, int line)
    {
        this.E1 = E1;
        this.C1 = C1;
        this.C2 = C2;
        this.line = line;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitIfElseCommand(this, arg);
    } 
};


