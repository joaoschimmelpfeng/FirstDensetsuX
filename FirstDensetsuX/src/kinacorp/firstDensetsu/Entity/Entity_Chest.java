package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_Chest extends Entity_Base
{
	private static final String GOTAXE = "gotAxe";
	private static final String GOTRED = "gotRed";
	private static final String GOTBLUE = "gotBlue";
	private static final String GOTHASTE = "gotHaste";
	
	boolean isOpen;
	boolean showItem;
	
	int contain;
	
	float itemX;
	float itemY;
	
	Texture sheet;
	TextureRegion chest;
	TextureRegion item;

	public Entity_Chest(State_Gameplay gameplay, float x, float y,boolean isOpen,int contain)
	{
	 super(gameplay, x, y);
	 this.isOpen = isOpen;
	 this.contain = contain;
	 showItem = false;
	 itemX = pos.x;
	 itemY = pos.y - 10;
	 
	 if(isOpen)
	 {
	  chest = new TextureRegion(new Texture("data/TileSheet1.png"),224,160,32,32);
	  chest.flip(false,true);
	 }
	 else
	 {
	  chest = new TextureRegion(new Texture("data/TileSheet1.png"),224,128,32,32);
	  chest.flip(false,true);
	 }
	 
	}
	
	public void Update()
	{
	 if(showItem)
	 {
	  if(itemY <= pos.y - 40)
	  {
	   switch(contain)
	   {
	    case Tools_Hud_Inventory.AXE:
		 gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(GOTAXE));
		break;
		
	    case Tools_Hud_Inventory.RED:
			 gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(GOTRED));
		break;
			
	    case Tools_Hud_Inventory.BLUE:
			 gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(GOTBLUE));
		break;
		
	    case Tools_Hud_Inventory.HASTE:
	    	gameplay.hud.dialog.CallDialog(gameplay.manager.main.localization.getString(GOTHASTE));
	    break;
	   }
	   showItem = false;
	  }
	  else
	  {
	   itemY -= 50 * Gdx.graphics.getDeltaTime();
	  }
	 }
	}
	
	public void openChest()
	{
	 chest = new TextureRegion(new Texture("data/TileSheet1.png"),224,160,32,32);
	 chest.flip(false,true);
	 giveItem();
	 showItem = true;
	}
	
	public void giveItem()
	{
	 switch(contain)
	 {
	  case Tools_Hud_Inventory.AXE:
	   gameplay.hud.inventory.hasAxe = true;
	   gameplay.manager.main.android.save(Tools_Hud_Inventory.HAS_AXE, true);
	   item = new TextureRegion(new Texture("data/items.png"),52,40,32,32);
	   item.flip(false, true);
	  break;
	  
	  case Tools_Hud_Inventory.RED:
	   gameplay.hud.inventory.hasRed = true;
	   gameplay.manager.main.android.save(Tools_Hud_Inventory.HAS_RED, true);
	   item = new TextureRegion(new Texture("data/items.png"),52,72,32,32);
	   item.flip(false, true);
	  break;
	  
	  case Tools_Hud_Inventory.BLUE:
	   gameplay.hud.inventory.hasBlue = true;
	   gameplay.manager.main.android.save(Tools_Hud_Inventory.HAS_BLUE, true);
	   item = new TextureRegion(new Texture("data/items.png"),52,104,32,32);
	   item.flip(false, true);
	  break;
	  
	  case Tools_Hud_Inventory.HASTE:
		   gameplay.hud.inventory.hasHaste = true;
		   gameplay.manager.main.android.save(Tools_Hud_Inventory.HAS_HASTE, true);
		   item = new TextureRegion(new Texture("data/items.png"),52,136,32,32);
		   item.flip(false, true);
	  break;
	  
	  case Tools_Hud_Inventory.SWORD:
		  gameplay.hud.inventory.hasSword = true;
		  gameplay.manager.main.android.save(Tools_Hud_Inventory.HAS_SWORD, true);
		  item = new TextureRegion(new Texture("data/items.png"),52,168,32,32);
		  item.flip(false, true); 
	  break;
	 }
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(chest,pos.x,pos.y);
	 if(showItem)
	 {
	  batch.draw(item,itemX,itemY);
	 }
	}

}
