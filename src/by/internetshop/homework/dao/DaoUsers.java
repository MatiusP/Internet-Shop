package by.internetshop.homework.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.entity.Users;
import by.internetshop.homework.entity.Users;

public class DaoUsers implements DaoIntarface<Users> {

	private DataBase db;

	public DaoUsers(DataBase db) {
		this.db = db;
	}

	@Override
	public void insert(Users ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn()
				.prepareStatement("insert into " + ob.getClass().getSimpleName()
						+ " (login,password,role,balance,block_status,delete_status)" + " values(?,?,?,?,?,?)");
		ps.setString(1, ob.getLogin());
		ps.setString(2, ob.getPassword());
		ps.setInt(3, ob.getRole());
		ps.setInt(4, ob.getBalance());
		ps.setInt(5, ob.getBlock_status());
		ps.setInt(6, ob.getDelete_status());
		ps.execute();
	}

	@Override
	public void update(Users ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement("update " + ob.getClass().getSimpleName()
				+ "set login=?,pass=?,role=?,block_status=?," + " balance=? where users_id=" + ob.getUsers_id());
		ps.setString(1, ob.getLogin());
		ps.setString(2, ob.getPassword());
		ps.setInt(3, ob.getRole());
		ps.setInt(4, ob.getBalance());
		ps.setInt(5, ob.getBlock_status());
		ps.execute();
	}

	public void updateBalance(Users ob) throws SQLException {
		int balance = 0;
		ResultSet rs = db.query("select balance from users " + "where login=" + ob.getLogin() + "");
		if (rs.next()) {
			balance = rs.getInt(1);
			PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
					"update" + ob.getClass().getSimpleName() + " set balanse=? where login =" + ob.getLogin());
			ps.setInt(1, balance + ob.getBalance());
			ps.execute();
		}
	}

	@Override
	public void softDelete(Users ob) throws SQLException {
		db.update("delete from " + ob.getClass().getSimpleName() + "where users_id=" + ob.getUsers_id());
	}

	@Override
	public void delete(Users ob) throws SQLException {
		db.update("update " + ob.getClass().getSimpleName() + "set delete_status=1 where users_id=" + ob.getUsers_id());
	}

	public Users getUser(ResultSet rs) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return new Users(rs.getInt("users_id"), rs.getString("login"), rs.getString("password"), rs.getInt("role"),
				rs.getInt("balance"), rs.getInt("block_status"));
	}
}