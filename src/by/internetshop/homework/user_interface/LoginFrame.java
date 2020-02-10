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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import by.internetshop.homework.dao.DaoUsers;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.helpers.Encryption;
import by.internetshop.homework.dao.DaoUsers;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.helpers.Encryption;

public class LoginFrame extends JFrame {
	private JPanel panel;
	private JLabel labelLogin, labelPassword;
	private JTextField tfLogin;
	private JPasswordField tfPassword;
	private JButton enter, registration;
	private DataBase db;
	private LoginFrame login;

	public LoginFrame(DataBase db) {
		this.db = db;
		setSize(250, 230);
		setTitle("Login Frame");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setVisible(true);
		login = this;
	}

	private void initComponents() {
		panel = new JPanel();
		labelLogin = new JLabel("Login");
		labelPassword = new JLabel("Password");
		tfLogin = new JTextField("admin", 20);
		tfPassword = new JPasswordField("admin", 20);
		enter = new JButton("Enter");
		registration = new JButton("Registrarion");
		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPassword);
		panel.add(tfPassword);
		panel.add(enter);
		panel.add(registration);
		add(panel);
	}

	private void action() {
		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ResultSet rs = db
							.query("select * from users where login='" + tfLogin.getText() + "' and delete_status=0");
					if (rs.next()) {
						try {
							if (Encryption.encryptPassword(String.valueOf(tfPassword.getPassword()))
									.equals(rs.getString("password"))) {
								DaoUsers du = new DaoUsers(db);
								if (rs.getInt("role") == 1) {
									new UserFrame(db, du.getUser(rs), login);
									dispose();
								} else if (rs.getInt("role") == 0) {
									new AdminFrame(db);
									dispose();
								}
							} else {
								JOptionPane.showMessageDialog(panel, "Incorrect password", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						} catch (NoSuchAlgorithmException ex) {
						} catch (UnsupportedEncodingException ex) {
						}
					} else {
						JOptionPane.showMessageDialog(panel, "Incorrect login", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(panel, "Error in database\n" + ex, "Error",
							JOptionPane.ERROR_MESSAGE);
					;
				}
			}
		});

		registration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new RegistrationFrame(db);
				dispose();
			}
		});
	}
}