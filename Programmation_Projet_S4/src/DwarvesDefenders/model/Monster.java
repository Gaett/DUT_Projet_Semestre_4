package DwarvesDefenders.model;

import DwarvesDefenders.application.MenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Monster extends Entity{
	public static int monsterId = 0;
	
	private double health;
	public int id;
	public boolean alive = true;
	public Road[] theWay;
	public long speed = 13;
	public int value = 10;
	public boolean invincible = true;
	public boolean started = false;
	public boolean arrived = false;
	public boolean damageDealt = false;
	
	public Monster(HitManager hm)
	{
		super(hm);
		monsterId++;
		this.id = monsterId;
		super.setImage(new Image("DwarvesDefenders/img/goblin/Goblin_Front_Walk_01.png",32,32,true,false));
		this.health = 0;
		setChanged();
		notifyObservers(ObserverType.MovementManagerMonster);
	}
	
	public Monster(int h, int e,HitManager hm)
	{
		super(hm);
		monsterId++;
		this.id = monsterId;
		super.setImage(new Image("DwarvesDefenders/img/goblin/Goblin_Front_Walk_01.png",32,32,true,false));
		this.health = h;
		setChanged();
		notifyObservers(ObserverType.MovementManagerMonster);
	}
	
	public Monster(int h, int e, double hg1, double hg2, double bd1, double bd2,HitManager hm)
	{
		super(hg1,hg2,bd1,bd2,hm);
		monsterId++;
		this.id = monsterId;
		super.setImage(new Image("DwarvesDefenders/img/goblin/Goblin_Front_Walk_01.png",32,32,true,false));
		this.health = h;
		setChanged();
		notifyObservers(ObserverType.MovementManagerMonster);
	}
	
	public void vulnerable()
	{
		this.invincible = false;
	}
	
	public void receiveDamage(double dam)
	{
		if(!this.invincible && alive)
		{
			health -= dam;
			if(health <= 0)
			{
				health = 0;
				alive = false;
				setChanged();
				notifyObservers(ObserverType.MonsterDeath);
			}
		}
	}
	
	@Override
	public void incrementPosition(double x, double y)
	{
		this.position.incrementPosition(x, y);
		setChanged();
		notifyObservers(ObserverType.MovementManagerMonster);
	}
	
	public boolean incrementPositionVers(int x, int y)
	{
		
		if(x < this.getPosition().getPoints()[0].getPoint()[0])
		{
			if(y < this.getPosition().getPoints()[0].getPoint()[1])
			{
				incrementPosition(-1,-1);
			}
			else if(y > this.getPosition().getPoints()[0].getPoint()[1])
			{
				incrementPosition(-1,1);
			}
			else
			{
				incrementPosition(-1,0);
			}
		}
		else if(x > this.getPosition().getPoints()[0].getPoint()[0])
		{
			if(y < this.getPosition().getPoints()[0].getPoint()[1])
			{
				incrementPosition(1,-1);
			}
			else if(y > this.getPosition().getPoints()[0].getPoint()[1])
			{
				incrementPosition(1,1);
			}
			else
			{
				incrementPosition(1,0);
			}
		}
		else
		{
			if(y < this.getPosition().getPoints()[0].getPoint()[1])
			{
				incrementPosition(0,-1);
			}
			else if(y > this.getPosition().getPoints()[0].getPoint()[1])
			{
				incrementPosition(0,1);
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	public void goCase(Case c)
	{
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!((c.getPositionCase()[0] == Monster.this.getPosition().getPoints()[0].getPoint()[0]) && ( c.getPositionCase()[1] == Monster.this.getPosition().getPoints()[0].getPoint()[1])) && alive && MenuController.active)
				{
					try {
						Thread.sleep(Monster.this.speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					finally
					{
						Monster.this.incrementPositionVers(c.getPositionCase()[0],c.getPositionCase()[1]);
					}
					if(!MenuController.active)
					{
						try {
							this.finalize();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if(!MenuController.active)
				{
					try {
						this.finalize();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
	
	public void goThrough()
	{
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				if(!started)
				{
					started = true;
				}
				for(Case c : MapManager.theWay)
				{
					if(MenuController.active)
					{
						try {
							Thread.sleep(750);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						finally
						{
							Monster.this.vulnerable();
							goCase(c);
						}
					}
				}
				if(Monster.this.alive && !damageDealt && !arrived)
				{
					damageDealt = true;
					Monster.this.arrived = true;
					setChanged();
					notifyObservers(ObserverType.monsterArrivedMonster);
					Monster.this.invincible = true;
					alive = false;
				}
				if(MenuController.active)
				{
					try {
						this.finalize();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
	
	public void walkImageCycle(int cycle_number)
	{
		String path = "DwarvesDefenders/img/goblin/Goblin_";
		switch(facing) 
		{
		case Front:
			path+= "Front_Walk_0"+cycle_number+"";
			break;
		case Back:
			path+= "Back_Walk_0"+cycle_number+"";
			break;
		case Left:
			path+= "Left_Walk_0"+cycle_number+"";
			break;
		case Right:
			path+= "Right_Walk_0"+cycle_number+"";
			break;
		}
		path+=".png";
		super.setImage(new Image(path,32,32,true,false));
	}
	
	public void animationChoice()
	{
		if(!animationLaunched)
		{
			animationLaunched = true;
			Thread t = new Thread(new Runnable() 
			{
				@Override
				public void run() {
					int comptWalk = 1;
					while(MenuController.active)
					{
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							//nothing
						}
						finally
						{
							comptWalk++;
							if(comptWalk > 2)
							{
								comptWalk = 1;
							}
							walkImageCycle(comptWalk);
						}
					}
					
				}
			});
			t.start();
		}
	}
}
