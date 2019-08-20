package DwarvesDefenders.application;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import DwarvesDefenders.model.GameCore;
import DwarvesDefenders.model.Monster;
import DwarvesDefenders.model.Tower;
import DwarvesDefenders.model.Tower.TowerType;
import Tools.DevelopperTool;

public class MenuController implements DevelopperTool{
	public static boolean started = false;
	public static boolean launched = false;
	public static boolean active;
	public int selectedTowerAction = 0;
	private Thread render;
	private Thread keyboardRefresher;
	public static boolean gameEnded = false;
	public static boolean activateOnce = false;
	public static int scoreGame = 0;
	
	@FXML
	public Canvas Game;
	@FXML
	public Canvas Map;
	@FXML
	public Canvas Towers;
	@FXML
	public Canvas LifeBar;
	@FXML
	public Label lives;
	@FXML
	public Label remainingMonsters;
	@FXML
	public Label nextWaves;
	@FXML
	public Label gold;
	@FXML
	public Label score;
	@FXML
	public Label startLabel;
	@FXML
	public MenuButton towersButtons;
	
	public Image lifeBar;
	public GameCore gc;
	
	public static void reset()
	{
		started = false;
		launched = false;
		active = true;
		gameEnded = false;
		activateOnce = false;
		scoreGame = 0;
	}
	
