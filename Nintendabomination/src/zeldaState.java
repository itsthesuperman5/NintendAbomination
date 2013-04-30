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
public class zeldaState extends BasicGameState {
	
	private bomb bombs;
	private float bombX, bombY;
	private int stateID;
	private int hitCounter;
	private TiledMap map;
	private Rectangle playerBound;
	private Rectangle[][] rect;
	private float pX, pY, bx1, bx2, by1, by2, dkX, dkY;
	private float playerVel, playerLift, climbVel, enemyVel;
	private grantSide player;
	private boolean[][] ground;
	private boolean[][] baddyGrid;
	private Shape[] shape;
	private int shapeCount;
	private int mapX, mapY;
	private SpriteSheet enemySheet;
	private SpriteSheet linkSheet;
	private boolean jumping, right, left, crouched, moving;
	private boolean bgMusic, finMusic, playerDead;
	private boolean deathPlayed, bombPlaced, bombExplode;
	private ArrayList<badGuy> baddies;
	private ArrayList<Rectangle> baddyBound;
	private Image baddyImage;
	private Image baddyImageFlip;
	private Rectangle linkBound;
	private Image[] linkImages;
	private Animation linkWalkAnim;
	private Animation linkHitAnim;
	private Music background, finished, intro, death, playing;
	private Sound jump, breaks;
	private int bombTimer, attackTimer;

