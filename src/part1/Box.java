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

public class Box extends Package{
	private final double maxWeight = 40.0;
	
	//constructors
	protected Box() {super();}
	
	//getter
	protected double getMaxWeight() {return maxWeight;}
	
	protected String printGetClass() {
		return "Box";
	}
	
}
