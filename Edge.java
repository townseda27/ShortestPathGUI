
/**
 * Class representing an 'Edge', which has a start and end vertex as well as
 * various costs such as distance or time.
 * 
 * @author Daniel Townsend
 *
 */
public class Edge {
	public Vertex fromVertex;
	public Vertex toVertex;
	
	public int timeCost;
	public int distCost;
	public int leftTurnCount;
	public int restStopCount;
	public int chargingStationCount;
	
	/**
	 * Workhorse constructor for the Edge class.
	 * 
	 * @param fromVertex the starting Vertex.
	 * @param toVertex the ending Vertex.
	 * @param timeCost the time cost from fromVertex to toVertex.
	 * @param distCost the distance cost from fromVertex to toVertex.
	 * @param leftTurnCount the number of left turns from fromVertex to toVertex.
	 * @param restStopCount the number of rest stops from fromVertex to toVertex.
	 * @param chargingStationCount the number of charging stations from fromVertex to toVertex.
	 */
	public Edge(Vertex fromVertex, Vertex toVertex, int timeCost, int distCost, int leftTurnCount,
			    int restStopCount, int chargingStationCount) {
		super();
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.timeCost = timeCost;
		this.distCost = distCost;
		this.leftTurnCount = leftTurnCount;
		this.restStopCount = restStopCount;
		this.chargingStationCount = chargingStationCount;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(fromVertex.toString());
		str.append(" -> ");
		str.append(toVertex.toString());
		str.append(" (");
		 
		if(Graph.useDistCost) {
			str.append(distCost);
			str.append(" miles");
		} else if(Graph.useTimeCost) {
			str.append(timeCost);
			str.append(" minutes");
		} else if(Graph.useLeftTurns) {
			str.append(leftTurnCount);
			str.append(" left turns");
		} else if(Graph.useRestStops) {
			str.append(restStopCount);
			str.append(" rest stops");
		} else {
			str.append(chargingStationCount);
			str.append(" vehicle charging stations");
		}
		
		str.append(")");
		return str.toString();
	}
}
