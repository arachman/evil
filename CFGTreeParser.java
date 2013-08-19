// $ANTLR 2.7.7 (20070330): "cfg.g" -> "CFGTreeParser.java"$

	import java.util.*;
	import antlr.*;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class CFGTreeParser extends antlr.TreeParser       implements CFGTreeParserTokenTypes
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
	
public CFGTreeParser() {
	tokenNames = _tokenNames;
}

	public final Vector  start(AST _t) throws RecognitionException {
		Vector cfgV = new Vector();
		
		AST start_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST t = null;
		AST d = null;
		AST f = null;
		Vector fun = null;
		
		AST __t2 = _t;
		AST tmp1_AST_in = (AST)_t;
		match(_t,PROG);
		_t = _t.getFirstChild();
		t = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		d = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		f = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		_t = __t2;
		_t = _t.getNextSibling();
		
					paramCountStack = new Stack<Integer>();
					globalARP = new ActivationRecord();
					types(t);
					isGlobal = true;
					declarations(d);
					isGlobal = false;
					fun = functions(f);
					cfgV = fun; 
				
		_retTree = _t;
		return cfgV;
	}
	
	public final Vector  functions(AST _t) throws RecognitionException {
		Vector cfgV = new Vector();
		
		AST functions_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST f = null;
		
		AST __t4 = _t;
		AST tmp2_AST_in = (AST)_t;
		match(_t,FUNS);
		_t = _t.getFirstChild();
		{
		_loop6:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= NEW && _t.getType() <= FUNCALL))) {
				f = (AST)_t;
				if ( _t==null ) throw new MismatchedTokenException();
				_t = _t.getNextSibling();
				
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
						
			}
			else {
				break _loop6;
			}
			
		} while (true);
		}
		_t = __t4;
		_t = _t.getNextSibling();
		_retTree = _t;
		return cfgV;
	}
	
	public final void function(AST _t) throws RecognitionException {
		
		AST function_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST p = null;
		AST r = null;
		AST b = null;
		
		AST __t8 = _t;
		i = _t==ASTNULL ? null :(AST)_t;
		match(_t,ID);
		_t = _t.getFirstChild();
		p = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		r = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		b = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		_t = __t8;
		_t = _t.getNextSibling();
		
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
				
		_retTree = _t;
	}
	
	public final void types(AST _t) throws RecognitionException {
		
		AST types_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST t = null;
		
		AST __t10 = _t;
		AST tmp3_AST_in = (AST)_t;
		match(_t,TYPES);
		_t = _t.getFirstChild();
		{
		_loop12:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= NEW && _t.getType() <= FUNCALL))) {
				t = (AST)_t;
				if ( _t==null ) throw new MismatchedTokenException();
				_t = _t.getNextSibling();
				
							globalARP.defineStruct(t.getFirstChild().getText());
							type_decl(t);
						
			}
			else {
				break _loop12;
			}
			
		} while (true);
		}
		_t = __t10;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void type_decl(AST _t) throws RecognitionException {
		
		AST type_decl_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST t = null;
		AST ty_id = null;
		
		AST __t14 = _t;
		AST tmp4_AST_in = (AST)_t;
		match(_t,STRUCT);
		_t = _t.getFirstChild();
		i = (AST)_t;
		match(_t,ID);
		_t = _t.getNextSibling();
		AST __t15 = _t;
		AST tmp5_AST_in = (AST)_t;
		match(_t,NESTED_DECL);
		_t = _t.getFirstChild();
		{
		_loop18:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==TY_DECL)) {
				AST __t17 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,TY_DECL);
				_t = _t.getFirstChild();
				t = (AST)_t;
				if ( _t==null ) throw new MismatchedTokenException();
				_t = _t.getNextSibling();
				ty_id = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t17;
				_t = _t.getNextSibling();
				
							 	globalARP.defineField(i.getText(), ty_id.getText());
								if(t.getText().equals("struct")){
									globalARP.nestedStructDecl(ty_id.getText(), t.getFirstChild().getText(), i.getText());
								}
									
							
			}
			else {
				break _loop18;
			}
			
		} while (true);
		}
		_t = __t15;
		_t = _t.getNextSibling();
		_t = __t14;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void declarations(AST _t) throws RecognitionException {
		
		AST declarations_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t20 = _t;
		AST tmp7_AST_in = (AST)_t;
		match(_t,DECLS);
		_t = _t.getFirstChild();
		{
		_loop22:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==DECL)) {
				declaration(_t);
				_t = _retTree;
			}
			else {
				break _loop22;
			}
			
		} while (true);
		}
		_t = __t20;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void declaration(AST _t) throws RecognitionException {
		
		AST declaration_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST t = null;
		AST i = null;
		
		AST __t24 = _t;
		AST tmp8_AST_in = (AST)_t;
		match(_t,DECL);
		_t = _t.getFirstChild();
		t = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		AST __t25 = _t;
		AST tmp9_AST_in = (AST)_t;
		match(_t,IDS);
		_t = _t.getFirstChild();
		{
		_loop27:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==ID)) {
				i = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				
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
						
			}
			else {
				break _loop27;
			}
			
		} while (true);
		}
		_t = __t25;
		_t = _t.getNextSibling();
		_t = __t24;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void parameters(AST _t) throws RecognitionException {
		
		AST parameters_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t29 = _t;
		AST tmp10_AST_in = (AST)_t;
		match(_t,PARAMS);
		_t = _t.getFirstChild();
		{
		_loop31:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==TY_DECL)) {
				decl(_t);
				_t = _retTree;
			}
			else {
				break _loop31;
			}
			
		} while (true);
		}
		_t = __t29;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void decl(AST _t) throws RecognitionException {
		
		AST decl_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST t = null;
		AST i = null;
		
		AST __t33 = _t;
		AST tmp11_AST_in = (AST)_t;
		match(_t,TY_DECL);
		_t = _t.getFirstChild();
		t = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		i = (AST)_t;
		match(_t,ID);
		_t = _t.getNextSibling();
		_t = __t33;
		_t = _t.getNextSibling();
		
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
				
		_retTree = _t;
	}
	
	public final void body(AST _t) throws RecognitionException {
		
		AST body_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST d = null;
		AST s = null;
		
		AST __t35 = _t;
		AST tmp12_AST_in = (AST)_t;
		match(_t,BODY);
		_t = _t.getFirstChild();
		d = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		s = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		_t = __t35;
		_t = _t.getNextSibling();
		
					declarations(d);
					statement_list(s);
				
		_retTree = _t;
	}
	
	public final void statement_list(AST _t) throws RecognitionException {
		
		AST statement_list_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST s = null;
		
		AST __t37 = _t;
		AST tmp13_AST_in = (AST)_t;
		match(_t,STMT_LST);
		_t = _t.getFirstChild();
		{
		_loop39:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_tokenSet_0.member(_t.getType()))) {
				s = _t==ASTNULL ? null : (AST)_t;
				statement(_t);
				_t = _retTree;
			}
			else {
				break _loop39;
			}
			
		} while (true);
		}
		_t = __t37;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void statement(AST _t) throws RecognitionException {
		
		AST statement_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST ll = null;
		AST rr = null;
		AST p = null;
		AST lval = null;
		AST e = null;
		AST b1 = null;
		AST el = null;
		AST b2 = null;
		AST ge = null;
		AST wb = null;
		AST re = null;
		AST fid = null;
		AST args = null;
		Register l, r; l = r = null; int i = 0;
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case EQUAL:
		{
			AST __t41 = _t;
			AST tmp14_AST_in = (AST)_t;
			match(_t,EQUAL);
			_t = _t.getFirstChild();
			ll = _t==ASTNULL ? null : (AST)_t;
			l=lvalue(_t);
			_t = _retTree;
			rr = _t==ASTNULL ? null : (AST)_t;
			r=expr(_t);
			_t = _retTree;
			_t = __t41;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case PRINT:
		{
			AST __t42 = _t;
			p = _t==ASTNULL ? null :(AST)_t;
			match(_t,PRINT);
			_t = _t.getFirstChild();
			r=expr(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ENDL:
			{
				AST tmp15_AST_in = (AST)_t;
				match(_t,ENDL);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t42;
			_t = _t.getNextSibling();
			
						if(p.getNumberOfChildren() > 1){
			
							currentBlock.addInst(new IOInstruction(IOInstruction.IOOpCode.PRINTLN,
										r));
						}
						else 
						{
							currentBlock.addInst(new IOInstruction(IOInstruction.IOOpCode.PRINT,
									r));
						}
					
			break;
		}
		case READ:
		{
			AST __t44 = _t;
			AST tmp16_AST_in = (AST)_t;
			match(_t,READ);
			_t = _t.getFirstChild();
			lval = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t44;
			_t = _t.getNextSibling();
			
						read = true;
						l = lvalue(lval);
						read = false;
						currentBlock.addInst(new IOInstruction(IOInstruction.IOOpCode.READ, l));
						
					
			break;
		}
		case IF:
		{
			AST __t45 = _t;
			AST tmp17_AST_in = (AST)_t;
			match(_t,IF);
			_t = _t.getFirstChild();
			AST __t46 = _t;
			AST tmp18_AST_in = (AST)_t;
			match(_t,GUARD);
			_t = _t.getFirstChild();
			e = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t46;
			_t = _t.getNextSibling();
			AST __t47 = _t;
			AST tmp19_AST_in = (AST)_t;
			match(_t,THEN);
			_t = _t.getFirstChild();
			b1 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t47;
			_t = _t.getNextSibling();
			AST __t48 = _t;
			el = _t==ASTNULL ? null :(AST)_t;
			match(_t,ELS);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NEW:
			case FUN:
			case WHILE:
			case RETURN:
			case PRINT:
			case READ:
			case STRUCT:
			case IF:
			case ELSE:
			case INT:
			case BOOL:
			case NULL:
			case TRUE:
			case FALSE:
			case VOID:
			case ENDL:
			case DELETE:
			case SEMI:
			case DOT:
			case BANG:
			case EQUAL:
			case COMPAR:
			case AND:
			case OR:
			case MULT:
			case DIV:
			case LT:
			case GT:
			case NE:
			case LTE:
			case GTE:
			case PLUS:
			case MINUS:
			case LPAREN:
			case RPAREN:
			case LCURLY:
			case RCURLY:
			case COMMA:
			case NUM:
			case ID:
			case WS:
			case COMM:
			case ARGS:
			case PROG:
			case BODY:
			case PARAMS:
			case FUNS:
			case DECLS:
			case DECL:
			case NESTED_DECL:
			case TYPES:
			case IDS:
			case NUMS:
			case STMT_LST:
			case THEN:
			case ELS:
			case INVOKE:
			case DO:
			case UNARY:
			case TYPE:
			case TY_DECL:
			case GUARD:
			case FUNCALL:
			{
				b2 = (AST)_t;
				if ( _t==null ) throw new MismatchedTokenException();
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t48;
			_t = _t.getNextSibling();
			_t = __t45;
			_t = _t.getNextSibling();
			
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
						
					
			break;
		}
		case WHILE:
		{
			AST __t50 = _t;
			AST tmp20_AST_in = (AST)_t;
			match(_t,WHILE);
			_t = _t.getFirstChild();
			AST __t51 = _t;
			AST tmp21_AST_in = (AST)_t;
			match(_t,GUARD);
			_t = _t.getFirstChild();
			ge = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t51;
			_t = _t.getNextSibling();
			AST __t52 = _t;
			AST tmp22_AST_in = (AST)_t;
			match(_t,DO);
			_t = _t.getFirstChild();
			wb = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t52;
			_t = _t.getNextSibling();
			_t = __t50;
			_t = _t.getNextSibling();
			
			
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
			
					
			break;
		}
		case RETURN:
		{
			AST __t53 = _t;
			AST tmp23_AST_in = (AST)_t;
			match(_t,RETURN);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NEW:
			case FUN:
			case WHILE:
			case RETURN:
			case PRINT:
			case READ:
			case STRUCT:
			case IF:
			case ELSE:
			case INT:
			case BOOL:
			case NULL:
			case TRUE:
			case FALSE:
			case VOID:
			case ENDL:
			case DELETE:
			case SEMI:
			case DOT:
			case BANG:
			case EQUAL:
			case COMPAR:
			case AND:
			case OR:
			case MULT:
			case DIV:
			case LT:
			case GT:
			case NE:
			case LTE:
			case GTE:
			case PLUS:
			case MINUS:
			case LPAREN:
			case RPAREN:
			case LCURLY:
			case RCURLY:
			case COMMA:
			case NUM:
			case ID:
			case WS:
			case COMM:
			case ARGS:
			case PROG:
			case BODY:
			case PARAMS:
			case FUNS:
			case DECLS:
			case DECL:
			case NESTED_DECL:
			case TYPES:
			case IDS:
			case NUMS:
			case STMT_LST:
			case THEN:
			case ELS:
			case INVOKE:
			case DO:
			case UNARY:
			case TYPE:
			case TY_DECL:
			case GUARD:
			case FUNCALL:
			{
				re = (AST)_t;
				if ( _t==null ) throw new MismatchedTokenException();
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t53;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case DELETE:
		{
			AST __t55 = _t;
			AST tmp24_AST_in = (AST)_t;
			match(_t,DELETE);
			_t = _t.getFirstChild();
			r=expr(_t);
			_t = _retTree;
			_t = __t55;
			_t = _t.getNextSibling();
			
						currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
										r, new Immediate("0")));
						currentBlock.addInst(new InvokeInstruction(InvokeInstruction.CallOpCode.CALL,
									new Label("free"), new Immediate("1")));
					
			break;
		}
		case INVOKE:
		{
			AST __t56 = _t;
			AST tmp25_AST_in = (AST)_t;
			match(_t,INVOKE);
			_t = _t.getFirstChild();
			fid = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			args = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t56;
			_t = _t.getNextSibling();
			
						Immediate argCount;
						paramCount = 0;
						arg = true;
						arguments(args);
						arg = false;
						argCount = new Immediate(Integer.toString(paramCount));
						currentBlock.addInst(new InvokeInstruction(InvokeInstruction.CallOpCode.CALL,
									new Label(fid.getText()), argCount));
								
					
			break;
		}
		case STMT_LST:
		{
			AST __t57 = _t;
			AST tmp26_AST_in = (AST)_t;
			match(_t,STMT_LST);
			_t = _t.getFirstChild();
			{
			_loop59:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					statement(_t);
					_t = _retTree;
				}
				else {
					break _loop59;
				}
				
			} while (true);
			}
			_t = __t57;
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
	}
	
	public final Register  lvalue(AST _t) throws RecognitionException {
		Register result = null;
		
		AST lvalue_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST d = null;
		AST e1 = null;
		AST e2 = null;
		AST i = null;
		Register l, r; l = r = null;
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case DOT:
		{
			AST __t64 = _t;
			d = _t==ASTNULL ? null :(AST)_t;
			match(_t,DOT);
			_t = _t.getFirstChild();
			e1 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			e2 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t64;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case ID:
		{
			i = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return result;
	}
	
	public final Register  expr(AST _t) throws RecognitionException {
		Register result = null;
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST p = null;
		AST m = null;
		AST exp1 = null;
		AST exp2 = null;
		AST andexp1 = null;
		AST andexp2 = null;
		AST orexp1 = null;
		AST orexp2 = null;
		AST ebang = null;
		AST e1 = null;
		AST e2 = null;
		AST fid = null;
		AST args = null;
		AST ni = null;
		AST n = null;
		Register t1, t2, l; l = t1= t2 = null;
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case ID:
		{
			i = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			
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
						
					
			break;
		}
		case PLUS:
		{
			AST __t70 = _t;
			p = _t==ASTNULL ? null :(AST)_t;
			match(_t,PLUS);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t70;
			_t = _t.getNextSibling();
			
						result = Register.make();
						currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.ADD,
									t1, t2, result));
			
					
			break;
		}
		case MINUS:
		{
			AST __t71 = _t;
			m = _t==ASTNULL ? null :(AST)_t;
			match(_t,MINUS);
			_t = _t.getFirstChild();
			exp1 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NEW:
			case FUN:
			case WHILE:
			case RETURN:
			case PRINT:
			case READ:
			case STRUCT:
			case IF:
			case ELSE:
			case INT:
			case BOOL:
			case NULL:
			case TRUE:
			case FALSE:
			case VOID:
			case ENDL:
			case DELETE:
			case SEMI:
			case DOT:
			case BANG:
			case EQUAL:
			case COMPAR:
			case AND:
			case OR:
			case MULT:
			case DIV:
			case LT:
			case GT:
			case NE:
			case LTE:
			case GTE:
			case PLUS:
			case MINUS:
			case LPAREN:
			case RPAREN:
			case LCURLY:
			case RCURLY:
			case COMMA:
			case NUM:
			case ID:
			case WS:
			case COMM:
			case ARGS:
			case PROG:
			case BODY:
			case PARAMS:
			case FUNS:
			case DECLS:
			case DECL:
			case NESTED_DECL:
			case TYPES:
			case IDS:
			case NUMS:
			case STMT_LST:
			case THEN:
			case ELS:
			case INVOKE:
			case DO:
			case UNARY:
			case TYPE:
			case TY_DECL:
			case GUARD:
			case FUNCALL:
			{
				exp2 = (AST)_t;
				if ( _t==null ) throw new MismatchedTokenException();
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t71;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case MULT:
		{
			AST __t73 = _t;
			AST tmp27_AST_in = (AST)_t;
			match(_t,MULT);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t73;
			_t = _t.getNextSibling();
			
						result = Register.make();
						currentBlock.addInst(new BinopInstruction(BinopInstruction.ArithmeticOpCode.MULT,
									t1, t2, result));
					
			break;
		}
		case DIV:
		{
			AST __t74 = _t;
			AST tmp28_AST_in = (AST)_t;
			match(_t,DIV);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t74;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case AND:
		{
			AST __t75 = _t;
			AST tmp29_AST_in = (AST)_t;
			match(_t,AND);
			_t = _t.getFirstChild();
			andexp1 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			andexp2 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t75;
			_t = _t.getNextSibling();
			
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
							
					
			break;
		}
		case OR:
		{
			AST __t76 = _t;
			AST tmp30_AST_in = (AST)_t;
			match(_t,OR);
			_t = _t.getFirstChild();
			orexp1 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			orexp2 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t76;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case COMPAR:
		{
			AST __t77 = _t;
			AST tmp31_AST_in = (AST)_t;
			match(_t,COMPAR);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t77;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case LT:
		{
			AST __t78 = _t;
			AST tmp32_AST_in = (AST)_t;
			match(_t,LT);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t78;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case GT:
		{
			AST __t79 = _t;
			AST tmp33_AST_in = (AST)_t;
			match(_t,GT);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t79;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case GTE:
		{
			AST __t80 = _t;
			AST tmp34_AST_in = (AST)_t;
			match(_t,GTE);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t80;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case LTE:
		{
			AST __t81 = _t;
			AST tmp35_AST_in = (AST)_t;
			match(_t,LTE);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t81;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case NE:
		{
			AST __t82 = _t;
			AST tmp36_AST_in = (AST)_t;
			match(_t,NE);
			_t = _t.getFirstChild();
			t1=expr(_t);
			_t = _retTree;
			t2=expr(_t);
			_t = _retTree;
			_t = __t82;
			_t = _t.getNextSibling();
			
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
			
					
			break;
		}
		case BANG:
		{
			AST __t83 = _t;
			AST tmp37_AST_in = (AST)_t;
			match(_t,BANG);
			_t = _t.getFirstChild();
			ebang = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t83;
			_t = _t.getNextSibling();
			
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
			
					
			break;
		}
		case DOT:
		{
			AST __t84 = _t;
			AST tmp38_AST_in = (AST)_t;
			match(_t,DOT);
			_t = _t.getFirstChild();
			e1 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			e2 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t84;
			_t = _t.getNextSibling();
				
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
					
			break;
		}
		case FUNCALL:
		{
			AST __t85 = _t;
			AST tmp39_AST_in = (AST)_t;
			match(_t,FUNCALL);
			_t = _t.getFirstChild();
			fid = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			args = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t85;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case NEW:
		{
			AST __t86 = _t;
			AST tmp40_AST_in = (AST)_t;
			match(_t,NEW);
			_t = _t.getFirstChild();
			ni = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t86;
			_t = _t.getNextSibling();
			
						result = Register.make();
						currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
										new Immediate(Integer.toString(arp.getStructSize(ni.getText()))), 
										new Immediate("0")));
						currentBlock.addInst(new InvokeInstruction(InvokeInstruction.CallOpCode.CALL,
									new Label("malloc"), new Immediate("1")));
						currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadRetOpCode.LOADRET,
									result));
					
			break;
		}
		case NULL:
		{
			AST tmp41_AST_in = (AST)_t;
			match(_t,NULL);
			_t = _t.getNextSibling();
			
						result = Register.make();
						currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate("0"), result));
					
			break;
		}
		case TRUE:
		{
			AST tmp42_AST_in = (AST)_t;
			match(_t,TRUE);
			_t = _t.getNextSibling();
			
						//result = nextRegister();
						result = Register.make();
						currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate("1"), result));
					
			break;
		}
		case FALSE:
		{
			AST tmp43_AST_in = (AST)_t;
			match(_t,FALSE);
			_t = _t.getNextSibling();
			
						result = Register.make();
						currentBlock.addInst(new LoadInstruction(LoadInstruction.LoadIOpCode.LOADI,  new Immediate("0"), result));
					
			break;
		}
		case NUM:
		{
			n = (AST)_t;
			match(_t,NUM);
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return result;
	}
	
	public final void block(AST _t) throws RecognitionException {
		
		AST block_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		statement_list(_t);
		_t = _retTree;
		_retTree = _t;
	}
	
	public final Register  lvaluedot(AST _t) throws RecognitionException {
		Register result = null;
		
		AST lvaluedot_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST d = null;
		AST e1 = null;
		AST e2 = null;
		AST i = null;
		Register l, r; l = r = null;
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case DOT:
		{
			AST __t62 = _t;
			d = _t==ASTNULL ? null :(AST)_t;
			match(_t,DOT);
			_t = _t.getFirstChild();
			e1 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			e2 = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t62;
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		case ID:
		{
			i = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			
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
					
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return result;
	}
	
	public final void arguments(AST _t) throws RecognitionException {
		
		AST arguments_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		Register r = null; realArgs = new Vector<Register>();
		
		AST __t66 = _t;
		AST tmp44_AST_in = (AST)_t;
		match(_t,ARGS);
		_t = _t.getFirstChild();
		{
		_loop68:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_tokenSet_1.member(_t.getType()))) {
				r=expr(_t);
				_t = _retTree;
				
								/*	
								currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
											r, new Immediate(Integer.toString(paramCount))));
								*/
								realArgs.add(r);
								paramCount++;
							
			}
			else {
				break _loop68;
			}
			
		} while (true);
		}
		_t = __t66;
		_t = _t.getNextSibling();
		
					for(int i = 0; i < paramCount; i++){
						currentBlock.addInst(new StoreInstruction(StoreInstruction.StoreOutArgOpCode.STOREOUTARG,
									realArgs.get(i), new Immediate(Integer.toString(i))));
					}
				
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"new\"",
		"\"fun\"",
		"\"while\"",
		"\"return\"",
		"\"print\"",
		"\"read\"",
		"\"struct\"",
		"\"if\"",
		"\"else\"",
		"\"int\"",
		"\"bool\"",
		"\"null\"",
		"\"true\"",
		"\"false\"",
		"\"void\"",
		"\"endl\"",
		"\"delete\"",
		"SEMI",
		"DOT",
		"BANG",
		"EQUAL",
		"COMPAR",
		"AND",
		"OR",
		"MULT",
		"DIV",
		"LT",
		"GT",
		"NE",
		"LTE",
		"GTE",
		"PLUS",
		"MINUS",
		"LPAREN",
		"RPAREN",
		"LCURLY",
		"RCURLY",
		"COMMA",
		"NUM",
		"ID",
		"WS",
		"COMM",
		"ARGS",
		"PROG",
		"BODY",
		"PARAMS",
		"FUNS",
		"DECLS",
		"DECL",
		"NESTED_DECL",
		"TYPES",
		"IDS",
		"NUMS",
		"STMT_LST",
		"THEN",
		"ELS",
		"INVOKE",
		"DO",
		"UNARY",
		"TYPE",
		"TY_DECL",
		"GUARD",
		"FUNCALL"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 1297036692700531648L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 13331557744656L, 4L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	}
	
