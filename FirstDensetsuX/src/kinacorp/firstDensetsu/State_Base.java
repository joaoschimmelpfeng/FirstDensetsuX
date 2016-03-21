package kinacorp.firstDensetsu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State_Base 
{
	public State_Manager manager;
	
	public State_Base(State_Manager manager)
	{
	 this.manager = manager;
	}
	
	public void Update()
	{
		
	}
	
	public void Render(SpriteBatch batch)
	{
		
	}
}
