/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Comando_VAR extends Comando {
    public Variavel V1;
    public Expressao E1;
    public int line;
    
    public Comando_VAR(Variavel V1, Expressao E1, int line)
    {
        this.V1 = V1;
        this.E1 = E1;
        this.line = line;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitVarCommand(this, arg);
    } 
};


