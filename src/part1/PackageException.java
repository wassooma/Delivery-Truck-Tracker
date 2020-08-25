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

@SuppressWarnings("serial")
public class PackageException extends Exception{
	protected PackageException() {
		super("PackageException");
	}
	protected PackageException(String s) {
		super("PackageException");
	}
}

@SuppressWarnings("serial")
class TruckFullException extends PackageException{
	protected TruckFullException() {
		super("TruckFullException");
	}
}

@SuppressWarnings("serial")
class NotAllowedToCarryException extends PackageException{
	protected NotAllowedToCarryException() {
		super("NotAllowedToCarryException");
	}
}

@SuppressWarnings("serial")
class MaxWeightExceededException extends PackageException{
	protected MaxWeightExceededException() {
		super("MaxWeightExceededException");
	}
}