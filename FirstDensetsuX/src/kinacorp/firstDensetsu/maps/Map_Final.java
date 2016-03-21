package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.State_Manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

public class Map_Final extends Map_Base
{
	private final static String MAP_FINAL_DIALOG = "map_final_dialog";
	private final static String MAP_FINAL_DIALOG2 = "map_final_dialog1";
	
	boolean firstStats;
	boolean scene1;
	boolean scene2;
	boolean acabar;
	
	public Map_Final(State_Gameplay gameplay) 
	{
	 super(gameplay);
	 mapLoader = new TmxMapLoader();
	 
	 firstStats = true;
	 acabar = false;
		
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapFinal.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 scene1 = true;
	}
	
	public void Update()
	{
	 if(firstStats)
	 {
	  gameplay.player.destinyY = 96;
	  gameplay.player.isMoving = true;
	  firstStats = false;
	 }
	 
	 if(!gameplay.player.isMoving && scene1)
	 {
	  gameplay.player.facing = 0;
	  gameplay.player.Update();
	  gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_FINAL_DIALOG));
	  scene1 = false;
	  scene2 = true;
	  gameplay.player.destinyY = -32;
	  gameplay.player.isMoving = true;
	  gameplay.player.facing = 3;
	  gameplay.player.Update();
	  return;
	 }
	 
	 if(!gameplay.player.isMoving && scene2)
	 {
	  gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_FINAL_DIALOG2));
	  scene2 = false;
	  acabar = true;
	  gameplay.player.destinyY = -32;
	  return;
	 }
	 
	 
	 
	 if(acabar)
	 {
	  gameplay.audioManager.disposeMusic();
	  gameplay.manager.changeState(State_Manager.TITLE);
	  gameplay.hud.blockInv = false;
	  gameplay.manager.main.android.deleteSharedPrefs();
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
