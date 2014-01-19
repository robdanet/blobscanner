 /* 
  * Blobscanner v. 0.1-a
  * by Antonio Molinaro (c)  08/12/2013.
  * Method usage example: 
  * getEdgePoints(int blobnumber)
  *
  * Computes the coordinates of the edge's pixels for
  * the specified blob.
  * findBlobs() or imageFindBlobs(), and loadBlobsFeatures()
  * must be called before this method. 
  */
import blobscanner.*;
PImage blobs;
Detector bs;
final int STDANDARD = 296;//tile standard size
PVector []  edge  ; 
int i;

void setup(){
  size(413, 235);
  //we look for black blobs so last param value is 0
  bs = new Detector( this, 0 );
  blobs = loadImage("tiles.png"); 
  image(blobs,0,0); 
}
 
void draw(){
  int edgelen = 0;
  blobs.filter(THRESHOLD);
  bs.imageFindBlobs(blobs);
  bs.loadBlobsFeatures();
  
  strokeWeight(2); 
  
  //For each blob
  for(int i = 0; i < bs.getBlobsNumber(); i++){
  
  //gets the edge's pixels coordinates  
   edge  = bs.getEdgePoints(i); 
   
   //if its size is not standart
   if(edge.length != STDANDARD){
   
   //paints the pixels in red  
   for(int k = 0; k < edge .length;k++){
     stroke(255,0,0); 
     point(edge[k] .x , edge[k] .y );
   }
   
   //and sends to the std. output a warning 
   println("Tile " + (i+1) + " size " + edge .length 
         + " ERROR! - tile " + (i+1)+ " malformed");
 
 //otherwise paints the pixels in green
 }else{
    for(int k = 0; k < edge .length;k++){
     stroke(0,255,0); 
     point(edge[k] .x , edge[k] .y );
    }
     //and sends to the std. output an ok. 
     println("Tile " + (i+1) + " size " + edge .length +" --OK");
   } 
} 
   noLoop();
}
