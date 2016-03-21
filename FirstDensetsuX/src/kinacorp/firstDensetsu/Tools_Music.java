package kinacorp.firstDensetsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Tools_Music 
{
	Music music;
	Sound sound1;
	
	Rectangle openAudio;
	Rectangle plusBt;
	Rectangle minusBt;
	
	TextureRegion volumeControl;
	
	State_Gameplay gameplay;
	
	BitmapFont font;
	
	float volume;
	float menuY;
	
	int showVol = 0;
	float timeToClose = 0;
	float modifier1 = 0;
	
	boolean habilitVolume;
	boolean isOpen;
	
	public Tools_Music()
	{
	 menuY = -38;
	 volume = 0.99f;
	 volumeControl = new TextureRegion(new Texture("data/items.png"),160,0,96,48);
	 volumeControl.flip(false, true);
	 
	 habilitVolume = false;
	 isOpen = false;
	}
	
	public Tools_Music(State_Gameplay gameplay)
	{
	 this.gameplay = gameplay;	
	 
	 menuY = -38;
	 
	 font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
	 font.setScale(0.8f,-0.8f);
	 
	 openAudio = new Rectangle(300,0,20,12);
	 plusBt = new Rectangle(228,3,26,26);
	 minusBt= new Rectangle(291,3,26,26);
	 
	 volume = 0.99f;
	 volumeControl = new TextureRegion(new Texture("data/items.png"),160,0,96,48);
	 volumeControl.flip(false, true);
	 
	 habilitVolume = false;
	 isOpen = false;
	}
	
	public void Update()
	{
	 if(habilitVolume)
	 {
	  if(!isOpen)
	  {
	   if(Gdx.input.justTouched() && gameplay.touchRect.overlaps(openAudio))
	   {
		menuY = 0;
		isOpen = true;
	   }
	  }
	  else
	  {
	   timeToClose += Gdx.graphics.getDeltaTime();
	   if(timeToClose >= 3)
	   {
		menuY = -38;
		timeToClose = 0;
		isOpen = false;
	   }
	   
	   if(Gdx.input.isTouched() && gameplay.touchRect.overlaps(plusBt))
	   {
		timeToClose = 0;
		if(volume >= 0.99f)
		{
		 volume = 0.99f;
		 reVolume();
		}
		else
		{
	     volume += (Gdx.graphics.getDeltaTime() / 4 );
	     reVolume();
		}
	   }
	   
	   if(Gdx.input.isTouched() && gameplay.touchRect.overlaps(minusBt))
	   {
		timeToClose = 0;
		if(volume <= 0)
		{
		 volume = 0;
		 reVolume();
		}
		else
		{
	     volume -= (Gdx.graphics.getDeltaTime() / 4);
	     reVolume();
		}
	   }
	  }
	 }
	}
	
	public void createMusic(String path)
	{
	 disposeMusic();
	 music = Gdx.audio.newMusic(Gdx.files.internal(path));
	 music.setLooping(true);
	 music.setVolume(volume);
	}
	
	public void playMusic()
	{
	 music.setVolume(volume);
	 music.play();
	}
	
	public void pauseMusic()
	{
	 music.pause();
	}
	
	public void disposeMusic()
	{
	 if(music != null)
	 {
	  music.pause();
	  music.dispose();
	 }
	}
	
	public void createSound1(String path,int modifier)
	{
	 sound1 = Gdx.audio.newSound(Gdx.files.internal(path));
	 modifier1 = modifier;
	 sound1.setVolume(0, volume+modifier);
	}
	
	public void playSound1()
	{
	 sound1.play();
	}
	
	public void pauseSound1()
	{
	 sound1.pause();
	}
	
	public void disposeSound1()
	{
	 if(sound1 != null)
	 {
	  sound1.pause();
	  sound1.dispose();
	 }
	}
	
	public void reVolume()
	{
     if(music != null)
     {
	  music.setVolume(volume);
     }
     if(sound1 != null)
     {
	  sound1.setVolume(0, volume+modifier1);
     }
	}
	
	public void Render(SpriteBatch batch)
	{
	 if(habilitVolume)
	 {
	  batch.draw(volumeControl, 320-96,menuY);
	 }
	 
	 if(isOpen)
	 {
	  showVol = Math.round(volume * 100);
	  font.draw(batch, ""+showVol, 259, 8);
	 }
	}
}
