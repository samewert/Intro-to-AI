package Homework1;

// All imports used
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Pathfinder {

//	Hashmap of vertexes, indexes to dynamically assign indexes to each vertex
//	Allows unique indexes regardless the number of vertexes in the graph
	private LinkedHashMap<String, Integer> hm = new LinkedHashMap<String, Integer>();
//  Adjacency list stored as a list of lists (uses indexes that correspond with each vertex)
	private ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
//	String of the start vertex
	private String start ="";
//	String of goal vertex
	private String goal = "";

//	Constructor. Calls the pathfinder method
	public Pathfinder(String filename) {
		pathfinder(filename);
	}
	
//	Creates an adjacency list of the graph
	private void createAdjList(String filename){
		try {
			int index = 0; // incremented to give each vertex a unique index
			Scanner Scan = new Scanner(new FileReader(filename)); // reads in the .txt file
			while(Scan.hasNext()) {
				String vertex1 = Scan.next(); // String of vertex1
				int vertexNum1 = index; // index of the vertex

//				Get index of first vertex in the edge
				if(hm.containsKey(vertex1)) { // checks if the vertex already has an index
					vertexNum1 = hm.get(vertex1); // gets pre-existing index
				} else {
					hm.put(vertex1, index); // creates new index for the vertex
					adjList.add(new ArrayList<Integer>()); // initializes a list for the vertex
					index++; // increment to assign new index for next vertex
				}
				
				String vertex2 = Scan.next(); // repeat of line 36-46 for the second vertex in the edge
				int vertexNum2 = index;
				
//				Get index of second vertex in the edge
				if(hm.containsKey(vertex2)) {
					vertexNum2 = hm.get(vertex2);
				} else {
					hm.put(vertex2, index);
					index++;
					adjList.add(new ArrayList<Integer>());
				}
				
//				assigns the last vertex to be the goal
				goal = vertex2; 
				
//			    adds the edge to the adjacency list
				adjList.get(vertexNum1).add(vertexNum2); 
			}
			start = (String) hm.keySet().toArray()[0]; // get first location (start)
			
			Scan.close(); // closes Scanner
			
		} catch (FileNotFoundException e) {
			e.printStackTrace(); // prints error if incorrect filename
			System.out.println("Incorrect Filename"); // easier to know the error
		} 
	}
	
//	Depth First Search
//	Follows pseudo code
	private String DFS() {
		Stack<String> S = new Stack<String>();
		S.push(start);
		boolean[] visited = new boolean[hm.size()];
		visited[hm.get(start)] = true;
		while(S.size() > 0) {
			String T = S.pop();
			System.out.println(T);
			if(T.equals(goal)) {
				break;
			}
			for(int i = 0; i < adjList.get(hm.get(T)).size(); i++) {
				int t = adjList.get(hm.get(T)).get(i);
				if(!visited[t]) {
					visited[t] = true;
					S.push((String) hm.keySet().toArray()[t]);
				}
			}
		}
		return "";
	}
	
//	Breadth First Search
//	Follows pseudo code
	private String BFS() {
		Queue<String> Q = new LinkedList<String>();
		Q.add(start);
		boolean[] visited = new boolean[hm.size()];
		visited[hm.get(start)] = true;
		while(Q.size() > 0) {
			String T = Q.remove();
			System.out.println(T);
			if(T.equals(goal)) {
				break;
			}
			for(int i = 0; i < adjList.get(hm.get(T)).size(); i++) {
				int t = adjList.get(hm.get(T)).get(i);
				if(!visited[t]) {
					visited[t] = true;
					Q.add((String) hm.keySet().toArray()[t]);
				}
			}
		}
		return "";
	}
	
//	Method that runs and prints everything
	private void pathfinder(String filename) {
		createAdjList(filename); // creates the adjacency list for the graph
		System.out.println("Path");
		
		System.out.println("DFS:");
		long DFSStart = System.currentTimeMillis(); // records start time
		long DFSNanoStart = System.nanoTime(); // records start time
		DFS(); // runs Depth First Search
		long DFSFinish = System.currentTimeMillis(); // records finish time
		long DFSNanoFinish = System.nanoTime(); // records finish time
		long DFSTime = DFSFinish - DFSStart; // calculates runtime
		long DFSNanoTime = DFSNanoFinish - DFSNanoStart; // calculates runtime
		
		
		System.out.println("\nBFS:");
		long BFSStart = System.currentTimeMillis(); // records start time
		long BFSNanoStart = System.nanoTime(); // records start time
		BFS(); // runs Breadth First Search
		long BFSFinish = System.currentTimeMillis(); // calculates runtime
		long BFSNanoFinish = System.nanoTime(); // calculates runtime
		long BFSTime = BFSFinish - BFSStart; // calculates runtime
		long BFSNanoTime = BFSNanoFinish - BFSNanoStart; // calculates runtime
		
		System.out.println("\nTime:");
//		Prints the run times with labels
		System.out.println("DFS\t" + DFSTime + "\t(milliseconds)\t" + DFSNanoTime + "\t(nanoseconds)");
		System.out.println("BFS\t" + BFSTime + "\t(milliseconds)\t" + BFSNanoTime + "\t(nanoseconds)");

	}
	
	
	public static void main(String[] args) {
//		create an Object with a correct filename to run DFS and BFS
		System.out.println("********* Example Map *********\n");
		Pathfinder PF = new Pathfinder("src/Homework1/exampleMap.txt"); // example graph from the homework document
		System.out.println("\n********* Custom Map *********\n");
		Pathfinder customPF = new Pathfinder("src/Homework1/customMap.txt"); // graph from the first part of homework 1
	}

}
