/*
 * blobscanner v. 0.1-a 
 * Antonio Molinaro (c) 17/12/2013
 * Defines a Region Of Interest where
 * blobscanner searches for blobs.
 */
 import blobscanner.*;
 
 
 Detector bd;
 PImage img;
 
 void setup()
 {
   img = loadImage("blobs.jpg");
   img.filter(THRESHOLD);
   size(img.width, img.height);
   
   int roisx = 50;
   int roisy = 90;
   int roiw = 200;
   int roih = 200;
   int minWeight = 1900;
   
   bd = new Detector( this, 255 );
   bd.setRoi(roisx, roisy, roiw, roih);
   
   image(img, 0, 0);
   
   img.loadPixels();
   bd.findBlobs(img.pixels,img.width,img.height);
   img.updatePixels();
   bd.loadBlobsFeatures();
   bd.weightBlobs(true); 
   
   bd.drawContours(color(0, 255, 0), 2);
   bd.drawSelectBox(minWeight, color(200, 200, 0), 1);
   bd.findCentroids();
   
   int bnum, count = 0;
   int x,y;
   
   noFill();
   stroke(255, 0, 0);
   
   beginShape(/*TRIANGLE_STRIP*/);
   for(int i=0; i<bd.getBlobsNumber(); i++)
   {
     println("Blob " + bd.getBlobNumberAt( (int)bd.getCentroidX(i) , (int)bd.getCentroidY(i)) + 
     " centroid is at x " + (int)bd.getCentroidX(i)+ " and y " + (int)bd.getCentroidY(i));
     if(bd.getBlobWeight(i) > minWeight){
     vertex(bd.getCentroidX(i), bd.getCentroidY(i));
     count++;
     }
   }
   endShape();
   
   rect(roisx, roisy, roiw, roih);
   
   println("Blobs inside region of interest:");
   println(bd.getBlobsNumber() +
   " of which " + count + " above minimum weight.\n");
   println(bd.version());
 
 }
