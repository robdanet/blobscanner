/* 
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro (c) 09/12/2013.
 * Draws the blobs boundingbox only
 * for the blobs with mass equals or bigger
 * to the specified value.
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
int boundingBoxThickness = 2;
int minimumWeight = 1900;

img.loadPixels();

bd.findBlobs(img.pixels, img.width, img.height);
// to be called always before using a method 
// returning or processing a blob feature
bd.loadBlobsFeatures(); 
// to be called always before an operation 
// involving the blob's weight( mass )
bd.weightBlobs(true);   
bd.drawSelectBox(minimumWeight, boundingBoxCol, boundingBoxThickness);

println(bd.version());
 

 
