package part1;

/**
 *Name and ID: Wasim Boughattas 40126028
 *<p>
 *COMP249
 *<p>
 *Assignment # 2
 *<p>
 *Due Date 07/26/2020 
 *
 *@author Wasim Boughattas
 */

//-----------------------------------------------------
//Assignment 2
//Part: 1
//Written by: Wasim Boughattas id: 40126028
//-----------------------------------------------------

abstract public class Package{
	
	protected abstract String printGetClass();
	protected abstract double getMaxWeight();
	
	protected int trackNum;
	protected double weight;
	protected double shippingCost;
	
	//constructors
	protected Package(){
		trackNum = 0;
		weight = 0.0;
		shippingCost = 0.0;
	}
	
	//setters
	protected void setTrackNum(int trackNum) {this.trackNum = trackNum;}
	protected void setWeight(double weight) throws MaxWeightExceededException{this.weight = Truck.round(weight);}
	protected void setShippingCost() {
		if (this instanceof Letter) this.shippingCost = Truck.round(Truck.lbToOunces(this.weight) * 0.05);
		else if (this instanceof Box) this.shippingCost = Truck.round(this.weight * 2);
		else if (this instanceof WoodenCrate) this.shippingCost = Truck.round(this.weight * 2.5);
		else if (this instanceof MetalCrate) this.shippingCost = Truck.round(this.weight * 3);
	}
	
	//getters
	protected int getTrackNum() {return this.trackNum;}
	protected double getWeight() {return this.weight;}
	protected double getShippingCost() {return this.shippingCost;}
	
	//toString
	public String toString() {
		return this.printGetClass() + ": [" + "Tracking number: " + this.getTrackNum() +
				"; " + "Weight(lb): " + this.getWeight() + "; Shipping cost: " + this.getShippingCost() +
				"$]";
	}
	
	
	
	
	
	
	
	
	
	
	
}
