/*
 * Blobscanner v. 0.1-a
 * by Antonio Molinaro 26/12/2013 (c)
 * Demonstrates the usage of the unsetRoi
 * method.
 */
import blobscanner.*;

Detector bd;
PImage img;
boolean roiIsSet = false;
int[]p;

void setup()
{
  img = loadImage("blobs.png");
  size(img.width, img.height);
  bd = new Detector( this , 0 );p = new int[4];
}

void draw()
{
  image(img, 0, 0); 
  img.loadPixels();
  bd.findBlobs(img.pixels, img.width, img.height);
  bd.loadBlobsFeatures();
  bd.drawBox(color(255,0,0),2);
}

void mouseClicked()
{
  bd.setRoi(20,20,120,200); 
}

 
void keyPressed()
{
   if(key == 'u')
   bd.unsetRoi();
   
   if(key == 'p'){
     p = bd.getRoiParameters();
     for(int i=0;i<p.length;i++){
       println(p[i]); 
     } 
   }
 }
