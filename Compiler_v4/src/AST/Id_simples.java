/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Id_simples extends Id {
    public String spelling;
    
    public Id_simples(String spelling)
    {
        this.spelling = spelling;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitSimpleId(this, arg);
    }
}
