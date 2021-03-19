
public class Storage {
	String PID;
	String Model;
	String brand;
	String Capacity;
	float rate;
	int qty;
	int SID;
	String type;
	int actualQuantity;
	int schemaID;
	Storage(String PID,String Model,String brand,String type,String Capacity,float rate,int qty,int SID,int schemaID){
		this.PID=PID;
		this.Model=Model;
		this.brand=brand;
		this.rate=rate;
		this.qty=qty;
		this.type=type;
		this.SID=SID;
		this.Capacity=Capacity;
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
	boolean qtyAvailability(int q){
		if(qty<q) {
			return false;
		}
		else {
			return true;
		}
	}

}
