package DwarvesDefenders.model;

public enum Weapon {
	Fist(1.0,30.0),
	Rusty_Axe(4.0,60.0),
	Durandal(8.0,100.0);
	
	private final double damage;
	//Range en pixel
	private final double range;
	
	Weapon(double dam, double ran)
	{
		this.damage = dam;
		this.range = ran;
	}
	
	public double getDamage()
	{
		return this.damage;
	}
	
	public double getRange()
	{
		return this.range;
	}
}
