import java.util.*;
import antlr.*;

public class Env {

	public Env parent = null;
	public Hashtable symTable = new Hashtable();
	public Hashtable structSymTable = new Hashtable();
	public Hashtable paramSymTable = new Hashtable();
	public Hashtable fieldSymTable = new Hashtable();
	public Hashtable funSymTable = new Hashtable();

	public Env(Env upperLevel) {

		this.parent = upperLevel;

	}

	public Env pop() {
		return parent;
	}

	public void varDecl(String id, String type) throws SemanticException {
		if(symTable.get(id) == null){
			symTable.put(id, type);
		}
		else
		{
			throw new SemanticException("Conflicting type with previous declaration: " + id);
		}
			
	}
	/*
	public void fieldDecl(String id, String type){
		fieldSymTable.put(id, type);
	}
	*/
	public void fieldDecl(String structName, String id, String type){
		Hashtable fields = (Hashtable)structSymTable.get(structName);
		fields.put(id, type);
	}

	public String fieldTypeLookUp(String structName, String id) throws SemanticException
	{
		String type;	
		Hashtable fields = (Hashtable)structSymTable.get(structName);
		type = (String)fields.get(id);
		if(type == null){
			throw new SemanticException("Cannot find symbol: " + id);
		}
		return type;
	}

	public void structDecl(String id){
		structSymTable.put(id, new Hashtable());
	}

	public void funDecl(String id, String type){
		funSymTable.put(id, type);
	}

	public void paramDecl(String id, String type){
		paramSymTable.put(id, type);
	}
	
	public String funLookUp(String id){
		String type;
		type = (String)funSymTable.get(id);
		return type;
	}

	public String varLookUp(String id) {
		String type;
		type = (String)paramSymTable.get(id);
		
		if(type == null){
			
			type = (String)symTable.get(id);

			
			if(type == null) 
			{
				type = (String)structSymTable.get(id);
				if(type == null){
					if(!(parent == null)){
						type = parent.varLookUp(id); 
					}
				}
			}
			return type;
			
		}
		else 
		{
			return type;
		}
	}

	public String  structLookUp(String id) {
		String type;
		if((Hashtable)structSymTable.get(id) != null){
			type = id;
		}
		else 
		{
			if(parent != null){
				type = parent.structLookUp(id);
			}
			else
			{
				type = null;
			}
		}
		return type;
	}
}
