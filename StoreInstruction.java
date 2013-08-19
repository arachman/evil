import antlr.*;
import java.io.*;
import java.util.*;

public class StoreInstruction extends ILOC {
	
	public StoreInstruction(StoreAIOpCode opCode, Register source1, Register source2, Immediate source3){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source1);
		sources.add(source2);
		sources.add(source3);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}
	
	public StoreInstruction(StoreGlobalOpCode opCode, Register source, Immediate imm, Register source2){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		sources.add(imm);
		sources.add(source2);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}

	public StoreInstruction(StoreOutArgOpCode opCode, Register source, Immediate target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}
	public StoreInstruction(StoreOutArgOpCode opCode, Immediate source, Immediate target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}

	public StoreInstruction(StoreInArgOpCode opCode, Register source, Immediate target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}
	
	public enum StoreAIOpCode implements Ops{
		STOREAI("storeai", "st");
		StoreAIOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s, %s", ilocOp, 
					((Register)sources.get(0)).getName(),
					((Register)sources.get(1)).getName(),
					((Immediate)sources.get(2)).getValue());
		}	
		public String genAsm(Vector sources, Vector targets){
			//MUST FIX THIS!!!!! REVERT TO ORIGINAL CODE , ACCIDENT DELETION
			String off;	
			StringTokenizer st = new StringTokenizer(((Immediate)sources.get(2)).getValue(), ":");
			off = st.nextToken();
			off = off.equals("%g0") ? "0" : off;
			//if(((Register)sources.get(0)).getName().equals("rarp")){
			if(((Register)sources.get(1)).getName().equals("rarp")){
				return String.format("\t%s\t%s, [%%fp-%s]", asmOp, 
						((Register)sources.get(0)).getName(),
						off); 
			}
			else
			{
				return String.format("\t%s\t%s, [%s+%s]", asmOp, 
						((Register)sources.get(0)).getName(),
						((Register)sources.get(1)).getName(),
						off);
			}
		}	
		private String ilocOp;
		private String asmOp;
	}

	public enum StoreOutArgOpCode implements Ops{
		STOREOUTARG("storeoutargument", "mov");
		StoreOutArgOpCode(String iloc, String asm){
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){

			if(sources.get(0) instanceof Immediate){
				String val = ((Immediate)targets.get(0)).getValue();
				val = val.equals("%g0") ? "0" : val;
				return String.format("\t%s\t%s, %s", ilocOp, 
						((Immediate)sources.get(0)).getValue(),
						val);
			}
			else
			{
				String val = ((Immediate)targets.get(0)).getValue();
				val = val.equals("%g0") ? "0" : val;
				return String.format("\t%s\t%s, %s", ilocOp, 
						((Register)sources.get(0)).getName(),
						val);
			}
		}	
		public String genAsm(Vector sources, Vector targets){

			if(sources.get(0) instanceof Immediate)
			{
				String val = ((Immediate)targets.get(0)).getValue();
				val = val.equals("%g0") ? "0" : val;
				return String.format("\t%s\t%s, %%o%s", asmOp, 
						((Immediate)sources.get(0)).getValue(),
						val);
			}
			else
			{
				String val = ((Immediate)targets.get(0)).getValue();
				val = val.equals("%g0") ? "0" : val;
				return String.format("\t%s\t%s, %%o%s", asmOp, 
						((Register)sources.get(0)).getName(),
						val);
			}
		}	
		private String ilocOp;
		private String asmOp;
	}
	public enum StoreInArgOpCode implements Ops{
		STOREINARG("storeinargument", "mov");
		StoreInArgOpCode(String iloc, String asm){
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			String val = ((Immediate)targets.get(0)).getValue();
			val = val.equals("%g0") ? "0" : val;
			return String.format("\t%s\t%s, %s", ilocOp, 
					((Register)sources.get(0)).getName(),
					val);
		}	
		public String genAsm(Vector sources, Vector targets){
			String val = ((Immediate)targets.get(0)).getValue();
			val = val.equals("%g0") ? "0" : val;
			return String.format("\t%s\t%s, %%i%s", asmOp, 
					((Register)sources.get(0)).getName(),
					val);
		}	
		private String ilocOp;
		private String asmOp;
	}
	
	public enum StoreGlobalOpCode implements Ops{
		STOREGLOBL("storeglobal", "st");

		StoreGlobalOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s", ilocOp, 
					((Register)sources.get(0)).getName(),
					((Immediate)sources.get(1)).getValue());
		}
		public String genAsm(Vector sources, Vector targets){
	       		return String.format("\t%s\t%s, [%s]", asmOp,
				((Register)sources.get(0)).getName(),
				((Register)sources.get(2)).getName());		
		}
		private String ilocOp;
		private String asmOp;
	}
}
