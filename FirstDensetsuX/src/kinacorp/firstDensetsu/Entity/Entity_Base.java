package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity_Base 
{
	public State_Gameplay gameplay;
	public Rectangle colBox;
	public Vector2 pos;
	
	int level;
	
	//Enemy purposes;
	float mvmTime;
	boolean isDead;
	boolean beSolid;
	
	public Entity_Base(State_Gameplay gameplay,float x, float y)
	{
	 this.gameplay = gameplay;
	 pos = new Vector2(x,y);
	 colBox = new Rectangle(x,y,32,32);
	 level = 0;
	}
	
	public void Update()
	{
		
	}
	
	public void AIFunction(int f)
	{
	 
	}
	
	public void Render(SpriteBatch batch)
	{
		
	}
}
