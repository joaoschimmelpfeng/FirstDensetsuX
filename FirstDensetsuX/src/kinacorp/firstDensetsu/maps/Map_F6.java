package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;
import kinacorp.firstDensetsu.Entity.Entity_Chest;
import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_SpawnCloud;
import kinacorp.firstDensetsu.Entity.Entity_Stairs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.Rectangle;

public class Map_F6 extends Map_Base
{
	
	private static final String MAP_F6_DIALOG1 = "mapF6_Dialog1";
	private static final String MAP_F6_DIALOG2 = "mapF6_Dialog2";
	private static final String MAP_F6_DIALOG3 = "mapF6_Dialog3";
	private static final String MAP_F6_DIALOG4 = "mapF6_Dialog4";
	private static final String MAP_F6_DIALOG5 = "mapF6_Dialog5";
	private static final String MAP_F6_PUZZLE1 = "mapF6_Puzzle1";
	private static final String MAP_F6_CHEST1 = "mapF6_Chest1";
	
	Entity_Stairs stairs;
	Entity_Chest chest;
	
	Rectangle chestRect;
	
	boolean initStairs;
	
	boolean showChest;
	boolean chestIsOpen;
	boolean createChest;

	public Map_F6(State_Gameplay gameplay) 
	{
     super(gameplay);
     mapLoader = new TmxMapLoader();
     
     initStairs = true;
     
     chestRect = new Rectangle(0,0,0,0);
	 
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapF6.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 
	 showChest = gameplay.manager.main.android.getKey(MAP_F6_PUZZLE1);
	 chestIsOpen = gameplay.manager.main.android.getKey(MAP_F6_CHEST1);
	 createChest = true;
	}
	
	public void Update()
	{
	 if(initStairs)
	 {
	  stairs = new Entity_Stairs(gameplay,256,64,64,224,new Map_B1(gameplay));
	  initStairs = false;
	 }
	 
	 if(stairs.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairs.initTransition();
	 }
	 
	 if(Gdx.input.getAccelerometerX()  < -9 && Gdx.input.getAccelerometerX() > -9.9 && !showChest)
     {
      showChest = true;
      gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_F6_DIALOG5));
      gameplay.manager.main.android.save(MAP_F6_PUZZLE1,true);
     }
	 
	 if(showChest)
	 {
	  if(createChest)
	  {
	   chest = new Entity_Chest(gameplay,192,96,chestIsOpen,Tools_Hud_Inventory.HASTE);
	   Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),chest.pos.x,chest.pos.y);
	   gameplay.addEntity(chest);
	   gameplay.addEntity(e);
	   chestRect.set(chest.pos.x -1,chest.pos.y + 32,30,30);
	   createChest = false;
	  }
	  if(!chestIsOpen)
	  {
	   if(chestRect.overlaps(gameplay.player.colBox) && gameplay.player.facing == 3 && gameplay.hud.touchA())
	   {
		chest.openChest();
		chestIsOpen = true;
		gameplay.manager.main.android.save(MAP_F6_CHEST1,true);
	   }
	  }
	 }
	 
	 
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(ev.ev == 1)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.player.facing == 3
			  && gameplay.hud.touchA() && Gdx.input.justTouched())
	  {
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_F6_DIALOG1));
	  }
	 }
	 
	 if(ev.ev == 2)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.player.facing == 3
			  && gameplay.hud.touchA() && Gdx.input.justTouched())
	  {
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_F6_DIALOG2));
	  }
	 }
	 
	 if(ev.ev == 3)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.player.facing == 3
			  && gameplay.hud.touchA() && Gdx.input.justTouched())
	  {
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_F6_DIALOG3));
	  }
	 }
	 
	 if(ev.ev == 4)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.player.facing == 3
			  && gameplay.hud.touchA() && Gdx.input.justTouched())
	  {
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_F6_DIALOG4));
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
