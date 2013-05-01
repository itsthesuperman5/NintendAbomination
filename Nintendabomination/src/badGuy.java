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
		
		if(name.equalsIgnoreCase("beaver"))
		{
			type = 4;
			sheet = new SpriteSheet("res/DK Res/dkfrog.png", 40, 35);
			right = sheet.getSprite(0, 0);
			left = right.getFlippedCopy(true, false);
			jump = null;
			rightSteps = new Image[6];
			rightSteps[0] = right.getScaledCopy(2);
			rightSteps[1] = sheet.getSprite(1, 0).getScaledCopy(2);
			rightSteps[2] = sheet.getSprite(2, 0).getScaledCopy(2);
			rightSteps[3] = sheet.getSprite(3, 0).getScaledCopy(2);
			rightSteps[4] = sheet.getSprite(4, 0).getScaledCopy(2);
			rightSteps[5] = sheet.getSprite(5, 0).getScaledCopy(2);
			leftSteps = new Image[6];
			leftSteps[0] = rightSteps[0].getFlippedCopy(true, false);
			leftSteps[1] = rightSteps[1].getFlippedCopy(true, false);
			leftSteps[2] = rightSteps[2].getFlippedCopy(true, false);
			leftSteps[3] = rightSteps[3].getFlippedCopy(true, false);
			leftSteps[4] = rightSteps[4].getFlippedCopy(true, false);
			leftSteps[5] = rightSteps[5].getFlippedCopy(true, false);
			attackRightSteps = null;
			attackLeftSteps = null;
			moveLeft = new Animation(leftSteps, 200);
			moveRight = new Animation(rightSteps, 200);
			attackRight = null;
			attackLeft = null;
		}
		if(name.equalsIgnoreCase("vulture"))
		{
			type = 4;
			sheet = new SpriteSheet("res/DK Res/dkvulture.png", 65, 65);
			right = sheet.getSprite(0, 0);
			left = right.getFlippedCopy(true, false);
			jump = null;
			rightSteps = new Image[10];
			rightSteps[0] = right;
			rightSteps[1] = sheet.getSprite(1, 0);
			rightSteps[2] = sheet.getSprite(2, 0);
			rightSteps[3] = sheet.getSprite(3, 0);
			rightSteps[4] = sheet.getSprite(4, 0);
			rightSteps[5] = sheet.getSprite(5, 0);
			rightSteps[6] = sheet.getSprite(6, 0);
			rightSteps[7] = sheet.getSprite(7, 0);
			rightSteps[8] = sheet.getSprite(8, 0);
			rightSteps[9] = sheet.getSprite(9, 0);
			leftSteps = new Image[10];
			leftSteps[0] = rightSteps[0].getFlippedCopy(true, false);
			leftSteps[1] = rightSteps[1].getFlippedCopy(true, false);
			leftSteps[2] = rightSteps[2].getFlippedCopy(true, false);
			leftSteps[3] = rightSteps[3].getFlippedCopy(true, false);
			leftSteps[4] = rightSteps[4].getFlippedCopy(true, false);
			leftSteps[5] = rightSteps[5].getFlippedCopy(true, false);
			leftSteps[6] = rightSteps[6].getFlippedCopy(true, false);
			leftSteps[7] = rightSteps[7].getFlippedCopy(true, false);
			leftSteps[8] = rightSteps[8].getFlippedCopy(true, false);
			leftSteps[9] = rightSteps[9].getFlippedCopy(true, false);
			attackRightSteps = null;
			attackLeftSteps = null;
			moveLeft = new Animation(leftSteps, 100);
			moveRight = new Animation(rightSteps, 100);
			attackRight = null;
			attackLeft = null;
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
