import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class grant {
	//character's sprite sheet
	private SpriteSheet sheet;
	//the images for when he's not moving
	private Image standUp, standLeft, standRight, standDown;
	//the animations for him walking
	private Animation walkingUp, walkingDown, walkingLeft, walkingRight;
	//the arrays to hold the walking images
	private Image[] upArray, downArray, leftArray, rightArray;
	
	/**
	 * Constructor
	 * @throws SlickException 
	 */
	public grant() throws SlickException
	{
		sheet = new SpriteSheet("res/alangrantTop1.png", 40, 40);
		standUp = sheet.getSubImage(0, 0);
		standDown = sheet.getSubImage(0, 1);
		standLeft = sheet.getSubImage(0, 2);
		standRight = standLeft.getFlippedCopy(true, false);
		upArray = new Image[8];
		downArray = new Image[8];
		leftArray = new Image[8];
		rightArray = new Image[8];
		upArray[0] = sheet.getSprite(0, 0);
		upArray[1] = sheet.getSprite(1, 0);
		upArray[2] = sheet.getSprite(2, 0);
		upArray[3] = sheet.getSprite(3, 0);
		upArray[4] = sheet.getSprite(4, 0);
		upArray[5] = sheet.getSprite(5, 0);
		upArray[6] = sheet.getSprite(6, 0);
		upArray[7] = sheet.getSprite(7, 0);
		downArray[0] = sheet.getSprite(0, 1);
		downArray[1] = sheet.getSprite(1, 1);
		downArray[2] = sheet.getSprite(2, 1);
		downArray[3] = sheet.getSprite(3, 1);
		downArray[4] = sheet.getSprite(4, 1);
		downArray[5] = sheet.getSprite(5, 1);
		downArray[6] = sheet.getSprite(6, 1);
		downArray[7] = sheet.getSprite(7, 1);
		leftArray[0] = sheet.getSprite(0, 2);
		leftArray[1] = sheet.getSprite(1, 2);
		leftArray[2] = sheet.getSprite(2, 2);
		leftArray[3] = sheet.getSprite(3, 2);
		leftArray[4] = sheet.getSprite(4, 2);
		leftArray[5] = sheet.getSprite(5, 2);
		leftArray[6] = sheet.getSprite(6, 2);
		leftArray[7] = sheet.getSprite(7, 2);
		rightArray[0] = leftArray[0].getFlippedCopy(true, false);
		rightArray[1] = leftArray[1].getFlippedCopy(true, false);
		rightArray[2] = leftArray[2].getFlippedCopy(true, false);
		rightArray[3] = leftArray[3].getFlippedCopy(true, false);
		rightArray[4] = leftArray[4].getFlippedCopy(true, false);
		rightArray[5] = leftArray[5].getFlippedCopy(true, false);
		rightArray[6] = leftArray[6].getFlippedCopy(true, false);
		rightArray[7] = leftArray[7].getFlippedCopy(true, false);
		walkingUp = new Animation(upArray, 200);
		walkingDown = new Animation(downArray, 200);
		walkingLeft = new Animation(leftArray, 200);
		walkingRight = new Animation(rightArray, 200);
	}
	
	public Image getStandUp()
	{
		return standUp;
	}
	
	public Image getStandDown()
	{
		return standDown;
	}
	
	public Image getStandLeft()
	{
		return standLeft;
	}
	
	public Image getStandRight()
	{
		return standRight;
	}
	
	public Animation getWalkUp()
	{
		return walkingUp;
	}
	
	public Animation getWalkDown()
	{
		return walkingDown;
	}
	
	public Animation getWalkLeft()
	{
		return walkingLeft;
	}
	
	public Animation getWalkRight()
	{
		return walkingRight;
	}
}
