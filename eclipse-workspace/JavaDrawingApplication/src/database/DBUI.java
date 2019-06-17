package database;

import database.DatabaseLogin;
import static javapackage.Main.frame;
import javapackage.EditorTools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DBUI extends JFrame implements ActionListener{
	
	DatabaseLogin dblogin;
	EditorTools editor;
	EditorTools neweditor;
	
	JLabel dbmanaLab;
	JTextField dbmanaField;
	
	JLabel hostLab;
	JTextField hostField;
	
	JLabel portLab;
	JTextField portField;
	
	JLabel userLab;
	JTextField userField;
	
	JLabel passLab;
	JPasswordField passField;
	
	JLabel dbnameLab;
	JTextField dbnameField;
	
	JLabel tabLab;
	JTextField tabField;
	
	JLabel empty1;
	JButton connector;
	
	JLabel empty2;
	JButton save;
	
	JLabel empty3;
	JButton importdb;
	
	public DBUI() {
		this.editor = frame.editor;
		setLayout();
	}

	private void setLayout() {
		
		GridLayout layout = new GridLayout(10,2);
		setLayout(layout);
		//setLayout(new GridLayout(10,2));
		setSize(400,300);
		
		dbmanaLab = new JLabel("Database Management System: ");
		add(dbmanaLab);
		dbmanaField = new JTextField("mysql", 20);
		add(dbmanaField);
		
		hostLab = new JLabel ("Host: ");
		add(hostLab);
		hostField = new JTextField("localhost", 20);
		add(hostField);
		
		portLab = new JLabel("Port: ");
		add(portLab);
		portField = new JTextField("3306", 20);
		add(portField);
		
		userLab = new JLabel("Username: ");
		add(userLab);
		userField = new JTextField("root", 20);
		add(userField);
		
		passLab = new JLabel("Password: ");
		add(passLab);
		passField = new JPasswordField("admin", 20);
		add(passField);
		
		dbnameLab = new JLabel("Database Name: ");
		add(dbnameLab);
		dbnameField = new JTextField("objects", 20);
		add(dbnameField);
		
		tabLab = new JLabel ("Table Name: ");
		add(tabLab);
		tabField = new JTextField("cadproject", 20);
		tabField.setEditable(false);
		add(tabField);
		
		empty1 = new JLabel();
		add(empty1);
		connector = new JButton ("Connect to Database");
		add(connector);
		
		empty2 = new JLabel(" ");
		add(empty2);
		save = new JButton("Save to Database");
		add(save);
		
		empty3 = new JLabel(" ");
		add(empty3);
		importdb = new JButton("Import from Database");
		add(importdb);
		
		connector.addActionListener(this);
		save.addActionListener(this);
		importdb.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eTarget = e.getSource();
		
		if( eTarget.equals(connector)) {
			connectDatabase();
		} else if ( eTarget.equals(save)) {
			try {
				save();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (eTarget.equals(importdb)) {
			try {
				neweditor = dblogin.extractObjects();
				frame.overwriteObjects(neweditor);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}

	public void connectDatabase() {
		try {
			getConnection();
			empty1.setVisible(true);
			empty1.setForeground(Color.GREEN);
			empty1.setText("Successfully connected");
		} catch (SQLException e) {
			empty1.setText("No connection");
			empty1.setForeground(Color.RED);
			empty1.setVisible(true);
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		dblogin = new DatabaseLogin();
		dblogin.DBMS = (String) dbmanaField.getText();
		dblogin.dbName = (String) dbnameField.getText();
		dblogin.dbHost = (String) hostField.getText();
		dblogin.dbPort = (String) portField.getText();
		dblogin.dbUser = (String) userField.getText();
		dblogin.dbPassword = (String) passField.getText();

		
		return dblogin.accessorConnection();
	}
	
	/**
	 * Save objects to the database by creating table 
	 * @throws SQLException
	 * @author 
	 */
	public void save() throws SQLException {
		String dbName = dbnameField.getText();
		createTable();
		deleteEntries();
		dblogin.insertObjects(neweditor);
		JOptionPane.showMessageDialog(null, "Saved to cadproject in " + dbName);
	}
	
	/**
	 * Creates a table 'toolobject_db' in the database if this table doesn't already exist
	 * [Added in the course of the Module Integration Test]
	 * @throws SQLException
	 * @author 
	 */
	public void createTable() throws SQLException {
		Connection connection = getConnection();
		PreparedStatement createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+
				"cadproject (gid int NOT NULL AUTO_INCREMENT, "
					+ "type varchar(10), "
					+ "geom longtext, "
					+ "PRIMARY KEY(gid))");
		createTable.executeUpdate();
	}
	
	/**
	 * Delete Entries and truncate toolobjects_db database 
	 * @throws SQLException
	 * @author ghsa1011
	 */
	public void deleteEntries() throws SQLException {
		Connection connection = getConnection();
		PreparedStatement truncateDatabase = connection.prepareStatement("TRUNCATE cadproject");
		truncateDatabase.executeUpdate();
	}
}
