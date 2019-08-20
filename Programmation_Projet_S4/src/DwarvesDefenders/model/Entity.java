package DwarvesDefenders.model;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.image.Image;

//Cette classe d�finit le comportement commun � tout les objets de l'application
public class Entity extends Observable implements Observer{
	protected Image image;
	protected Position position;
	private HitManager hm;
	public Orientation facing = Orientation.Front;
	public boolean animationLaunched = false;
	
	public Entity(HitManager hm)
	{
		image = new Image("DwarvesDefenders/img/dwarf.png");
		position = new Position(0,0,0,0);
		setHitManager(hm);
	}
	
	public Entity(double hg1, double hg2, double bd1, double bd2,HitManager hm)
	{
		image = new Image("DwarvesDefenders/img/dwarf.png");
		position = new Position(hg1,hg2,bd1,bd2);
		setHitManager(hm);
	}
	
	public void setHitManager(HitManager hm)
	{
		if(hm != null)
		{
			this.deleteObserver(hm);
			this.hm = null;
		}
		this.hm = hm;
		addObserver(this.hm);
	}
	
	public HitManager getHitManager()
	{
		return this.hm;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage(String url)
	{
		this.image = new Image(url);
		this.setPosition(new Position(this.position.getPoint(0).getPoint()[0],this.position.getPoint(0).getPoint()[1],this.position.getPoint(0).getPoint()[0]+this.image.getWidth(),this.position.getPoint(0).getPoint()[0]+this.image.getHeight()));
	}
	
	public void setImage(Image img)
	{
		this.image = img;
		this.setPosition(new Position(this.position.getPoint(0).getPoint()[0],this.position.getPoint(0).getPoint()[1],this.position.getPoint(0).getPoint()[0]+this.image.getWidth(),this.position.getPoint(0).getPoint()[0]+this.image.getHeight()));
	}
	
	public Position getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Position p)
	{
		this.position = p;
	}
	
	public void incrementPosition(double x, double y)
	{
		this.position.incrementPosition(x, y);
		setChanged();
		notifyObservers(ObserverType.MovementManager);
	}
	
	public Point getCenter()
	{
		double x = this.position.getPoint(0).getPoint()[0] + this.position.getPoint(1).getPoint()[0];
		double y = this.position.getPoint(0).getPoint()[1] + this.position.getPoint(1).getPoint()[1];
		return new Point(x/2,y/2);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
	
}
