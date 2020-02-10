package by.internetshop.homework.user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.crypto.Data;

import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.db.WorkDB;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.db.WorkDB;

public class StartFrame extends JFrame {

	private JPanel panel;
	private JLabel labelUrl, labelLogin, labelPassword;
	private JTextField tfUrl, tfLogin;
	private JPasswordField tfPassword;
	private JButton create, delete, connect;

	public StartFrame() {
		setSize(250, 230);
		setTitle("Start Frame");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setVisible(true);
	}

	private void initComponents() {
		panel = new JPanel();
		labelUrl = new JLabel("URL");
		labelLogin = new JLabel("Login");
		labelPassword = new JLabel("Password");
		tfUrl = new JTextField("localhost", 20);
		tfLogin = new JTextField("root", 20);
		tfPassword = new JPasswordField("root", 20);
		create = new JButton("Create");
		delete = new JButton("Delete");
		connect = new JButton("Connect");

		panel.add(labelUrl);
		panel.add(tfUrl);
		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPassword);
		panel.add(tfPassword);
		panel.add(create);
		panel.add(delete);
		panel.add(connect);
		add(panel);
	}

	private void action() {
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						WorkDB.createDB("jdbc:mysql://" + tfUrl.getText() + "/?useUnicode=true&serverTimezone=UTC", tfLogin.getText(),
								String.valueOf(tfPassword.getPassword()));
					} catch (NoSuchAlgorithmException ex) {
					} catch (UnsupportedEncodingException ex) {
					}
					JOptionPane.showMessageDialog(panel, "Database installing successfully", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException ex) {
					System.out.println(ex);
					JOptionPane.showMessageDialog(panel, "Error intalling database \n" + ex, "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					System.out.println(ex);
					JOptionPane.showMessageDialog(panel, "Error installing dstabase \n" + ex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					WorkDB.deleteDB("jdbc:mysql://" + tfUrl.getText() + "/?useUnicode=true&serverTimezone=UTC",
							tfLogin.getText(), tfPassword.getText());
					JOptionPane.showMessageDialog(panel, "Database drop successfully", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException ex) {
					JOptionPane.showMessageDialog(panel, "Error drop database \n" + ex, "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(panel, "Error drop database \n" + ex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DataBase db = new DataBase("jdbc:mysql://" + tfUrl.getText() + "/" + WorkDB.NAME_DB + "?useUnicode=true&serverTimezone=UTC",
							tfLogin.getText(), tfPassword.getText());
					new LoginFrame(db);
					dispose();
				} catch (ClassNotFoundException ex) {
					JOptionPane.showMessageDialog(panel, "Database is not installed \n" + ex, "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(panel, "Database is not installed \n" + ex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
