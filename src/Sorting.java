import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

//A java program that sorts sets of integers with bubble sort, quick sort, table sort, and selection sort

public class Sorting {
	
	//Declaring global variables
	private File input;
	private String path;
	private Scanner userin;
	private Scanner fileReader;
	private ArrayList<Integer> nums;
	private float startTime;
	private float endTime;
	private float timeTaken;
	
	Sorting(){
		//Initializes numbers
		userin = new Scanner(System.in);
		nums = new ArrayList<Integer>();
		getFile(); 
		getNumbers(); 
		sort(); 
	}

	//Gets the file's path from the user
	public void getFile(){
		System.out.println("Enter the path to the file you want to sort: ");
		path = userin.nextLine();
		input = new File(path);
		if(!input.exists()){ // Make sure to get a correct file path
			System.out.println("Please re-enter the file path");
			getFile();
		}

		try {
			fileReader = new Scanner(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//Gets all the numbers inside the file that the user gave
	public void getNumbers(){
		while(fileReader.hasNext()){
			String current = fileReader.next();
			for(int i = 0; i < current.length(); i++)
				if(Character.isDigit(current.charAt(i)))
					nums.add(Character.getNumericValue(current.charAt(i)));
		}
	}

	//Sorts the numbers based on user input, also gets the time it took for the sorting method to work
	public void sort(){
		System.out.println("What sorting method do you want to use?(bubble, selection, table, quick)");
		String method = userin.nextLine();
		if(method.toLowerCase().equals("bubble")){
			startTime = System.currentTimeMillis();
			
			nums = bubble(nums);

			endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
		}else if(method.toLowerCase().equals("selection")){
			startTime = System.currentTimeMillis();
			
			nums = selection(nums);
			
			endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
		}else if(method.toLowerCase().equals("table")){
			startTime = System.currentTimeMillis();
			
			nums = table(nums);
			
			endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
		}else if(method.toLowerCase().equals("quick")){
			startTime = System.currentTimeMillis();
			
			nums = quick(nums, 0, nums.size() - 1);
			
			endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
		}else{
			System.out.println("That's not a sorting method. Please re-enter your choice.");
			sort();
		}
		try {
			writeOutput(method);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Bubble sorts the array
	ArrayList<Integer> bubble(ArrayList<Integer> array){
		boolean swapping = true; 
		while(swapping){
			int swaps = 0;
			int first;
			int second;
			for(int i = 0; i < array.size() - 1; i++){ 
				first = array.get(i); //While looping through the array, set the first value to whatever the index is
				second = array.get(i + 1);//One to the right of first
				
				if(first > second){ //If their in the wrong order, switch them
					array.set(i, second);
					array.set(i + 1, first);
					swaps++;
				}
			}
			//If we went through the whole array without swapping any values then it must be sorted
			if(swaps == 0)
				swapping = false;
		}
		return array;
	}

	//Sorts a given array using selection sort
	ArrayList<Integer> selection(ArrayList<Integer> array){
		//Creates a new empty array called sorted to hold all the sorted values
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		int i = 0;
		//Keeps sorting until the unsorted array is empty
		while(array.size() != 0){
			//Gets the first value in the array
			int least = array.get(i);
			//Loops through the array and finds the actual least value in the array, if it doesn't find one then the first value must be the least
			for(Integer I : array){
				if(I.intValue() < least){
					least = I.intValue();
				}
			}
			//Adds the value to sorted and removes it from the unsorted array
			sorted.add(least);
			array.remove(nums.indexOf(least));
		}
		return sorted;
	}

	//Table sorts the array
	ArrayList<Integer> table(ArrayList<Integer> array){
		
		int[] tally = new int[array.size() + 1];	
		
		for(int i = 0; i < array.size(); i++)
			tally[array.get(i)]++;
		
		int count = 0;
		
		for(int i = 0; i < tally.length; i++){
			for(int j = 0; j < tally[i]; j++){
				array.set(count, i);
				count++;
			}
		}
		return array;
	}

	//Quick sorts the array
	ArrayList<Integer> quick(ArrayList<Integer> array, int left, int right){
		//Partition the array and sets split to the splitpoint
		int split = partition(array, left, right);
		if (left < right)
			quick(array, left, split - 1);
		if (split < right)
			quick(array, split, right);

		//If l
		return array;
    }

    //A partition method that returns an int for the spitpoint of the new partitions
	public int partition(ArrayList<Integer> array, int low, int high){
      
      //Sets up the left value, right value, and the pivot used for partitioning the array
      int left = low, right = high;
      int pivot = array.get((low + high) / 2);
     	
      while (left <= right) {
      		//moves the left value towards the middle, finds a value thats larger than the pivot on the left side of the section of the array
            while (array.get(left) < pivot)
                  left++;

            //moves the right value to the middle, finds a value thats smaller than the pivot on the right side of the section of the array 
            while (array.get(right) > pivot)
                  right--;

            //when the values crossover
            if (left <= right) {
            	  //swap them and move the left and right values over
                  array = swap(array, left , right);
                  left++;
                  right--;
            }
      };
     
      return left;
    }

    //A method used by the quick sort to swap to values on the array
    public ArrayList<Integer> swap(ArrayList<Integer> array, int i, int j){
    	int temp = array.get(i);
    	array.set(i, array.get(j));
    	array.set(j, temp);

    	return array;
    }
    //A method to write the new sorted array and the time it took to the console and the file
	public void writeOutput(String method) throws IOException{
		String output = "";
		for(Integer i : nums)
			output += " " + i.intValue();
		
		File outputFile = new File("output.txt");
		if(!outputFile.exists())
			outputFile.createNewFile();
		else if(outputFile.exists()){
			outputFile.delete();
			writeOutput(method);
			return;
		}
			
		FileWriter fw = new FileWriter(outputFile.getAbsolutePath(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);
		
		out.println("\nThe " + method + " sorting took " + timeTaken + " milliseconds");
		out.println(output);
		
		out.close();
		bw.close();
		fw.close();
		
		System.out.println("\nThe " + method + " sorting took " + timeTaken + " milliseconds");
		System.out.println(output);
		System.out.println("The output was printed to " + outputFile.getAbsolutePath());
	}
	public static void main(String[] args){
		new Sorting();
	}
}
