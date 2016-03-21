package kinacorp.firstDensetsu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State_Manager 
{
	
	public static final int TITLE = 0;
	public static final int GAMEPLAY = 1;
	public static final int INTRO = 2;
	
	boolean changeState;
	
	public FirstDensetsu main;
	
	State_Base actual;
	State_Base placeHolder;
	State_Title title;
	
	public State_Manager(FirstDensetsu main)
	{
	 this.main = main;
	 changeState = false;
	 
	 title = new State_Title(this);
	 actual = title;
	}
	
	public void changeState(int i)
	{
	 switch(i)
	 {
	  case TITLE:
	   placeHolder = new State_Title(this);
	   changeState = true;
	  break;
	   
	   case GAMEPLAY:
	    placeHolder = new State_Gameplay(this);
	    changeState = true;
	   break;
	 }
	}
	
	public void Run(SpriteBatch batch)
	{
	 actual.Update();
	 main.camera.update();
	 actual.Render(batch);
	 if(changeState)
	 {
	  actual = placeHolder;
	  changeState = false;
	 }
	}
}
