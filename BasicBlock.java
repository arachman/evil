import antlr.*;
import antlr.collections.*;
import antlr.debug.misc.ASTFrame;
import java.util.*;
import java.io.*;

public class BasicBlock{

	public String label;
	

	public BasicBlock predecessor1;
	public BasicBlock predecessor2;
	public BasicBlock successor1;
	public BasicBlock successor2;
	public Vector<ILOC> instructions;
	public boolean visited;

	/* Hack to make variable analysis easier */
	protected Vector<BasicBlock> predecessors;
	protected Vector<BasicBlock> successors;	

	/* Live Variable Analyssis */	
	protected BitSet gen;
	protected BitSet kill;
	protected BitSet liveout;
	protected boolean stable;

	/* Copy propagation analysis */
	protected BitSet copyGen;
	protected BitSet copyKill;
	protected BitSet CPin;

	/* Interference Graph */
	protected BitSet livenow;
	protected Vector<Register> liveoutRegs;
	protected Vector<Register> livenowRegs;

	public BasicBlock(){
		label = null;
		predecessor1 = null;
		predecessor2 = null;
		successor1 = null;
		successor2 = null;
		instructions = new Vector();
		visited = false;
		gen = new BitSet();
		kill = new BitSet();
		copyGen = new BitSet();
		copyKill = new BitSet();
		CPin = new BitSet();
		liveout = new BitSet();
		stable = false;
		livenow = new BitSet();
	}

	public void setLivenowRegs(Vector<Register> regs){
		livenowRegs = regs;
	}
			
	public void setLiveoutRegs(Vector<Register> regs){
		liveoutRegs = regs;
	}

	public Vector<Register> getLivenowRegs(){
		return livenowRegs;
	}

	public void markVisited(){
		visited = true;
	}
	public void unmark(){
		visited = false;
	}
	
	public  void addInst(ILOC inst){
		instructions.add(inst);
	}
	public Vector<ILOC> getInst(){
		return instructions;
	}	
	public BasicBlock(String _label, BasicBlock _predecessor1, BasicBlock _predecessor2,
				BasicBlock _successor1, BasicBlock _successor2){
		label = _label;
		predecessor1 = _predecessor1;
		predecessor2 = _predecessor2;
		successor1 = _successor2;
		successor2 = _successor2;
	}

	public void addSuccessor(BasicBlock next){
		if(successor1 == null)
		{
			successor1 = next;
		}
		else if(successor2 == null) 
		{
			successor2 = next;
		}
		else
		{
			System.out.println("Error: can't have more than 2 successors::"+label);
		}
	}
	public void addLeftSuccessor(BasicBlock next){
		if(successor1 == null){
			successor1 = next;
		}
		else
		{
			System.out.println("Left successor is not null");
		}
	}
	public void addRightSuccessor(BasicBlock next){
		if(successor2 == null){
			successor2 = next;
		}
		else
		{
			System.out.println("Right successor is not null");
		}
	}

	public void addPred1(BasicBlock prev){
		predecessor1 = prev;
	}
	public void addPredecessor(BasicBlock prev){
		if(predecessor1 == null)
		{
			predecessor1 = prev;
		}
		else if(predecessor2 == null) 
		{
			predecessor2 = prev;
		}
		else
		{
			System.out.println("Error: can't have more than 2 predecessor"+label);
		}
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	
	public BasicBlock getLeftSuccessor(){
		return successor1;
	}
	public BasicBlock getRightSuccessor(){
		return successor2;
	}

	public Vector<BasicBlock> getSuccessors(){
		successors = new Vector<BasicBlock> ();
		if(successor1 != null){
			successors.add(successor1);
		}
		if(successor2 != null){
			successors.add(successor2);
		}
		return successors;
	}

	public Vector<BasicBlock> getPredecessors(){
		predecessors = new Vector<BasicBlock> ();	
		if(predecessor1 != null){
			predecessors.add(predecessor1);
		}
		if(predecessor2 != null){
			predecessors.add(predecessor2);
		}
		return predecessors;
	}

	public BitSet getCopyGen(){
		return copyGen;
	}
	public BitSet getCopyKill(){
		return copyKill;
	}

	public BitSet getGen(){
		return gen;
	}
	
	public BitSet getKill(){
		return kill;
	}

	public BitSet getCPin(){
		return CPin;
	}
	
	public BitSet getLiveout(){
		return liveout;
	}
	public void setLiveout(BitSet liveout){
		this.liveout = liveout;
	}
	
	public boolean isStable(){
		return stable;
	}
	public void setStable(){
		stable = true;
	}

}
