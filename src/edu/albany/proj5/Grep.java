package edu.albany.proj5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Finds all the lines with occurrences of the given Pattern and saves them in
 * a StringBuilder. Designed for use with threads.
 * Occurrence lines are accessed by using the toString method
 */
public class Grep implements Runnable {

	private File file;
	private int lineNumber;
	private int stopLine;
	private Pattern pattern;
	private StringBuilder stringBuilder;
	
	/** Constructor sets all the fields and intializes StringBuilder
	 * @param file
	 * @param lineNumber - line number of file to start search
	 * @param pattern
	 * @param stopLine - line number of file to stop searching
	 */
	public Grep(File file, int lineNumber, Pattern pattern, int stopLine) {
		this.file = file;
		this.lineNumber = lineNumber;
		this.pattern = pattern;
		this.stopLine = stopLine;
		stringBuilder = new StringBuilder();
	}
	
	/* Searches the file for each line that the Pattern occurs in
	 * Adds the line number and the line to the stringBuilder
	 */
	@Override
	public void run() {
		String line = null;
		//read first line at lineNumber
		try {
			line = Files.readAllLines(Paths.get(file.getName())).get(lineNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(line!= null) {
			Matcher matcher = pattern.matcher(line);
			if(matcher.find()) {
				stringBuilder.append((lineNumber + 1) + ": " + line);
				stringBuilder.append("\n");
			}
			lineNumber += 1;
			if(lineNumber < stopLine) {
				try {
					line = Files.readAllLines(Paths.get(file.getName())).get(lineNumber);
				} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
				}
			} else {
				line = null;
			}
		}
	}
	
	/* Converts the StringBuilder to a String
	 * @return - the String
	 */
	public String toString() {
		return stringBuilder.toString();
	}
	
}
