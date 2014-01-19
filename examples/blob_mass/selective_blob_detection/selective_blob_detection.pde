/* 
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro - 08/12/2013.
 * Computes the blobs in the video's frame
 * and draws the blob's contours and bounding box
 * choosing the color based on a weight threshold.
 *
 */
import blobscanner.*;
import processing.video.*;

Detector bd;
Capture frame;
PImage img;

void setup(){
      size(640,  480 );
      img = createImage(160, 120,RGB);
      
      frame = new Capture(this,160, 120,"/dev/video0");
      frame.start();
      
      bd = new Detector( this, 255 );
     
}

void draw(){
    if(frame.available()){
      frame.read();
  
      scale(4);
      image(frame,0,0);
 
      frame.filter(THRESHOLD);
      frame.filter(BLUR,0.5);
      frame.loadPixels();
     
      bd.findBlobs(frame.pixels,frame.width,frame.height);
      bd.loadBlobsFeatures();
      bd.weightBlobs(true);
  
      int minimumWeight = 1000;
      int thickness = 1;
      color contoursColor = color(255, 0, 0);
      color boundingBoxColor = color(0, 255, 0);
      color selectBoxColor = color(255, 255, 0);
      color selectContoursColor = color(0, 255, 255);
      
  
      bd.drawContours(contoursColor, thickness);
      bd.drawSelectContours(minimumWeight, selectContoursColor, thickness);
      bd.drawBox(boundingBoxColor, thickness);
      bd.drawSelectBox(minimumWeight, selectBoxColor, thickness);
  
      println(frameRate);
    
  }//avoids flickering
}
 
