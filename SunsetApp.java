/*
 * CONTROLS:
 * MOUSE MOVES THE SUN
 * LEFT AND RIGHT ARROW MOVES THE CHARACTER
 * TAB GENERATES NEW BG
 * 'Q' or 'q' QUIT
 */

package sunset;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class SunsetApp extends PApplet {
	
	//variables
	public final int WIDTH=500, HEIGHT=400;
	private int xpos,ypos,speed;
	boolean faceRight;
	Background bg;

	public void setup() {
		//create window and place character
		size(WIDTH,HEIGHT);
		noStroke();
		xpos = 245;
		ypos = 300;
		speed = 3;
		faceRight = false;
		bg = new Background();
	}
	
	public void draw() {
		//go through methods
		background();
		clouds();
		drawFPS();
		move();
		
		//translates guy and shadow based on movement parameters
		pushMatrix();
		translate(xpos,ypos);
		guy();
		shade();
		popMatrix();
	}
	
	//show FPS on screen
	private void drawFPS() {
		fill(255);
		textSize(10);
		text("FPS: " + (int) frameRate, 10, 15);
		text("MOUSE + ARROW KEYS", 380, 15); 
	}
	
	//a method that draws the guy
	private void guy() {
		
		fill(0); //black fill
		
		//build body
		rect(-3,-5,3,5);
		rect(+3,-5,3,5);
		rect(-3,-15,9,10);
		rect(0,-17,3,2);
		rect(-2,0-23,7,7);
		
		//arms based on direction
		if (faceRight) {
			rect(-5,-14,17,2);
			rect(-6,-14,2,7);
		}
		else {
			rect(-9,-14,17,2);
			rect(+7,-14,2,7);
		}
	}
	
	//read keyboard and move guy accordingly
	private void move() {
		//read if a key is pressed
		if (keyPressed) {
			//if right, move right
			if (keyCode == RIGHT && xpos < 490) {
				xpos += speed;
				faceRight = true;
			}
			//if left, move left
			else if (keyCode == LEFT && xpos > 8) {
				xpos -= speed;
				faceRight = false;
			}
			//if q, quit program
			else if (key == 113 || key == 81) exit();
			else if (key == 9) bg = new Background();
		}
		
	}

	//draw shadow, react to light
	private void shade() {
		
		//declare variables
		float sideX,sideY,angle,scale;
		
		//find out the distance from sun to guy
		sideX = xpos-mouseX;
		sideY = ypos-mouseY;
		
		//find angle between the sun and the guy based on horizontal distance
		angle = atan2(sideX,50);
		
		//scale the shadow based on vertical distance
		if (mouseY < 300) scale = 300/sideY;
		else scale = 300;
		
		//draw the shadow and warp it based on math
		pushMatrix();
		shearX(angle);
		scale(1,-1*scale);
		guy();
		popMatrix();
		
	}

	//sky, sun and grass react to light
	//also calls stars method
	private void background() {
		
		//declare variables
		float sunset,time; 
		
		time = bg.getTime(mouseY);
		sunset = bg.getSunset(mouseY);
		
		//sky
		background(100,100,255); //day sky
		fill(49, 31, 49,time); //night: increase opacity with time
		rect(0,0,500,400);
		fill(255, 0, 0,sunset); //sunset based on distance from horizon
		rect(0,0,500,400);
		
		fill(248, 228, 105); //sun, change color based on time of day
		ellipse(mouseX,mouseY,50,50);
		fill(255, 123, 80,time);
		ellipse(mouseX,mouseY,50,50);
		
		stars();
		
		fill(100,255,100); //render grass, darken based on time
		rect(0,299,500,101);
		fill(0,time+20);
		rect(0,299,500,101);
	
	}

	//draw clouds, get darker as night comes
	private void clouds() {
		
		//darken fill as mouse lowers
		fill(255-(mouseY/3),150);
		
		//draw all clouds defined in background class
		for (Cloud fluff: bg.clouds) {
			rect(fluff.xpos,fluff.ypos,fluff.c1x,fluff.c1y);
			rect(fluff.xpos+fluff.xdif,fluff.ypos+fluff.ydif,fluff.c2x,fluff.c2y);
		}
		
		bg.step();
		
	}

	//draw stars, get brighter as night comes
	private void stars() {
		//increase opacity as night comes
		fill(255,mouseY-200);
		
		//draw stars
		for (int i = 0; i < bg.starX.length; i ++) {
			rect(bg.starX[i],bg.starY[i],1,1);
		}
	}
	
}

