import java.io.*;
import java.util.*;


public class CFG{

	public String fun;
	public BasicBlock currentBlock;
	public BasicBlock entryBlock;
	public BasicBlock exitBlock;
	protected ActivationRecord ar;
	protected Vector<BasicBlock> blocks;
	protected int stackSize;
 	protected HashMap<String, Register> regMap;

	public CFG(){
		currentBlock = null;
		entryBlock = null;
		exitBlock = null;
		ar = null;
		blocks = null;
		stackSize = 0;

	}
	public void setFunName(String name){
		this.fun = name;
	}
	public String getFunName(){
		return fun;
	}
	public void setSize(int size){
		stackSize = size;
	}
	public int getStackSize(){
		return stackSize;
	}

	public void setBlocks(Vector<BasicBlock> blocks){
		this.blocks = blocks;
	}

	public Vector<BasicBlock> getBlocks(){
		return blocks;
	}
	
	public void setAR(ActivationRecord arp){
		ar = arp;
	}

	public ActivationRecord getAR(){
		return ar;
	}
	
	public BasicBlock getEntry(){
		return entryBlock;
	}

	public BasicBlock getExit(){
		return exitBlock;
	}

	public BasicBlock getCurrent(){
		return currentBlock;
	}

	public void setEntry(BasicBlock entry){
		this.entryBlock = entry;
	}

	public void setExit(BasicBlock exit){
		this.exitBlock = exit;
	}

 	public void setRegMap(HashMap<String, Register> mappings){
 		regMap = mappings;
 	}

 	public Vector<Register> toRegisters(BitSet bits){
 		Vector<Register> regs = new Vector<Register> ();
 		for(int i=bits.nextSetBit(0); i >= 0; i=bits.nextSetBit(i+1)){
 			regs.add(regMap.get("r"+i));
 			//System.out.println(regMap.get("r"+i));
 		}
 		return regs;
 	}
}
