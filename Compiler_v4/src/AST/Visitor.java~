/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 13_03_2019
 */

package minipascal.ast;
 
public interface Visitor 
{
		// Program
		public Object visitProgram (Program prog, Object arg);
		
		// Commands
		public Object visitIfCommand(Comando_IF com, Object arg);
		public Object visitIfElseCommand(Comando_IF_composto com, Object arg);
		public Object visitIfSequentialCommand(Comando_Seq com, Object arg);
		public Object visitVarCommand(Comando_VAR com, Object arg);
		public Object visitWhileCommand(Comando_WHILE com, Object arg);
		
		// Body
		public Object visitCorpo(Corpo com, Object arg);
		
		// Declaration
		public Object visitSimpleDeclaration(Declaracao_simples com, Object arg);
		public Object visitCompoundDeclaration(Declaracao_seq com, Object arg);
		
		// Expressions
		public Object visitSimpleExpression(Expressao_s com, Object arg);
		public Object visitCompoundExpression(Expressao_composta com, Object arg);
		
		// Expression Simple
		public Object visitSimpleExpressionSimple(Expressao_simples_simples com, Object arg);
		public Object visitCompoundExpressionSimple(Expressao_simples_composta com, Object arg);
		
		// Fator 
		public Object visitFatorVar(Fator_VAR com, Object arg);
		public Object visitFatorLit(Fator_LIT com, Object arg);
		public Object visitFatorExp(Fator_EXP com, Object arg);
		
		// Identification
		public Object visitSimpleId(Id_simples com, Object arg);
		public Object visitCompoundId(Id_seq com, Object arg);
		
		// Term
		public Object visitSimpleTerm(Termo_unico com, Object arg);
		public Object visitCompoundTerm(Termo_composto com, Object arg);
		
		// Type
		public Object visitSimpleType(Tipo_simples com, Object arg);
		public Object visitArrayType(Tipo_array com, Object arg);
		
		// Variable
		public Object visitSimpleVar(Var_simples com, Object arg);
		
}
