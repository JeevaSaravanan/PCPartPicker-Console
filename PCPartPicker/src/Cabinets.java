
public class Cabinets {
	String PID;
	String Model;
	String brand;
	float rate;
	int qty;
	int actualQuantity;
	int schemaID;
	Cabinets(String PID,String Model,String brand,float rate,int qty,int schemaID){
		this.PID=PID;
		this.Model=Model;
		this.brand=brand;
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
