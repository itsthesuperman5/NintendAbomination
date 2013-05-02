import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class NewGame extends StateBasedGame {

	public static final int MENUSTATE = 0;
	public static final int HUBSTATE = 1;
	public static final int DKSTATE = 2;
	public static final int ZELDASTATE = 3;
	public static final int COUNTRYSTATE = 4;
	public static final int KIRBYSTATE = 5;
	public static final int METROIDSTATE = 6;
	public static final int GAMEOVERSTATE = 7;
	
	public NewGame() {
		super("NintendAbomination");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		AppGameContainer app = new AppGameContainer(new NewGame());
		app.setDisplayMode(800, 600, false);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		this.addState(new menuState(MENUSTATE));
		this.addState(new Hub(HUBSTATE));
		this.addState(new dkState(DKSTATE));
		this.addState(new zeldaState(ZELDASTATE));
		this.addState(new countryState(COUNTRYSTATE));
		this.addState(new kirbyState(KIRBYSTATE));
		this.addState(new metroidState(METROIDSTATE));
		this.addState(new gameOverState(GAMEOVERSTATE));
	}

}
