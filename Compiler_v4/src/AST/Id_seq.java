/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Id_seq extends Id {
    public Id I1, I2;
    
    public Id_seq(Id I1, Id I2)
    {
        this.I1 = I1;
        this.I2 = I2;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitCompoundId(this, arg);
    } 
}

