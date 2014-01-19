/* 
 * Blobscanner v. 0.1-a  
 * by Antonio Molinaro (c) 16/12/2013.
 * Draws the blob's bounding box.
 *
 */
import blobscanner.*;

Detector bd;

PImage img = loadImage("blobs.jpg");

size(img.width, img.height);

img.filter(THRESHOLD);

bd = new Detector( this, 255 );

image(img, 0, 0);

color boundingBoxCol = color(255, 0, 0);
int boundingBoxThickness = 1;

img.loadPixels();

bd.findBlobs(img.pixels, img.width, img.height);

// to be called always before using a method 
// returning or processing a blob's feature
bd.loadBlobsFeatures(); 

bd.drawBox(boundingBoxCol, boundingBoxThickness);

println(bd.version());
 

 
