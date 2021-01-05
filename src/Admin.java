import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Admin extends Application {
	Stage stage = new Stage();
	String selectedR=null;
	static Connection connection = null;
	static String databaseName = "JiewenYing";
	static String url = "jdbc:mysql://jiewenyingmysql.c2hzoc0o1r4x.us-east-1.rds.amazonaws.com:3306/"+databaseName;
	static String username = "JiewenYing";
	static String password = "12fdzbhy";
	static Statement stmt = null;

	
	public void start(int n,Vector b)  {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find Driver");
			e.printStackTrace();
		}
		   
		   try {
			connection = DriverManager.getConnection(url, username, password);
			if(!connection.isClosed()) {
				System.out.println("DB Connected for Admin");
			}
		} catch (SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
		}
		   

		
		VBox bp = new VBox();
		GridPane gridM = new GridPane(); 
		GridPane gridR = new GridPane();
		bp.getChildren().add(gridM);
	    Separator line =new Separator();
	    bp.getChildren().add(line);
		bp.getChildren().add(gridR);
		
		Text addR = new Text("Edit Restaurant ");
		addR.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		Text name = new Text("Name");
		Text x = new Text("XCoord");
		Text y = new Text("YCoord");
		Text open = new Text("Open Time");
		Text close = new Text("Close on");
		TextField inputName = new TextField();
		TextField inputX = new TextField();
		TextField inputY = new TextField();
		TextField inputOpen = new TextField();
		TextField inputClose = new TextField();
		Button ar = new Button("Add");
		Button dr = new Button("Delete");
		Button ur = new Button("Update");
		gridR.setPadding(new Insets(10,0,0,10));
		gridR.add(addR, 5, 1);
		gridR.add(name, 5, 2);
		gridR.add(inputName, 5, 3);
		gridR.add(x, 6, 2);
		gridR.add(y, 7, 2);
		gridR.add(inputX, 6, 3);
		gridR.add(inputY, 7, 3);
		gridR.add(open, 5, 4);
		gridR.add(close, 6, 4);
		gridR.add(inputOpen, 5, 5);
		gridR.add(inputClose, 6, 5);
		gridR.add(ar, 5, 15);
		gridR.add(dr, 6, 15);
		gridR.add(ur, 7, 15);
		gridR.setVgap(2);
		gridM.setVgap(2);
		gridM.setHgap(2);
		
		
		
		
		
		
		
		
		
//Menu part		
		Set s = new HashSet();
		for(int i=0;i<b.size();i++) {	
			s.add(b.elementAt(i));
		}
		
	
		Iterator it = s.iterator();
		int count=0;
		while(it.hasNext()) {	
			RadioButton rb = new RadioButton();
			
			rb.setText(it.next().toString());
			rb.autosize();
			gridM.add(rb, 0, count+1);
			count++;
			rb.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					selectedR=rb.getText();
				}
				
			});
			
		}
		
		
//add Restaurant		
		ar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					PreparedStatement ps =connection.prepareStatement("insert into Restaurant (RName,XCoord,YCoord,Open,Close) values(?,?,?,?,?)");
			//		ps.setInt(1, newRid);
					ps.setString(1, inputName.getText());
					ps.setString(2, inputX.getText());
					ps.setString(3, inputY.getText());
					ps.setString(4, inputOpen.getText());
					ps.setString(5, inputClose.getText());
					
					int i = ps.executeUpdate();
					System.out.println(i);				
					
					if(!s.contains(inputName.getText())) {
						b.addElement(inputName.getText());
						String ct ="CREATE TABLE "+inputName.getText()+
									"(idRestaurant INT NOT NULL AUTO_INCREMENT,"+
									"Rid VARCHAR(45) NOT NULL,"+
									"Product VARCHAR(45) NOT NULL,"+
									"Price DECIMAL(4,2) NOT NULL,"+
									"PRIMARY KEY (idRestaurant),"+
									"UNIQUE INDEX Product_UNIQUE (Product ASC))";
						Statement stmt=null;
						stmt=connection.createStatement();
						stmt.executeUpdate(ct);
						System.out.println("A New Table is created");
						
					}
					
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}				
				if(!stage.isShowing()) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
		
