package DwarvesDefenders.application;

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
	
	public static MenuController mc;
	public static Stage gameStage;
	public static boolean gameStarted = false;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root1 = (AnchorPane)FXMLLoader.load(getClass().getResource("DwarvesDefendersMenu.fxml"));
			Scene scene1 = new Scene(root1,600,400);
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
	
	@Override
	public void stop() 
	{
		MenuController.active = false;
	}
}


//public static MediaPlayer mp;
//public static Thread music;
//Code pour mettre de la musique
//		 Main.music = new Thread( new Runnable() {
//			@Override
//			public void run() {
//				//Musique libre de droit sur https://www.bensound.com/royalty-free-music
//				String[] musics = new String[10];
//				musics[0] = getClass().getResource("/application/img/audio/Crusade.mp3").toString();
//				Random r = new Random();
//				Media m = new Media(musics[r.nextInt(2)]);
//				Main.mp = new MediaPlayer(m);
//				Main.mp.play();
//			}
//		});
//		Main.music.start();