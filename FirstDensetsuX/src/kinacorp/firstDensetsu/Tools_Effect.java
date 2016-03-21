package kinacorp.firstDensetsu;

import kinacorp.firstDensetsu.Entity.Entity_Event;
import kinacorp.firstDensetsu.Entity.Entity_SpawnCloud;
import kinacorp.firstDensetsu.Entity.Entity_TileAnimation;
import kinacorp.firstDensetsu.Entity.Entity_TileHole;

import com.badlogic.gdx.graphics.Texture;

public class Tools_Effect 
{
	State_Gameplay gameplay;
	
	Texture sheet;
	
	public Tools_Effect(State_Gameplay gameplay)
	{
	 this.gameplay = gameplay;
	 sheet = new Texture("data/effectSheet.png");
	}
	
	public void callSpawn()
	{
	 for(int i = 0; i < gameplay.entityList.size(); i++)
	 {
	  if(gameplay.entityList.get(i) instanceof Entity_Event)
	  {
	   continue;
	  }
	  if(gameplay.entityList.get(i) instanceof Entity_TileAnimation)
	  {
	   continue;
	  }
	  if(gameplay.entityList.get(i) instanceof Entity_TileHole)
	  {
	   continue;
	  }
	  
	  Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,sheet,
			  gameplay.entityList.get(i).pos.x,gameplay.entityList.get(i).pos.y);
	  gameplay.addEntity(e);
	 }
	}
}
