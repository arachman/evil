import antlr.*;
import antlr.collections.*;
import antlr.debug.misc.ASTFrame;
import java.util.*;
import java.io.*;

public abstract class ILOC{
	
	public Vector getSources(){
		return sources;
	}
	public Vector getTargets(){
		return targets;
	}
	public String getOpCode(){
		return opCode;
	}
	public String getIlocInst(){
		ilocInst = op.genIlocInst(this.ilocInst, sources, targets);
		return ilocInst;
	}
	public String emit(){
		asmInst = op.genAsm(sources, targets);
		return asmInst;
	}

	public interface Ops {
		public abstract String genAsm(Vector<Object> sources, 
				Vector<Object> targets);
		public abstract String genIlocInst(String ilocInst, Vector sources, Vector targets);
	}	
		
	protected String ilocInst;
	protected String asmInst;
	protected Vector<Object> sources;
	protected Vector<Object> targets;
	protected String opCode;
	protected Ops op;

}



