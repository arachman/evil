import antlr.*;
import java.io.*;
import java.util.*;
/*
public class AllocationInstruction extends ILOC {
	
	public AllocationInstruction(AllocOpCode opCode, Immediate imm, Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(imm);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);

	}
	
	public enum LoadAIOpCode implements Ops{
		NEW("new", "malloc");

		AllocOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s, %s", ilocOp, 
					((Register)sources.get(0)).getName(),
					((Immediate)sources.get(1)).getValue(), 
					((Register)targets.get(0)).getName());
		}
		public String genAsm(Vector sources, Vector targets){
			String off;	
			StringTokenizer st = new StringTokenizer(((Immediate)sources.get(1)).getValue(), ":");
			off = st.nextToken();
			if(((Register)sources.get(0)).getName().equals("rarp")){
				return String.format("\t%s\t[%%fp-%s], %s", asmOp, 
						off, 
						((Register)targets.get(0)).getName());
			}
			else
			{
				return String.format("\t%s\t[%s-%s], %s", asmOp,
					        ((Register)sources.get(0)).getName(),	
						off, 
						((Register)targets.get(0)).getName());
			}
		}

		private String ilocOp;
		private String asmOp;
	}
}
*/
