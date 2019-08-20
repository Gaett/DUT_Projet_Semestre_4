package DwarvesDefenders.model;

public class Point {
	private double x;
	private double y;
	
	public Point()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	//D'abord le x, puis le y
	public double[] getPoint()
	{
		double[] point = {this.x,this.y};
		return point;
	}
	
	//Cette méthode permet d'increment les valeurs x et y du point
	public void incrementPoint(double x,double y)
	{
		this.x += x; 
		this.y += y;
		if(this.x < 0)
		{
			this.x = 0;
		}
		if(this.y < 0)
		{
			this.y = 0;
		}
	}
	
	@Override
	public String toString()
	{
		String res = "";
		res += "X : " + this.x + " Y : " + this.y;
		return res;
	}
	
	
}
