!! 
------------------------------------------------------------------------
    THIS IS A PROGRAM TO TEST THE SYNTATICAL OF MINI-PASCAL.
------------------------------------------------------------------------
PROJECT TITLE: SYNTATICAL TESTER
PURPOSE OF PROJECT: USE ALMOST ALL TOKENS OF MINI-PASCAL LANGUAGE 
VERSION or DATE: 5/01/2019
HOW TO START THIS PROJECT:  
AUTHORS:    GUSTAVO MARQUES ( @GUTODISSE ) 
USER INSTRUCTIONS:
!
    
program Lesson1Program3; ! THIS IS A ONLY LINE COMMENT
var Nums : array[ 1 .. 3 ] of array[ 1 .. 3 ] of real;
var Num1, Num2, Num3 : integer;
var Sum : real;
var testes : boolean;
begin 
    Num1 := 1;  ! TEST COMMENT
    Num3 := 1.1;
    Num3 := .1;
    testes := true;
    
    Sum  := Num1 + Num2; ! TEST COMMENT
    
    if Num1 <= 2 then testes := false;
    while (testes) do 
    begin
        testes := false;
    end;
        
    begin
        if Num1 <= 2 then 
            begin
                testes := false;
            end ; 
            
        Sum  := Num1 + Num2 + 3 + ( 2 < 10 );
    end;
    
end.
