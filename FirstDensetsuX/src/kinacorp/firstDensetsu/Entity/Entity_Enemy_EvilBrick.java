package kinacorp.firstDensetsu.Entity;

import java.util.Random;

import kinacorp.firstDensetsu.State_Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_Enemy_EvilBrick extends Entity_Base
{
	Texture sheet;
	TextureRegion current;
	TextureRegion animA;
	TextureRegion animB;
	TextureRegion dead;
	
	int push;
	
	float animTime = 0;
	float destinyX;
	float destinyY;
	float oldX;
	float oldY;
	float frequency;
	
	boolean isMoving;
	boolean isA;
	boolean isLeft;
	boolean timing;
	
	Random rand;

	public Entity_Enemy_EvilBrick(State_Gameplay gameplay, float x, float y) 
	{
		super(gameplay, x, y);
		isDead = false;
		push = 0;
		rand = new Random();
		frequency = 0.5f;
		timing = false;
		isLeft = true;
		
	    sheet = new Texture("data/charSheet1.png");
	    animA = new TextureRegion(sheet,0,96,32,32);
	    animA.flip(false, true);
	    animB = new TextureRegion(sheet,32,96,32,32);
	    animB.flip(false,true);
	    dead = new TextureRegion(sheet,0,32,32,32);
	    dead.flip(false, true);
	    
	    destinyY = y;
	    destinyX = x;
	    
	    isMoving = true;
		current = animA;
		isA = true;
	}
	
	public void Update()
	{
     colBox.set(pos.x,pos.y,32,32);
     
     if(destinyX == pos.x)
	 {
	  if(isLeft)
	  {
	   if(!gameplay.map_manager.getSolidTileForEntity((int)((pos.x - 32)/32),(int)(pos.y/32)))
	   {
	    destinyX = pos.x-32;
	   }
	   else
	   {
		isLeft = false;
		return;
	   }
	  }
	  else
	  {
	   if(!gameplay.map_manager.getSolidTileForEntity((int)((pos.x + 32)/32),(int)(pos.y/32)))
	   {
	    destinyX = pos.x+32;
	   }
	   else
	   {
		isLeft = true;
		return;
	   }  
	  }
	 }
     
     
	 if(!gameplay.player.colBox.overlaps(colBox))
	 {
	  timing = false;
	 }
	 
	 if(gameplay.player.colBox.overlaps(colBox) && !timing)
	 {
	  push = rand.nextInt(2);
	  if(push == 0)
	  {
	   gameplay.player.destinyY = gameplay.player.destinyY + 32;
	   if(gameplay.player.megaFat)
	   {
		gameplay.player.steps += 1;
	   }
	   gameplay.player.isMoving = true;
	  }
	  else
	  {
	   gameplay.player.destinyY = gameplay.player.destinyY - 32;
	   if(gameplay.player.megaFat)
	   {
		gameplay.player.steps += 1;
	   }
	   gameplay.player.isMoving = true;  
	  }
	  timing = true;
	 }
	 
	 if(isMoving)
	 {
	  if(pos.x == destinyX && pos.y == destinyY)
	  {
	   isMoving = false;
	  }
	  
	   if(pos.x > destinyX)
	   {
	    pos.x -= Gdx.graphics.getDeltaTime() * 100;
	    if(pos.x < destinyX)
        {
      	 pos.x = destinyX;
	    }
	   }
	   else
	   {
	    pos.x += Gdx.graphics.getDeltaTime() * 100;  
	    if(pos.x > destinyX)
	    {
	     pos.x = destinyX;
	    }
	   }
	      
	   if(pos.y > destinyY)
	   {
	    pos.y -= Gdx.graphics.getDeltaTime() * 100;
	    if(pos.y < destinyY)
	    {
	     pos.y = destinyY;
	    }
	   }
	   else
	   {
	    pos.y += Gdx.graphics.getDeltaTime() * 100;
	    if(pos.y > destinyY)
	    {
	     pos.y = destinyY;
	    }
	   }
      }
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(current,pos.x,pos.y);
	}

}
