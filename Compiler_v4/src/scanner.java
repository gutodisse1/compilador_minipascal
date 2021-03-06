/**
 * Write a description of class Scanner here.
 *
 * @author (Gustavo Marques @gutodisse)
 * @version 26_DEC_2018
 */
package minipascal.syntatic_analyser;

import java.io.*;

public class scanner
{
        // Variables 
        private File file;
        private FileInputStream file_Stream;
        
        private int currentColunn;
        private int currentLine;
        
        private char currentChar;
        private byte currentKind;
        private StringBuilder currentSpelling;
        
        private void errorPrint(char... expected)
        {
            String lineIWant = new String("");
            FileInputStream f_Stream;
            
            try{
                f_Stream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(
                                    new InputStreamReader(f_Stream));
                for(int i = 0; i < currentLine; i++)
                {
                    try {
                        lineIWant = br.readLine();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (IOException e) {
                    e.printStackTrace();
                }
               
             
            if(expected.length > 0)
            {
                if(expected[0] == '\n')
                {
                    System.out.println("\n\t"+file.getName()+":"+currentLine+"-"+currentColunn+
                                ": error: " + "expected <newline> instead of " + currentChar);
                }
                else
                {
                System.out.println("\n\t"+file.getName()+":"+currentLine+"-"+currentColunn+
                                ": error: " + "expected" + expected[0] + "instead of" + currentChar);
                }
            }
            else
            {
                System.out.println("\n\t"+file.getName()+":"+currentLine+"-"+currentColunn+
                                ": error: " + currentChar + " unexpected");
            }
            System.out.println(lineIWant);
            
            //System.out.print("\t\t");
            for(int i = 1; i < currentColunn; i++)
                System.out.print(" ");
            System.out.print("^\n");
        }
        
        public void errorHandler(boolean exit, char expected){
            errorPrint(expected);
        }
        
        public void errorHandler(boolean exit){
            
            errorPrint();
                    
            if(exit)
                System.exit(0);
        }
        
        
        private void takeIt() {
            try {
                    currentSpelling.append(currentChar);
                    
                    if(currentChar == '\n')
                    {
                        currentColunn = 0;
                        currentLine = currentLine + 1;
                    }
                    else
                    {
                        currentColunn = currentColunn + 1;
                    }
                    
                    if(file_Stream.available() >= 0)
                    {
                        currentChar = (char) file_Stream.read();
                        
                    }
                    else
                    {
                        errorHandler(true);
                    }
                    
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        
        private void take (char expectedChar) {
            if(currentChar == expectedChar){
                takeIt();
            } else
                errorHandler(true,expectedChar);
        }
            
        private void scanSeparator() throws IOException {
            boolean multiline;
            switch(currentChar) {
                case '!': 
                    takeIt();
                    
                    multiline = false;
                    if(currentChar == '!')
                        multiline = true;
                    
                    while( isSymbol(currentChar)
                            || multiline)
                        {
                            
                            takeIt();
                            if(multiline == true && currentChar == '!')
                            {
                                multiline = false;
                                takeIt();   
                            }
                        }
                        
                    if(multiline)
                    {
                        errorHandler(true, '!');
                    }
                    take('\n');
                break;
                case ' ':case '\t':
                    takeIt();
                 break;
                 case '\n':
                    takeIt();
                break;
            };
        }
       
        private boolean isSeparator(char currentChar){
            switch(currentChar){
                case ' ': case '\t':
                    return true;
                default:
                    return false;
            }
        }
        
        private boolean isSymbol(char currentChar){
        
            if(currentChar > 20 && currentChar < 255)
            {
                return true;
            }
            else
                return false;
                 
        }
        
        private boolean isDigit(char currentChar){
            switch(currentChar){
                case '0':case '1':case '2':case '3':case '4':case '5':
                case '6':case '7':case '8':case '9':
                    return true;
                default:
                    return false;
                }
        }
        
        private boolean isLetter(char currentChar){
            
            switch(currentChar){
                case 'q':case 'w':case 'e':case 'r':case 't':case 'y':
                case 'u':case 'i':case 'o':case 'p':case 'a':case 's':
                case 'd':case 'f':case 'g':case 'h':case 'j':case 'k':
                case 'l':case 'z':case 'x':case 'c':case 'ç':case 'v':
                case 'b':case 'n':case 'm':
                case 'Q':case 'W':case 'E':case 'R':case 'T':case 'Y':
                case 'U':case 'I':case 'O':case 'P':case 'A':case 'S':
                case 'D':case 'F':case 'G':case 'H':case 'J':case 'K':
                case 'L':case 'Z':case 'X':case 'C':case 'Ç':case 'V':
                case 'B':case 'N':case 'M':
            return true;
        default:
            return false;
        }
    }

    private byte scanToken() {
        switch(currentChar){
            case 'q':case 'w':case 'e':case 'r':case 't':case 'y':
            case 'u':case 'i':case 'o':case 'p':case 'a':case 's':
            case 'd':case 'f':case 'g':case 'h':case 'j':case 'k':
            case 'l':case 'z':case 'x':case 'c':case 'ç':case 'v':
            case 'b':case 'n':case 'm':
            case 'Q':case 'W':case 'E':case 'R':case 'T':case 'Y':
            case 'U':case 'I':case 'O':case 'P':case 'A':case 'S':
            case 'D':case 'F':case 'G':case 'H':case 'J':case 'K':
            case 'L':case 'Z':case 'X':case 'C':case 'Ç':case 'V':
            case 'B':case 'N':case 'M':
                takeIt();
                while(isLetter(currentChar)
                        || isDigit(currentChar))
                        takeIt();
                return Token.IDENTIFIER;
            case '0':case '1':case '2':case '3':case '4':case '5':
            case '6':case '7':case '8':case '9':
                takeIt();
                while(isDigit(currentChar))
                    takeIt();
                
                if(currentChar == '.')
                {
                    takeIt();
                    while(isDigit(currentChar))
                        takeIt();
                    return Token.FLOATLIT;
                }
                                
                return Token.INTLITERAL;
                
            case ',':
                takeIt();
                return Token.COMMA;
            case ':':
                takeIt();
                if(currentChar == '=')
                {
                    takeIt();
                    return Token.IS;
                    
                }
                return Token.COLON;
            case '.':
                takeIt();
                if(isDigit(currentChar))
                {
                    while(isDigit(currentChar))
                        takeIt();
                    return Token.FLOATLIT;
                }
                return Token.DOT;
                    
            case ';':
                takeIt();
                return Token.SEMICOLON;
            case '+':case '-':
                takeIt();
                return Token.OP_AD;
            case '*':case '/':
                takeIt();
                return Token.OP_MUL;
            case '<':
                takeIt();
                if( currentChar == '=' ||
                    currentChar == '>')
                    takeIt();
                return Token.OP_REL;
            case '>':
                takeIt();
                if( currentChar == '=')
                    takeIt();
                return Token.OP_REL;
            case '=':
                takeIt();
                return Token.OP_REL;    
            case '[':
                takeIt();
                return Token.R_SQUARE;
            case ']':
                takeIt();
                return Token.L_SQUARE;
            case '(':
                takeIt();
                return Token.R_PAREN;
            case ')':
                takeIt();
                return Token.L_PAREN;
            case '\0':
                //takeIt();
                return Token.EOT;
            default:
                errorHandler(true);  
                return -1;
        }
    };

    public Token scan() throws IOException {
        currentSpelling = new StringBuilder("");
        
        while((currentChar == '!'
                || currentChar == ' '
                || currentChar == '\t'
                || currentChar == '\n') && file_Stream.available() > 0) {
            scanSeparator();
        }
        currentSpelling = new StringBuilder("");
        if(file_Stream.available() > 0)
        {
            currentKind = scanToken();
        }
        else if(file_Stream.available() == 0)
                currentKind = Token.EOT;
        
        return new Token (currentKind, currentSpelling.toString(),currentColunn,currentLine);   
    }
    
    public void run() throws IOException
    {
        Token currentToken = scan();
        currentToken.print();

        
        while (currentToken.kind != Token.EOT) {
            currentToken = scan();
            currentToken.print();
        }
    }
    
    /**
    * Constructor for objects of class Scanner
    */
    public scanner(File file_object) throws IOException
    {
        currentLine = 1;
        currentColunn = 0;
        
        
        try {
            file = file_object;
            file_Stream = new FileInputStream(file_object);
            
            currentChar = (char) file_Stream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

