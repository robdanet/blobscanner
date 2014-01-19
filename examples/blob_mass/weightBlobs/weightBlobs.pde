/* 
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro - 17/12/2013.
 * Compute the mass of each blob in the image.
 * 
 */
import blobscanner.*;

Detector bd;

PImage img = loadImage("blobs.jpg");

size(img.width, img.height);

img.filter(THRESHOLD);

bd = new Detector( this, 255);

bd.imageFindBlobs(img);

//The parameter is used to print or not a message
//when no blobs are found.
bd.weightBlobs(false);
//for each blob in the image..
for(int i = 0; i < bd.getBlobsNumber(); i++)
//computes and prints the mass.
println("   The mass of blob #" + (i+1) + " is " + bd.getBlobWeight(i) + " pixels.");
 
image(img, 0, 0);
