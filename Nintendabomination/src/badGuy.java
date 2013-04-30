import java.util.Random;

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
public class badGuy 
{
	private SpriteSheet sheet;
	private Image left, right, jump;
	private Animation moveLeft, moveRight, attackRight, attackLeft;
	private Image[] leftSteps, rightSteps, attackLeftSteps, attackRightSteps;
	private int type;
	private float delay;
	private Random rand = new Random();
	private String direction;
	private boolean alive, attacking;
	
	public badGuy(String name) throws SlickException
	{
		alive = true;
		attacking = false;
		delay = rand.nextInt(5000)+1;
		int x = rand.nextInt(2)+1;
		if(x == 1)
			direction = "left";
		else if(x == 2)
			direction = "right";
		if(name.equalsIgnoreCase("redFlame"))
		{
			type = 1;
			sheet = new SpriteSheet("res/ArcadeEnemies.png", 25, 25);
			left = sheet.getSprite(0, 0).getScaledCopy(2);
			right = left.getFlippedCopy(true, false);
			jump = null;
			leftSteps = null;
			rightSteps = null;
			attackLeftSteps = null;
			moveLeft = null;
			moveRight = null;
			attackRight = null;
		}
		if(name.equalsIgnoreCase("blueFlame"))
		{
			type = 2;
			sheet = new SpriteSheet("res/ArcadeEnemies.png", 25, 25);
			left = sheet.getSprite(1, 0).getScaledCopy(2);
			right = left.getFlippedCopy(true, false);
			jump = null;
			leftSteps = null;
			rightSteps = null;
			attackLeftSteps = null;
			moveLeft = null;
			moveRight = null;
			attackRight = null;
		}
		if(name.equalsIgnoreCase("stalfos"))
		{
			type = 3;
			sheet = new SpriteSheet("res/Zelda Res/zeldaEnemy.png", 35, 35);
			right = sheet.getSprite(0, 0);
			left = right.getFlippedCopy(true, false);
			jump = null;
			rightSteps = new Image[2];
			rightSteps[0] = right;
			rightSteps[1] = sheet.getSprite(1, 0);
			leftSteps = new Image[2];
			leftSteps[0] = rightSteps[0].getFlippedCopy(true, false);
			leftSteps[1] = rightSteps[1].getFlippedCopy(true, false);
			attackRightSteps = new Image[2];
			attackRightSteps[0] = sheet.getSprite(2, 0);
			attackRightSteps[1] = sheet.getSprite(4, 0);
			attackLeftSteps = new Image[2];
			attackLeftSteps[0] = attackRightSteps[0].getFlippedCopy(true, false);
			attackLeftSteps[1] = attackRightSteps[1].getFlippedCopy(true, false);
			moveLeft = new Animation(leftSteps, 300);
			moveRight = new Animation(rightSteps, 300);
			attackRight = new Animation(attackRightSteps, 500);
			attackLeft = new Animation(attackLeftSteps, 500);
		}
	}
	
	public Image getLeft()
	{
		return left;
	}
	
	public Image getRight()
	{
		return right;
	}
	
	public Animation getMoveLeft()
	{
		return moveLeft;
	}
	
	public Animation getMoveRight()
	{
		return moveRight;
	}
	
	public boolean getAttacking()
	{
		return attacking;
	}
	
	public void setAttacking(boolean t)
	{
		attacking = t;
	}
	
	public Animation getAttackRight()
	{
		return attackRight;
	}
	
	public Animation getAttackLeft()
	{
		return attackLeft;
	}
	
	public boolean getAlive()
	{
		return alive;
	}
	
	public void setAlive(boolean t)
	{
		alive = t;
	}
	
	public void newDelay()
	{
		delay = rand.nextInt(5000)+1;
	}
	
	public void setDelay(float num)
	{
		delay = num;
	}
	
	public float getDelay()
	{
		return delay;
	}
	
	public void setDirection(String dir)
	{
		direction = dir;
	}
	
	public void changeDirection()
	{
		if(direction.equalsIgnoreCase("right"))
			direction = "left";
		else if(direction.equalsIgnoreCase("left"))
			direction = "right";
	}
	
	public String getDirection()
	{
		return direction;
	}
}
