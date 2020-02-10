package by.internetshop.homework.entity;

import by.internetshop.homework.helpers.Encryption;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import static by.internetshop.homework.helpers.Encryption.encryptPassword;;

public class Users {
	private int users_id;
	private String login;
	private String password;
	private int role;
	private int balance;
	private int block_status;
	private int delete_status;

	public Users(int id) {
		this.users_id = id;
		this.login = "";
		this.password = "";
		this.role = 1;
		this.balance = 0;
		this.block_status = 0;
		this.delete_status = 0;
	}

	public Users(int id, String login, String pass, int role, int block_status, int balance)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.users_id = id;
		this.login = login;
		this.password = Encryption.encryptPassword(pass);
		this.role = role;
		this.balance = balance;
		this.block_status = block_status;
	}

	public Users(String login, String pass, int role, int balance, int block_status, int delete_status)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.login = login;
		this.password = Encryption.encryptPassword(pass);
		this.role = role;
		this.block_status = block_status;
		this.delete_status = delete_status;
		this.balance = balance;
	}

	public Users(String login, int balance) {
		this.login = login;
		this.balance = balance;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getBlock_status() {
		return block_status;
	}

	public void setBlock_status(int block_status) {
		this.block_status = block_status;
	}

	public int getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(int delete_status) {
		this.delete_status = delete_status;
	}
}