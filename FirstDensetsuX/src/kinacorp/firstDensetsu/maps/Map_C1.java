package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Entity.Entity_Stairs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

public class Map_C1 extends Map_Base
{
	
	Entity_Stairs stairs;
	Entity_Stairs stairs2;
	
	boolean initStairs;

	public Map_C1(State_Gameplay gameplay) 
	{
	 super(gameplay);
     mapLoader = new TmxMapLoader();
     
     initStairs = true;
	 
	 Parameters parameters = new Parameters();
	 parameters.yUp = false;
	 map = mapLoader.load("data/mapC1.tmx",parameters);
	 
	 botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
	 bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
	 topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
	 collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
	}
	
	public void Update()
	{
	 if(initStairs)
	 {
	  stairs = new Entity_Stairs(gameplay,32,64,128,64,new Map_B2(gameplay));
	  stairs2 = new Entity_Stairs(gameplay,256,224,224,224,new Map_D1(gameplay));
	  initStairs = false;
	 }
	 
	 if(stairs.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairs.initTransition();
	 }
	 
	 if(stairs2.colBox.overlaps(gameplay.player.colBox))
	 {
	  stairs2.initTransition();
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
