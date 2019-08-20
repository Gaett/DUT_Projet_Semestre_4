package DwarvesDefenders.model;

public class Position {
	//Point en haut � gauche de l'image
	Point HG;
	//Point en bas � droite de l'image
	Point BD;
	
	public Position()
	{
		HG = null;
		BD = null;
	}
	
	//D'abord le point Haut Gauche, puis l'autre
	public Position(Point a,Point b)
	{
		HG = a;
		BD = b;
	}
	
	//D'abord le point Haut Gauche, puis l'autre
	public Position(double hg1, double hg2, double bd1, double bd2)
	{
		HG = new Point(hg1,hg2);
		BD = new Point(bd1,bd2);
	}
	
	//D'abord le point Haut Gauche, puis l'autre
	public Point[] getPoints()
	{
		Point[] res = {HG,BD};
		return res;
	}
	
	//D'abord le point Haut Gauche en 1 , puis l'autre en 2
	public Point getPoint(int position)
	{
		if(position <= 0)
		{
			position = 0;
			return HG;
		}
		else
		{
			position = 1;
			return BD;
		}
	}
	
	//Renvoi la distance entre les 2 points de la position
	public double distanceBetweenPoints()
	{
		return(Math.sqrt(((HG.getPoint()[0] - BD.getPoint()[0]) * (HG.getPoint()[0] - BD.getPoint()[0])) + ((HG.getPoint()[1] - BD.getPoint()[1]) * (HG.getPoint()[1] - BD.getPoint()[1]))));
	}
	
	//Renvoi la distance entre les 2 points
	public static double distanceBetweenPoints(Point p1, Point p2)
	{
		return(Math.sqrt(((p1.getPoint()[0] - p2.getPoint()[0]) * (p1.getPoint()[0] - p2.getPoint()[0])) + ((p1.getPoint()[1] - p2.getPoint()[1]) * (p1.getPoint()[1] - p2.getPoint()[1]))));
	}
	
	//Cette méthode permet d'incrémenter les valeurs des 2 points
	//Ceci est une translation vectoriel
	public void incrementPosition(double x, double y)
	{
		this.HG.incrementPoint(x, y);
		this.BD.incrementPoint(x, y);
	}
	
	@Override
	public String toString()
	{
		String res = "";
		res+= "Point HG : " + HG.getPoint()[0] + " , " + HG.getPoint()[1] + " Point BD : " + BD.getPoint()[0] + " , " + BD.getPoint()[1];
		return res;
	}
}
