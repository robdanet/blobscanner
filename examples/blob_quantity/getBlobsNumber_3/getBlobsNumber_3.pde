/* 
 * Blobscanner v. 0.2-a 
 * by Antonio Molinaro (c) 20/07/2014.
 * Finds the number of blobs in the frame.
 * In this example the method findBlob(int[], int, int)
 * is used instead of imageFindBlobs(PImage).
 * This method is the faster.
 */
import blobscanner.*;
import processing.video.*;

Detector bd;
Capture frame;
 

void setup(){
  size(320, 240);
  frame = new Capture(this, width, height);
  frame.start();
  
  bd = new Detector( this, 255 );
   
}

void draw(){
  if(frame.available()){
    frame.read();
    image(frame, 0, 0);
  }
 
   
 
  frame.filter(THRESHOLD);
  frame.loadPixels();
  
  bd.findBlobs(frame.pixels, frame.width, frame.height);
  
  println(bd.getBlobsNumber() + " BLOBS FOUND");
  
   
}
 
