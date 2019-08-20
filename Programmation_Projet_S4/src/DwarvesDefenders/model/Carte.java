package DwarvesDefenders.model;

import java.util.ArrayList;
import java.util.Observable;

//Classe servant à créer et gérer les cases sous formes de tableau 2 dimensions
public class Carte extends Observable{
	public static int rowN;
	public static int colN;
	
	private int rowNumber;
	private int colNumber;
	private Case[][] cases;
	
	public Carte(int row, int col) {
		this.rowNumber = row;
		this.colNumber = col;
		
		rowN = row;
		colN = col;

		this.cases = new Case[row][col];
		for(int i = 0; i < row; ++i) {
			for(int j = 0; j < col; ++j) {
				cases[i][j] = new Case(false);
				/** De cette maniere la position d'une case est recuperable aupres d'elles */
				this.cases[i][j].setPositionsCase(i*GameCore.tileSize, j*GameCore.tileSize);
			}
		}		
	}
	
	public Case getCase(int i, int j) {
		return this.cases[i][j];
	}
	
	/** Methode permettant de remplacer une case de la carte */
	
	public void setCase(Case c, int i, int j)
	{
		this.cases[i][j] = c;
		this.cases[i][j].setPositionsCase(i*GameCore.tileSize, j*GameCore.tileSize);
		setChanged();
		notifyObservers();
	}
	
	/** Permet de trouver une case par son identifiant unique */
	public Case getCaseById(int id)
	{
		Case c = new Case(true);
		for(int i = 0; i < this.rowNumber; ++i) {
			for(int j = 0; j < this.colNumber; ++j) {
				if(this.cases[i][j].getIndex() == id)
				{
					c = this.cases[i][j];
				}
			}
		}
		return c;
	}

	//Methode renvoyant la liste des cases voisines à une case
	public Case[] voisins(Case c)
	{
		int i = (int)(c.getPositionCase()[0] / GameCore.tileSize);
		int j = (int)(c.getPositionCase()[1] / GameCore.tileSize);
		ArrayList<Case> cases = new ArrayList<Case>();
		for(int a = -1; a < 2; a++)
		{
				if(!((i+a == i) || (i+a<0) ))
				{
					if(i+a < rowNumber)
					{
						cases.add(this.getCase(i+a, j));
					}
				}
		}
		for(int b = -1; b < 2; b++)
		{
			if(!((j+b == j) || (j+b<0)) )
			{
				if( j+b < colNumber)
				{
					cases.add(this.getCase(i, j+b));
				}
			}
		}
		Case[] res = new Case[cases.size()];
		cases.toArray(res);
		return res;
	}
	
	/** Renvoi tout les voisins traversable d'une case */
	public Case[] voisinsTraversables(Case c)
	{
		Case[] cases = voisins(c);
		ArrayList<Case> casesTraversables = new ArrayList<Case>();
		for(Case ca : cases)
		{
			if(ca.isTraversable())
			{
				casesTraversables.add(ca);
			}
		}
		Case[] res = new Case[casesTraversables.size()];
		casesTraversables.toArray(res);
		return res;
	}
	
	/** Converti le tableau 2 dimension en 1 dimension 
	 * pour être utilisé pour la lecture simple des cases de la carte */
	public Case[] convertMap() 
	{
		Case[] result;
		ArrayList<Case> longMap = new ArrayList<Case>();
		for (int i = 0; i < rowN; i++) 
		{
			for (int j = 0; j < colN; j++) 
			{
				longMap.add(cases[i][j]);
			}
		}
		result = new Case[longMap.size()];
		longMap.toArray(result);
		return result;
	}
}