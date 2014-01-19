/* 
 * Blobscanner v. 0.1-a  
 * by Antonio Molinaro - 05/12/2013.
 * The boolean methods isEdge() returns
 * true if at the location with coordinates x y
 * there is a blob's edge pixel.
 * The edges coordinate are stored    
 * in an array and then drawn to
 * a PGraphics buffer. 
 *  
 */

import blobscanner.*;
import processing.video.*;

PGraphics edges;
Detector bs;
Capture frame;
 
final int minimumWeight = 900;
final int MAXEDGES = (160*120)/4 ; // assume max edges point a quarter of the frame
int[] X ;
int[] Y ;


void setup(){
     
      size(640, 480);
       
      frame = new Capture(this, 160, 120,"/dev/video0");
      frame.start();
      bs = new Detector(this, 255); 
      edges = createGraphics(160, 120);
 }

void draw(){
 if (frame.available() == true) {
  frame.read();
 }
 
      
      bs.imageFindBlobs(frame);
      bs.loadBlobsFeatures();
      bs.weightBlobs(false);
      
      getEdgeCoordinates();
      
      edges.beginDraw();
      edges.background(0);
 
      
      for(int i = 0; i < MAXEDGES; i++){
         
            
            edges.stroke(0, 255, 0);
            
            edges.point(X[i], Y[i]);//or do what you want
          
      }
      edges.endDraw();
      
      copy(edges,0,0,160, 120,0,0,640, 480);
      image(frame,0,0);
      bs.drawSelectBox(minimumWeight,color(0,255,0),2); 
  
}
 
 
 
void  getEdgeCoordinates(){ 
   
      X = new int[MAXEDGES];
      Y = new int[MAXEDGES];
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

