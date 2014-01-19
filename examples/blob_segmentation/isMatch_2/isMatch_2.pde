/*
  Blobscanner v. 0.1-a
  Antonio Molinaro (c) 08/12/2013
  An image segmentation example using isMatch method. The sketch starts
  in manual mode, segmenting the image on each mouse click.
  Pressing key 'l' the program enters in auto mode.
  Pressing key 'l' again, returns to manual mode. 
 */

import blobscanner.*;

Detector bd; 
PImage img ;
PFont f ;
PImage dest ; 
int x, y;
int t;
boolean loop  = false; 

void setup()
{
  size(190, 190);
  img = loadImage("seg_test.jpg");// an image with a more compless connected component
  //img = loadImage ("blobs.jpg");// a simple image with 16 white blobs on black background 
  bd = new Detector( this, 255 );
  dest = createImage(img.width, img.height, RGB); 
  f = createFont("Courrier", 10);
  textFont(f, 10); 
  cursor(CROSS);
  println("MANUAL\tpress l to go in auto mode or");//prints the starting state
  println("\tclick the mouse to redraw the frame\n" );
}

void draw()
{
  img.filter(THRESHOLD); 
  image(img, 0, 0);

  bd.imageFindBlobs(img); 

  loadPixels();
  //for each blob
  for (int i = 0; i < bd.getBlobsNumber(); i++)
  {
    //acquires a new color 
    color c = setColor(i); 
    //for each pixel  
    for (int y = 0; y < height ; y++)
    {
      for (int x =0; x < width ; x++)
      {
        //if the pixel belongs to the current blob, set it to the new color
        if (bd.isMatch(x, y, i)) { 
          pixels[x+y*width] = c ;
        }
      }
    }
  }
  updatePixels(); 
  //draws the tab
  tab();
  if (!loop)noLoop();
}

//Generates random colors
color setColor(int bnum)
{ 
  color setcolor = 0;
  for (int i = 0; i < bd.getBlobsNumber(); i++)
  { 
    setcolor = color(random(255), random(255), random(255));
  } 
  return setcolor;
}

//Draws a tab showing the label of the blob at the
//mouse position. If no blob is 
//present(e.g. on the drawing's lines)at that position, a message is given. 
void tab() {   
  int x=0; 
  int y=0;
  //makes sure the tab remains in the window space
  x = constrain(mouseX, 0, width-90);
  y = constrain(mouseY, 0, height-18);

  //tab's rectangle
  fill(0, 145);
  textFont(f, 10);
  rect(x, y, 90, 18);

  //tab's text 
  fill(255, 0, 0  );
  if (bd.isBlob(mouseX, mouseY)) {
    text("blob label is " + bd.getLabel(mouseX, mouseY), x+5, y+13);
    fill(255);
    text("blob label is " + bd.getLabel(mouseX, mouseY), x+6, y+14);
  }
  else {
    text("no blob", x+5, y+13);
    fill(255);
    text("no blob", x+6, y+14);
  }
} 

//redraws the frame
void mousePressed()
{
  redraw();
}
//changes from manual to automatic frame redrawing 
void keyPressed()
{
  if (key == 'l') { 
    loop ^= true; 
    loop();
    if (loop) {
      println("AUTO\tpress l to return in manual mode\n\n");
    }
    else {
      println("MANUAL\tpress l to go in auto mode or" ); 
      println("\tclick the mouse to redraw the frame\n" );
    }
  }
}

