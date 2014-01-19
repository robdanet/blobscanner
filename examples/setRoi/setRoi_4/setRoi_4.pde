/* Blobscanner v. 0.1-a
 * Antonio Molinaro (c) 08/12/2013
 * Searching for blob in 
 * a Region Of Interest defined by the mouse's
 * coordinates using setRoi().
 */
 
import blobscanner.*;
import processing.video.*;

Detector bd;
Capture frame;

final int WIDTH = 640;
final int HEIGHT = 480;
int SX, SY;
PImage src;

void setup() 
{
  size(WIDTH, HEIGHT);
  frame = new Capture(this, WIDTH, HEIGHT, "/dev/video0");
  frame.start();
 
  bd = new Detector(this, 255);
}

void draw(){
  if(frame.available()){
    frame.read();
    
    image(frame, 0, 0);
    frame.filter(THRESHOLD);
  }
  // this is needed to make sure ROI's area stays inside 
  // the frame and also to place the poitner at the 
  // center of the box representing it
  int x = (int)constrain(mouseX-100 ,0 ,WIDTH-200  );
  int y = (int)constrain(mouseY-100 ,0 ,HEIGHT-200 );
  bd.setRoi( x ,y  ,200,200  );
 
  frame.loadPixels();
  bd.findBlobs(frame.pixels,frame.width,frame.height);
   
  
  bd.loadBlobsFeatures();
  bd.weightBlobs(true);
  copy(frame, x, y, 200, 200, x, y, 200, 200);
  
  bd.drawSelectBox(1000, color(0,255,0),1);
  bd.drawSelectContours(1000, color(233,0, 245),1);
   
  noFill();
  stroke(255,255,0);
  rect(x,y ,200,200);
  
  println("frameRate " + frameRate);
}
 
