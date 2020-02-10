package by.internetshop.homework.db;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import by.internetshop.homework.dao.DaoGoods;
import by.internetshop.homework.dao.DaoOrders;
import by.internetshop.homework.dao.DaoUsers;
import by.internetshop.homework.entity.Goods;
import by.internetshop.homework.entity.Orders;
import by.internetshop.homework.entity.Users;
import by.internetshop.homework.dao.DaoGoods;
import by.internetshop.homework.dao.DaoOrders;
import by.internetshop.homework.dao.DaoUsers;
import by.internetshop.homework.entity.Goods;
import by.internetshop.homework.entity.Orders;
import by.internetshop.homework.entity.Users;

public class WorkDB {

	public final static String NAME_DB = "InternetShop";

	public static void createDB(String url, String user, String password)
			throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException {
		DataBase db = new DataBase(url, user, password);
		db.update("create database " + NAME_DB + " DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci");
		db.update("use " + NAME_DB);

		db.update("create table users (" + "users_id int auto_increment primary key,"
				+ "login varchar(30) not null unique," + "password varchar(128) not null," + "role int(1),"
				+ "balance int," + "block_status int(1)," + "delete_status int(1))");

		DaoUsers du = new DaoUsers(db);
		du.insert(new Users("user1", "1111", 1, 5000, 0, 0));
		du.insert(new Users("user2", "2222", 1, 3500, 0, 0));
		du.insert(new Users("admin", "admin", 0, 15000, 0, 0));

		db.update("create table goods (" + "goods_id int auto_increment primary key," + "name varchar(30),"
				+ "image_path text," + "price int," + "description text," + "delete_status int(1))");

		DaoGoods dg = new DaoGoods(db);
		dg.insert(new Goods(1, "Computer", "komputer.jpg", 1000, "Very big computer", 0));
		dg.insert(new Goods(2, "Mouse", "mouse.jpg", 50, "LogiTech", 0));
		dg.insert(new Goods(3, "Keyboard", "keyboard", 125, "Logitech", 0));

		db.update("create table orders (" + "orders_id int auto_increment primary key," + "users_id int,"
				+ "payment ENUM('processing','reject','payed')," + "delete_status int(1)," + "total_cost int,"
				+ "foreign key(users_id) references users(users_id))");

		DaoOrders dor = new DaoOrders(db);
		dor.insert(new Orders(1, 1, "processing", 0, 50));
		dor.insert(new Orders(2, 3, "processing", 0, 3000));
		dor.insert(new Orders(3, 1, "reject", 0, 150));

		db.update("create table goods_in_orders(" + "orders_id int," + "goods_id int," + "count int,"
				+ "foreign key(orders_id) references orders(orders_id),"
				+ "foreign key(goods_id) references goods(goods_id))");

		db.update("insert into goods_in_orders values (1,2,5)");
		db.update("insert into goods_in_orders values (2,1,3)");
		db.update("insert into goods_in_orders values (3,3,10)");
		db.close();
	}

	public static void deleteDB(String url, String user, String password) throws SQLException, ClassNotFoundException {
		DataBase db = new DataBase(url, user, password);
		db.update("drop database " + NAME_DB);
		db.close();
	}
}