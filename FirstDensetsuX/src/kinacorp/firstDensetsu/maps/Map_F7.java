package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;
import kinacorp.firstDensetsu.Entity.Entity_Chest;
import kinacorp.firstDensetsu.Entity.Entity_SpawnCloud;
import kinacorp.firstDensetsu.Entity.Entity_Stairs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.Rectangle;

public class Map_F7 extends Map_Base
{
	private static final String MAP_F7_CHEST1 = "mapF7_Chest1";
	private static final String MAP_F7_DIALOG1 = "gotSword";
	
	Entity_Stairs stairs;
	Entity_Chest chest;
	
	Rectangle chestRect;
	
	Texture sheet;
	TextureRegion[] fans = new TextureRegion[5];
	TextureRegion[] rotating = new TextureRegion[5];
	
    boolean initStairs;
	boolean chestIsOpen;
	boolean createChest;
	boolean fanOn;
	boolean rotate;
	
	float timing = 0;
	float animation = 0;

	public Map_F7(State_Gameplay gameplay) 
	{
	 super(gameplay);
     mapLoader = new TmxMapLoader();
     
     initStairs = true;
     
     chestRect = new Rectangle(0,0,0,0);
     
     fanOn = false;
	 
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapF7.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 
	 chestIsOpen = gameplay.manager.main.android.getKey(MAP_F7_CHEST1);
	 createChest = true;
	 
	 sheet = new Texture("data/TileSheet2.png");
	 for(int i = 0; i < 5; i++)
	 {
	  fans[i] = new TextureRegion(sheet,96,96,32,32);
	  fans[i].flip(false,true);
	  rotating[i] = new TextureRegion(sheet,128,96,32,32);
	  rotating[i].flip(false,true);
	 }
	}
	
	public void Update()
	{
	 if(initStairs)
	 {
	  stairs = new Entity_Stairs(gameplay,256,64,64,64,new Map_B2(gameplay));
	  initStairs = false;
	 }
	 
	 if(createChest)
	 {
	  Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),32,96);
	  chest = new Entity_Chest(gameplay,32,96,chestIsOpen,Tools_Hud_Inventory.SWORD);
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
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_F7_DIALOG1));
	   chestIsOpen = true;
	   gameplay.manager.main.android.save(MAP_F7_CHEST1,true);
	  }
	 }
	 
	 if(stairs.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairs.initTransition();
	 }
	 
	 timing += Gdx.graphics.getDeltaTime();
	 if(timing >= 2)
	 {
	  fanOn = true;
	  if(timing >= 3)
	  {
	   fanOn = false;
	   timing = 0;
	  }
	 }
	 
	 if(fanOn)
	 {
	  animation += Gdx.graphics.getDeltaTime();
	  if(animation >= 0.2f)
	  {
	   if(rotate)
	   {
		rotate = false;
	   }
	   else
	   {
		rotate = true;
	   }
	   animation = 0;
	  }
	  
	  if(gameplay.player.pos.x > 64 && gameplay.player.pos.x < 224 && !gameplay.player.isFat)
	  {
	   gameplay.player.destinyY = gameplay.player.pos.y + 32;
	   if(gameplay.player.megaFat)
	   {
		gameplay.player.steps += 1;
	   }
	   gameplay.player.isMoving = true;
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
	 
	 if(rotate)
	 {
	  batch.draw(rotating[0],64,32);
	  batch.draw(rotating[1],96,32);
	  batch.draw(rotating[2],128,32);
	  batch.draw(rotating[3],160,32);
	  batch.draw(rotating[4],192,32);
	 }
	 else
	 {
	  batch.draw(fans[0],64,32);
	  batch.draw(fans[1],96,32);
	  batch.draw(fans[2],128,32);
	  batch.draw(fans[3],160,32);
	  batch.draw(fans[4],192,32); 
	 }
	}

}
