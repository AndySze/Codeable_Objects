/*
 * This file is part of the Codeable Objects Framework.
 *
 *     Codeable Objects is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Codeable Objects is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Codeable Objects.  If not, see <http://www.gnu.org/licenses/>.
 */

import processing.pdf.*;

//import fullscreen.*;

/*This example generates a lamp with no design. Add some points to make it more interesting */

import com.design.Controller;

//global variables
Controller pointController;
int screenNum = 0; //0 for 3d model, 1 for toolpaths, 2 for pattern


void setup() {
  size(1024, 800, P3D); //size of your screen
  
  // Create the fullscreen object
  //FullScreen fs = new FullScreen(this); 
  
  // enter fullscreen mode
 //fs.enter(); 
  
  
  pointController = new Controller(this); //intialize the library


  //CODE FOR CONTROLING LAMP SHAPE ///
  //All units are in millimeters

    pointController.setWidth(170);//sets the width of the middle of your lamp.
  pointController.setHeight(200); // sets the height of your lamp.
  pointController.setBottomWidth(80); //sets the width of the bottom of your lamp.
  pointController.setTopWidth(80); // sets the width of the top of your lamp.

  pointController.setSideNum(6);//sets the number of sides of your lamp.
  pointController.setTopCirclePos(20);//sets the vertical position of your top base
  pointController.setBottomCirclePos(20);//sets the vertical position of your bottom base
  pointController.setNotchWidth(8.92);//sets the width of your notches for the press fit
  pointController.setNotchHeight( 5.64);//sets the height of your notches for the press fit
  pointController.setTopHoleWidth(80);//sets the width of the opening in the top base of your lamp
  pointController.setBottomHoleWidth(24);//sets the width of the opening in the bottom base of your lamp
  //NOTE!! Depending on which side your light fixture will be installed, you must set the hole on bottom or top of your lamp to the diameter of the light fixture


 double freq = 1.5;                          //Frequency of the sine wave
  int numPoints =25;                      //The number of points that will be drawn for each wave
  int xStep = width/numPoints;             //The x increment at each for loop update
  double vUpdate = (360.0/numPoints)*freq;  //The increment used to update the sine wave value at each for loop update
  double amp = 0;                           //The amplitude of the sine wave (will be set later)
  float v = 0;                             //The value of the sine wave (will be set later)
  double xOrig = 10;                         //The x origin that the sine wave will be drawn from (will be set later)
  double yOrig = 0;                         //The y origin that the sine wave will be drawn from (will be set later)
  double xPos = 0;                          //The x position of a point, updated at each for loop update
  double yPos = 0;                          //The y position of a point, updated at each for loop update

  //Set if you want to draw all three regions and if the points are visible

  boolean drawCenterWaves = true;
  boolean drawTopWaves = true;
  boolean drawBottomWaves = true;

  if ( drawCenterWaves ) {
    v = 0;
    //xOrig = 0;
    yOrig = height/2.0;
    xPos = 0;
    yPos = 0;
    amp = height/8;
    for (int i=0; i<numPoints; i++) {
      yPos = sin(v*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = sin((v+180)*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos(v*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v+180)*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      xPos += xStep;
      v += vUpdate;
    }
  }

  if ( drawTopWaves ) {
    v = 0;
    //xOrig = 0;
    yOrig = height/4.0;
    xPos = 0;
    yPos = 0;
    amp = height/8;

    for (int i=0; i<numPoints; i++) {
      yPos = sin(v*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = sin((v+180)*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v)*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v+180)*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      xPos += xStep;
      v += vUpdate;
    }
  }

  if ( drawBottomWaves ) {
    v = 0;
    //xOrig = 0;
    yOrig = height-(height/4.0);
    xPos = 0;
    yPos = 0;
    amp = height/8;

    for (int i=0; i<numPoints; i++) {
      yPos = sin(v*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = sin((v+180)*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v)*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v+180)*DEG_TO_RAD)*amp;
      pointController.addPoint(xOrig+xPos, yOrig+yPos);

      xPos += xStep;
      v += vUpdate;
    }
  }
   pointController.renderAll(); // intializes all of the parameters set above.
}

void draw() {
  
//println(frameRate);
  pointController.drawScreen(screenNum); // draws the selected screen for you to preview according to the screenNum variable
 
 
}



void mousePressed(){
  pointController.mousePressed(mouseX,mouseY);
}

void mouseDragged(){
  pointController.mouseDragged(mouseX,mouseY);
}

void keyPressed() {
 
}




void drawPoint(double orgX, double orgY, float theta, double diameter) { //function that generates and adds circular points

  double xPos = sin(theta)*diameter+orgX;
  double yPos = cos(theta)*diameter+orgY;
  strokeWeight(3);
  pointController.addPoint(xPos, yPos);
}






