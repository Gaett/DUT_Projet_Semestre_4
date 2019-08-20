package DwarvesDefenders.model;

import java.util.Observable;

import DwarvesDefenders.application.MenuController;
import javafx.scene.image.Image;

public class Dwarf extends Entity {
	private int health;
	private int energy;
	private Weapon weapon = Weapon.Durandal;
	private double speed = 0.5;
	public int wallet = 250;
	public int life = 10;
	public int score = 0;
	public boolean attacking = false;
	public boolean lifeChanged = false;
	
	public Dwarf(int h, int e, double hg1, double hg2, double bd1, double bd2,HitManager hm)
	{
		super(hg1,hg2,bd1,bd2,hm);
		super.setImage(new Image("DwarvesDefenders/img/dwarf/Dwarf_Front_Idle_01.png",50,50,true,false));
		this.health = h;
		this.energy = e;
	}

	public int getHealth() 
	{
		return health;
	}

	public void setHealth(int health) 
	{
		this.health = health;
	}

	public int getEnergy() 
	{
		return energy;
	}

	public void setEnergy(int energy) 
	{
		this.energy = energy;
	}
	
	public void attack()
	{
		this.setChanged();
		this.notifyObservers(ObserverType.HitManager);
	}
	
	public Weapon getWeapon()
	{
		return this.weapon;
	}
	
	public void setWeapon(Weapon w)
	{
		this.weapon = w;
	}
	
	public void walkImageCycle(int cycle_number)
	{
		String path = "DwarvesDefenders/img/dwarf/Dwarf_";
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
		super.setImage(new Image(path,45,45,true,false));
	}
	
	public void attackImageCycle(int cycle_number)
	{
		String path = "DwarvesDefenders/img/dwarf/Dwarf_";
		switch(facing) 
		{
		case Front:
			path+= "Front_Attack_0"+cycle_number+"";
			break;
		case Back:
			path+= "Back_Attack_0"+cycle_number+"";
			break;
		case Left:
			path+= "Left_Attack_0"+cycle_number+"";
			break;
		case Right:
			path+= "Right_Attack_0"+cycle_number+"";
			break;
		}
		path+=".png";
		super.setImage(new Image(path,45,45,true,false));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Message)
		{
			switch((Message)arg)
			{
				//Default est la valeur MovementUP
				default:
					attacking = false;
					this.incrementPosition(0,-3);
					this.facing = Orientation.Back;
					break;
				case MovementDOWN:
					attacking = false;
					this.incrementPosition(0,3);
					this.facing = Orientation.Front;
					break;
				case MovementLEFT:
					attacking = false;
					this.incrementPosition(-3,0);
					this.facing = Orientation.Left;
					break;
				case MovementRIGHT:
					attacking = false;
					this.incrementPosition(3,0);
					this.facing = Orientation.Right;
					break;
				case Attack:
					this.attack();
					attacking = true;
					break;
			}
		}
		else if(arg instanceof Integer)
		{
			//10 valeur fixe des monstres
			wallet+= 10;
			score++;
		}
		else if(arg == ObserverType.monsterArrivedManager)
		{
			life--;
			lifeChanged = true;
			if(life <= 0)
			{
				MenuController.active = false;
			}
		}
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
							
							if(!attacking)
							{
								walkImageCycle(comptWalk);
							}
							else
							{
								int comptAttack = 1;
								while(comptAttack < 3)
								{
									try {
										Thread.sleep(50);
									} catch (InterruptedException e) {
									}
									finally
									{
										attackImageCycle(comptAttack);
									}
									comptAttack++;
								}
								attacking = false;
							}
						}
					}
					
				}
			});
			t.start();
		}
	}
}
