package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_BossOrb extends Entity_Base
{
	
	public TextureRegion texture;
	
	public float destinyX;
	public float destinyY;
	public float oldX;
	public float oldY;
	public float speed = 100;
	public boolean hurtBoss = false;
	public boolean isMoving = true;

	public Entity_BossOrb(State_Gameplay gameplay, float x, float y,Texture sheet) 
	{
     super(gameplay, x, y);
     
     texture = new TextureRegion(sheet,224,96,32,32);
     texture.flip(false, true);
     
     destinyX = (gameplay.player.pos.x + 8);
     destinyY = (gameplay.player.pos.y-16);
     
     oldX = x;
     oldY = y;
     
	}
	
	public void Update()
	{
	 colBox.set(pos.x + 4,pos.y + 21,8,11);
		if(isMoving)
		 {
		  if(pos.x < destinyX)
		  {
		   pos.x += speed * Gdx.graphics.getDeltaTime();
		  }
		  
		  if(pos.x > destinyX)
		  {
		   pos.x -= speed * Gdx.graphics.getDeltaTime();  
		  }
		  
		  if(pos.y < destinyY)
		  {
		   pos.y += speed * Gdx.graphics.getDeltaTime();
		  }
		  
		  if(pos.y > destinyY)
		  {
		   pos.y -= speed * Gdx.graphics.getDeltaTime();
		  }
		  
	      
	      if(pos.x == destinyX && pos.y == destinyY)
		  {
		   isMoving = false;
		  }
	     }
		
		if(colBox.overlaps(gameplay.player.colBox))
		{
	     gameplay.deleteEntity(this);
	     gameplay.player.health -= 1;
		}
	}
	
	public void goBack()
	{
	 destinyX = oldX;
	 destinyY = oldY;
	 hurtBoss = true;
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(texture,pos.x,pos.y);
	}

}
