package qpcr_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Data_Scanner {

	private Scanner sample_name;
	private Scanner target_name;
	private Scanner CT;

	// File sample_name_file = new File("sample_name.txt");

	/*
	 * Function to handle file opening. So far only has strings. input: text file
	 * output: ArrayList of strings
	 */
	public void fileOpen(File file) throws FileNotFoundException {

		Scanner data = new Scanner(file);
		ArrayList<String> results = new ArrayList<String>();
		int i = 0;

		while (data.hasNextLine()) {
			results.add(data.nextLine());
			System.out.println(results.get(i));
			i++;
		}

		data.close();
	}

	// main to test functions
	public static void main(String args[]) throws FileNotFoundException {
		File file = new File("sample_name.txt");
		Data_Scanner test = new Data_Scanner();
		test.fileOpen(file);
	}

}
