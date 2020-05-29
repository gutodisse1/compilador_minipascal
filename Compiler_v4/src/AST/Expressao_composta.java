/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Expressao_composta extends Expressao {
    public Expressao E1, E2;
    public String OP_REL;
    
    public Expressao_composta(Expressao E1, String OP_REL, Expressao E2)
    {
        this.E1 = E1;
        this.OP_REL = OP_REL;
        this.E2 = E2;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitCompoundExpression(this, arg);
    } 
};
