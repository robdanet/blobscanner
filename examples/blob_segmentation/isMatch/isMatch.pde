/** 
 * Blobscanner v. 0.1-a
 * Antonio Molinaro (c) 08/12/2013
 * Method usage example: 
 * isMatch(int x, int y, int blobToMatch)
 *
 * Returns true only if the pixel at the coordinates x y
 * is inside the blob represented by the parameter
 * <code>blobToMatch</code>.
 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>
 * must be called first to call this method. 
 * @param x The X coordinate of the pixel to test.
 * @param y The Y coordinate of the pixel to test.
 * @param blobToMatch The blob to match
 * @return  Returns true only if the pixel at the coordinates x y
 * is inside the blob represented by the parameter<code>blobToMatch</code>.
 */

import blobscanner.*;
PImage img ;
Detector bs;
int blobToMatch;

void setup(){
  size(200, 200);
  img = loadImage("blobs.jpg");
  bs = new Detector( this,255 );
  img.filter(THRESHOLD);
  frameRate(6);
}

void draw(){
  image(img, 0, 0);
  bs.imageFindBlobs(img);
  bs.loadBlobsFeatures();
  bs.weightBlobs(false);
  
   
  if(frameCount==4){           //The library starts to count the blobs from 0
    frameCount=0;              //so at 4 resets
    blobToMatch = (int)random(3);//and get a random blob to be matched.
 }
 
  
  //If the pixel at mouse location is inside a blob and the blob is not a match...
  if(bs.isMatch(mouseX, mouseY, frameCount) && frameCount!=blobToMatch){
  
  //gets the coordinates of all the pixels inside that blob  
  PVector[] pix = bs.getBlobPixelsLocation(frameCount); 
   
   //and paints them all in black.
   stroke(0);
   for(int j = 0; j < pix.length; j++){
      point(pix[j].x, pix[j].y);
   }
   println("Sorry, no match!");//sends a message to the console
  }
  //Else if the pixel at mouse location is inside a blob and and the blob it's a match...
  else if(bs.isMatch(mouseX, mouseY, frameCount)){
    
    //gets the coordinates of all the pixels inside that blob  
    PVector[] pix = bs.getBlobPixelsLocation(frameCount); 
  
    //and this time paints them all in red.
    stroke(255,0,0);
    for(int j = 0; j < pix.length; j++){
      point(pix[j].x, pix[j].y);
   }
    println("MATCH!!! you are on blob number  " + blobToMatch);//you WIN!!
  }
 
}  
   
        
