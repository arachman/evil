import java.util.*;

public class Copy{

	private static int counter = 0;
	private static HashMap <Integer, Copy> copies = new HashMap<Integer, Copy>();

	protected int bitID;
	protected Register u;
	protected Register v;
	protected BasicBlock block;
	protected int pos;

	public static Copy getCopy(int id){
		return copies.get(id);
	}

	public static void deleteCopy(int c){
		copies.remove(c);
	}

	public Copy(Register target, Register source, BasicBlock b, int i){
		bitID = counter++;
		u = target;
		v = source;
		block = b;
		pos = i;
		copies.put(bitID, this);
	}

	public static Vector<Copy> getLocalCopies(BasicBlock bb){
		Vector<Copy> cs = new Vector<Copy> ();
		for(Copy c : copies.values()){
			if((c.block).equals(bb)){
				cs.add(c);
			}
		}
		return cs;
	}

	public static Vector<Copy> getCopies(){
		Vector<Copy> cs = new Vector<Copy> ();
		for(Copy c : copies.values()){
			cs.add(c);
		}
		return cs;
	}

	public boolean isChanged(Register target){
		return ((this.u).equals(target) || (this.v).equals(target));
	}

	public static Vector<Copy> getCopiesIn(BitSet cpin){
		Vector<Copy> cs = new Vector<Copy> ();
		for(int i=cpin.nextSetBit(0); i >= 0; i=cpin.nextSetBit(i+1)){
			cs.add(copies.get(i));
		}
		return cs;
	}


}
		
