package MainMenu;

import java.io.IOException;

import Tools.DevelopperTool;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenuController implements DevelopperTool {
	@Override
	public Stage createWindow(String filename, int width, int height) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
	    Parent parent;
	    Stage stage = new Stage();
	    Scene scene;
		try {
			parent = fxmlLoader.load();
			if(width == 0 && height == 0)
			{
				scene = new Scene(parent);
			}
			else
			{
				scene = new Scene(parent, width, height);
			}
			scene.setCursor(new ImageCursor(new Image("DwarvesDefenders/img/Cursor.png")));
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.initStyle(StageStyle.UNDECORATED);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return stage;
	}
	
	@FXML
	public void onQuit(Event e)
	{
		Node  source = (Node)  e.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
	@FXML
	public void onDwarvesDefenders(Event e)
	{
		createWindow("../DwarvesDefenders/application/DwarvesDefendersMenu.fxml",600,400).show();
		Node  source = (Node)  e.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}

	
	@FXML
	public void info()
	{
		Stage stage = new Stage();
		stage = createWindow("interfaceInfo.fxml", 400, 300);
		stage.initStyle(StageStyle.DECORATED);
		stage.showAndWait();
	}
}
