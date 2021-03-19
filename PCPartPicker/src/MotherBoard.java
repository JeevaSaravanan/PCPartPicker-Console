
public class MotherBoard {
	String PID;
	String Model;
	String brand;
	float rate;
	int qty;
	int seriesid;
	int actualQuantity;
	int schemaID;
	MotherBoard(String PID,String Model,String brand,float rate,int qty,int seriesid,int schemaID){
		this.PID=PID;
		this.Model=Model;
		this.brand=brand;
		this.rate=rate;
		this.qty=qty;
		this.seriesid=seriesid;
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
