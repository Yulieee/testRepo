package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

public class FileSeparator {

	public static void main(String[] args) {
		selectFile();
	}

	public static void selectFile() {

		JFileChooser jfc = new JFileChooser("/Users/yulia/Desktop/Batch2/");

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			readFile(selectedFile);
		}
	}

	public static void readFile(File file) {
		List<String> list = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;

			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
		processList(list);

	}

	public static void processList(List<String> list) {

		// String to be scanned to find the pattern.
		String pattern = "^\\d{7}_\\d+$";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		for (int i = 0; i < list.size(); i++) {

			Matcher m = r.matcher(list.get(i));

			if (m.find()) {

				ArrayList<String> txtFile = new ArrayList<String>();
				txtFile.add(list.get(i) + "\n");

				for (int j = i + 1; j < list.size(); j++) {

					Matcher m2 = r.matcher(list.get(j));

					if (!m2.find()) {
						txtFile.add(list.get(j) + "\n");
					} else {
						writeToFile(txtFile);
						break;
					}
				}
				writeToFile(txtFile);

			}
		}
	}

	public static void writeToFile(ArrayList<String> arr) {

		FileWriter writer;

		try {

			String header = "Filename: " + arr.get(0) + "Language: rus\n" + "Transcriber: Yulia Brown\n"
					+ "Comment: hum, ahn, ah, eh, um signal hesitation; x or xxx represent incomprehensible material\n"
					+ "Transcription Time: 1 minute\n\n";

			File newFile = new File("/Users/yulia/Desktop/Batch2/" + arr.get(0).trim() + ".txt");

			writer = new FileWriter(newFile);
			writer.write(header);

			for (int i = 1; i < arr.size(); i++) {
				writer.append(arr.get(i));
			}

			writer.close();

		} catch (IOException e) {
			System.out.println("Error writing file");
		}
	}
}
