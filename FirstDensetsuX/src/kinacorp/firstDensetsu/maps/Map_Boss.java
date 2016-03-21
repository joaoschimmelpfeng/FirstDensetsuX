package kinacorp.firstDensetsu.maps;

import java.util.Random;

import kinacorp.firstDensetsu.State_Gameplay;
import kinacorp.firstDensetsu.Entity.Entity_BossOrb;
import kinacorp.firstDensetsu.Entity.Entity_SpawnCloud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.Rectangle;

public class Map_Boss extends Map_Base
{
	
	boolean movePlayer;
	boolean scene1;
	boolean scene2;
	boolean scene3;
	
	boolean boss1Alive = true;
	boolean boss2Alive = false;
	boolean boss3Alive = false;
	boolean everyoneDead = false;
	boolean prepare = true;
	boolean changemap = false;
	
	Random rand;
	
	Rectangle colBoss1;
	Rectangle colBoss2;
	Rectangle colBoss3;
	
	Animation charging;
	
	int nextToShoot = 0;
	int contador = 0;
	float timingAnim = 0;
	float timing2 =0;
	
	Texture sheet;
	TextureRegion[] anim1 = new TextureRegion[9];
	TextureRegion boss1;
	TextureRegion boss2;
	TextureRegion boss3;
	TextureRegion deadBoss;

	public Map_Boss(State_Gameplay gameplay) 
	{
		super(gameplay);
		mapLoader = new TmxMapLoader();
		
		rand = new Random();
		
		sheet = new Texture("data/Char1.png");
		boss1 = new TextureRegion(sheet,160,32,32,32);
		boss1.flip(false,true);
		
		boss2 = new TextureRegion(sheet,160,32,32,32);
		boss2.flip(false,true);
		
		boss3 = new TextureRegion(sheet,160,32,32,32);
		boss3.flip(false,true);
		
		deadBoss = new TextureRegion(sheet,193,128,32,32);
		deadBoss.flip(false,true);
		
		colBoss1 = new Rectangle(160,96,32,32);
		colBoss2 = new Rectangle(64,160,32,32);
		colBoss3 = new Rectangle(256,160,32,32);
		
		for(int i = 0; i < 3; i++)
		{
		 anim1[i] = new TextureRegion(sheet,(32 * i) + 160,32,32,32);
		 anim1[i].flip(false,true);
		}
		anim1[3] = new TextureRegion(sheet,160,64,32,32);
		anim1[4] = new TextureRegion(sheet,192,64,32,32);
		anim1[5] = new TextureRegion(sheet,224,64,32,32);
		anim1[6] = new TextureRegion(sheet,160,64,32,32);
		anim1[7] = new TextureRegion(sheet,160,96,32,32);
		anim1[8] = new TextureRegion(sheet,192,96,32,32);
		
		
		anim1[3].flip(false,true);
		anim1[4].flip(false,true);
		anim1[5].flip(false,true);
		anim1[6].flip(false,true);
		anim1[7].flip(false,true);
		anim1[8].flip(false,true);
		
		charging = new Animation(0.1f,anim1);
		charging.setPlayMode(Animation.NORMAL);
		
		 
		Parameters parameters = new Parameters();
		parameters.yUp = false;
		map = mapLoader.load("data/mapBOSS.tmx",parameters);
		
		botLayer = (TiledMapTileLayer) map.getLayers().get("Bot");
		bot2Layer = (TiledMapTileLayer) map.getLayers().get("Bot2");
		topLayer = (TiledMapTileLayer) map.getLayers().get("Top");
		collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
		
		movePlayer = true;
		scene1 = true;
		scene2 = false;
		scene3 = false;
	}
	
