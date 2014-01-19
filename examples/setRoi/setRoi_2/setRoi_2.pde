/*
 * blobscanner v. 0.1-a 
 * Antonio Molinaro (c) 17/12/2013
 * This sketch shows how to use the
 * new method seRoi(). When you press
 * a key, blobscanner search for blobs
 * only inside the 'region of interest' 
 * defined with this method.
 */

import blobscanner.*;

Detector bd;
PImage img;
boolean roiIsSet = false;
int roiStartX, roiStartY, roiWidth, roiHeight;
 

void setup()
{
   
  size(400, 400);
  img = loadImage("blobs.png");
 
  roiStartX = roiStartY = 20;
  roiWidth = roiHeight = 100; 
  
  bd = new Detector( this, 0 );
  
  print("Press any key...\n"); 
}

void draw()
{ 
   if(roiIsSet){ 
    bd.setRoi(roiStartX, roiStartY, roiWidth, roiHeight);
    rect(80,75,100,120);
   }else{  
      bd.unsetRoi();
   }
   image(img,  0,  0); 
   img.filter(THRESHOLD);
   
   img.loadPixels();
   bd.findBlobs(img.pixels,img.width,img.height);
   
   bd.loadBlobsFeatures();
  
   bd.drawBox(color(0,255,255),1);
   bd.drawContours(color(255,0,255),1);
   
   if(roiIsSet){
    pushStyle();
    noFill();
    stroke(255, 0, 0); 
    rect(roiStartX, roiStartY, roiWidth, roiHeight);
    popStyle();
   }
   noLoop();
 }
 
 
 void keyPressed()
 {
   roiIsSet =! roiIsSet;
   loop();
 }
 
 void keyReleased()
 {
   println(bd.getBlobsNumber()+ " blobs found");
   if(roiIsSet)
   println("in the region of interest.\n");
   else
   println("in the image.\n");
 }
