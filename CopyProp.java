import java.io.*;
import java.util.*;

public class CopyProp{

	protected CFG cfg;
	protected BasicBlock currBlock;
	protected Vector<BasicBlock> blocks;

	public CopyProp(CFG cfg){
		this.cfg = cfg;
		blocks = toVector(cfg);
		this.cfg.setBlocks(blocks);
	}

	protected void analyze(){
		Vector<ILOC> instructions;
		Vector<Object> sources;
		//Vector<Object> targets;
		Vector<BasicBlock> successors;
		Vector<BasicBlock> predecessors;
		boolean stable = false;
		boolean andMap = true;
		boolean oneMorePass = false;
		BitSet prevLiveout;
		BitSet nLiveout;
		BitSet nCPin;
		BitSet currGen;
		BitSet currKill;
		BitSet tempLiveout;
		int pass = 0;
		

		/* Generate Gen Set */		
		for(BasicBlock bb : cfg.getBlocks()){
			currGen = bb.getCopyGen();
			instructions = bb.getInst();
			for(ILOC inst : instructions){
				if(inst instanceof MoveInstruction){

					/* Check if this copy destroy other copies */
					Vector<Object> targets = inst.getTargets();
					for(Object o : targets){
						if(o instanceof Register){
							Vector<Copy> localCopies = Copy.getLocalCopies(bb);
							for(Copy copy : localCopies){
								if(copy.isChanged(((Register)o))){
									currGen.clear(copy.bitID);
									Copy.deleteCopy(copy.bitID);
								}			
							}	
						}
					}

					/* add copy to copies */
					Copy c = new Copy( ((Register)((inst.getTargets()).get(0))), 
							((Register)((inst.getSources()).get(0))), bb, instructions.indexOf(inst));
					currGen.set(c.bitID);
				}
				else
				{
					Vector<Object> targets = inst.getTargets();
					for(Object o : targets){
						if(o instanceof Register){
							Vector<Copy> localCopies = Copy.getLocalCopies(bb);
							for(Copy c : localCopies){
								if(c.isChanged(((Register)o))){
									currGen.clear(c.bitID);
									Copy.deleteCopy(c.bitID);		
								}			
							}	
						}
					}
				}	
			}	
		}

		/* Generate Kill Set */
		for(BasicBlock bb : cfg.getBlocks()){
			currKill = bb.getCopyKill();
			instructions = bb.getInst();
			Vector<Copy> copies = Copy.getCopies();
			for(ILOC inst : instructions){
				Vector<Object> targets = inst.getTargets();
				for(Object o : targets){
					if(o instanceof Register){
						for(Copy c : copies){
							if(c.isChanged(((Register)o)) && !(c.block.equals(bb))){
								currKill.set(c.bitID);
							}	
						}		
					}
				}
			}
		}
		
		BitSet prevCPin;
		int count = 0;	
			
		BitSet setChanges = new BitSet(cfg.getBlocks().size());
		setChanges.set(0, cfg.getBlocks().size());
		while(setChanges.nextSetBit(0) != -1 || count < 5){
		/* CPin analysis for each basic block */
		setChanges.set(0, cfg.getBlocks().size());
		for(BasicBlock bb : cfg.getBlocks()){
			nCPin = bb.getCPin();
			prevCPin = bb.getCPin();	

			predecessors = bb.getPredecessors();
			if(predecessors.size() == 1){

				BitSet tempCPin = new BitSet();
				BitSet mCPin = predecessors.get(0).getCPin();
				BitSet mCopyGen = predecessors.get(0).getCopyGen();
				BitSet mCopyKill = predecessors.get(0).getCopyKill();
				tempCPin.or(mCPin);
				tempCPin.andNot(mCopyKill);
				tempCPin.or(mCopyGen);
				nCPin.clear();
				nCPin.or(tempCPin);
				

				if(nCPin.equals(prevCPin)){
					setChanges.clear(cfg.getBlocks().indexOf(bb));
				}
				else
				{
					setChanges.set(cfg.getBlocks().indexOf(bb));
				}
			}
			else if(predecessors.size() == 2){
				BitSet tempCPin1 = new BitSet();
				BitSet mCPin1 = predecessors.get(0).getCPin();
				BitSet mCopyGen1 = predecessors.get(0).getCopyGen();
				BitSet mCopyKill1 = predecessors.get(0).getCopyKill();
				tempCPin1.or(mCPin1);
				tempCPin1.andNot(mCopyKill1);
				tempCPin1.or(mCopyGen1);
				


				BitSet tempCPin2 = new BitSet();
				BitSet mCPin2 = predecessors.get(1).getCPin();
				BitSet mCopyGen2 = predecessors.get(1).getCopyGen();
				BitSet mCopyKill2 = predecessors.get(1).getCopyKill();
				tempCPin2.or(mCPin2);
				tempCPin2.andNot(mCopyKill2);
				tempCPin2.or(mCopyGen2);
					
				
				nCPin.clear();	
				nCPin.or(tempCPin1);
				nCPin.and(tempCPin2);

				if(nCPin.equals(prevCPin)){
					setChanges.clear(cfg.getBlocks().indexOf(bb));
				}
				else
				{
					setChanges.set(cfg.getBlocks().indexOf(bb));
				}
			}
		}
			count++;
		}
		for(BasicBlock bb : cfg.getBlocks()){
			for(Copy c : Copy.getLocalCopies(bb)){
			}
		}
	}

	public void transform(){
		Vector<Object> sources;
		for(BasicBlock bb : cfg.getBlocks()){
			Vector<Copy> copies = Copy.getCopiesIn(bb.getCPin());
			for(ILOC i : bb.getInst()){
				sources = i.getSources();
				for(Object o : sources){
					if(o instanceof Register){
						for(Copy c : copies){
							/* If copy exist in CPin replace sources with copy */
							if(c.u.equals(((Register)o))){
								sources.set(sources.indexOf(o), c.v);
							}
						}
					}
				}
			}
		}
		/*
		for(BasicBlock bb : cfg.getBlocks()){
			Vector<Copy> copies = Copy.getCopiesIn(bb.getCPin());
			Vector<ILOC> instructions = bb.getInst();
			for(ILOC i : instructions){
				for(Copy c : copies){
					if(instructions.indexOf(i) == c.pos && bb.equals(c.block)){
						instructions.
				}
			}
				
		}
		*/
	}	

	protected Vector<BasicBlock> toVector(CFG cfg){
		BasicBlock currentBlock = cfg.getEntry().getLeftSuccessor();
		java.util.Stack<BasicBlock> branchNode = new java.util.Stack();
		Vector<BasicBlock> blocks = new Vector<BasicBlock> ();

		while(true){
			
			if(currentBlock.getRightSuccessor() != null){
				branchNode.push(currentBlock.getRightSuccessor());
			}
			
			if(!(currentBlock.equals(cfg.getExit()))){
				if(!(blocks.contains(currentBlock))){
					blocks.add(currentBlock);
				}
			}	
			currentBlock.markVisited();

			if(currentBlock.getLeftSuccessor() != null && !(currentBlock.getLeftSuccessor().visited)){
				currentBlock = currentBlock.getLeftSuccessor();
			}
			else
			{
				if(!(branchNode.empty())){
					currentBlock = (BasicBlock)branchNode.pop();
				}
				else
				{
					break;
				}
			}
		}
		return blocks; 
	}	
}	
