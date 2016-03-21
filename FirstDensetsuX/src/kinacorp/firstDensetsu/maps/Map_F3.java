package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;
import kinacorp.firstDensetsu.Entity.Entity_Chest;
import kinacorp.firstDensetsu.Entity.Entity_Cutable;
import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_Pushable;
import kinacorp.firstDensetsu.Entity.Entity_SpawnCloud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.Rectangle;

public class Map_F3 extends Map_Base
{
	
	private static final String MAP_F3_PUZZLE1 = "map_f3_puzzle1";
	private static final String MAP_F3_CHEST1 = "map_f3_chest1";
	

	Entity_Chest chest;
	
	Rectangle spot1;
	Rectangle spot2;
	Rectangle spot3;
	Rectangle chestRect;
	
	boolean isSpot1;
	boolean isSpot2;
	boolean isSpot3;
	
	boolean showChest;
	boolean chestIsOpen;
	boolean createChest;
	boolean didntSave;

	public Map_F3(State_Gameplay gameplay) 
	{
	 super(gameplay);
	 tilePushable = 2;
	 cutable = Entity_Cutable.BOX;
     mapLoader = new TmxMapLoader();
     
     isSpot1 = false;
     isSpot2 = false;
     isSpot3 = false;
     didntSave = true;
     
     chestRect = new Rectangle(0,0,0,0);
	 
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapF3.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 
	 spot1 = new Rectangle(32,64,32,32);
	 spot2 = new Rectangle(32,224,32,32);
	 spot3 = new Rectangle(256,224,32,32);
	 
	 showChest = gameplay.manager.main.android.getKey(MAP_F3_PUZZLE1);
	 chestIsOpen = gameplay.manager.main.android.getKey(MAP_F3_CHEST1);
	 createChest = true;
	}
	
	public void Update()
	{
	 isSpot1 = false;
	 isSpot2 = false;
	 isSpot3 = false;
	 for(int i = 0; i < gameplay.entityList.size();i++)
	 {
	  if(gameplay.entityList.get(i) instanceof Entity_Pushable)
	  {
	   if(gameplay.entityList.get(i).colBox.overlaps(spot1))
	   {
	    isSpot1 = true;
	   }
	  
	   if(gameplay.entityList.get(i).colBox.overlaps(spot2))
	   {
	    isSpot2 = true;
	   }
	  
	   if(gameplay.entityList.get(i).colBox.overlaps(spot3))
	   {
	    isSpot3 = true;
	   }
	  }
	 }
	 
	 if(isSpot1 && isSpot2 && isSpot3 && didntSave)
	 {
	  showChest = true;
	  gameplay.manager.main.android.save(MAP_F3_PUZZLE1, true);
	  didntSave = false;
	 }
	 
	 if(showChest)
	 {
	  if(createChest)
	  {
	   chest = new Entity_Chest(gameplay,160,64,chestIsOpen,Tools_Hud_Inventory.RED);
	   Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),chest.pos.x,chest.pos.y);
	   System.out.println("aqui");
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
		gameplay.manager.main.android.save(MAP_F3_CHEST1,true);
	   }
	  }
	 }
	 
	 
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(ev.ev == 1)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchRight())
	  {
	   gameplay.map_manager.changeMap(2,new Map_F2(gameplay));
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
