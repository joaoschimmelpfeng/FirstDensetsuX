package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;
import kinacorp.firstDensetsu.Entity.Entity_BuddyBat;
import kinacorp.firstDensetsu.Entity.Entity_Chest;
import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_Pushable;
import kinacorp.firstDensetsu.Entity.Entity_SpawnCloud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.Rectangle;

public class Map_A5 extends Map_Base
{
	private static final String MAP_A5_DIALOG1 = "mapA5_Dialog1";
	private static final String MAP_A5_PUZZLE1 = "mapA5_Puzzle1";
	private static final String MAP_A5_CHEST1 = "mapA5_Chest1";
	
	boolean alreadyTalk;
	boolean summonBat;
	boolean showChest;
	boolean chestIsOpen;
	boolean createChest;
	
	Entity_BuddyBat bat;
	Entity_Chest chest;
	
	Rectangle chestRect;

	public Map_A5(State_Gameplay gameplay) 
	{
	 super(gameplay);
	 mapLoader = new TmxMapLoader();
	 summonBat = true;
	 
	 chestRect = new Rectangle(0,0,0,0);
		
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapA5.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 
	 alreadyTalk = gameplay.manager.main.android.getKey(MAP_A5_DIALOG1);
	 showChest = gameplay.manager.main.android.getKey(MAP_A5_PUZZLE1);
	 chestIsOpen = gameplay.manager.main.android.getKey(MAP_A5_CHEST1);
	 createChest = true;
	}
	
	public void Update()
	{
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
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_A5_DIALOG1));
	   bat.batReturn = true;
	   alreadyTalk = true;
	   gameplay.manager.main.android.save(MAP_A5_DIALOG1, true);
	  }
	 }
	 if(showChest)
	 {
	  if(createChest)
	  {
	   Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),256,96);
	   chest = new Entity_Chest(gameplay,256,96,chestIsOpen,Tools_Hud_Inventory.AXE);
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
		gameplay.manager.main.android.save(MAP_A5_CHEST1,true);
	   }
	  }
	 }
	 else
	 {
	  for(int i = 0; i < gameplay.entityList.size();i++)
	  {
	   if(gameplay.entityList.get(i) instanceof Entity_Pushable)
	   {
	    if(gameplay.entityList.get(i).pos.x == 224 && gameplay.entityList.get(i).pos.y == 224)
	    {
	 	 showChest = true; 
	 	 gameplay.manager.main.android.save(MAP_A5_PUZZLE1,true);
	    }
	   }
	  }
	 }
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(ev.ev == 1)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchLeft())
	  {
	   gameplay.map_manager.changeMap(1,new Map_A2(gameplay));
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
