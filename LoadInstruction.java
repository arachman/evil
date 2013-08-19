import antlr.*;
import java.io.*;
import java.util.*;

public class LoadInstruction extends ILOC {
	
	public LoadInstruction(LoadIOpCode opCode, Immediate imm, Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(imm);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	
	public LoadInstruction(LoadGlobalOpCode opCode, Immediate imm, Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(imm);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}

	public LoadInstruction(LoadInArgOpCode opCode, Immediate imm, Immediate imm2, Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(imm);
		sources.add(imm2);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
	}
	
	public LoadInstruction(LoadAIOpCode opCode, Register source, Immediate imm, Register target, int offset){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		sources.add(imm);
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets, offset);
		opCode.setOffset(offset);
	}

	public LoadInstruction(LoadRetOpCode opCode, Register target){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		targets.add(target);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	
	public enum LoadIOpCode implements Ops {
		LOADI("loadi", "mov");
			
		LoadIOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s", ilocOp, 
					((Immediate)sources.get(0)).getValue(), 
					((Register)targets.get(0)).getName());
		}	
		public String genAsm(Vector sources, Vector targets){
			int val; 
			if(((Immediate)sources.get(0)).getValue().equals("%g0")){
				val = 0;
			}
			else
			{ 
				val = Integer.parseInt(((Immediate)sources.get(0)).getValue()); 
			}
			if(-4096 < val && val < 4096){ 
				return String.format("\t%s\t%s, %s", asmOp, 
						((Immediate)sources.get(0)).getValue(), 
						((Register)targets.get(0)).getName());
			}
			else
			{
				return String.format("\tsethi \t%%hi(%s), %s\n\tor\t%s, %%lo(%s), %s",
						((Immediate)sources.get(0)).getValue(),
						((Register)targets.get(0)).getName(),
						((Register)targets.get(0)).getName(),
						((Immediate)sources.get(0)).getValue(),
						((Register)targets.get(0)).getName());
			}
		}	
		private String ilocOp;
		private String asmOp;
	}

	public enum LoadAIOpCode implements Ops{
		LOADAI("loadai", "ldsw");

		LoadAIOpCode(String iloc, String asm) {
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
				return String.format("\t%s\t[%s+%s], %s", asmOp,
					        ((Register)sources.get(0)).getName(),	
						off, 
						((Register)targets.get(0)).getName());
			}
		}

		public void setOffset(int offs){
			offset = offs;
		}
		private int offset;	
		private String ilocOp;
		private String asmOp;
	}
	public enum LoadInArgOpCode implements Ops{
		LOADINARG("loadinargument", "mov");

		LoadInArgOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			String off;
			off = ((Immediate)sources.get(1)).getValue();
			if(off.equals("%g0")){
				off = "0";
			}
			
			return String.format("\t%s\t%s, %s, %s", ilocOp, 
					((Immediate)sources.get(0)).getValue(),
					off, 
					((Register)targets.get(0)).getName());
		}
		public String genAsm(Vector sources, Vector targets){
			String off;
			off = ((Immediate)sources.get(1)).getValue();
			if(off.equals("%g0")){
				off = "0";
			}
			//System.out.println("LOADINARGTARGET: " + ((Register)targets.get(0)).getName() + "::" + ((Register)targets.get(0)).getColor());
			//System.out.println("TARGET ADDRESS: " + ((Register)targets.get(0)));
			return String.format("\t%s\t%%i%s, %s", asmOp,
				off,
			 	((Register)targets.get(0)).getName());	 
		}

		private String ilocOp;
		private String asmOp;
	}
	public enum LoadRetOpCode implements Ops{
		LOADRET("loadret", "mov");

		LoadRetOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s", ilocOp, 
					((Register)targets.get(0)).getName());
		}
		public String genAsm(Vector sources, Vector targets){
			return String.format("\t%s\t%%o0, %s", asmOp,
			 	((Register)targets.get(0)).getName());	 
		}
		private String ilocOp;
		private String asmOp;
	}
	public enum LoadGlobalOpCode implements Ops{
		LOADGLOBL("loadglobal", "st"),
		COMPUTEGLOBL("computeglobaladdress", "sethi");
		LoadGlobalOpCode(String iloc, String asm) {
			ilocOp =  iloc;
			asmOp = asm;
		}

		public String getIlocOp() {
			return ilocOp;
		}

		public String genIlocInst(String ilocInst, Vector sources, Vector targets){
			return String.format("\t%s\t%s, %s", ilocOp, 
					((Immediate)sources.get(0)).getValue(),
					((Register)targets.get(0)).getName());
		}
		public String genAsm(Vector sources, Vector targets){
			if(ilocOp.startsWith("compute")){
	       			return String.format("\tsethi\t%%hi(%s), %%g1\n\tor\t%%g1, %%lo(%s), %s",
					((Immediate)sources.get(0)).getValue(), ((Immediate)sources.get(0)).getValue(),
					((Register)targets.get(0)).getName());		
			}
			else
			{
	       			return String.format("\tsethi\t%%hi(%s), %%g1\n\tor\t%%g1, %%lo(%s), %%g2\n\tld\t[%%g2], %s",
					((Immediate)sources.get(0)).getValue(), ((Immediate)sources.get(0)).getValue(),
					((Register)targets.get(0)).getName());		
			}
		}
		private String ilocOp;
		private String asmOp;
	}
}


		