	public void Update()
	{
	 timingAnim += Gdx.graphics.getDeltaTime();
	 if(movePlayer)
	 {
	  gameplay.player.facing = 3;
	  gameplay.player.destinyX = 160;
	  gameplay.player.destinyY = 192;
	  gameplay.player.isMoving = true;
	  movePlayer = false;
	 }
	 
	 if(scene1)
	 {
	  if(!gameplay.player.isMoving)
	  {
	   boss2Alive = true;
	   boss3Alive = true;
	   Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),64,160);
	   gameplay.addEntity(e);
	   
	   Entity_SpawnCloud e2 = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),256,160);
	   gameplay.addEntity(e2);
	   gameplay.hud.isInBoss = true;
	   scene1 = false;
	   scene2 = true;
	  }
	 }
	 
	 if(scene2)
	 {
      if(prepare)
      {
       nextToShoot = rand.nextInt(3);
       prepare = false;
      }
      
      switch(nextToShoot)
      {
       case 0:
          boss1 = charging.getKeyFrame(timingAnim);
       break;
       
       case 1:
          boss2 = charging.getKeyFrame(timingAnim);
       break;
        
       case 2:
          boss3 = charging.getKeyFrame(timingAnim);
       break;
      }
      
      if(charging.isAnimationFinished(timingAnim))
      {
       prepare = true;
       timingAnim = 0;
       switch(nextToShoot)
       {
        case 0:
         Entity_BossOrb e1 = new Entity_BossOrb(gameplay,160,96,sheet);
         boss1 = charging.getKeyFrame(timingAnim);
         gameplay.addEntity(e1);
        break;
           
        case 1:
         Entity_BossOrb e2 = new Entity_BossOrb(gameplay,64,160,sheet);
         boss2 = charging.getKeyFrame(timingAnim);
         gameplay.addEntity(e2);
        break;
            
        case 2:
         Entity_BossOrb e3 = new Entity_BossOrb(gameplay,256,160,sheet);
         boss3 = charging.getKeyFrame(timingAnim);
         gameplay.addEntity(e3);
        break;
       }
      }
      
      for(int i = 0; i < gameplay.entityList.size();i++)
      {
       if(gameplay.entityList.get(i) instanceof Entity_BossOrb)
       {
    	if(gameplay.entityList.get(i).colBox.overlaps(colBoss1))
    	{
         if(((Entity_BossOrb)gameplay.entityList.get(i)).hurtBoss)
         {
          contador++;
          gameplay.deleteEntity(gameplay.entityList.get(i));
         }
    	}
    	
    	if(gameplay.entityList.get(i).colBox.overlaps(colBoss2))
    	{
    	 if(((Entity_BossOrb)gameplay.entityList.get(i)).hurtBoss)
         {
          contador++;
          gameplay.deleteEntity(gameplay.entityList.get(i));
         }
    	}
    	
    	if(gameplay.entityList.get(i).colBox.overlaps(colBoss3))
    	{
    	 if(((Entity_BossOrb)gameplay.entityList.get(i)).hurtBoss)
         {
          contador++;
          gameplay.deleteEntity(gameplay.entityList.get(i));
         }
    	}
       }
      }
      
      
      if(contador > 10)
      {
       scene2 = false;
       scene3 = true;
       Entity_SpawnCloud e3 = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),160,96);
	   gameplay.addEntity(e3);
       
       Entity_SpawnCloud e = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),64,160);
	   gameplay.addEntity(e);
	   
	   Entity_SpawnCloud e2 = new Entity_SpawnCloud(gameplay,new Texture("data/effectSheet.png"),256,160);
	   gameplay.addEntity(e2);
	   
	   boss1Alive = false;
	   boss2Alive = false;
	   boss3Alive = false;
	   everyoneDead = true;
	   
	   for(int i = 0; i < gameplay.entityList.size();i++)
	   {
		if(gameplay.entityList.get(i) instanceof Entity_BossOrb)
		{
		 gameplay.deleteEntity(gameplay.entityList.get(i));
		}
	   }
      }
	 }
	 
	 if(scene3)
	 {
	  timing2 += Gdx.graphics.getDeltaTime();
	  if(timing2 >= 2)
	  {
	   if(!changemap)
	   {
		gameplay.map_manager.changeMapFade(new Map_Final(gameplay));
	   }
	   if(gameplay.isDark)
	   {
		gameplay.map_manager.finishedTransition();
	    gameplay.player.pos.x = 128;
 	    gameplay.player.pos.y = 256;
	    gameplay.player.iniX = 96;
	    gameplay.player.iniY = 256;
	    gameplay.player.destinyX = 128;
 	    gameplay.player.destinyY = 128;
	    gameplay.player.health = 3;
	    gameplay.player.backToNormal();
	    gameplay.hud.isInBoss = false;
	    gameplay.hud.blockInv = true;
	    gameplay.isDark = false;
	    gameplay.deadTime = 0;
	   }
	  }
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
	 
	 if(boss1Alive)
	 {
  	  batch.draw(boss1,160,96);
	 }
	 
	 if(boss2Alive)
	 {
	  batch.draw(boss2,64,160); 
	 }
	 
	 if(boss3Alive)
	 {
	  batch.draw(boss3,256,160);	 
	 }
	 
	 if(everyoneDead)
	 {
      batch.draw(deadBoss,160,96);
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
