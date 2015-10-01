import java.util.Set;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

public class DistanceCalculator implements Runnable {
	public final ListenableUndirectedWeightedGraph<AgeProfessionNode, DefaultWeightedEdge> 	graph;  
	public volatile Double distance ;
	public final AgeProfessionNode currentVertex ; 
	public DistanceCalculator(AgeProfessionNode node , ListenableUndirectedWeightedGraph<AgeProfessionNode, DefaultWeightedEdge> 	graph){
		this.graph =  graph;
		this.currentVertex= node ; 
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//get all the vertex list.
		double mDis = 0.0; 
		int count = 0 ; 
		//System.out.println("Processing Parent vertex : " + currentVertex.getId());
		Set<AgeProfessionNode> vertices = graph.vertexSet();
		//For all vertices in the graph
		//Find the minimum vertex pair
		//For each iteration store the minimum using a priority queue
		//find the miniumum of that priority queue
		for ( AgeProfessionNode vertex : vertices){
			//System.out.println("Processing Vertex : " + vertex.getId());
			if( ! currentVertex.equals(vertex)){
				mDis = currentVertex.computeDistance(vertex);	
				//compare current vertex with all the vertices
				if ( count == 0){
					distance = mDis;
				}
				else{
					mDis = currentVertex.computeDistance(vertex);
					if ( mDis < distance ){
						//This minimum for the current vertex. Global minima will will be 
						//the minimum for all the currentVertices. 
						distance = mDis; 
					}
				}
				//System.out.println("Distance for the node " + vertex.getId() + " is "+ mDis);
				count++; 
			}
		}
		System.out.println();
		//System.out.println("Distance is " + distance + " for vertex id  " + currentVertex.getId());
	}

}
