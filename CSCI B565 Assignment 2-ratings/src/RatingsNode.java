import java.util.List;
import java.util.Set;

import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.graph.DefaultWeightedEdge;


public class RatingsNode implements Comparable<RatingsNode>{
	private  int id ; 
	private int rating, movieid; 
	private int genre; 
	private boolean processedNode ; 
	public RatingsNode(int id, int movieid, int rating, int genre){
		this.id = id;
		this.movieid = movieid;
		this.rating = rating; 
		this.genre = genre; 
	}	
	public int getId(){
		return this.id;
	}
	@Override
	public synchronized boolean equals(Object other) {
	    if (!(other instanceof RatingsNode)) {
	        return false;
	    }
	    RatingsNode otherobj = (RatingsNode) other;
	    // Custom equality check here.
	    return (this.id == (otherobj.getId()));
	}
	public int getGenre(){
		return this.genre;
	}
	public synchronized String Print(){
		return (";" + id  + ";" + this.movieid +";"+ rating + ";" + this.genre + "\n");
	}
	public synchronized void setProcessed ( boolean ans){
		this.processedNode = ans ; 
	}
	public int getAge(){
		return this.rating;
	}
	public synchronized boolean isProcessed(){
		return processedNode; 
	}
	public Double computeDistance(RatingsNode node){
		return Math.abs(Math.random());
	}
	public int GetMovieId(){
		return this.movieid;
	}
	public int GetRating(){
		return this.rating;
	}
	public static double CalculateDistance( List<RatingsNode> n1 , List<RatingsNode> n2){
		double d = 0.0;
		//For each of the clusters , find the optimal distance ( average link )
		double avgDist = 0.0; 
		if ( n1.size() == 1 && n2.size() ==1 ){
			d = Math.sqrt(Math.pow((n2.get(0).GetRating() - n1.get(0).GetRating()),2) + 
					Math.pow((n1.get(0).getGenre() -n2.get(0).getGenre()),2));
		}
		else{
			for ( RatingsNode nn1 : n1){
				double ld = 0.0; 
				//Compute the distance between nn1 and nn2
				for ( RatingsNode nn2 : n2){
					ld = Math.sqrt(Math.pow((nn1.GetRating() - nn2.GetRating()),2) + 
							Math.pow((nn1.getGenre() -nn2.getGenre()),2));
					d += ld ; 
				}
			}
			//take the average
			d = d / ( n1.size() * n2.size());
		}
		return d; 
	}
	@Override
	public int compareTo(RatingsNode o) {
		// TODO Auto-generated method stub
		//Calculate distances for these two nodes
		return 0; 
	}
}
