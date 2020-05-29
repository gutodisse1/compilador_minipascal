/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Tipo_array extends Tipo {
    public String L1, L2;
    public Tipo T1;
    
    public Tipo_array(String L1, Tipo T1, String L2)
    {
        this.L1 = L1;
        this.T1 = T1;
        this.L2 = L2;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitArrayType(this, arg);
    } 
};


