/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;
 
// =============================================================================
public class Comando_Seq extends Comando {
    public Comando C1, C2;
    public int begin_type;
    
    public Comando_Seq(Comando C1, Comando C2, int begin_type)
    {
        this.C1 = C1;
        this.C2 = C2;
        this.begin_type = begin_type;
    }
    
    public Object visit(Visitor v, Object arg) {
    	return v.visitIfSequentialCommand(this, arg);
    } 
};

