import antlr.*;
import java.io.*;
import java.util.*;

public class MoveInstruction extends ILOC {
	
	public MoveInstruction(MovOpCode opCode, Register source, Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		targets.add(target);
		//ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	
	
	public enum MovOpCode implements Ops{
		MOV("mov", "mov");
			
		MovOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s", ilocOp, 
					((Register)sources.get(0)).getName(),
					((Register)targets.get(0)).getName());
		}	
		public String genAsm(Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s", asmOp, 
					((Register)sources.get(0)).getName(), 
					((Register)targets.get(0)).getName());
		}
		private String asmOp;	
		private String ilocOp;
	}
}
