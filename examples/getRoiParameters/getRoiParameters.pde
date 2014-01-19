/* blobscanner v. 0.1-a
 * by Antonio Molinaro (c) 27/12/2013
 * Demonstrate the usage of getRoiParameters
 * method. 
 */
 import blobscanner.*;
 
 Detector bd;
 PImage img;
 
 int roi_start_x;
 int roi_start_y;
 int roi_width;
 int roi_height;
 
 void setup()
 {
   img = loadImage("blackstone_UFO.png");
   size(img.width, img.height);
   
   bd = new Detector( this, 0 );
   //set the roi area
   bd.setRoi(width/4, height/4, width/2,
                               height/2);
   image(img, 0, 0);
   
   //draw a rectangle to make it visible
   noFill();
   stroke(255,0,0);
   rect(width/4, height/4, width/2,
                          height/2);
    
   //prepare the image foe blob detection
   img.filter(THRESHOLD);
   
   //find blobs in the image
   bd.imageFindBlobs(img);
   bd.loadBlobsFeatures();
   bd.drawBox(255,2);

   //collect roi's parameters
   roi_start_x = bd.getRoiParameters()[0];
   roi_start_y = bd.getRoiParameters()[1];
   roi_width   = bd.getRoiParameters()[2];
   roi_height  = bd.getRoiParameters()[3];  
  
   //draw a rectangle with them  
   noStroke();
   fill(255, 0, 0, 50);
   rect(roi_start_x,roi_start_y,roi_width,roi_height); 
 }
