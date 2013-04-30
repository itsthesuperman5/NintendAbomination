import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class bomb {
	private SpriteSheet sheet;
	private Image explosion;
	private Image[] bombImages;
	private Animation setBomb;
	private Sound placeBomb;
	private Sound blowBomb;
	
	public bomb() throws SlickException
	{
		sheet = new SpriteSheet("res/Zelda Res/bombs.png", 20, 20);
		explosion = new Image("res/Zelda Res/explosion.png");
		bombImages = new Image[3];
		bombImages[0] = sheet.getSprite(0, 0);
		bombImages[1] = sheet.getSprite(1, 0);
		bombImages[2] = sheet.getSprite(2, 0);
		setBomb = new Animation(bombImages, 300);
		placeBomb = new Sound("res/Zelda Res/setBomb.wav");
		blowBomb = new Sound("res/Zelda Res/blowBomb.wav");
	}
	
	public Animation getBomb()
	{
		return setBomb;
	}
	
	public Sound getBombSound()
	{
		return placeBomb;
	}
	
	public Sound getExplodeSound()
	{
		return blowBomb;
	}
	
	public Image getExplosion()
	{
		return explosion.getScaledCopy(2);
	}

}
