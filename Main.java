import antlr.*;
import antlr.collections.*;
import antlr.debug.misc.ASTFrame;
import java.util.*;
import java.io.*;

public class Main
{

	public static void main(String[] args)
   	{
		boolean displayAST = false;
	    	boolean dumpIL = false;
		boolean typeCheck = true;
		boolean regAlloc = false;
		boolean tailRecOpt = false;
		boolean copyPropOpt = false;
		boolean noGen = false;
		String fileName = null;
		String outFile = null;
		String outILFile = null;
		Vector<CFG> cfgV = null;
		CFG cfg = null;
		BasicBlock bb;
		ILOC ilocInst = null;
		String arg;
		int i = 0;
		FileReader inp = null;
		PrintWriter outp = null;
		PrintWriter outIL = null;
		LiveVar liveVar = null;
		TailRec tailRec = null;
		CopyProp copyProp = null;
		InterferenceGraph iGraph = null;
        	     		
	       	while( i < args.length){ 
			arg = args[i];
			if(args[i].startsWith("-")){
				if(arg.equals("-displayAST")){
					displayAST = true;
				}
				else if(arg.equals("-dumpIL")){
					dumpIL = true;
				}
				else if(arg.equals("-noTypeCheck")){
					typeCheck = false;
				}
				else if(arg.equals("-regAlloc")){
					regAlloc = true;
				}
				else if(arg.equals("-tailRec")){
					tailRecOpt = true;
				}
				else if(arg.equals("-copyProp")){
					copyPropOpt = true;
				}
				else if(arg.equals("-noGen")){
					noGen = true;
				}
			}
			else 
			{
				/*
				StringTokenizer st = new StringTokenizer(arg, ".ev");	
				outFile = st.nextToken();
				outILFile = outFile;
				*/
				outFile = arg;
				outILFile = arg;
				outFile = outFile.replaceAll("ev$", "s");
				outILFile = outILFile.replaceAll("ev$", "il");
				//outFile += new String(".s");
				//outILFile += new String(".il");
				fileName = arg; //st.nextToken();
				try
				{
					inp = new FileReader(fileName);
					outp = new PrintWriter(outFile);
					outIL = new PrintWriter(outILFile);
				}
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
					


			}
			i++;

			
		}
        	MyParser parser = new MyParser(new MyLexer(inp));
		MyTreeParser treeParser = new MyTreeParser();
		CFGTreeParser cfgTreeParser = new CFGTreeParser();
				
      		try
      		{
         		parser.prog();
         		AST t = parser.getAST();
         	
			if ((t != null) && displayAST)
         		{
            			ASTFrame frame = new ASTFrame("AST", t);
            			frame.setVisible(true);
         		}
			if (typeCheck) {
	 			treeParser.start(t);
			}
			//cfgV = cfgTreeParser.start(t);
			if(!noGen){	
				cfgV = cfgTreeParser.start(t);
				
				if(tailRecOpt){
					tailRec = new TailRec(cfgV);
					tailRec.analyze();
					unmark(cfgV);
				}

				if(copyPropOpt){
					for(CFG c : cfgV){
						copyProp = new CopyProp(c);
						copyProp.analyze();
						copyProp.transform();
					}
					unmark(cfgV);
				}
			
				if (dumpIL){

					Iterator iter = cfgV.iterator();
					while(iter.hasNext()){	
						cfg = (CFG)iter.next();
						dumpILOC((BasicBlock)cfg.getEntry(), outIL);		
					}
					unmark(cfgV);
				}

				if (regAlloc){
					for(CFG c : cfgV){
						liveVar = new LiveVar(c);
						liveVar.analyze();
						iGraph = new InterferenceGraph(c);
						iGraph.build();
						iGraph.colorGraph();
					}					
					unmark(cfgV);
				}
				
				generateSparc(cfgV, outp);
			}
      		}
		catch(UnknownFormatConversionException e){
			e.printStackTrace();
		}
		catch(ClassCastException e){
			e.printStackTrace();
		}
		catch(MissingFormatArgumentException e){
			e.printStackTrace();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		catch (NullPointerException e){
			e.printStackTrace();
		}
		catch (NoSuchElementException e){
			e.printStackTrace();
		}
      		catch (antlr.TokenStreamException e)
      		{
         		error(e.toString());
			e.printStackTrace();
      		}
      		catch (antlr.RecognitionException e)
      		{

         		error(e.toString());
			e.printStackTrace();
      		}
      		catch (Exception e){
         		error(e.toString());
			e.printStackTrace();
      		}
				

   	}

   	private static void error(String msg)
   	{
      		System.err.println(msg);
      		System.exit(1);
   	}
	private static void unmark(Vector cfgV){
		Iterator iter = cfgV.iterator();
		CFG cfg = null;
		java.util.Stack<BasicBlock> branchNode = new java.util.Stack();
		BasicBlock currentBlock = null;
		
		while(iter.hasNext()){
			cfg = (CFG)iter.next();
			currentBlock = cfg.getEntry();

			while(true){
				if(currentBlock.getRightSuccessor() != null){
					branchNode.push(currentBlock.getRightSuccessor());
				}
			
				currentBlock.unmark();

				if(currentBlock.getLeftSuccessor() != null && (currentBlock.getLeftSuccessor().visited)){
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
		}
	}
				
				
	private static void generateSparc(Vector cfgV, PrintWriter out){
		Iterator iter = cfgV.iterator();
		Iterator it = null;
		CFG cfg = null;
		ILOC ilocInst = null;
		java.util.Stack<BasicBlock> branchNode = new java.util.Stack();
		BasicBlock currentBlock = null;	
		String procName = null;
		BasicBlock lastToPrint = null;
			
		for(String s : ((((CFG)cfgV.get(0)).getAR()).getGlobalMap().values())){
				out.printf("\t.common %s,4,4\n", s);
		}
				
		out.printf("\t.section\t\".text\"\n");
		while(iter.hasNext()){
			cfg = (CFG)iter.next();
			/*
			for(BasicBlock bl : cfg.getBlocks()){
				System.out.println("Label: " +bl.getLabel());
			}
			*/

			procName = (cfg.getEntry()).getLeftSuccessor().getLabel();
			out.printf("\t.align 4\n\t.global %s\n", procName);
			out.printf("\t.type\t%s, #function\n", procName);
	/////////////////////////////////////////////////////	
			currentBlock = cfg.getEntry();
			currentBlock = currentBlock.getLeftSuccessor();
				
			while(true){
			
				if(currentBlock.getRightSuccessor() != null){
					branchNode.push(currentBlock.getRightSuccessor());
				}
				if(currentBlock.getLeftSuccessor() != null && currentBlock.getLeftSuccessor().equals(cfg.getExit())){
					//System.out.println("LAST BLOCK::"+currentBlock.getLabel());
					lastToPrint = currentBlock;
					currentBlock.markVisited();
				}
				else
				{	
					if(!(currentBlock.visited)){	
					currentBlock.markVisited();
					it = currentBlock.instructions.iterator();
					if(!(currentBlock.getLabel().startsWith("Li")) && currentBlock.getLabel().startsWith("L") && !(currentBlock.getLabel().equals(cfg.getExit().getLabel()))){
						out.printf(".%s\n", currentBlock.getLabel() + ":");
					}
					else if(!(currentBlock.getLabel().equals(cfg.getExit().getLabel())))
					{
						out.printf("%s\n", currentBlock.getLabel() + ":");
						out.printf("\t!#PROLOGUE# 0\n");
						out.printf("\tsave\t%%sp, -%s, %%sp\n", cfg.getAR().getStackSize());
						out.printf("\t!#PROLOGUE 1\n");
						out.printf(".%s\n", cfg.getEntry().getLabel()+ ":");
					}
					while (it.hasNext()){
						ilocInst = (ILOC)it.next();
						out.printf("%s\n",ilocInst.emit());
					}
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
			it = lastToPrint.instructions.iterator();
			if(lastToPrint.getLabel().startsWith("L") && !(lastToPrint.getLabel().startsWith("Li"))){
				out.printf(".%s\n", lastToPrint.getLabel() + ":");
			}
			else 
			{
				out.printf("%s\n", lastToPrint.getLabel() + ":");
				out.printf("\t!#PROLOGUE# 0\n");
				out.printf("\tsave\t%%sp, -%s, %%sp\n", cfg.getAR().getStackSize());
				out.printf("\t!#PROLOGUE 1\n");
				out.printf(".%s\n", cfg.getEntry().getLabel()+ ":");
			}
			while (it.hasNext()){
				ilocInst = (ILOC)it.next();
				out.printf("%s\n",ilocInst.emit());
			}
			out.printf(".%s:\n", cfg.getExit().getLabel());
			out.printf("\tret\n\trestore\n\t.size\t%s, .-%s\n", procName, procName);
////////////////////////////////////////////
		}
		out.printf("\n\n\t.section\t\".rodata\"\n");
		out.printf("\t.align 8\n");
		out.printf("\t.LLC0:\n");
		out.printf("\t.asciz \"%%d \"\n");
		out.printf("\t.align 8\n");
		out.printf("\t.LLC1:\n");
		out.printf("\t.asciz \"%%d\\n\"\n");
		out.printf("\t.align 8\n");
		out.printf("\t.LLC2:\n");
		out.printf("\t.asciz \"%%d\"\n");
		out.flush();
	}
	
	private static void dumpILOC(BasicBlock currentBlock, PrintWriter out){
		
		ILOC ilocInst;	
		Iterator it;
		java.util.Stack<BasicBlock> branchNode = new java.util.Stack();	
		String inst = null;
		String tailRec = null;
		
		tailRec = currentBlock.getLabel();
		currentBlock = currentBlock.getLeftSuccessor();
		while(true){
			
			if(currentBlock.getRightSuccessor() != null){
				branchNode.push(currentBlock.getRightSuccessor());
			}
			if(!(currentBlock.visited)){	
				it = currentBlock.instructions.iterator();
			//System.out.println(currentBlock.getLabel() + ":");
				out.printf("%s\n", currentBlock.getLabel() + ":");
				if(!(currentBlock.getLabel().startsWith("L")))
				{
					out.printf("%s\n", tailRec + ":");
				}
				while (it.hasNext()){
					ilocInst = (ILOC)it.next();
				//System.out.println(ilocInst.getIlocInst());
					out.printf("%s\n", ilocInst.getIlocInst());
					out.flush();
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
		out.flush();
	}
/*
	private static Vector<BasicBlock> topologicalSort(CFG cfg){
		java.util.Stack<BasicBlock> branchNode = new java.util.Stack();
		java.util.Stack<BasicBlock> dfs = new java.util.Stack();
		BasicBlock currentBlock = null;	
		currentBlock = cfg.getEntry();
		currentBlock = currentBlock.getLeftSuccessor();
		Vector<BasicBlock> sortedBlocks = new Vector<BasicBlock>();	
		while(true){
			
			if(currentBlock.getRightSuccessor() != null){
				branchNode.push(currentBlock.getRightSuccessor());
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
	}
	*/
}
