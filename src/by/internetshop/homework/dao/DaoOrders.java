package by.internetshop.homework.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.entity.Orders;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.entity.Orders;

public class DaoOrders implements DaoIntarface<Orders> {

	private DataBase db;

	public DaoOrders(DataBase db) {
		this.db = db;
	}

	@Override
	public void insert(Orders ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement("insert into "
				+ ob.getClass().getSimpleName() + "(users_id,payment,delete_status,total_cost) " + "values(?,?,?,?)");
		ps.setInt(1, ob.getUsers_id());
		ps.setString(2, ob.getPayment());
		ps.setInt(3, ob.getDelete_status());
		ps.setInt(4, ob.getTotal_cost());
		ps.execute();
	}

	@Override
	public void update(Orders ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
				"update" + ob.getClass().getSimpleName() + "set payment=? where orders_id=" + ob.getOrders_id());
		ps.setString(1, ob.getPayment());
		ps.execute();
	}

	@Override
	public void softDelete(Orders ob) throws SQLException {
		db.update("delete from" + ob.getClass().getSimpleName() + "where orders_id=" + ob.getOrders_id());
	}

	@Override
	public void delete(Orders ob) throws SQLException {
		db.update(
				"update" + ob.getClass().getSimpleName() + "set delete_status=1 where orders_id=" + ob.getOrders_id());
	}

	public int getLastInsertId() throws SQLException {
		ResultSet rs = db.query("SELECT LAST_INSERT_ID() FROM orders");
		rs.next();
		return rs.getInt(1);
	}
}