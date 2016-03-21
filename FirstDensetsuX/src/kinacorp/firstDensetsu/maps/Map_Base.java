package kinacorp.firstDensetsu.maps;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Entity.Entity_Cutable;
import kinacorp.firstDensetsu.Entity.Entity_Enemy_EvilBrick;
import kinacorp.firstDensetsu.Entity.Entity_Enemy_Ghostling;
import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_Pushable;
import kinacorp.firstDensetsu.Entity.Entity_TileAnimation;
import kinacorp.firstDensetsu.Entity.Entity_TileHole;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class Map_Base 
{
	public State_Gameplay gameplay;
	public TiledMapTileLayer collisionLayer;
	public TiledMapTileLayer entityLayer;
	public TiledMapTileLayer botLayer;
	public TiledMapTileLayer bot2Layer;
	public TiledMapTileLayer topLayer;
	public TiledMap map;
	public TmxMapLoader mapLoader;
	
	public Vector2 pos;
	
	public boolean entityLoaded;
	
	public int cutable;

    public int tileAnimation;
    public int tilePushable;
	
	public int getWidthinTiles()
	{
     return collisionLayer.getWidth();
	}
	
	public int getHeightinTiles()
	{
     return collisionLayer.getHeight();
	}
	
	public Map_Base(State_Gameplay gameplay)
	{
	 this.gameplay = gameplay;
	 pos = new Vector2(0,0);
	 entityLoaded = false;
	 cutable = 0;
	 tileAnimation = 1;
	 tilePushable = 1;
	}
	
	public void loadEntities()
	{
	 entityLayer = (TiledMapTileLayer) map.getLayers().get("Entity");
	 for(int i = 0; i < 10; i++)
	 {
	  for(int j = 0; j < 9; j++)
	  {
	   if(entityLayer.getCell(i,j) != null)
	   {
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("E1"))
		{
		 Entity_Event e = new Entity_Event(gameplay,i*32,j*32,1);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("E2"))
		{
		 Entity_Event e = new Entity_Event(gameplay,i*32,j*32,2);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("E3"))
		{
		 Entity_Event e = new Entity_Event(gameplay,i*32,j*32,3);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("E4"))
		{
		 Entity_Event e = new Entity_Event(gameplay,i*32,j*32,4);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("enemy1"))
		{
		 Entity_Enemy_Ghostling e = new Entity_Enemy_Ghostling(gameplay,i * 32,j * 32,0);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("enemy2"))
		{
		 Entity_Enemy_EvilBrick e = new Entity_Enemy_EvilBrick(gameplay,i * 32,j * 32);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("river"))
		{
		 Entity_TileAnimation e = new Entity_TileAnimation(gameplay,i * 32,j * 32,tileAnimation);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("pushable"))
		{
		 Entity_Pushable e = new Entity_Pushable(gameplay,i * 32,j * 32,tilePushable);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("cutable"))
		{
		 Entity_Cutable e = new Entity_Cutable(gameplay,i * 32,j * 32,cutable);
		 gameplay.addEntity(e);
		 continue;
		}
		
		if(entityLayer.getCell(i, j).getTile().getProperties().containsKey("hole"))
		{
		 Entity_TileHole e = new Entity_TileHole(gameplay,i * 32,j * 32,false,null,0,0);
		 gameplay.addEntity(e);
		 continue;
		}
	   }
	  }
	 }
	 entityLoaded = true;
	}
	
	public void Update()
	{
		
	}
	
	public void callEvent(Entity_Event ev)
	{

	}
	
	public void RenderUnder(SpriteBatch batch)
	{
		
	}
	
	public void RenderTop(SpriteBatch batch)
	{
		
	}
}
