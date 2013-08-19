import java.util.*;
import java.io.*;

public class ActivationRecord implements Cloneable{
	/* global mapping */	
	protected HashMap<String, String> globalMap = null;	
	protected HashMap<String, String> globalStructMap = null;
		
	/* Local Mapping */
	protected HashMap localMap = null;
	protected Vector localVector = null;
	
	/* Parameter Mapping */
	protected HashMap<String, String> paramVar = null;

	/* Struct Mapping */
	protected HashMap<String, LinkedList<String>> structMap = null;
	protected HashMap<String, String> localStructMap = null;
	protected HashMap<String, HashMap<String, String>> nestedStructMap = null;
	protected HashMap<String, String> paramStructMap = null;	

	protected int spillSize;


	public ActivationRecord(){
		localMap = new HashMap <String, Integer>();
		localVector = new Vector();
		globalMap = new HashMap <String, String>();
		localStructMap = new HashMap<String, String> ();
		paramStructMap = new HashMap<String, String> ();
		structMap = new HashMap<String, LinkedList<String>> ();	
		nestedStructMap = new HashMap<String, HashMap<String, String>> ();
		globalStructMap = new HashMap<String, String> ();
		paramVar = new HashMap<String, String>();
		spillSize = 0;
	}

	public ActivationRecord clone(){
		ActivationRecord clonedAR = new ActivationRecord();
		clonedAR.globalMap = (HashMap<String, String>)this.globalMap.clone();
		clonedAR.localMap = (HashMap)this.localMap.clone();
		clonedAR.localVector = (Vector)this.localVector.clone();
		clonedAR.structMap = (HashMap<String, LinkedList<String>>)this.structMap.clone();
		clonedAR.localStructMap = (HashMap<String, String>)this.localStructMap.clone();
		clonedAR.nestedStructMap = (HashMap<String, HashMap<String, String>>)this.nestedStructMap.clone();
		clonedAR.spillSize = this.spillSize;

		return clonedAR;
	}
	
	public HashMap<String, String> getGlobalMap(){
		return this.globalMap;
	}

	public HashMap<String, String> getGlobalStructMap(){
		return this.globalStructMap;
	}
	public HashMap<String, LinkedList<String>> getStructMap(){
		return this.structMap;
	}
	public HashMap<String, HashMap<String, String>> getNestedStructMap(){
		return this.nestedStructMap;
	}
	
	public void setGlobalAR(ActivationRecord globl){
		this.globalMap = globl.getGlobalMap();
		this.globalStructMap = globl.getGlobalStructMap();
		this.structMap = globl.getStructMap();
		this.nestedStructMap = globl.getNestedStructMap();
	}

	public void nestedStructDecl(String fname, String ftype, String strName){
		(nestedStructMap.get(strName)).put(fname, ftype);
	}

	public String getNestedStructType(String fname, String strName){
		
		return (nestedStructMap.get(strName)).get(fname);
	}	
	
	public String getStructType(String vname){
		String ret;
		ret = localStructMap.get(vname);
		if(ret == null){
			ret = paramStructMap.get(vname);
			if(ret == null){
				ret = globalStructMap.get(vname);
				
			}
		}
		return ret;
	}	

	public int getFieldOffset(String strName, String fName){
		
		
		return (4 * ((structMap.get(strName)).indexOf(fName) + 0));
	}
	public void addLocalStruct(String name, String strName){
		
		localStructMap.put(name, strName); 		
	}
	public void addParamStruct(String name, String strName){
		
		paramStructMap.put(name, strName); 		
	}

	public void addGlobal(String directive){
		globalMap.put(directive, directive);
	}

	public void addGlobalStruct(String directive, String structName){
		
		
		
		globalStructMap.put(directive, structName);
		
	}

	public String getGlobalDir(String name){
		return globalMap.get(name);
	}

	public void defineField(String strName, String fieldName){
		
		(structMap.get(strName)).add(fieldName);
		//nestedStructMap.put(strName, new HashMap<String, String> ());
		
	}

	public void defineStruct(String name){
		
		structMap.put(name, new LinkedList<String> ());
		nestedStructMap.put(name, new HashMap<String, String> ());
	}

	
	public int getLocalSize(){
		return localVector.size();
	}
	
	public void addLocal(String name){
		localVector.add(name);
		localMap.put(name, new Integer(localVector.indexOf(name) + 1));
		
	}
	
	public int getVarOffset(String name){
		
		if((Integer)localMap.get(name) == null){
			return -1;
		}
		int i = ((Integer)localMap.get(name)).intValue();
		if(i == 0){
			return -1;
		}
		
		return ((i * 4));
	}

	public int getStructSize(String strName){
		
		return (4 * (structMap.get(strName)).size());
	}


	public int getStackSize(){
		
		int _size = 72;
		int _args = (6 * 4);
		int _spills = (spillSize * 4);
		int _locals = this.getLocalSize() * 4;
		/* args */	
		if((_args % 8) != 0){
			_args += (8 - (_args % 8));
		}

		/* spills */
		if((_spills % 8) != 0){
			_spills += (8 - (_spills % 8));
		}

		_size += _args + _spills + _locals;	
		if((_size % 8) != 0){
			_size += (8 - (_size %8));
		}
		return _size;
	}

	public void clearLocal(){
		localMap.clear();
		localVector.clear();
	}

	public void allocSpill(){
		spillSize++;
	}

	public void addParam(String var, String off){
		paramVar.put(var, off);
	}
	
	public String getParamOff(String var){
		return paramVar.get(var);
	}
}	
