package DwarvesDefenders.model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.input.KeyCode;

public class HitManager extends Observable implements Observer {
	HashMap<Integer,Monster> monsterList= new HashMap<Integer,Monster>();
	HashMap<Integer,Tower> towerList= new HashMap<Integer,Tower>();
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg == ObserverType.HitManager)
		{
			Dwarf d = (Dwarf)o;
			monsterList.forEach((key, value) -> {
				if(d.getWeapon().getRange() >= Position.distanceBetweenPoints(value.getCenter(), d.getCenter()) && value.alive)
				{
					value.receiveDamage(d.getWeapon().getDamage());
				}
			});
		}
		else if(arg == ObserverType.towerPlace)
		{
			Tower t = (Tower)o;
			towerList.put(t.id,t);
			t.activate();
			this.addObserver(t);
		}
		else if(arg == ObserverType.towerChange)
		{
			Tower t = (Tower)o;
			towerList.put(t.id,t);
		}
		else if(arg == ObserverType.MonsterDeath)
		{
			Monster m = (Monster)o;
			setChanged();
			notifyObservers(m.value);
		}
		else if(arg == ObserverType.monsterArrivedMonster)
		{
			setChanged();
			notifyObservers(ObserverType.monsterArrivedManager);
		}
		else if(arg == ObserverType.MovementManagerMonster)
		{
			Monster m = (Monster)o;
			monsterList.put(m.id, m);
			
			towerList.forEach((key, value) -> {
				if(m.alive && !m.invincible)
				{
					Point p = new Point(value.getPositionCase()[0],value.getPositionCase()[1]);
					if(value.tt.range >= Position.distanceBetweenPoints(m.getCenter(), p) )
					{
						setChanged();
						notifyObservers(m);
					}
					
				}
			});
			
		}
		
	}

}
