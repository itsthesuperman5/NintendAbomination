import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.RotateTransition;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class Hub extends BasicGameState{

	public static int worldsBeaten = 0;
	public static boolean classicBeaten = false;
	public static boolean zeldaBeaten = false;
	public static boolean countryBeaten = false;
	public static boolean kirbyBeaten = false;
	public static boolean metroidBeaten = false;
	public static int numLives;
	private Image background;  //the hub's background image
	private int stateID;  //the current stateID
	private grant grant;  //the player's character
	private float playerX, playerY;  //the player's x,y coordinates
	private String orientation; // the player's facing direction
	private boolean moving;  //whether the player is moving or not
	private Animation[] swirls;
	private vortex vortex;
	private Music bg;
	
	public Hub(int stateID)
	{
		this.stateID = stateID;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg.play();
		playerX = 390;
		playerY = 260;
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		bg.stop();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		background = new Image("res/nexus.png");
		bg = new Music("res/hub.wav");
		numLives = 9;
		grant = new grant();
		vortex = new vortex();
		orientation = "Down";
		playerX = 390;
		playerY = 260;
		swirls = new Animation[6];
		swirls[0] = vortex.getSwirl();
		swirls[1] = vortex.getSwirl();
		swirls[2] = vortex.getSwirl();
		swirls[3] = vortex.getSwirl();
		swirls[4] = vortex.getSwirl();
		swirls[5] = vortex.getSwirl();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		background.draw(); //draws the hub background
		g.drawString("Lives left: "+numLives, 650, 25);
		if(!kirbyBeaten)
			swirls[0].draw(165, 270);
		if(!countryBeaten)
			swirls[1].draw(215, 380);
		if(!metroidBeaten)
			swirls[2].draw(370, 460);
		if(!zeldaBeaten)
			swirls[3].draw(540, 380);
		if(!classicBeaten)
			swirls[4].draw(610, 270);
		if(worldsBeaten == 5)
			swirls[5].draw(380, 75);
		//the next 4 conditionals draw player's standing image
		if(orientation.equalsIgnoreCase("Down") && !moving)
		{
			grant.getStandDown().draw(playerX, playerY);
		}
		else if(orientation.equalsIgnoreCase("Up") && !moving)
		{
			grant.getStandUp().draw(playerX, playerY);
		}
		else if(orientation.equalsIgnoreCase("Left") && !moving)
		{
			grant.getStandLeft().draw(playerX, playerY);
		}
		else if(orientation.equalsIgnoreCase("Right") && !moving)
		{
			grant.getStandRight().draw(playerX, playerY);
		}
		
		if(orientation.equalsIgnoreCase("Down") && moving)
		{
			grant.getWalkDown().draw(playerX, playerY);
		}
		else if(orientation.equalsIgnoreCase("Up") && moving)
		{
			grant.getWalkUp().draw(playerX, playerY);
		}
		else if(orientation.equalsIgnoreCase("Left") && moving)
		{
			grant.getWalkLeft().draw(playerX, playerY);
		}
		else if(orientation.equalsIgnoreCase("Right") && moving)
		{
			grant.getWalkRight().draw(playerX, playerY);
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(numLives == 0)
			sbg.enterState(7);
		this.checkWorlds(sbg);
		Input input = gc.getInput();
		float dip = .1f*delta;
		if(input.isKeyDown(Input.KEY_DOWN) && playerY < 575)
		{
			orientation = "Down";
			moving = true;
			playerY += dip;
		}
		
		else if(input.isKeyDown(Input.KEY_UP) && playerY > 0)
		{
			orientation = "Up";
			moving = true;
			playerY -= dip;
		}
		
		else if(input.isKeyDown(Input.KEY_LEFT) && playerX > 0)
		{
			orientation = "Left";
			moving = true;
			playerX -= dip;
		}
		
		else if(input.isKeyDown(Input.KEY_RIGHT) && playerX < 775)
		{
			orientation = "Right";
			moving = true;
			playerX += dip;
		}
		else
			moving = false;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

	private void checkWorlds(StateBasedGame sbg)
	{
		if((playerX > 620 && playerX < 650) && (playerY > 250 && playerY < 300) && !classicBeaten)
		{
			sbg.enterState(2, new EmptyTransition(), new RotateTransition());
		}
		if((playerX > 540 && playerX < 580) && (playerY > 380 && playerY < 420) && !zeldaBeaten)
		{
			sbg.enterState(3, new EmptyTransition(), new RotateTransition());
		}
		if((playerX > 215 && playerX < 265) && (playerY > 380 && playerY < 430) && !countryBeaten)
		{
			sbg.enterState(4, new EmptyTransition(), new RotateTransition());
		}
		if((playerX > 165 && playerX < 205) && (playerY > 270 && playerY < 310) && !kirbyBeaten)
		{
			sbg.enterState(5, new EmptyTransition(), new RotateTransition());
		}
		if((playerX > 370 && playerX < 420) && (playerY > 460 && playerY < 500) && !metroidBeaten)
		{
			sbg.enterState(6, new EmptyTransition(), new RotateTransition());
		}
		if((playerX > 380 && playerX < 420) && (playerY > 75 && playerY < 115) && worldsBeaten == 5)
		{
			sbg.enterState(7, new EmptyTransition(), new RotateTransition());
		}
	}
}
