import antlr.*;
import java.io.*;
import java.util.*;

public class IOInstruction extends ILOC {
	
	public IOInstruction(IOOpCode opCode, Register source){
		sources = new Vector<Object> ();
		targets = new Vector<Object> ();	
		this.opCode = opCode.getIlocOp();
		op = opCode;
		sources.add(source);
		ilocInst = opCode.genIlocInst(this.ilocInst, sources, targets);
		//asmInst = opCode.genAsm(sources, targets);
	}
	
	public enum IOOpCode implements Ops{
		PRINT("print", "printf"),
		PRINTLN("println", "printf"),
		READ("read", "scanf"); 
			
		IOOpCode(String iloc, String asm) {
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
			String ret;
			if(ilocOp.equals("print")){
				ret = String.format("\tsethi\t%%hi(.LLC0), %%g1\n");
				ret += String.format("\tor\t%%g1, %%lo(.LLC0), %%o0\n");
				ret += String.format("\tmov\t%s, %%o1\n", ((Register)sources.get(0)).getName());
				ret += String.format("\tcall\tprintf\n\tnop");	

				return ret;
			}	
			else if(ilocOp.equals("println"))
			{	
				ret = String.format("\tsethi\t%%hi(.LLC1), %%g1\n");
				ret += String.format("\tor\t%%g1, %%lo(.LLC1), %%o0\n");
				ret += String.format("\tmov\t%s, %%o1\n", ((Register)sources.get(0)).getName());
				ret += String.format("\tcall\tprintf\n\tnop");	

				return ret;
			}
			else 
			{	
				ret = String.format("\tsethi\t%%hi(.LLC2), %%o0\n");
				ret += String.format("\tor\t%%o0, %%lo(.LLC2), %%o0\n");
				ret += String.format("\tmov\t%s, %%o1\n", ((Register)sources.get(0)).getName());
				ret += String.format("\tcall\tscanf\n\tnop");	
				return ret;	
				//return String.format("\t%s\t%s", asmOp,
				//	((Register)sources.get(0)).getName());
			}	
		}	
		private String ilocOp;
		private String asmOp;
	}
}

