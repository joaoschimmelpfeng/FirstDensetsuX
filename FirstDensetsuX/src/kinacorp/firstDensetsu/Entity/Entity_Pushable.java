package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Entity_Pushable extends Entity_Base
{
	TextureRegion current;
	
	Rectangle xAxis;
	Rectangle yAxis;
	Rectangle xAxis2;
	Rectangle yAxis2;
	
	boolean isMoving;
	float destinyX;
	float destinyY;
	int type;

	public Entity_Pushable(State_Gameplay gameplay, float x, float y,int type) 
	{
	 super(gameplay, x, y);
	 
	 xAxis = new Rectangle(0,0,0,0);
	 yAxis = new Rectangle(0,0,0,0);
	 xAxis2 = new Rectangle(0,0,0,0);
	 yAxis2 = new Rectangle(0,0,0,0);
	 
	 if(type == 1 )
	 {
	  current = new TextureRegion(new Texture("data/TileSheet1.png"),224,192,32,32);
	  current.flip(false, true);
	 }
	 else
	 {
      current = new TextureRegion(new Texture("data/TileSheet2.png"),0,128,32,32);
	  current.flip(false, true);
	 }
	}
	
	public void Update()
	{
	 colBox.set(pos.x + 1,pos.y + 1,30,30);
	 xAxis.set(pos.x - 31,pos.y,31,31);
	 yAxis.set(pos.x,pos.y -31,31,31);
	 xAxis2.set(pos.x + 31,pos.y,31,31);
	 yAxis2.set(pos.x,pos.y + 31,31,31);
	 
	 if(gameplay.player.usingItem)
	 {
	  return;
	 }
	 
	 if(xAxis.overlaps(gameplay.player.colBox) && !gameplay.player.isMoving && !isMoving)
	 {
	  if(gameplay.player.newInput && gameplay.hud.touchRight())
	  {
	   if(!gameplay.map_manager.getSolidTileForEntity((int)((pos.x + 32)/ 32),(int) pos.y / 32))
	   {
	    destinyX = pos.x + 32;
	    destinyY = pos.y;
	    isMoving = true;
	   }
	  }
	 }
	 else if(xAxis2.overlaps(gameplay.player.colBox) && !gameplay.player.isMoving && !isMoving)
	 {
	  if(gameplay.player.newInput && gameplay.hud.touchLeft())
	  {
	   if(!gameplay.map_manager.getSolidTileForEntity((int)((pos.x - 32)/ 32),(int) pos.y / 32))
	   {
		destinyX = pos.x - 32;
		destinyY = pos.y;
		isMoving = true;
	   }  
	  }
	 }
	 else if(yAxis.overlaps(gameplay.player.colBox) && !gameplay.player.isMoving && !isMoving)
	 {
	  if(gameplay.player.newInput && gameplay.hud.touchDown())
	  {
	   if(!gameplay.map_manager.getSolidTileForEntity((int)pos.x / 32,(int)((pos.y + 32) / 32)))
	   {
		destinyX = pos.x;
		destinyY = pos.y + 32;
		isMoving = true;
	   }
	  }
	 }
	 else if(yAxis2.overlaps(gameplay.player.colBox) && !gameplay.player.isMoving && !isMoving)
	 {
	  if(gameplay.player.newInput && gameplay.hud.touchUp())
	  {
	   if(!gameplay.map_manager.getSolidTileForEntity((int)pos.x / 32,(int)((pos.y - 32) / 32)))
	   {
		destinyX = pos.x;
		destinyY = pos.y - 32;
		isMoving = true;
	   }
	  }
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
	  }
	 }
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(current,pos.x,pos.y);
	}
	
}
