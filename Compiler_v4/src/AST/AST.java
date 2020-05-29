/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 28_02_2019
 */

package minipascal.ast;

import minipascal.runtime.*;
 
public abstract class AST 
{
	public abstract Object visit (Visitor v, Object arg);
	
	public RuntimeEntity entity;
};

