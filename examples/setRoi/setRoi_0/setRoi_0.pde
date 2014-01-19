/*
 * Blobscanner v. 0.1-a
 * by Antonio Molinaro (c) 26/12/2013  
 * Defines a Region Of Interest where
 * blobscanner searches for blobs.
 * Usage: click the right button to create blobs
 *        then, while clicking the left button, drag a 
 *        rectangle on them. The rectangle will be 
 *        visible when the button is released. The central 
 *        button clears the scene.
 */
 import blobscanner.*;
 
 int oldmousex = 0;
 int oldmousey = 0;
 
 Detector bd;
 
 void setup()
 {
   size(640, 240);
   background(0); 
   bd = new Detector( this, 255 ); 
 }
 
 void draw(){}
 
 void mouseReleased()
 { 
   
   if(mouseButton == LEFT)
   { 
        
     bd.setRoi(oldmousex, oldmousey, mouseX-oldmousex, mouseY-oldmousey);
      
     loadPixels();
     bd.findBlobs(pixels, width, height);
     updatePixels();
     
     pushStyle(); 
     noFill();
     stroke(0,250,0);
     rect(oldmousex, oldmousey, mouseX-oldmousex, mouseY-oldmousey);
     
     bd.drawContours(color(255,0,0),2);
     popStyle();
     
     println(bd.getBlobsNumber() 
         + " blobs found in the last region selected.");
   }
    
 }
 
 void mousePressed()
 {
   if(mouseButton == LEFT)
   { 
     oldmousex = mouseX;
     oldmousey = mouseY;
   }
   
   if(mouseButton == RIGHT)
   {
     float r = random(40);
     
     noStroke();
     ellipse(mouseX, mouseY, r,r);
      
     
   }
   
   if(mouseButton == CENTER)
     background(0);
 }

