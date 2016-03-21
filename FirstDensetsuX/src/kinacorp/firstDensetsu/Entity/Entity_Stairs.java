package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.maps.Map_Base;

public class Entity_Stairs extends Entity_Base
{
	Map_Base toWhere;
	boolean teleport;
	
	float px;
	float py;

	public Entity_Stairs(State_Gameplay gameplay, float x, float y,float px,float py,Map_Base toWhere) 
	{
	 super(gameplay, x, y);
	 this.toWhere = toWhere;
	 this.px = px;
	 this.py = py;
	 
	 teleport = false;
	}
	
	public void initTransition()
	{
	 if(gameplay.player.isMoving || teleport)
	 {
	  return;
	 }
	 gameplay.map_manager.changeMapFade(toWhere);
	 teleport = true;
	 gameplay.addEntity(this);
	}
	
	public void Update()
	{
	 if(teleport)
	 {
	  if(gameplay.isDark)
	  {
	   gameplay.map_manager.finishedTransition();
	   gameplay.player.pos.x = px;
	   gameplay.player.pos.y = py;
	   gameplay.player.iniX = px;
	   gameplay.player.iniY = py;
	   gameplay.player.destinyX = px;
	   gameplay.player.destinyY = py;
	   gameplay.isDark = false;
	  }
	 }
	 
	 if(colBox.overlaps(gameplay.player.colBox) && !teleport)
	 {
	  
	 }
	}
	
}
