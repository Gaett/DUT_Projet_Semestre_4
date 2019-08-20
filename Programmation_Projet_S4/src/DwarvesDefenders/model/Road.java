package DwarvesDefenders.model;

public class Road extends Case{
	public int number = 0;
	
	public Road(int num) {
		super(true);
		this.number = num;
		super.setImage("DwarvesDefenders/img/herbe.png");
	}

}
