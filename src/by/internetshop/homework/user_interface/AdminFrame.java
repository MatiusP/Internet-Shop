package by.internetshop.homework.user_interface;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.user_interface.tables_panel.TableGoodsPanel;
import by.internetshop.homework.user_interface.tables_panel.TableOrdersPanel;
import by.internetshop.homework.user_interface.tables_panel.TablePanel;
import by.internetshop.homework.user_interface.tables_panel.TableUsersPanel;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.user_interface.tables_panel.TableOrdersPanel;

public class AdminFrame extends JFrame {

	private DataBase db;
	private TablePanel panelUsers, panelGoods, panelOrders;
	private JTabbedPane tabbedTables;

	public AdminFrame(DataBase db) {
		this.db = db;
		setSize(750, 480);
		setTitle("Admin frame");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
	}

	private void initComponents() {
		panelUsers = new TableUsersPanel(db);
		panelGoods = new TableGoodsPanel(db);
		panelOrders = new TableOrdersPanel(db);
		tabbedTables = new JTabbedPane();
		tabbedTables.add("Users", panelUsers);
		tabbedTables.add("Goods", panelGoods);
		tabbedTables.add("Orders", panelOrders);
		add(tabbedTables);
	}

	private void action() {
		tabbedTables.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				panelUsers.updateTable();
				panelGoods.updateTable();
				panelOrders.updateTable();
			}
		});
	}
}