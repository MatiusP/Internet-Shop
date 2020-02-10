package by.internetshop.homework.user_interface.tables_panel;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.user_interface.MyTable;
import by.internetshop.homework.db.DataBase;

public class TableGoodsInOrdersPanel extends TablePanel {

	private int orders_id;

	public TableGoodsInOrdersPanel(DataBase db, int users_id) {
		super(db);
		this.orders_id = users_id;
		initComponents();
		scroll.setSize(250, 200);
		remove(add);
		remove(delete);
		remove(change);
	}

	@Override
	public void action() {
	}

	@Override
	public void createTable() {
		try {
			table = new MyTable(db
					.query("SELECT goods.name,goods_in_orders.count from goods JOIN goods_in_orders ON goods.goods_id=goods_in_orders.goods_id where goods_in_orders.orders_id="
							+ orders_id)) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error creating table\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}