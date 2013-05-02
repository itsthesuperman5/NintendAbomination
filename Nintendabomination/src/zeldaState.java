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
	private TiledMap map;
	private Rectangle playerBound;
	private Rectangle[][] rect;
	private float pX, pY, linkX, linkY;
	private float playerVel, playerLift, enemyVel;
	private grantSide player;
	private boolean[][] ground;
	private boolean[][] baddyGrid;
	private Shape[] shape;
	private int shapeCount;
	private int mapX, mapY;
	private int linkHit;
	private SpriteSheet linkSheet;
	private boolean jumping, right, left, crouched, moving;
	private boolean bgMusic, playerDead, linkDead, bossFight, hitLink;
	private boolean victoryPlayed, bombPlaced, bombExplode, linkAttacking;
	private ArrayList<badGuy> baddies;
	private ArrayList<Rectangle> baddyBound;
	private Rectangle linkBound;
	private Image[] linkWalkImages;
	private Image[] linkAttackImages;
	private Animation linkWalkAnim;
	private Animation linkAttackAnim;
	private Music background;
	private Sound jump, victory;
	private int bombTimer, delay, newDelay;
	private boolean livesDecreased;

	public zeldaState(int stateID)
	{
		this.stateID = stateID;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		victoryPlayed = false;
		livesDecreased = false;
		playerDead = false;
		moving = false;
		crouched = false;
		jumping = false;
		bombPlaced = false;
		bombExplode = false;
		linkDead = false;
		linkAttacking = false;
		bossFight = false;
		hitLink = false;
		mapX = 0;
		mapY = 0;
		linkHit = 0;
		playerBound.setX(pX);
		playerBound.setY(pY);
		linkBound.setX(linkX);
		linkBound.setY(linkY);
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
		livesDecreased = false;
		victory = new Sound("res/Zelda Res/zeldaVictory.wav");
		linkSheet = new SpriteSheet("res/Zelda Res/link.png", 35, 35);
		linkWalkImages = new Image[4];
		linkAttackImages = new Image[2];
		linkWalkImages[0] = linkSheet.getSprite(3, 0).getScaledCopy(4).getFlippedCopy(true, false);
		linkWalkImages[1] = linkSheet.getSprite(2, 0).getScaledCopy(4).getFlippedCopy(true, false);
		linkWalkImages[2] = linkSheet.getSprite(1, 0).getScaledCopy(4).getFlippedCopy(true, false);
		linkWalkImages[3] = linkSheet.getSprite(0, 0).getScaledCopy(4).getFlippedCopy(true, false);
		linkAttackImages[0] = linkSheet.getSprite(4, 0).getScaledCopy(4).getFlippedCopy(true, false);
		linkAttackImages[1] = linkSheet.getSprite(5, 0).getScaledCopy(4).getFlippedCopy(true, false);
		linkWalkAnim = new Animation(linkWalkImages, 200);
		linkAttackAnim = new Animation(linkAttackImages, 400);
		bombs = new bomb();
		bombExplode = false;
		bossFight = false;
		linkDead = false;
		linkAttacking = false;
		bombPlaced = false;
		hitLink = false;
		baddies = new ArrayList<badGuy>();
		baddyBound = new ArrayList<Rectangle>();
		bgMusic = false;
		background = new Music("res/Zelda Res/zelda.wav");
		jump = new Sound("res/Classic Music/jump.wav");
		playerDead = false;
		victoryPlayed = false;
		map = new TiledMap("res/Zelda Res/zeldamap.tmx");
		player = new grantSide();
		jumping = false; right = true; left = false; crouched = false; moving = false;
		pX = 100;
		pY = 500;
		mapX = 0;
		mapY = 0;
		linkHit = 0;
		playerVel = 0.1f;
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
				String value1 = map.getTileProperty(tileID, "link", "false");
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
					linkX = (float)(x*32);
					linkY = (float)(y*32);
				}
				if("true".equals(value2))
				{
					baddyGrid[x][y] = true;
				}
			}
		}
		linkBound = new Rectangle(linkX, linkY, linkWalkImages[0].getWidth(), linkWalkImages[0].getHeight());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		map.render(mapX, mapY, 0);
		if(!linkDead && !playerDead)
		{
			if(!linkAttacking)
			{
				linkWalkAnim.draw(linkBound.getX()+mapX, linkBound.getY()+mapY-10);
			}
			else
				linkAttackAnim.draw(linkBound.getX()+mapX, linkBound.getY()+mapY);
		}
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
			background.setVolume(0.1f);
			bgMusic = true;
		}
		float hip = playerVel * delta;
		float pip = playerLift * delta;
		float erp = enemyVel * delta;
		if(playerBound.getY() > 550)
			playerDead = true;
		if(!linkDead){
		if(linkHit >= 3)
			linkDead = true;
		if(linkBound.getCenterX() - playerBound.getCenterX() < 200 && !bossFight)
			bossFight = true;
		if(bossFight)
		{
			linkBound.setX(linkBound.getX() - 0.08f*delta);
			if(linkAttacking)
			{
				delay -= delta;
				if(delay <= 0)
				{
					playerBound.setX(playerBound.getX() - .8f * delta);
					newDelay -= delta;
					if(newDelay <= 0)
						playerDead = true;
				}
			}
			if(linkBound.getCenterX() - playerBound.getCenterX() < 50 && !linkAttacking){
				linkAttacking = true;
				delay = 450;
				newDelay = 500;
			}
			if(hitLink)
			{
				delay -= delta;
				if(delay <= 0)
				{
					linkHit++;
					hitLink = false;
				}
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
		if(linkBound.contains(bombX, bombY) && bombExplode && !hitLink)
		{
			hitLink = true;
			delay = 2000;
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
			if(!livesDecreased){
				Hub.numLives--;
				livesDecreased = true;
			}
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
					Hub.zeldaBeaten = true;
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