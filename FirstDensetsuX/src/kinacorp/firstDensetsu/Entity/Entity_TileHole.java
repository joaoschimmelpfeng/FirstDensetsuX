package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.maps.Map_Base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_TileHole extends Entity_Base
{
	Map_Base toWhere;
	
	TextureRegion solid;
	
	boolean teleport;
	boolean initiateTransition;
	float pX;
	float pY;
	
	public Entity_TileHole(State_Gameplay gameplay, float x, float y,boolean teleport,Map_Base toWhere,float pX,float pY) 
	{
	 super(gameplay, x, y);
	 this.teleport = teleport;
	 this.toWhere = toWhere;
	 this.pX = pX;
	 this.pY = pY;
	 colBox.set(x+ 10,y + 10,12,12);
	 initiateTransition = false;
	 solid = new TextureRegion(new Texture("data/TileSheet3.png"),64,128,32,32);
	 solid.flip(false,true);
	 beSolid = false;
	}
	
	
	public void Update()
	{
	 if(gameplay.player.megaFat || beSolid)
	 {
	  return;
	 }
     
	 if(colBox.overlaps(gameplay.player.colBox))
	 {
	  if(teleport)
	  {
	   
	   if(!gameplay.player.isFalling && !gameplay.player.finishedFall)
	   {
		gameplay.player.isFalling = true;
		gameplay.player.pos.x = pos.x;
		gameplay.player.pos.y = pos.y;
		gameplay.player.iniX = pos.x;
		gameplay.player.iniY = pos.y;
	   }
	   if(gameplay.player.finishedFall)
	   {
		if(!initiateTransition)
		{
		 gameplay.map_manager.changeMapFade(toWhere);
		 initiateTransition = true;
		}
	   }
	  }
	  else
	  {
	   if(!gameplay.player.isFalling && !gameplay.player.finishedFall)
	   {
		gameplay.player.isFalling = true;
		gameplay.player.pos.x = pos.x;
		gameplay.player.pos.y = pos.y;
	   }
	   if(gameplay.player.finishedFall)
	   {
		gameplay.player.finishedFall = false;
		gameplay.player.doDamage(1);
	   }
	  }
	 }
	 
	 if(teleport)
	 {
	  if(gameplay.isDark)
	  {
	   gameplay.map_manager.finishedTransition();
	   gameplay.player.iniX = pX;
	   gameplay.player.iniY = pY;
	   gameplay.player.pos.x = pX;
	   gameplay.player.pos.y = pX;
	   gameplay.player.destinyX = pX;
	   gameplay.player.destinyY = pY;
	   gameplay.player.isMoving = false;
	   gameplay.isDark = false;
	  }
	 }
	 
	}
	
	public void Render(SpriteBatch batch)
	{
	 if(beSolid)
	 {
	  batch.draw(solid,pos.x,pos.y); 
	 }
	}
	
	
}
