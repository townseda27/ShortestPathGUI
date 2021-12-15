import java.util.HashSet;
import java.util.LinkedList;

/**
 * Class that contains the algorithm for Dijkstra's shortest path.
 * 
 * @author Daniel Townsend
 *
 */
public class Dijkstra {
	
	static int totalCost;
	
	/**
	 * Finds the most optimal path depending on choice of start and end vertex.
	 * 
	 * @param graph the Graph to search through.
	 * @param startVertex the starting Vertex location.
	 * @param endVertex the ending Vertex location.
	 * @return the most optimal Path if one exists. If no path exists,
	 * it returns null.
	 */
	public static Path shortestPath(Graph graph, Vertex startVertex, Vertex endVertex) {
		
		HeapPriorityQueue<Path> queue = new HeapPriorityQueue<Path>();
		queue.add(new Path(new Edge(startVertex, startVertex, 0, 0, 0, 0, 0)));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		
		while(!queue.isEmpty()) {
			Path nextEntry = queue.remove();
			Vertex vertex = nextEntry.path.getLast().toVertex;
			
			if(visited.contains(vertex)) {
				continue;
			} else {
				visited.add(vertex);
				
				if(vertex.equals(endVertex)) {
					return nextEntry;
				} else {
					Vertex currVertex = vertex;
					int currCost = -1;
					if(graph.useDistCost) {
						currCost = nextEntry.totalDistCost;
					} else if(Graph.useTimeCost) {
						currCost = nextEntry.totalTimeCost;
					} else if(Graph.useLeftTurns) {
						currCost = nextEntry.leftTurns;
					} else if(Graph.useRestStops) {
						currCost = nextEntry.restStops;
					} else {
						currCost = nextEntry.chargingStations;
					}
					
					for(Edge edge : graph.edges) {
						if(edge.fromVertex.equals(currVertex) && !visited.contains(edge.toVertex)) {
							Vertex neighbor = edge.toVertex;
							Edge nextEdge = new Edge(currVertex, neighbor, edge.timeCost, edge.distCost, 
									                 edge.leftTurnCount, edge.restStopCount, edge.chargingStationCount);
							
							Path nextPath = new Path();
							if(graph.useDistCost) {
								nextPath.totalDistCost = currCost + edge.distCost;
							} else if(Graph.useTimeCost) {
								nextPath.totalTimeCost = currCost + edge.timeCost;
							} else if(Graph.useLeftTurns) {
								nextPath.leftTurns = currCost + edge.leftTurnCount;
							} else if(Graph.useRestStops) {
								nextPath.restStops = currCost + edge.restStopCount;
							} else {
								nextPath.chargingStations = currCost + edge.chargingStationCount;
							}
							
							LinkedList<Edge> currList = new LinkedList<Edge>(nextEntry.path); 
							nextPath.path = currList;
							nextPath.path.add(nextEdge);
							queue.add(nextPath);						
						}
					}
				}
			}
		}
		// if we return null, no possible path existed
		return null;		
	}	
}
