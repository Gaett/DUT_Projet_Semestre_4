package MainMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root1 = (AnchorPane)FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene scene1 = new Scene(root1,600,600);
			Font.loadFont(
				Main.class.getResource("8_bit_wonder.ttf").toExternalForm(),10
		    );	
			scene1.setCursor(new ImageCursor(new Image("DwarvesDefenders/img/Cursor.png")));
			primaryStage.setScene(scene1);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
