package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_BuddyBat extends Entity_Base
{
	
	public float destinyX;
	public float destinyY;
	public float oldX;
	public float oldY;
	
	public float stateTime;
	
	public boolean isA;
	public boolean isMoving;
	public boolean finishMove;
	public boolean gotPlayer;
	public boolean batReturn;
	
	public Texture sheet;
	public TextureRegion animA;
	public TextureRegion animB;
	public TextureRegion current;

	public Entity_BuddyBat(State_Gameplay gameplay, float x, float y,int facing) 
	{
	 super(gameplay, x, y);
	 stateTime = 0;
	 sheet = new Texture("data/charSheet1.png");
	 animA = new TextureRegion(sheet,64,0,32,32);
	 animA.flip(false,true);
	 animB = new TextureRegion(sheet,96,0,32,32);
	 animB.flip(false,true);
	 current = animA;
	 isA = true;
	 
	 batReturn = false;
	 
	 finishMove = false;
	 
	 oldX = pos.x;
     oldY = pos.y;
     if(facing == 0)
     {
      destinyX = pos.x;
      destinyY = pos.y + 32;
     }
     else if(facing == 1)
     {
      destinyX = pos.x - 32;
      destinyY = pos.y;
     }
     else if (facing == 2)
     {
      destinyX = pos.x + 64;
      destinyY = pos.y;
     }
     else if(facing == 3)
     {
      destinyX = pos.x;
      destinyY = pos.y - 32;
     }
     
     isMoving = true;
     gotPlayer = false;
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
	 
	 if(batReturn)
	 {
	  destinyX = gameplay.player.pos.x;
	  destinyY = gameplay.player.pos.y;
	  isMoving = true;
	 }
	 
	 if(isMoving)
	 {
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
      
      if(pos.x == destinyX && pos.y == destinyY)
	  {
	   isMoving = false;
	   finishMove = true;
	   if(batReturn)
	   {
		gameplay.deleteEntity(this);
	   }
	  }
     }
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(current,pos.x,pos.y);
	}

}
