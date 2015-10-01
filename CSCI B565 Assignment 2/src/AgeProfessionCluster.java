import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.event.VertexSetListener;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.ClosestFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;


public class AgeProfessionCluster implements VertexSetListener<AgeProfessionNode>, Runnable{
	int nVertices = 0 ;
	int gc = 0 ; 
	public HashMap<Integer, Integer> functionMap = new HashMap<Integer, Integer>();
	public SimpleWeightedGraph<AgeProfessionNode, DefaultWeightedEdge> wg = new SimpleWeightedGraph<AgeProfessionNode, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	public ListenableUndirectedWeightedGraph<AgeProfessionNode, DefaultWeightedEdge> 	clusterGraph ; 
	public GraphIterator<AgeProfessionNode, DefaultWeightedEdge> classItr ;  	
	public AgeProfessionNode current = null; 
	public AgeProfessionCluster(){
		clusterGraph = new ListenableUndirectedWeightedGraph<AgeProfessionNode, DefaultWeightedEdge>(wg); 
		clusterGraph.addVertexSetListener(this);
	}
	public synchronized void AddAllEdgesToGraph(AgeProfessionNode nextNode){
		System.out.println("Calling MEthod in thread ");
		System.out.println(nextNode.Print());
		AgeProfessionNode minNode = null; 
        	if ( !((nextNode.isProcessed()))){
        		nextNode.setProcessed(true);		
        		NeighborIndex<AgeProfessionNode, DefaultWeightedEdge> ni = new NeighborIndex<AgeProfessionNode, DefaultWeightedEdge>(clusterGraph);
            	double minimumDistance = 0 ; 
            	int count = 0 ; 
            	for ( AgeProfessionNode items: ni.neighborListOf(nextNode) ){
            		double lm  ;
            		DefaultWeightedEdge de = clusterGraph.getEdge(nextNode, items);  
                  		lm = clusterGraph.getEdgeWeight(de);
            			if ( count == 0){	
	            				minimumDistance = lm ; 
	            				minNode = items;
	            		}
	            		count++; 
	            		if ( lm < minimumDistance){
		            		
	            			minimumDistance = lm ;
	            			minNode = items ; 
	            		}
            			}
            	//Once the minimum distance is found, add these two vertices with edges to a new cluster. 
            	//Remove all vertices from graph and add this edge to the graph.
            		System.out.println(++gc);
            	System.out.println("Minimum Distance Edge is" + minimumDistance);
            	
            	//AgeProfessionCluster singleCluster = new AgeProfessionCluster(); 
            	//singleCluster.addSingleVertexEdgePair(nextNode, minNode, minimumDistance);
            		}
        
    	
    			   }
            	
	public void addSingleVertexEdgePair(AgeProfessionNode v1, AgeProfessionNode v2, double weight ){
		this.clusterGraph.addVertex(v1); 
		this.clusterGraph.addVertex(v2); 
		DefaultWeightedEdge de = this.clusterGraph.addEdge(v1, v2);
		clusterGraph.setEdgeWeight(de, weight);
		System.out.println("Added Vertex " + v1.Print() + " and " + v2.Print() + " to seperate cluster ");
	}
	@Override
	public void vertexAdded(GraphVertexChangeEvent<AgeProfessionNode> arg0) {
		nVertices++; 
		System.out.println("Processed vertex: " + nVertices);
		/*AgeProfessionNode current = arg0.getVertex();
		
		GraphIterator<AgeProfessionNode, DefaultWeightedEdge> itr = 
                new DepthFirstIterator<AgeProfessionNode, DefaultWeightedEdge>(this.clusterGraph);
    	//Calculate distances 
		while ( itr.hasNext()){
			AgeProfessionNode next= itr.next();
			if (! current.equals(next)){
				DefaultWeightedEdge de = clusterGraph.addEdge(current, next);
        		clusterGraph.setEdgeWeight(de, Math.abs(Math.random()));
			}	
		}*/
	
	}
	public void AddVertex(AgeProfessionNode n){
		this.clusterGraph.addVertex(n);
		//Calculate the distance from all teh existing vertices and add the vertex. 	
	}
	public void AddEdges(int v ){
		
	}
	@Override
	public void vertexRemoved(GraphVertexChangeEvent<AgeProfessionNode> arg0) {
		// TODO Auto-generated method stub
		
	}
	public synchronized void AddVertices(int d){
		this.clusterGraph.addVertex(new AgeProfessionNode(2334444, 23, (int)Math.floor(Math.random() * 100)));
		System.out.println("Calling Thread");
		for(int i= 0 ; i < d ; i++){
			int a = 0; 
		}
		System.out.println("Printing..");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Thread run ;
		AddAllEdgesToGraph(classItr.next());
	}
	
	
}
