package kinacorp.firstDensetsu;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FirstDensetsuX";
		cfg.useGL20 = true;
		cfg.width = 320;
		cfg.height = 480;
		
		new LwjglApplication(new FirstDensetsu(new Tools_Android_For_Desktop()), cfg);
	}
}
