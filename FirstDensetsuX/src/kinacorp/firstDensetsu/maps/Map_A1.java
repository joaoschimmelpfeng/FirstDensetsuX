package kinacorp.firstDensetsu.maps;


import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Entity.Entity_BuddyBat;
import kinacorp.firstDensetsu.Entity.Entity_Event;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

public class Map_A1 extends Map_Base
{
	int[] down = new int[] {0,1};
	int[] top = new int[] {2};
	
	boolean setCheckpoint;
	
	private static final String MAP_A1_DIALOG1 = "mapA1_Dialog1";
	
	Entity_BuddyBat bat;
	
	boolean alreadyTalk;
	boolean summonBat;
	

	public Map_A1(State_Gameplay gameplay) 
	{
	 super(gameplay);
	 mapLoader = new TmxMapLoader();
	 summonBat = true;
	 
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapA1.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 
	 gameplay.isNight = true;
	 gameplay.audioManager.createMusic("Sound/music01.mp3");
	 gameplay.audioManager.playMusic();
	 
	 alreadyTalk = gameplay.manager.main.android.getKey(MAP_A1_DIALOG1);
	 setCheckpoint = true;
	}
	
	public void Update()
	{
		
	 if(setCheckpoint)
	 {
	  gameplay.map_manager.checkpoint = this;
	  setCheckpoint = false;
	 }
	 
	 if(!alreadyTalk)
	 {
	  if(summonBat)
	  {
	   bat = new Entity_BuddyBat(gameplay,gameplay.player.pos.x,gameplay.player.pos.y,gameplay.player.facing);
	   gameplay.addEntity(bat);
	   summonBat = false;
	   return;
	  }
	  if(bat.finishMove)
	  {
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_A1_DIALOG1));
	   bat.batReturn = true;
	   alreadyTalk = true;
	   gameplay.manager.main.android.save(MAP_A1_DIALOG1, true);
	  }
	 }
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(ev.ev == 1)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchUp())
	  {
	   gameplay.map_manager.changeMap(3,new Map_A2(gameplay));
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
