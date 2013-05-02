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
public class metroidBomb {
	private SpriteSheet sheet, explosion;
	private Image[] bombImages;
	private Image[] explosionImages;
	private Animation setBomb;
	private Animation explodeBomb;
	private Sound placeBomb;
	private Sound blowBomb;
	
	public metroidBomb() throws SlickException
	{
		sheet = new SpriteSheet("res/Metroid Res/bombSheet.png", 10, 10);
		explosion = new SpriteSheet("res/Metroid Res/explosionSheet.png", 40, 40);
		bombImages = new Image[4];
		bombImages[0] = sheet.getSprite(0, 0).getScaledCopy(2);
		bombImages[1] = sheet.getSprite(1, 0).getScaledCopy(2);
		bombImages[2] = sheet.getSprite(2, 0).getScaledCopy(2);
		bombImages[3] = sheet.getSprite(3, 0).getScaledCopy(2);
		explosionImages = new Image[5];
		explosionImages[0] = explosion.getSprite(0, 0).getScaledCopy(3);
		explosionImages[1] = explosion.getSprite(1, 0).getScaledCopy(3);
		explosionImages[2] = explosion.getSprite(2, 0).getScaledCopy(3);
		explosionImages[3] = explosion.getSprite(3, 0).getScaledCopy(3);
		explosionImages[4] = explosion.getSprite(4, 0).getScaledCopy(3);
		setBomb = new Animation(bombImages, 800);
		explodeBomb = new Animation(explosionImages, 50);
		placeBomb = new Sound("res/Metroid Res/bomb_set.wav");
		blowBomb = new Sound("res/Metroid Res/bomb_explode.wav");
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
	
	public Animation getExplosion()
	{
		return explodeBomb;
	}
}
