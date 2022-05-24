import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class MainConnect2 {

	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		JeuConnect jeu=new JeuConnect("Connect Me");
		AppGameContainer app=new AppGameContainer(jeu);
		app.setDisplayMode(640, 640, false);
		app.setShowFPS(false);
		app.setTargetFrameRate(60);
		app.start();
	}

}
