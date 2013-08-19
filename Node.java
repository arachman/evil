import java.util.*;
import java.io.*;

public class Node<T> {

	private T data;
	private Vector<Node<T>> edges;

	public Node(T data){
		this.data = data;
		edges = new Vector<Node<T>> ();
	}

	public Node(T data, Vector<Node<T>> edges){
		this.data = data;
		this.edges = edges;
	}

	public T getData(){
		return data;
	}

	public Vector<Node<T>> getEdges(){
		return edges;
	}

	public void addEdge(Node<T> node){
 		if(!(node.equals(this))){
 			if(!(edges.contains(node))){
 				edges.add(node);
 			}
 		}
	}

	public int getDegree(){
		return edges.size();
	}

	public void removeEdge(Node<T> node){
		edges.remove(node);
	}
}


