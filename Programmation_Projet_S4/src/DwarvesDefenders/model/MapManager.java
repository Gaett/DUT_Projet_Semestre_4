package DwarvesDefenders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import DwarvesDefenders.model.Tower.TowerType;

public class MapManager extends Observable{
	public Carte map;
	public Carte basic;
	//selectedType prendra des valeurs permettant de définir un type d'action,0 rien, 1 pose d'une tourelle légere, 2 pose d'une tourelle lourde, 3 pose d'une tourelle magique, 4 amélioration de la tourelle pointée.
	public int selectedType;
	public int currentX,currentY;
	public HitManager hm;
	public static Case[] theWay;
	public Case entry;
	public Case exit;
	
	public MapManager(HitManager hm)
	{
		this.hm = hm;
		this.basic = new Carte(40,22);
		this.map = new Carte(40,22);
		map.setCase(new Entry(), 0, 5);
		
		map.setCase(new Road(0), 1, 5);
		map.setCase(new Road(1), 2, 5);
		map.setCase(new Road(2), 3, 5);
		map.setCase(new Road(3), 4, 5);
		map.setCase(new Road(4), 5, 5);
		map.setCase(new Road(5), 6, 5);
		map.setCase(new Road(6), 6, 6);
		map.setCase(new Road(7), 6, 7);
		map.setCase(new Road(8), 6, 8);
		map.setCase(new Road(9), 6, 9);
		map.setCase(new Road(10), 6, 10);
		map.setCase(new Road(11), 7, 10);
		map.setCase(new Road(12), 7, 9);
		map.setCase(new Road(13), 7, 8);
		map.setCase(new Road(14), 7, 7);
		map.setCase(new Road(15), 7, 6);
		map.setCase(new Road(16), 8, 6);
		map.setCase(new Road(17), 9, 6);
		map.setCase(new Road(18), 10, 6);
		map.setCase(new Road(19), 11, 6);
		map.setCase(new Road(20), 11, 7);
		map.setCase(new Road(21), 11, 8);
		map.setCase(new Road(22), 11, 9);
		map.setCase(new Road(23), 11, 10);
		map.setCase(new Road(24), 11, 11);
		map.setCase(new Road(25), 11, 12);
		map.setCase(new Road(26), 11, 13);
		map.setCase(new Road(27), 12, 13);
		map.setCase(new Road(28), 13, 13);
		map.setCase(new Road(29), 14, 13);
		map.setCase(new Road(30), 15, 13);
		map.setCase(new Road(31), 15, 14);
		map.setCase(new Road(32), 15, 15);
		map.setCase(new Road(33), 14, 15);
		map.setCase(new Road(34), 13, 15);
		map.setCase(new Road(35), 12, 15);
		
		map.setCase(new Road(36), 12, 16);
		map.setCase(new Road(37), 12, 17);
		map.setCase(new Road(38), 12, 18);
		
		map.setCase(new Road(39), 13, 18);
		map.setCase(new Road(40), 14, 18);
		map.setCase(new Road(41), 15, 18);
		map.setCase(new Road(42), 16, 18);
		map.setCase(new Road(43), 17, 18);
		map.setCase(new Road(44), 18, 18);
		map.setCase(new Road(45), 19, 18);
		map.setCase(new Road(46), 20, 18);
		
		map.setCase(new Road(47), 20, 17);
		map.setCase(new Road(48), 20, 16);
		map.setCase(new Road(49), 20, 15);
		map.setCase(new Road(50), 20, 14);
		map.setCase(new Road(51), 20, 13);
		map.setCase(new Road(52), 20, 12);
		map.setCase(new Road(53), 20, 11);
		map.setCase(new Road(54), 20, 10);

		map.setCase(new Road(55), 21, 10);
		map.setCase(new Road(56), 22, 10);
		map.setCase(new Road(57), 23, 10);
		map.setCase(new Road(58), 24, 10);
		map.setCase(new Road(59), 25, 10);
		
		map.setCase(new Road(60), 25, 11);
		map.setCase(new Road(61), 25, 12);
		map.setCase(new Road(62), 25, 13);
		
		map.setCase(new Road(63), 26, 13);
		map.setCase(new Road(64), 27, 13);
		map.setCase(new Road(65), 28, 13);
		map.setCase(new Road(66), 29, 13);
		map.setCase(new Road(67), 30, 13);
		
		map.setCase(new Road(68), 30, 12);
		map.setCase(new Road(69), 30, 11);
		map.setCase(new Road(70), 30, 10);
		map.setCase(new Road(71), 30, 9);
		map.setCase(new Road(72), 30, 8);
		map.setCase(new Road(73), 30, 7);
		map.setCase(new Road(74), 30, 6);
		
		map.setCase(new Road(83), 38, 5);
		map.setCase(new Road(82), 37, 5);
		map.setCase(new Road(81), 36, 5);
		map.setCase(new Road(80), 35, 5);
		map.setCase(new Road(79), 34, 5);
		map.setCase(new Road(78), 33, 5);
		map.setCase(new Road(77), 32, 5);
		map.setCase(new Road(76), 31, 5);
		map.setCase(new Road(75), 30, 5);
		
		map.setCase(new Exit(), 39, 5);
		Map<Integer,Case> toSort = new HashMap<Integer,Case>();
		
		int roads = 0;
		for(int i = 0; i < map.rowN;i++)
		{
			for(int j = 0; j < map.colN;j++)
			{
				if(map.getCase(i, j) instanceof Exit)
				{
					this.exit = map.getCase(i, j);
				}
				else if(map.getCase(i, j) instanceof Entry)
				{
					this.entry = map.getCase(i, j);
				}
				else if(map.getCase(i, j) instanceof Road)
				{
					roads++;
					Road r = (Road)map.getCase(i, j);
					toSort.put(r.number, r);
				}
			}
		}
		theWay = new Case[roads];

		for(int i = 0; i < roads; i++)
		{
			theWay[i] = toSort.get(i);
		}
		
	}
	
