
public class PowerSupply {
	String PID;
	String Model;
	String brand;
	String Details;
	float rate;
	int qty;
	int actualQuantity;
	int schemaID;
	PowerSupply(String PID,String Model,String Details,String brand,float rate,int qty,int schemaID){
		this.PID=PID;
		this.Model=Model;
		this.brand=brand;
		this.Details=Details;
		this.rate=rate;
		this.qty=qty;
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
