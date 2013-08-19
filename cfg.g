header {
	import java.util.*;
	import antlr.*;
}
class CFGTreeParser extends TreeParser;
options
{
	importVocab=MyLexer;
	defaultErrorHandler=false;
}
{
	Stack<BasicBlock> endIf = new Stack();
	Stack<BasicBlock> startIf = new Stack();
	Stack<BasicBlock> endLoop = new Stack();
	Stack<BasicBlock> startLoop = new Stack();
	BasicBlock entry;
	BasicBlock nextBlock;
	BasicBlock currentBlock;
	BasicBlock exit;
	BasicBlock mergeConditional;
	BasicBlock thenBlock;
	BasicBlock elseBlock;
	BasicBlock startConditional;
	String thenLabel = null;
	String elseLabel = null;
	String mergeLabel = null;
	String structType = null;
	boolean ret = true;
	boolean loadStructAddr = false;
	boolean conditionalMove = false;
	boolean compi = false;
	boolean read = false;
	boolean dotOp = false;
	boolean isGlobal = false;
	boolean arg = false;
	Register alternateReg = null;
	Immediate storeStruct = null;
	int label = 0;
	int reg = 0;	
	int nextRegister() {
		return (++reg);
	}
	int nextLabel() {
		return (++label);
	}
	int paramCount = 0;
	int returnReg = 0;
	Stack<Integer> paramCountStack = null;
	Vector<Register> realArgs = null;
	Stack<Vector<Register>> realArgsStack = new Stack<Vector<Register>> ();
	ActivationRecord arp = null;
	ActivationRecord globalARP = null;
	
}

start returns [Vector cfgV = new Vector()]
{Vector fun = null;}
	: #(PROG t:. d:. f:.)//fun=f:functions)
		{ 
			paramCountStack = new Stack<Integer>();
			globalARP = new ActivationRecord();
			types(t);
			isGlobal = true;
			declarations(d);
			isGlobal = false;
			fun = functions(f);
			cfgV = fun; 
		}
	;
functions returns [Vector cfgV = new Vector()] 
	: #(FUNS (f:.
		{ 
			CFG cfg = new CFG();
			arp = new ActivationRecord();
			arp.setGlobalAR(globalARP);
			//arp = (ActivationRecord)(globalARP.clone());
			cfg.setAR(arp);
			cfg.setFunName(f.getText());
			entry = new BasicBlock();
			entry.setLabel("L"+nextLabel());
			exit = new BasicBlock();
			exit.setLabel("L"+nextLabel());
			cfg.setEntry(entry);
			currentBlock = entry;
			function(f);
			cfg.setRegMap(Register.copyMap());
			currentBlock.addLeftSuccessor(exit);
			cfg.setExit(exit);
			cfg.setSize(arp.getStackSize());
			cfgV.add(cfg);	
		})*)
	;
function //returns [BasicBlock succ = new BasicBlock()]
	: #(i:ID p:. r:. b:.)
		{
			arp.clearLocal();
			//Register.clear();
			Register.clearMap();
			nextBlock = new BasicBlock();
			nextBlock.setLabel(i.getText());
			nextBlock.addPredecessor(currentBlock);
			currentBlock.addLeftSuccessor(nextBlock);
			currentBlock = nextBlock;
			paramCount = 0;
			parameters(p);
			paramCount = 0;
			body(b);
		}
	;
types
	: #(TYPES (t:.
		{ 
			globalARP.defineStruct(t.getFirstChild().getText());
			type_decl(t);
		})*)
	;
type_decl
	: #(STRUCT i:ID #(NESTED_DECL 
			(#(TY_DECL t:. ty_id:ID)
			 {
			 	globalARP.defineField(i.getText(), ty_id.getText());
				if(t.getText().equals("struct")){
					globalARP.nestedStructDecl(ty_id.getText(), t.getFirstChild().getText(), i.getText());
				}
					
			 })*))
	;

declarations
	: #(DECLS (declaration)*)
	;
declaration
	: #(DECL t:. #(IDS (i:ID 
		{
	       		if(!isGlobal){	
				arp.addLocal(i.getText());
		       		if(t.getText().equals("struct")){
					arp.addLocalStruct(i.getText(), 
						t.getFirstChild().getText());
				}
			}
			else
			{
				globalARP.addGlobal(i.getText());
				if(t.getText().equals("struct")){
					globalARP.addGlobalStruct(i.getText(),
						t.getFirstChild().getText());
				}
			}	
		})*))
	;
