/* 
 * Blobscanner v. 0.1-a
 * by Antonio Molinaro (c) 17/12/2013.
 * Computes width and height 
 * for each blob in the image.
 * 
 */
import blobscanner.*;

Detector bd;

PImage img = loadImage("blobs.jpg");

size(img.width, img.height);

img.filter(THRESHOLD);

bd = new Detector( this, 255 );

image(img, 0, 0);

bd.imageFindBlobs(img);

bd.loadBlobsFeatures();
 

//For each blob in the image..
for(int i = 0; i < bd.getBlobsNumber(); i++) {
  
//...compute and print the width and the height to the console.
println("BLOB " + (i+1) + " WIDTH IS " + bd.getBlobWidth(i)); 
println("BLOB " + (i+1) + " HEIGHT IS " + bd.getBlobHeight(i));
 
}
  
