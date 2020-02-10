package by.internetshop.homework.user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.internetshop.homework.dao.DaoUsers;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.entity.Users;
import by.internetshop.homework.entity.Users;

public class RegistrationFrame extends JFrame {

	private JPanel panel;
	private JLabel labelLogin, labelPassword;
	private JTextField tfLogin, tfPassword;
	private JButton registrarion;
	private DataBase db;

	public RegistrationFrame(DataBase db) {
		this.db = db;
		setSize(250, 230);
		setTitle("Registration frame");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setVisible(true);
	}

	private void initComponents() {
		panel = new JPanel();
		labelLogin = new JLabel("Login");
		labelPassword = new JLabel("Password");
		tfLogin = new JTextField(20);
		tfPassword = new JTextField(20);
		registrarion = new JButton("Registration");
		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPassword);
		panel.add(tfPassword);
		panel.add(registrarion);
		add(panel);
	}

	private void action() {
		registrarion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tfLogin.getText().equals("") || tfPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(panel, "Incorrect login or password", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						ResultSet rs = db.query("select * from users " + "where login='" + tfLogin.getText() + "'");
						if (rs.next()) {
							JOptionPane.showMessageDialog(panel, "This username is not available", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							DaoUsers daoUser = new DaoUsers(db);
							try {
								daoUser.insert(new Users(tfLogin.getText(), tfPassword.getText(), 1, 0, 0, 0));
								JOptionPane.showMessageDialog(panel, "Registrarion successful", "Message",
										JOptionPane.INFORMATION_MESSAGE);
								new LoginFrame(db);
								dispose();
							} catch (NoSuchAlgorithmException ex) {
							} catch (UnsupportedEncodingException ex) {
							}
						}
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(panel, "Error accessing" + " database\n" + ex, "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}