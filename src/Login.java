import java.util.Vector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application{
	Stage stage = new Stage();
	Admin am = new Admin();

	public void start(int n,Vector b)  {
		GridPane grid = new GridPane(); 
		grid.setAlignment(Pos.CENTER);
		Scene sc = new Scene(grid,280,220);
		Text adminName = new Text("AdminName ");
		Text adminPassWord = new Text("PassWord ");
		TextField inputName = new TextField();
		PasswordField inputPassWord = new PasswordField();
		grid.setPadding(new Insets(10,10,30,10));
		grid.add(adminName, 0, 15);
		grid.add(inputName, 5, 15);
		grid.add(adminPassWord, 0, 20);
		grid.add(inputPassWord, 5, 20);
		Button click = new Button("Submit");
		grid.add(click, 5, 30);
		click.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(inputName.getText().equals("admin")&&inputPassWord.getText().equals("admin")) {
					stage.close();
					int k= 6;
					am.start(k,b);
				}
				
			}
		
		});
		

		stage.setTitle("Login");
		stage.setScene(sc);
		stage.show();
		
	}

	public void start(Stage b) throws Exception {

		stage.show();
		
	}
}