	public void selectType(int i)
	{
		this.selectedType = i;
	}
	
	public void setObserver(Observer o)
	{
		if(this.countObservers() == 0)
		{
			this.addObserver(o);
			setChanged();
			notifyObservers();
		}
	}
	
	public void onMouseEvent(MouseEvent e)
	{
		currentX = (int)(e.getX()/GameCore.tileSize);
		currentY = (int)(e.getY()/GameCore.tileSize);
		if(this.selectedType == 0)
		{
			//rien
		}
		else if((map.getCase(currentX, currentY) instanceof Tower) && (this.selectedType !=4) ||(map.getCase(currentX, currentY) instanceof Road))
		{
			Alert erreur = new Alert(Alert.AlertType.ERROR, "You can't place a tower here !");
			erreur.setResizable(true);
	        erreur.showAndWait();
		}
		else
		{
			switch(this.selectedType)
			{
				case 0:
					break;
				case 1:
					map.setCase(new Tower(TowerType.LightTower1,this.hm), currentX, currentY);
					this.selectedType = 0;
					setChanged();
					notifyObservers();
					break;
				case 2:
					map.setCase(new Tower(TowerType.HeavyTower1,this.hm), currentX, currentY);
					this.selectedType = 0;
					setChanged();
					notifyObservers();
					break;
				case 3:
					map.setCase(new Tower(TowerType.MagicTower1,this.hm), currentX, currentY);
					this.selectedType = 0;
					setChanged();
					notifyObservers();
					break;
				case 4:
					if(map.getCase(currentX, currentY) instanceof Tower)
					{
						Tower t = (Tower)map.getCase(currentX, currentY);
						t.upgrade();
						setChanged();
						notifyObservers();
					}
					this.selectedType = 0;
					break;
			}
		}
		
	}
	
	public Case getEntry()
	{
		return this.entry;
	}
	
	public Case getExit()
	{
		return this.exit;
	}
}
