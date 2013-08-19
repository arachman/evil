import antlr.*;
import java.io.*;
import java.util.*;

public class BinopInstruction extends ILOC {
	
	public BinopInstruction(ArithmeticOpCode opCode, Register source1, Register source2,  Register target){
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
	
	public BinopInstruction(ArithmeticOpCode opCode, Register source1, Immediate source2,  Register target){
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
	public BinopInstruction(BooleanOpCode opCode, Register source1, Register source2,  Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source1);
		sources.add(source2);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}
	
	public BinopInstruction(BooleanOpCode opCode, Register source1, Immediate source2,  Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source1);
		sources.add(source2);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}
	public enum ArithmeticOpCode implements Ops{
		ADD("add", "add"),
		ADDI("addi", "add"), 
		DIV("div", "sdivx"),
		MULT("mult", "mulx"),
		SUB("sub", "sub"),
		RSUBI("rsubi", "sub"); //??
			
		ArithmeticOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			if(ilocOp.startsWith("r")){
				String imm;	
				StringTokenizer st = new StringTokenizer(((Immediate)sources.get(1)).getValue(), ":");
				imm = st.nextToken();
				imm = imm.equals("%g0") ? "0" : imm;
				
				return String.format("\t%s\t%s, %s, %s", ilocOp,
					((Register)sources.get(0)).getName(),
					((Immediate)sources.get(1)).getValue(),
					((Register)targets.get(0)).getName());
			}
			else if(ilocOp.endsWith("i")){
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
			if(ilocOp.startsWith("r")){

				return String.format("\t%s\t%s, %s, %s", asmOp,
					((Immediate)sources.get(1)).getValue(),
					((Register)sources.get(0)).getName(),
					((Register)targets.get(0)).getName());

			}
			else if(ilocOp.endsWith("i"))
			{
				String imm;	
				StringTokenizer st = new StringTokenizer(((Immediate)sources.get(1)).getValue(), ":");
				imm = st.nextToken();
				return String.format("\t%s\t%%fp, -%s, %s", asmOp,
					imm,
					((Register)targets.get(0)).getName());

			}
			else
			{	
				return String.format("\t%s\t%s, %s, %s", asmOp,
					((Register)sources.get(0)).getName(),
					((Register)sources.get(1)).getName(),
					((Register)targets.get(0)).getName());
			}	
		}	
		private String ilocOp;
		private String asmOp;
	}

	public enum BooleanOpCode implements Ops{
		AND("and", "and"),
		OR("or", "or"),
		XORI("xori", "xor");

		BooleanOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}
		
		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			if(ilocOp.endsWith("i")){
				return  String.format("\t%s\t%s, %s, %s", ilocOp,
						((Register)sources.get(0)).getName(),
						((Immediate)sources.get(1)).getValue(),
						((Register)targets.get(0)).getName());
			}
			else
			{
				return String.format("\t%s\t%s, %s, %s", asmOp, 
						((Register)sources.get(0)).getName(), 
						((Register)sources.get(1)).getName(), 
						((Register)targets.get(0)).getName());
			}
		}

		public String genAsm(Vector sources, Vector targets){
			if(ilocOp.endsWith("i")){
				return String.format("\t%s\t%s, %s, %s", asmOp,
						((Register)sources.get(0)).getName(),
						((Immediate)sources.get(1)).getValue(),
						((Register)targets.get(0)).getName());
			}
			else
			{
				//return "null";
				return String.format("\t%s\t%s, %s, %s", ilocOp, 
						((Register)sources.get(0)).getName(), 
						((Register)sources.get(1)).getName(), 
						((Register)targets.get(0)).getName());

			}
		}	
		private String ilocOp;
		private String asmOp;
	}

}

