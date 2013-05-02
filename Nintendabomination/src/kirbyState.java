import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
public class kirbyState extends BasicGameState{
	
	private int stateID;
	private TiledMap map;
	private Rectangle playerBound;
	private Rectangle[][] rect;
	private float pX, pY;
	private float playerVel, playerLift, enemyVel;
	private grantSide player;
	private boolean[][] ground;
	private boolean[][] baddyGrid;
	private boolean[][] endGrid;
	private Shape[] shape;
	private int shapeCount;
	private int mapX, mapY;
	private boolean jumping, right, left, crouched, moving;
	private boolean bgMusic, playerDead;
	private boolean victoryPlayed, victorious;
	private ArrayList<badGuy> baddies;
	private ArrayList<badGuy> randBaddies;
	private ArrayList<Rectangle> baddyBound, randBaddyBound;
	private Music background;
	private Sound victory, death, bounce;
	private int delay;
	private boolean deathPlayed;
	private Random rand;
	private boolean livesDecreased;
	
	public kirbyState(int stateID)
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
		deathPlayed = false;
		victorious = false;
		mapX = 0;
		mapY = -5760;
		playerBound.setX(pX);
		playerBound.setY(pY);
		this.setBaddies();
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		baddies.clear();
		baddyBound.clear();
		randBaddies.clear();
		randBaddyBound.clear();
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		rand = new Random();
		livesDecreased = false;
		victory = new Sound("res/Kirby Res/kirbyVictory.wav");
		death = new Sound("res/Kirby Res/kirbyGameover.wav");
		bounce = new Sound("res/Kirby Res/kirbyBounce.wav");
		deathPlayed = false;
		victorious = false;
		baddies = new ArrayList<badGuy>();
		baddyBound = new ArrayList<Rectangle>();
		randBaddyBound = new ArrayList<Rectangle>();
		randBaddies = new ArrayList<badGuy>();
		bgMusic = false;
		background = new Music("res/Kirby Res/kirbyBackground.wav");
		playerDead = false;
		victoryPlayed = false;
		map = new TiledMap("res/Kirby Res/kirbyMap.tmx");
		player = new grantSide();
		jumping = false; right = true; left = false; crouched = false; moving = false;
		pX = 50;
		pY = 6176;
		mapX = 0;
		mapY = -5760;
		playerVel = 0.15f;
		enemyVel = 0.15f;
		shapeCount = 0;
		delay = 2000;
		gc.setMinimumLogicUpdateInterval(20);
		playerBound = new Rectangle(pX, pY, 10, 40);
		ground = new boolean[map.getWidth()][map.getHeight()];
		rect = new Rectangle[map.getWidth()][map.getHeight()];
		baddyGrid = new boolean[map.getWidth()][map.getHeight()];
		endGrid = new boolean[map.getWidth()][map.getHeight()];
		shape = new Shape[10000];
		for (int x=0;x<map.getWidth();x++) 
		{
			for (int y=0;y<map.getHeight();y++)
			{
				int tileID = map.getTileId(x, y, 1);
				String value = map.getTileProperty(tileID, "blocked", "false");
				String value1 = map.getTileProperty(tileID, "end", "false");
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
					endGrid[x][y] = true;
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
					if(baddyBound.get(i).getX() > playerBound.getX())
						baddies.get(i).getMoveRight().draw(baddyBound.get(i).getX()+mapX-16, baddyBound.get(i).getY()+mapY-26);
					else
						baddies.get(i).getMoveLeft().draw(baddyBound.get(i).getX()+mapX-16, baddyBound.get(i).getY()+mapY-26);
				}
			}
		}
		if(!randBaddies.isEmpty() && !playerDead)
		{
			for(int i = 0; i < randBaddies.size(); i++)
			{
				if(randBaddies.get(i).getAlive())
				{
					randBaddies.get(i).getMoveLeft().draw(randBaddyBound.get(i).getX()+mapX-32, randBaddyBound.get(i).getY()+mapY-36);
				}
			}
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
		System.out.println(""+(mapY)+"/"+(playerBound.getY()*-1));
		if(!bgMusic)
		{
			background.loop();	
			bgMusic = true;
		}
		if(!playerDead && endGrid[(int)playerBound.getCenterX()/32][(int)playerBound.getCenterY()/32])
		{
			victorious = true;
		}
		if(victorious)
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
					Hub.kirbyBeaten = true;
					Hub.worldsBeaten++;
					sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
				}
					
			}
		}
		else{
		float hip = playerVel * delta;
		float pip = playerLift * delta;
		float erp = enemyVel * delta;
		mapY += .007f * delta;
		if((playerBound.getY()*-1)-mapY < -700)
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
		delay -= delta;
		if(delay <= 0)
		{
			delay = 2000;
			this.sendEnemy();
		}
		if(!baddies.isEmpty())
		{
			for(int i = 0; i < baddies.size(); i++)
			{
				if(baddies.get(i).getAlive()){
					if(baddyBound.get(i).intersects(playerBound) && !jumping)
					{
							playerDead = true;
					}
				if(!collides(baddyBound.get(i), shape))
				{
					baddyBound.get(i).setY(baddyBound.get(i).getY()+0.3f*delta);
				}
				if(playerBound.getY() - baddyBound.get(i).getY() < 20 && !baddies.get(i).getAttacking())
				{
					baddies.get(i).setAttacking(true);
					if(baddyBound.get(i).getX() > playerBound.getX())
						baddies.get(i).setAttackingLeft(true);
					else if(baddyBound.get(i).getX() <= playerBound.getX())
						baddies.get(i).setAttackingRight(true);
				}
				if(baddies.get(i).getAttackingRight())
					baddyBound.get(i).setX(baddyBound.get(i).getX() + erp);
				else if(baddies.get(i).getAttackingLeft())
					baddyBound.get(i).setX(baddyBound.get(i).getX() - erp);	
					
				}
			}
		}
		if(!randBaddies.isEmpty())
		{
			for(int i = 0; i < randBaddies.size(); i++)
			{
				if(randBaddies.get(i).getAlive()){
					if(randBaddyBound.get(i).intersects(playerBound) && !jumping)
					{
							playerDead = true;
					}
					randBaddyBound.get(i).setY(randBaddyBound.get(i).getY() + erp);
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
		if(jumping && playerLift >= 0)
		{
			playerBound.setY(playerBound.getY()-pip);
			playerLift -= 0.03f;
			if(!baddies.isEmpty())
			{
				for(int i = 0; i < baddies.size(); i++)
				{
					if(playerBound.intersects(baddyBound.get(i)))
					{
						playerDead = true;
					}
				}
			}
			if(!randBaddies.isEmpty())
			{
				for(int i = 0; i < randBaddies.size(); i++)
				{
					if(playerBound.intersects(randBaddyBound.get(i)))
					{
						playerDead = true;
					}
				}
			}
		}
		else if(jumping && playerLift < 0)
		{
			if(!baddies.isEmpty())
			{
				for(int i = 0; i < baddies.size(); i++)
				{
					if(baddyBound.get(i).contains(playerBound.getCenterX(), playerBound.getY()+playerBound.getHeight()+26))
					{
						baddies.get(i).setAlive(false);
						baddyBound.remove(i);
						baddies.remove(i);
						playerLift = 0.6f;
						bounce.play();
					}
				}
			}
			if(!randBaddies.isEmpty())
			{
				for(int i = 0; i < randBaddies.size(); i++)
				{
					if(randBaddyBound.get(i).contains(playerBound.getCenterX(), playerBound.getY()+playerBound.getHeight()+26))
					{
						randBaddies.get(i).setAlive(false);
						randBaddyBound.remove(i);
						randBaddies.remove(i);
						playerLift = 0.6f;
						bounce.play();
					}
				}
			}
			if((int)(((playerBound.getY()+player.getStandRight().getHeight())-(pip))/32) < 196)
			{
				if (ground[(int)playerBound.getX()/32][((int)(((playerBound.getY()+32)-(pip))/32))] != true)
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
			playerLift = 0.5f;
		}
		if(input.isKeyDown(Input.KEY_RIGHT) && !playerDead && !crouched && !lookAhead(playerBound.getX()+32, playerBound.getY(), shape))
		{
			left = false;
			right = true;
			moving = true;
			playerBound.setX(playerBound.getX()+hip);
		}
		else if(input.isKeyDown(Input.KEY_LEFT) && !playerDead && playerBound.getX()+mapX > mapX && !crouched && !lookAhead(playerBound.getX()-32, playerBound.getY(), shape))
		{
			right = false;
			left = true;
			moving = true;
			playerBound.setX(playerBound.getX()-hip);
		}
		else
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
	
	public boolean lookAhead(float x, float y, Shape[] b)
	{
		boolean hit = false;
		for(int i=0; i < shapeCount; i++)
		{
			if(b[i].contains(x, y) && !jumping)
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
					baddies.add(new badGuy("ball"));
					baddyBound.add(new Rectangle(i*32, j*32, baddies.get(0).getLeft().getWidth()-10, baddies.get(0).getLeft().getHeight()-10));
				}
			}
		}
	}
	
	public void sendEnemy() throws SlickException
	{
		int xLoc = rand.nextInt(800);
		randBaddies.add(new badGuy("flutter"));
		randBaddyBound.add(new Rectangle(xLoc, mapY, randBaddies.get(0).getLeft().getWidth()-10, randBaddies.get(0).getLeft().getHeight()-30));		
	}

}