	@Override
	public Stage createWindow(String filename, int width, int height)
	{
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
	public void onButtonLight(Event e)
	{
		if(this.gc.player.wallet < TowerType.LightTower1.price)
		{
			Alert erreur = new Alert(Alert.AlertType.ERROR, "Not enough gold");
			erreur.setResizable(true);
	        erreur.showAndWait();
		}
		else
		{
			gc.mm.selectType(1);
			selectedTowerAction = 1;
		}
	}
	@FXML
	public void onButtonHeavy(Event e)
	{
		if(this.gc.player.wallet < TowerType.HeavyTower1.price)
		{
			Alert erreur = new Alert(Alert.AlertType.ERROR, "Not enough gold");
			erreur.setResizable(true);
	        erreur.showAndWait();
		}
		else
		{
			gc.mm.selectType(2);
			selectedTowerAction = 2;
		}
	}
	@FXML
	public void onButtonMagic(Event e)
	{
		if(this.gc.player.wallet < TowerType.MagicTower1.price)
		{
			Alert erreur = new Alert(Alert.AlertType.ERROR, "Not enough gold");
			erreur.setResizable(true);
	        erreur.showAndWait();
		}
		else
		{
			gc.mm.selectType(3);
			selectedTowerAction = 3;
		}
	}
	@FXML
	public void onButtonBoost(Event e)
	{
		selectedTowerAction = 4;
	}
	
	@FXML
	public void onMouseClicked(MouseEvent e)
	{
		switch(selectedTowerAction)
		{
		case 1:
			this.gc.player.wallet -= TowerType.LightTower1.price;
			break;
		case 2:
			this.gc.player.wallet -= TowerType.HeavyTower1.price;
			break;
		case 3:
			this.gc.player.wallet -= TowerType.MagicTower1.price;
			break;
		case 4:
			int x = (int)e.getX()/GameCore.tileSize;
			int y = (int)e.getY()/GameCore.tileSize;
			if(this.gc.mm.map.getCase(x,y) instanceof Tower)
			{
				Tower t = (Tower)this.gc.mm.map.getCase(x,y);
				if(this.gc.player.wallet < t.nextTT.price)
				{
					Alert erreur = new Alert(Alert.AlertType.ERROR, "Not enough gold");
					erreur.setResizable(true);
			        erreur.showAndWait();
				}
				else
				{
					gc.mm.selectType(4);
					this.gc.player.wallet -= t.nextTT.price;
				}
			}
			break;
		}
		selectedTowerAction = 0;
		gc.mm.onMouseEvent(e);
	}
	
	@FXML
	public void onLoad()
	{
		active = true;
		startLabel.setText("PRESS P TO START AND ESC FOR OPTIONS" 
						+ '\n' +'\n' + "KEYS : Z,Q,S,D" 
						+ '\n' + '\n' + "ATTACK : SPACEBAR" 
						+ '\n' + '\n' +"PLACE YOUR TOWERS WITH YOUR MOUSE");
		started = !started;
		lifeBar = new Image("DwarvesDefenders/img/ui/UI_PV10.png",640,45,false,false);
		gc = new GameCore();
		gc.mm.setObserver(new Observer() {
			@Override
			public void update(Observable arg0, Object arg1) {
				Towers.getGraphicsContext2D().clearRect(0, 0, Towers.getWidth(), Towers.getHeight());
				for(int i = 0; i < gc.mm.map.rowN;i++)
				{
					for(int j = 0; j < gc.mm.map.colN;j++)
					{
						if(gc.mm.map.getCase(i, j) instanceof Tower)
						{
							Towers.getGraphicsContext2D().drawImage(gc.mm.map.getCase(i, j).getImage(), i*GameCore.tileSize, j*GameCore.tileSize-GameCore.tileSize);
						}
					}
				}
			}
		});
		render = new Thread(new Runnable()
		{
			@Override
			public void run() {
				renderLoop();
			}
		});
		render.start();
		keyboardRefresher = new Thread(new Runnable() 
		{
			@Override
			public void run() {
				keyboard_Refreshing_loop();
			}
		});
		keyboardRefresher.start();
		this.gc.player.animationChoice();
	}
	
	@FXML
	public void onPressed(KeyEvent e)
	{
		if(e.getCode() == KeyCode.ENTER)
		{
			gc.mm.selectType(0);
			selectedTowerAction = 0;
		}
		if(e.getCode() == KeyCode.P && !launched)
		{
			startLabel.setVisible(false);
			launched = true;
			gc.start();
		}
		if(e.getCode() == KeyCode.ESCAPE)
		{
			onOption(e);
		}
		else
		{
			gc.kbc.onPress(e);
		}
	}
	
	@FXML
	public void onRelease(KeyEvent e)
	{
		gc.kbc.onRelease(e);
	}
	
	public void close()
	{
		this.gc.stopSpawn();
		active = false;
		render.interrupt();
		Main.gameStage.close();
	}
	
	@FXML
	public void onOption(Event e)
	{
		createWindow("DwarvesDefendersOption.fxml", 557, 251).showAndWait();
	}
	
	@FXML
	public void onGameFinished()
	{
		createWindow("DwarvesDefendersScoreScreen.fxml", 600, 400).showAndWait();
	}
	
	public void renderLoop()
	{
		while(active)
		{
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				//nothing
			}
			finally
			{
				scoreGame = this.gc.player.score;
				
				if(gc.player.life == 0 && !activateOnce)
				{
					gameEnded = true;
					activateOnce = true;
					Platform.runLater(() -> {
						onGameFinished();
					});
				}
				Game.getGraphicsContext2D().clearRect(0, 0, Game.getWidth(), Game.getHeight());
				
				for(Monster m : gc.ms.monsters)
				{
					if(m.alive && !m.arrived && m.started)
					{
						Game.getGraphicsContext2D().drawImage(m.getImage(), m.getPosition().getPoint(0).getPoint()[0], m.getPosition().getPoint(0).getPoint()[1]);
					}
				}

				Game.getGraphicsContext2D().drawImage(gc.player.getImage(), gc.player.getPosition().getPoint(0).getPoint()[0], gc.player.getPosition().getPoint(0).getPoint()[1]);
				
				Platform.runLater(() -> {
					this.lives.setText("");
					this.remainingMonsters.setText("Remaining monster : " +this.gc.ms.getRemainingMonster());
					this.nextWaves.setText("Next wave : " +this.gc.monsterNumber);
					this.gold.setText("" + this.gc.player.wallet);
					this.score.setText("Score : " + this.gc.player.score);
				});
				
				if(this.gc.player.lifeChanged)
				{
					lifeBar = new Image("DwarvesDefenders/img/ui/UI_PV"+ this.gc.player.life + ".png",640,45,false,false);
					this.gc.player.lifeChanged = false;
				}
				
				Game.getGraphicsContext2D().drawImage(lifeBar, 320, 0);
			}
		}	
	}
	
	public void keyboard_Refreshing_loop()
	{
		while(active)
		{
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				//nothing
			}
			finally
			{
				gc.kbc.keyBoardActivity();
			}
		}
	}
}
