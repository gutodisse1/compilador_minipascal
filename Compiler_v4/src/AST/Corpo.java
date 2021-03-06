/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Corpo extends AST {
    public Declaracao D;
    public Comando C;
    
    public Corpo(Declaracao D, Comando C)
    {
        this.D = D;
        this.C = C;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitCorpo(this, arg);
    } 
};
