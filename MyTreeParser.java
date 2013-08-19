// $ANTLR 2.7.7 (20070330): "evil.g" -> "MyTreeParser.java"$

	import java.io.*;
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


public class MyTreeParser extends antlr.TreeParser       implements MyLexerTokenTypes
 {

	int mainCount = 0;
	boolean type_decl = false;
	String currentStruct = null;
	String arithOpsCheck(String leftOp, String rightOp) throws SemanticException{
			
		if(leftOp.equals(rightOp) && leftOp.equals("int")){
			return new String("int");
		}
		else
		{
			throw new SemanticException("Arithmetic operator needs integer operands.");
		}
	}

	String relOpsCheck(String leftOp, String rightOp) throws SemanticException{

		if(leftOp.equals(rightOp) && leftOp.equals("int")){
			return new String("bool");
		}
		else
		{
			throw new SemanticException("Relational operator needs integer operands.\n Given : " + leftOp );
		}
	}
	
	String boolOpsCheck(String leftOp, String rightOp) throws SemanticException{

		if(leftOp.equals(rightOp) && leftOp.equals("bool")){
			return new String("bool");
		}
		else
		{
			throw new SemanticException("Boolean operator needs boolean operands.");
		}
	}
	
	String equalOpsCheck(String leftOp, String rightOp) throws SemanticException{

		if(!(leftOp.equals("bool") || rightOp.equals("bool"))){
			if(!(rightOp.equals("null"))){

				if(leftOp.equals(rightOp)){	
					return new String("bool");
				}
				else
				{
					throw new SemanticException("Equality requires integer or structure operands.");
		
				}
			}
			else
			{
				if(!(leftOp.equals("int"))){
					return new String("bool");
				}
				else
				{
					throw new SemanticException("Integer operand cannot be compared with null.");
				}

			}
		}
		else
		{
			throw new SemanticException("Equality requires integer or structure operands.");
		}
	}


	//Env globlEnv = new Env(null);
	Env currentEnv = new Env(null);
public MyTreeParser() {
	tokenNames = _tokenNames;
}

	public final void start(AST _t) throws RecognitionException, SemanticException {
		
		AST start_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST t = null;
		AST d = null;
		AST f = null;
		
		AST __t107 = _t;
		AST tmp71_AST_in = (AST)_t;
		match(_t,PROG);
		_t = _t.getFirstChild();
		t = _t==ASTNULL ? null : (AST)_t;
		types(_t);
		_t = _retTree;
		d = _t==ASTNULL ? null : (AST)_t;
		declarations(_t);
		_t = _retTree;
		f = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		_t = __t107;
		_t = _t.getNextSibling();
		
					functions(f);
					if (mainCount == 0)
					{
						throw new SemanticException("Main function not defined. ");
					}
					else if (mainCount > 1)
					{
						throw new SemanticException("Multiple main definition found. ");
					}
				
		_retTree = _t;
	}
	
	public final void types(AST _t) throws RecognitionException {
		
		AST types_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t109 = _t;
		AST tmp72_AST_in = (AST)_t;
		match(_t,TYPES);
		_t = _t.getFirstChild();
		{
		_loop111:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==STRUCT)) {
				type_decl(_t);
				_t = _retTree;
			}
			else {
				break _loop111;
			}
			
		} while (true);
		}
		_t = __t109;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void declarations(AST _t) throws RecognitionException {
		
		AST declarations_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t123 = _t;
		AST tmp73_AST_in = (AST)_t;
		match(_t,DECLS);
		_t = _t.getFirstChild();
		{
		_loop125:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==DECL)) {
				declaration(_t);
				_t = _retTree;
			}
			else {
				break _loop125;
			}
			
		} while (true);
		}
		_t = __t123;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void type_decl(AST _t) throws RecognitionException {
		
		AST type_decl_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST n = null;
		
		AST __t113 = _t;
		AST tmp74_AST_in = (AST)_t;
		match(_t,STRUCT);
		_t = _t.getFirstChild();
		i = (AST)_t;
		match(_t,ID);
		_t = _t.getNextSibling();
		n = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		_t = __t113;
		_t = _t.getNextSibling();
		
					currentEnv.structDecl(i.getText());
					
					type_decl = true;
					currentStruct = i.getText();
					nested_decl(n);
					type_decl = false;
				
		_retTree = _t;
	}
	
	public final void nested_decl(AST _t) throws RecognitionException {
		
		AST nested_decl_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t115 = _t;
		AST tmp75_AST_in = (AST)_t;
		match(_t,NESTED_DECL);
		_t = _t.getFirstChild();
		{
		_loop117:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==TY_DECL)) {
				decl(_t);
				_t = _retTree;
			}
			else {
				break _loop117;
			}
			
		} while (true);
		}
		_t = __t115;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void decl(AST _t) throws RecognitionException {
		
		AST decl_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST t = null;
		AST i = null;
		
		AST __t119 = _t;
		AST tmp76_AST_in = (AST)_t;
		match(_t,TY_DECL);
		_t = _t.getFirstChild();
		t = _t==ASTNULL ? null : (AST)_t;
		type(_t);
		_t = _retTree;
		i = (AST)_t;
		match(_t,ID);
		_t = _t.getNextSibling();
		_t = __t119;
		_t = _t.getNextSibling();
		
					//
					if(type_decl){
						
						if(t.getText().equals("struct"))
						{
							if((currentEnv.structLookUp(t.getFirstChild().getText())) == null)
							{
								throw new SemanticException("Cannot find symbol in decl if : "+ 
										t.getFirstChild().getText());
							}
							else
							{
								currentEnv.fieldDecl(currentStruct, i.getText(), t.getFirstChild().getText());
							}
						}
						else
						{
							currentEnv.fieldDecl(currentStruct, i.getText(), t.getText());
						}
								
					}
					else
					{
						
						if(t.getText().equals("struct"))
						{
							if((currentEnv.structLookUp(t.getFirstChild().getText())) == null)
							{
								throw new SemanticException("Cannot find symbol in decl else: "+ 
										t.getFirstChild().getText());
							}
							else
							{
								currentEnv.paramDecl(i.getText(), t.getFirstChild().getText());
							}
						}
						else
						{
							currentEnv.paramDecl(i.getText(), t.getText());
						}
					}
				
		_retTree = _t;
	}
	
	public final void type(AST _t) throws RecognitionException {
		
		AST type_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case INT:
		{
			AST tmp77_AST_in = (AST)_t;
			match(_t,INT);
			_t = _t.getNextSibling();
			break;
		}
		case BOOL:
		{
			AST tmp78_AST_in = (AST)_t;
			match(_t,BOOL);
			_t = _t.getNextSibling();
			break;
		}
		case STRUCT:
		{
			AST __t121 = _t;
			AST tmp79_AST_in = (AST)_t;
			match(_t,STRUCT);
			_t = _t.getFirstChild();
			AST tmp80_AST_in = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t121;
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
	
	public final void declaration(AST _t) throws RecognitionException {
		
		AST declaration_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST t = null;
		AST i = null;
		
		AST __t127 = _t;
		AST tmp81_AST_in = (AST)_t;
		match(_t,DECL);
		_t = _t.getFirstChild();
		t = _t==ASTNULL ? null : (AST)_t;
		type(_t);
		_t = _retTree;
		AST __t128 = _t;
		AST tmp82_AST_in = (AST)_t;
		match(_t,IDS);
		_t = _t.getFirstChild();
		{
		_loop130:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==ID)) {
				i = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				
								 	if(t.getText().equals("struct"))
									{ 
										if((currentEnv.structLookUp(t.getFirstChild().getText())) == null)
										{
											throw new SemanticException("Cannot find symbol in declaration: "+
												t.getFirstChild().getText());
										}
										else
										{
											currentEnv.varDecl(i.getText(), t.getFirstChild().getText());
										}
									}
									else
									{
										
							 			currentEnv.varDecl(i.getText(), t.getText()); 
									}
								 	//
									
								
			}
			else {
				break _loop130;
			}
			
		} while (true);
		}
		_t = __t128;
		_t = _t.getNextSibling();
		_t = __t127;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void functions(AST _t) throws RecognitionException {
		
		AST functions_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t132 = _t;
		AST tmp83_AST_in = (AST)_t;
		match(_t,FUNS);
		_t = _t.getFirstChild();
		{
		_loop134:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==ID)) {
				function(_t);
				_t = _retTree;
			}
			else {
				break _loop134;
			}
			
		} while (true);
		}
		_t = __t132;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void function(AST _t) throws RecognitionException, SemanticException {
		
		AST function_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST p = null;
		AST r = null;
		AST b = null;
		
		AST __t136 = _t;
		i = _t==ASTNULL ? null :(AST)_t;
		match(_t,ID);
		_t = _t.getFirstChild();
		p = _t==ASTNULL ? null : (AST)_t;
		parameters(_t);
		_t = _retTree;
		r = _t==ASTNULL ? null : (AST)_t;
		return_type(_t);
		_t = _retTree;
		b = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		_t = __t136;
		_t = _t.getNextSibling();
		
					if(r.getText().equals("struct")){
						currentEnv.funDecl(i.getText(), r.getFirstChild().getText());
					}
					else
					{
						currentEnv.funDecl(i.getText(), r.getText());
					}
					currentEnv = new Env(currentEnv);
					//body(b);
					if ((i.getText()).equals("main")){
						
						mainCount++;
						if (!(r.getText().equals("int"))){
							throw new SemanticException("Return value of main has to be int");
						}
						if (!(p.getNumberOfChildren() == 0)){
							throw new SemanticException("Main cannot have an argument");
						}
					}
		
					if(r.getText().equals("struct"))
					{
						currentEnv.funDecl(i.getText(), r.getFirstChild().getText());
					}
					else
					{
						currentEnv.funDecl(i.getText(), r.getText());
						
					}
					body(b);
					currentEnv = currentEnv.pop();
				
		_retTree = _t;
	}
	
	public final void parameters(AST _t) throws RecognitionException {
		
		AST parameters_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t138 = _t;
		AST tmp84_AST_in = (AST)_t;
		match(_t,PARAMS);
		_t = _t.getFirstChild();
		{
		_loop140:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==TY_DECL)) {
				decl(_t);
				_t = _retTree;
			}
			else {
				break _loop140;
			}
			
		} while (true);
		}
		_t = __t138;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void return_type(AST _t) throws RecognitionException {
		
		AST return_type_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case STRUCT:
		case INT:
		case BOOL:
		{
			type(_t);
			_t = _retTree;
			break;
		}
		case VOID:
		{
			AST tmp85_AST_in = (AST)_t;
			match(_t,VOID);
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
	
	public final void body(AST _t) throws RecognitionException {
		
		AST body_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST d = null;
		AST s = null;
		
		AST __t143 = _t;
		AST tmp86_AST_in = (AST)_t;
		match(_t,BODY);
		_t = _t.getFirstChild();
		d = _t==ASTNULL ? null : (AST)_t;
		declarations(_t);
		_t = _retTree;
		s = (AST)_t;
		if ( _t==null ) throw new MismatchedTokenException();
		_t = _t.getNextSibling();
		_t = __t143;
		_t = _t.getNextSibling();
		
					statement_list(s);
				
		_retTree = _t;
	}
	
	public final void statement_list(AST _t) throws RecognitionException {
		
		AST statement_list_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t145 = _t;
		AST tmp87_AST_in = (AST)_t;
		match(_t,STMT_LST);
		_t = _t.getFirstChild();
		{
		_loop147:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_tokenSet_0.member(_t.getType()))) {
				statement(_t);
				_t = _retTree;
			}
			else {
				break _loop147;
			}
			
		} while (true);
		}
		_t = __t145;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void statement(AST _t) throws RecognitionException {
		
		AST statement_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		String l, r; l = r = "";
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case EQUAL:
		{
			AST __t149 = _t;
			AST tmp88_AST_in = (AST)_t;
			match(_t,EQUAL);
			_t = _t.getFirstChild();
			l=lvalue(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t149;
			_t = _t.getNextSibling();
			
						if(!(l.equals(r))){
							if(!(r.equals("null"))){
								throw new SemanticException("Type mismatch. " + "expected: "+ l + ". got: " + r + ".");
							}
						}
					
			break;
		}
		case PRINT:
		{
			AST __t150 = _t;
			AST tmp89_AST_in = (AST)_t;
			match(_t,PRINT);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ENDL:
			{
				AST tmp90_AST_in = (AST)_t;
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
			_t = __t150;
			_t = _t.getNextSibling();
			
						if(!(l.equals("int"))){
							throw new SemanticException("Print statement requires integer operand.");
						}
					
			break;
		}
		case READ:
		{
			AST __t152 = _t;
			AST tmp91_AST_in = (AST)_t;
			match(_t,READ);
			_t = _t.getFirstChild();
			l=lvalue(_t);
			_t = _retTree;
			_t = __t152;
			_t = _t.getNextSibling();
			
						if(!(l.equals("int"))){
							throw new SemanticException("Read statement requires integer operand.");
						}
					
			break;
		}
		case IF:
		{
			AST __t153 = _t;
			AST tmp92_AST_in = (AST)_t;
			match(_t,IF);
			_t = _t.getFirstChild();
			AST __t154 = _t;
			AST tmp93_AST_in = (AST)_t;
			match(_t,GUARD);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			_t = __t154;
			_t = _t.getNextSibling();
			AST __t155 = _t;
			AST tmp94_AST_in = (AST)_t;
			match(_t,THEN);
			_t = _t.getFirstChild();
			block(_t);
			_t = _retTree;
			_t = __t155;
			_t = _t.getNextSibling();
			AST __t156 = _t;
			AST tmp95_AST_in = (AST)_t;
			match(_t,ELS);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case STMT_LST:
			{
				block(_t);
				_t = _retTree;
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
			_t = __t156;
			_t = _t.getNextSibling();
			_t = __t153;
			_t = _t.getNextSibling();
			
						if(!(l.equals("bool"))){
							throw new SemanticException("If statement requires boolean guard.");
						}
					
			break;
		}
		case WHILE:
		{
			AST __t158 = _t;
			AST tmp96_AST_in = (AST)_t;
			match(_t,WHILE);
			_t = _t.getFirstChild();
			AST __t159 = _t;
			AST tmp97_AST_in = (AST)_t;
			match(_t,GUARD);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			_t = __t159;
			_t = _t.getNextSibling();
			AST __t160 = _t;
			AST tmp98_AST_in = (AST)_t;
			match(_t,DO);
			_t = _t.getFirstChild();
			block(_t);
			_t = _retTree;
			_t = __t160;
			_t = _t.getNextSibling();
			_t = __t158;
			_t = _t.getNextSibling();
			
						if(!(l.equals("bool"))){
							throw new SemanticException("While loop requires boolean guard.");
						}
					
			break;
		}
		case RETURN:
		{
			AST __t161 = _t;
			AST tmp99_AST_in = (AST)_t;
			match(_t,RETURN);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NEW:
			case NULL:
			case TRUE:
			case FALSE:
			case DOT:
			case BANG:
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
			case NUM:
			case ID:
			case FUNCALL:
			{
				l=expr(_t);
				_t = _retTree;
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
			_t = __t161;
			_t = _t.getNextSibling();
			break;
		}
		case DELETE:
		{
			AST __t163 = _t;
			AST tmp100_AST_in = (AST)_t;
			match(_t,DELETE);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			_t = __t163;
			_t = _t.getNextSibling();
			break;
		}
		case INVOKE:
		{
			AST __t164 = _t;
			AST tmp101_AST_in = (AST)_t;
			match(_t,INVOKE);
			_t = _t.getFirstChild();
			i = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			arguments(_t);
			_t = _retTree;
			_t = __t164;
			_t = _t.getNextSibling();
			
						if(currentEnv.pop().funLookUp(i.getText()) == null){
							throw new SemanticException("Cannot find symbol : " +i.getText());
						}
					
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
	}
	
	public final String  lvalue(AST _t) throws RecognitionException, SemanticException {
		String t = "";
		
		AST lvalue_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST d = null;
		AST lvalr = null;
		AST i = null;
		String l, r; l = r = "";
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case DOT:
		{
			AST __t167 = _t;
			d = _t==ASTNULL ? null :(AST)_t;
			match(_t,DOT);
			_t = _t.getFirstChild();
			l=lvalue(_t);
			_t = _retTree;
			lvalr = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t167;
			_t = _t.getNextSibling();
			
						
						
						t = currentEnv.pop().fieldTypeLookUp(l, lvalr.getText());
						
					
					
			break;
		}
		case ID:
		{
			i = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			t = currentEnv.varLookUp(i.getText());
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return t;
	}
	
	public final String  expr(AST _t) throws RecognitionException, SemanticException {
		String t = "";
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST p = null;
		AST m = null;
		AST rexpr = null;
		AST fid = null;
		AST newID = null;
		String l, r; l = r = "";
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case ID:
		{
			i = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			
						t = currentEnv.varLookUp(i.getText()); 
						if(t == null){
							throw new SemanticException("Cannot find symbol : " + i.getText());
						}
					
			break;
		}
		case PLUS:
		{
			AST __t173 = _t;
			p = _t==ASTNULL ? null :(AST)_t;
			match(_t,PLUS);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t173;
			_t = _t.getNextSibling();
			
						t = arithOpsCheck(l, r);
					
			break;
		}
		case MINUS:
		{
			AST __t174 = _t;
			m = _t==ASTNULL ? null :(AST)_t;
			match(_t,MINUS);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NEW:
			case NULL:
			case TRUE:
			case FALSE:
			case DOT:
			case BANG:
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
			case NUM:
			case ID:
			case FUNCALL:
			{
				r=expr(_t);
				_t = _retTree;
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
			_t = __t174;
			_t = _t.getNextSibling();
			
					        if(m.getNumberOfChildren() > 1){	
							t = arithOpsCheck(l, r);
						}
						else
						{
							t = l;
						}	
					
			break;
		}
		case MULT:
		{
			AST __t176 = _t;
			AST tmp102_AST_in = (AST)_t;
			match(_t,MULT);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t176;
			_t = _t.getNextSibling();
			
						t = arithOpsCheck(l, r);
					
			break;
		}
		case DIV:
		{
			AST __t177 = _t;
			AST tmp103_AST_in = (AST)_t;
			match(_t,DIV);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t177;
			_t = _t.getNextSibling();
			
						t = arithOpsCheck(l, r);
					
			break;
		}
		case AND:
		{
			AST __t178 = _t;
			AST tmp104_AST_in = (AST)_t;
			match(_t,AND);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t178;
			_t = _t.getNextSibling();
				
						t = boolOpsCheck(l, r);
					
			break;
		}
		case OR:
		{
			AST __t179 = _t;
			AST tmp105_AST_in = (AST)_t;
			match(_t,OR);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t179;
			_t = _t.getNextSibling();
				
						t = boolOpsCheck(l, r);
					
			break;
		}
		case COMPAR:
		{
			AST __t180 = _t;
			AST tmp106_AST_in = (AST)_t;
			match(_t,COMPAR);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t180;
			_t = _t.getNextSibling();
				
						t = equalOpsCheck(l, r);
					
			break;
		}
		case LT:
		{
			AST __t181 = _t;
			AST tmp107_AST_in = (AST)_t;
			match(_t,LT);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t181;
			_t = _t.getNextSibling();
			
						t = relOpsCheck(l,r);
					
			break;
		}
		case GT:
		{
			AST __t182 = _t;
			AST tmp108_AST_in = (AST)_t;
			match(_t,GT);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t182;
			_t = _t.getNextSibling();
			
						t = relOpsCheck(l,r);
					
			break;
		}
		case GTE:
		{
			AST __t183 = _t;
			AST tmp109_AST_in = (AST)_t;
			match(_t,GTE);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t183;
			_t = _t.getNextSibling();
			
						t = relOpsCheck(l,r);
					
			break;
		}
		case LTE:
		{
			AST __t184 = _t;
			AST tmp110_AST_in = (AST)_t;
			match(_t,LTE);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t184;
			_t = _t.getNextSibling();
			
						t = relOpsCheck(l,r);
					
			break;
		}
		case NE:
		{
			AST __t185 = _t;
			AST tmp111_AST_in = (AST)_t;
			match(_t,NE);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			r=expr(_t);
			_t = _retTree;
			_t = __t185;
			_t = _t.getNextSibling();
			
						t = equalOpsCheck(l,r);
					
			break;
		}
		case BANG:
		{
			AST __t186 = _t;
			AST tmp112_AST_in = (AST)_t;
			match(_t,BANG);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			_t = __t186;
			_t = _t.getNextSibling();
			
						t = l;
					
			break;
		}
		case DOT:
		{
			AST __t187 = _t;
			AST tmp113_AST_in = (AST)_t;
			match(_t,DOT);
			_t = _t.getFirstChild();
			l=expr(_t);
			_t = _retTree;
			rexpr = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t187;
			_t = _t.getNextSibling();
			
						t = currentEnv.pop().fieldTypeLookUp(l, rexpr.getText());
					
			break;
		}
		case FUNCALL:
		{
			AST __t188 = _t;
			AST tmp114_AST_in = (AST)_t;
			match(_t,FUNCALL);
			_t = _t.getFirstChild();
			fid = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			arguments(_t);
			_t = _retTree;
			_t = __t188;
			_t = _t.getNextSibling();
			
						t = currentEnv.pop().funLookUp(fid.getText());
					
			break;
		}
		case NEW:
		{
			AST __t189 = _t;
			AST tmp115_AST_in = (AST)_t;
			match(_t,NEW);
			_t = _t.getFirstChild();
			newID = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t189;
			_t = _t.getNextSibling();
			
						t = currentEnv.pop().structLookUp(newID.getText()); 
					
			break;
		}
		case NULL:
		{
			AST tmp116_AST_in = (AST)_t;
			match(_t,NULL);
			_t = _t.getNextSibling();
			t = "null";
			break;
		}
		case NUM:
		{
			AST tmp117_AST_in = (AST)_t;
			match(_t,NUM);
			_t = _t.getNextSibling();
			t = "int";
			break;
		}
		case TRUE:
		{
			AST tmp118_AST_in = (AST)_t;
			match(_t,TRUE);
			_t = _t.getNextSibling();
			t = "bool";
			break;
		}
		case FALSE:
		{
			AST tmp119_AST_in = (AST)_t;
			match(_t,FALSE);
			_t = _t.getNextSibling();
			t = "bool";
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return t;
	}
	
	public final void block(AST _t) throws RecognitionException {
		
		AST block_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		statement_list(_t);
		_t = _retTree;
		_retTree = _t;
	}
	
	public final void arguments(AST _t) throws RecognitionException {
		
		AST arguments_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		String e = "";
		
		AST __t169 = _t;
		AST tmp120_AST_in = (AST)_t;
		match(_t,ARGS);
		_t = _t.getFirstChild();
		{
		_loop171:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_tokenSet_1.member(_t.getType()))) {
				e=expr(_t);
				_t = _retTree;
			}
			else {
				break _loop171;
			}
			
		} while (true);
		}
		_t = __t169;
		_t = _t.getNextSibling();
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
		long[] data = { 1152921504624675776L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 13331557744656L, 4L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	}
	
