/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Fator_EXP extends Fator {
    public Expressao E;
    public Type type;
    
    public Fator_EXP(Expressao E)
    {
        this.E = E;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitFatorExp(this, arg);
    } 
};
