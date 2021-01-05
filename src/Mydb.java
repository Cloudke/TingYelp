import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Mydb {

	static Connection connection = null;
	static String databaseName = "JiewenYing";
	static String url = "jdbc:mysql://jiewenyingmysql.c2hzoc0o1r4x.us-east-1.rds.amazonaws.com:3306/"+databaseName;
	static String username = "JiewenYing";
	static String password = "12fdzbhy";
	static Vector<String> RestName = new Vector<String>();
	Vector productV = new Vector();
	Vector priceV = new Vector();
	static Statement stmt = null;

	   public static void run(){     
		   try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动");
			e.printStackTrace();
		}
		   
		   try {
			connection = DriverManager.getConnection(url, username, password);
			if(!connection.isClosed()) {
				System.out.println("数据库连接成功");
			}
		} catch (SQLException e) {
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}


		try {
			stmt = connection.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		   String sql;
		   sql = "select RName from Restaurant";
		   ResultSet ret = null;
		   

		try {
			ret = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(ret.next()) {
				   String name = null;
				try {
					name = ret.getString("RName");
					System.out.println(name);
					RestName.addElement(name);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	   }
	   
	   
	   void loadMenu(String a) {		
				System.out.println(a);
				String productSql;
				productSql ="select Product from "+a+",Restaurant where Restaurant.idRestaurant="+a+".Restaurant";
				ResultSet productSet =null;
				
				try {
					productSet = stmt.executeQuery(productSql);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				try {
				
					while(productSet.next()) {
						String product=null;
						try {
							product = productSet.getString("Product");
							productV.addElement(product);
							System.out.println(product);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					   }
				} catch (SQLException e) {
					e.printStackTrace();
				}//Load productName
				
				
				
				String priceSql;
				priceSql ="select Price from "+a+",Restaurant where Restaurant.idRestaurant="+a+".Restaurant";
				ResultSet priceSet =null;
				
				try {
					priceSet = stmt.executeQuery(priceSql);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				try {
				
					while(priceSet.next()) {
						String price =null;
						try {
							price = priceSet.getString("Price");
							productV.addElement(price);
							System.out.println(price);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					   }
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
	   
}
