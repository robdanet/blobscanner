/*
 * Blobscanner v. 0.1-a 
 * by Antonio Molianro(c) 08/12/2013
 *
 * The library assign to each blob a label or if you
 * prefer an ID to identify it. This ID is necessary 
 * for many computer vision task, including the blob 
 * detection itself. Blobscanner allows to retrive the 
 * label of a blob in two ways, and those are by 
 * passing the x y screen coordinates of any of the 
 * blob's pixel or by passing the blob number. 
 */
import blobscanner.*;

Detector bs;
PImage img ;
PFont f = createFont(" ", 10);
  
void setup(){
  size(320, 240);
  bs = new Detector( this, 255 );
  img = loadImage("blobs.jpg");
  textFont(f, 10); 

}

void draw(){
  image(img, 0, 0);
  
    
  img.filter(THRESHOLD);
  
  bs.imageFindBlobs(img);
 //if the label at mouse position is the same as the current blob..
 for(int i = 0; i < bs.getBlobsNumber(); i++){
   if(bs.getLabel(i)==bs.getLabel(mouseX, mouseY)){
   //...draw the tab with the current blob's label number..
     tab();
   }
   //.. else if the label at mouse position is 0 (no blob)...
   else if(bs.getLabel(mouseX, mouseY)==0){
   fill(255, 0, 0);
   //...give message.
   text("LABEL IS 0", mouseX+5, mouseY+15);
  }  
 }
      
     
 
}

void tab(){
 
  noFill();
  stroke(255, 0, 0);
  rect(mouseX, mouseY, 30, 20);
  fill(0);
  //get the label of the pixels at the mouse location
  text(bs.getLabel(mouseX, mouseY), mouseX+5, mouseY+15);
  cursor(CROSS); 
 
}
  
