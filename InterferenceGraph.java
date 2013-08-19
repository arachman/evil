import java.io.*;
import java.util.*;

public class InterferenceGraph{

	private HashMap<String, Node<Register>> nodes;
	private CFG cfg;
	private static final String[] color = {"%l0", "%l1", "%l2", "%l3", "%l4", "%l5", "%l6", "%l7"};
	
	public InterferenceGraph(CFG cfg){
		nodes = new HashMap<String, Node<Register>> ();	
		this.cfg = cfg;
	}

	public Node<Register> getNode(Register r){
		Node<Register> n = nodes.get(r.getName());
		if(n == null){
			n = new Node(r);
			nodes.put(r.getName(), n);
		}
		return n;
	}
	
	public void build(){
		Vector<BasicBlock> blocks = cfg.getBlocks();
		Vector<ILOC> insts;
		Vector<Object> sources;	
		Vector<Object> targets;
		Node<Register> node;
		Node<Register> targetNode;

		for(BasicBlock b : blocks){
			insts = b.getInst();
			b.setLiveoutRegs(cfg.toRegisters(b.getLiveout()));
			b.setLivenowRegs(cfg.toRegisters(b.getLiveout()));
			Vector<Register> livenow = b.getLivenowRegs();
						for(Register lr : livenow){
						}	
			Collections.reverse(insts);
			// for each instruction bottom up
			for(ILOC i : insts){
				targets = i.getTargets();
				sources = i.getSources();

				/** Required when livenow is empty otherwise some imaginary register
				 *  is not going to get a real register because it's not part of the graph
				 */
				if(livenow.isEmpty()){
					for(Object o : targets){
						if(o instanceof Register){
							if(!((((Register)o).getName().equals("rarp")) || (((Register)o).getName().equals("%g0")) ||(((Register)o).getName().equals("ccr")))){
								getNode((Register) o);
							}
						}
					}
				}
				// for each register in live now
				for(Register r : livenow){
					// create a new node for graph
					node = getNode(r);
					// put node in graph
					//nodes.put(r.getName(), node);

					// w/ each target of i
					for(Object o : targets){
						if(o instanceof Register){
						for(Register lr : livenow){
						}	
							if(!(((Register) o).getName().equals(r.getName()))){
								if(!((((Register)o).getName().equals("rarp")) || (((Register)o).getName().equals("%g0")) || (((Register)o).getName().equals("ccr")))){
								

								// create a node for interference edge
									targetNode = getNode((Register) o); 
								//	new Node((Register)o);

								// add interference edge with node
									node.addEdge(targetNode);
									targetNode.addEdge(node);
								//nodes.put(targetNode.getData().getName(), targetNode);
									//for(Node k : node.getEdges()){
									//}
 									for(Node k : targetNode.getEdges()){
 									}
 									for(Node k : node.getEdges()){
 									}
								}
							
							}
						}
					}
				}
				for(Object o : targets){
					if(o instanceof Register){
						livenow.remove(o);
					}
				}
				for(Object o : sources){
					if(o instanceof Register){
						if(!((((Register)o).getName().equals("rarp")) || (((Register)o).getName().equals("%g0")) || (((Register)o).getName().equals("ccr")))){
							if(!(livenow.contains((Register)o))){
								livenow.add((Register)o);
							}
						}
					}
				}	
			}
			Collections.reverse(insts);	
		}
		for(Node<Register> n : nodes.values()){
		}
	}

	public void colorGraph(){
		Stack<Node> nodeStack = new Stack<Node> ();
		Node<Register> node;
		while(!(nodes.isEmpty())){
			node = getUnconstrained();
			if(node == null){
				node = getLeastConstrained();
			}
			nodeStack.push(node);
			nodes.remove((node.getData()).getName());
			for(Node<Register> n : nodes.values()){
				n.removeEdge(node);
			}
		}
		while(!(nodeStack.isEmpty())){
			node = nodeStack.pop();
			for(Node<Register> e : node.getEdges()){
				for(Node<Register> n : nodes.values()){
					if(n.equals(e)){
						n.addEdge(e);
					}
				}
			}
			nodes.put(node.getData().getName(), node);
			BitSet cBit = new BitSet();
			for(Node<Register> e : node.getEdges()){
				for(int i = 0; i < color.length; i++){
					if(e.getData().getName().equals(color[i])){
						cBit.set(i);
					}
				}
			}
			if(cBit.nextClearBit(0) > 7){
				System.out.println("Has to spill!!!!");
			}
			else
			{
				/*
				if(!(node.getData().getName().startsWith("%"))){
					System.out.println("Assigning " + color[cBit.nextClearBit(0)] + " to "+ node.getData().getName());
					node.getData().assignColor(color[cBit.nextClearBit(0)]);
					nodes.remove(node.getData().getColor());
					nodes.put(node.getData().getName(), node);
					System.out.println("Register "+node.getData().getColor()+" gets "+node.getData().getName());
					System.out.println("Register Address: " + node.getData());
				}
				*/
				if(!(node.getData().getName().startsWith("%"))){
					node.getData().assignColor(color[cBit.nextClearBit(0)]);
				}
			}
			//System.out.println("Stack size: "+nodeStack.size());
		}	
	}

	public Node<Register> getUnconstrained(){
		for(Node<Register> n : nodes.values()){
			if(n.getDegree() < color.length){
				return n;
			}
		}
		return null;
	}

	public Node<Register> getLeastConstrained(){
		//	Node<Register> node = new Node<Register> (Register.make()); 
		//	(Node<Register>)((nodes.values()).get(0));
		Node<Register> node = null;
		Collection<Node<Register>> nodeColls = (Collection<Node<Register>>)(nodes.values());
		List n_list = new ArrayList(nodeColls);	
		for(int i = 0; i < (nodeColls.size() - 1); i++){
			if(((Node<Register>)n_list.get(i)).getDegree() <= ((Node<Register>)n_list.get(i+1)).getDegree()){
				node = (Node<Register>)n_list.get(i);
			}
			else
			{
				node = (Node<Register>)n_list.get(i+1);
			}
		}
		//for(Node<Register> n : nodes.values()){
		//	if(n.getDegree() < node.getDegree()){
		//		node = n;
		//	}
		//}
		if(nodeColls.size() == 1)
		{
			node = (Node<Register>)n_list.get(0);
		}
		return node;
	}
			
}
