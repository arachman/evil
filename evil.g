header
{
	import java.io.*;
	import antlr.*;
}

class MyLexer extends Lexer;
options
{
	k = 2;
	charVocabulary='\u0000'..'\u007F';
	defaultErrorHandler=false;
}

tokens
{
	NEW	= "new";
	FUN	= "fun";
	WHILE	= "while";
	RETURN	= "return";
	PRINT	= "print";
	READ	= "read";
	STRUCT	= "struct";
	IF	= "if";
	ELSE	= "else";
	INT	= "int";
	BOOL	= "bool";
	NULL	= "null";
	TRUE	= "true";
	FALSE	= "false";
	VOID	= "void";
	ENDL	= "endl";
	DELETE	= "delete";

}

SEMI		: ';';
DOT		: '.';
BANG		: '!';
EQUAL		: '=';
COMPAR		: "==";
AND		: "&&";
OR		: "||";
MULT		: '*';
DIV		: '/';
LT		: '<';
GT		: '>';
NE		: "!=";
LTE		: "<=";
GTE		: ">=";
PLUS		: '+';
MINUS		: '-';
LPAREN		: '(';
RPAREN		: ')';
LCURLY		: '{';
RCURLY		: '}';
COMMA		: ',';
NUM		: ('0'..'9')+;
ID options {testLiterals=true;}
		: ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')*; //('a'..'z' | 'A'..'Z')+;
WS		: ( 	' '
	    	  | '\t' 
		  | '\f' 
		  | ( options {generateAmbigWarnings=false;} 
		    : "\r\n"	// Evil DOS 
		    | '\r'	// Macintosh 
		    | '\n'	// Unix
	//	    | ('#')(~'\n')('\n')*
		    ) 
		    {newline();} 
		  )+ 
		  { $setType(Token.SKIP); }
	  	;
COMM		: ('#')(~'\n')*
			{$setType(Token.SKIP); }
		;

class MyParser extends Parser;
options
{	
	k = 3;
	buildAST=true;
	defaultErrorHandler=false;
}

tokens
{
	ARGS;
	PROG;
	BODY;
	PARAMS;
	FUNS;
	DECLS;
	DECL;
	NESTED_DECL;
	TYPES;	
	IDS;
	NUMS;
	STMT_LST;
	THEN;
	ELS;
	INVOKE;
	DO;
	UNARY;
	TYPE;
	TY_DECL;
	GUARD;
	FUNCALL;
}

