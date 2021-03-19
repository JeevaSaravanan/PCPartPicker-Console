
public class GraphicCard {
	String PID;
	String Model;
	String brand;
	float rate;
	int qty;
	String gpuChip;
	int actualQuantity;
	int schemaID;
	GraphicCard(String PID,String Model,String gpuChip,String brand,float rate,int qty,int schemaID){
		this.PID=PID;
		this.Model=Model;
		this.brand=brand;
		this.rate=rate;
		this.qty=qty;
		this.gpuChip=gpuChip;
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
