package edu.albany.proj5;

/** Tester class for Grep
 * and Worker classes and the time taken
 * Expects command line arguments in the form 
 * LC_ALL='C' time grep -n 'word to find' /path/to/file/abc.txt
 */
public class Tester {

	public static void main(String args[]) {
		long start, end;
		Worker worker = null;
		
		try {
			//get pattern without ''
			String s = args[4].substring(1, args[4].length()-1);
			worker = new Worker(args[5],s);
			
			start = System.currentTimeMillis();
			
			String occurences = worker.findAll();
			
			end = System.currentTimeMillis();
			
			System.out.println(s + " found in " + args[5]);
			System.out.print(occurences);
			System.out.println("Time taken: " + (end - start) + "ms");
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Incorrect input given");
		}
	}
}
