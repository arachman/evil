import antlr.*;
import java.io.*;
import java.util.*;

public class InvokeInstruction extends ILOC {
	
	public InvokeInstruction(RetOpCode opCode, Register source){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}
	
	public InvokeInstruction(CallOpCode opCode, Label target, Immediate imm){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		targets.add(target);
		targets.add(imm);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}

	public enum RetOpCode implements Ops{
		RET("storeret", "mov");
		RetOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s", ilocOp, 
					((Register)sources.get(0)).getName());
		}	
		public String genAsm(Vector sources, Vector targets){
			return String.format("\t%s\t%s, %%i0", asmOp, 
					((Register)sources.get(0)).getName());
		}	
		private String ilocOp;
		private String asmOp;
	}
	
	public enum CallOpCode implements Ops{
		CALL("call", "call");
		CallOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s", ilocOp, 
					((Label)targets.get(0)).getLabelName(),
					((Immediate)targets.get(1)).getValue());
		}	
		public String genAsm(Vector sources, Vector targets){
			return String.format("\t%s\t%s\n\tnop", asmOp, 
					((Label)targets.get(0)).getLabelName());
		}	
		private String ilocOp;
		private String asmOp;
	}
}
