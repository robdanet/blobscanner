/* 
 * Blobscanner v. 0.1-a
 * by Antonio Molinaro (c)  08/12/2013.
 * Compute the number of blobs in the image.
 * Check the next example for a faster
 * method {findBlobs()}
 */
import blobscanner.*;

Detector bd;

PImage img = loadImage("blobs.jpg");

size(img.width, img.height);

img.filter(THRESHOLD);
 
bd = new Detector( this, 255 );

bd.imageFindBlobs(img);

println(bd.getBlobsNumber() + " BLOBS FOUND.");
 
image(img, 0, 0);
