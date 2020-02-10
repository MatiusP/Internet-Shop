package by.internetshop.homework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

	private Connection cn;
	private Statement st;

	public DataBase(String url, String user, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		cn = (Connection) DriverManager.getConnection(url, user, password);
		st = (Statement) cn.createStatement();
	}

	public void update(String sql) throws SQLException {
		st.executeUpdate(sql);
	}

	public ResultSet query(String sql) throws SQLException {
		ResultSet rs = null;
		rs = st.executeQuery(sql);
		return rs;
	}

	public void showTable(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			System.out.println(rsmd.getColumnName(i) + "\t");
		}
		while (rs.next()) {
			System.out.println();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.println(rs.getString(i) + "\t");
			}
		}
	}

	public Connection getCn() {
		return cn;
	}

	public void close() throws SQLException {
		st.close();
		cn.close();
	}
}