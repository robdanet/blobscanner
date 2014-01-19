/*
 *
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro(c) 08/12/2013
 *
 * The library assign to each blob a label or if you
 * prefer an ID to identify it. This ID is necessary 
 * for many computer vision task, 
 * including the blob detection itself. Blobscanner
 * allows to retrive the label of a blob in two ways,
 * and those are by passing the x y screen coordinates 
 * of any of the blob's pixel or 
 * by passing the blob number. 
 *
 */

import blobscanner.*;

Detector bs;
PImage img ;
 

void setup(){
  size(320, 240);
  bs = new Detector( this, 255 );
  img = loadImage("blobs.jpg");
 
}

void draw(){
  image(img, 0, 0);
  img.filter(THRESHOLD);
  bs.imageFindBlobs(img);
 
  for(int i = 0; i < bs.getBlobsNumber(); i++)
  println("blob #" + i + " is labelled with " + bs.getLabel(i));
  noLoop();
}
  
