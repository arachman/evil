import java.io.*;
import java.util.*;

public class LiveVar{

	protected CFG cfg;
	protected BasicBlock currBlock;
	protected Vector<BasicBlock> blocks;

	public LiveVar(CFG cfg){
		this.cfg = cfg;
		blocks = toVector(cfg);
		this.cfg.setBlocks(blocks);
	}

	protected void analyze(){
		Vector<ILOC> instructions;
		Vector<Object> sources;
		Vector<Object> targets;
		Vector<BasicBlock> successors;
		Vector<BasicBlock> predecessors;
		boolean stable = false;
		boolean andMap = true;
		boolean oneMorePass = false;
		BitSet prevLiveout;
		BitSet nLiveout;
		BitSet currGen;
		BitSet currKill;
		BitSet tempLiveout;
		BitSet setChanges = new BitSet(cfg.getBlocks().size());
		setChanges.set(0, cfg.getBlocks().size());

		int pass = 0;

		//while(!(stable) ||  (pass <=  18) || oneMorePass){	
		//while((pass <=  20)){	
		while(true){	
			
			andMap = true;
			for(BasicBlock b : blocks){
				prevLiveout = (BitSet)(b.getLiveout().clone());	
				nLiveout = new BitSet();
				currGen = b.getGen();
				currKill = b.getKill();

				instructions = b.getInst();
				successors = b.getSuccessors();
				predecessors = b.getPredecessors();	
				for(ILOC i : instructions){
					sources = i.getSources();
					targets = i.getTargets();
					for(Object o : sources){
						if(o instanceof Register){
							if(!(((Register) o).getName().endsWith("arp") || ((Register)o).getName().startsWith("cc"))){
								if(!(b.getKill().get(((Register)o).getNumber()))){
									b.getGen().set(((Register) o).getNumber());
									//
								}
							}		
						}
					}
					for(Object o : targets){
						if(o instanceof Register){
							if(!(((Register)o).getName().startsWith("cc") || ((Register)o).getName().endsWith("arp"))){
								b.getKill().set(((Register)o).getNumber());
								//
							}
						}
					}
				}
				for(BasicBlock bb : successors){
					BitSet mLiveout = (BitSet)(bb.getLiveout().clone());
					BitSet mGen = (BitSet)(bb.getGen().clone());
				       	BitSet mKill = (BitSet)(bb.getKill().clone());
					//mKill.flip(0, (mKill.length()) - 1);
					mLiveout.andNot(mKill);
					mGen.or(mLiveout);
					nLiveout.or(mGen);
				}
				b.setLiveout(nLiveout);
				//prevLiveout.andNot(nLiveout);
				if(pass > 1){
					/*
					if(prevLiveout.nextSetBit(0) == -1){
						//b.setStable();
						stable = true;
					}
					*/
					if(prevLiveout.equals(nLiveout)){
						setChanges.clear(cfg.getBlocks().indexOf(b));
					}
					else
					{
						setChanges.set(cfg.getBlocks().indexOf(b));
					}
				}
				
				//
				//
			}
			
			if(setChanges.nextSetBit(0) == -1){
				break;
			}
			/*
			if(oneMorePass){
				break;
			}
			*/
			/*
			if(stable){
				oneMorePass = true;
			}
			*/
			pass++;	
		}
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
