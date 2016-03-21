package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_TileAnimation extends Entity_Base
{
	Texture sheet;
	TextureRegion animA;
	TextureRegion animB;
	TextureRegion current;
	
	int tile;
	
	float stateTime;
	
	boolean isMoving;
	boolean isA;

	public Entity_TileAnimation(State_Gameplay gameplay, float x, float y,int tile) 
	{
	 super(gameplay, x, y);
	 this.tile = tile;
	 
	 if(tile == 1)
	 {
	  sheet = new Texture("data/TileSheet1.png");
	  animA = new TextureRegion(sheet,0,96,32,32);
	  animA.flip(false, true);
	  animB = new TextureRegion(sheet,32,96,32,32);
	  animB.flip(false, true);
	  current = animA;
	 }
	 else if (tile == 2)
	 {
	  sheet = new Texture("data/TileSheet2.png");
	  animA = new TextureRegion(sheet,0,96,32,32);
	  animA.flip(false, true);
	  animB = new TextureRegion(sheet,32,96,32,32);
	  animB.flip(false, true);
	  current = animA; 
	 }
	 
	 isA = true;
	 stateTime = 0;
	}
	
	public void Update()
	{
	 stateTime += Gdx.graphics.getDeltaTime();
	 if(stateTime >= 1)
	 {
	  stateTime = 0;
	  if(isA)
	  {
	   current = animB;
	   isA = false;
	  }
	  else
	  {
	   current = animA;
	   isA = true;
	  }
	 }
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(current,pos.x,pos.y);
	}
}