parameters
	: #(PARAMS (decl)*)
	;
decl
	: #(TY_DECL t:. i:ID) 
		{
			//currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadOpCode.LOADINARG,
			//			, i.getText(), new Immediate(Integer.toString(paramCount)), Register.make(i.getText())));
			//paramCount++;
			Register result = Register.make(i.getText());
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadInArgOpCode.LOADINARG,
						new Immediate(i.getText()), new Immediate(Integer.toString(paramCount)),
						result));
			if(t.getText().equals("struct")){
				arp.addParamStruct(i.getText(), 
						t.getFirstChild().getText());
			}
			arp.addParam(i.getText(), Integer.toString(paramCount));
			paramCount++;
		}
	;
body 
	: #(BODY d:. s:.)
		{
			declarations(d);
			statement_list(s);
		}
	;
statement_list
	: #(STMT_LST (s:statement)*)
	;
statement 
{Register l, r; l = r = null; int i = 0;}
	: #(EQUAL l=ll:lvalue r=rr:expr)
		{
			if(ll.getText().equals(".")){
				currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreAIOpCode.STOREAI,
							r, l, storeStruct));
			}
			else if(rr.getText().equals("new") && !(ll.getText().equals("."))){
				if(arp.getVarOffset(ll.getText()) == -1){
					currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.COMPUTEGLOBL,
							new Immediate(ll.getText()), l));
					currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreGlobalOpCode.STOREGLOBL,
							r, new Immediate(ll.getText()), l));
					
				}
				else
				{
					currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreAIOpCode.STOREAI,
							r, Register.getReg("rarp") , new Immediate(arp.getVarOffset(ll.getText()) + ":"+ ll.getText())));
				}
			}
			else
			{
				if(arp.getStructType(ll.getText()) == null && arp.getGlobalDir(ll.getText()) == null){
					currentBlock.addInst(new MoveInstruction(MoveInstruction.MovOpCode.MOV,
						r, l));
				}
				else
				{
					if(arp.getVarOffset(ll.getText()) == -1){
						/*
						currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreAIOpCode.STOREAI,
							r, l, new Immediate("0")));
						*/
						if(arp.getParamOff(ll.getText()) != null){
							currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreInArgOpCode.STOREINARG,
								r, new Immediate(arp.getParamOff(ll.getText()))));
							currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadInArgOpCode.LOADINARG,
								new Immediate(ll.getText()), new Immediate(arp.getParamOff(ll.getText())),
								Register.getReg(ll.getText())));
						}
						else if (arp.getGlobalDir(ll.getText()) != null){
							currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.COMPUTEGLOBL,
								new Immediate(ll.getText()), l));
							currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreGlobalOpCode.STOREGLOBL,
								r, new Immediate(ll.getText()), l));
						}

					}
					else
					{
						currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreAIOpCode.STOREAI,
							r, Register.getReg("rarp") , new Immediate(arp.getVarOffset(ll.getText()) + ":"+ ll.getText())));
					}
				}
			}
		}
	| #(p:PRINT r=expr (ENDL)?)
		{
			if(p.getNumberOfChildren() > 1){

				currentBlock.addInst(new IOInstruction(IOInstruction.IOOpCode.PRINTLN,
							r));
			}
			else 
			{
				currentBlock.addInst(new IOInstruction(IOInstruction.IOOpCode.PRINT,
						r));
			}
		}
	| #(READ lval:.)
		{
			read = true;
			l = lvalue(lval);
			read = false;
			currentBlock.addInst(new IOInstruction(IOInstruction.IOOpCode.READ, l));
			
		}
	| #(IF #(GUARD e:.) #(THEN b1:.) #(el:ELS (b2:.)?))
		{ 
			conditionalMove = true;
			/* Conditional Block */
			nextBlock = new BasicBlock();
			nextBlock.setLabel("L"+nextLabel());
			currentBlock.addLeftSuccessor(nextBlock);
			nextBlock.addPredecessor(currentBlock);
			currentBlock = nextBlock;
			
			//expr(e);
			
			/* Then Block */
			nextBlock = new BasicBlock();
			nextBlock.setLabel("L"+nextLabel());
			thenLabel = nextBlock.getLabel();
			currentBlock.addLeftSuccessor(nextBlock);
			nextBlock.addPredecessor(currentBlock);
			
			/* Else Block */
			if(el.getNumberOfChildren() > 0){
				nextBlock = new BasicBlock();
				nextBlock.setLabel("L"+nextLabel());
				elseLabel = nextBlock.getLabel();
				currentBlock.addRightSuccessor(nextBlock);
				nextBlock.addPredecessor(currentBlock);
			}
			
			mergeConditional = new  BasicBlock();
			mergeConditional.setLabel("L"+nextLabel());
			
			if(!(el.getNumberOfChildren() > 0)){
				elseLabel = mergeConditional.getLabel();
			}
			
			if(e.getNumberOfChildren() == 0){
				compi = true;
			}
			else if(e.getText().equals("!")){
				compi = true;
			}

			expr(e);
			conditionalMove = false;
			compi = false;
			startConditional = currentBlock;
			startIf.push(startConditional);
			
			//mergeConditional = new  BasicBlock();
			//mergeConditional.setLabel("L"+nextLabel());

			endIf.push(mergeConditional);
			endIf.push(mergeConditional);
			endIf.push(mergeConditional);
			
			/*	
			if(el.getNumberOfChildren() > 0){
				currentBlock.getRightSuccessor().addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  
							new Immediate("0"), alternateReg));
			}
			*/

			currentBlock = currentBlock.getLeftSuccessor();

			/*
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  
						new Immediate("1"), alternateReg));
			*/
			block(b1);

			mergeConditional = (BasicBlock)endIf.pop();
			currentBlock.addLeftSuccessor(mergeConditional);
			mergeConditional.addPredecessor(currentBlock);
			mergeLabel = mergeConditional.getLabel();
			//currentBlock.addInst(new ILOC("JumpI", "", "", mergeLabel));
			currentBlock.addInst(new BranchingInstruction(BranchingInstruction.JumpOpCode.JUMPI , 
						new Label(mergeLabel)));

			currentBlock = (BasicBlock)startIf.pop();

			if(el.getNumberOfChildren() > 0){
				currentBlock = currentBlock.getRightSuccessor();
				block(b2);

				mergeConditional = (BasicBlock)endIf.pop();
				mergeLabel = mergeConditional.getLabel();
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.JumpOpCode.JUMPI , 
							new Label(mergeLabel)));
				
				currentBlock.addLeftSuccessor(mergeConditional);
				mergeConditional.addPredecessor(currentBlock);
			}
			else
			{
				mergeConditional = (BasicBlock)endIf.pop();
				currentBlock.addRightSuccessor(mergeConditional);
				mergeConditional.addPredecessor(currentBlock);
			}
			//currentBlock = mergeConditional;
			currentBlock = (BasicBlock)endIf.pop();

			/*
			if(!(el.getNumberOfChildren() > 0)){
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  
							new Immediate("0"), alternateReg));
			}
			*/
			
		}


	| #(WHILE #(GUARD ge:.) #(DO wb:.))
		{

			/* Conditional Block */
			nextBlock = new BasicBlock();
			nextBlock.setLabel("L"+nextLabel());
			currentBlock.addLeftSuccessor(nextBlock);
			nextBlock.addPredecessor(currentBlock);
			currentBlock = nextBlock;
			
			/* loop body */	
			nextBlock = new BasicBlock();
			nextBlock.setLabel("L"+nextLabel());
			thenLabel = nextBlock.getLabel();
			currentBlock.addLeftSuccessor(nextBlock);
			nextBlock.addPredecessor(currentBlock);
			
			/* after while */	
			mergeConditional = new  BasicBlock();
			mergeConditional.setLabel("L"+nextLabel());

			/* sets the label for after while */
			elseLabel = mergeConditional.getLabel();
			
			conditionalMove	= true;	
			expr(ge);
			conditionalMove	= false;	
			
			currentBlock.addRightSuccessor(mergeConditional);
			mergeConditional.addPredecessor(currentBlock);	

			startConditional = currentBlock;
			startLoop.push(startConditional);
			endLoop.push(mergeConditional);
			startLoop.push(startConditional);
			endLoop.push(mergeConditional);
			
			startLoop.push(startConditional);
			endLoop.push(mergeConditional);
			endLoop.push(mergeConditional);
			
			currentBlock = currentBlock.getLeftSuccessor();
			block(wb);
		
			/*			
			conditionalMove	= true;
			thenLabel = ((BasicBlock)startLoop.pop()).getLeftSuccessor().getLabel();
			elseLabel = ((BasicBlock)endLoop.pop()).getLabel();
			expr(ge);
			conditionalMove	= false;	
			*/	

			/*	
			thenLabel = ((BasicBlock)startLoop.pop()).getLeftSuccessor().getLabel();
			elseLabel = ((BasicBlock)endLoop.pop()).getLabel();
			currentBlock.addInst(new BranchingInstruction(BranchingInstruction.JumpOpCode.JUMPI , 
						new Label(((BasicBlock)startLoop.pop()).getLabel())));
			*/	

			nextBlock = new BasicBlock();
			nextBlock.setLabel("L"+nextLabel());
			currentBlock.addLeftSuccessor(nextBlock);
			nextBlock.addPredecessor(currentBlock);
			currentBlock = nextBlock;

			currentBlock.addLeftSuccessor(((BasicBlock)startLoop.pop()).getLeftSuccessor());	
			currentBlock.addRightSuccessor((BasicBlock)endLoop.pop());	
			((BasicBlock)endLoop.pop()).addPredecessor(currentBlock);

			conditionalMove	= true;
			thenLabel = ((BasicBlock)startLoop.pop()).getLeftSuccessor().getLabel();
			elseLabel = ((BasicBlock)endLoop.pop()).getLabel();
			expr(ge);
			conditionalMove	= false;

			currentBlock = (BasicBlock)endLoop.pop();

		}
	| #(RETURN (re:.)?)
		{
			ret = true;
			if(re != null){
				r = expr(re);
			}
			ret = false;
			if(r != null){
				currentBlock.addInst(new InvokeInstruction(InvokeInstruction.RetOpCode.RET,
							r));
			}
			currentBlock.addInst(new BranchingInstruction(BranchingInstruction.JumpOpCode.JUMPI , 
						new Label(exit.getLabel())));
		}
	| #(DELETE r=expr)
		{
			currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
							r, new Immediate("0")));
			currentBlock.addInst(new InvokeInstruction(InvokeInstruction.CallOpCode.CALL,
						new Label("free"), new Immediate("1")));
		}
	| #(INVOKE fid:ID args:.)
		{
			Immediate argCount;
			paramCount = 0;
			arg = true;
			arguments(args);
			arg = false;
			argCount = new Immediate(Integer.toString(paramCount));
			currentBlock.addInst(new InvokeInstruction(InvokeInstruction.CallOpCode.CALL,
						new Label(fid.getText()), argCount));
					
		}
	| #(STMT_LST (statement)*)
	;
