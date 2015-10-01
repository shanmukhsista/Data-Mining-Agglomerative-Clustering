import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.event.VertexSetListener;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.ClosestFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
public class ClusterFactory<T> implements VertexSetListener<T>{
	public SimpleWeightedGraph<T, DefaultWeightedEdge> wg = new SimpleWeightedGraph<T, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	public ListenableUndirectedWeightedGraph<T, DefaultWeightedEdge> 	clusterGraph ; 
	int nVertices = 0 ; 
	List<Double> shortestDistances= new ArrayList<Double>(); 
	
	public ClusterFactory(){
		clusterGraph = new ListenableUndirectedWeightedGraph<T, DefaultWeightedEdge>(wg); 
		//clusterGraph.addVertexSetListener();
	}
	

	@Override
	public void vertexAdded(GraphVertexChangeEvent<T> arg0) {
		nVertices++; 
		//System.out.println("Inserted Vertex : " + arg0.getVertex());
		// TODO Auto-generated method stub
		//Get all the vertices for the graph. 
		Set<T> vset = this.clusterGraph.vertexSet();
		//System.out.println("Vertex added. Printing existing elements");
		double shortestDistance = 0.0; 
		double edgeWeight = Math.abs(Math.random());
		for( T item : vset){
			if ( item != arg0.getVertex()){
				//Add edges to all the other vertices.
				DefaultWeightedEdge e = this.clusterGraph.addEdge(item, arg0.getVertex()); 
				this.clusterGraph.setEdgeWeight(e, edgeWeight);
				System.out.println("Adding link between " + item + " and " + arg0.getVertex() + " with weight " + this.clusterGraph.getEdgeWeight(e) );
			}
		}
	
	}
	/*public void DFS(){
		DefaultWeightedEdge e = this.clusterGraph.getEdge(1, 0);
		DefaultWeightedEdge e1 = this.clusterGraph.getEdge(1, 2);
		DefaultWeightedEdge e2 = this.clusterGraph.getEdge(1, 3);
		DefaultWeightedEdge e3 = this.clusterGraph.getEdge(1, 4);
		
		this.clusterGraph.removeEdge(e);
		this.clusterGraph.removeEdge(e1);
		this.clusterGraph.removeEdge(e2);
		this.clusterGraph.removeEdge(e3);
		
		GraphIterator<T, DefaultWeightedEdge> iterator = 
                new DepthFirstIterator<T, DefaultWeightedEdge>(this.clusterGraph);;
        while (iterator.hasNext()) {
            System.out.println( "Edge " + iterator.next() );
            if ( iterator.next() ==3 ){
            	this.clusterGraph.removeVertex(4);
            }
            
            }
	}
	@Override
	public void vertexRemoved(GraphVertexChangeEvent<Integer> arg0) {
		// TODO Auto-generated method stub
		
	}*/


	@Override
	public void vertexRemoved(GraphVertexChangeEvent<T> arg0) {
		// TODO Auto-generated method stub
		
	}
}
