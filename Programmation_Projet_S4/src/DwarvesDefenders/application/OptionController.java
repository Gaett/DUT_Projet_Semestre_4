package DwarvesDefenders.application;

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

public class OptionController implements DevelopperTool{
	private boolean activateOnce = false;
	
	@Override
	public Stage createWindow(String filename, int width, int height) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
	    Parent parent;
	    Stage stage = new Stage();
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent, width, height);
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
	public void quit(Event e)
	{
		Node  source = (Node)  e.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
	@FXML
	public void quitApp(Event e)
	{
		if(Main.mc != null)
		{
			Main.mc.close();
		}
		if(MenuController.started)
		{
			MenuController.reset();
			createWindow("DwarvesDefendersMenu.fxml", 600, 400).show();
		}
		Node  source = (Node)  e.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}

	
}
