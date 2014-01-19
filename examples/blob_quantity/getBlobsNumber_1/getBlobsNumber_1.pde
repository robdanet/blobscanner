/* 
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro (c) 08/12/2013.
 * Finds the number of blobs in the image.
 * In this example the method findBlob(int[], int, int)
 * is used instead of imageFindBlobs(PImage).
 * This method is faster.
 */
import blobscanner.*;

Detector bd;

PImage img = loadImage("blobs.jpg");

size(img.width, img.height);

img.filter(THRESHOLD);

bd = new Detector( this, 255 );

img.loadPixels();

bd.findBlobs(img.pixels, img.width, img.height);

println(bd.getBlobsNumber() + " BLOBS FOUND.");
 
image(img, 0, 0);
