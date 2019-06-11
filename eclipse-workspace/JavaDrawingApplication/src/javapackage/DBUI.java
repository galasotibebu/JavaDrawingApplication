package javapackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;

@SuppressWarnings("serial")
public class DBUI extends JFrame implements ActionListener{
	
	JLabel dbmanaLab;
	JTextField dbmanaField;
	
	JLabel hostLab;
	JTextField hostField;
	
	JLabel portLab;
	JTextField portField;
	
	JLabel userLab;
	JTextField userField;
	
	JLabel passLab;
	JTextField passField;
	
	JLabel dbnameLab;
	JTextField dbnameField;
	
	JLabel tabLab;
	JTextField tabField;
	
	JLabel empty;
	JButton connector;
	
	public DBUI() {
		
		this.setLayout(new GridLayout(8,2));
		this.setSize(400,300);
		
		dbmanaLab = new JLabel("Database Management System: ");
		this.add(dbmanaLab);
		dbmanaField = new JTextField("mysql", 15);
		this.add(dbmanaField);
		
		hostLab = new JLabel ("Host: ");
		this.add(hostLab);
		hostField = new JTextField("localhost", 30);
		this.add(hostField);
		
		portLab = new JLabel("Port: ");
		this.add(portLab);
		portField = new JTextField("3306", 10);
		this.add(portField);
		
		userLab = new JLabel("Username: ");
		this.add(userLab);
		userField = new JTextField("root", 20);
		this.add(userField);
		
		passLab = new JLabel("Password: ");
		this.add(passLab);
		passField = new JPasswordField("admin", 20);
		this.add(passField);
		
		dbnameLab = new JLabel("Database Name: ");
		this.add(dbnameLab);
		dbnameField = new JTextField("database", 20);
		this.add(dbnameField);
		
		tabLab = new JLabel ("Table Name: ");
		this.add(tabLab);
		tabField = new JTextField(" ", 30);
		this.add(tabField);
		
		empty = new JLabel(" ");
		this.add(empty);
		
		connector = new JButton ("Connect to Database");
		this.add(connector);
		
		connector.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
