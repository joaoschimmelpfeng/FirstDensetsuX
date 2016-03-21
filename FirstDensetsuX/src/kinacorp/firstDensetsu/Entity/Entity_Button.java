package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.maps.Map_Base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_Button extends Entity_Base
{
	public static final int LEVER = 0;
	
	Texture sheet;
	TextureRegion current;
	TextureRegion button;
	TextureRegion buttonClicked;
	Map_Base map;
	
	boolean isClicked;

	public Entity_Button(State_Gameplay gameplay,Map_Base map, float x, float y,int type) 
	{
	 super(gameplay, x, y);
	 
	 sheet = new Texture("data/TileSheet3.png");
	 button = new TextureRegion(sheet,0,128,32,32);
	 button.flip(false, true);
	 
	 buttonClicked = new TextureRegion(sheet,32,128,32,32);
	 buttonClicked.flip(false, true);
	 
	 current = button;
	 
	 this.map = map;
	}
	
	public void map_B2_Switch()
	{
	 if(!isClicked)
	 {
	  current = buttonClicked;
	  isClicked = true;
	 }
	 
	 for(int i = 0; i < gameplay.entityList.size(); i++)
	 {
	  if(gameplay.entityList.get(i) instanceof Entity_TileHole)
	  {
	   if(i == 32 || i == 31 || i == 30)
	   {
	    Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),gameplay.entityList.get(i).pos.x,
			   gameplay.entityList.get(i).pos.y);
	    gameplay.addEntity(e);
	    gameplay.entityList.get(i).beSolid = true;
	   }
	  }
	 }
	}
	
	public void map_B2_OFF()
	{
	 if(isClicked)
	 {
	  current = button;
	  isClicked = false;
	 }
	 
	 for(int i = 0; i < gameplay.entityList.size(); i++)
	 {
	  if(gameplay.entityList.get(i) instanceof Entity_TileHole)
	  {
	   if(i == 32 || i == 31 || i == 30)
	   {
	    Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),gameplay.entityList.get(i).pos.x,
	    gameplay.entityList.get(i).pos.y);
	    gameplay.addEntity(e);
	    gameplay.entityList.get(i).beSolid = false;
	   }
	  }
	 }	
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(current,pos.x,pos.y);
	}
	
	

}
