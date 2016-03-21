package kinacorp.firstDensetsu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstDensetsu implements ApplicationListener 
{
	public OrthographicCamera camera;
	private SpriteBatch batch;
	
	public Tools_Android android;
	public Tools_Localization localization;
	
	State_Manager manager;
	
	public FirstDensetsu(Tools_Android android)
	{
	 this.android = android;
	}
	
	@Override
	public void create() 
	{
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 320, 480);
		
		batch = new SpriteBatch();
		
		localization = Tools_Localization.getInstance();
		
		manager = new State_Manager(this);
	}

	@Override
	public void dispose() 
	{
		batch.dispose();
	}

	@Override
	public void render() 
	{	
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		manager.Run(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
