package kinacorp.firstDensetsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class State_Title extends State_Base
{
	TextureRegion splashScreen;
	TextureRegion title2;
	TextureRegion title;
	
	Tools_Hud hud;
	Tools_Music audioManager;
	
	Vector2 pos;
	
	Color splashColor;
	
	boolean isSplash;
	boolean alterColor1;
	boolean fading;
	boolean fadeReverse;
	boolean alterColor2;
	boolean alterColor3;
	boolean alterColor4;
	boolean alterColor5;
	boolean anim1;
	boolean anim2;
	boolean anim3;
	boolean unlock;
	
	float fadeTime;
	float stayTime;
	

	public State_Title(State_Manager manager) 
	{
	 super(manager);
	 hud = new Tools_Hud();
	 splashScreen = new TextureRegion(new Texture("data/splash.png"));
	 splashScreen.flip(false,true);
	 title = new TextureRegion(new Texture("data/title.png"));
	 title.flip(false,true);
	 title2 = new TextureRegion(new Texture("data/title2.png"));
	 title2.flip(false,true);
	 
	 audioManager = new Tools_Music();
	 
	 pos = new Vector2(0,0);
	 pos.x = 160;
	 
	 isSplash = true;
	 alterColor1 = true;
	 alterColor2 = true;
	 alterColor3 = true;
	 alterColor4 = true;
	 alterColor5 = false;
	 anim1 = true;
	 anim2 = false;
	 anim3 = false;
	 unlock = false;
	 fadeReverse = false;
	 
	 fadeTime = 0;
	 stayTime = 0;
	 
	 audioManager.createMusic("Sound/Title.mp3");
	 audioManager.playMusic();
	 audioManager.createSound1("Sound/SwordTransition.wav",50);
	}
	
	public void Update()
	{
		
	}
	
	public void Render(SpriteBatch batch)
	{
	 if(isSplash)
	 {
	  fadeTime += Gdx.graphics.getDeltaTime() / 2;
	  if(alterColor1)
	  {
	   splashColor = batch.getColor();
	   splashColor.a = 0;
	   alterColor1 = false;
	   fading = true;
	  }
	  if(fading)
	  {
	   splashColor.lerp(Color.WHITE, fadeTime);
	   if(fadeTime >= 1)
	   {
	    fading = false;
	    fadeReverse = true;
	   }
	  }
	 
	  if(fadeReverse)
	  {
	   splashColor.a -= Gdx.graphics.getDeltaTime();
	   if(splashColor.a <= 0.05f)
	   {
	    fadeReverse = false;
	    isSplash = false;
	    fadeTime = 0;
	    splashColor.a = 0;
	   }
	  }
	 
	 
	  batch.setColor(splashColor);
	  batch.draw(splashScreen,0,0);
	 }
	 else
	 {
	  if(alterColor2)
	  {
	   fadeTime += Gdx.graphics.getDeltaTime() / 4;
	   splashColor.lerp(Color.WHITE, fadeTime);
	   if(fadeTime >= 0.2f)
	   {
		alterColor2 = false;
	   }
	   batch.setColor(splashColor);
	  }
	  if(anim1)
	  {
	   if(!alterColor2)
	   {
		if(splashColor.a >= 0.02f)
		{
	     splashColor.a -= Gdx.graphics.getDeltaTime();
		}
	   }
	   batch.setColor(splashColor);
	   batch.draw(title2,pos.x,pos.y);
	   pos.y += 50 * Gdx.graphics.getDeltaTime();
	   
	   if(pos.y >= 100)
	   {
		anim1 = false;
		anim2 = true;
		pos.x = 0;
		splashColor.a = 0;
		fadeTime = 0;
	   }
	  }
	  if(anim2)
	  {
	   if(alterColor3)
	   {
	    fadeTime += Gdx.graphics.getDeltaTime();
	    splashColor.lerp(Color.WHITE,fadeTime);
	    if(fadeTime >= 1)
	    {
	     alterColor3 = false;
	    }
	   }
	   if(!alterColor3)
	   {
		if(splashColor.a >= 0.02f)
		{
		 splashColor.a -= Gdx.graphics.getDeltaTime();
		}
		else
		{
		 splashColor.a = 0;
		}
	   }
	   batch.setColor(splashColor);
	   batch.draw(title2,pos.x,pos.y);
	   pos.x += 50 * Gdx.graphics.getDeltaTime();
	   if(pos.x >= 160)
	   {
		anim2 = false;
		anim3 = true;
		splashColor.a = 0;
		fadeTime = 0;
	   }
	  }
	  if(anim3)
	  {
	   if(alterColor4)
	   {
		fadeTime += Gdx.graphics.getDeltaTime();
		splashColor.lerp(Color.WHITE, fadeTime);
		if(fadeTime >= 1f)
		{
	     unlock = true;
	     alterColor4 = false;
		}
	   }
	   
	   batch.setColor(splashColor);
	   batch.draw(title,0,0);
	   
	   if(unlock && Gdx.input.justTouched())
	   {
		alterColor5 = true;
	   }
	   
	   if(alterColor5)
	   {
		splashColor.a -= Gdx.graphics.getDeltaTime();
		if(splashColor.a <= 0.2f)
		{
	     manager.main.android.showAd();
		 manager.changeState(State_Manager.GAMEPLAY);
		 audioManager.sound1.play();
		 audioManager.disposeMusic();
		}
	   }
	   
	  }
	 }
	 
	 batch.setColor(Color.WHITE);
	 hud.RenderOnlyHud(batch);
	}
	

}
