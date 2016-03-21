package kinacorp.firstDensetsu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kinacorp.firstDensetsu.Entity.Entity_Base;
import kinacorp.firstDensetsu.Entity.Entity_BossOrb;
import kinacorp.firstDensetsu.Entity.Entity_Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class State_Gameplay extends State_Base
{
	public Tools_Hud hud;
	public Tools_Effect effects;
	public Tools_Music audioManager;
	
	public Entity_Player player;
	public Map_Manager map_manager;
	
	public TextureRegion night;
	
	public Vector3 touch;
	
	public Rectangle touchRect;
	
	public List<Entity_Base> entityList;
	public List<Entity_Base> removeList;
	public List<Entity_Base> addList;
	
	public Color color;
	
	public boolean changeMap = false;
	public boolean changeY = false;
	public boolean fadeOut = false;
	public boolean fadeIn = false;
	public boolean getColor = true;
	public boolean init = true;
	public boolean getColor1 = true;
	public boolean alterColor1 = true;
	public boolean isDark = false;
	public boolean dead = false;
	public float alpha = 0;
	public float alpha2 = 0;
	public float changeDir = 0;
	public float changeDestiny = 0;
	public float colorTime = 0;
	public float deadTime = 0;
	
	public boolean isNight;
	
	
	public State_Gameplay(State_Manager manager) 
	{
	 super(manager);
	 hud = new Tools_Hud(this);
	 effects = new Tools_Effect(this);
	 audioManager = new Tools_Music(this);
	 audioManager.habilitVolume = true;
	 
	 player = new Entity_Player(this,32*5,32 * 3);
	 touch = new Vector3(0,0,0);
	 touchRect = new Rectangle(0,0,8,8);
	 night = new TextureRegion(new Texture("data/nite.png"));
	 
	 entityList = new ArrayList<Entity_Base>();
	 removeList = new ArrayList<Entity_Base>();
	 addList = new ArrayList<Entity_Base>();
	 
	 color = Color.WHITE;
	 
	 map_manager = new Map_Manager(this);
	}
	
	public void Update()
	{
	 touch.x = Gdx.input.getX();
	 touch.y = Gdx.input.getY();
	 manager.main.camera.unproject(touch);
	 touchRect.x = touch.x;
	 touchRect.y = touch.y;
	 
	 flushEntity();
	 
	 audioManager.Update();
	 
	 if(player.health <= 0)
	 {
	  player.die();
	  player.inAnim = false;
	  deadTime += Gdx.graphics.getDeltaTime();
	  if(!dead && deadTime >= 1)
	  {
       map_manager.changeMapFade(map_manager.checkpoint);
       dead = true;
	  }
	  if(isDark)
	  {
	   manager.main.android.showAd();
	   map_manager.finishedTransition();
	   player.pos.x = 96;
	   player.pos.y = 128;
	   player.iniX = 96;
	   player.iniY = 128;
	   player.destinyX = 96;
	   player.destinyY = 128;
	   player.health = 3;
	   dead = false;
	   player.backToNormal();
	   hud.isInBoss = false;
	   isDark = false;
	   deadTime = 0;
	  }
	 }
	 
	 if(hud.dialog.showDialog)
	 {
	  hud.Update();
	   
	  return;
	 }
	 
	 if(hud.inventory.showInventory)
	 {
	  hud.Update();
	  return;
	 }
	 
	 if(changeMap)
	 {
	  if(changeY)
	  {
	   map_manager.map.pos.y += Math.round(changeDir * Gdx.graphics.getDeltaTime());
	   map_manager.map2.pos.y += Math.round(changeDir * Gdx.graphics.getDeltaTime());
	   player.pos.y += Math.round(changeDir * Gdx.graphics.getDeltaTime());
	   for(int i = 0; i < entityList.size(); i++)
	   {
		entityList.get(i).pos.y += Math.round(changeDir * Gdx.graphics.getDeltaTime());
	   }
	   
	   if(changeDir > 0)
	   {
	    if(map_manager.map2.pos.y > changeDestiny)
	    {
	     player.destinyY = 32 * 8;
	     player.destinyX = player.pos.x;
	     player.iniY = 32 * 8;
	     player.iniX = player.pos.x;
		 player.isMoving = true;
		 map_manager.finishedTransition(); 
		 return;
	    }
	   }
	   else
	   {
	    if(map_manager.map2.pos.y < changeDestiny)
	    {
	     player.destinyY = 0;
	     player.destinyX = player.pos.x;
	     player.iniY = 0;
	     player.iniX = player.pos.x;
		 player.isMoving = true;
 		 map_manager.finishedTransition();
	 	 return;
	    }
	   }
	  }
	  else
	  {
	   map_manager.map.pos.x += Math.round(changeDir * Gdx.graphics.getDeltaTime());
	   map_manager.map2.pos.x += Math.round(changeDir * Gdx.graphics.getDeltaTime());
	   player.pos.x += Math.round(changeDir * Gdx.graphics.getDeltaTime());
	   for(int i = 0; i < entityList.size(); i++)
	   {
		entityList.get(i).pos.x += Math.round(changeDir * Gdx.graphics.getDeltaTime());
	   }
	   
	   if(changeDir > 0)
	   {
	    if(map_manager.map2.pos.x > changeDestiny)
	    {
	     player.destinyX = 32 * 9;
	     player.destinyY = player.pos.y;
	     player.iniX = 32 * 9;
	     player.iniY = player.pos.y;
	     player.isMoving = true;
	 	 map_manager.finishedTransition(); 
		 return;
	    }
	   }
	   else
	   {
	    if(map_manager.map2.pos.x < changeDestiny)
	    {
	     player.destinyX = 0;
	     player.destinyY = player.pos.y;
	     player.iniX = 0;
	     player.iniY = player.pos.y;
	     player.isMoving = true;
		 map_manager.finishedTransition();
		 return;
	    }
	   }
	  }
	  
	  
	  
	  return;
	 }
	 
	 hud.Update();
	 player.Update();
	 map_manager.Update();
	 
	 for(int i = 0; i < entityList.size(); i++)
	 {
	  entityList.get(i).Update();
	 }
	}
	
	public Entity_Base EntityCollision(Entity_Base e1)
	{
	 for(int i = 0; i < entityList.size();i++)
	 {
	  if(e1.colBox.overlaps(entityList.get(i).colBox))
	  {
	   return entityList.get(i);
	  }
	 }
	 return null;
	}
	
	public void deleteEntity(Entity_Base e)
	{
	 removeList.add(e);
	}
	
	public void deleteEntityAll(Collection<Entity_Base> e)
	{
	 removeList.addAll(e);
	}
	
	public void addEntity(Entity_Base e)
	{
	 addList.add(e);
	}
	
	public void flushEntity()
	{
	 entityList.removeAll(removeList);
	 entityList.addAll(addList);
	 removeList.removeAll(removeList);
	 addList.removeAll(addList);
	}
	
	public void init(SpriteBatch batch)
	{
	 if(getColor1)
	 {
	  color = batch.getColor();
	  color.a = 0;
	  getColor1 = false;
	 }
	 if(alterColor1)
	 {
	  colorTime += Gdx.graphics.getDeltaTime() / 10;
	  color.lerp(Color.WHITE,colorTime);
	  if(color.a >= 1)
	  {
	   alterColor1 = false;
	   init = false;
	  }
	 }
	 batch.setColor(color);
	 map_manager.RenderBot(batch);
	 for(int i = 0; i < entityList.size(); i++)
	 {
	  entityList.get(i).Render(batch);
	 }
	 player.Render(batch);
	 map_manager.RenderTop(batch);
	 batch.draw(night,0,0,320,288);
	 batch.setColor(Color.WHITE);
	 hud.Render(batch);
	}
	
	public void bossCheck(Rectangle e)
	{
	 for(int i = 0; i < entityList.size();i++)
	 {
	  if(entityList.get(i) instanceof Entity_BossOrb)
	  {
	   if(entityList.get(i).colBox.overlaps(e))
	   {
		((Entity_BossOrb) entityList.get(i)).goBack();
	   }
	  }
	 }
	}
	
	public void Render(SpriteBatch batch)
	{
     if(init)
     {
      init(batch);
      return;
     }
     if(fadeOut)
     {
      if(getColor)
      {
       color = batch.getColor();
       getColor = false;
       alpha = 0;
      }
      alpha += Gdx.graphics.getDeltaTime();
      if(alpha >= 1)
      {
       fadeIn = true;
       isDark = true;
      }
      if(fadeIn)
      {
       alpha2 += Gdx.graphics.getDeltaTime();
       color.lerp(Color.WHITE, alpha2);
       if(alpha2 >= 1)
       {
    	fadeIn = false;
    	fadeOut = false;
    	getColor = true;
    	isDark = false;
    	alpha = 0;
    	alpha2 = 0;
    	color = Color.WHITE;
       }
      }
      else
      {
       color.lerp(Color.BLACK, alpha);
      }
      
      batch.setColor(color);
     }
     
     
	 map_manager.RenderBot(batch);
	 for(int i = 0; i < entityList.size(); i++)
	 {
	  entityList.get(i).Render(batch);
	 }
	 
	 if(!map_manager.inFade)
	 {
	  player.Render(batch);
	 }
	 map_manager.RenderTop(batch);
	 
	 if(isNight)
	 {
	  batch.draw(night,0,0,320,288);
	 }
	 
	 if(fadeOut)
	 {
	  batch.setColor(Color.WHITE);
	 }
	 
	 audioManager.Render(batch);
	 hud.Render(batch);
	}

}
