package DwarvesDefenders.model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import DwarvesDefenders.application.MenuController;
import javafx.scene.image.Image;

public class Tower extends Case implements Observer{
	
	public enum TowerType
	{
		LightTower1(5.0,500,100,"DwarvesDefenders/img/Archer_T1.png",50), HeavyTower1(25.0,1250,150,"DwarvesDefenders/img/Heavy_T1.png",80), MagicTower1(30,3500,175,"DwarvesDefenders/img/Magic_T1.png",100),
		LightTower2(15.0,450,150,"DwarvesDefenders/img/Archer_T2.png",150), HeavyTower2(40.0,1100,175,"DwarvesDefenders/img/Heavy_T2.png",200), MagicTower2(50,3000,200,"DwarvesDefenders/img/Magic_T2.png",250),
		LightTower3(30.0,400,200,"DwarvesDefenders/img/Archer_T3.png",250), HeavyTower3(65.0,1000,200,"DwarvesDefenders/img/Heavy_T3.png",350), MagicTower3(100,1800,225,"DwarvesDefenders/img/Magic_T3.png",400);
		public final String img;
		public final double damage;
		public final long attackSpeed;
		public final double range;
		public final int price;

		
		private TowerType(double dam, long attack, double range,String img, int prix)
		{
			this.img = img;
			this.damage = dam;
			this.attackSpeed = attack;
			this.range = range;
			this.price = prix;
		}
	}
	
	private static int towerId;
	public TowerType tt;
	public TowerType nextTT;
	HashMap<Integer,Monster> targets = new HashMap<Integer,Monster>();
	private Monster target;
	public int id;
	public int price;

	public Tower(TowerType t,HitManager hm) {
		super(false);
		this.addObserver(hm);
		towerId++;
		this.id = towerId;
		this.tt = t;
		switch(tt)
		{
		case LightTower1:
			this.nextTT = TowerType.LightTower2;
			break;
		case HeavyTower1:
			this.nextTT = TowerType.HeavyTower2;
			break;
		case MagicTower1:
			this.nextTT = TowerType.MagicTower2;
			break;
		}
		this.price = tt.price;
		super.setImage(generateTowerImage(tt.img));
		setChanged();
		notifyObservers(ObserverType.towerPlace);
	}
	
	public TowerType getTowerType()
	{
		return this.tt;
	}
	
	public Image generateTowerImage(String str)
	{
		return new Image(str,32,64,false,false);
	}
	
	public void upgrade()
	{
		switch(this.tt)
		{
		case LightTower1:
			this.tt = TowerType.LightTower2;
			this.nextTT = TowerType.LightTower3;
			super.setImage(generateTowerImage(tt.img));
			break;
		case LightTower2:
			this.tt = TowerType.LightTower3;
			super.setImage(generateTowerImage(tt.img));
			break;
		case HeavyTower1:
			this.tt = TowerType.HeavyTower2;
			this.nextTT = TowerType.HeavyTower3;
			super.setImage(generateTowerImage(tt.img));
			break;
		case HeavyTower2:
			this.tt = TowerType.HeavyTower3;
			super.setImage(generateTowerImage(tt.img));
			break;
		case MagicTower1:
			this.tt = TowerType.MagicTower2;
			this.nextTT = TowerType.MagicTower3;
			super.setImage(generateTowerImage(tt.img));
			break;
		case MagicTower2:
			this.tt = TowerType.MagicTower3;
			super.setImage(generateTowerImage(tt.img));
			break;
			
		}
		setChanged();
		notifyObservers(ObserverType.towerChange);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Monster)
		{
			Monster m = (Monster)arg1;
			this.targets.put(m.id,m);
		}
	}
	
	public void calculateTarget()
	{
		try
		{
			targets.forEach((key, value) -> {
				this.target = value;
			});
			
			targets.forEach((key, value) -> {
				Point p = new Point(this.getPositionCase()[0],this.getPositionCase()[1]);
				if(Position.distanceBetweenPoints(this.target.getCenter(), p) > Position.distanceBetweenPoints(value.getCenter(), p) && value.alive)
				{
					this.target = value;
				}
			});
		} catch(Exception e)
		{
			//rien
		}
		
	}
	
	public void activate()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run() {
				while(MenuController.active)
				{
					try {
						Thread.sleep(tt.attackSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					finally
					{
						Tower.this.calculateTarget();
						if(Tower.this.target != null )
						{
							Tower.this.target.receiveDamage(Tower.this.tt.damage);
							Tower.this.targets.remove(Tower.this.target.id);
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
				
			}
		});
		t.start();
	}
}
