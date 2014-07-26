/* 
 * Blobscanner v. 0.2-a  
 * by Antonio Molinaro (c) 20/07/2013.
 * Computes the total blobs mass.
 * 
 */
import blobscanner.*;

Detector bd;

PImage img = loadImage("blobs.jpg");

size(img.width, img.height);

img.filter(THRESHOLD);

bd = new Detector( this, 255 ); 
bd.imageFindBlobs(img);
 
println("   The total blob's mass is " + bd.getGlobalWeight() + " pixels.");
 

image(img, 0, 0);
