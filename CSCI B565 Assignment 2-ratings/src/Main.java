import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.DepthFirstIterator;
public class Main {
	//Agglomerative heirarchial clustering java
	public static void main(String[] args) throws InterruptedException {
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
			    System.out.println("Fetching data...");
				//rs = s.executeQuery("	 limit 5");
				rs = s.executeQuery("select r.userid, m.movieid , rating, gc.genreid "
						+ "from ratings r inner join movies m on  r.movieid =m.movieid inner "
						+ "join genre_categories gc where locate( gc.genrename, m.genre) > 0"
						+ " group by  r.userid, m.movieid , rating, gc.genrename "
						+ " limit 2000");
				long startTime= System.nanoTime();
				List<List<RatingsNode>> data= new ArrayList<List<RatingsNode>>(); 
				RatingsNode n ;
				while ( rs.next()){
					//Create a new node 
					List<RatingsNode> ln = new ArrayList<RatingsNode>(); 
					n = new RatingsNode(rs.getInt(1), rs.getInt(2) , rs.getInt(3), rs.getInt(4));
					ln.add(n); 
					data.add(ln); 
					n = null; 
				}
				
				//Now we have all the age entries of database in this arraylist. 
				//now create a distance matrix 
				
				ConcurrentHashMap<String, Double> map = new 
						ConcurrentHashMap<String, Double>(data.size()/10); 
				List<RatingsNode> outerNode ,innerNode; 
				double d ; 
				String key =""; 
				//For every item in treemap
				System.out.println("Processing...");
				while ( map.size() != 1){
					System.out.println("New Iteration Started");
					int datasize = data.size(); 
					//System.out.println("\n\n*****New Iteration*********\n\n");
					//Compute distance matrix for one iteration 
					for ( int i = 0 ; i < datasize ;i++){ 
						outerNode = data.get(i);
						String outerKey = ""; 
						for ( RatingsNode on : outerNode){
							if (outerNode.size() > 1){
								if ( outerKey != ""){
									outerKey = outerKey + ";";
								}
								outerKey = outerKey + String.valueOf(on.getId());
							}
							else{
								outerKey = outerKey + String.valueOf(on.getId());
							}
						}
						//System.out.println("outer key is " + outerKey);
						for ( int j = i+1 ; j < data.size(); j++){
							innerNode = data.get(j);
							String innerKey = ""; 
							for ( RatingsNode in : innerNode){
								if (innerNode.size() > 1){
									if ( innerKey != ""){
										innerKey = innerKey + ";";
									}
									innerKey = innerKey + String.valueOf(in.getId());
								}
								else{
									innerKey = innerKey +  String.valueOf(in.getId());
								}
							}	
							d = RatingsNode.CalculateDistance(outerNode, innerNode);
							key= outerKey + ";" + innerKey;
							map.put( key , d);
							
						}
					}
					//Hashmap is the distance matrix 
					//Loop through all the values in the hashmap and find the minimum value for key
					double min = 0.0;
					String minkey = ""; 
					int ic = 0; 
					Set<String> keys = map.keySet();
					for ( String k : keys){
						if ( ic == 0 ){
							min = map.get(k);
							minkey = k ; 
							ic++; 
						}
						else{
							double lmin = map.get(k); 
							if ( lmin < min){
								min = lmin; 
								minkey = k;
							}
						}
					}
					//Add this minkey to our cluster adn remove the association from hashtable.
					//Delete all the keys that have these vertices. 
					Stack<List<RatingsNode>> ds = new Stack<List<RatingsNode>>();
					List<List<RatingsNode>> rm = new ArrayList<List<RatingsNode>> (); 
					HashMap<String, RatingsNode> hm = new HashMap<String, RatingsNode>(); 
					for ( String v : minkey.split(";")){
						//create a new list
						//v is the key in the treenode as well. 					
						//loop through the list to find the items
				
							for (List<RatingsNode> n1 : data){
								for( RatingsNode in1 : n1 ){
								//check if the given node list is in the array s
								String in1id = String.valueOf(in1.getId());
								if (v.compareTo(in1id) == 0){
									//System.out.println("Removing node " + in1id);
									if ( !hm.containsKey(v)){
										hm.put(v, in1);
										rm.add(n1);
										break; 
									}
									//data.remove(k);
									//Check if the elements are already present in the stack. 
									//ds.push(n1);
									//System.out.println("pushing data");
									break;
								}
							}
						}
						
						//Add a node to data 
						
						for ( String ikey : keys ){
							if ( ikey.contains(v)){
								map.remove(ikey);
								//Combine and remove from list
								
							}
						}
					}
					
					//System.out.println(minkey + " is minimum with a value  "+ min);
					
					
					List<RatingsNode> newNode = new ArrayList<RatingsNode>();
					
					for ( List<RatingsNode> rm1 : rm){
						//remove from list
						data.remove(rm1);
					}
					for ( String ks : hm.keySet()){
						newNode.add(hm.get(ks));
					}
					//add this list to the existing list 
					data.add(newNode);
					//System.out.println("map after key deletion");
					/*for ( String keyss : map.keySet()){
						System.out.println("key value is "+ keyss +  " Val : " + map.get(keyss));
					}*/
					/*for ( List<AgeProfessionNode> ln  : data){
						System.out.println("Parent -> children");
						for ( AgeProfessionNode lns : ln){
							//System.out.println("CHild " + lns.getId());
							System.out.println(lns.Print());
						}
					}*/
					//This minimum will form a new cluster
					//update the treemap with the new points
					
				}
				//Write all the clusters to file. 
				FileWriter write = new FileWriter( "cluster-"+ System.nanoTime()  + ".txt", false);
				PrintWriter printer = new PrintWriter(write); 
			
				
			int level = 0; 
			for	( List<RatingsNode> ln  : data){
				//System.out.println("Level " + level);
				for ( RatingsNode lns : ln){
					//System.out.println("CHild " + lns.getId());
					StringBuilder sb = new StringBuilder(); 
					sb.append(level);
					sb.append(lns.Print());
					System.out.print(sb.toString());
					printer.println(sb.toString());
				}
				level++; 
			}
			printer.close();
				long endtime = System.nanoTime(); 
				System.out.println("Total time : " + (endtime - startTime)/1000000000.0);			 
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
