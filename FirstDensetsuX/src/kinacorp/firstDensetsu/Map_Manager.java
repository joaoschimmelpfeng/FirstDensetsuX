package kinacorp.firstDensetsu;


import kinacorp.firstDensetsu.Entity.Entity_Chest;
import kinacorp.firstDensetsu.Entity.Entity_Cutable;
import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_Pushable;
import kinacorp.firstDensetsu.Entity.Entity_TileHole;
import kinacorp.firstDensetsu.maps.Map_A1;
import kinacorp.firstDensetsu.maps.Map_Base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Map_Manager 
{
	State_Gameplay gameplay;
	
	public Map_Base map;
	public Map_Base map2;
	public Map_Base checkpoint;
	
	public Rectangle collision;
	
	public boolean loadMap;
	public boolean waitingFade;
	public boolean inFade;
	public float stateTime;
	public float transictionTime;
	
	
	public Map_Manager(State_Gameplay gameplay)
	{
	 this.gameplay = gameplay;
	 inFade = false;
	 collision = new Rectangle(0,0,0,0);
	 
	 transictionTime = 200;
	 
	 map = new Map_A1(gameplay);
	 checkpoint = map;
	 map.loadEntities();
	 stateTime = 0;
	}
	
	public void Update()
	{
	 map.Update();
	}
	
	public void callEvent(Entity_Event ev)
	{
	 map.callEvent(ev);
	}
	
	public boolean getSolidTile(int x, int y)
	{
	 if(x >= map.collisionLayer.getWidth() || x < 0
			 || y > map.collisionLayer.getHeight() -1 || y < 0)
	 {
	  return true;
	 }
	 
	 if(getSolidEntity(x,y))
	 {
	  return true;
	 }
	 
	 return map.collisionLayer.getCell(x, y).getTile().getProperties().containsKey("Solid");
	}
	
	public boolean getSolidTileForEntity(int x, int y)
	{
	 if(x >= map.collisionLayer.getWidth() || x < 0
			 || y > map.collisionLayer.getHeight() -1 || y < 0)
	 {
	  return true;
	 }
	 
	 if(getSolidEntityForEntity(x,y))
	 {
	  return true;
	 }
	 
	 return map.collisionLayer.getCell(x, y).getTile().getProperties().containsKey("Solid");
	}
	
	public boolean getSolidEntityForEntity(int x, int y)
	{
	 collision.set(x * 32,y * 32,32,32);
	 for(int i = 0; i < gameplay.entityList.size();i++)
	 {
	  if(gameplay.entityList.get(i) instanceof Entity_Pushable)
	  {
	   if(gameplay.entityList.get(i).colBox.overlaps(collision))
	   {
	    return true;
	   }
	  }
	  else if (gameplay.entityList.get(i) instanceof Entity_Chest)
	  {
	   if(gameplay.entityList.get(i).colBox.overlaps(collision))
	   {
	    return true;
	   }  
	  }
	  else if (gameplay.entityList.get(i) instanceof Entity_Cutable)
	  {
	   if(gameplay.entityList.get(i).colBox.overlaps(collision))
	   {
	    return true;
	   }
	  }
	  else if (gameplay.entityList.get(i) instanceof Entity_TileHole)
	  {
	   if(gameplay.entityList.get(i).colBox.overlaps(collision))
	   {
	    return true;
	   }
	  }
	 }
	 return false;
	}
	
	public boolean getSolidEntity(int x, int y)
	{
	 collision.set(x * 32,y * 32,32,32);
	 for(int i = 0; i < gameplay.entityList.size();i++)
	 {
	  if(gameplay.entityList.get(i) instanceof Entity_Pushable)
	  {
	   if(gameplay.entityList.get(i).colBox.overlaps(collision))
	   {
	    return true;
	   }
	  }
	  else if (gameplay.entityList.get(i) instanceof Entity_Chest)
	  {
	   if(gameplay.entityList.get(i).colBox.overlaps(collision))
	   {
	    return true;
	   }  
	  }
	  else if (gameplay.entityList.get(i) instanceof Entity_Cutable)
	  {
	   if(gameplay.entityList.get(i).colBox.overlaps(collision))
	   {
	    return true;
	   }  
	  }
	 }
	 return false;
	}
	
	public void changeMapFade(Map_Base nextMap)
	{
     map2 = nextMap;
     gameplay.fadeOut = true;
     waitingFade = true;
     inFade = true;
	}
	
	public void changeMap(int dir,Map_Base nextMap)
	{
	 switch(dir)
	 {
	  case 0:
	   gameplay.changeY = true;
	   gameplay.changeMap = true;
	   gameplay.changeDir = -transictionTime;
	   gameplay.changeDestiny = 0;
	   loadMap = true;
	   map2 = nextMap;
	   map2.pos.y = 32 * 9;
	  break;
	  
	  case 1:
	   gameplay.changeY = false;
	   gameplay.changeMap = true;
	   gameplay.changeDir = transictionTime;
	   gameplay.changeDestiny = 0;
	   loadMap = true;
	   map2 = nextMap;
	   map2.pos.x = -320;
	  break;
	  
	  case 2:
	   gameplay.changeY = false;
	   gameplay.changeMap = true;
	   gameplay.changeDir = -transictionTime;
	   gameplay.changeDestiny = 0;
	   loadMap = true;
	   map2 = nextMap;
	   map2.pos.x = 320;
	  break;
	  
	  case 3:
       gameplay.changeY = true;
	   gameplay.changeMap = true;
	   gameplay.changeDir = transictionTime;
	   gameplay.changeDestiny = 0;
	   loadMap = true;
	   map2 = nextMap;
	   map2.pos.y = -32 * 9;
	  break;
	 }
	}
	
	public void finishedTransition()
	{
	 gameplay.changeMap = false;
	 inFade = false;
	 loadMap = false;
	 waitingFade = false;
	 map = map2;
	 map.pos.x = 0;
	 map.pos.y = 0;
	 gameplay.deleteEntityAll(gameplay.entityList);
	 gameplay.flushEntity();
	 map.loadEntities();
	 gameplay.flushEntity();
	 gameplay.effects.callSpawn();
	 gameplay.deadTime = 0;
	}
	
	public void RenderBot(SpriteBatch batch)
	{
	 map.RenderUnder(batch);
	 if(loadMap)
	 {
	  map2.RenderUnder(batch);
	 }
	}
	
	public void RenderTop(SpriteBatch batch)
	{
	 map.RenderTop(batch);
	 if(loadMap)
	 {
	  map2.RenderTop(batch);
	 }
	}
	
}
