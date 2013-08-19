FILES= *.java	

Evil.class : antlr.generated ${FILES}
	javac *.java

antlr.generated: antlr.generated.evil antlr.generated.cfg
	touch antlr.generated

antlr.generated.evil : evil.g
	java antlr.Tool evil.g
	touch antlr.generated.evil

antlr.generated.cfg : cfg.g
	java antlr.Tool cfg.g
	touch antlr.generated.cfg
clean:
	rm *.class
safe:
	\cp *.g *.java ~/.431
