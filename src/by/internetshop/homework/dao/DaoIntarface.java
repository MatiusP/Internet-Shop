package by.internetshop.homework.dao;

import java.sql.SQLException;

public interface DaoIntarface<T> {
	public void insert(T ob) throws SQLException;

	public void delete(T ob) throws SQLException;

	public void update(T ob) throws SQLException;

	public void softDelete(T ob) throws SQLException;
}