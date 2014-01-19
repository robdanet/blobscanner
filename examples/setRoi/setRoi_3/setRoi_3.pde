/* Blobscanner v. 0.1-a
 * Antonio Molinaro (c) 08/12/2013
 * Searching for blob in 
 * a Region Of Interest defined by the mouse's
 * coordinates using the new method setRoi().
 */
 
import blobscanner.*;

Detector bd;
final int WIDTH = 640;
final int HEIGHT = 400;
int SX, SY;
PImage src;

void setup() 
{
  size(WIDTH, HEIGHT);
  src = loadImage("blobs.png");
  src.filter(THRESHOLD); 
  bd = new Detector(this, 0);
}

void draw(){
  set(0, 0, src);
 
  
  // this is needed to make sure the ROI's area stays inside 
  // the image's boundaries and also to place the 
  // poitner at the center of the box representing it
  int x = (int)constrain(mouseX-100,1,WIDTH-200  );
  int y = (int)constrain(mouseY-100,1 ,HEIGHT-200 );
  bd.setRoi(x, y, 200, 200);
 
  src.loadPixels();
  bd.findBlobs(src.pixels,src.width,src.height);
  src.updatePixels();
  
  bd.loadBlobsFeatures();
  
  bd.drawBox(color(0,255,0),1);
  bd.drawContours(color(233,0, 245),2);
   
  noFill();
  stroke(255,255,0);
  rect(x,y ,200,200);
 // println(frameRate);
}


