/* 
 * Blobscanner v. 0.2-a 
 * by Antonio Molinaro (c) 20/07/2014.
 * Computes the number of blobs in the video 
 * frame. Check the next example for a faster
 * method {findBlobs()}
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
  
  bd.imageFindBlobs(frame);
  
  println(bd.getBlobsNumber() + " BLOBS FOUND");
}
 
