import java.util.List;
public class AgeProfessionNode implements Comparable<AgeProfessionNode>{
	private  int id ; 
	private int age, professionCode; 
	private boolean processedNode ; 
	public AgeProfessionNode(int id, int age, int professionCode){
		this.age = age; 
		this.id = id;
		this.professionCode = professionCode; 
	}	
	public int getId(){
		return this.id;
	}
	@Override
	public synchronized boolean equals(Object other) {
	    if (!(other instanceof AgeProfessionNode)) {
	        return false;
	    }
	    AgeProfessionNode otherobj = (AgeProfessionNode) other;
	    // Custom equality check here.
	    return (this.id == (otherobj.getId()));
	}
	public synchronized String Print(){
		return (";" + id  + ";" + age + ";" + this.professionCode + "\n");
	}
	public synchronized void setProcessed ( boolean ans){
		this.processedNode = ans ; 
	}
	public int getAge(){
		return this.age;
	}
	public synchronized boolean isProcessed(){
		return processedNode; 
	}
	public Double computeDistance(AgeProfessionNode node){
		return Math.abs(Math.random());
	}
	public int GetProfession(){
		return this.professionCode;
	}
	public static double CalculateDistance( List<AgeProfessionNode> n1 , List<AgeProfessionNode> n2){
		double d = 0.0;
		//For each of the clusters , find the optimal distance ( average link )
		double avgDist = 0.0; 
		if ( n1.size() == 1 && n2.size() ==1 ){
			d = Math.sqrt(Math.pow((n2.get(0).getAge() - n1.get(0).getAge()),2) + 
					Math.pow((n1.get(0).GetProfession() -n2.get(0).GetProfession()),2));
		}
		else{
			for ( AgeProfessionNode nn1 : n1){
				double ld = 0.0; 
				//Compute the distance between nn1 and nn2
				for ( AgeProfessionNode nn2 : n2){
					ld = Math.sqrt(Math.pow((nn1.getAge() - nn2.getAge()),2) + 
							Math.pow((nn1.GetProfession() -nn2.GetProfession()),2));
					d += ld ; 
				}
			}
			//take the average
			d = d / ( n1.size() * n2.size());
		}
		return d; 
	}
	@Override
	public int compareTo(AgeProfessionNode o) {
		// TODO Auto-generated method stub
		//Calculate distances for these two nodes
		return 0; 
	}
}
