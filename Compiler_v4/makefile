#
# THIS MAKE FILE CREATE THE STRUCT AND THEN COMPILER THE MINIPASCAL PROJECT
#
# @AUTHOR Gustavo Marques ( @GUTODISSE )
# @VERSION 04_03_2019
#

FLAG_CLASSDIR = -d classes
FLAG_BINDIR = -d bin
JC = javac


AST = \
	src/AST/*.java

Syntatic = \
        src/Token.java \
        src/scanner.java \
        src/Parser.java \
        src/Print.java 

Contextual = \
        src/Checker.java \
        src/identificationTable.java     

CodeGeneration = \
        src/Encoder.java \
        src/Instruction.java \
        src/Runtime/*.java

Main = src/Main.java

make:
	@echo "COMPILANDO:"
	@echo "Criando diretorios"
	rm -rf bin && mkdir bin
	@echo "Compilando classes"
	$(JC) $(FLAG_BINDIR) $(CodeGeneration) $(AST) $(Syntatic) $(Contextual) 
	@echo "Linkando o programa"
	$(JC) $(FLAG_BINDIR) -cp "bin/" $(Main)
	@echo "Feito"
	
AST:
	@echo "COMPILANDO APENAS AST"
	rm -rf bin && mkdir bin
	@echo "Compilando classes"
	$(JC) $(FLAG_BINDIR) $(AST)
	@echo "Linkando o programa"
	$(JC) $(FLAG_BINDIR) -cp "bin/" $(Main)
	@echo "Feito"

SYNT:
	@echo "COMPILANDO APENAS AST E SINTATICO"
	rm -rf bin && mkdir bin
	@echo "Compilando classes"
	$(JC) $(FLAG_BINDIR) $(AST) $(Syntatic)
	@echo "Linkando o programa"
	$(JC) $(FLAG_BINDIR) -cp "bin/" $(Main)
	@echo "Feito"

CONTEXT:
	@echo "COMPILANDO APENAS AST, SINTATICO E CONTEXTO"
	rm -rf bin && mkdir bin
	@echo "Compilando classes"
	$(JC) $(FLAG_BINDIR) $(AST) $(Syntatic) $(Contextual)
	@echo "Linkando o programa"
	$(JC) $(FLAG_BINDIR) -cp "bin/" $(Main)
	@echo "Feito"
#
# RM is a predefined macro in make (RM = rm -f)
#
clean:
	rm -rf classes
