/* 
 * Blobscanner v. 0.1-a 
 * by Antonio Molinaro - 08/12/2013.
 * Creates a syntetic blob image
 * and finds the blob mass.
 * If you click on the image the blob 
 * disappears and weightBlob() method
 * outputs a message to the console.
 */
import blobscanner.*;

Detector bd;

 
boolean white = true;
void setup(){
  size(320, 240);
  bd = new Detector( this, 255 );
  rectMode(CENTER); 
  background(0);
}

void draw(){
    
    if(white == true) 
    fill(255);
     
    noStroke();
    rect(width/2, height/2, 122, 122);
    fill(0);
     
    rect( mouseX ,constrain( mouseY/2,height/2, height/2),1, height);
    loadPixels();
    bd.findBlobs(pixels, width, height);
    updatePixels();
    
    //The parameter is used to print or not a message
    //when no blobs are found.
    bd.weightBlobs(true);
    //if we have blobs  
    if(bd.getGlobalWeight() > 0){
    //for each blob in the image..  
    for(int i = 0; i < bd.getBlobsNumber(); i++)
    //...computes the mass and prints it to the console.
    println("   The mass of blob #" + i + " is " + bd.getBlobWeight(i) + " pixels.");
  }
}
void mousePressed(){
  //hides the blob
  white=!white;
}

 
