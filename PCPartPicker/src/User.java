
public class User {
	String uid;
	String firstName;
	String lastName;
	String email;
	String psw;
	String phone;
	String address="2/122 Sivan North Street, Puthur Manjakollai(p.o)";
	String city="Nagapattinam";
	String state="Tamil Nadu";
	String zip="611106";
	String country="India";
	boolean active=false;
	int emailVerfication=1;
	int phoneVerfication=1;
	User(String firstName,String lastName,String email,String psw,String phone){
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.psw=psw;
		this.phone=phone;
	}
	public void setActive(){
		active=true;
	}
	public void clearActive(){
		active=false;
	}
	public void userDetails() {
		System.out.println("----------------------------------------------------------------");
		System.out.println("---Name: "+firstName+" "+lastName+"                         ---");
		System.out.println("---Email: "+email+"\t Phone: "+phone+"              ---");
		System.out.println("---Address: "+address+", "+city+"-"+zip+", "+state+", "+country);
		System.out.println("----------------------------------------------------------------");
		
	}
	public void generateUID(){
		uid=firstName.charAt(0)+""+lastName.charAt(0)+""+phone.substring(0, 4);
	}
	
}
