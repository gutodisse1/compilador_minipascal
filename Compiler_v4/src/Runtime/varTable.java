import java.util.*; 


public class varTable {
	
	private int index;
	public Hashtable<String, Short> varTable;
     
    public varTable()
    {
    	varTable = new Hashtable<String, Short>();
    }
    
    public void enter (String Id, short gs)
	{
	    varTable.put(Id, new Short((short)gs));
		
	}
	
	public short retrieve (String Id) 
	{
		return (short) (varTable.get(Id)).shortValue();
	}
	
        
}
