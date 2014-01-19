/* 
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro - 08/12/2013.
 * Draws the blobs contours only
 * for blobs with mass equals or bigger
 * of the specified value.
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
int contoursThickness = 2;
int minimumWeight = 1900;

img.loadPixels();

bd.findBlobs(img.pixels, img.width, img.height);

// to be called always before using a method 
// returning or processing a blob feature
bd.loadBlobsFeatures();

// to be called always before an operation 
// involving the blob's weight( mass )
bd.weightBlobs(true);   
bd.drawSelectContours(minimumWeight, contoursCol, contoursThickness);
 

 
