import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;
/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class grantSide {

	private SpriteSheet sheet;
	private Image standingRight;
	private Image standingLeft;
	private Image jumpRight;
	private Image jumpLeft;
	private Image crouchRight;
	private Image crouchLeft;
	private Image onLadder;
	private Image[] runRight, runLeft, climbs;
	private Animation runningRight, runningLeft, climbing;
	
	public grantSide() throws SlickException
	{
		sheet = new SpriteSheet("res/alangrantSide1.png", 64, 64);
		standingRight = sheet.getSprite(0, 0);
		standingLeft = standingRight.getFlippedCopy(true, false);
		jumpRight = sheet.getSprite(1, 1);
		jumpLeft = jumpRight.getFlippedCopy(true, false);
		crouchRight = sheet.getSprite(7, 1);
		crouchLeft = crouchRight.getFlippedCopy(true, false);
		onLadder = sheet.getSprite(2, 1);
		runRight = new Image[8];
		runLeft = new Image[8];
		climbs = new Image[4];
		runRight[0] = sheet.getSprite(1, 0);
		runLeft[0] = runRight[0].getFlippedCopy(true, false);
		runRight[1] = sheet.getSprite(2, 0);
		runLeft[1] = runRight[1].getFlippedCopy(true, false);
		runRight[2] = sheet.getSprite(3, 0);
		runLeft[2] = runRight[2].getFlippedCopy(true, false);
		runRight[3] = sheet.getSprite(4, 0);
		runLeft[3] = runRight[3].getFlippedCopy(true, false);
		runRight[4] = sheet.getSprite(5, 0);
		runLeft[4] = runRight[4].getFlippedCopy(true, false);
		runRight[5] = sheet.getSprite(6, 0);
		runLeft[5] = runRight[5].getFlippedCopy(true, false);
		runRight[6] = sheet.getSprite(7, 0);
		runLeft[6] = runRight[6].getFlippedCopy(true, false);
		runRight[7] = sheet.getSprite(0, 1);
		runLeft[7] = runRight[7].getFlippedCopy(true, false);
		climbs[0] = sheet.getSprite(3, 1);
		climbs[1] = sheet.getSprite(4, 1);
		climbs[2] = sheet.getSprite(5, 1);
		climbs[3] = sheet.getSprite(6, 1);
		runningRight = new Animation(runRight, 200);
		runningLeft = new Animation(runLeft, 200);
		climbing = new Animation(climbs, 200);
		
	}
	
	public Image getStandRight()
	{
		return standingRight;
	}
	
	public Image getStandLeft()
	{
		return standingLeft;
	}
	
	public Image getJumpRight()
	{
		return jumpRight;
	}
	
	public Image getJumpLeft()
	{
		return jumpLeft;
	}
	
	public Image getCrouchRight()
	{
		return crouchRight;
	}
	
	public Image getCrouchLeft()
	{
		return crouchLeft;
	}
	
	public Image getLadder()
	{
		return onLadder;
	}
	
	public Animation getRunRight()
	{
		return runningRight;
	}
	
	public Animation getRunLeft()
	{
		return runningLeft;
	}
	
	public Animation getClimbing()
	{
		return climbing;
	}
}
