/*
 * BACKGROUND CLASS:
 * randomly generates amount of clouds and stars
 * generates each cloud
 * generates star field
 * does math for time-sensitive operations in main class
 */

package sunset;
import java.util.Random;

public class Background {
	
	//variables
	private int NUMCLOUDS, NUMSTARS;
	private float sunset, time;
	int[] starX,starY;
	Random rand = new Random();
	Cloud[] clouds;
	
	//creates background
	//random number of clouds and stars
	public Background() {
		//randomly decide how many clouds and stars
		NUMCLOUDS = rand.nextInt(8);
		NUMSTARS = rand.nextInt(100) + 100;
		
		//generate cloud and stars based on randomness
		createFirstClouds();
		generateStarfield();
		
	}
	
	//does math necessary for background color
	public float getTime(float mouseY) {
		//find time of day based on mouse y position
		time = (float) ((mouseY)*.637);
		return time;
	}
	public float getSunset(float mouseY) {
		//figure out how close sun is to horizon
		sunset = 50 - Math.abs(300-mouseY);
		return sunset;
	}
	
	//create first clouds
	private void createFirstClouds() {
		//declare variables
		float xpos = 400;
		
		//create array
		clouds = new Cloud[NUMCLOUDS];
		
		//fill array
		for (int i = 0;i < clouds.length; i++) {
			clouds[i] = new Cloud(xpos);
			xpos -= (600/NUMCLOUDS);
		}
	}
	
	//create star field
	private void generateStarfield() {
		//create arrays
		starX = new int[NUMSTARS];
		starY = new int[NUMSTARS];
		
		//fill with random values
		for (int i = 0; i < NUMSTARS; i++){
			starX[i] = rand.nextInt(500);
		}
		for (int i = 0; i < NUMSTARS; i++){
			starY[i] = rand.nextInt(320);
		}
		
	}
	
	//move each cloud by its speed
	public void step() {
		
		//move clouds over by SPEED and reset if past frame
		for (int i = 0; i < clouds.length; i++) {
			if (clouds[i].xpos < 515) {
				clouds[i].xpos += clouds[i].speed;
			}
			else {
				clouds[i] = new Cloud();
			}
		}
	}
	
}

//a class that contains the information for a cloud
class Cloud {
	
		//declare variables
	public final float c1x, c1y, c2x,c2y, ypos, xdif,ydif,speed;
	public float xpos;
	Random rand = new Random();
	
	//default constructor builds a cloud off screen
	Cloud() {
		this(-150);
	}
	
	//generates random values for everything except x position
	Cloud(float x) {
		//declare variables
		float min, max;
		
		//speed
		speed = (float) (rand.nextFloat() * (.4 - .1) + .1);
		
		//height
		max = 25;
		min= 10;
		c1y = rand.nextFloat() * (max - min) + min;
		c2y = rand.nextFloat() * (max - min) + min;
		//width
		max = 100;
		min = 50;
		c1x = rand.nextFloat() * (max - min) + min;
		c2x = rand.nextFloat() * (max - min) + min;
		max = 10;
		min = -10;
		//difference
		xdif = rand.nextFloat() * (max - min) + min;
		ydif = rand.nextFloat() * (max - min) + min;
		//Y position
		max = 150;
		min = 0;
		ypos = rand.nextFloat() * (max - min) + min;
		
		//x position
		xpos = x;
	}
	
	
}
