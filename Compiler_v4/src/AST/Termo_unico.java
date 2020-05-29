/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Termo_unico extends Termo {
    public Fator F;
    
    public Termo_unico(Fator F)
    {
        this.F = F;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitSimpleTerm(this, arg);
    }
};
