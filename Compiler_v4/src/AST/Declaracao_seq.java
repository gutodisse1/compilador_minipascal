/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Declaracao_seq extends Declaracao {
    public Declaracao D1, D2;
    
    public Declaracao_seq(Declaracao D1, Declaracao D2)
    {
        this.D1 = D1;
        this.D2 = D2;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitCompoundDeclaration(this, arg);
    }
    
}

