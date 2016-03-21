package kinacorp.firstDensetsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Tools_Hud_Inventory 
{
	public static final int NOTHING = -1;
	public static final int AXE = 0;
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int HASTE = 3;
	public static final int SWORD = 4;
	
	public static final String HAS_AXE = "has_shield";
	public static final String HAS_RED = "has_red";
	public static final String HAS_BLUE = "has_blue";
	public static final String HAS_HASTE = "has_haste";
	public static final String HAS_SWORD = "has_sword";
	
	Rectangle slot1;
	Rectangle slot2;
	Rectangle slot3;
	Rectangle slot4;
	Rectangle slot5;
	
	Tools_Hud hud;
	
	Texture sheet;
	TextureRegion items;
	TextureRegion itemSelector;
	TextureRegion equiped;
	TextureRegion selectedItem;
	TextureRegion axe;
	TextureRegion red;
	TextureRegion blue;
	TextureRegion haste;
	TextureRegion sword;
	
	boolean showInventory;
	boolean openInventory;
	boolean callSelector;
	boolean closeInventory;
	
	public boolean isEquiped;
	public boolean hasAxe;
	public boolean hasRed;
	public boolean hasBlue;
	public boolean hasHaste;
	public boolean hasSword;
	
	int selected;
	int selectCount = 0;
	
	float selectorX;
	float selectorY;
	float itemsX;
	float equipY;
	
	public Tools_Hud_Inventory(Tools_Hud hud)
	{
	 this.hud = hud;
	 
	 selected = 0;
	 itemsX = -100;
	 
	 showInventory = false;
	 
	 sheet = new Texture("data/items.png");
	 items = new TextureRegion(sheet,0,0,52,288);
	 items.flip(false, true);
	 itemSelector = new TextureRegion(sheet,52,0,40,40);
	 itemSelector.flip(false, true);
	 equiped = new TextureRegion(sheet,92,0,52,67);
	 equiped.flip(false, true);
	 
	 slot1 = new Rectangle(10,21,40,40);
	 slot2 = new Rectangle(10,62,40,40);
	 slot3 = new Rectangle(10,102,40,40);
	 slot4 = new Rectangle(10,142,40,40);
	 slot5 = new Rectangle(10,182,40,40);
	 
	 axe = new TextureRegion(sheet,52,40,32,32);
	 axe.flip(false,true);
	 
	 red = new TextureRegion(sheet,52,72,32,32);
	 red.flip(false,true);

	 blue = new TextureRegion(sheet,52,104,32,32);
	 blue.flip(false, true);
	 
	 haste = new TextureRegion(sheet,52,136,32,32);
	 haste.flip(false,true);
	 
	 sword = new TextureRegion(sheet,52,168,32,32);
	 sword.flip(false,true);
	 
	 hasAxe = hud.gameplay.manager.main.android.getKey(HAS_AXE);
	 hasBlue = hud.gameplay.manager.main.android.getKey(HAS_BLUE);
	 hasRed  = hud.gameplay.manager.main.android.getKey(HAS_RED);
	 hasHaste = hud.gameplay.manager.main.android.getKey(HAS_HASTE);
	 hasSword = hud.gameplay.manager.main.android.getKey(HAS_SWORD);
	 
	 selectorX = 10;
	 selectorY = 21;
	 equipY = -68;
	}
	
	public void prepareInventory()
	{
	 if(hud.gameplay.player.megaFat)
	 {
	  return;
	 }
	 itemsX = -56;
	 showInventory = true;
	 openInventory = true;
	}
	
	public void closeInventory()
	{
	 closeInventory = true;
	 callSelector = false;
	}
	
	public void Update()
	{
	 if(openInventory)
	 {
	  if(equipY < 0)
	  {
	   equipY += 100 * Gdx.graphics.getDeltaTime();
	  }
	  else
	  {
	   equipY = 0;
       openInventory = false;
       callSelector = true;
	  }
      if(itemsX  < 4)
      {
       itemsX += 100 * Gdx.graphics.getDeltaTime();
      }
      else
      {
       itemsX = 4;
      }
	 }
	 
	 if(showInventory)
	 {
	  if(hud.gameplay.touchRect.overlaps(slot1))
	  {
	   selectorY = 21;
	   selectCount = 0;
	  }
	  else if(hud.gameplay.touchRect.overlaps(slot2))
	  {
	   selectorY = 61;
	   selectCount = 1;
	  }
	  else if(hud.gameplay.touchRect.overlaps(slot3))
	  {
	   selectorY = 101;
	   selectCount = 2;
	  }
	  else if(hud.gameplay.touchRect.overlaps(slot4))
	  {
	   selectorY = 141;
	   selectCount = 3;
	  }
	  else if(hud.gameplay.touchRect.overlaps(slot5))
	  {
	   selectorY = 182;
	   selectCount = 4;
	  }
	 }
	 
	 if(closeInventory)
	 {
	  if(equipY > -68)
	  {
	   equipY -= 100 * Gdx.graphics.getDeltaTime();
	  }
	  else
	  {
	   equipY = -68;
	   closeInventory = false;
	   showInventory = false;
	  }
	  if(itemsX > -56)
	  {
	   itemsX -= 100 * Gdx.graphics.getDeltaTime();
	  }
	  else
	  {
	   itemsX = -56;
	  }
	 }
	 
	 if(hud.touchDown() && Gdx.input.justTouched())
	 {
	  if(selectorY >= 161)
	  {
	   selectorY = 21;
	   selectCount = 0;
	  }
	  else
	  {
	   selectorY += 40;
	   selectCount ++;
	  }
	 }
	 
	 if(hud.touchUp() && Gdx.input.justTouched())
	 {
	  if(selectorY <= 22)
	  {
	   selectorY = 182;
	   selectCount = 4;
	  }
	  else
	  {
	   selectorY -= 40;
	   selectCount --;
	  }
	 }
	 
	 if(hud.touchA() && Gdx.input.justTouched())
	 {
	  selectItem();
	 }
	}
	
	public void selectItem()
	{
	 if(selectCount == 0 && hasAxe)
	 {
      selectedItem = axe;
      isEquiped = true;
      hud.gameplay.player.itemSelected = AXE;
	 }
	 else if (selectCount == 1 && hasRed)
	 {
	  selectedItem = red;
	  isEquiped = true;
	  hud.gameplay.player.itemSelected = RED;
	 }
	 else if(selectCount == 2 && hasBlue)
	 {
	  selectedItem = blue;
	  isEquiped = true;
	  hud.gameplay.player.itemSelected = BLUE;	 
	 }
	 else if(selectCount == 3 && hasHaste)
	 {
	  selectedItem = haste;
	  isEquiped = true;
	  hud.gameplay.player.itemSelected = HASTE;
	 }
	 else if(selectCount == 4 && hasSword)
	 {
	  selectedItem = sword;
	  isEquiped = true;
	  hud.gameplay.player.itemSelected = SWORD;	 
	 }
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(items,itemsX,0);
	 batch.draw(equiped,268,equipY);
	 if(callSelector)
	 {
	  batch.draw(itemSelector,selectorX,selectorY);
	 }
	 
	 if(isEquiped)
	 {
	  batch.draw(selectedItem,278,equipY + 22);
	 }
	 
	 if(hasAxe)
	 {
	  batch.draw(axe,itemsX + 10,25);
	 }
	 
	 if(hasRed)
	 {
	  batch.draw(red,itemsX + 10,70);
	 }
	 
	 if(hasBlue)
	 {
	  batch.draw(blue,itemsX + 10,109);
	 }
	 
	 if(hasHaste)
	 {
	  batch.draw(haste,itemsX + 10,148);
	 }
	 
	 if(hasSword)
	 {
	  batch.draw(sword,itemsX + 10, 187);
	 }
	}
}
