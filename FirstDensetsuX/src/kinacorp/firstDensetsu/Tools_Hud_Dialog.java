package kinacorp.firstDensetsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tools_Hud_Dialog 
{
	Tools_Hud hud;
	
	Texture sheet;
	TextureRegion dialogTL;
	TextureRegion dialogTR;
	TextureRegion dialogBL;
	TextureRegion dialogBR;
	
	TextureRegion MT;
	TextureRegion MB;
	TextureRegion ML;
	TextureRegion MR;
	
	TextureRegion M;
	
	BitmapFont font;
	
	public boolean showDialog;
	boolean click;
	boolean drawSecond;
	boolean end;
	boolean animDialog;
	boolean animReverse;
	
	float stateTime;
	float xSize;
	float xPos1;
	float xPos2;
	float yPos;
	int cont;
	int cont2;
	
	String text;
	String text2;
	char[] chars;
	
	private float dialogAnimSpeed = 200;
	private float dialogSpeed = 0.05f;
	
	public Tools_Hud_Dialog(Tools_Hud hud)
	{
	 this.hud = hud;
	 font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
	 font.setScale(0.8f,-0.8f);
	 
	 stateTime = 0;
	 xSize = 0;
	 xPos1 = 5 * 32;
	 xPos2 = 5 * 32;
	 yPos = 11*32;
	 cont = 0;
	 cont2 = 0;
	 
	 text = "";
	 text2 = "";
	 
	 end = false;
	 
	 showDialog = false;
	 click = false;
	 drawSecond = false;
	 animDialog = false;
	 animReverse = false;
		
	 sheet = new Texture("data/dialog.png");
	 dialogTL = new TextureRegion(sheet,0,0,32,32);
	 dialogTL.flip(false, true);
	 dialogTR = new TextureRegion(sheet,32,0,32,32);
	 dialogTR.flip(false, true);
	 dialogBL = new TextureRegion(sheet,0,32,32,32);
	 dialogBL.flip(false, true);
	 dialogBR = new TextureRegion(sheet,32,32,32,32);
	 dialogBR.flip(false, true);
	 MT = new TextureRegion(sheet,16,0,16,32);
	 MT.flip(false, true);
	 MB = new TextureRegion(sheet,16,32,16,32);
	 MB.flip(false, true);
	 ML = new TextureRegion(sheet,0,16,32,16);
	 ML.flip(false, true);
	 MR = new TextureRegion(sheet,32,16,32,16);
	 MR.flip(false, true);
	 M = new TextureRegion(sheet,16,16,32,32);
	 M.flip(false,true);
	}
	
	public void Update()
	{
	 if(animDialog)
	 {
	  if(yPos <= 6 * 32)
	  {
	   if(xSize >= 8 * 32)
	   {
		xSize = 8*32;
		xPos1 = 32;
		xPos2 = 9 * 32;
		showDialog = true;
		animDialog = false;
		return;
	   }
	   yPos = 6*32;
	   xSize += (dialogAnimSpeed * 2) * Gdx.graphics.getDeltaTime();
	   xPos1 -= dialogAnimSpeed * Gdx.graphics.getDeltaTime();
	   xPos2 += dialogAnimSpeed * Gdx.graphics.getDeltaTime();
	  }
	  else
	  {
	   yPos -= dialogAnimSpeed * Gdx.graphics.getDeltaTime();
	  }
	  return;
	 }
	 else if(animReverse)
	 {
	  if(xSize <= 0)
	  {
	   yPos += 100 * Gdx.graphics.getDeltaTime();
	   if(yPos > 11 * 32)
	   {
		xSize = 0;
		xPos1 = 5 * 32;
		xPos2 = 5 * 32;
		yPos = 11*32;
		animReverse = false;
		showDialog = false;
	   }
	   return;
	  }
	  xSize -= (dialogAnimSpeed * 2) * Gdx.graphics.getDeltaTime();
	  xPos1 += dialogAnimSpeed * Gdx.graphics.getDeltaTime();
	  xPos2 -= dialogAnimSpeed * Gdx.graphics.getDeltaTime();
	 }
	 
	 if(!click)
	 {
	  stateTime += Gdx.graphics.getDeltaTime();
	  if(stateTime >= dialogSpeed)
	  {
	   if(drawSecond)
	   {
		text2 += chars[cont];
	   }
	   else
	   {
	    text += chars[cont];
	   }
	   cont++;
	   cont2++;
	   stateTime = 0;
	   if(cont2 == 22)
	   {
		drawSecond = true;
	   }
	   
	   if(cont2 >= 44)
	   {
		click = true;
	   }
	   
	   if(cont >= chars.length)
	   {
		click = true;
		end = true;
	   }
	  }
	  return;
	 }
	 
	 if(hud.touchA() && Gdx.input.justTouched())
	 {
	  if(end)
	  {
	   closeDialog();
	   return;
	  }
	  click = false;
	  text = "";
	  text2 = "";
	  cont2 = 0;
	  drawSecond = false;
	 }
	}
	
	public void CallDialog(String text)
	{
	 chars = text.toCharArray();
	 animDialog = true;
	 showDialog = true;
	 end = false;
	 click = false;
	}
	
	public void closeDialog()
	{
	 drawSecond = false;
	 animReverse = true;
	 animDialog = false;
	 text = "";
	 text2 = "";
	 cont = 0;
	 cont2 = 0;
	}
	
	public void Render(SpriteBatch batch)
	{
	 batch.draw(MT,xPos1,yPos,xSize,32);
	 batch.draw(MB,xPos1,yPos + 48,xSize,32);
	 batch.draw(ML,xPos1 -32,yPos + 32,32,32);
	 batch.draw(MR,xPos2,yPos + 32,32,32);
	 batch.draw(M,xPos1,yPos + 32,xSize,32);
	 batch.draw(dialogTL,xPos1 -32,yPos);
	 batch.draw(dialogTR,xPos2,yPos);
	 batch.draw(dialogBL,xPos1-32,yPos + 48);
	 batch.draw(dialogBR,xPos2,yPos + 48);
	 font.draw(batch,text, 10, (6*32) +20);
	 if(drawSecond)
	 {
	  font.draw(batch,text2,10,(7*32)+20);
	 }
	}
}
