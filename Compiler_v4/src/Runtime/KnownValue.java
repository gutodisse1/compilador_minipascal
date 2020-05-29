/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 23_03_2019
 */
 
package minipascal.runtime;
 
public class KnownValue extends RuntimeEntity {
    public short value;
    
    public KnownValue(short size, short value) {
        this.size   =   size;
        this.value  =   value;
    }
    
}
