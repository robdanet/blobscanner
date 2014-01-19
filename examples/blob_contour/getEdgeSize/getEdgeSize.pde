
/**
  * Blobscanner v. 0.1-a
  * by A.Molinaro(c) 08/12/2013.
  * Method usage example:  
  * getEdgeSize(int blobnumber)
  *
  * Computes the number of edge pixel for
  * the specified blob.
  * findBlobs() or imageFindBlobs(),loadBlobsFeatures()
  * must be called before this method.   
  */
import blobscanner.*;
PImage blobs;
Detector bs;
PFont f ;
int bn = 0;
int i;

void setup(){
  
  size(200, 200);
  f = createFont("",10);
  textFont(f,10);
  bs = new Detector(this,255);  
  blobs = loadImage("blobs.jpg"); 
  image(blobs,0,0);
  blobs.filter(THRESHOLD); 
}
 
void draw(){
   
  bs.imageFindBlobs(blobs);
  bs.loadBlobsFeatures();
   
   
  PVector[] edge = bs.getEdgePoints(bn);
  println(bs.getEdgeSize(bn));
   
   
  stroke(255,0,0);strokeWeight(2);
  point(edge[i].x , edge[i].y );
  i++;
  if(i==edge.length){i=0;image(blobs,0,0);}
   
  if(i%bs.getEdgeSize(bn)==0){
  bn++;
  text("blob " + (bn-1) +" edge has " + edge.length + " pixels" , 10,10);
  if(bn==bs.getBlobsNumber()) bn=0;
  }
  println(edge.length); 
  fill(0);
  noStroke();
  rect(10,10,120,20);
  fill(255); 
  text("blob " + bn + " edge points " + i,10,20); 
  println(bs.getBlobsNumber()); 
}
