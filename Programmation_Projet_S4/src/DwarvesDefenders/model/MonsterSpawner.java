package DwarvesDefenders.model;

import java.util.ArrayList;
import java.util.Observable;

import DwarvesDefenders.application.MenuController;

public class MonsterSpawner extends Observable {
	public int spawnNumber;
	public int spawnX;
	public int spawnY;
	/* not used */
	public long minute = 5000;
	public HitManager hm;
	public ArrayList<Monster> monsters;
	
	public MonsterSpawner(int monsterNumber, int startX, int startY, HitManager hm)
	{
		this.hm = hm;
		this.spawnNumber = monsterNumber;
		this.spawnX = startX;
		this.spawnY = startY;
		this.monsters = new ArrayList<Monster>();
	}
	
	public void initializeSpawner()
	{
		for(int i = 0; i < this.spawnNumber;i++)
		{
			monsters.add(new Monster(100, 0, this.spawnX, this.spawnY, 0, 0,this.hm));
		}
	}
	
	public int getRemainingMonster()
	{
		int res = 0;
		for(Monster m : monsters)
		{
			if(m.alive)
			{
				res++;
			}
		}
		return res;
	}
	
	public void startSpawner()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run() {
				for(Monster m : monsters)
				{
					if(!MenuController.active)
					{
						try {
							this.finalize();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					finally
					{
						m.goThrough();
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
}
