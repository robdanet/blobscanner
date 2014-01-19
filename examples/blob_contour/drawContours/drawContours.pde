/* 
 * Blobscanner v. 0.1-a
 * by Antonio Molinaro - 08/12/2013.
 * Draws the blobs contours.
 *
 */
import blobscanner.*;

Detector bd;

PImage img = loadImage("blobs.jpg");

size(img.width, img.height);

img.filter(THRESHOLD);

bd = new Detector( this, 255 );

image(img, 0, 0);

color contoursCol = color(255, 0, 0);
int contoursThickness = 1;

img.loadPixels();

bd.findBlobs(img.pixels, img.width, img.height);
 
bd.loadBlobsFeatures(); 
bd.drawContours(contoursCol, contoursThickness); 
 

 
