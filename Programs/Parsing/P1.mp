!! 
------------------------------------------------------------------------
    THIS IS A PROGRAM TO TEST THE LEXICAL GRAMMAR OF MINI-PASCAL.
------------------------------------------------------------------------
PROJECT TITLE: LEXICAL TESTER
PURPOSE OF PROJECT: USE ALMOST ALL TOKENS OF MINI-PASCAL LANGUAGE 
VERSION or DATE: 16/03/2019
HOW TO START THIS PROJECT:  
AUTHORS:    GUSTAVO MARQUES ( @GUTODISSE ) 
USER INSTRUCTIONS:
!
    
program Lesson1Program3; ! THIS IS A ONLY LINE COMMENT
var Num1, Num2, Num3, Sum : integer;
var Nums : array[ 1 .. 3 ] of real;
var testes : boolean;
begin 
    Num1 := 1;  ! TEST COMMENT
    Num3 := 1;
    Num3 := .1;
    testes := true;
    
    Sum  := Num1 + Num2; ! TEST COMMENT
    
    if Num1 <= 2 then testes := false;
    while testes do testes := false;

    begin
        !! MULTILINE COMMENT
            WORKING 
        !
    end;
    
end.
