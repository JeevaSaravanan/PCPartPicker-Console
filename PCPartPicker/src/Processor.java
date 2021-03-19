
public class Processor {
	String PID;
	String Model;
	String Details;
	float rate;
	int qty;
	int gc;
	int gid;
	int actualQuantity;
	int schemaID;
	Processor(String PID,String Model,String Details,float rate,int qty,int gc,int gid,int schemaID){
		this.PID=PID;
		this.Model=Model;
		this.Details=Details;
		this.rate=rate;
		this.qty=qty;
		this.gc=gc;
		this.gid=gid;
		this.schemaID=schemaID;
	}
	void setActualQuantity(int qty){
		actualQuantity=qty;
	}
	boolean checkAvailability(){
		if(qty==0) {
			return false;
		}
		else {
			return true;
		}
	}
}
