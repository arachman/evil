import java.io.*;
import java.util.*;

public class TailRec{

	protected Vector<CFG> cfgV;
	protected Vector<BasicBlock> blocks;

	public TailRec(Vector<CFG> cfgV){
		this.cfgV = cfgV;
	}

	protected void analyze(){
		Vector<ILOC> instructions;
		int index;
		int nargs;
		int count;
		ILOC toReplace;
		Vector<Object> t;
		Vector<Object> s;
		for(CFG c : cfgV){
			//blocks = c.getBlocks();
			blocks = toVector(c);
			for(BasicBlock b : blocks){
				instructions = b.getInst();
				Collections.reverse(instructions);
				
				for(ILOC i : instructions){
					if(i.getOpCode().equals("storeret")){
						index = instructions.indexOf(i);
						if(index + 1 >= instructions.size())
							continue;						
						if(!(((ILOC)(instructions.get(index + 1))).getOpCode().equals("loadret")))
						{
							continue;
						}
						/* else this may be  a tail recursion */
						if(index + 1 >= instructions.size())
							continue;						
						if(!((((Label) ((instructions.get(index + 2).getTargets()).get(0))).getLabelName()).equals(c.getFunName())))
						{
							continue;
						}
						System.out.println(instructions.get(index + 2).getIlocInst());
						nargs = Integer.parseInt(((Immediate) instructions.get(index + 2).getTargets().get(1)).getValue());
						/* this is most likely is a tail recursion */
						instructions.set(index + 2, new BranchingInstruction(BranchingInstruction.JumpOpCode.JUMPI,
								new Label(c.getEntry().getLabel())));
						System.out.println(instructions.get(index + 2).getIlocInst());
						//nargs = Integer.parseInt(((Immediate) instructions.get(index + 2).getTargets().get(1)).getValue());
						index = index + 3;
						for(count = 0; count < nargs; count++){
							toReplace = instructions.get(index + count);
							s = toReplace.getSources();
							t = toReplace.getTargets();
							instructions.set(index + count, new StoreInstruction(StoreInstruction.StoreInArgOpCode.STOREINARG,
									(Register)(s.get(0)), (Immediate)(t.get(0))));
						}				
					}			
				}
				Collections.reverse(instructions);
			}
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
