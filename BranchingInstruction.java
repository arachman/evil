import antlr.*;
import java.io.*;
import java.util.*;

public class BranchingInstruction extends ILOC {
	
	public BranchingInstruction(BranchOpCode opCode, Register source, Label target1, Label target2){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		targets.add(target1);
		targets.add(target2);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	public BranchingInstruction(InsLabelOpCode opCode,  Label target1){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		targets.add(target1);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	public BranchingInstruction(ComparOpCode opCode, Register source1, Register source2, Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source1);
		sources.add(source2);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	
	public BranchingInstruction(ComparOpCode opCode, Register source1, Immediate source2, Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source1);
		sources.add(source2);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	public BranchingInstruction(JumpOpCode opCode, Label target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	
	public enum BranchOpCode implements Ops{
		CBRGE("cbrge", "bge"),
		CBREQ("cbreq", "be"),
		CBRGT("cbrgt", "bg"),
		CBRLE("cbrle", "ble"),
		CBRLT("cbrlt", "bl"),
		CBRNE("cbrne", "bne");
			
		BranchOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s, %s", ilocOp, 
					((Register)sources.get(0)).getName(),
					((Label)targets.get(0)).getLabelName(),
					((Label)targets.get(1)).getLabelName());
		}	
		public String genAsm(Vector sources, Vector targets){
			return String.format("\t%s\t%s\n\tnop\n\tba\t%s\n\tnop", asmOp, "."+((Label)targets.get(0)).getLabelName(),
					"."+((Label)targets.get(1)).getLabelName());
		}	
		private String ilocOp;
		private String asmOp;
	}

	public enum ComparOpCode implements Ops{
		COMP("comp", "cmp"),
		COMPI("compi", "cmp");
		ComparOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}
		
		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			if(ilocOp.endsWith("i")){
				return String.format("\t%s\t%s, %s, %s", ilocOp, 
					((Register)sources.get(0)).getName(),
					((Immediate)sources.get(1)).getValue(),
					((Register)targets.get(0)).getName());
			}
			else
			{
				return String.format("\t%s\t%s, %s, %s", ilocOp, 
					((Register)sources.get(0)).getName(),
					((Register)sources.get(1)).getName(),
					((Register)targets.get(0)).getName());

			}
		}	
		public String genAsm(Vector sources, Vector targets){
			if(ilocOp.endsWith("i")){
				return String.format("\t%s\t%s, %s", asmOp, 
					((Register)sources.get(0)).getName(),
					((Immediate)sources.get(1)).getValue());
			}
			else
			{
				return String.format("\t%s\t%s, %s", asmOp, 
					((Register)sources.get(0)).getName(),
					((Register)sources.get(1)).getName());
			}
		}
		private String ilocOp;
		private String asmOp;
	}
	public enum InsLabelOpCode implements Ops{
		INSERTLABL("", ".");
		InsLabelOpCode(String iloc, String asm){
			ilocOp = iloc;
			asmOp = asm;
		}
		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("%s%s:", ilocOp, ((Label)targets.get(0)).getLabelName());
		}
		public String genAsm(Vector sources, Vector targets){
			return String.format("%s%s:", asmOp, ((Label)targets.get(0)).getLabelName());
		}	
		private String ilocOp;
		private String asmOp;
	}
	
	public enum JumpOpCode implements Ops{
		JUMPI("jumpi", "ba");
		JumpOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s", ilocOp, 
					"."+((Label)targets.get(0)).getLabelName());
		}	
		public String genAsm(Vector sources, Vector targets){
			return String.format("\t%s\t%s\n\tnop", asmOp, 
					"."+((Label)targets.get(0)).getLabelName());
		}	
		private String ilocOp;
		private String asmOp;
	}

}
