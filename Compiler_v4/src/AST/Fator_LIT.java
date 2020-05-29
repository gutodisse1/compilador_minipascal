/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Fator_LIT extends Fator {
    public String Lit;
    public byte kind;
    public Type type;
    
    public Fator_LIT(String Lit, byte kind)
    {
        this.Lit = Lit;
        this.kind = kind;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitFatorLit(this, arg);
    } 
};
