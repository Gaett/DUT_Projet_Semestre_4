package DwarvesDefenders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyBoardControls extends Observable {
	private Map<KeyCode,Boolean> keyTable;
	
	public void keyTableInitialisation()
	{
		keyTable = new HashMap<KeyCode, Boolean>();
		keyTable.put(KeyCode.Z, false);
		keyTable.put(KeyCode.S, false);
		keyTable.put(KeyCode.Q, false);
		keyTable.put(KeyCode.D, false);
		keyTable.put(KeyCode.SPACE, false);
	}
	
	public KeyBoardControls(Dwarf d)
	{
		this.keyTableInitialisation();
		addObserver(d);
	}
	
	public void onPress(KeyEvent e)
	{
		switch(e.getCode())
		{
			case Z:
				if(!keyTable.get(KeyCode.Z))
				{
					keyTable.put(KeyCode.Z, true);
				}
				break;
			case S:
				if(!keyTable.get(KeyCode.S))
				{
					keyTable.put(KeyCode.S, true);
				}
				break;
			case Q:
				if(!keyTable.get(KeyCode.Q))
				{
					keyTable.put(KeyCode.Q, true);
				}
				break;
			case D:
				if(!keyTable.get(KeyCode.D))
				{
					keyTable.put(KeyCode.D, true);
				}
				break;
			case SPACE:
				if(!keyTable.get(KeyCode.SPACE))
				{
					keyTable.put(KeyCode.SPACE, true);
					setChanged();
					notifyObservers(sortKeyCode(KeyCode.SPACE));
				}
				break;
		}
	}
	
	public void keyBoardActivity()
	{
		keyTable.forEach((key, value) -> {
			if(value && key != KeyCode.SPACE)
			{
				setChanged();
				notifyObservers(sortKeyCode(key));
			}
		});
	}
	
	public Message sortKeyCode(KeyCode k)
	{
		switch(k)
		{
			case Z:
					return(Message.MovementUP);
			case S:
					return(Message.MovementDOWN);
			case Q:
					return(Message.MovementLEFT);
			case D:
					return(Message.MovementRIGHT);
			case SPACE:
					return(Message.Attack);
		}
		return null;
	}
	
	public void onRelease(KeyEvent e)
	{
		switch(e.getCode())
		{
			case Z:
				keyTable.put(KeyCode.Z, false);
				break;
			case S:
				keyTable.put(KeyCode.S, false);
				break;
			case Q:
				keyTable.put(KeyCode.Q, false);
				break;
			case D:
				keyTable.put(KeyCode.D, false);
				break;
			case SPACE:
				keyTable.put(KeyCode.SPACE, false);
				break;
		}
	}
}
