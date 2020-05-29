/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Tipo_simples extends Tipo {
    public String spelling;
    
    public Tipo_simples(String spelling)
    {
        this.spelling = spelling;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitSimpleType(this, arg);
    } 
};


