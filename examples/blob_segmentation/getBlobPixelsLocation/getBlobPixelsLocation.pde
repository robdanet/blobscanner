/** 
 * Blobscanner v. 0.1-a
 * Antonio Molinaro (c) 08/12/2013
 * Method usage example:  
 * getBlobPixelsLocation(int blobnumber)
 *
 * Computes and returns the coordinates of all the
 * pixels in the specified blob.
 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>,
 * <code>weightBlobs(boolean)</code>,
 * and <code>loadBlobsFeatures()</code> methods
 * must be called before to call this method.
 * @param blobnumber The blob for which the pixels
 * coordinates are computed.
 * @return pixelsCoordinates The array of PVector objects
 * containing the blob pixels coordinates.
 */
  import blobscanner.*; 
  PImage blobs;
  Detector bs;
  int[] seconds = new int[60];
  int bn;

void setup(){
    size(200, 200);
    
    bs = new Detector(this,255);
   
    blobs = loadImage("blobs.jpg");
    image(blobs,0,0); 
    frameRate(1);
}
 
void draw(){
  
    blobs.filter(THRESHOLD);
    
    bs.imageFindBlobs(blobs);
    bs.loadBlobsFeatures();
    bs.weightBlobs(false); 
    
    image(blobs,  0, 0); 
    
    PVector[] pixloc = bs.getBlobPixelsLocation(bn);
    println("blob " + bn + " painted...");
    bn = frameCount;//syncronize bn with frameCount
    
    //if all blobs in the image have been painted, then reset all
    if(bn==bs.getBlobsNumber()){ bn = 0; frameCount = 0; }
     
       
    stroke(255,215,0);
    strokeWeight(1);
    
    //paints all the pixels inside the current blob (bn)
    for(int i = 0; i < pixloc.length; i++){
    point(pixloc[i].x , pixloc[i].y );
    }
 
}
