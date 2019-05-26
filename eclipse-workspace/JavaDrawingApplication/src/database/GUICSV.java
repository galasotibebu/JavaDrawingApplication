package database;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javapackage.EditorTools;
import static javapackage.Main.frame;

@SuppressWarnings("serial")
public class GUICSV {
	
	public static EditorTools itemeditor;
	
	JButton save;
	JButton open;
	
	EditorTools neweditor;
	
	static String filePath;
	static String fileName;
	
	/**
	 * Setting setLayout() method to the CSV interface  which contains the elements of CSV interface 
	 * @author 
	 */
	public GUICSV() {	
		//setLayout();
	}
	
	/**
	 * Defining the Layout of CSV interface 
	 * @author 
	 */
	/*private void setLayout() {
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		
		save 	= new JButton("Save");
		save.setBackground(Color.decode("#e6ffb3"));
		save.setPreferredSize(new Dimension(130, 75));
		add(save);
		
		open 	= new JButton("Open");
		open.setBackground(Color.decode("#a3bbf7"));
		open.setPreferredSize(new Dimension(130, 75));
		add(open);
		
		save.addActionListener(this);
		open.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,123);
		
	}*/

	
	
	/**
	 * Defining openFileChooserDialog for opening the CSV file
	 * @throws Exception
	 * @author 
	 */
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
	
	/**
	 * Defining saveFileChooserDialog for saving the CSV file 
	 * @throws Exception
	 * @author 
	 */
	public void saveFileChooserDialog() throws Exception {
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
	
	/**
	 * Saving objects to the CSV file with name and path 
	 * @throws Exception
	 * @author 
	 */
	public void saveObjectsToCSV() throws Exception {
		CreateCSV createFile = new CreateCSV();
		createFile.openFile();
		createFile.fillFile(filePath);
		createFile.closeFile();
		JOptionPane.showMessageDialog(null, "Saved as " + fileName + " in " + filePath);
	}
	
	/**
	 * Displaying objects from CSV file 
	 * @throws Exception
	 * @author 
	 */
	public void displayObjectFromCSV() throws Exception {
		DisplayCSV  displayCSV = new DisplayCSV(filePath);
		neweditor = displayCSV.displayObjects();
		frame.overwriteObjects(neweditor);
	}
	
	/**
	 * Getting file path 
	 * @return
	 * @author 
	 */
	public static String getFilePath() {
		return  filePath;
	}

}

