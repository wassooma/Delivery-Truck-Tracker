package part1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Object;
import java.text.DecimalFormat;

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
public class Truck implements Serializable{
	
	private String driverName;
	private String originCity;
	private String destinationCity;
	private double grossWeight;
	private double unloadedWeight;
	private double grossIncome;
	/**
	 * an array that refers to tracking numbers of packages of type int.
	 * It works in parallel with {@link #packageNumList}. They have same length and refer simultaneously to each other.
	 */
	private int[] packageNumList;
	/**
	 * an array that refers to loaded packages of type Package.
	 * It works in parallel with {@link #grossIncome}. They have same length and refer simultaneously to each other.
	 */
	private Package [] packageInfo;
	private final static int maxPackageNum = 200;
	
	//constructors
	/**
	 * default constructor
	 */
	protected Truck(){
		driverName = null;
		originCity = null;
		destinationCity = null;
		grossWeight = 0.0;
		unloadedWeight = 0.0;
		grossIncome = 0.0;
		packageNumList = null;
		packageInfo = null;
	}
	
	//setters
	/**
	 * sets the name of the truck driver
	 * @param driverName of type String
	 */
	protected void setDriverName(String driverName) {this.driverName = driverName;}
	
	/**
	 * sets the name of the originating city
	 * @param originCity of type String
	 */
	protected void setOriginCity(String originCity) {this.originCity = originCity;}
	
	/**
	 * sets the name of the destination city
	 * @param destinationCity of type String
	 */
	protected void setDestinationCity(String destinationCity) {this.destinationCity = destinationCity;}
	
	/**
	 * sets the gross weight of the truck by summing up each package weight with a the unloaded weight of the truck.
	 * It rounds the final grossWeight to 2 decimal places
	 */
	protected void setGrossWeight (){
		grossWeight = 0.0;
		for(int i = 0; i<packageInfo.length; i++)
			this.grossWeight += packageInfo[i].getWeight();
		this.grossWeight += this.getUnloadedWeight();
		grossWeight = round(grossWeight);
	}
	
	/**
	 * sets the unloaded weight of the truck. The unloaded weight is rounded to 2 decimal places
	 * @param unloadedWeight of type double
	 */
	protected void setUnloadedWeight(double unloadedWeight) {this.unloadedWeight = round(unloadedWeight);}
	/**
	 * sets the gross income by summing up each package shipping cost.
	 * It rounds the gross income to 2 decimal places.
	 */
	protected void setGrossIncome() {
		for (int i = 0; i<packageInfo.length; i++) {
			grossIncome += packageInfo[i].getShippingCost();
		}
		grossIncome = round(grossIncome);
	}
	/**
	 * sets the tracking number of the last package in {@link #packageNumList} after extending the array by 1 null element. 
	 * @param packageNum of type integer
	 */
	protected void setPackageNumList(int packageNum) {
		packageNumList = extendArraySize(packageNumList);
		packageNumList[packageNumList.length - 1] = packageNum;
	}
	/**
	 * creates an object depending on its tracking number that the driver provides. If the last significant figure is 0: a letter is created, else if it's 1:
	 * a box is created, else if it's 2: a wooden crate is created, else if it's 3: a metal crate is created.
	 * <p>
	 * The method refers to the created package in {@link #packageInfo}'s latest element after extending the array by 1 null element.
	 * @param packageNum of type integer of type integer
	 * @throws NotAllowedToCarryException delegates the exception throwing to the calling method 
	 */
	protected void setPackageInfo(int packageNum) throws NotAllowedToCarryException{
		packageInfo = extendArraySize(packageInfo);
		if (Integer.toString(getPackageNum()).substring(Integer.toString(getPackageNum()).length()-1,Integer.toString(getPackageNum()).length()).equals("0")) {
			packageInfo[packageInfo.length - 1] = new Letter();
			packageInfo[packageInfo.length - 1].setTrackNum(packageNum);
		}
		else if (Integer.toString(getPackageNum()).substring(Integer.toString(getPackageNum()).length()-1,Integer.toString(getPackageNum()).length()).equals("1")) {
			packageInfo[packageInfo.length - 1] = new Box();
			packageInfo[packageInfo.length - 1].setTrackNum(packageNum);
		}
		else if (Integer.toString(getPackageNum()).substring(Integer.toString(getPackageNum()).length()-1,Integer.toString(getPackageNum()).length()).equals("2")) {
			packageInfo[packageInfo.length - 1] = new WoodenCrate();
			packageInfo[packageInfo.length - 1].setTrackNum(packageNum);
		}
		else if (Integer.toString(getPackageNum()).substring(Integer.toString(getPackageNum()).length()-1,Integer.toString(getPackageNum()).length()).equals("3")) {
			packageInfo[packageInfo.length - 1] = new MetalCrate();
			packageInfo[packageInfo.length - 1].setTrackNum(packageNum);
		}		
	}
	
