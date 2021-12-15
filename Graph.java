import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Class that represents given vertices and their edges as a complete Graph.
 * 
 * @author Daniel Townsend
 *
 */
public class Graph {
	public static boolean useDistCost = true;
	public static boolean useTimeCost = false;
	public static boolean useLeftTurns = false;
	public static boolean useRestStops = false;
	public static boolean returnAddress = false;
	public static boolean useAlternateString = false;
	
	public HashMap<String, Vertex> vertices;
	public ArrayList<Vertex> vertexList;
	public ArrayList<Edge> edges;
	
	/**
	 * Empty constructor. Used for testing purposes.
	 */
	public Graph() {
		vertices = new HashMap<String, Vertex>();
		vertexList = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	
	/**
	 * Workhorse constructor that takes in a file name and reads in the vertices and their edges.
	 * 
	 * @param fileName a String that holds the name of the file to read in.
	 */
	public Graph(String fileName) {
		vertices = new HashMap<String, Vertex>();
		edges = new ArrayList<Edge>();
		vertexList = new ArrayList<Vertex>();
		
		String[] parts;
		
		try(Scanner fin = new Scanner(new File(fileName))){ 
			while(fin.hasNextLine()) {
				parts = fin.nextLine().split("\t");
				if(parts[0].equals("<Nodes>")) {
					fin.nextLine();
					
					while(true) {
						parts = fin.nextLine().split("\t");
						if(parts[0].equals("</Nodes>")) {
							break;
						}
						vertexList.add(new Vertex(parts[0], parts[1]));
						vertices.put(parts[0], new Vertex(parts[0], parts[1]));
					}
					
				} else if(parts[0].equals("<Edges>")) {
					fin.nextLine();
					
					while(true) {
						parts = fin.nextLine().split("\t");
						if(parts[0].equals("</Edges>")) {
							break;
						}
						edges.add(new Edge(
								  vertices.get(parts[0]),
								  vertices.get(parts[1]),
								  Integer.parseInt(parts[2]),
								  Integer.parseInt(parts[3]),
								  Integer.parseInt(parts[4]),
								  Integer.parseInt(parts[5]),
								  Integer.parseInt(parts[6])));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		Graph.useAlternateString = false;
		for(Edge e : edges) {
			s.append(e).append("\n");
		}
		Graph.useAlternateString = true;
		return s.toString();
	}

}
