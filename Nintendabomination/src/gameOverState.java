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
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		victory = new Image("res/youWin.png");
		background = new Music("res/dothemario.wav");
		delay = 3500;
		played = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		if(Hub.numLives > 0)
			victory.drawCentered(0, 0);
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
