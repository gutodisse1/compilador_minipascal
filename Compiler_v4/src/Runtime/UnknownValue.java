/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 23_03_2019
 */
 
package minipascal.runtime;
 
public class UnknownValue extends RuntimeEntity {
    public short address;
    
    public UnknownValue(short size, short address) {
        this.size       =   size;
        this.address    =   address;
    }
}
