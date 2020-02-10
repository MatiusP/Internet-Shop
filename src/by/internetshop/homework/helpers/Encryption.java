package by.internetshop.homework.helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Encryption {

	public static String encryptPassword(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String password = "";
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(pass.getBytes("UTF-8"));
		password = byteToHex(crypt.digest());
		return password;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}