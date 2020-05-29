/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Declaracao_simples extends Declaracao {
    public Id I;
    public Tipo T;
    
    public Declaracao_simples(Id I, Tipo T)
    {
        this.I = I;
        this.T = T;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitSimpleDeclaration(this, arg);
    } 
}

