/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Expressao_s extends Expressao {
    public Expressao_simples E;
    
    public Expressao_s(Expressao_simples E)
    {
        this.E = E;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitSimpleExpression(this, arg);
    } 
};