block
	: statement_list
	;
lvaluedot returns [Register result = null]
{Register l, r; l = r = null;}
	: #(d:DOT e1:. e2:.)
		{
			dotOp = true;
			result = Register.make();
			l = lvaluedot(e1);
			if(e1.getText().equals(".")){
				//structType = arp.getStructType(e1.getSibling().getText());
				
				structType = arp.getNestedStructType(e1.getFirstChild().getNextSibling().getText(), structType);
			}	
			
			
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
						l, new Immediate( arp.getFieldOffset(structType, e2.getText()) + ":" + e2.getText()), 
						result, arp.getFieldOffset(structType, e2.getText())));
			//result = l;
			//storeStruct = new Immediate(arp.getFieldOffset(structType, e2.getText()) + ":" + e2.getText());
		}
	| i:ID
		{
			if(!dotOp){
				result = Register.getReg(i.getText());
				if(result == null){
					result = Register.make(i.getText());

					if(read)
					{
						currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.ADDI,
								Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()),
								result));
						//Register.remove(i.getText());	
					}
					else
					{
						currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
								Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
								result, arp.getVarOffset(i.getText())));
					}
				}
				/*
				if(read)
				{
					currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.ADDI,
							Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()),
							result));
					Register.remove(i.getText());	
				}
				*/
			}	
			else
			{
				
				result = Register.make();
				structType = arp.getStructType(i.getText());
				
				if(arp.getVarOffset(i.getText()) == -1){
					if(arp.getParamOff(i.getText()) == null && arp.getGlobalDir(i.getText()) != null){
						currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.COMPUTEGLOBL,
						new Immediate(i.getText()), result));
					}
					else
					{
						currentBlock.addInst(new MoveInstruction(MoveInstruction.MovOpCode.MOV,
							Register.getReg(i.getText()), result));
					}
				}
				else
				{
					currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
						Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
						result, arp.getVarOffset(i.getText())));
				}
				/*
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
						Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
						result, arp.getVarOffset(i.getText())));
				*/
				dotOp = false;
			}	
		}
	;
