import java.util.ArrayList;

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
public class dkState extends BasicGameState {
	
	private int stateID;
	private int hitCounter;
	private TiledMap map;
	private Rectangle playerBound;
	private Rectangle[][] rect;
	private float pX, pY, bx1, bx2, by1, by2, dkX, dkY;
	private float playerVel, playerLift, climbVel, enemyVel;
	private grantSide player;
	private boolean[][] ground;
	private boolean[][] ladder;
	private boolean[][] baddyGrid;
	private Shape[] shape;
	private int shapeCount;
	private int mapX, mapY;
	private SpriteSheet enemySheet;
	private SpriteSheet dkSheet;
	private boolean jumping, right, left, crouched, moving, climbing, barreling1, barreling2;
	private boolean dkIsHit, drawBarrel1, drawBarrel2, dkDead, bgMusic, finMusic, playerDead;
	private boolean deathPlayed;
	private ArrayList<badGuy> baddies;
	private ArrayList<Rectangle> baddyBound;
	private Image baddyImage;
	private Image baddyImageFlip;
	private Rectangle barrel1Bound;
	private Rectangle barrel2Bound;
	private Rectangle dkBound;
	private Image[] dkImages;
	private Image[] barrelImages;
	private Image dkHit, dkFall;
	private Image barrelSit, barrelSit1;
	private Animation dkAnim;
	private Animation barrelAnim;
	private Animation barrelAnim1;
	private int numBarrels;
	private Music background, finished, intro, death, playing;
	private Sound jump, breaks;
	private boolean livesDecreased, introPlayed;

