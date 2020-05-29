import java.util.*; 
import minipascal.ast.Tipo;
import minipascal.ast.Tipo_simples;
import minipascal.ast.Tipo_array;
import minipascal.ast.Type;

public class identificationTable {
	
	private Tipo T;
	private int index;
	public Hashtable<String, Tipo> identificationTable;
     
    public identificationTable()
    {
    	identificationTable = new Hashtable<String, Tipo>();
    	T = null;
    }
    
    public void setTipo(Tipo T)
    {
    	this.T = T;
    }
     
	public void enter (String Id)
	{
		if(T != null)
		{
			//System.out.println("ID:"+Id);
			//System.out.println("Type:"+T);
			Tipo t = identificationTable.get(Id);
		
			if(t == null)
			{
				identificationTable.put(Id, T);
			}else
			{
				System.out.println("\n\t[CONTEXT ERROR]");
				System.out.println("\tVariable already declared! - " + Id );
				System.exit(0);
			}
		}
	}
	
	public Type retrieve (String Id) 
	{
		//System.out.println("ID:"+Id);
		//System.out.println("Type:"+identificationTable.get(Id));
		Tipo t = identificationTable.get(Id);
		String spelling = null;
		Type type = Type.error;
		
		if( t != null)
		{
			if(t instanceof Tipo_simples)
			{
				Tipo_simples t_s = (Tipo_simples) t;
				spelling = t_s.spelling;
			}
			
			if(t instanceof Tipo_array)
			{
				Tipo_array t_a = (Tipo_array) t;
				Tipo_simples t_s = (Tipo_simples) t_a.T1;
				spelling = t_s.spelling;
			}
			
			if(spelling.equals(spellings[BOOL])) {
					type = Type.bool;
			}
			else if(spelling.equals(spellings[INTEGER])) {
					type = Type.integer;
			}
			else if(spelling.equals(spellings[REAL])) {
					type = Type.real;
			}
			else if(spelling.equals(spellings[LITERAL])) {
					type = Type.lit;
			}
		}
		else
		{
			System.out.println("\n\t[CONTEXT ERROR]");
			System.out.println("\tVariable not declared! - " + Id);
			System.exit(0);
		}
		return type;
	}
	public static final byte BOOL 		= 0, 
							 INTEGER 	= 1, 
							 REAL 		= 2, 
							 LITERAL 	= 3, 
							 ERROR 		= -1;
							 
	public final static String[] spellings = {
        "boolean",
        "integer",
        "real",
        "literal" };
        
}
