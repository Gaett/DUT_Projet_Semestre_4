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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DwarvesDefendersScoreScreenController implements DevelopperTool{
	@FXML
	public Label scoreLab;
	
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
	public void quitApp(Event e)
	{
		Main.mc.close();
		Node  source = (Node)  e.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
	@FXML
	public void showScore()
	{
		String text = "Score ";
		text+= MenuController.scoreGame + "";
		scoreLab.setText(text);
	}
	
	@FXML
	public void mainMenu(Event e)
	{
		Main.mc.close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DwarvesDefendersMenu.fxml"));
        Parent parent;
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent, 600, 400);
			scene.setCursor(new ImageCursor(new Image("DwarvesDefenders/img/Cursor.png")));
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Node  source = (Node)  e.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
}
