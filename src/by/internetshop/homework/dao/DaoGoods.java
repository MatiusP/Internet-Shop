package by.internetshop.homework.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.entity.Goods;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.entity.Goods;

public class DaoGoods implements DaoIntarface<Goods> {

	private DataBase db;

	public DaoGoods(DataBase db) {
		this.db = db;
	}

	@Override
	public void insert(Goods ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn()
				.prepareStatement("insert into " + ob.getClass().getSimpleName()
						+ " (name,image_path,price,description,delete_status)" + " values(?,?,?,?,?)");
		ps.setString(1, ob.getName());
		ps.setString(2, ob.getImagePath());
		ps.setInt(3, ob.getPrice());
		ps.setString(4, ob.getDescription());
		ps.setInt(5, ob.getDelete_status());
		ps.execute();
	}

	@Override
	public void update(Goods ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement("update" + ob.getClass().getSimpleName()
				+ "set.name=?,price=?description=?" + "where goods_id=" + ob.getGoods_id());
		ps.setString(1, ob.getName());
		ps.setInt(2, ob.getPrice());
		ps.setString(3, ob.getDescription());
		ps.execute();
	}

	@Override
	public void softDelete(Goods ob) throws SQLException {
		db.update("delete from" + ob.getClass().getSimpleName() + "where goods_id=" + ob.getGoods_id());
	}

	@Override
	public void delete(Goods ob) throws SQLException {
		db.update("update" + ob.getClass().getSimpleName() + "set delete_status=1 where goods_id=" + ob.getGoods_id());
	}

	public ArrayList<Goods> selectAll() throws SQLException {
		ArrayList<Goods> arrList = new ArrayList<Goods>();
		ResultSet rs = this.db.query("SELECT * FROM goods");
		while (rs.next()) {
			arrList.add(new Goods(rs.getInt("goods_id"), rs.getString("name"), rs.getString("image_path"),
					rs.getInt("price"), rs.getString("description"), rs.getInt("delete_status")));
		}
		return arrList;
	}

	public void setImagePath(Goods ob) throws SQLException {
		System.out.println(ob.getImagePath());
		db.update("update" + ob.getClass().getSimpleName() + "set image_path=" + ob.getImagePath().replace("\\", "\\/")
				+ "where goods_id=" + ob.getGoods_id());
	}
}