/* 
 * Blobscanner v. 0.1-a  
 * by Antonio Molinaro (c) 08/12/2013.
 * For each blob in the image computes 
 * and prints the center of mass 
 * coordinates x y to the console
 * Also draws a point at their location. 
 *   
 * 
 */
import blobscanner.*;

Detector bd;

PImage img;
PFont f = createFont("", 10);

 
size(200, 200);
img = loadImage("blobs.jpg");
img.filter(THRESHOLD);
textFont(f, 10);

bd = new Detector(this, 255 );
 
  
image(img, 0, 0);

bd.imageFindBlobs(img);

 
//To be called before quering
//the library.
bd.loadBlobsFeatures();

strokeWeight(5);
stroke(255, 0, 0);
 
//Computes the blob center of mass. 
//Replaces findCentroids(boolean,boolean)
//since v. 0.1-alpha.Also no more need to call 
//weigthBlobs before it;
bd.findCentroids();
point(bd.getCentroidX(0), bd.getCentroidY(0));
fill(0,200,100);
text("x-> " + bd.getCentroidX(0) + "\n" + "y-> " + bd.getCentroidY(0), bd.getCentroidX(0), bd.getCentroidY(0)-7);
println("Blob 0 has centroid's coordinates at x:" 
            + bd.getCentroidX(0) 
            + " and y:" 
            + bd.getCentroidY(0)); 
            
println(bd.version());
