/* 
 * Blobscanner v. 0.2-a  
 * by Antonio Molinaro (c) 20/07/2013.
 * The boolean methods isEdge() returns
 * true if at the location with coordinates x y
 * there is a blob's edge pixel.
 * In this sketch the coordinates are stored    
 * in an array and then drawn to a PGraphics buffer, 
 * which in turn is drawnto the screen buffer. 
 */

import blobscanner.*;
import processing.video.*;

PGraphics edges;
Detector bs;
Capture frame;
 
final int MIN_WEIGHT = 900;
/* assumes max edges points to be a quarter of the frame */
final int MAX_EDGES = (160*120)/4 ; /* you may need to increase */ 
int[] X ;
int[] Y ;


void setup(){
     
      size(640, 480);
       
      frame = new Capture(this, 160, 120,"/dev/video0");
      frame.start();
      
      bs = new Detector(this, 255); 
      bs.setRoi(0, 0, 160, 120);
      bs.setPrecision(MIN_WEIGHT);
      edges = createGraphics(160, 120);
 }

void draw(){
 if (frame.available() == true) {
  frame.read();
 }
 
      
      bs.imageFindBlobs(frame);
      bs.loadBlobsFeatures();
      bs.weightBlobs(false);
      
      saveEdgeCoordinates();
      
      edges.beginDraw();
      edges.background(0);
 
      
      for(int i = 0; i < MAX_EDGES; i++){
         
            
            edges.stroke(0, 255, 0);
            
            edges.point(X[i], Y[i]);//or do what you want
          
      }
      edges.endDraw();
      
      /* copy off screen buffer to main buffer */
      copy(edges,0,0,160, 120,0,0,640, 480); 
      image(frame,0,0);
      bs.drawSelectBox(MIN_WEIGHT,color(0,255,0),2); 
  
}
 
 
 
void  saveEdgeCoordinates(){ 
   
      X = new int[MAX_EDGES];
      Y = new int[MAX_EDGES];
       int i = 0 ; 

        for (int y = 0; y < frame.height; y++) {
             for (int x = 0; x < frame.width; x++) {

                 if (bs.isEdge(x, y)){ 
                     
                      X[i] = x ;
                      Y[i] = y ;
  
                     i++;
 
                 }
             }
       }
 }

