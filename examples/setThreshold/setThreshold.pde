/*
 * Blobscanner v. 0.1-a
 * by Antonio Molinaro 26/12/2013 (c) 
 * Demonstrates the new constructor's
 * usage and the setThreshold method.
 */
 import blobscanner.*;
 Detector bd;
 int brightness;
 boolean thresholdIsSet = false;
 
 int []alphaVal = {2, 4, 8, 16, 32, 64, 128, 255}; 
 
 void setup()
 {
   size(580,140);
   bd = new Detector(this); 
 }
 
 void draw()
 {
   drawScene();
   loadPixels();
   bd.findBlobs(pixels, width, height);
   updatePixels();
   if(thresholdIsSet)
   bd.drawContours(color(255, 90, 120),3);
   
 }
 void drawScene()
 {
   background(0);
   stroke(255,90);
   for (int i = 0; i<alphaVal.length; i++) {
     fill(255, alphaVal[i]);
     rect(20 + i * 50 + 20 * i, 20, 50, 100);
   }
   
 }
 
 //grabs the pixel brightness value and
 //sets the detector's threshold with it
 void mouseClicked(){
    brightness = (int)brightness(get(mouseX, mouseY)); 
    bd.setThreshold(brightness);                       
    thresholdIsSet = true;
    println(brightness);
 }
 
 void keyPressed() {
   thresholdIsSet =! thresholdIsSet;
 }
  
