package edu.albany.proj5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

/** Uses the Grep class to Create two threads and
 * search a file for occurrences of a pattern and returns the lines that
 * it occurs on
 */
public class Worker {

	private Pattern pattern;
	private File file;
	
	
	/** Constructor that sets the file and Pattern
	 * @param fileName
	 * @param expression
	 */
	public Worker(String fileName, String expression) {
		file = new File(fileName);
		pattern = Pattern.compile(expression);
	}
	
	/** Finds all lines that the pattern occurs on using 2 threads and 
	 * returns them as a String
	 * @return
	 */
	public String findAll() {
		//get the number of lines in the file
		int numLines = Worker.countLines(file);
		//get the middle to split up threads
		int middle = numLines/2;
		
		//Create two Grep objects
		Runnable first = new Grep(file, 0, pattern, middle-1);
		Runnable second = new Grep(file, middle, pattern, numLines);
		
		Thread thread1 = new Thread(first);
		Thread thread2 = new Thread(second);
		
		//start the threads
		thread1.start();
		thread2.start();
		
		//call join to make sure the threads both finish
		try {
			thread1.join();
		} catch (InterruptedException e) {
			System.out.println("Thread join error occurred");
			e.printStackTrace();
		}
		return "" + first + second;
	}

	/** Counts the number of lines in a file
	 * @param file
	 * @return - number of lines
	 */
	public static int countLines(File file) {
		Scanner scanner;
		int count = 0;
		//loop through each line and increment counter
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				count++;
				scanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return count;
	}
}