lvalue returns [Register result = null]
{Register l, r; l = r = null;}
	: #(d:DOT e1:. e2:.)
		{
			//result = Register.make();
			//test this was not commented out 06/10/2007

			
			dotOp = true;
			//result = Register.make();
			l = lvaluedot(e1);
			if(e1.getText().equals(".")){
				//structType = arp.getStructType(e1.getSibling().getText());
				
				structType = arp.getNestedStructType(e1.getFirstChild().getNextSibling().getText(), structType);
			}	
			
			
			//currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
			//			l, new Immediate( arp.getFieldOffset(structType, e2.getText()) + ":" + e2.getText()), 
			//			result, arp.getFieldOffset(structType, e2.getText())));
		
				
			result = l;
			storeStruct = new Immediate(arp.getFieldOffset(structType, e2.getText()) + ":" + e2.getText());
			
			/*
			storeStruct = new Immediate(arp.getFieldOffset(structType, e2.getText()) + ":" + e2.getText());
			currentBlock.addInst(new MoveInstruction(MoveInstruction.MovOpCode.MOV,
						l, result));
			*/
		}
	| i:ID
		{
			if(!dotOp){
			//	if((arp.getVarOffset(i.getText()) == -1) && (arp.getGlobalDir(i.getText()) != null)){
					//result = Register.make(i.getText());
					//currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreGlobalOpCode.STOREGLOBL,
					//		new Immediate(i.getText()), result));	
			//	}
			//	else
			//	{
					result = Register.getReg(i.getText());
					
					if(result == null){
						result = Register.make(i.getText());
					
					
						if(read)
						{
							if(arp.getVarOffset(i.getText()) != -1){
								currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.ADDI,
									Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()),
									result));
							}
							else
							{
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.COMPUTEGLOBL,
									new Immediate(i.getText()), result));

							}
							result.readSet();
							read = false;		
							//Register.remove(i.getText());	
						}
						else if(arp.getVarOffset(i.getText()) == -1){
							currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.COMPUTEGLOBL,
								new Immediate(i.getText()), result));
						}
						else 
						{
							
							currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
								Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
								result, arp.getVarOffset(i.getText())));
						}
					}
					else
					{
						if(result.isRecentlyRead()){
							if(arp.getVarOffset(i.getText()) != -1){
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
									Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
									result, arp.getVarOffset(i.getText())));
							}
							else
							{
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.LOADGLOBL,
									new Immediate(i.getText()), result));
							}
							result.readUnset();
						}
					}
					if(read)
					{
						currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.ADDI,
							Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()),
							result));
						result.readSet();
						read = false;
						//Register.remove(i.getText());	
					}
			//	}
					
				/*	
						if(read)
						{
							result = Register.getReg(i.getText());
							if(result == null)
								result = Register.make(i.getText());

							currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.ADDI,
								Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()),
								result));
							Register.remove(i.getText());	
						}
						else 
						{
							result = Register.getReg(i.getText());
							if(result == null){
								result = Register.make(i.getText());
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
									Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
									result, arp.getVarOffset(i.getText())));
							}
						}
				*/	
				
			}	
			else
			{
				
				result = Register.make();
				structType = arp.getStructType(i.getText());
				
				if(arp.getVarOffset(i.getText()) == -1){
					currentBlock.addInst(new MoveInstruction(MoveInstruction.MovOpCode.MOV,
						Register.getReg(i.getText()), result));
				}
				else
				{
					currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
						Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
						result, arp.getVarOffset(i.getText())));
				}
				/*
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
						Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
						result, arp.getVarOffset(i.getText())));
				*/
				dotOp = false;
			}	
		}
	;
