/**
 * Write a description of class Main here.
 *
 * @author Gustavo Marques ( @GUTODISSE ) 
 * @version 05_01_2019
 */
import java.io.*;

import minipascal.syntatic_analyser.*;

public class Main
{   
         
    /**
     * Constructor for objects of class Main
     */
    public static void main(String[] args) throws Exception 
    {
        boolean only            = false;
        boolean only_scanner    = false;
        boolean only_parser		= false;
        boolean p_print         = false;
        boolean only_checker 	= false;
        boolean only_encode     = false;
        
        scanner current_scanner;
         
        String sourceFile_path = new String("");
        String objectFile_path = new String(""); 
        
        File file;
        
        //----------------------------------------------------------------------
        for (int i = 0; i < args.length; i++) {
            if(args[i].charAt(0) == '-')
            {
                switch (args[i].charAt(1)) {
                    case 'i':
                        if(args.length-1 <= i)
                            throw new IllegalArgumentException("Expected file path after: "+args[i]);
                        sourceFile_path = args[i+1];
                    break;
                    case 'f':
                        if(args.length-1 <= i)
                            throw new IllegalArgumentException("Expected file path after: "+args[i]);
                        objectFile_path = args[i+1];
                    break;
                    case 's':
                        only            = true;
                        only_scanner    = true;
                    break;
                    case 'p':
                    	only            = true;
                        only_parser     = true;
                    break;
                    case 'c':
                    	only            = true;
                        only_checker    = true;
                    break;
                    case 'o':
                        only            = true;
                    	p_print		    = true;
                    break;
                    case 'e':
                        only            = true;
                        only_encode     = true;
                    break;
                    case 'h':
                        System.out.println("Pascal Minicompiler. This compiler was developed during the compiler course given by Marcus Ramos at Univasf on 2018.1.");
                        System.out.println("Usage: [-options] -i path to source code");
                        System.out.println("where options include:");
                        System.out.println("\t-s\t To print scanner output;");
                        System.out.println("\t-p\t To print parser output;");
                        System.out.println("\t-c\t To print checker output;");
                        System.out.println("\t-e\t To print encoder output;");
                        System.out.println("\t-o\t To print an AST;");
                        System.out.println("\t-i\t To set path to the source code.");
                        System.out.println("\t-f\t To set path to save the program machine code.");
                        System.exit(0);  
                    break;
                    default:
                        // arg
                        //argsList.add(args[i]);
                    break;
                }
            }
        }    
        
        //----------------------------------------------------------------------
        file = new File(sourceFile_path);
        
        if (!file.exists()) {
            System.out.println("[ERROR]");
            System.out.println("File does not exist.");
            System.exit(0);  
        }
        
        if (!(file.isFile() && file.canRead())) {
            System.out.println(file.getName() + " cannot be read from.");
            System.exit(0);  
        }
        try {
        
            if(!only)
            {
                
                	System.out.print("Checking Syntax");
                    Parser current_parser = new Parser(file);
                    System.out.println("\t\t\tOK");
                    System.out.println("Checking Context:");
                    Checker check = new Checker(current_parser.P);
                    System.out.println("Context\t\t\t\tOK");
                    Encoder encoder = new Encoder(current_parser.P);
                    encoder.salve_code(objectFile_path);             
            }
            else
            {
            
                if(only_scanner)
                {
                    System.out.println("Scanner on: "+file.getName());

                    current_scanner = new scanner(file);
                    current_scanner.run();       
                    
                }
                else if(only_parser)
                {
                	System.out.print("Checking Syntax");
                    Parser current_parser = new Parser(file);
                    System.out.println("\t\t\tOK");
                }
            	else if(only_checker)
                {
                	System.out.print("Checking Syntax");
                    Parser current_parser = new Parser(file);
                    System.out.println("\t\t\tOK");
                    System.out.println("Checking Context:");
                    Checker check = new Checker(current_parser.P);
                    System.out.println("Context\t\t\t\tOK");
                }
                else if(p_print)
                {
                    Parser current_parser = new Parser(file);
                    Print print = new Print(current_parser.P);
                }
                else if(only_encode)
                {
                    Parser current_parser = new Parser(file);
                    System.out.println("CONTEXT CHECK");
		            System.out.println("- - - - - - - - - - - - - - - - - - - ");
                    Checker check = new Checker(current_parser.P);
                    Encoder encoder = new Encoder(current_parser.P);
                    encoder.print_code();  
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
    } 

}