	public dkState(int stateID)
	{
		this.stateID = stateID;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		deathPlayed = false;
		playerDead = false;
		livesDecreased = false;
		moving = false;
		crouched = false;
		jumping = false;
		climbing = false;
		playerBound.setX(pX);
		playerBound.setY(pY);
		this.setBaddies();
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		baddies.clear();
		baddyBound.clear();
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		baddies = new ArrayList<badGuy>();
		baddyBound = new ArrayList<Rectangle>();
		livesDecreased = false;
		bgMusic = false;
		introPlayed = false;
		finMusic = false;
		background = new Music("res/Classic Music/backmusic.wav");
		finished = new Music("res/Classic Music/finished.wav");
		intro = new Music("res/Classic Music/intro.wav");
		death = new Music("res/Classic Music/death.wav");
		jump = new Sound("res/Classic Music/jump.wav");
		breaks = new Sound("res/Classic Music/barrel.wav");
		playing = intro;
		numBarrels = 2;
		playerDead = false;
		deathPlayed = false;
		dkIsHit = false;
		dkDead = false;
		barreling1 = false;
		barreling2 = false;
		drawBarrel1 = true;
		drawBarrel2 = true;
		enemySheet = new SpriteSheet("res/ArcadeEnemies.png", 25, 25);
		dkSheet = new SpriteSheet("res/dkSprites.png", 50, 40);
		baddyImage = enemySheet.getSprite(0, 0);
		baddyImageFlip = baddyImage.getFlippedCopy(true, false);
		dkImages = new Image[3];
		barrelImages = new Image[4];
		dkImages[0] = dkSheet.getSprite(2, 0).getScaledCopy(2);
		dkImages[1] = dkSheet.getSprite(0, 0).getScaledCopy(2);
		dkImages[2] = dkSheet.getSprite(1, 0).getScaledCopy(2);
		dkHit = dkSheet.getSprite(0, 1).getScaledCopy(2);
		dkFall = dkSheet.getSprite(1, 1).getScaledCopy(2);
		barrelSit = enemySheet.getSprite(0, 1).getScaledCopy(2);
		barrelSit1 = barrelSit;
		barrelImages[0] = enemySheet.getSprite(1, 1).getScaledCopy(2);
		barrelImages[1] = enemySheet.getSprite(2, 1).getScaledCopy(2);
		barrelImages[2] = enemySheet.getSprite(3, 1).getScaledCopy(2);
		barrelImages[3] = enemySheet.getSprite(4, 1).getScaledCopy(2);
		dkAnim = new Animation(dkImages, 500);
		barrelAnim = new Animation(barrelImages, 300);
		barrelAnim1 = barrelAnim;
		map = new TiledMap("res/dkLevel.tmx");
		player = new grantSide();
		jumping = false; right = true; left = false; crouched = false; moving = false; climbing = false;
		pX = 100;
		pY = 500;
		bx1 = 115;
		by1 = 60;
		bx2 = 660;
		by2 = 60;
		dkX = 360;
		dkY = 20;
		mapX = 0;
		mapY = 0;
		playerVel = 0.1f;
		climbVel = 0.1f;
		enemyVel = 0.05f;
		shapeCount = 0;
		gc.setMinimumLogicUpdateInterval(20);
		playerBound = new Rectangle(pX, pY, 10, 40);
		barrel1Bound = new Rectangle(bx1, by1, 30, 30);
		barrel2Bound = new Rectangle(bx2, by2, 30, 30);
		dkBound = new Rectangle(dkX, dkY, 60, 60);
		ground = new boolean[map.getWidth()][map.getHeight()];
		ladder = new boolean[map.getWidth()][map.getHeight()];
		rect = new Rectangle[map.getWidth()][map.getHeight()];
		baddyGrid = new boolean[map.getWidth()][map.getHeight()];
		shape = new Shape[10000];
		for (int x=0;x<map.getWidth();x++) 
		{
			for (int y=0;y<map.getHeight();y++)
			{
				int tileID = map.getTileId(x, y, 1);
				String value = map.getTileProperty(tileID, "blocked", "false");
				String value1 = map.getTileProperty(tileID, "ladder", "false");
				String value2 = map.getTileProperty(tileID, "baddy", "false");
				if ("true".equals(value))
				{
					ground[x][y] = true;
					rect[x][y] = new Rectangle((float)(x*32), (float)(y*32), map.getTileWidth(), map.getTileHeight());
					shape[shapeCount] = rect[x][y];
					shapeCount++;
				}
				if("true".equals(value1))
				{
					ladder[x][y] = true;
				}
				if("true".equals(value2))
				{
					baddyGrid[x][y] = true;
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		map.render(0, 0, 0);
		if(!baddies.isEmpty() && !dkDead && !playerDead)
		{
			for(int i = 0; i < baddies.size(); i++)
			{
				baddies.get(i).getLeft().draw(baddyBound.get(i).getX()+mapX-20, baddyBound.get(i).getY()+mapY-10);
			}
		}
		if(dkDead && !playerDead)
			dkFall.draw(dkBound.getX(), dkBound.getY());
		else if(!dkIsHit && !playerDead)
			dkAnim.draw(dkBound.getX(), dkBound.getY());
		else if(dkIsHit && !playerDead){
			dkHit.draw(dkBound.getX(), dkBound.getY());
		}
		if(drawBarrel1)
		{
		if(!barreling1)
			barrelSit.draw(barrel1Bound.getX(), barrel1Bound.getY());
		else
			barrelAnim.draw(barrel1Bound.getX(), barrel1Bound.getY());
		}
		if(drawBarrel2)
		{
		if(!barreling2)
			barrelSit1.draw(barrel2Bound.getX(), barrel2Bound.getY());
		else
			barrelAnim1.draw(barrel2Bound.getX(), barrel2Bound.getY());
		}
		if(climbing && moving)
		{
			player.getClimbing().draw(playerBound.getX()-32+mapX, playerBound.getY()-26+mapY);
		}
		else if(climbing)
		{
			player.getLadder().draw(playerBound.getX()-32+mapX, playerBound.getY()-26+mapY);
		}
		else if(jumping && right)
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
		// TODO Auto-generated method stub
		System.out.println(intro.playing());
		if(!introPlayed)
		{
			intro.play();
			introPlayed = true;
		}
		if(intro.playing() == false && !death.playing() && !bgMusic)
		{
			background.loop();	
			bgMusic = true;
		}
		float hip = playerVel * delta;
		float pip = playerLift * delta;
		float cip = climbVel * delta;
		float erp = enemyVel * delta;
		float eip = 0.2f * delta;
		float fip = 0.3f * delta;
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
		if(!baddies.isEmpty() && !intro.playing())
		{
			for(int i = 0; i < baddies.size(); i++)
			{
				if(baddyBound.get(i).intersects(playerBound))
				{
					playerDead = true;
				}
				if(baddyBound.get(i).getX() > 780)
					baddyBound.get(i).setX(-5);
				if(baddyBound.get(i).getX() < -5)
					baddyBound.get(i).setX(780);
				baddies.get(i).setDelay(baddies.get(i).getDelay() - delta);
				if(baddies.get(i).getDelay() <= 0)
				{
					baddies.get(i).newDelay();
					baddies.get(i).changeDirection();
				}
				if(baddies.get(i).getDirection().equalsIgnoreCase("left"))
					baddyBound.get(i).setX(baddyBound.get(i).getX() - erp);
				else if(baddies.get(i).getDirection().equalsIgnoreCase("right"))
					baddyBound.get(i).setX(baddyBound.get(i).getX() + erp);
			}
		}
		if(dkDead)
		{
			
			dkBound.setY(dkBound.getY() + fip);
			if(dkBound.getY() > 550 && finished.playing() == false)
			{
				Hub.classicBeaten = true;
				Hub.worldsBeaten++;
				sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
			}
		}
		if(dkIsHit)
		{
			if(numBarrels == 0)
			{
				background.stop();
				if(!finMusic)
				{
					finished.play();
					finMusic = true;
				}
			}
			hitCounter = hitCounter + 1 * delta;
			if(hitCounter >= 3000)
			{
				dkIsHit = false;
				if(numBarrels == 0)
				{
					dkDead = true;
				}
			}
		}
		if(barrel1Bound.intersects(dkBound))
		{
			breaks.play();
			dkIsHit = true;
			drawBarrel1 = false;
			hitCounter = 0;
			barrel1Bound.setLocation(0, 0);
			barreling1 = false;
			numBarrels--;
		}
		if(barrel2Bound.intersects(dkBound))
		{
			breaks.play();
			dkIsHit = true;
			drawBarrel2 = false;
			hitCounter = 0;
			barrel2Bound.setLocation(0, 0);
			barreling2 = false;
			numBarrels--;
		}
		if(barreling1)
		{
			if(barrel1Bound.getX() >= dkBound.getX())
				barrel1Bound.setX(barrel1Bound.getX() - eip);
			else
				barrel1Bound.setX(barrel1Bound.getX() + eip);
		}
		if(barreling2)
		{
			if(barrel2Bound.getX() >= dkBound.getX())
				barrel2Bound.setX(barrel2Bound.getX() - eip);
			else
				barrel2Bound.setX(barrel2Bound.getX() + eip);
		}
		if(!jumping && !collides(playerBound, shape) && !climbing)
		{
			playerBound.setY(playerBound.getY()+0.3f*delta);
		}
		if(playerBound.intersects(barrel1Bound))
		{
			barreling1 = true;
		}
		if(playerBound.intersects(barrel2Bound))
		{
			barreling2 = true;
		}
		Input input = gc.getInput();
		if(!intro.playing() && !playerDead && !dkDead){
		System.out.println(""+input.getMouseX()+"/"+input.getMouseY());
		if(input.isKeyPressed(Input.KEY_UP) && !jumping && !climbing && 
				ladder[(int)playerBound.getCenterX()/32][(int)(playerBound.getCenterY()/32)])
		{
			climbing = true;
		}
		if(climbing)
		{
			if(input.isKeyDown(Input.KEY_UP) && ladder[(int)playerBound.getCenterX()/32][(int)((playerBound.getCenterY())/32)])
			{
				moving = true;
				playerBound.setY(playerBound.getY() - cip);
			}
			else if(input.isKeyDown(Input.KEY_DOWN) && ladder[(int)playerBound.getCenterX()/32][(int)((playerBound.getCenterY()+32)/32)])
			{
				moving = true;
				playerBound.setY(playerBound.getY() + cip);
			}
			else
				moving = false;
		}
		
		if(input.isKeyDown(Input.KEY_DOWN))
		{
			moving = false;
			crouched = true;
		}
		else
			crouched = false;
		if(jumping && playerLift >= 0)
		{
			playerBound.setY(playerBound.getY()-pip);
			playerLift -= 0.04f;
		}
		else if(jumping && playerLift < 0)
		{
			if (ground[(int)playerBound.getX()/32][((int)(((playerBound.getY()+player.getStandRight().getHeight())-(pip))/32))] != true)
			{
				playerBound.setY(playerBound.getY()-pip);
				playerLift -= 0.04f;		
			}
			else
			{
				System.out.println("not jumping");
				jumping = false;		
			}
		}
		if(input.isKeyPressed(Input.KEY_SPACE) && !jumping)
		{
			jumping = true;
			climbing = false;
			playerLift = 0.5f;
			jump.play();
		}
		if(input.isKeyDown(Input.KEY_RIGHT) && !crouched && !climbing)
		{
			left = false;
			right = true;
			moving = true;
			playerBound.setX(playerBound.getX()+hip);
		}
		else if(input.isKeyDown(Input.KEY_LEFT) && !crouched && !climbing && playerBound.getX() > -10)
		{
			right = false;
			left = true;
			moving = true;
			playerBound.setX(playerBound.getX()-hip);
		}
		else if(!climbing)
			moving = false;
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
	
	public void setBaddies() throws SlickException
	{
		for(int i = 0; i < map.getWidth(); i++)
		{
			for(int j = 0; j < map.getHeight(); j++)
			{
				if(baddyGrid[i][j])
				{
					baddies.add(new badGuy("redFlame"));
					baddyBound.add(new Rectangle(i*32, j*32, baddies.get(0).getLeft().getWidth()-30, baddies.get(0).getLeft().getHeight()-40));
				}
			}
		}
	}

}