arguments
{Register r = null; realArgs = new Vector<Register>();}
	: #(ARGS (r=expr { 
				/*	
				currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
							r, new Immediate(Integer.toString(paramCount))));
				*/
				realArgs.add(r);
				paramCount++;
			})*)
		{
			for(int i = 0; i < paramCount; i++){
				currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
							realArgs.get(i), new Immediate(Integer.toString(i))));
			}
		}
	;
expr returns [Register result = null]
{Register t1, t2, l; l = t1= t2 = null;}
	: i:ID
		{
			if(!dotOp){
			//currentBlock.addInst(new ILOC("loadAI", "rarp","@"+i.getText(),  "r"+result));
				//if((arp.getVarOffset(i.getText()) == -1) && (arp.getGlobalDir(i.getText()) != null)){
				//	result = Register.make(i.getText());
				//	currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.LOADGLOBL,
				//			new Immediate(i.getText()), result));	
				//}
				//else
				//{	
					result = Register.getReg(i.getText());
					if(result == null){
						result = Register.make(i.getText());
						if(arp.getVarOffset(i.getText()) == -1){
							currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.LOADGLOBL,
								new Immediate(i.getText()), result));
						}
						else
						{
							currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
								Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
								result, arp.getVarOffset(i.getText())));
						}
					}
					else
					{
						if(result.isRecentlyRead()){
							if(arp.getVarOffset(i.getText()) != -1){
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
									Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
									result, arp.getVarOffset(i.getText())));
							}
							else
							{
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.LOADGLOBL,
									new Immediate(i.getText()), result));
							}
							result.readUnset();
							
						}
						else if(arp.getStructType(i.getText()) != null || arp.getGlobalDir(i.getText()) != null){// && (arg || ret)){
							if(!(arp.getVarOffset(i.getText()) == -1)){
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
									Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
									result, arp.getVarOffset(i.getText())));
							}
							else if(arp.getParamOff(i.getText()) != null)
							{
								currentBlock.addInst(new MoveInstruction(MoveInstruction.MovOpCode.MOV,
									Register.getReg(i.getText()), result));
								/*	
								Register temp = Register.make();
								currentBlock.addInst(new MoveInstruction(MoveInstruction.MovOpCode.MOV,
									Register.getReg(i.getText()), temp));
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
									temp, new Immediate("0"), 
									result, 0));
								*/
							}
							else if(arp.getGlobalDir(i.getText()) != null){
								currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.LOADGLOBL,
								new Immediate(i.getText()), result));
							}
								
						}
						/*
						else if(arp.getGlobalDir(i.getText()) != null){
							result = Register.make(i.getText());
							currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadGlobalOpCode.LOADGLOBL,
								new Immediate(i.getText()), result));
						}	
						*/
					}
				//}
			}
			else
			{
				
				result = Register.make();
				
				structType = arp.getStructType(i.getText());
				
				if(arp.getVarOffset(i.getText()) == -1){
					currentBlock.addInst(new MoveInstruction(MoveInstruction.MovOpCode.MOV,
						Register.getReg(i.getText()), result));
				}
				else
				{
					currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
						Register.getReg("rarp"), new Immediate( arp.getVarOffset(i.getText()) + ":" + i.getText()), 
						result, arp.getVarOffset(i.getText())));
				}
				dotOp = false;
			}
			if(compi){
				alternateReg = Register.make();
				//result = alternateReg;
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), alternateReg));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMPI,
							result, new Immediate("1"), Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBREQ,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
				result = alternateReg;
			}
			
		}
	| #(p:PLUS t1=expr t2=expr) 
		{ 
			result = Register.make();
			currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.ADD,
						t1, t2, result));

		}
	//| #(MINUS t1=exp1:expr (t2=exp2:expr)?)
	| #(m:MINUS exp1:. (exp2:.)?)
		{ 
			result = Register.make();
			//++++++++++++++Fix this for --
			if(m.getNumberOfChildren() > 1){
				t1=expr(exp1);
				t2=expr(exp2);		
				currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.SUB,
						t1, t2, result));
			}
			else
			{
				t1=expr(exp1);
				//currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(m.getText() + exp1.getText()), result));
				currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.RSUBI, t1, new Immediate("0"), result));

			}
		}
	| #(MULT t1=expr t2=expr)
		{ 
			result = Register.make();
			currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.MULT,
						t1, t2, result));
		}
	| #(DIV t1=expr t2=expr)
		{ 
			result = Register.make();
			
			
			//Local regs will cause problem has to call div function 
			/*	
			currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.DIV,
						t1, t2, result));
			*/	
				
			currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
							t1, new Immediate("0")));
			currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
							t2, new Immediate("1")));
			currentBlock.addInst(new InvokeInstruction(InvokeInstruction.CallOpCode.CALL,
						new Label(".div"), new Immediate("2")));
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadRetOpCode.LOADRET,
						result));
		}
 	| #(AND andexp1:. andexp2:.)
		{ 
			//result = nextRegister(); 
			//currentBlock.addInst(new ILOC("and", "r"+t1, "r"+t2, "r"+result));
			result = Register.make();
			if(conditionalMove){
				conditionalMove = false;
				t1 = expr(andexp1);
				t2 = expr(andexp2);
				currentBlock.addInst(new BinopInstruction(BinopInstruction.BooleanOpCode.AND,
						t1, t2, result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMPI,
							result, new Immediate("1"), Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBREQ,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
				alternateReg = result;
			}
			else
			{
				t1 = expr(andexp1);
				t2 = expr(andexp2);

				currentBlock.addInst(new BinopInstruction(BinopInstruction.BooleanOpCode.AND,
						t1, t2, result));
			}
				
		}
	| #(OR orexp1:. orexp2:.)
		{ 
			result = Register.make();
			if(conditionalMove){
				conditionalMove = false;
				t1 = expr(orexp1);
				t2 = expr(orexp2);
				currentBlock.addInst(new BinopInstruction(BinopInstruction.BooleanOpCode.OR,
						t1, t2, result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMPI,
							result, new Immediate("1"), Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBREQ,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
				alternateReg = result;
			}
			else
			{
				t1 = expr(andexp1);
				t2 = expr(andexp2);

				currentBlock.addInst(new BinopInstruction(BinopInstruction.BooleanOpCode.OR,
						t1, t2, result));
			}
		}
	| #(COMPAR t1=expr t2=expr)
		{
			if(conditionalMove){
				alternateReg = Register.make();
				result = alternateReg;
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBREQ,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
			}
			else
			{
				result = Register.make();
				Label trueLabel = new Label("L"+nextLabel());
				Label falseLabel = new Label("L"+nextLabel());
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBREQ,
							Register.getReg("ccr"), trueLabel, falseLabel));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							falseLabel));
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(0)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							trueLabel));

			}
		}	
	| #(LT t1=expr t2=expr)
		{
			if(conditionalMove){
				alternateReg = Register.make();
				result = alternateReg;
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRLT,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
			}
			else
			{
				result = Register.make();
				Label trueLabel = new Label("L"+nextLabel());
				Label falseLabel = new Label("L"+nextLabel());
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRLT,
							Register.getReg("ccr"), trueLabel, falseLabel));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							falseLabel));
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(0)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							trueLabel));

			}
		}	
	| #(GT t1=expr t2=expr)
		{
			if(conditionalMove){
				alternateReg = Register.make();
				result = alternateReg;
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRGT,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
			}
			else
			{
				result = Register.make();
				Label trueLabel = new Label("L"+nextLabel());
				Label falseLabel = new Label("L"+nextLabel());
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRGT,
							Register.getReg("ccr"), trueLabel, falseLabel));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							falseLabel));
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(0)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							trueLabel));

			}
		}	
	| #(GTE t1=expr t2=expr)
		{
			if(conditionalMove){
				alternateReg = Register.make();
				result = alternateReg;
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRGE,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
			}
			else
			{
				result = Register.make();
				Label trueLabel = new Label("L"+nextLabel());
				Label falseLabel = new Label("L"+nextLabel());
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRGE,
							Register.getReg("ccr"), trueLabel, falseLabel));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							falseLabel));
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(0)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							trueLabel));

			}
		}	
	| #(LTE t1=expr t2=expr)
		{
			if(conditionalMove){
				alternateReg = Register.make();
				result = alternateReg;
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRLE,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
			}
			else
			{
				result = Register.make();
				Label trueLabel = new Label("L"+nextLabel());
				Label falseLabel = new Label("L"+nextLabel());
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRLE,
							Register.getReg("ccr"), trueLabel, falseLabel));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							falseLabel));
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(0)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							trueLabel));

			}
		}	
	| #(NE t1=expr t2=expr)
		{
			if(conditionalMove){
				alternateReg = Register.make();
				result = alternateReg;
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRNE,
							Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));
			}
			else
			{
				result = Register.make();
				Label trueLabel = new Label("L"+nextLabel());
				Label falseLabel = new Label("L"+nextLabel());
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMP,
							t1, t2, Register.getReg("ccr")));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBRNE,
							Register.getReg("ccr"), trueLabel, falseLabel));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							falseLabel));
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(0)), result));
				currentBlock.addInst(new BranchingInstruction(BranchingInstruction.InsLabelOpCode.INSERTLABL,
							trueLabel));

			}

		}	
	| #(BANG ebang:.)
		{
			compi = false;
			t1 = expr(ebang);			
			alternateReg = Register.make();
			result = alternateReg;
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(Integer.toString(1)), result));
			currentBlock.addInst(new BinopInstruction(BinopInstruction.BooleanOpCode.XORI,
						t1, new Immediate("1"), result));
			currentBlock.addInst(new BranchingInstruction(BranchingInstruction.ComparOpCode.COMPI,
						result, new Immediate("1"), Register.getReg("ccr")));
			currentBlock.addInst(new BranchingInstruction(BranchingInstruction.BranchOpCode.CBREQ,
						Register.getReg("ccr"), new Label(thenLabel), new Label(elseLabel)));

		}
	| #(DOT e1:. e2:.)//t1=. t2:.)
		{	
			dotOp = true;
			result = Register.make();
			l = expr(e1);
			if(e1.getText().equals(".")){
				//structType = arp.getStructType(e1.getSibling().getText());
				
				structType = arp.getNestedStructType(e1.getFirstChild().getNextSibling().getText(), structType);
			}	
			
			
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadAIOpCode.LOADAI,
						l, new Immediate( arp.getFieldOffset(structType, e2.getText()) + ":" + e2.getText()), 
						result, arp.getFieldOffset(structType, e2.getText())));
		}
	| #(FUNCALL fid:ID args:.)
		{
			Immediate argCount;
			result = Register.make();
			
			paramCountStack.push(paramCount);
			realArgsStack.push(realArgs);
			
			paramCount = 0;
			arg = true;
			arguments(args);
			arg = false;
			argCount = new Immediate(Integer.toString(paramCount));
			currentBlock.addInst(new InvokeInstruction(InvokeInstruction.CallOpCode.CALL,
						new Label(fid.getText()), argCount));

			paramCount = paramCountStack.pop();
			realArgs = realArgsStack.pop();
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadRetOpCode.LOADRET, 
						result));
		}
	| #(NEW ni:ID)
		{
			result = Register.make();
			currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
							new Immediate(Integer.toString(arp.getStructSize(ni.getText()))), 
							new Immediate("0")));
			currentBlock.addInst(new InvokeInstruction(InvokeInstruction.CallOpCode.CALL,
						new Label("malloc"), new Immediate("1")));
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadRetOpCode.LOADRET,
						result));
		}
	| NULL
		{
			result = Register.make();
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate("0"), result));
		}
	| TRUE
		{ 
			//result = nextRegister();
			result = Register.make();
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate("1"), result));
		}
	| FALSE
		{
			result = Register.make();
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate("0"), result));
		}
	| n:NUM
		{ 
			//result = nextRegister();
			/*
			if(n.getText().equals("0")){
				result = Register.getReg("%g0");
			}
			else
			{
				result = Register.make();
				currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(n.getText()), result));
			}
			*/
			result = Register.make();
			currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate(n.getText()), result));
		}
	;
