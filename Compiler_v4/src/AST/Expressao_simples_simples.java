/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Expressao_simples_simples extends Expressao_simples {
    public Termo T;
    
    public Expressao_simples_simples(Termo T)
    {
        this.T = T;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitSimpleExpressionSimple(this, arg);
    } 
};
