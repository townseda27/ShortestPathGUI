/**
 * Class representing a Vertex. A vertex has a symbol and an address.
 * 
 * @author Daniel Townsend
 *
 */
public class Vertex {
	private static int longestAddress = 1;
	
	public String symbol;
	public String address;
	
	/**
	 * Workhorse constructor. Initializes a vertex with the given parameters.
	 * 
	 * @param symbol a String to set the Vertex's symbol property with.
	 * @param address a String to set the Vertex's address property with.
	 */
	public Vertex(String symbol, String address) {
		this.symbol = symbol;
		this.address = address;
		longestAddress = Math.max(longestAddress, address.length());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Vertex)) {
			return false;
		}
		
		Vertex otherVertex = (Vertex) obj;
		
		return this.symbol.equals(otherVertex.symbol) &&
			   this.address.equals(otherVertex.address);
	}
	
	@Override
	public String toString() {
		if(Graph.useAlternateString) {
			return symbol + ", " + address;	
		}
		return String.format("%-" + (Graph.returnAddress ? longestAddress : 1) + "s", Graph.returnAddress ? address : symbol);
	}
	
	/**
	 * Returns a string depending on if the user wants to
	 * display the addresses of the Vertex or not.
	 * 
	 * @return a String representing either a Vertex's symbol or address.
	 */
	public String toStringOutputBox() {
		if(Graph.returnAddress) {
			return address;
		} else {
			return symbol;
		}
	}
	
}
