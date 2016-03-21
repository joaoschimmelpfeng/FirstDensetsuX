package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Entity.Entity_Button;
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

public class Map_B2 extends Map_Base
{
	
	Entity_Button lever;
	
	Entity_Stairs stairs;
	Entity_Stairs stairs2;
	
	Rectangle leverBot;
	Rectangle leverLeft;
	Rectangle leverRight;
	
	boolean initEntity;
	boolean start;
	float timer = 0;

	public Map_B2(State_Gameplay gameplay) 
	{
	 super(gameplay);
     mapLoader = new TmxMapLoader();
	 
     initEntity = true;
     start = false;
     
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapB2.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	}
	
	public void Update()
	{	 
	 if(initEntity)
	 {
	  lever = new Entity_Button(gameplay,this,64,224,Entity_Button.LEVER);
	  leverBot = new Rectangle(lever.pos.x,lever.pos.y + 32, 32,32);
	  leverLeft = new Rectangle(lever.pos.x - 32,lever.pos.y, 32,32);
	  leverRight = new Rectangle(lever.pos.x + 32,lever.pos.y, 32,32);
	  Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),64,224);
	  gameplay.addEntity(lever);
	  gameplay.addEntity(e);
	  stairs = new Entity_Stairs(gameplay,32,64,256,96,new Map_F7(gameplay));
	  stairs2 = new Entity_Stairs(gameplay,160,64,64,64,new Map_C1(gameplay));
	  initEntity = false;
	 }
	 
	 if(stairs.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairs.initTransition();
	 }
	 
	 if(stairs2.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairs2.initTransition();
	 }
	 
	 if(leverBot.overlaps(gameplay.player.colBox) && gameplay.hud.touchA() && !start)
	 {
	  lever.map_B2_Switch();
	  start = true;
	 }
	 
	 if(leverLeft.overlaps(gameplay.player.colBox) && gameplay.hud.touchA() && !start)
	 {
	  lever.map_B2_Switch();
	  start = true;
	 }
	 
	 if(leverRight.overlaps(gameplay.player.colBox) && gameplay.hud.touchA() && !start)
	 {
	  lever.map_B2_Switch();
	  start = true;
	 }
	 
	 if(start)
	 {
	  timer += Gdx.graphics.getDeltaTime();
	  if(timer >= 2.5f)
	  {
	   start = false;
	   timer = 0;
	   lever.map_B2_OFF();
	  }
	 }
	}
	
	public void callEvent(Entity_Event ev)
	{
	 if(ev.ev == 1)
	 {
	  
	  if(ev.colBox.x == gameplay.player.pos.x && ev.colBox.y == gameplay.player.pos.y && gameplay.hud.touchDown())
	  {
	   gameplay.map_manager.changeMap(0,new Map_B1(gameplay));
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
