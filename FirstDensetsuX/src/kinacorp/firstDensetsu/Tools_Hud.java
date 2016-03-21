package kinacorp.firstDensetsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tools_Hud 
{
	public Texture items;
	
	public TextureRegion controls;
	public TextureRegion bossControls;
	public TextureRegion[] hearts = new TextureRegion[3];
	public State_Gameplay gameplay;
	
	public Tools_Hud_Dialog dialog;
	public Tools_Hud_Inventory inventory;
	
	public Rectangle upArrow;
	public Rectangle downArrow;
	public Rectangle rightArrow;
	public Rectangle leftArrow;
	public Rectangle buttonA;
	public Rectangle buttonB;
	public Rectangle attackLeft;
	public Rectangle attackMid;
	public Rectangle attackRight;
	
	Vector2 pos;
	
	float realX;
	float realY;
	float attackTiming = 0;
	
	public boolean isInBoss = false;
	public boolean isAttacking = false;
	public boolean blockInv = false;
	
	public Tools_Hud()
	{
     controls = new TextureRegion(new Texture("data/buttons.png"));
	 controls.flip(false,true);
	 
	 bossControls = new TextureRegion(new Texture("data/buttons2.png"));
	 bossControls.flip(false,true);
	 
	 pos = new Vector2(0,0);
	 
	 upArrow = new Rectangle(47,318,38,38);
	 leftArrow = new Rectangle(10,356,38,38);
	 rightArrow = new Rectangle(86,356,38,38);
	 downArrow = new Rectangle(47,393,38,38);
	 buttonA = new Rectangle(244,332,44,44);
	 buttonB = new Rectangle(181,362,44,44);

	 items = new Texture("data/items.png");
		 
	 //hearts
	 hearts[0] = new TextureRegion(items,144,0,15,13);
	 hearts[0].flip(false,true);
	 hearts[1] = new TextureRegion(items,144,0,15,13);
	 hearts[1].flip(false,true);
	 hearts[2] = new TextureRegion(items,144,0,15,13);
	 hearts[2].flip(false,true);
	}
	
	public Tools_Hud(State_Gameplay gameplay)
	{
	 this.gameplay = gameplay;
	 dialog = new Tools_Hud_Dialog(this);
	 inventory = new Tools_Hud_Inventory(this);
	 
	 controls = new TextureRegion(new Texture("data/buttons.png"));
	 controls.flip(false,true);
	 
	 bossControls = new TextureRegion(new Texture("data/buttons2.png"));
	 bossControls.flip(false,true);
	 
	 pos = new Vector2(0,0);
	 
	 upArrow = new Rectangle(47,318,38,38);
	 leftArrow = new Rectangle(10,356,38,38);
	 rightArrow = new Rectangle(86,356,38,38);
	 downArrow = new Rectangle(47,393,38,38);
	 buttonA = new Rectangle(244,332,44,44);
	 buttonB = new Rectangle(181,362,44,44);
	 attackLeft = new Rectangle(47,381,44,44);
	 attackMid = new Rectangle(144,323,44,44);
	 attackRight = new Rectangle(239,381,44,44);

	 items = new Texture("data/items.png");
	 
 	 //hearts
     hearts[0] = new TextureRegion(items,144,0,15,13);
     hearts[0].flip(false,true);
     hearts[1] = new TextureRegion(items,144,0,15,13);
     hearts[1].flip(false,true);
     hearts[2] = new TextureRegion(items,144,0,15,13);
     hearts[2].flip(false,true);
	 
	}
	
	public void Update()
	{
	 realX = gameplay.manager.main.camera.position.x - (gameplay.manager.main.camera.viewportWidth / 2);
	 realY = gameplay.manager.main.camera.position.y - (gameplay.manager.main.camera.viewportHeight / 2);
	 
	 pos.x = realX;
	 pos.y = realY;
	 
	 upArrow.set(realX+47,realY+318,38,38);
	 leftArrow.set(realX+10,realY+356,38,38);
	 rightArrow.set(realX+86,realY+356,38,38);
	 downArrow.set(realX+47,realY+393,38,38);
	 
	 if(isInBoss)
	 {
	  if(Gdx.input.justTouched() && !isAttacking)
	  {
	   if(gameplay.touchRect.overlaps(attackLeft))
	   {
	    gameplay.player.Sword(0);
	    isAttacking = true;
	   }
	   
	   if(gameplay.touchRect.overlaps(attackMid))
	   {
	    gameplay.player.Sword(1);
	    isAttacking = true;
	   }
	   
	   if(gameplay.touchRect.overlaps(attackRight))
	   {
	    gameplay.player.Sword(2);
	    isAttacking = true;
	   }
	  }
	  
	  if(isAttacking)
	  {
	   attackTiming += Gdx.graphics.getDeltaTime();
	   if(attackTiming >= 0.5f)
	   {
		attackTiming = 0;
		isAttacking = false;
		gameplay.player.backToNormal();
	   }
	  }
	  return;
	 }
	 
	 if(touchB() && Gdx.input.justTouched() && !dialog.showDialog && !blockInv)
	 {
	  if(!inventory.openInventory && !inventory.closeInventory)
	  {
	   if(!inventory.showInventory)
	   {
	    inventory.prepareInventory();
	   }
	   else
	   {
	    inventory.closeInventory();
	   }
	  }
	 }
	 
	 if(dialog.showDialog)
	 {
	  dialog.Update();
	 }
	 if(inventory.showInventory)
	 {
	  inventory.Update();
	 }
	}
	
	public boolean touchUp()
	{
	 if(gameplay.touchRect.overlaps(upArrow))
	 {
	  return true;
	 }
	 return false;
	}
	
	public boolean touchLeft()
	{
	 if(gameplay.touchRect.overlaps(leftArrow))
	 {
	  return true;
	 }
	 return false;	
	}
	
	public boolean touchRight()
	{
	 if(gameplay.touchRect.overlaps(rightArrow))
	 {
	  return true;
	 }
	 return false;	
	}
	
	public boolean touchDown()
	{
	 if(gameplay.touchRect.overlaps(downArrow))
	 {
	  return true;
	 }
	 return false;	
	}
	
	public boolean touchA()
	{
	 if(gameplay.touchRect.overlaps(buttonA))
	 {
	  return true;
	 }
	 return false;	
	}
	

	
	public boolean touchB()
	{
	 if(gameplay.touchRect.overlaps(buttonB))
	 {
	  return true;
	 }
	 return false;	
	}
	
	public void Render(SpriteBatch batch)
	{
	 if(dialog.showDialog)
	 {
	  dialog.Render(batch);
	 }
	 if(inventory.showInventory)
	 {
	  inventory.Render(batch);
	 }
	 if(isInBoss)
	 {
      batch.draw(bossControls,pos.x,pos.y,320,480);
	 }
	 else
	 {
	  batch.draw(controls,pos.x,pos.y,320,480);
	 }
	 
	 if(gameplay.player.health >= 3)
	 {
	  batch.draw(hearts[0],2,5);
	  batch.draw(hearts[1],17,5);
	  batch.draw(hearts[2],33,5);
	 }
	 else if(gameplay.player.health == 2)
	 {
	  batch.draw(hearts[0],0,5);
	  batch.draw(hearts[1],17,5);
	 }
	 else if(gameplay.player.health == 1)
	 {
	  batch.draw(hearts[0],2,5);
	 }
	}
	
	public void RenderOnlyHud(SpriteBatch batch)
	{
	 if(isInBoss)
	 {
	  batch.draw(bossControls,pos.x,pos.y,320,480);
	 }
	 else
	 {
	  batch.draw(controls,pos.x,pos.y,320,480);
	 }
	}
}
