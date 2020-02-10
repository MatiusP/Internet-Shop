package by.internetshop.homework.user_interface;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.db.DataBase;

public class MyTable extends JTable {

	private DefaultTableModel model;

	public DefaultTableModel getModel() {
		return model;
	}

	public MyTable(ResultSet rs) {
		model = new DefaultTableModel();
		try{
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++){
				model.addColumn(rsmd.getColumnName(i));
			}
			while (rs.next()){
				Vector v = new Vector();
				for (int i = 1; i <= rsmd.getColumnCount(); i++){
					v.add(rs.getString(i));
				}
				model.addRow(v);
			}
			setModel(model);
			setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		}catch (SQLException ex){
			Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}