	//getters
	protected String getDriverName() {return this.driverName;}
	protected String getOriginCity() {return this.originCity;}
	protected String getDestinationCity() {return this.destinationCity;}
	protected double getGrossWeight() {return round(this.grossWeight);}
	protected double getUnloadedWeight() {return round(this.unloadedWeight);}
	protected double getGrossIncome() {return round(this.grossIncome);}
	protected int getPackageNum() {return packageNumList[packageNumList.length-1 ];}
	protected Package[] getPackageInfo() {return packageInfo;} 
	protected static int getMaxPackageNum() {return maxPackageNum;}
	
	//display menu
	/**
	 * displays the menu to the truck driver
	 */
	protected static void displayMenu() {
		System.out.println("\nWhat would you like to do?\r\n" + 
				"\t1. Start a cargo\r\n" + 
				"\t\ta. Driver name\r\n" + 
				"\t\tb. Unload weight(kg; lb)\r\n" + 
				"\t\tc. Originating city\r\n" + 
				"\t\td. Destination city\r\n" + 
				"\t2. Load the truck with packages\r\n" + 
				"\t\ta. Package tracking number\r\n" + 
				"\t\tb. Package weight(oz; lb)\r\n" + 
				"\t\tc. Package shipping cost\r\n" + 
				"\t3. Unload a package\r\n" + 
				"\t4. The number of packages loaded\r\n" + 
				"\t5. The gross income earned by shipping of the cargo\r\n" + 
				"\t6. Weight the truck(after it has been completely loaded)\r\n" + 
				"\t7. Drive the truck to destination\r\n" +
				"\t8. Print info of loaded packages\r\n" +
				"\t9. Load random " + getMaxPackageNum() + " packages\r\n" +
				"\t0. To quit\r\n" + 
				"Please enter your choice and press <Enter>:");
	}
	