//deleteRestaurant
		
		dr.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					PreparedStatement ps =connection.prepareStatement("delete from Restaurant where xCoord=? AND yCoord=?");
					ps.setString(1, inputX.getText());
					ps.setString(2, inputY.getText());

					int i = ps.executeUpdate();
					System.out.println(i);
				} catch (SQLException e) {
					e.printStackTrace();
				}				
				if(!stage.isShowing()) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
		

//update Restaurant
		
		ur.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					PreparedStatement ps =connection.prepareStatement("update Restaurant set Open = ?,Close =? where XCoord = ? AND YCoord = ?");
					ps.setString(1, inputOpen.getText());
					ps.setString(2, inputClose.getText());
					ps.setInt(3,Integer.parseInt(inputX.getText()));
					ps.setInt(4,Integer.parseInt(inputY.getText()));
					int i = ps.executeUpdate();
					System.out.println(i);
				} catch (SQLException e) {
					e.printStackTrace();
				}				
				
				if(!stage.isShowing()) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		Scene sc = new Scene(bp,550,400);
		Text addupcourse = new Text("Add/Update Course");
		TextField inputCourse = new TextField();
		Text price = new Text("Price");
		TextField inputPrice = new TextField();
		Text deletecourse = new Text("Delete Course");
		TextField deleteCourse = new TextField();
		gridM.setPadding(new Insets(10,10,10,10));
		

		gridM.add(addupcourse, 3, 0);
		gridM.add(price, 4, 0);
		gridM.add(inputCourse, 3, 1);
		gridM.add(inputPrice, 4, 1);
		gridM.add(deletecourse, 3, 2);
		gridM.add(deleteCourse, 3, 3);
		
		
		Text course = new Text("Edit Course ");
		course.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		gridM.add(course, 0, 0);
		Button ac = new Button("Add Course");
		gridM.add(ac, 6, 1);
		
		ac.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					PreparedStatement ps =connection.prepareStatement("insert into "+selectedR+"(Rid,Product,Price) values(?,?,?)");
					ps.setInt(1, 10);
					ps.setString(2, inputCourse.getText());
					ps.setDouble(3, Double.parseDouble(inputPrice.getText()));
					int i = ps.executeUpdate();
					System.out.println(i);
				} catch (SQLException e) {
					e.printStackTrace();
				}				
				if(!stage.isShowing()) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
		
		});
		
		
		Button dc = new Button("Delete Course");
		gridM.add(dc, 6, 3);
		
		dc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					PreparedStatement ps =connection.prepareStatement("delete from "+selectedR+" where Product = ?");
					ps.setString(1,deleteCourse.getText());
					int i = ps.executeUpdate();
					System.out.println(i);
				} catch (SQLException e) {
					e.printStackTrace();
				}				
				
				if(!stage.isShowing()) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		
		});
		
		
//updatecourse
	
		Button uc = new Button("Update Course");
		gridM.add(uc, 6, 5);
		uc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					PreparedStatement ps =connection.prepareStatement("update "+selectedR+" set Price = ? where Product = ?");
					System.out.println(selectedR);
					System.out.println(inputPrice.getText());
					System.out.println(inputCourse.getText());
					ps.setDouble(1,Double.parseDouble(inputPrice.getText()));
					ps.setString(2,inputCourse.getText());
					int i = ps.executeUpdate();
					System.out.println(i);
				} catch (SQLException e) {
					e.printStackTrace();
				}				
				
				if(!stage.isShowing()) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		
		});
		
		
		
		stage.setTitle("Admin");
		stage.setScene(sc);
		stage.show();
	}	
	
	
	
	@Override
	public void start(Stage arg0) throws Exception {
		
	}

}
