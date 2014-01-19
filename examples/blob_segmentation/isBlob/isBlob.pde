/* 
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro - 08/12/2013.
 * The screen pixel is set after  
 * cheking if it is a blobs pixel or not.
 *
 */
import blobscanner.*;
import processing.video.*;

Detector bd;
Capture frame;
 
 

void setup(){
  size(320, 240);
  frame = new Capture(this, width, height,"/dev/video0");
  frame.start();
  
  bd = new Detector( this, 255 );
}

void draw(){
  if(frame.available()){
    frame.read();
   } 
 
  frame.filter(GRAY);
  frame.filter(BLUR);
  frame.filter(THRESHOLD);
  
  bd.imageFindBlobs(frame);
  bd.loadBlobsFeatures();
  
 
  for(int x = 0; x < width; x++){
    for(int y = 0; y < height; y++){  
      float c = map(y,0,height, 0, 254); 
      if(bd.isBlob(x, y)){
        set(x, y, color(c));
      } 
      else {
         
        set(x, y,color(255-c));
      }
    } 
  }
} 
