package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;
import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_SpawnCloud;
import kinacorp.firstDensetsu.Entity.Entity_Stairs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

public class Map_D1 extends Map_Base
{
	private static final String MAP_D1_DIALOG1 = "mapD1_Dialog1";
	private static final String MAP_D1_PUZZLE1 = "mapD1_Puzzle1";
	
	Texture texture;
	TextureRegion eye;
	TextureRegion stair;
	
	boolean puzzle1;
	boolean showEye;
	boolean initStairs;
	boolean showStairs;
	boolean initFirstStairs;
	
	Entity_Stairs stairsE;
	Entity_Stairs stairsA;

	public Map_D1(State_Gameplay gameplay) 
	{
     super(gameplay);
     mapLoader = new TmxMapLoader();
     
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapD1.tmx",parameters);
	 
	 texture = new Texture("data/TileSheet3.png");
	 
	 eye = new TextureRegion(texture,192,96,32,32);
	 eye.flip(false,true);
	 showEye = false;
	 
	 stair = new TextureRegion(texture,192,32,32,32);
	 stair.flip(false,true);
	 
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	 
	 initStairs = true;
	 initFirstStairs = true;
	 showStairs = false;
	 puzzle1 = gameplay.manager.main.android.getKey(MAP_D1_PUZZLE1);
	}
	
	public void Update()
	{
	 if(initFirstStairs)
	 {
	  stairsA = new Entity_Stairs(gameplay,256,224,224,224, new Map_C1(gameplay));
	  initFirstStairs = false;
	 }
	 if(puzzle1)
	 {
	  if(initStairs)
	  {
	   stairsE = new Entity_Stairs(gameplay,32,224,64,224, new Map_Boss(gameplay));
	   Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),32,224);
	   gameplay.addEntity(e);
	   showStairs = true;
	   initStairs = false;
	  }
	  
	  if(stairsE.colBox.overlaps(gameplay.player.colBox))
	  {
	   stairsE.initTransition();
	  }
	 }
	 if(!gameplay.hud.dialog.showDialog && showEye)
	 {
	  showEye = false;
	 }
	 
	 if(stairsA.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairsA.initTransition();
	 }
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(puzzle1)
	 {
	  return;
	 }
	 
	 if(ev.ev == 1)
	 {
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && 
			  gameplay.player.itemSelected == Tools_Hud_Inventory.SWORD && gameplay.hud.touchA())
	  {
	   showEye = true;
	   gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(MAP_D1_DIALOG1));
	   puzzle1 = true;
	   gameplay.manager.main.android.save(MAP_D1_PUZZLE1, true);
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
	 if(showStairs)
	 {
      batch.draw(stair,32,224);
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
	 
	 if(showEye)
	 {
      batch.draw(eye,96,64);
	 }
	}

}
