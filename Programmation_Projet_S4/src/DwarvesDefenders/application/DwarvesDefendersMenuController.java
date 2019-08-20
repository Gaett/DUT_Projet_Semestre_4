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

public class DwarvesDefendersMenuController implements DevelopperTool{
	
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
	public void onQuit(Event e)
	{
		createWindow("../../MainMenu/MainMenu.fxml", 600, 600).show();
		MenuController.active = false;
		Node  source = (Node)  e.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
	@FXML
	public void onOption(Event e)
	{
		createWindow("DwarvesDefendersOption.fxml", 557, 251).showAndWait();
	}
	
	@FXML
	public void onNewGame(Event e)
	{
		/* Puisque l'on utilise le controller de l'interface on choisit ici de ne pas utiliser la méthode createWindow */
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DwarvesDefenders.fxml"));
        Parent parent;
		try {
			parent = fxmlLoader.load();
			//1280 720
			Scene scene = new Scene(parent, 1280,704);
			scene.setCursor(new ImageCursor(new Image("DwarvesDefenders/img/Cursor.png")));
			Main.gameStage = new Stage();
			Main.gameStage.initModality(Modality.APPLICATION_MODAL);
			Main.gameStage.setScene(scene);
			Main.gameStage.setResizable(false);
			Main.gameStage.initStyle(StageStyle.UNDECORATED);
			Main.gameStage.show();
			Main.gameStarted = true;
	        Main.mc = fxmlLoader.<MenuController>getController();
	        MenuController.reset();
	        Main.mc.onLoad();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Node  source = (Node)  e.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
	

}
