package by.internetshop.homework.user_interface.tables_panel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.user_interface.MyTable;
import by.internetshop.homework.db.DataBase;

public abstract class TablePanel extends JPanel {

	protected DataBase db;
	protected JScrollPane scroll;
	protected MyTable table;
	protected JButton delete, add, change;

	public TablePanel(DataBase db) {
		this.db = db;
		setSize(500, 500);
		setLayout(null);
	}

	public void initComponents() {
		createTable();
		scroll = new JScrollPane(table);
		scroll.setBounds(20, 20, 680, 300);
		delete = new JButton("Delete");
		add = new JButton("Add");
		change = new JButton("Change");
		add.setBounds(20, 350, 200, 20);
		change.setBounds(260, 350, 200, 20);
		delete.setBounds(495, 350, 200, 20);
		add(scroll);
		add(add);
		add(delete);
		add(change);
	}

	public abstract void action();

	public abstract void createTable();

	public void updateTable() {
		remove(scroll);
		createTable();
		scroll = new JScrollPane(table);
		scroll.setBounds(20, 20, 680, 300);
		add(scroll);
		updateUI();
	}
}