/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Program extends AST {
    public Id I;
    public Corpo Cr;
    
    public Program(Id I, Corpo Cr)
    {
        this.I = I;
        this.Cr = Cr;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitProgram(this, arg);
    }
}
