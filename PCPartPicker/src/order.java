import java.util.*;

public class order {
	String orderID;
	String userID;
	float total;
	order(String userID,float total){
		this.userID=userID;
		this.total=total;
	}
	void generateOrderID() {
		 Random r = new Random( System.currentTimeMillis() );
		   orderID="O"+((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}
}
