package kinacorp.firstDensetsu;

public interface Tools_Android 
{
	public float tilt();
	
	public void save(String key,boolean value);
	
	public void deleteSharedPrefs();
	
	public boolean getKey(String key);
	
	public void showAd();
}
