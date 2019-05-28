package database;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javapackage.EditorTools;
import static javapackage.Main.frame;


public class GUICSV {
	
	public static EditorTools itemeditor;
	
	static EditorTools neweditor;
	
	static String filePath;
	static String fileName;
	

	public GUICSV() {	

	}

	public static void openFileChooserDialog() throws Exception {
		JFileChooser jfilechooser = new JFileChooser();
		int filechoose = jfilechooser.showOpenDialog(null);
		while( filechoose == JFileChooser.APPROVE_OPTION && !jfilechooser.getSelectedFile().getName().endsWith(".csv")) {
			JOptionPane.showMessageDialog(null, "The file " + jfilechooser.getSelectedFile() + " is not a csv file!","Error", JOptionPane.ERROR_MESSAGE);
			filechoose = jfilechooser.showOpenDialog(null);
		}
		
		if ( filechoose == JFileChooser.APPROVE_OPTION) {
			File f = jfilechooser.getSelectedFile();
			filePath = f.getAbsolutePath();
			fileName = f.getName();
		}
	}
	
	public static void saveFileChooserDialog() throws Exception {
		JFileChooser jfilechooser = new JFileChooser();
		int filechoose = jfilechooser.showSaveDialog(null);
		while( filechoose == JFileChooser.APPROVE_OPTION && !jfilechooser.getSelectedFile().getName().endsWith(".csv")) {
			JOptionPane.showMessageDialog(null, "The file " + jfilechooser.getSelectedFile() + " is not a csv file!","Error", JOptionPane.ERROR_MESSAGE);
			filechoose = jfilechooser.showOpenDialog(null);
		}
		
		if ( filechoose == JFileChooser.APPROVE_OPTION) {
			File f = jfilechooser.getSelectedFile();
			filePath = f.getAbsolutePath();
			fileName = f.getName();
		}
	}

	public static void saveObjectsToCSV() throws Exception {
		CreateCSV createFile = new CreateCSV();
		createFile.openFile();
		createFile.fillFile(filePath);
		createFile.closeFile();
		
	}
	

	public static void displayObjectFromCSV() throws Exception {
		DisplayCSV  displayCSV = new DisplayCSV(filePath);
		neweditor = displayCSV.displayObjects();
		frame.overwriteObjects(neweditor);
	}
	

	public static String getFilePath() {
		return  filePath;
	}

}

