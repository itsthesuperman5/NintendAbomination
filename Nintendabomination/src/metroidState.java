import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class metroidState extends BasicGameState{
	
	private metroidBomb bombs;
	private float bombX, bombY;
	private int stateID;
	private TiledMap map;
	private Rectangle playerBound;
	private Rectangle[][] rect;
	private float pX, pY, ridleyX, ridleyY;
	private float playerVel, playerLift, enemyVel;
	private grantSide player;
	private boolean[][] ground;
	private Shape[] shape;
	private int shapeCount;
	private int mapX, mapY;
	private int ridleyHit;
	private SpriteSheet ridleySheet;
	private boolean jumping, right, left, crouched, moving;
	private boolean bgMusic, playerDead, ridleyDead, hitRidley;
	private boolean victoryPlayed, bombPlaced, bombExplode, ridleyAttacking;
	private Rectangle ridleyBound;
	private Image[] ridleyWalkImages;
	private Image[] ridleyAttackImages;
	private Image[] ridleyWalkRightImages;
	private Image[] ridleyAttackRightImages;
	private SpriteSheet fireballs;
	private Image fireballLeft, fireballDown;
	private Rectangle fireballBound;
	private Animation ridleyWalkAnim;
	private Animation ridleyAttackAnim;
	private Animation ridleyWalkRightAnim;
	private Animation ridleyAttackRightAnim;
	private Music background;
	private Sound jump, victory, death;
	private int bombTimer, delay, newDelay, thirdDelay;
	private Image ridleysAttack;
	private Random rand;
	private boolean deathPlayed;
	private boolean livesDecreased;

	public metroidState(int stateID)
	{
		this.stateID = stateID;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		victoryPlayed = false;
		deathPlayed = false;
		livesDecreased = false;
		playerDead = false;
		moving = false;
		crouched = false;
		jumping = false;
		bombPlaced = false;
		bombExplode = false;
		ridleyDead = false;
		ridleyAttacking = false;
		hitRidley = false;
		mapX = 0;
		mapY = 0;
		ridleyHit = 0;
		delay = rand.nextInt(3000);
		playerBound.setX(pX);
		playerBound.setY(pY);
		ridleyBound.setX(ridleyX);
		ridleyBound.setY(ridleyY);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		rand = new Random();
		livesDecreased = false;
		death = new Sound("res/Metroid Res/metroidDeath.wav");
		victory = new Sound("res/Metroid Res/metroidVictory.wav");
		ridleySheet = new SpriteSheet("res/Metroid Res/ripley.png", 80, 130);
		fireballs = new SpriteSheet("res/Metroid Res/FireBall.png",32,32);
		fireballLeft = fireballs.getSprite(1, 1).getScaledCopy(2);
		fireballDown = fireballs.getSprite(1, 0).getScaledCopy(2);
		ridleyWalkImages = new Image[3];
		ridleyAttackImages = new Image[3];
		ridleyWalkRightImages = new Image[3];
		ridleyAttackRightImages = new Image[3];
		ridleyWalkImages[0] = ridleySheet.getSprite(0, 0).getScaledCopy(2);
		ridleyWalkImages[1] = ridleySheet.getSprite(1, 0).getScaledCopy(2);
		ridleyWalkImages[2] = ridleySheet.getSprite(2, 0).getScaledCopy(2);
		ridleyAttackImages[0] = ridleySheet.getSprite(0, 0).getScaledCopy(2);
		ridleyAttackImages[1] = ridleySheet.getSprite(3, 0).getScaledCopy(2);
		ridleyAttackImages[2] = ridleySheet.getSprite(6, 0).getScaledCopy(2);
		ridleysAttack = ridleySheet.getSprite(8, 0).getScaledCopy(2);
		ridleyWalkRightImages[0] = ridleyWalkImages[0].getFlippedCopy(true, false);
		ridleyWalkRightImages[1] = ridleyWalkImages[1].getFlippedCopy(true, false);
		ridleyWalkRightImages[2] = ridleyWalkImages[2].getFlippedCopy(true, false);
		ridleyAttackRightImages[0] = ridleyAttackImages[0].getFlippedCopy(true, false);
		ridleyAttackRightImages[1] = ridleyAttackImages[1].getFlippedCopy(true, false);
		ridleyAttackRightImages[2] = ridleyAttackImages[2].getFlippedCopy(true, false);
		ridleyWalkAnim = new Animation(ridleyWalkImages, 600);
		ridleyWalkAnim.setPingPong(true);
		ridleyAttackAnim = new Animation(ridleyAttackImages, 200);
		ridleyAttackAnim.setPingPong(true);
		ridleyWalkRightAnim = new Animation(ridleyWalkRightImages, 600);
		ridleyWalkRightAnim.setPingPong(true);
		ridleyAttackRightAnim = new Animation(ridleyAttackRightImages, 200);
		ridleyAttackRightAnim.setPingPong(true);
		bombs = new metroidBomb();
		bombExplode = false;
		ridleyDead = false;
		ridleyAttacking = false;
		bombPlaced = false;
		hitRidley = false;
		bgMusic = false;
		background = new Music("res/Metroid Res/metroidBackground.wav");
		jump = new Sound("res/Metroid Res/jump.wav");
		playerDead = false;
		victoryPlayed = false;
		map = new TiledMap("res/Metroid Res/metroidMap.tmx");
		player = new grantSide();
		jumping = false; right = true; left = false; crouched = false; moving = false;
		pX = 100;
		pY = 300;
		ridleyX = 550;
		ridleyY = 270;
		mapX = 0;
		mapY = 0;
		ridleyHit = 0;
		playerVel = 0.1f;
		enemyVel = 0.05f;
		shapeCount = 0;
		gc.setMinimumLogicUpdateInterval(20);
		playerBound = new Rectangle(pX, pY, 10, 40);
		ground = new boolean[map.getWidth()][map.getHeight()];
		rect = new Rectangle[map.getWidth()][map.getHeight()];
		shape = new Shape[10000];
		for (int x=0;x<map.getWidth();x++) 
		{
			for (int y=0;y<map.getHeight();y++)
			{
				int tileID = map.getTileId(x, y, 1);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value))
				{
					ground[x][y] = true;
					rect[x][y] = new Rectangle((float)(x*32), (float)(y*32), map.getTileWidth(), map.getTileHeight());
					shape[shapeCount] = rect[x][y];
					shapeCount++;
				}
			}
		}
		ridleyBound = new Rectangle(ridleyX, ridleyY, ridleyWalkImages[0].getWidth(), ridleyWalkImages[0].getHeight());
		fireballBound = new Rectangle(ridleyX, ridleyY, fireballLeft.getWidth(), fireballLeft.getHeight());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		map.render(mapX, mapY, 0);
		if(!ridleyDead && !playerDead)
		{
			
			if(!ridleyAttacking)
			{
				ridleyWalkAnim.draw(ridleyBound.getX()+mapX, ridleyBound.getY()+mapY-10);
			}
			else
			{
				ridleysAttack.draw(ridleyBound.getX()+mapX, ridleyBound.getY()+mapY);
				fireballLeft.draw(fireballBound.getX(), fireballBound.getY());
			}
		}
		if(bombPlaced)
		{
			bombs.getBomb().draw(bombX+mapX, bombY+mapY);
		}
		if(bombExplode)
		{
			bombs.getExplosion().draw(bombX+mapX-30, bombY+mapY-80);
		}
		if(jumping && right)
		{
			player.getJumpRight().draw(playerBound.getX()-32+mapX, playerBound.getY()-26+mapY);
		}
		else if(jumping && left)
		{
			player.getJumpLeft().draw(playerBound.getX()-32+mapX, playerBound.getY()-26+mapY);
		}
		else if(crouched && right)
		{
			player.getCrouchRight().draw(playerBound.getX()-32+mapX, playerBound.getY()-26+mapY);
		}
		else if(crouched && left)
		{
			player.getCrouchLeft().draw(playerBound.getX()-32+mapX, playerBound.getY()-26+mapY);
		}
		else if(!moving && right)
		{
			player.getStandRight().draw(playerBound.getX()-32+mapX, playerBound.getY()-26+mapY);
		}
		else if(!moving && left)
		{
			player.getStandLeft().draw(playerBound.getX()-32+mapX, playerBound.getY()-26+mapY);
		}
		else if(moving && right)
		{
			player.getRunRight().draw((playerBound.getX()-32)+mapX, playerBound.getY()-26+mapY);
		}
		else if(moving && left)
		{
			player.getRunLeft().draw((playerBound.getX()-32)+mapX, playerBound.getY()-26+mapY);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub]
		if(!bgMusic)
		{
			background.loop();
			background.setVolume(0.1f);
			bgMusic = true;
		}
		float hip = playerVel * delta;
		float pip = playerLift * delta;
		float erp = enemyVel * delta;
		if(playerBound.intersects(fireballBound) && !hitRidley)
			playerDead = true;
		if(!ridleyDead){
		if(ridleyHit >= 3)
			ridleyDead = true;
		if(ridleyAttacking)
		{
			fireballBound.setX(fireballBound.getX()-0.4f*delta);
			fireballBound.setY(fireballBound.getY()+0.5f*delta);
			if(fireballBound.getX() < 0 || fireballBound.getY() > 600)
			{
				fireballBound.setX(ridleyX);
				fireballBound.setY(ridleyY);
			}
		}
		delay -= delta;
		if(delay <= 0)
		{
			ridleyAttacking = true;
			newDelay -= delta;
			if(newDelay <= 0)
			{
				newDelay = 2000;
				delay = rand.nextInt(3000);
				ridleyAttacking = false;
			}
		}
		else
		{
			fireballBound.setX(ridleyX);
			fireballBound.setY(ridleyY);
		}
			if(hitRidley)
			{
				playerBound.setX(playerBound.getX() - 0.2f * delta);
				thirdDelay -= delta;
				if(thirdDelay <= 0)
				{
					ridleyHit++;
					hitRidley = false;
				}
			}
		
		if(bombExplode)
		{
			bombTimer -= delta;
			if(bombTimer <= 0)
			{
				bombExplode = false;
			}
		}
		if(bombPlaced)
		{
			bombTimer -= delta;
			if(bombTimer <= 0)
			{
				bombExplode = true;
				bombs.getExplodeSound().play();
				bombPlaced = false;
				bombTimer = 1000;
			}
		}
		if(ridleyBound.contains(bombX, bombY) && bombExplode && !hitRidley)
		{
			hitRidley = true;
			thirdDelay = 2000;
		}
		if(playerBound.getX()+5 >= bombX && playerBound.getX() < bombX + 50 && bombExplode
						&& playerBound.getY()+25 >= bombY-10 && playerBound.getY() < bombY + 50)
			playerDead = true;
		if(playerDead)
		{
			background.stop();
			bgMusic = false;
			if(!deathPlayed)
			{
				death.play();
				deathPlayed = true;
			}
			playerBound.setY(playerBound.getY()+0.1f*delta);
			if(!livesDecreased){
				Hub.numLives--;
				livesDecreased = true;
			}
			if(!death.playing())
				sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		if(!jumping && !collides(playerBound, shape))
		{
			playerBound.setY(playerBound.getY()+0.3f*delta);
		}
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_DOWN))
		{
			moving = false;
			crouched = true;
		}
		else
			crouched = false;
		if(!jumping && input.isKeyPressed(Input.KEY_B) && !bombPlaced && !bombExplode)
		{
			bombPlaced = true;
			bombX = playerBound.getX();
			bombY = playerBound.getCenterY()-5;
			bombTimer = 3000;
			bombs.getBombSound().play();
		}
		if(jumping && playerLift >= 0)
		{
			playerBound.setY(playerBound.getY()-pip);
			playerLift -= 0.03f;
		}
		else if(jumping && playerLift < 0)
		{
			if((int)(((playerBound.getY()+player.getStandRight().getHeight())-(pip))/32) < 19){
			if (ground[(int)playerBound.getX()/32][((int)(((playerBound.getY()+player.getStandRight().getHeight())-(pip))/32))] != true)
			{
				playerBound.setY(playerBound.getY()-pip);
				playerLift -= 0.03f;		
			}
			else
			{
				jumping = false;		
			}
			}
			else
				playerDead = true;
		}
		if(input.isKeyPressed(Input.KEY_SPACE) && !jumping)
		{
			jumping = true;
			playerLift = 0.6f;
			jump.play();
		}
		if(input.isKeyDown(Input.KEY_RIGHT) && !crouched && !lookAhead(playerBound.getX()+32, playerBound.getY(), shape))
		{
			left = false;
			right = true;
			moving = true;
			playerBound.setX(playerBound.getX()+hip);
		}
		else if(input.isKeyDown(Input.KEY_LEFT) && playerBound.getX()+mapX > mapX && !crouched && !lookAhead(playerBound.getX()-32, playerBound.getY(), shape))
		{
			right = false;
			left = true;
			moving = true;
			playerBound.setX(playerBound.getX()-hip);
		}
		else
			moving = false;
		}
		else
		{
			background.stop();
			if(!victoryPlayed)
			{
				victory.play();
				victoryPlayed = true;
			}
			else
			{
				if(!victory.playing())
				{
					Hub.metroidBeaten = true;
					Hub.worldsBeaten++;
					sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
				}
					
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
	
	public boolean collides(Rectangle a, Shape[] b)
	{
		boolean hit = false;
		for(int i=0; i < shapeCount; i++)
		{
			if(a.intersects(b[i]))
				hit = true;
		}
		return hit;
	}
	
	public boolean lookAhead(float x, float y, Shape[] b)
	{
		boolean hit = false;
		for(int i=0; i < shapeCount; i++)
		{
			if(b[i].contains(x, y))
				hit = true;
		}
		return hit;
	}
}
