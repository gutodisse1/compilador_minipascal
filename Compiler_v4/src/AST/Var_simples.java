/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Var_simples extends Variavel {
    public Id I;
    public Expressao E;
    public Type type;
    
    public Var_simples(Id I, Expressao E)
    {
        this.I = I;
        this.E = E;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitSimpleVar(this, arg);
    }
};

