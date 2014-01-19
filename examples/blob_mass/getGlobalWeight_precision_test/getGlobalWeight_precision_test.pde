/* 
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro - 08/12/2013.
 * Precision test for getGlobalWeigth() 
 * method. 
 */
import blobscanner.*;
 
  Detector bd;
  size(400, 200);

  background(0);
  noStroke();
   
  rect( 40, 40, 100, 100 );//  100 * 100 = 10000 +
  rect( 180, 40, 50, 100 );//   50 * 100 =  5000 +
  rect( 270, 40, 25, 100 );//   25 * 100 =  2500 +
  rect( 350, 40, 25, 50 );//    25 *  50 =  1250 +
  rect( 350, 140, 1, 1 );//      1 *   1 =     1 +
  rect( 327, 40, 1, 100 );//   100 *   1 =   100 = 18851
 
  

  bd = new Detector( this, 255 );
  loadPixels(); 
  bd.findBlobs(pixels, width, height);
  updatePixels();
  bd.loadBlobsFeatures();
  bd.weightBlobs(true);

  println("   The total blobs mass is " + bd.getGlobalWeight() + " pixels.");
  println("   The total blobs number " + bd.getBlobsNumber() + " blobs");
 

