import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class gameOverState extends BasicGameState{
	private int stateID;
	private Image victory;
	private Music background;
	private Sound click;
	private int delay;
	private boolean played;
	
	public gameOverState(int stateID)
	{
		this.stateID = stateID;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		background.play();
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		victory = new Image("res/nintendoCredits.jpg");
		background = new Music("res/dothemario.wav");
		delay = 3500;
		played = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		if(Hub.numLives > 0){
			victory.drawCentered(400, 300);
			g.drawString("Lead Developer: ", 10, 150);
			g.drawString("Stephen 'The Man' Wright", 10, 170);
			g.drawString("Developer/Design: ", 10, 200);
			g.drawString("Christopher T. Bolgiano", 10, 230);
			g.drawString("Art/Design: ", 10, 260);
			g.drawString("Tolga Cerrah", 10, 290);
			g.drawString("Joey", 10, 310);
			g.drawString("Max", 10, 340);
			g.drawString("Thank you Radford University!!!", 250, 50);
			g.drawString("ITEC493 Advanced Game Development", 250, 80);
			g.drawString("Spring 2013", 345, 110);
			g.drawString("Game Over", 350, 500);
		}
		else
			g.drawString("Game Over", 350, 300);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
}
