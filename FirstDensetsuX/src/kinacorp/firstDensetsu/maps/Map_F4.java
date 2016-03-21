package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;
import kinacorp.firstDensetsu.Entity.Entity_Chest;
import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_SpawnCloud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.Rectangle;

public class Map_F4 extends Map_Base
{
	
	//256
	private static final String MAP_F4_CHEST1 = "map_f4_chest1";
	
	Entity_Chest chest;

	Rectangle chestRect;

	boolean chestIsOpen;
	boolean createChest;

	public Map_F4(State_Gameplay gameplay) 
	{
	 super(gameplay);
	 tilePushable = 2;
	 mapLoader = new TmxMapLoader();
	 
	 chestRect = new Rectangle(0,0,0,0);
	 
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapF4.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");

	 chestIsOpen = gameplay.manager.main.android.getKey(MAP_F4_CHEST1);
	 createChest = true;
	}
	
	public void Update()
	{
	 if(createChest)
	 {
	  chest = new Entity_Chest(gameplay,256,64,chestIsOpen,Tools_Hud_Inventory.BLUE);
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
	   gameplay.manager.main.android.save(MAP_F4_CHEST1,true);
	  }
	 }
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(ev.ev == 1)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchLeft())
	  {
	   gameplay.map_manager.changeMap(1,new Map_F2(gameplay));
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
