import java.util.*;

public class Label extends Operand {

	protected String labelName;

	public Label(String name){
		labelName = name; 
	}

	public String getLabelName(){
		return labelName;
	}
/*	
	public String toString(){
		return Integer.toString(value);
	}
*/
}