prog
	:! t:types d:declarations f:functions
		{ #prog = #([PROG, "PROG"], #t, #d, #f); }
	;
types 	
	: (type_decl)*
		{ #types = #([TYPES, "TYPES"], #types); }
	;
type_decl!
	: STRUCT i:ID LCURLY! nd:nested_decl RCURLY! SEMI!
		{ #type_decl = #(STRUCT, #i, #([NESTED_DECL, "NSTDECL"], #nd)); }
	;
nested_decl
	: decl SEMI! (decl SEMI!)*
		//{ #nested_decl = #([NESTED_DECL, "NESTED_DECL"], #nested_decl); }
	;
decl! 	
	: t:type i:ID
		{ #decl = #(#[TY_DECL, "TY_DECL"], #t, #i); }
//	: t:type i:ID (i2:ID)?
	//	{ #decl = #(#t, #i, #i2); }
	;
type	
	: i:INT
//		{#type = #([TYPE, "TYPE"], #i);}
	| b:BOOL
//		{#type = #([TYPE, "TYPE"], #b);}
	| s:STRUCT^ id:ID
//		{#type = #([TYPE, "TYPE"], #s, #id);}
	;
declarations	
	: (declaration)*
		{ #declarations = #([DECLS, "DECLS"], #declarations); }
	;
declaration!
	: t:type il:id_list SEMI!
		{ #declaration = #([DECL, "DECL"], #t, #([IDS, "IDS"], #il)); }
//	: t:type (i:ID)? il:id_list SEMI!
//		{ #declaration = #(#t, #i, #il); } 
	;
id_list
	: ID (COMMA! ID)*
	;
functions
	: (function)*
		{ #functions = #([FUNS, "FUNS"], #functions); }
	;
function
	:! FUN i:ID p:parameters rt:return_type LCURLY! ds:declarations sl:statement_list RCURLY!
		{ #function = #(#i, #p, #rt, #([BODY, "BODY"], #ds, #sl)); }
	;
parameters
	: LPAREN! (decl (COMMA! decl)*)? RPAREN!
		{ #parameters = #([PARAMS, "PARAMS"], #parameters); }	
	;
return_type
	: type
	| VOID
	;
statement
	: block 
	| assignment
	| print
	| read
	| conditional
	| loop
	| delete
	| ret
	| invocation
	;
block 
	: LCURLY! statement_list RCURLY!
	;
statement_list
	: (statement)*
		{ #statement_list = #([STMT_LST, "STMT_LST"], #statement_list); }
	;
assignment
	: lvalue EQUAL^ expression SEMI!
	;
print 
	: PRINT^ expression (ENDL)? SEMI!
	;
read	
	: READ^ lvalue SEMI!
	;
conditional!
	: IF LPAREN! e:expression RPAREN! b:block (ELSE! alt:block)?
		{#conditional = #(IF, #([GUARD, "GUARD"], #e), #([THEN, "THEN"], #b), #([ELS, "ELSE"], #alt)); }
	;
loop!
	: WHILE LPAREN! e:expression RPAREN! b:block
		{#loop = #(WHILE, #([GUARD, "GUARD"], #e), #([DO, "DO"], #b)); }  
	;
delete
	: DELETE^ expression SEMI!
	;
ret	
	: RETURN^ (expression)? SEMI!
	;
invocation!
	: ID a:arguments SEMI!
		{#invocation = #([INVOKE, "INVOKE"], ID,  #(#[ARGS, "ARGS"], #a));} 
	;
lvalue
	: ID (DOT^ ID)*
	;
expression
	: boolterm ((AND^ | OR^) boolterm)*
	;
boolterm
	: simple ((COMPAR^ | LT^ | GT^ | NE^ | LTE^ | GTE^) simple)?
	;
simple
	: term ((PLUS^ | MINUS^) term)*
	;
term 
	: unary ((MULT^ | DIV^) unary)*
	;
unary 
	: BANG^ unary 
	| MINUS^ unary
	//	{ #unary = #([UNARY, "UNARY"], #unary); }
	| selector
	;
selector
	: factor (DOT^ ID)*
	;
factor 
	: LPAREN! expression RPAREN!
	|! i:ID a:arguments
		{#factor = #([FUNCALL, "FUNCALL"], #i, #([ARGS, "ARGS"], #a)); }
	| ID
	| NUM
	| TRUE
	| FALSE
	| NEW^ ID
	| NULL
	;
arguments
	: LPAREN! (expression (COMMA! expression)*)? RPAREN!
	;


class MyTreeParser extends TreeParser;
options {

	defaultErrorHandler=false;
}
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
}

start  throws SemanticException
	: #(PROG t:types d:declarations f:.)
		{
			functions(f);
			if (mainCount == 0)
			{
				throw new SemanticException("Main function not defined. ");
			}
			else if (mainCount > 1)
			{
				throw new SemanticException("Multiple main definition found. ");
			}
		}

	;
types
	: #(TYPES (type_decl)*)
	;
type_decl
	: #(STRUCT i:ID n:.) //nested_decl)
		{
			currentEnv.structDecl(i.getText());
			
			type_decl = true;
			currentStruct = i.getText();
			nested_decl(n);
			type_decl = false;
		}
	;
nested_decl
	: #(NESTED_DECL (decl)*)
	;
decl 
	: #(TY_DECL t:type i:ID) 
		{ 
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
		}
					

	;
type
	: INT
	| BOOL
	| #(STRUCT ID)
	;
declarations
	: #(DECLS (declaration)*)
	;

declaration
	: #(DECL t:type #(IDS 
				(i:ID 
				 {
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
					
				 } )*)
			)
	;
functions 
	: #(FUNS (function)*)
	;
function throws SemanticException
	: #(i:ID p:parameters r:return_type b:.)
	//: #(i:ID p:parameters r:return_type b:body)
		{
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
		}
	;
parameters
	: #(PARAMS (decl)*)
	;
return_type
	: type
	| VOID
	;

body
	: #(BODY d:declarations s:.)
		{
			statement_list(s);
		}
	;
statement_list
	: #(STMT_LST (statement)*)
	;
statement 
{String l, r; l = r = "";}
	: #(EQUAL l=lvalue r=expr) 
		{ 
			if(!(l.equals(r))){
				if(!(r.equals("null"))){
					throw new SemanticException("Type mismatch. " + "expected: "+ l + ". got: " + r + ".");
				}
			}
		}
	| #(PRINT l=expr (ENDL)?)
		{
			if(!(l.equals("int"))){
				throw new SemanticException("Print statement requires integer operand.");
			}
		}
	| #(READ l=lvalue)
		{
			if(!(l.equals("int"))){
				throw new SemanticException("Read statement requires integer operand.");
			}
		}
	| #(IF #(GUARD l=expr) #(THEN block) #(ELS (block)?))
		{
			if(!(l.equals("bool"))){
				throw new SemanticException("If statement requires boolean guard.");
			}
		}
	| #(WHILE #(GUARD l=expr) #(DO block))
		{
			if(!(l.equals("bool"))){
				throw new SemanticException("While loop requires boolean guard.");
			}
		}
	| #(RETURN (l=expr)?)
	| #(DELETE l=expr)
	| #(INVOKE i:ID arguments)
		{
			if(currentEnv.pop().funLookUp(i.getText()) == null){
				throw new SemanticException("Cannot find symbol : " +i.getText());
			}
		}
	;
block 	
	: statement_list
	;
lvalue returns [String t = ""] throws SemanticException
{ String l, r; l = r = ""; }
	//: #(d:DOT (l=lvalue)*) 
	: #(d:DOT l=lvalue lvalr:.) 
		{ 
			
			
			t = currentEnv.pop().fieldTypeLookUp(l, lvalr.getText());
			
		
		}
	| i:ID { t = currentEnv.varLookUp(i.getText()); }
	;
arguments
{ String e = ""; }
	: #(ARGS (e=expr)*)
	;
expr returns [String t = ""] throws SemanticException
{ String l, r; l = r = ""; }
	: i:ID { 
			t = currentEnv.varLookUp(i.getText()); 
			if(t == null){
				throw new SemanticException("Cannot find symbol : " + i.getText());
			}
		}
	| #(p:PLUS l=expr r=expr) 
		{ 
			t = arithOpsCheck(l, r);
		}
	| #(m:MINUS l=expr (r=expr)?)
		{
		        if(m.getNumberOfChildren() > 1){	
				t = arithOpsCheck(l, r);
			}
			else
			{
				t = l;
			}	
		}
	| #(MULT l=expr r=expr) 
		{ 
			t = arithOpsCheck(l, r);
		}
	| #(DIV  l=expr r=expr)
		{ 
			t = arithOpsCheck(l, r);
		}
	| #(AND  l=expr r=expr)
		{ 	
			t = boolOpsCheck(l, r);
		}
	| #(OR l=expr r=expr)
		{ 	
			t = boolOpsCheck(l, r);
		}
	| #(COMPAR l=expr r=expr)
		{ 	
			t = equalOpsCheck(l, r);
		}
	| #(LT l=expr r=expr)	
		{
			t = relOpsCheck(l,r);
		}	
	| #(GT l=expr r=expr)	
		{
			t = relOpsCheck(l,r);
		}	
	| #(GTE l=expr r=expr)	
		{
			t = relOpsCheck(l,r);
		}	
	| #(LTE l=expr r=expr)	
		{
			t = relOpsCheck(l,r);
		}	
	| #(NE l=expr r=expr)	
		{
			t = equalOpsCheck(l,r);
		}	
	| #(BANG l=expr) 
		{
			t = l;
		} 
	| #(DOT l=expr rexpr:.) 
		{
			t = currentEnv.pop().fieldTypeLookUp(l, rexpr.getText());
		}
	| #(FUNCALL fid:ID arguments) 
		{
			t = currentEnv.pop().funLookUp(fid.getText());
		}
	| #(NEW newID:ID)
		{ 
			t = currentEnv.pop().structLookUp(newID.getText()); 
		}
	| NULL { t = "null";}
	| NUM { t = "int"; }
	| TRUE { t = "bool"; }
	| FALSE { t = "bool"; }
	;

