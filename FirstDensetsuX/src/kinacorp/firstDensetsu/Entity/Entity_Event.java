package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;

public class Entity_Event extends Entity_Base
{
	public int ev;
	
	public Entity_Event(State_Gameplay gameplay, float x, float y,int ev) 
	{
	 super(gameplay, x, y);
	 this.ev = ev;
	}

}
