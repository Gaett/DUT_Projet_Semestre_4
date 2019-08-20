package DwarvesDefenders.model;


import DwarvesDefenders.application.MenuController;

public class GameCore{
	//La tile size est prédéfinie en fonction de la taille d'une case de la carte
	public static int tileSize = 32;
	public Dwarf player;
	public KeyBoardControls kbc;
	public MapManager mm;
	public HitManager hm;
	public MonsterSpawner ms;
	public Thread clock;
	public boolean started = false;
	
	public int monsterNumber = 20;
	public int startX;
	public int startY;
	
	public GameCore()
	{
		this.hm = new HitManager();
		this.player = new Dwarf(100,100,5*GameCore.tileSize,5*GameCore.tileSize,0,0,this.hm);
		this.hm.addObserver(this.player);
		this.kbc = new KeyBoardControls(this.player);
		this.mm = new MapManager(this.hm);
		
		startX = this.mm.getEntry().getPositionCase()[0];
		startY = this.mm.getEntry().getPositionCase()[1];
		
		this.ms = new MonsterSpawner(monsterNumber, startX, startY,this.hm);
		this.ms.initializeSpawner();
		
	}
	
	public void start()
	{
		this.ms.startSpawner();
		this.started = true;
		clock = new Thread(new Runnable()
		{
			@Override
			public void run() {
				while(MenuController.active && !MenuController.gameEnded)
				{
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						//nothing
					}
					finally
					{
						if(MenuController.active && (GameCore.this.ms.getRemainingMonster() == 0))
						{
							GameCore.this.monsterNumber = (int)(GameCore.this.monsterNumber * 1.25);
							if(GameCore.this.monsterNumber > 100)
							{
								GameCore.this.monsterNumber = 100;
							}
							GameCore.this.ms = new MonsterSpawner(GameCore.this.monsterNumber,GameCore.this.startX,GameCore.this.startY,GameCore.this.hm);
							GameCore.this.ms.initializeSpawner();
							GameCore.this.ms.startSpawner();
						}
					}
				}
			}
		});
		clock.start();
	}
	
	public void stopSpawn()
	{
		if(this.started)
		{
			this.clock.interrupt();
		}
	}
}
