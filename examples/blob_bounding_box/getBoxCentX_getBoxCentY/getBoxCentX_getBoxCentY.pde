/* 
 * Blobscanner v. 0.1-a  
 * by Antonio Molinaro (c) 08/12/2013.
 * For each blob in the image computes and
 * prints the blob's bounding box center  
 * coordinates x y to the console
 * and draws a line from their location 
 * to the mouse location. 
 */
import blobscanner.*;

Detector bd;

PImage img;
PFont f = createFont("", 10);

void setup(){
size(200, 200);
img = loadImage("blobs.jpg");
img.filter(THRESHOLD);
textFont(f, 10);

bd = new Detector( this, 255 );
 
}

void draw(){
image(img, 0, 0);

bd.imageFindBlobs(img);
 
bd.loadBlobsFeatures();
//Draws the bounding box for all the blobs in the image.
bd.drawBox(color(0, 100, 255),1);

stroke(255, 100, 0); 

//For each blob in the image..
for(int i = 0; i < bd.getBlobsNumber(); i++) {
  
stroke(255, 100, 0); 
  
//...computes and prints the bounding box center coordinates x y to the console...
println("BLOB " + (i+1) + " BOUNDING BOX CENTER X COORDINATE IS " + bd.getBoxCentX(i));
println("BLOB " + (i+1) + " BOUNDING BOX CENTER Y COORDINATE IS " + bd.getBoxCentY(i));
println("\n");
//...and draws a line from them to the mouse coordinates.
line(bd.getBoxCentX(i), bd.getBoxCentY(i), mouseX, mouseY);
stroke(0);
point(bd.getBoxCentX(i), bd.getBoxCentY(i));
//if the mouse is at 2 pixels from the center of a blob...
  if((int)dist(mouseX,mouseY,bd.getBoxCentX(i),bd.getBoxCentY(i))< 2){
//...draws this green shape over that blob.  
  stroke(0, 255, 0);
  noFill();
  beginShape(QUADS);
  vertex(bd.getA()[i].x,bd.getA()[i].y);
  vertex(bd.getB()[i].x,bd.getB()[i].y);
  vertex(bd.getC()[i].x,bd.getC()[i].y);
  vertex(bd.getD()[i].x,bd.getD()[i].y);
  endShape();
 }
}

fill(255, 0, 0);
text("-> mouseX " + mouseX, 20, 10);
text("-> mouseY " + mouseY, 20, 20);
text(bd.version(),100,height-5);
}
  
