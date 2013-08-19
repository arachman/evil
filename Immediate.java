import java.io.*;
import java.util.*;

public class Immediate extends Operand {

	protected String value;

	public Immediate(String val){
		if(val.equals("0"))
			value = "%g0";
		else
			value = val;
	}

	public String getValue(){
		return value;
	}
/*	
	public String toString(){
		return Integer.toString(value);
	}
*/
}
