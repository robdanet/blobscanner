/**
 * Blobscanner v. 0.1-a
 * by A.Molinaro(c) 18/12/2013.
 * Method usage example:  
 * drawBlobContour(int blobnumber,int contourColor,float thickness)
 *
 * Draws the blob contour for the blob specified by
 * <code> blobnumber</code> parameter value.
 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>
 * and <code>loadBlobsFeatures() </code> methods must be called
 * before to call this method.
 * @param  blobnumber The blob which contours are drawn
 * @param  contourColor The contour color. Here it can be passed color( r, g, b)
 * Processing method.
 * @param  thickness The contour thickness. This parameter is passed
 * to <code>strokeWeight</code> Processing method.
 * @see #loadBlobsFeatures()
 **/
import blobscanner.*; 
PImage blobs;
Detector bs;
PVector[] edgeCoordinates ;
int bn ; //current  blob number

void setup(){
  size(200, 200);
  bs = new Detector(this,0,0,200,200,255);
  blobs = loadImage("blobs.jpg");
  frameRate(30);
}

void draw(){
  blobs.filter(THRESHOLD);
  bs.imageFindBlobs(blobs);
  bs.loadBlobsFeatures();
   
  image(blobs,0,0);
 
  if(frameCount%30==0)//increments bn each second
  bn++; 
  //if bn is equal to the number of blobs in the image, then reset it
  if(bn==bs.getBlobsNumber())bn=0;
  
  //draws the blob contour for the blob specified by bn
  bs.drawBlobContour(bn,color(255, 0, 0),2);
 
 }
