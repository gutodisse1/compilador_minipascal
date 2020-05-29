/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Expressao_simples_composta extends Expressao_simples {
    public Expressao_simples T1, T2;
    public String OP_AD;
    
    public Expressao_simples_composta(Expressao_simples T1, String OP_AD, Expressao_simples T2)
    {
        this.T1 = T1;
        this.OP_AD = OP_AD;
        this.T2 = T2;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitCompoundExpressionSimple(this, arg);
    } 
};
