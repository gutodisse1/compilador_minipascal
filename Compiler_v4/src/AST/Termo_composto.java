/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Termo_composto extends Termo {
    public Termo F1, F2;
    public String OP_MUL;
    
    public Termo_composto(Termo F1, String OP_MUL, Termo F2)
    {
        this.F1 = F1;
        this.OP_MUL = OP_MUL;
        this.F2 = F2;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitCompoundTerm(this, arg);
    }
};
