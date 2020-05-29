/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Fator_VAR extends Fator {
    public Variavel V;
    public Type type;
    
    public Fator_VAR(Variavel V)
    {
        this.V = V;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitFatorVar(this, arg);
    } 
};
