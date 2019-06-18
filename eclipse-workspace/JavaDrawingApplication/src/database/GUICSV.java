package database;

import static javapackage.Main.maineditor;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javapackage.EditorTools;
/***
 * Opens file dialogue to go through directories for saving or opening CSV files
 * @author amse1013
 * 
 */
public class GUICSV {

	public static EditorTools itemeditor;

	static EditorTools neweditor;

	static String filePath;
	static String fileName;


	public GUICSV() {}


	public static void openFileChooserDialog() throws Exception {
		JFileChooser jfilechooser = new JFileChooser();
		int selectfile = jfilechooser.showOpenDialog(null);
		while (selectfile == JFileChooser.APPROVE_OPTION
				&& !jfilechooser.getSelectedFile().getName().endsWith(".csv")) {
			JOptionPane.showMessageDialog(null, "Selected file is not a csv ",
					"Error", JOptionPane.ERROR_MESSAGE);
			selectfile = jfilechooser.showOpenDialog(null);
		}

		if (selectfile == JFileChooser.APPROVE_OPTION) {
			File f = jfilechooser.getSelectedFile();
			filePath = f.getAbsolutePath();
			fileName = f.getName();
		}
	}

// For selecting directory for saving csv 

	public static void saveFileChooserDialog() throws Exception {
		JFileChooser jfilechooser = new JFileChooser();
		int selectfile = jfilechooser.showSaveDialog(null);
		while (selectfile == JFileChooser.APPROVE_OPTION
				&& !jfilechooser.getSelectedFile().getName().endsWith(".csv")) {
			JOptionPane.showMessageDialog(null, "The file " + jfilechooser.getSelectedFile() + " is not a csv file!",
					"Error", JOptionPane.ERROR_MESSAGE);
			selectfile = jfilechooser.showOpenDialog(null);
		}

		if (selectfile == JFileChooser.APPROVE_OPTION) {
			File f = jfilechooser.getSelectedFile();
			filePath = f.getAbsolutePath();
			fileName = f.getName();
		}
	}

	public static void exportAsCsv() throws Exception {
		CreateCSV createFile = new CreateCSV();
		createFile.openFile();
		createFile.addFeatures(filePath);
		createFile.closeFile();

	}

	public static void displayObjectFromCSV() throws Exception {
		DisplayCSV displayCSV = new DisplayCSV(filePath);
		neweditor = displayCSV.displayObjects();
		maineditor.overwriteObjects(neweditor);
	}

	public static String getFilePath() {
		return filePath;
	}

}