	public zeldaState(int stateID)
	{
		this.stateID = stateID;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		deathPlayed = false;
		playerDead = false;
		moving = false;
		crouched = false;
		jumping = false;
		bombPlaced = false;
		bombExplode = false;
		mapX = 0;
		mapY = 0;
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
		bombs = new bomb();
		bombExplode = false;
		bombPlaced = false;
		baddies = new ArrayList<badGuy>();
		baddyBound = new ArrayList<Rectangle>();
		bgMusic = false;
		finMusic = false;
		background = new Music("res/Zelda Res/zelda.wav");
		jump = new Sound("res/Classic Music/jump.wav");
		breaks = new Sound("res/Classic Music/barrel.wav");
		playing = intro;
		playerDead = false;
		deathPlayed = false;
		enemySheet = new SpriteSheet("res/ArcadeEnemies.png", 25, 25);
		baddyImage = enemySheet.getSprite(0, 0);
		baddyImageFlip = baddyImage.getFlippedCopy(true, false);
		map = new TiledMap("res/Zelda Res/zeldamap.tmx");
		player = new grantSide();
		jumping = false; right = true; left = false; crouched = false; moving = false;
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
		ground = new boolean[map.getWidth()][map.getHeight()];
		rect = new Rectangle[map.getWidth()][map.getHeight()];
		baddyGrid = new boolean[map.getWidth()][map.getHeight()];
		shape = new Shape[10000];
		for (int x=0;x<map.getWidth();x++) 
		{
			for (int y=0;y<map.getHeight();y++)
			{
				int tileID = map.getTileId(x, y, 1);
				String value = map.getTileProperty(tileID, "blocked", "false");
				String value2 = map.getTileProperty(tileID, "baddy", "false");
				if ("true".equals(value))
				{
					ground[x][y] = true;
					rect[x][y] = new Rectangle((float)(x*32), (float)(y*32), map.getTileWidth(), map.getTileHeight());
					shape[shapeCount] = rect[x][y];
					shapeCount++;
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
		map.render(mapX, mapY, 0);
		if(!baddies.isEmpty() && !playerDead)
		{
			for(int i = 0; i < baddies.size(); i++)
			{
				if(baddies.get(i).getAlive())
				{
					if(baddies.get(i).getAttacking() && baddyBound.get(i).getX() >= playerBound.getX())
					{
						baddies.get(i).getAttackLeft().draw(baddyBound.get(i).getX()+mapX, baddyBound.get(i).getY()+mapY-15);
					}
					else if(baddies.get(i).getAttacking() && baddyBound.get(i).getX() < playerBound.getX())
					{
						baddies.get(i).getAttackRight().draw(baddyBound.get(i).getX()+mapX, baddyBound.get(i).getY()+mapY-15);
					}
				if(baddyBound.get(i).getX() >= playerBound.getX() && !baddies.get(i).getAttacking())
					baddies.get(i).getMoveLeft().draw(baddyBound.get(i).getX()+mapX, baddyBound.get(i).getY()+mapY-15);
				else if(baddyBound.get(i).getX() < playerBound.getX() && !baddies.get(i).getAttacking())
					baddies.get(i).getMoveRight().draw(baddyBound.get(i).getX()+mapX, baddyBound.get(i).getY()+mapY-15);
				}
			}
		}
		if(bombPlaced)
		{
			bombs.getBomb().draw(bombX+mapX, bombY+mapY);
		}
		if(bombExplode)
		{
			bombs.getExplosion().draw(bombX+mapX, bombY+mapY-40);
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
			bgMusic = true;
		}
		float hip = playerVel * delta;
		float pip = playerLift * delta;
		float cip = climbVel * delta;
		float erp = enemyVel * delta;
		float eip = 0.2f * delta;
		float fip = 0.3f * delta;
		if(bombExplode)
		{
			System.out.println(""+bombX+"/"+bombY);
			System.out.println(""+playerBound.getX()+"/"+playerBound.getY());
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
		if(playerBound.getX()+5 >= bombX && playerBound.getX() < bombX + 50 && bombExplode
						&& playerBound.getY()+25 >= bombY-10 && playerBound.getY() < bombY + 50)
			playerDead = true;
		if(playerDead)
		{
			background.stop();
			bgMusic = false;
			/*if(!deathPlayed)
			{
				death.play();
				deathPlayed = true;
			}*/
			playerBound.setY(playerBound.getY()+0.1f*delta);
			//if(!death.playing())
				sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		if(!baddies.isEmpty())
		{
			for(int i = 0; i < baddies.size(); i++)
			{
				if(baddies.get(i).getAlive()){
					if(baddies.get(i).getAttacking())
					{
						System.out.println(baddyBound.get(i).intersects(playerBound));
						baddies.get(i).setDelay(baddies.get(i).getDelay()-delta);
						if(baddies.get(i).getDelay() <= 0)
							baddies.get(i).setAttacking(false);
						if(baddyBound.get(i).intersects(playerBound))
							playerDead = true;
					}
				if(baddyBound.get(i).getX() >= bombX && baddyBound.get(i).getX() < bombX + 50 && bombExplode
						&& baddyBound.get(i).getY() >= bombY-10 && baddyBound.get(i).getY() < bombY + 50)
					baddies.get(i).setAlive(false);
				if(!collides(baddyBound.get(i), shape))
				{
					baddyBound.get(i).setY(baddyBound.get(i).getY()+0.3f*delta);
				}
				if(baddyBound.get(i).getX()-playerBound.getX() < 5 && baddyBound.get(i).getX()-playerBound.getX() > -5
						&& !baddies.get(i).getAttacking())
				{
					baddies.get(i).setAttacking(true);
					baddies.get(i).setDelay(500);
				}
				baddies.get(i).setDelay(baddies.get(i).getDelay() - delta);
				if(baddies.get(i).getDelay() <= 0)
				{
					baddies.get(i).newDelay();
					baddies.get(i).changeDirection();
				}
				if(baddyBound.get(i).getX() >= playerBound.getX() && !baddies.get(i).getAttacking())
				{
					if(baddyBound.get(i).getX() - playerBound.getX() <= 400)
						baddyBound.get(i).setX(baddyBound.get(i).getX() - erp);
				}
				else if(baddyBound.get(i).getX() < playerBound.getX() && !baddies.get(i).getAttacking())
				{
					if(baddyBound.get(i).getX() - playerBound.getX() >= -400)
						baddyBound.get(i).setX(baddyBound.get(i).getX() + erp);
				}
				}
			}
		}
		if(!jumping && !collides(playerBound, shape))
		{
			playerBound.setY(playerBound.getY()+0.3f*delta);
		}
		Input input = gc.getInput();
		System.out.println(""+input.getMouseX()+"/"+input.getMouseY());
		
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
			if (ground[(int)playerBound.getX()/32][((int)(((playerBound.getY()+player.getStandRight().getHeight())-(pip))/32))] != true)
			{
				playerBound.setY(playerBound.getY()-pip);
				playerLift -= 0.03f;		
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
			playerLift = 0.6f;
			jump.play();
		}
		if(input.isKeyDown(Input.KEY_RIGHT) && !crouched && !lookAhead(playerBound.getX()+32, playerBound.getY(), shape))
		{
			left = false;
			right = true;
			moving = true;
			playerBound.setX(playerBound.getX()+hip);
			mapX -= hip;
		}
		else if(input.isKeyDown(Input.KEY_LEFT) && playerBound.getX()+mapX > mapX && !crouched && !lookAhead(playerBound.getX()-32, playerBound.getY(), shape))
		{
			right = false;
			left = true;
			moving = true;
			playerBound.setX(playerBound.getX()-hip);
			mapX += hip;
		}
		else
			moving = false;
		
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
	
	public void setBaddies() throws SlickException
	{
		for(int i = 0; i < map.getWidth(); i++)
		{
			for(int j = 0; j < map.getHeight(); j++)
			{
				if(baddyGrid[i][j])
				{
					baddies.add(new badGuy("stalfos"));
					baddyBound.add(new Rectangle(i*32, j*32, baddies.get(0).getLeft().getWidth()-10, baddies.get(0).getLeft().getHeight()-10));
				}
			}
		}
	}

}