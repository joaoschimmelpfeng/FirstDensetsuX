package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_Cutable extends Entity_Base
{
	
	public static final int GRASS = 0;
	public static final int BOX = 1;
	
	int floortype;
	
	Texture sheet;
	TextureRegion cutable;

	public Entity_Cutable(State_Gameplay gameplay, float x, float y,int floortype) 
	{
	 super(gameplay, x, y);
	 this.floortype = floortype;
	 
	 if(floortype == GRASS)
	 {
	  sheet = new Texture("data/TileSheet1.png");
	  cutable = new TextureRegion(sheet,129,0,32,32);
	  cutable.flip(false,true);
	 }
	 
	 if(floortype == BOX)
	 {
	  sheet = new Texture("data/TileSheet2.png");
	  cutable = new TextureRegion(sheet,96,0,32,32);
	  cutable.flip(false,true);
	 }
	 
	}
	
	public void Update()
	{
	 if(gameplay.player.itemSelected == Tools_Hud_Inventory.AXE)
	 {
	  if(gameplay.player.usingItem && colBox.overlaps(gameplay.player.itemBox))
	  {
	   Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),pos.x,pos.y);
	   gameplay.addEntity(e);
	   gameplay.deleteEntity(this);
	  }
	 }
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(cutable,pos.x,pos.y);
	}

}
