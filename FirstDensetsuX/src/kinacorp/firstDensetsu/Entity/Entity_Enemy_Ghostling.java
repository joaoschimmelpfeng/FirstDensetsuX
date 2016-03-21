package kinacorp.firstDensetsu.Entity;

import java.util.Random;

import kinacorp.firstDensetsu.State_Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_Enemy_Ghostling extends Entity_Base
{
	Texture sheet;
	TextureRegion current;
	TextureRegion animA;
	TextureRegion animB;
	TextureRegion dead;
	
	int direction;
	int oldDirection;
	
	float animTime = 0;
	float destinyX;
	float destinyY;
	float oldX;
	float oldY;
	float frequency;
	
	boolean isMoving = false;
	boolean isA;
	boolean dirChanged;
	
	Random rand;
	
	public Entity_Enemy_Ghostling(State_Gameplay gameplay, float x, float y,int level) 
	{
	 super(gameplay, x, y);
	 this.level = level;
	 isDead = false;
	 rand = new Random();
	 frequency = 0.5f;
	 
	 oldDirection = 1;
	 
     sheet = new Texture("data/charSheet1.png");
     animA = new TextureRegion(sheet,0,0,32,32);
     animA.flip(false, true);
     animB = new TextureRegion(sheet,32,0,32,32);
     animB.flip(false,true);
     dead = new TextureRegion(sheet,0,32,32,32);
     dead.flip(false, true);
     
	 current = animA;
	 isA = true;
	 
	}
	
	public void Update()
	{
	 if(isDead)
	 {
	  current = dead;
	  return;
	 }
	 colBox.set(pos.x,pos.y,32,32);
	 animTime += Gdx.graphics.getDeltaTime();
	 
	 if(animTime >= 0.5f)
	 {
	  animTime = 0;
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
	 
	 if(direction != oldDirection)
	 {
	  if(direction != 3 && direction != 0)
	  {
	   animA.flip(true,false);
	   animB.flip(true,false);
	   oldDirection = direction;
	  }
	 }
	  
	 if(!isMoving)
	 {
	  mvmTime += Gdx.graphics.getDeltaTime();
	  if(mvmTime > frequency)
	  {
	   mvmTime = 0;
	   direction = rand.nextInt(4);
	   move();
	  }
	 }
	 else
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
	
	public void move()
	{
	 
	 switch(direction)
	 {
	  case 0:
	   if(!gameplay.map_manager.getSolidTile((int)pos.x /32,(int)( pos.y + 32)/32))
	   {
		isMoving = true;
		destinyX = pos.x;
		destinyY = pos.y + 32;
		oldX = pos.x;
		oldY = pos.y;
	   }
	  break;
	  case 1:
	   if(!gameplay.map_manager.getSolidTile((int)(pos.x- 32)/32,(int)pos.y/32))
	   {
		isMoving = true;
		destinyX = pos.x - 32;
		destinyY = pos.y;
		oldX = pos.x;
		oldY = pos.y;
	   }
	  break;
	  
	  case 2:
		if(!gameplay.map_manager.getSolidTile((int)(pos.x+ 32)/32,(int)pos.y/32))
		{
		 isMoving = true;
		 destinyX = pos.x + 32;
		 destinyY = pos.y;
	     oldX = pos.x;
		 oldY = pos.y;
		}
	  break;
	  
	  case 3:
	   if(!gameplay.map_manager.getSolidTile((int)pos.x/32,(int)(pos.y- 32)/32))
  	   {
		isMoving = true;
		destinyX = pos.x;
		destinyY = pos.y -32;
		oldX = pos.x;
		oldY = pos.y;
	   }
	  break;
	 }
	}
	
	public void AIFunction(int f)
	{
	 switch(f)
	 {
	  case 0:
	   destinyX = oldX;
	   destinyY = oldY;
	  break;
	 }
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(current,pos.x,pos.y);
	}

}