	//Interact with menu using scanners
	@SuppressWarnings("resource")
	protected void InteractMenu(){
		Scanner keyboard = new Scanner(System.in);
		Scanner fillData = new Scanner(System.in);
		this.packageInfo = new Package[0]; //initialize packageInfo array
		this.packageNumList = new int[0];  //initialize packageNumList array
		String measurementUnit;
		
		while (true) {
			displayMenu();
			int selectionNum = keyboard.nextInt();
			if (selectionNum == 0) {
				keyboard.close();
				fillData.close();
				System.out.println("Thank you for choosing our software.");
				System.exit(0);
			}
			else if (selectionNum == 1) {
				System.out.println("Enter driver's name: "); 
					this.setDriverName(fillData.nextLine());
				System.out.println("Enter unloadeded weight of truck followed by the unit of measurement: kg or lb: "); 
				System.out.println("e.g.: 1000 kg or 2204.62 lb");
					String input = fillData.nextLine();
					Scanner scanner = new Scanner(input);
					String weight = scanner.next();
					measurementUnit = scanner.next();
					if (measurementUnit.contentEquals("kg")) this.setUnloadedWeight(kgToPounds(Double.parseDouble(weight)));
					else if (measurementUnit.contentEquals("lb")) this.setUnloadedWeight(Double.parseDouble(weight));
					scanner.close();
				System.out.println("Enter originating city: "); 
					this.setOriginCity(fillData.nextLine());
				System.out.println("Enter destination city: "); 
					this.setDestinationCity(fillData.nextLine());
				continue;
			}	
			else if (selectionNum == 2) {
				try {if (this.getUnloadedWeight() == 0)throw new PackageException();
				} catch (PackageException y) {
					System.out.println("Enter unloaded weight of truck first");
					continue;
				}
				System.out.println("Enter package tracking number: "); 
					String str = fillData.nextLine();
					
				try {
					if (!Arrays.asList("0","1","2","3").contains(str.substring(str.length()-1,str.length()))) throw new NotAllowedToCarryException();
					else {
						setPackageNumList(Integer.parseInt(str));
						setPackageInfo(Integer.parseInt(str));
						
					}
				}catch (NotAllowedToCarryException a) {
					System.out.println("You are not allowed to carry this item. The item is now unloaded.");
					continue;
				}
				System.out.println("Enter package weight followed by the unit of measurement: oz or lb: ");
				System.out.println("e.g.: 10 lb or 160.00 oz");
					String input = fillData.nextLine();
					Scanner scanner = new Scanner(input);
					String weight = scanner.next();
					measurementUnit = scanner.next();
				
				try {
					if (measurementUnit.contentEquals("lb")) packageInfo[packageInfo.length - 1].setWeight(Double.parseDouble(weight));
					else if (measurementUnit.contentEquals("oz")) packageInfo[packageInfo.length - 1].setWeight(ozToPounds(Double.parseDouble(weight)));
				
					if (measurementUnit.contentEquals("lb") && Double.parseDouble(weight) > packageInfo[packageInfo.length - 1].getMaxWeight()) throw new MaxWeightExceededException();
					else if (measurementUnit.contentEquals("oz") && ozToPounds(Double.parseDouble(weight)) > packageInfo[packageInfo.length - 1].getMaxWeight()) throw new MaxWeightExceededException();
					
					if (packageInfo.length > getMaxPackageNum()) throw new TruckFullException();
					
				} 
				catch (MaxWeightExceededException a) {
					packageInfo = removeElement(packageInfo,packageInfo.length - 1);
					packageNumList = removeElement(packageNumList,packageNumList.length - 1);
					System.out.println("You have exceeded max weight for the loaded package. The package is now unloaded.");
					continue; 
				} 
				catch (TruckFullException f) {
					packageInfo = removeElement(packageInfo,packageInfo.length - 1);
					packageNumList = removeElement(packageNumList,packageNumList.length - 1);
					System.out.println("You have exceeded max allowed packages to carry. The package is now unloaded.");
					continue; 
				} 
					
				packageInfo[packageInfo.length - 1].setShippingCost();
				System.out.println("Package shipping cost: " + round(packageInfo[packageInfo.length - 1].getShippingCost()) + "$");
				scanner.close();
				continue;
			}
			else if (selectionNum == 3) {
				System.out.println("enter -1 to unload all or the tracking number of an object to unload exclusively: ");
				int nb = Integer.parseInt(fillData.nextLine());
				if (nb == -1) {
					packageInfo = null;
					packageNumList = null;
					this.packageInfo = new Package[0];
					this.packageNumList = new int[0];
					System.out.println("All items are unloaded successfully.");
					continue;
				}
				else {
					try{
						int deletedElementIndex = findIndex(packageNumList,nb);
						packageNumList = removeElement(packageNumList,deletedElementIndex);
						packageInfo = removeElement(packageInfo,deletedElementIndex);
						System.out.println("The item was unloaded successfully.");
					} catch (NegativeArraySizeException d) {
						System.out.println("Corresponding item is not found.");
					}
					continue;
				}
				
			}
			else if (selectionNum == 4) {
				System.out.println("The number of packages loaded: " + packageInfo.length);
				continue;
			}
			else if (selectionNum == 5) {
				this.setGrossIncome();
				System.out.println("The gross income earned by shipping of the cargo: $" + this.getGrossIncome());
				continue;
			}
			else if (selectionNum == 6) {
				this.setGrossWeight();
				System.out.println("Weight of the truck (after it has been completely loaded): (kg; lb) ");
				System.out.print("Enter your choice: kg to lb: ");
				String str = fillData.nextLine();
				if (str.contentEquals("kg"))System.out.println(lbToKilograms(this.getGrossWeight()) + "kg");
				if (str.contentEquals("lb"))System.out.println(this.getGrossWeight() + "lb");
				continue;
			}
			else if (selectionNum == 7) {
				System.out.println("Drive the truck to destination: from " + getOriginCity() + " to " + getDestinationCity());
				for (int i = 0; i <= 100; i++) {
		            System.out.println("Travel Progress: " + i + "%");

		            try {
		                Thread.sleep(50);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
				System.out.println("Reached Destination: " + getDestinationCity() + "\n");
		        
				String[] animationStrings = {"Weighing truck\n", "Verifying packages\n", "Writing report\n", "Verying input\n",
						"unloading packages\n", "Long assignment\n"};
				System.out.println("Generating report: ");
		        
				for (int i = 0; i <= 100; i++) {
		            int j = (int) (Math.random() + 0.1);
					
					if (j == 1) System.out.print("Processing: " + i + "% " + animationStrings[i % 6] );
					else System.out.println("Processing: " + i + "% ");
					
		            try {
		                Thread.sleep(90);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
				
				System.out.println("\nPackages unloaded\n\nElectronic receipt:");
				for (int i = 0; i<packageInfo.length; i++) System.out.println(packageInfo[i]);
				setGrossIncome(); setGrossWeight();
				System.out.println("\nGross Income: " + this.getGrossIncome() + "$" + "\nGross weight: " + this.getGrossWeight() +
						"lb, " + lbToKilograms(getGrossWeight()) + "kg");
				packageInfo = new Package[0];
				packageNumList = new int[0];
		        continue;
			}
			else if(selectionNum == 8) {
				for (int i = 0; i<packageInfo.length; i++) System.out.println(packageInfo[i]);
				if (packageInfo.length == 0) System.out.println("Nothing is loaded.");
				continue;
			}
			else if(selectionNum == 9) {
				packageInfo = new Package[0];
				packageNumList = new int[0];
				
				for(int i = 0; i < getMaxPackageNum(); i++) {
					int randomLeastSigFig = (int) (Math.random() * 4);
					int randomNum = (int) (Math.random() * 1000);
					int randomTrackNum = Integer.parseInt(String.valueOf(randomNum).substring(0,String.valueOf(randomNum).length()-1) + String.valueOf(randomLeastSigFig));
					double weight = 0;
					
					setPackageNumList(randomTrackNum);
					try {
						setPackageInfo(randomTrackNum);
					} catch (NotAllowedToCarryException e1) {
						System.err.println("An exception will NEVER be thrown in this package randomizer."
								+ " These packages were created while taking into consideration the least sig.fig. of the tracking number.");
					}
					
					if(packageInfo[i] instanceof Letter) weight = (double) (Math.random() * packageInfo[i].getMaxWeight());
					else if(packageInfo[i] instanceof Box) weight = (double) (Math.random() * packageInfo[i].getMaxWeight());
					else if(packageInfo[i] instanceof WoodenCrate) weight = (double) (Math.random() * packageInfo[i].getMaxWeight());
					else if(packageInfo[i] instanceof MetalCrate) weight = (double) (Math.random() * packageInfo[i].getMaxWeight());
					
					try{
						packageInfo[i].setWeight(round(weight));
					}catch (MaxWeightExceededException a) {
						System.err.println("An exception will NEVER be thrown in this package randomizer."
								+ " These packages were created while taking into consideration not to exceed their MaxWeight.");
					}
					
					packageInfo[i].setShippingCost();
					
		            System.out.println("Progress: " + (i + 1) + " loaded packages");
		            try {
		                Thread.sleep(20);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
				}
				
				
				System.out.println("200 packages have been loaded.");
				
				continue;
			}
		}
			
	}
	
	/**
	 * convert weight from lb to kg
	 * @param weight of type double in pounds
	 * @return weight of type double in kilograms
	 */
	protected static double lbToKilograms(double weight) {
		return Double.parseDouble(String.format("%.2f", weight/2.20462262185 ));
	}
	
	/**
	 * convert weight from kg to lb
	 * @param weight of type double in kilograms
	 * @return weight of type double in pounds
	 */
	protected static double kgToPounds(double weight) {
		return Double.parseDouble(String.format("%.2f", weight*2.20462262185 ));
	}
	
	/**
	 * convert weight from lb to oz
	 * @param weight of type double in pounds
	 * @return weight of type double in ounces
	 */
	protected static double lbToOunces(double weight) {
		return Double.parseDouble(String.format("%.2f", weight*16));
	}
	
	/**
	 * convert weight from oz to lb
	 * @param weight of type double in ounces
	 * @return weight of type double in pounds
	 */
	protected static double ozToPounds(double weight) {
		return Double.parseDouble(String.format("%.2f", weight/16));
	}
	
	/**
	 * round to 2 decimals for double integers
	 * @param number of type double
	 * @return rounded number of type double
	 */
	protected static double round(double number) {
		return Double.parseDouble(new DecimalFormat(".00").format(number));
	}
	
	/**
	 * extend Package Array's size by 1
	 * @param array of type Package[]
	 * @return an extended array of type Package[]
	 */
	private static Package[] extendArraySize(Package [] array){
		Package [] temp = array.clone();
	    array = new Package[array.length + 1];
	    System.arraycopy(temp, 0, array, 0, temp.length);
	    return array;
	}
	
	/**
	 * extend integer Array's size by 1
	 * @param array of type int[]
	 * @return an extended array of type int[]
	 */
	private static int[] extendArraySize(int [] array){
		int [] temp = array.clone();
	    array = new int[array.length + 1];
	    System.arraycopy(temp, 0, array, 0, temp.length);
	    return array;
	}
	
	/**
	 * shrink Package Array's size by 1
	 * @param arr of type Package[]
	 * @param index of type int
	 * @return a shrieked array of type Package[]
	 */
	public static Package[] removeElement(Package[] arr, int index) { 
		if (arr == null || index < 0 || index >= arr.length) return arr; 
		Package[] copy = new Package[arr.length - 1]; 
		for (int i = 0, k = 0; i < arr.length; i++) { 
			if (i == index) continue; 
			copy[k++] = arr[i]; 
		} 		
		return copy; 
	} 
	
	/**
	 * shrink integer Array's size by 1
	 * @param arr of type int[]
	 * @param index if type int
	 * @return a shrieked array of type int[]
	 */
	public static int[] removeElement(int[] arr, int index) { 
		int[] copy = new int[arr.length - 1]; 
		for (int i = 0, k = 0; i < arr.length; i++) { 
			if (i == index) continue; 
			copy[k++] = arr[i]; 
		} 		
		return copy; 
	} 
	
	/**
	 * finds the index of a specific number nb in an int[] array
	 * @param arr of type int[]
	 * @param nb of type int
	 * @return index of the specific number
	 */
	public static int findIndex(int arr[], int nb) { 
		
		int i = 0; 
	    for (int k = 0; k<arr.length; k++) { 
	    	if (arr[i] == nb)  
	    		return i; 
	    	else i = i + 1; 
	    } 
	       return -1; 
	     
	}
	
	
	
	/**
	 * Deep Copy using Serialization. It was not implemented in the other methods since the emphasis wasn't on security.
	 * @param o of type Object
	 * @return a deep copy of the Object
	 */
	
	public static Object deepCopy(Object o){
		  try {
		        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		        objectOutputStream.writeObject(o);
		        ByteArrayInputStream in = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		        ObjectInputStream objectInputStream = new ObjectInputStream(in);
		        return (Object) objectInputStream.readObject();
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		      return null;
		    }
	}
	
}
