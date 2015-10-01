import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.DepthFirstIterator;
public class Main1 {
	//Agglomerative heirarchial clustering java
	public static void main(String[] args) throws InterruptedException {
		AgeProfessionCluster c = new AgeProfessionCluster(); 
		Connection conn = null; 
		Statement s = null; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		//Load jdbc driver		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();   
				conn = DriverManager
				         .getConnection("jdbc:mysql://localhost/?user=root&password=root");
				s = conn.createStatement();
			    s.executeUpdate("use csci_b565;");
				rs = s.executeQuery("select occupation, count(occupation) as countoccupation from users group by occupation"); 
				//Create a hashmap for occupation 
				HashMap<Integer, Integer> map= c.functionMap;
				while ( rs.next()){
					map.put(rs.getInt(1), rs.getInt(2));
				}	
				
				s.executeUpdate("Drop table dm" );
				s.executeUpdate("CREATE TABle dm ( v1 int  , v2 int , d double)");	
				//rs = s.executeQuery("	 limit 5");
				rs = s.executeQuery("select userid, movieid, rating from ratings");
				long startTime= System.nanoTime();

				while ( rs.next()){
					//Create a new node 
					AgeProfessionNode n = new AgeProfessionNode(rs.getInt(1), rs.getInt(2) , rs.getInt(3));
					c.AddVertex(n);
					//System.out.println("Added vertex for user id " + rs.getInt(1));
				}
				
				ExecutorService executor = Executors.newFixedThreadPool(400);
				Set<AgeProfessionNode> values  =  c.clusterGraph.vertexSet();
				 
				for (AgeProfessionNode n : values){				
					DistanceCalculator dc = new DistanceCalculator(n, c.clusterGraph);
					executor.submit(dc);
				} 
				long endTime = System.nanoTime();
				System.out.println(c.nVertices + " inserted in " +( (endTime - startTime)/1000000000.0) + " seconds");
					
				executor.shutdown();
					 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		/*
//		
//		//Load jdbc driver		
//		try {
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//		   
//				conn = DriverManager
//				         .getConnection("jdbc:mysql://localhost/?user=root&password=root");
//			
//				s = conn.createStatement();
//			    s.executeUpdate("use csci_b565;");
//				//rs = s.executeQuery("SELECT * From veh_pri"); 
//			   
//			    long startTime= System.nanoTime();
//			    for ( int i = 0; i < 5; i++){
//			    	 c.AddVertex(i);
//			    }
//			   
//			   ClosestFirstIterator<Integer, DefaultWeightedEdge> irt = c.GetNearestVertexIterator(0); 
//			   irt.setCrossComponentTraversal(false);
//			   
//			   /*  NeighborIndex<Integer, DefaultWeightedEdge> ni = new NeighborIndex<Integer, DefaultWeightedEdge>(c.clusterGraph);
//			   for ( int items: ni.neighborListOf(0) ){
//				   System.out.println(items);
//			   }*/
//			  c.DFS();
//
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}

}
