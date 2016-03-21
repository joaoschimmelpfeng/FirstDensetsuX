package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Entity.Entity_BuddyBat;
import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_Stairs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

public class Map_B1 extends Map_Base
{
	public static final String MAP_B1_DIALOG1 = "mapB1_Dialog1";
	public static final String MAP_B1_DIALOG2 = "mapB1_Dialog2";
	
	Entity_Stairs stairs1;
	Entity_Stairs stairs2;
	
	boolean initStairs;
	boolean alreadyTalk;
	boolean summonBat;
	boolean setCheckpoint;
	
	Entity_BuddyBat bat;

	public Map_B1(State_Gameplay gameplay) 
	{
	 super(gameplay);
     mapLoader = new TmxMapLoader();
     
     initStairs = true;
	 
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapB1.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 
	 gameplay.audioManager.createMusic("Sound/Unity.mp3");
	 gameplay.audioManager.playMusic();
	 
	 alreadyTalk = gameplay.manager.main.android.getKey(MAP_B1_DIALOG2);
	 summonBat = true;
	 setCheckpoint = true;
	}
	
	public void Update()
	{
	 if(setCheckpoint)
	 {
	  gameplay.map_manager.checkpoint = this;
	  setCheckpoint = false;
	 }
	 
	 if(initStairs)
	 {
	  stairs1 = new Entity_Stairs(gameplay,256,224,256,96,new Map_F5(gameplay));
	  stairs2 = new Entity_Stairs(gameplay,32,224,256,96,new Map_F6(gameplay));
	  initStairs = false;
	 }
	 
	 if(stairs1.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairs1.initTransition();
	 }
	 
	 if(stairs2.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairs2.initTransition();
	 }
	 
	 if(!alreadyTalk)
	 {
	  if(summonBat)
	  {
	   bat = new Entity_BuddyBat(gameplay,gameplay.player.pos.x,gameplay.player.pos.y,gameplay.player.facing);
	   bat.destinyX = gameplay.player.pos.x - 32;
	   bat.destinyY = gameplay.player.pos.y;
	   gameplay.addEntity(bat);
	   summonBat = false;
	   return;
	  }
	  if(bat.finishMove)
	  {
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_B1_DIALOG2));
	   bat.batReturn = true;
	   alreadyTalk = true;
	   gameplay.manager.main.android.save(MAP_B1_DIALOG2, true);
	  }
	 }
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(ev.ev == 1)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchDown())
	  {
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_B1_DIALOG1));
	   gameplay.player.destinyY = gameplay.player.pos.y - 32;
	   gameplay.player.destinyX = gameplay.player.pos.x;
	   gameplay.player.isMoving = true;
	  }
	 }
	 
	 if(ev.ev == 2)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchUp())
	  {
	   gameplay.map_manager.changeMap(3,new Map_B2(gameplay));
	  }
	 }
	}
	
	
	public void RenderUnder(SpriteBatch batch)
	{
	 for(int i = 0; i < botLayer.getWidth(); i ++)
	 {
	  for(int j = 0; j < botLayer.getHeight();j ++)
	  {
	   if(botLayer.getCell(i,j) != null)
	   {
	    batch.draw(botLayer.getCell(i,j).getTile().getTextureRegion(),pos.x + (i * 32),pos.y + (j*32));
	   }
	  }
	 }
	 
	 for(int i = 0; i < bot2Layer.getWidth(); i ++)
	 {
	  for(int j = 0; j < bot2Layer.getHeight();j ++)
	  {
	   if(bot2Layer.getCell(i,j) != null)
	   {
	    batch.draw(bot2Layer.getCell(i,j).getTile().getTextureRegion(),pos.x + (i * 32),pos.y + (j*32));
	   }
	  }
	 }
	}
	
	public void RenderTop(SpriteBatch batch)
	{
	 for(int i = 0; i < topLayer.getWidth(); i ++)
	 {
	  for(int j = 0; j < topLayer.getHeight();j ++)
	  {
	   if(topLayer.getCell(i,j) != null)
	   {
	    batch.draw(topLayer.getCell(i,j).getTile().getTextureRegion(),pos.x + (i * 32),pos.y + (j*32));
	   }
	  }
	 }
	}

}
