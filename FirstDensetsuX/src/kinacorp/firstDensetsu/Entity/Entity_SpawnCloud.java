package kinacorp.firstDensetsu.Entity;

import kinacorp.firstDensetsu.State_Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity_SpawnCloud extends Entity_Base
{
	Animation animation;
	
	TextureRegion current;
	TextureRegion[] frames = new TextureRegion[6];
	float stateTime;

	public Entity_SpawnCloud(State_Gameplay gameplay,Texture sheet, float x, float y) 
	{
	 super(gameplay, x, y);
	 stateTime = 0;
	 
	 for(int i = 0; i < 6; i++)
	 {
	  if(i > 3)
	  {
	   frames[i] = new TextureRegion(sheet,32 * (i-4),64,32,32);
	  }
	  else
	  {
	   frames[i] = new TextureRegion(sheet,32 * i,32,32,32);
	  }
	 }
	 current = frames[0];
	 animation = new Animation(0.1f,frames);
	}
	
	public void Update()
	{
	 stateTime += Gdx.graphics.getDeltaTime();
	 if(animation.isAnimationFinished(stateTime))
	 {
	  gameplay.deleteEntity(this);
	  return;
	 }
	 current = animation.getKeyFrame(stateTime);
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(current,pos.x,pos.y);
	}
	
}
