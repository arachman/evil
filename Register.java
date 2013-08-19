import java.io.*;
import java.util.*;

public class Register extends Operand {

	
	protected String name;
	protected int number;
	protected String color;
	protected boolean recentlyRead;
	protected static HashMap regMap = new HashMap<String, Register> ();
	protected static int regNumber = 0;
	public Register(String name){
		recentlyRead = false;
		if((name.startsWith("%")) || (name.equals("rarp")) || (name.equals("ccr"))){
			this.name = name;
		}
		else
		{
			this.name = ("r"+name);
			this.color = this.name;
			this.number = Integer.parseInt(name);
		}
	}
	public static void instantiate(){
		regMap.put("rarp", new Register("rarp"));
		regMap.put("ccr", new Register("ccr"));
	}

	public void readSet(){
		recentlyRead = true;
	}

	public void readUnset(){
		recentlyRead = false;
	}

	public String getColor(){
		return color;
	}

	public static void clear(){
		regMap.clear();
	}

	public boolean isRecentlyRead(){
		return recentlyRead;
	}
	public static Register getReg(String name){
		instantiate();
		Register reg;
		String regName;
		reg = (Register)regMap.get(name);
		/*
		if(reg == null){
			regName = new String("r"+nextReg());
			regMap.put(name, regName);
			return new Register(regName);
		}
		*/
		return reg;
	}
	
	public static void remove(String name){
		Register r = (Register)regMap.get(name);
		regMap.remove(r.getName());
		regMap.remove(name);
	}
	
	public static Register make(){
		//Register r = new Register("r"+nextReg());
		Register r = new Register(Integer.toString(nextReg()));
		regMap.put(r.getName(), r);
		return r;
	}

	public static Register make(String name){
		int regName;
		Register reg;
		//regName = ("r"+nextReg());
		regName = nextReg();
		reg = new Register(Integer.toString(regName));	
		regMap.put(name, reg);
		regMap.put("r"+regName, reg);
		return reg;
	}

	public String getName(){
		return name;
	}

	public int getNumber(){
		return number;
	}
	
	public static int nextReg(){
		return (++regNumber);
	}

	public void setName(String name){
		this.name = name;
	}

	public static Vector<Register> toRegisters(BitSet bits){
		Vector<Register> regs = new Vector<Register> ();
		for(int i=bits.nextSetBit(0); i >= 0; i=bits.nextSetBit(i+1)){
			regs.add(getReg("r"+i));
		}
		return regs;
	}

	public void assignColor(String color){
		//this.color = this.name;
		this.name = color;
		//this.color = color;
	}
 	public static HashMap<String, Register> copyMap(){
 		String k;
 		HashMap<String, Register> mappings = new HashMap<String, Register> ();
 		Iterator it = regMap.keySet().iterator();
 		while(it.hasNext()){
 			k = (String) it.next();
 			mappings.put(k, (Register) regMap.get(k));
 		}
 		return mappings;	
 	}		
 
 	public static void clearMap(){
 		regMap.clear();
 	}

	
}
