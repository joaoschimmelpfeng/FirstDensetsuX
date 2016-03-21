package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Entity.Entity_BuddyBat;
import kinacorp.firstDensetsu.Entity.Entity_Event;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

public class Map_A4 extends Map_Base
{
	private static final String MAP_A4_DIALOG1 = "mapA4_Dialog1";
	boolean alreadyTalk;
	boolean summonBat;
	Entity_BuddyBat bat;
	
	public Map_A4(State_Gameplay gameplay) 
	{
	 super(gameplay);
	 mapLoader = new TmxMapLoader();
	 summonBat = true;
		
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapA4.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 
	 alreadyTalk = gameplay.manager.main.android.getKey(MAP_A4_DIALOG1);
	}
	
	public void Update()
	{
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(ev.ev == 1)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchRight())
	  {
	   gameplay.map_manager.changeMap(2,new Map_A2(gameplay));
	  }
	  return;
	 }
	 
	 if(ev.ev == 2)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchLeft())
	  {
	   gameplay.map_manager.changeMap(1,new Map_A6(gameplay));
	  }
	  return;
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
