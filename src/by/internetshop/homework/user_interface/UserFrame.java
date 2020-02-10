package by.internetshop.homework.user_interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import by.internetshop.homework.dao.DaoGoods;
import by.internetshop.homework.dao.DaoOrders;
import by.internetshop.homework.dao.DaoUsers;
import by.internetshop.homework.db.DataBase;
import by.internetshop.homework.entity.Goods;
import by.internetshop.homework.entity.Orders;
import by.internetshop.homework.entity.Users;
import by.internetshop.homework.entity.Users;

public class UserFrame extends JFrame {

	private DataBase db;
	private JPanel panel;
	private JScrollBar scroll;
	private MyTable tableUser;
	private JButton deleteUser, addUser;
	private KeyAdapter key;
	private JPanel itemList;
	private JScrollPane scrollPane;
	private JButton logout;
	private JPanel userInfo;
	private JButton order;
	private LoginFrame login;
	private ArrayList<InternetShopItem> goodsList;
	private Users currentUser;
	private JLabel balanceLabel;

	public UserFrame(DataBase db, Users user, LoginFrame login) {
		this.goodsList = new ArrayList<InternetShopItem>();
		this.db = db;
		this.login = login;
		this.currentUser = user;
		setSize(600, 600);
		setTitle("User frame");
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.userInfo = new JPanel();
		this.userInfo.add(new JLabel("Hello, "));
		this.balanceLabel = new JLabel("" + user.getBalance() + "$");
		this.userInfo.add(new JLabel(user.getLogin() + "!"));
		this.userInfo.add(new JLabel("Your balance: "));
		this.userInfo.add(balanceLabel);
		this.logout = new JButton("LogOut");
		this.logout.setSize(100, 100);
		this.userInfo.add(this.logout);
		add(this.userInfo, BorderLayout.NORTH);

		DaoGoods daoGoods = new DaoGoods(db);
		ArrayList<Goods> goods = null;
		int position = 100;
		try {
			goods = daoGoods.selectAll();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		this.itemList = new JPanel(new GridLayout(goods.size(), 1));
		this.itemList.setPreferredSize(new Dimension(500, goods.size() * 200));
		for (Goods i : goods) {
			InternetShopItem tmp = new InternetShopItem(i, position);
			this.itemList.add(tmp);
			this.goodsList.add(tmp);
		}

		this.scrollPane = new JScrollPane(this.itemList);
		this.scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(this.scrollPane, BorderLayout.CENTER);
		this.order = new JButton("Order");
		this.order.setSize(100, 100);
		add(this.order, BorderLayout.SOUTH);
		setVisible(true);
		actions();
	}

	public void actions() {
		this.logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				login.setVisible(true);
			}
		});
		this.order.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<Goods, Integer> choosenGoods = new HashMap<>();
				int totalCost = 0;
				for (InternetShopItem shopItem : goodsList) {
					if (shopItem.isChoosen()) {
						choosenGoods.put(shopItem.getCurrentItem(), shopItem.getAmount());
						totalCost += shopItem.getCurrentItem().getPrice() * shopItem.getAmount();
					}
				}
				if (currentUser.getBalance() > totalCost) {
					int lastInsertID = 0;
					try {
						new DaoOrders(db).insert(new Orders(currentUser.getUsers_id(), "processing", 0, totalCost));
						lastInsertID = new DaoOrders(db).getLastInsertId();
					} catch (SQLException ex) {
					}
					StringBuilder sb = new StringBuilder("INSERT INTO goods_in_orders VALUES");
					for (Entry<Goods, Integer> entry : choosenGoods.entrySet()) {
						sb.append(
								"(" + lastInsertID + "," + entry.getKey().getGoods_id() + "," + entry.getValue() + ")");
					}
					String result = sb.toString();
					try {
						db.update(result.substring(0, result.length() - 1));
						balanceLabel.setText("" + (currentUser.getBalance() - totalCost) + "$");
						currentUser.setBalance(currentUser.getBalance() - totalCost);
						new DaoUsers(db).update(currentUser);
						JOptionPane.showMessageDialog(null, "Total cost:" + totalCost, "Success order", 1);
					} catch (SQLException ex) {
					}
				} else {
					JOptionPane.showMessageDialog(null, "Total cost " + totalCost + " more then your balance",
							"Fail order", 1);
				}
			}
		});
	}
}