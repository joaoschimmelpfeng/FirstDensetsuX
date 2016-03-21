package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Tools_Hud_Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Entity_Player extends Entity_Base
{
	Animation[] stand = new Animation[4];
	Animation fatFloat;
	Animation falling;
	
	public TextureRegion sheet;
	public TextureRegion current;
	public TextureRegion fat;
	public TextureRegion swordingUp;
	public TextureRegion swordingLeft;
	public TextureRegion swordingRight;
	public TextureRegion dead;
	public TextureRegion[] usingAxe = new TextureRegion[4];
	public TextureRegion[] frames = new TextureRegion[2];
	public TextureRegion[] frames4 = new TextureRegion[4];
	public TextureRegion[] fatexture = new TextureRegion[2];
	
	
	Entity_Base collision;
	
	Rectangle itemBox;
	Rectangle finalBossRect;
	
	//0-front 1-left 2-right 3-back.
	public int facing = 0;
	public int steps = 0;
	public int itemSelected;
	
	public int health;
	
	public float animTime = 0;
	public float itemTime = 0;
	public float fallTime = 0;
	public float flashTime = 0;
	public float invTime = 0;
	public float destinyX;
	public float destinyY;
	public float oldX;
	public float oldY;
	public float iniX;
	public float iniY;
	public float animX = 0;
	public float animY = 0;
	
	public float speed = 100;
	
	public boolean isMoving = false;
	public boolean newInput = false;
	public boolean usingItem = false;
	public boolean isFalling = false;
	public boolean finishedFall = false;
	public boolean invencible = false;
	public boolean flash = false;
	public boolean isFat = false;
	public boolean megaFat = false;
	public boolean inAnim = false;
	
	public Entity_Player(State_Gameplay gameplay,float x, float y)
	{
	 super(gameplay,x,y);
	 itemSelected = -1;
	 itemBox = new Rectangle(0,0,0,0);
	 
	 finalBossRect = new Rectangle(0,0,0,0);
	 
	 health = 3;
	 
	 sheet = new TextureRegion(new Texture("data/Char1.png"));
	 
	 //standard animation.
	 for(int i = 0; i < 4; i++)
	 {
		 frames = new TextureRegion[2];
	  for(int j = 0; j < 2; j++)
	  {
	   frames[j] = new TextureRegion(sheet,32 * j,32 * i,32,32);
	   frames[j].flip(false,true);
	  }
	  stand[i] = new Animation(0.5f,frames);
	  stand[i].setPlayMode(Animation.LOOP);
	 }
	 current = frames[0];
	 
	 //using axe.
	 usingAxe[0] = new TextureRegion(sheet,64,0,32,64);
	 usingAxe[0].flip(false,true);
	 usingAxe[1] = new TextureRegion(sheet,64,64,64,32);
	 usingAxe[1].flip(false,true);
	 usingAxe[2] = new TextureRegion(sheet,64,96,64,32);
	 usingAxe[2].flip(false,true);
	 usingAxe[3] = new TextureRegion(sheet,96,0,32,64);
	 usingAxe[3].flip(false,true);
	 
	 //Swording
	 swordingUp = new TextureRegion(sheet,64,128,32,64);
	 swordingRight = new TextureRegion(sheet,0,128,64,64);
	 swordingLeft = new TextureRegion(sheet,96,128,64,64);
	 
	 swordingUp.flip(false, true);
	 swordingLeft.flip(false, true);
	 swordingRight.flip(false, true);
	 
	 //dead
	 dead = new TextureRegion(sheet,161,128,32,32);
	 dead.flip(false, true);
	 
	 //Falling animation:
	 for(int i = 0; i < 4; i++)
	 {
	  frames4[i] = new TextureRegion(sheet,128 + (32 * i),0,32,32);
	  frames4[i].flip(false, true);
	 }
	 falling = new Animation(0.2f,frames4);
	 falling.setPlayMode(Animation.NORMAL);
	 
	 //fat
	 fat = new TextureRegion(sheet,128,32,32,32);
	 fat.flip(false, true);
	 
	 fatexture[0] = new TextureRegion(sheet,128,64,32,32);
	 fatexture[0].flip(false, true);
	 fatexture[1] = new TextureRegion(sheet,128,96,32,32);
	 fatexture[1].flip(false, true);
	 fatFloat = new Animation(0.2f,fatexture);
	 fatFloat.setPlayMode(Animation.LOOP);
	 
	 level = 1;
	}
	
	public void Update()
	{
	 colBox.set(pos.x,pos.y,32,32);
	 animTime += Gdx.graphics.getDeltaTime();
	 
	 if(invencible)
	 {
	  invTime += Gdx.graphics.getDeltaTime();
	  if(invTime >= 1)
	  {
	   flash = false;
	   invencible = false;
	   invTime = 0;
	  }
	 }
	 
	 if(health <= 0)
	 {
	  return;
	 }
	 
	 if(isFalling && !finishedFall)
	 {
	  fallTime += Gdx.graphics.getDeltaTime();
	  if(falling.isAnimationFinished(fallTime))
	  {
	   fallTime = 0;
	   isFalling = false;
	   pos.x = iniX;
	   pos.y = iniY;
	   isMoving = false;
	   finishedFall = true;
	  }
	  else
	  {
	   current = falling.getKeyFrame(fallTime);
	  }
	  return;
	 }
	 
	 if(usingItem)
	 {
	  itemTime += Gdx.graphics.getDeltaTime();
	  if(isFat && itemSelected != Tools_Hud_Inventory.RED)
	  {
	   Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),pos.x,pos.y);
	   gameplay.addEntity(e);
	   isFat = false;
	  }
	  switch(itemSelected)
	  {
	   case Tools_Hud_Inventory.AXE:
		switch(facing)
		{
		 case 0:
	      current = usingAxe[0];
	     break;
	     
		 case 1:
		  
		  pos.x = oldX - 32;
		  current = usingAxe[1];
		 break;
		 
		 case 2:
		  current = usingAxe[2];
		 break;
		 
		 case 3:
		  pos.y = oldY - 32;
		  current = usingAxe[3];
		 break;
 		}
		
		if(itemTime > 1)
		{
		  pos.x = oldX;
		  pos.y = oldY;
		  current = stand[facing].getKeyFrame(animTime);
		  itemTime = 0;
		  usingItem = false;
		}
		return;
	   
	   case Tools_Hud_Inventory.RED:
		current = fat;
		if(Gdx.input.justTouched() && gameplay.hud.touchA())
		{
	     usingItem = false;
	     isFat = false;
	     Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),pos.x,pos.y);
	     gameplay.addEntity(e);
		}
	   return;
	   
	   case Tools_Hud_Inventory.BLUE:
	   
	   break;
	  }
	 }
	 
	 if(gameplay.hud.isInBoss)
	 {
	  return;
	 }
	 
	 if(itemSelected == Tools_Hud_Inventory.HASTE)
	 {
	  speed = 200;
	 }
	 else
	 {
	  speed = 100;
	 }
	 
	 if(gameplay.hud.touchA() && Gdx.input.justTouched())
	 {
	  oldX = pos.x;
	  oldY = pos.y;
	  useItem();
	 }
	 
	 if(megaFat)
	 {
	  current = fatFloat.getKeyFrame(animTime);
	  if(steps >= 3)
	  {
	   Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),pos.x,pos.y);
	   gameplay.addEntity(e);
	   megaFat = false;
	   usingItem = false;
	  }
	 }
	 else
	 {
	  switch(facing)
	  {
	   case 0:
	    current = stand[0].getKeyFrame(animTime);
	   break;
	   case 1:
	    current = stand[1].getKeyFrame(animTime);
	   break;
	   
	   case 2:
	    current = stand[2].getKeyFrame(animTime);
	   break;
	   
	   case 3:
	    current = stand[3].getKeyFrame(animTime);
	   break;
	  }
	 }
	 
	 newInput = false;
	 if(Gdx.input.justTouched())
	 {
	  newInput = true;
	 }
	 
	 if(Gdx.input.isTouched() && !isMoving)
	 {
	  if(gameplay.hud.touchDown())
	  {
	   facing = 0;
	   move();
	  }
	  else if(gameplay.hud.touchLeft())
	  {
	   facing = 1;
	   move();
	  }
	  else if(gameplay.hud.touchRight())
	  {
	   facing = 2;
	   move();
	  }
	  else if(gameplay.hud.touchUp())
	  {
	   facing = 3;
	   move();
	  }
	 }
	 
	 if(isMoving)
	 {
      if(pos.x > destinyX)
      {
       pos.x -= Gdx.graphics.getDeltaTime() * speed;
       if(pos.x < destinyX)
       {
    	pos.x = destinyX;
       }
      }
      else
      {
       pos.x += Gdx.graphics.getDeltaTime() * speed;  
       if(pos.x > destinyX)
       {
    	pos.x = destinyX;
       }
      }
      
      if(pos.y > destinyY)
      {
       pos.y -= Gdx.graphics.getDeltaTime() * speed;
       if(pos.y < destinyY)
       {
    	pos.y = destinyY;
       }
      }
      else
      {
       pos.y += Gdx.graphics.getDeltaTime() * speed;
       if(pos.y > destinyY)
       {
    	pos.y = destinyY;
       }
      }
      
      if(pos.x == destinyX && pos.y == destinyY)
	  {
	   isMoving = false;
	  }
	 }
	 
	 collision = gameplay.EntityCollision(this);
	 if(collision != null)
	 {
	  if(collision instanceof Entity_Enemy_Ghostling)
	  {
	   if(level >= collision.level)
	   {
	    collision.isDead = true;
	   }
	   else
	   {
	    if(destinyX == oldX && destinyY == oldY)
	    {
		 collision.AIFunction(0);
		 collision.mvmTime = -1;
	    }
	    else
	    {
	     destinyX = oldX;
	     destinyY = oldY;
	     collision.mvmTime = -1;
	    }
	   }
	  }
	  else if(collision instanceof Entity_Event)
	  {
	   gameplay.map_manager.callEvent((Entity_Event)collision);
	  }
	 }
	}
	
    public void useItem()
    {
     switch(itemSelected)
     {
      case Tools_Hud_Inventory.NOTHING:
       usingItem = false;
      break;
      
      case Tools_Hud_Inventory.AXE:
       usingItem = true;
       switch(facing)
       {
        case 0:
         itemBox.set(pos.x,pos.y + 32,32,32);
        break;
        
        case 1:
         itemBox.set(pos.x - 32,pos.y,32,32);
        break;
           
        case 2:
         itemBox.set(pos.x + 32,pos.y,32,32);
        break;
        
        case 3:
         itemBox.set(pos.x,pos.y - 32,32,32);
        break;
       }
      break;
      
      case Tools_Hud_Inventory.RED:
       if(!isFat)
       {
    	Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),pos.x,pos.y);
  	    gameplay.addEntity(e);
  	    usingItem = true;
        isFat = true;
       }
      break;
      
      case Tools_Hud_Inventory.BLUE:
    	if(megaFat)
  	    {
  	     return;
  	    }
  	    else
  	    {
  	     Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),pos.x,pos.y);
  	  	 gameplay.addEntity(e);
  	  	 megaFat = true;
  	  	 steps = 0;
  	  	 usingItem = false;
  	    }
      break;
     }
    }
    
    public void doDamage(int quant)
    {
     if(!invencible)
     {
      health -= quant;
      invencible = true;
     }
    }
	
	
	public void move()
	{
	 if(megaFat)
	 {
      steps += 1;
	 }
	 switch(facing)
	 {
	  case 0:
	   if(!gameplay.map_manager.getSolidTile((int)pos.x /32,(int)( pos.y + 32)/32))
	   {
		isMoving = true;
		destinyX = pos.x;
		destinyY = pos.y + 32;
		oldX = pos.x;
		oldY = pos.y;
	   }
	  break;
	  case 1:
	   if(!gameplay.map_manager.getSolidTile((int)(pos.x- 32)/32,(int)pos.y/32))
	   {
		isMoving = true;
		destinyX = pos.x - 32;
		destinyY = pos.y;
		oldX = pos.x;
		oldY = pos.y;
	   }
	  break;
	  
	  case 2:
		if(!gameplay.map_manager.getSolidTile((int)(pos.x+ 32)/32,(int)pos.y/32))
		{
		 isMoving = true;
		 destinyX = pos.x + 32;
		 destinyY = pos.y;
		 oldX = pos.x;
	     oldY = pos.y;
		}
	  break;
	  
	  case 3:
	   if(!gameplay.map_manager.getSolidTile((int)pos.x/32,(int)(pos.y- 32)/32))
  	   {
		isMoving = true;
		destinyX = pos.x;
		destinyY = pos.y -32;
		oldX = pos.x;
		oldY = pos.y;
	   }
	  break;
	 }
	}
	
	public void Sword(int side)
	{
	 inAnim = true;
     animX = pos.x;
	 animY = pos.y;
     animY -= 32;
     finalBossRect.set(animX,animY,32,32);
     switch(side)
     {
      case 0:
       current = swordingLeft;
       animX -= 7;
       finalBossRect.set(animX - 16,animY,32,64);
       
      break;
      
      case 1:
       current = swordingUp;
       finalBossRect.set(animX,animY + 16,32,32);
      break;
         
      case 2:
       current = swordingRight;
       finalBossRect.set(animX + 16,animY,32,64);
      break;
     }
     gameplay.bossCheck(finalBossRect);
	}
	
	public void backToNormal()
	{
     current = stand[3].getKeyFrame(0);
     inAnim = false;
	}
	
	public void die()
	{
	 current = dead;
	}
	
	public void Render(SpriteBatch batch)
	{
	 if(invencible)
	 {
	  flashTime += Gdx.graphics.getDeltaTime();
	  if(flashTime >= 0.2f)
	  {
	   flashTime = 0;
	   if(flash)
	   {
		flash = false;
	   }
	   else
	   {
		flash = true;
	   }
	  }
	 }
	 
	 if(!flash)
	 {
	  if(!inAnim)
	  {
	   batch.draw(current,pos.x,pos.y);
	  }
	  else
	  {
	   batch.draw(current,animX,animY);  
	  }
	 }
	}
}
