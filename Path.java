import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class that represents a Path along a Graph. In this implementation, a
 * Path consists of a sequence of Edges.
 * 
 * @author Daniel Townsen
 *
 */
public class Path implements Comparable<Path> {
	
	public LinkedList<Edge> path;
	public int totalDistCost = 0;
	public int totalTimeCost = 0;
	public int leftTurns = 0;
	public int restStops = 0;
	public int chargingStations = 0;

	/**
	 * Workhorse constructor. Initializes a path with an edge.
	 * @param startingEdge
	 */
	public Path(Edge startingEdge) {
		this.path = new LinkedList<Edge>();
		path.add(startingEdge);
	}
	
	/**
	 * Empty constructor. Initializes a Path object.
	 */
	public Path() {
		this.path = new LinkedList<Edge>();
	}
	
	@Override
	public int compareTo(Path otherPath) {
		
		if(Graph.useDistCost) {
			return otherPath.totalDistCost - totalDistCost;
		} else if(Graph.useTimeCost) {
			return otherPath.totalTimeCost - totalTimeCost;
		} else if(Graph.useLeftTurns) {
			return otherPath.leftTurns - leftTurns;
		} else if(Graph.useRestStops) {
			return restStops - otherPath.restStops;
		} else {
			return chargingStations - otherPath.chargingStations;
		}
	}
	
	@Override
	public String toString() {
		Graph.useAlternateString = false;
		StringBuilder str = new StringBuilder();
		
		for(Edge edge : path) {
			if(!edge.toVertex.equals(edge.fromVertex)) {
				str.append(edge.toString() + "\n");
			}
		}
		
		if(Graph.useDistCost) {
			str.append("\nTotal Cost: " + totalDistCost + " miles\n");
		} else if(Graph.useTimeCost) {
			str.append("\nTotal Cost: " + totalTimeCost + " minutes\n");
		} else if(Graph.useLeftTurns) {
			str.append("\nNumber of left turns: " + leftTurns + "\n");
		} else if(Graph.useRestStops) {
			str.append("\nNumber of rest stops: " + restStops + "\n");
		} else {
			str.append("\nNumber of vehicle charging stations: " + chargingStations + "\n");
		}
		
		Graph.useAlternateString = true;
		return str.toString();
	}
}
