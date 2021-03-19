import java.util.*;

public class ShoppingCart {
	Processor cpu;
	MotherBoard board;
	GraphicCard gpu;
	PowerSupply power;
	Cabinets cabinets;
	Ups ups;
	MemoryModule ram;
	Storage storage;
	int total=0;

	public void getSummary(){
		System.out.println("---------------------------------------------------------------------------------------------------------------");
		System.out.println("Order Summary");
		System.out.println("Processor");
		System.out.println("----MODEL----------------------DETAILS-----------------------RATE---------QTY--------------TOTAL");
		System.out.println(cpu.Model+"\t \t"+cpu.Details+"\t \t"+cpu.rate+"\t \t"+cpu.qty+"\t \t"+cpu.qty*cpu.rate);
		total+=(cpu.qty*cpu.rate);
		
		
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("Mother Board");
		System.out.println("---BRAND------------MODEL-------------------RATE---------QTY--------------TOTAL");
		System.out.println(board.brand+"\t \t"+board.Model+"\t \t"+board.rate+"\t \t"+board.qty+"\t \t"+board.qty*board.rate);
		total+=(board.qty*board.rate);
		if(gpu!=null) {
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
	
		System.out.println("Graphic Card");
		System.out.println("---BRAND---------------MODEL-----------------GPU CHIP---------------RATE---------QTY--------------TOTAL");
		System.out.println(gpu.brand+"\t \t"+gpu.Model+"\t \t"+gpu.gpuChip+"\t \t"+gpu.rate+"\t \t"+gpu.qty+"\t \t"+gpu.qty*gpu.rate);
		total+=(gpu.qty*gpu.rate);
		
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("Memory Module");
		System.out.println("---BRAND---------------MODEL--------------RATE---------QTY--------------TOTAL");
	    System.out.println(ram.brand+"\t \t"+ram.Model+"\t \t"+ram.rate+"\t \t"+ram.qty+"\t \t"+ram.qty*ram.rate);
		total+=(ram.qty*ram.rate);
		
		
	    System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("Power Supply");
		System.out.println("----MODEL----------------------DETAILS-----------------------RATE---------QTY--------------TOTAL");
		System.out.println(power.Model+"\t \t"+power.Details+"\t \t"+power.rate+"\t \t"+power.qty+"\t \t"+power.qty*power.rate);
		total+=(power.qty*power.rate);
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		
		System.out.println("Cabinets");
		System.out.println("---BRAND---------------MODEL--------------RATE---------QTY--------------TOTAL");
		System.out.println(cabinets.brand+"\t \t"+cabinets.Model+"\t \t"+cabinets.rate+"\t \t"+cabinets.qty+"\t \t"+cabinets.qty*cabinets.rate);
		total+=(cabinets.qty*cabinets.rate);
		
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("UPS");
		System.out.println("---BRAND---------------MODEL--------------RATE---------QTY--------------TOTAL");
		System.out.println(ups.brand+"\t \t"+ups.Model+"\t \t"+ups.rate+"\t \t"+ups.qty+"\t \t"+ups.qty*ups.rate);
		total+=(ups.qty*ups.rate);
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		
		System.out.println("Storage");
		System.out.println("---BRAND---------------MODEL--------------TYPE------------------------CAPACITY--------------RATE---------QTY--------------TOTAL");
	    System.out.println(storage.brand+"\t \t"+storage.Model+"\t \t"+storage.type+"\t \t"+storage.Capacity+"\t \t"+storage.rate+"\t \t"+storage.qty+"\t \t"+storage.qty*storage.rate);
		total+=(storage.qty*storage.rate);
		
		
		
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("-TOTAL:                                                 "+total+"                                              -");
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		
	}
}
