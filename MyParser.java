// $ANTLR 2.7.7 (20070330): "evil.g" -> "MyParser.java"$

	import java.io.*;
	import antlr.*;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class MyParser extends antlr.LLkParser       implements MyLexerTokenTypes
 {

protected MyParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public MyParser(TokenBuffer tokenBuf) {
  this(tokenBuf,3);
}

protected MyParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public MyParser(TokenStream lexer) {
  this(lexer,3);
}

public MyParser(ParserSharedInputState state) {
  super(state,3);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void prog() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST prog_AST = null;
		AST t_AST = null;
		AST d_AST = null;
		AST f_AST = null;
		
		types();
		t_AST = (AST)returnAST;
		declarations();
		d_AST = (AST)returnAST;
		functions();
		f_AST = (AST)returnAST;
		prog_AST = (AST)currentAST.root;
		prog_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(PROG,"PROG")).add(t_AST).add(d_AST).add(f_AST));
		currentAST.root = prog_AST;
		currentAST.child = prog_AST!=null &&prog_AST.getFirstChild()!=null ?
			prog_AST.getFirstChild() : prog_AST;
		currentAST.advanceChildToEnd();
		returnAST = prog_AST;
	}
	
	public final void types() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST types_AST = null;
		
		{
		_loop40:
		do {
			if ((LA(1)==STRUCT) && (LA(2)==ID) && (LA(3)==LCURLY)) {
				type_decl();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop40;
			}
			
		} while (true);
		}
		types_AST = (AST)currentAST.root;
		types_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TYPES,"TYPES")).add(types_AST));
		currentAST.root = types_AST;
		currentAST.child = types_AST!=null &&types_AST.getFirstChild()!=null ?
			types_AST.getFirstChild() : types_AST;
		currentAST.advanceChildToEnd();
		types_AST = (AST)currentAST.root;
		returnAST = types_AST;
	}
	
	public final void declarations() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST declarations_AST = null;
		
		{
		_loop49:
		do {
			if ((LA(1)==STRUCT||LA(1)==INT||LA(1)==BOOL)) {
				declaration();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop49;
			}
			
		} while (true);
		}
		declarations_AST = (AST)currentAST.root;
		declarations_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DECLS,"DECLS")).add(declarations_AST));
		currentAST.root = declarations_AST;
		currentAST.child = declarations_AST!=null &&declarations_AST.getFirstChild()!=null ?
			declarations_AST.getFirstChild() : declarations_AST;
		currentAST.advanceChildToEnd();
		declarations_AST = (AST)currentAST.root;
		returnAST = declarations_AST;
	}
	
	public final void functions() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST functions_AST = null;
		
		{
		_loop56:
		do {
			if ((LA(1)==FUN)) {
				function();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop56;
			}
			
		} while (true);
		}
		functions_AST = (AST)currentAST.root;
		functions_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNS,"FUNS")).add(functions_AST));
		currentAST.root = functions_AST;
		currentAST.child = functions_AST!=null &&functions_AST.getFirstChild()!=null ?
			functions_AST.getFirstChild() : functions_AST;
		currentAST.advanceChildToEnd();
		functions_AST = (AST)currentAST.root;
		returnAST = functions_AST;
	}
	
	public final void type_decl() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST type_decl_AST = null;
		Token  i = null;
		AST i_AST = null;
		AST nd_AST = null;
		
		AST tmp1_AST = null;
		tmp1_AST = astFactory.create(LT(1));
		match(STRUCT);
		i = LT(1);
		i_AST = astFactory.create(i);
		match(ID);
		match(LCURLY);
		nested_decl();
		nd_AST = (AST)returnAST;
		match(RCURLY);
		match(SEMI);
		type_decl_AST = (AST)currentAST.root;
		type_decl_AST = (AST)astFactory.make( (new ASTArray(3)).add(tmp1_AST).add(i_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NESTED_DECL,"NSTDECL")).add(nd_AST))));
		currentAST.root = type_decl_AST;
		currentAST.child = type_decl_AST!=null &&type_decl_AST.getFirstChild()!=null ?
			type_decl_AST.getFirstChild() : type_decl_AST;
		currentAST.advanceChildToEnd();
		returnAST = type_decl_AST;
	}
	
	public final void nested_decl() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST nested_decl_AST = null;
		
		decl();
		astFactory.addASTChild(currentAST, returnAST);
		match(SEMI);
		{
		_loop44:
		do {
			if ((LA(1)==STRUCT||LA(1)==INT||LA(1)==BOOL)) {
				decl();
				astFactory.addASTChild(currentAST, returnAST);
				match(SEMI);
			}
			else {
				break _loop44;
			}
			
		} while (true);
		}
		nested_decl_AST = (AST)currentAST.root;
		returnAST = nested_decl_AST;
	}
	
	public final void decl() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST decl_AST = null;
		AST t_AST = null;
		Token  i = null;
		AST i_AST = null;
		
		type();
		t_AST = (AST)returnAST;
		i = LT(1);
		i_AST = astFactory.create(i);
		match(ID);
		decl_AST = (AST)currentAST.root;
		decl_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(TY_DECL,"TY_DECL")).add(t_AST).add(i_AST));
		currentAST.root = decl_AST;
		currentAST.child = decl_AST!=null &&decl_AST.getFirstChild()!=null ?
			decl_AST.getFirstChild() : decl_AST;
		currentAST.advanceChildToEnd();
		returnAST = decl_AST;
	}
	
	public final void type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST type_AST = null;
		Token  i = null;
		AST i_AST = null;
		Token  b = null;
		AST b_AST = null;
		Token  s = null;
		AST s_AST = null;
		Token  id = null;
		AST id_AST = null;
		
		switch ( LA(1)) {
		case INT:
		{
			i = LT(1);
			i_AST = astFactory.create(i);
			astFactory.addASTChild(currentAST, i_AST);
			match(INT);
			type_AST = (AST)currentAST.root;
			break;
		}
		case BOOL:
		{
			b = LT(1);
			b_AST = astFactory.create(b);
			astFactory.addASTChild(currentAST, b_AST);
			match(BOOL);
			type_AST = (AST)currentAST.root;
			break;
		}
		case STRUCT:
		{
			s = LT(1);
			s_AST = astFactory.create(s);
			astFactory.makeASTRoot(currentAST, s_AST);
			match(STRUCT);
			id = LT(1);
			id_AST = astFactory.create(id);
			astFactory.addASTChild(currentAST, id_AST);
			match(ID);
			type_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = type_AST;
	}
	
	public final void declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST declaration_AST = null;
		AST t_AST = null;
		AST il_AST = null;
		
		type();
		t_AST = (AST)returnAST;
		id_list();
		il_AST = (AST)returnAST;
		match(SEMI);
		declaration_AST = (AST)currentAST.root;
		declaration_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(DECL,"DECL")).add(t_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IDS,"IDS")).add(il_AST))));
		currentAST.root = declaration_AST;
		currentAST.child = declaration_AST!=null &&declaration_AST.getFirstChild()!=null ?
			declaration_AST.getFirstChild() : declaration_AST;
		currentAST.advanceChildToEnd();
		returnAST = declaration_AST;
	}
	
	public final void id_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST id_list_AST = null;
		
		AST tmp8_AST = null;
		tmp8_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp8_AST);
		match(ID);
		{
		_loop53:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				AST tmp10_AST = null;
				tmp10_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp10_AST);
				match(ID);
			}
			else {
				break _loop53;
			}
			
		} while (true);
		}
		id_list_AST = (AST)currentAST.root;
		returnAST = id_list_AST;
	}
	
	public final void function() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_AST = null;
		Token  i = null;
		AST i_AST = null;
		AST p_AST = null;
		AST rt_AST = null;
		AST ds_AST = null;
		AST sl_AST = null;
		
		AST tmp11_AST = null;
		tmp11_AST = astFactory.create(LT(1));
		match(FUN);
		i = LT(1);
		i_AST = astFactory.create(i);
		match(ID);
		parameters();
		p_AST = (AST)returnAST;
		return_type();
		rt_AST = (AST)returnAST;
		match(LCURLY);
		declarations();
		ds_AST = (AST)returnAST;
		statement_list();
		sl_AST = (AST)returnAST;
		match(RCURLY);
		function_AST = (AST)currentAST.root;
		function_AST = (AST)astFactory.make( (new ASTArray(4)).add(i_AST).add(p_AST).add(rt_AST).add((AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(BODY,"BODY")).add(ds_AST).add(sl_AST))));
		currentAST.root = function_AST;
		currentAST.child = function_AST!=null &&function_AST.getFirstChild()!=null ?
			function_AST.getFirstChild() : function_AST;
		currentAST.advanceChildToEnd();
		returnAST = function_AST;
	}
	
	public final void parameters() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameters_AST = null;
		
		match(LPAREN);
		{
		switch ( LA(1)) {
		case STRUCT:
		case INT:
		case BOOL:
		{
			decl();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop61:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					decl();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop61;
				}
				
			} while (true);
			}
			break;
		}
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAREN);
		parameters_AST = (AST)currentAST.root;
		parameters_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PARAMS,"PARAMS")).add(parameters_AST));
		currentAST.root = parameters_AST;
		currentAST.child = parameters_AST!=null &&parameters_AST.getFirstChild()!=null ?
			parameters_AST.getFirstChild() : parameters_AST;
		currentAST.advanceChildToEnd();
		parameters_AST = (AST)currentAST.root;
		returnAST = parameters_AST;
	}
	
	public final void return_type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST return_type_AST = null;
		
		switch ( LA(1)) {
		case STRUCT:
		case INT:
		case BOOL:
		{
			type();
			astFactory.addASTChild(currentAST, returnAST);
			return_type_AST = (AST)currentAST.root;
			break;
		}
		case VOID:
		{
			AST tmp17_AST = null;
			tmp17_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp17_AST);
			match(VOID);
			return_type_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = return_type_AST;
	}
	
	public final void statement_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_list_AST = null;
		
		{
		_loop67:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				statement();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop67;
			}
			
		} while (true);
		}
		statement_list_AST = (AST)currentAST.root;
		statement_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(STMT_LST,"STMT_LST")).add(statement_list_AST));
		currentAST.root = statement_list_AST;
		currentAST.child = statement_list_AST!=null &&statement_list_AST.getFirstChild()!=null ?
			statement_list_AST.getFirstChild() : statement_list_AST;
		currentAST.advanceChildToEnd();
		statement_list_AST = (AST)currentAST.root;
		returnAST = statement_list_AST;
	}
	
	public final void statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_AST = null;
		
		switch ( LA(1)) {
		case LCURLY:
		{
			block();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case PRINT:
		{
			print();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case READ:
		{
			read();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case IF:
		{
			conditional();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case WHILE:
		{
			loop();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case DELETE:
		{
			delete();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case RETURN:
		{
			ret();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		default:
			if ((LA(1)==ID) && (LA(2)==DOT||LA(2)==EQUAL)) {
				assignment();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==ID) && (LA(2)==LPAREN)) {
				invocation();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (AST)currentAST.root;
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = statement_AST;
	}
	
	public final void block() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_AST = null;
		
		match(LCURLY);
		statement_list();
		astFactory.addASTChild(currentAST, returnAST);
		match(RCURLY);
		block_AST = (AST)currentAST.root;
		returnAST = block_AST;
	}
	
	public final void assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assignment_AST = null;
		
		lvalue();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp20_AST = null;
		tmp20_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp20_AST);
		match(EQUAL);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		match(SEMI);
		assignment_AST = (AST)currentAST.root;
		returnAST = assignment_AST;
	}
	
	public final void print() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST print_AST = null;
		
		AST tmp22_AST = null;
		tmp22_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp22_AST);
		match(PRINT);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case ENDL:
		{
			AST tmp23_AST = null;
			tmp23_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp23_AST);
			match(ENDL);
			break;
		}
		case SEMI:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(SEMI);
		print_AST = (AST)currentAST.root;
		returnAST = print_AST;
	}
	
	public final void read() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST read_AST = null;
		
		AST tmp25_AST = null;
		tmp25_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp25_AST);
		match(READ);
		lvalue();
		astFactory.addASTChild(currentAST, returnAST);
		match(SEMI);
		read_AST = (AST)currentAST.root;
		returnAST = read_AST;
	}
	
	public final void conditional() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditional_AST = null;
		AST e_AST = null;
		AST b_AST = null;
		AST alt_AST = null;
		
		AST tmp27_AST = null;
		tmp27_AST = astFactory.create(LT(1));
		match(IF);
		match(LPAREN);
		expression();
		e_AST = (AST)returnAST;
		match(RPAREN);
		block();
		b_AST = (AST)returnAST;
		{
		switch ( LA(1)) {
		case ELSE:
		{
			match(ELSE);
			block();
			alt_AST = (AST)returnAST;
			break;
		}
		case WHILE:
		case RETURN:
		case PRINT:
		case READ:
		case IF:
		case DELETE:
		case LCURLY:
		case RCURLY:
		case ID:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		conditional_AST = (AST)currentAST.root;
		conditional_AST = (AST)astFactory.make( (new ASTArray(4)).add(tmp27_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GUARD,"GUARD")).add(e_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(THEN,"THEN")).add(b_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ELS,"ELSE")).add(alt_AST))));
		currentAST.root = conditional_AST;
		currentAST.child = conditional_AST!=null &&conditional_AST.getFirstChild()!=null ?
			conditional_AST.getFirstChild() : conditional_AST;
		currentAST.advanceChildToEnd();
		returnAST = conditional_AST;
	}
	
	public final void loop() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST loop_AST = null;
		AST e_AST = null;
		AST b_AST = null;
		
		AST tmp31_AST = null;
		tmp31_AST = astFactory.create(LT(1));
		match(WHILE);
		match(LPAREN);
		expression();
		e_AST = (AST)returnAST;
		match(RPAREN);
		block();
		b_AST = (AST)returnAST;
		loop_AST = (AST)currentAST.root;
		loop_AST = (AST)astFactory.make( (new ASTArray(3)).add(tmp31_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GUARD,"GUARD")).add(e_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DO,"DO")).add(b_AST))));
		currentAST.root = loop_AST;
		currentAST.child = loop_AST!=null &&loop_AST.getFirstChild()!=null ?
			loop_AST.getFirstChild() : loop_AST;
		currentAST.advanceChildToEnd();
		returnAST = loop_AST;
	}
	
	public final void delete() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delete_AST = null;
		
		AST tmp34_AST = null;
		tmp34_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp34_AST);
		match(DELETE);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		match(SEMI);
		delete_AST = (AST)currentAST.root;
		returnAST = delete_AST;
	}
	
	public final void ret() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ret_AST = null;
		
		AST tmp36_AST = null;
		tmp36_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp36_AST);
		match(RETURN);
		{
		switch ( LA(1)) {
		case NEW:
		case NULL:
		case TRUE:
		case FALSE:
		case BANG:
		case MINUS:
		case LPAREN:
		case NUM:
		case ID:
		{
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case SEMI:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(SEMI);
		ret_AST = (AST)currentAST.root;
		returnAST = ret_AST;
	}
	
	public final void invocation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST invocation_AST = null;
		AST a_AST = null;
		
		AST tmp38_AST = null;
		tmp38_AST = astFactory.create(LT(1));
		match(ID);
		arguments();
		a_AST = (AST)returnAST;
		match(SEMI);
		invocation_AST = (AST)currentAST.root;
		invocation_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(INVOKE,"INVOKE")).add(tmp38_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGS,"ARGS")).add(a_AST))));
		currentAST.root = invocation_AST;
		currentAST.child = invocation_AST!=null &&invocation_AST.getFirstChild()!=null ?
			invocation_AST.getFirstChild() : invocation_AST;
		currentAST.advanceChildToEnd();
		returnAST = invocation_AST;
	}
	
	public final void lvalue() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST lvalue_AST = null;
		
		AST tmp40_AST = null;
		tmp40_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp40_AST);
		match(ID);
		{
		_loop81:
		do {
			if ((LA(1)==DOT)) {
				AST tmp41_AST = null;
				tmp41_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp41_AST);
				match(DOT);
				AST tmp42_AST = null;
				tmp42_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp42_AST);
				match(ID);
			}
			else {
				break _loop81;
			}
			
		} while (true);
		}
		lvalue_AST = (AST)currentAST.root;
		returnAST = lvalue_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		boolterm();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop85:
		do {
			if ((LA(1)==AND||LA(1)==OR)) {
				{
				switch ( LA(1)) {
				case AND:
				{
					AST tmp43_AST = null;
					tmp43_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp43_AST);
					match(AND);
					break;
				}
				case OR:
				{
					AST tmp44_AST = null;
					tmp44_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp44_AST);
					match(OR);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				boolterm();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop85;
			}
			
		} while (true);
		}
		expression_AST = (AST)currentAST.root;
		returnAST = expression_AST;
	}
	
	public final void arguments() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arguments_AST = null;
		
		match(LPAREN);
		{
		switch ( LA(1)) {
		case NEW:
		case NULL:
		case TRUE:
		case FALSE:
		case BANG:
		case MINUS:
		case LPAREN:
		case NUM:
		case ID:
		{
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop105:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop105;
				}
				
			} while (true);
			}
			break;
		}
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAREN);
		arguments_AST = (AST)currentAST.root;
		returnAST = arguments_AST;
	}
	
	public final void boolterm() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST boolterm_AST = null;
		
		simple();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case COMPAR:
		case LT:
		case GT:
		case NE:
		case LTE:
		case GTE:
		{
			{
			switch ( LA(1)) {
			case COMPAR:
			{
				AST tmp48_AST = null;
				tmp48_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp48_AST);
				match(COMPAR);
				break;
			}
			case LT:
			{
				AST tmp49_AST = null;
				tmp49_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp49_AST);
				match(LT);
				break;
			}
			case GT:
			{
				AST tmp50_AST = null;
				tmp50_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp50_AST);
				match(GT);
				break;
			}
			case NE:
			{
				AST tmp51_AST = null;
				tmp51_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp51_AST);
				match(NE);
				break;
			}
			case LTE:
			{
				AST tmp52_AST = null;
				tmp52_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp52_AST);
				match(LTE);
				break;
			}
			case GTE:
			{
				AST tmp53_AST = null;
				tmp53_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp53_AST);
				match(GTE);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			simple();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case ENDL:
		case SEMI:
		case AND:
		case OR:
		case RPAREN:
		case COMMA:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		boolterm_AST = (AST)currentAST.root;
		returnAST = boolterm_AST;
	}
	
	public final void simple() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_AST = null;
		
		term();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop92:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS)) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					AST tmp54_AST = null;
					tmp54_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp54_AST);
					match(PLUS);
					break;
				}
				case MINUS:
				{
					AST tmp55_AST = null;
					tmp55_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp55_AST);
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				term();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop92;
			}
			
		} while (true);
		}
		simple_AST = (AST)currentAST.root;
		returnAST = simple_AST;
	}
	
	public final void term() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST term_AST = null;
		
		unary();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop96:
		do {
			if ((LA(1)==MULT||LA(1)==DIV)) {
				{
				switch ( LA(1)) {
				case MULT:
				{
					AST tmp56_AST = null;
					tmp56_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp56_AST);
					match(MULT);
					break;
				}
				case DIV:
				{
					AST tmp57_AST = null;
					tmp57_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp57_AST);
					match(DIV);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				unary();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop96;
			}
			
		} while (true);
		}
		term_AST = (AST)currentAST.root;
		returnAST = term_AST;
	}
	
	public final void unary() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unary_AST = null;
		
		switch ( LA(1)) {
		case BANG:
		{
			AST tmp58_AST = null;
			tmp58_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp58_AST);
			match(BANG);
			unary();
			astFactory.addASTChild(currentAST, returnAST);
			unary_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp59_AST = null;
			tmp59_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp59_AST);
			match(MINUS);
			unary();
			astFactory.addASTChild(currentAST, returnAST);
			unary_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		case NULL:
		case TRUE:
		case FALSE:
		case LPAREN:
		case NUM:
		case ID:
		{
			selector();
			astFactory.addASTChild(currentAST, returnAST);
			unary_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = unary_AST;
	}
	
	public final void selector() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selector_AST = null;
		
		factor();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop100:
		do {
			if ((LA(1)==DOT)) {
				AST tmp60_AST = null;
				tmp60_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp60_AST);
				match(DOT);
				AST tmp61_AST = null;
				tmp61_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp61_AST);
				match(ID);
			}
			else {
				break _loop100;
			}
			
		} while (true);
		}
		selector_AST = (AST)currentAST.root;
		returnAST = selector_AST;
	}
	
	public final void factor() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST factor_AST = null;
		Token  i = null;
		AST i_AST = null;
		AST a_AST = null;
		
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case NUM:
		{
			AST tmp64_AST = null;
			tmp64_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp64_AST);
			match(NUM);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		{
			AST tmp65_AST = null;
			tmp65_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp65_AST);
			match(TRUE);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case FALSE:
		{
			AST tmp66_AST = null;
			tmp66_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp66_AST);
			match(FALSE);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		{
			AST tmp67_AST = null;
			tmp67_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp67_AST);
			match(NEW);
			AST tmp68_AST = null;
			tmp68_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp68_AST);
			match(ID);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case NULL:
		{
			AST tmp69_AST = null;
			tmp69_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp69_AST);
			match(NULL);
			factor_AST = (AST)currentAST.root;
			break;
		}
		default:
			if ((LA(1)==ID) && (LA(2)==LPAREN)) {
				i = LT(1);
				i_AST = astFactory.create(i);
				match(ID);
				arguments();
				a_AST = (AST)returnAST;
				factor_AST = (AST)currentAST.root;
				factor_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(FUNCALL,"FUNCALL")).add(i_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGS,"ARGS")).add(a_AST))));
				currentAST.root = factor_AST;
				currentAST.child = factor_AST!=null &&factor_AST.getFirstChild()!=null ?
					factor_AST.getFirstChild() : factor_AST;
				currentAST.advanceChildToEnd();
			}
			else if ((LA(1)==ID) && (_tokenSet_1.member(LA(2)))) {
				AST tmp70_AST = null;
				tmp70_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp70_AST);
				match(ID);
				factor_AST = (AST)currentAST.root;
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = factor_AST;
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
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 9345849887680L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2611313377280L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	
	}
