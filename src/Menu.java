import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {
	String Rname;
	int x;
	int y;
	double distance;
	int indexloc;
	Stage newStage = new Stage();
	GridPane menugrid = new GridPane();
	Text opentime = new Text();
	Text closedate = new Text();
   
    boolean mark=false;
    boolean clicked =false;
	VBox v= new VBox();
	
	public void start(String a) {
		BorderPane menuborder = new BorderPane();
		Rname = a;
		Text top = new Text(Rname);
		top.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		HBox h = new HBox();
		h.setPadding(new Insets(10,10,10,10));
		h.getChildren().add(top);
    		    
		menuborder.setTop(h);
		h.setStyle("-fx-background-color: #2eb8ea;");
		menuborder.setCenter(menugrid);

		menuborder.setRight(v);
		v.setPadding(new Insets(10,10,10,10));
		v.setSpacing(10);
		Text open = new Text("Open Time");
		open.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		v.getChildren().add(open);
		opentime.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		v.getChildren().add(opentime);
		Text close = new Text("Close on");
		close.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		v.getChildren().add(close);
		closedate.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		v.getChildren().add(closedate);
		
		Scene newscene = new Scene(menuborder,830,600);
	    menugrid.setHgap(30);
	    menugrid.setVgap(20);
	    menugrid.setPadding(new Insets(5, 5, 5, 5));    
	    
	    Text product1 = new Text("Product");
	    Text product2 = new Text("Product");
	    Text price1 = new Text("Price($)");
	    Text price2 = new Text("Price($)");
	    product1.setFont(Font.font("Arial", FontWeight.BOLD, 17));
	    product2.setFont(Font.font("Arial", FontWeight.BOLD, 17));
	    price1.setFont(Font.font("Arial", FontWeight.BOLD, 17));
	    price2.setFont(Font.font("Arial", FontWeight.BOLD, 17));
	    menugrid.add(product1, 0, 0);
	    menugrid.add(product2, 2, 0);
	    menugrid.add(price1, 1, 0);
	    menugrid.add(price2, 3, 0);
	    Separator line1 =new Separator(); 
	    Separator line2 =new Separator();
	    Separator line3 =new Separator();
	    Separator line4 =new Separator();
	    menugrid.add(line1, 0, 1);
	    menugrid.add(line2, 1, 1);
	    menugrid.add(line3, 2, 1);
	    menugrid.add(line4, 3, 1);
	    menugrid.setPadding(new Insets(30,0,0,10));
		newStage.setTitle(Rname);
		newStage.setScene(newscene);
		newStage.show();
	
	}

	public void start(Stage primaryStage) throws Exception {
			int a=5;
	}
	
}
