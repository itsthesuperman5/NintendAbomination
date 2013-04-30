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
public class vortex {
	private SpriteSheet sheet;
	private Image[] image;
	private Animation swirl;
	
	public vortex() throws SlickException
	{
		sheet = new SpriteSheet("res/vortex.png", 64, 64);
		image = new Image[15];
		image[0] = sheet.getSprite(0, 0);
		image[1] = sheet.getSprite(1, 0);
		image[2] = sheet.getSprite(2, 0);
		image[3] = sheet.getSprite(3, 0);
		image[4] = sheet.getSprite(4, 0);
		image[5] = sheet.getSprite(0, 1);
		image[6] = sheet.getSprite(1, 1);
		image[7] = sheet.getSprite(2, 1);
		image[8] = sheet.getSprite(3, 1);
		image[9] = sheet.getSprite(4, 1);
		image[10] = sheet.getSprite(0, 2);
		image[11] = sheet.getSprite(1, 2);
		image[12] = sheet.getSprite(2, 2);
		image[13] = sheet.getSprite(3, 2);
		image[14] = sheet.getSprite(4, 2);
		swirl = new Animation(image, 100);
	}
	
	public Animation getSwirl()
	{
		return swirl;
	}
}
