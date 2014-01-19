/**
 * Blobscanner 0.1-alpha 
 * Antonio Molinaro (c) 2014
 *
 * This program is a library for the Processing programming environment
 * (see <http://www.processing.org/>)and can be used for blob detection
 * and analysis in image and video. It can be also useful for image processing 
 * and image segmentation.
 *
 * This program is free software and it is distributed
 * under GPL v3(General Public License):
 * you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a>http://www.gnu.org/licenses</a>.
 *
 * For bugs and suggestions email to blobdetector.info@gmail.com or visit the
 * project site  <a>http://sites.google.com/site/blobscanner/home</a>.
 *
 * @version 0.1-alpha
 * @author Antonio Molinaro
 * @date 18/01/14 15:26:48 
 *
 */

 package blobscanner;
 
 import processing.core.PApplet;
 import processing.core.PImage;
 import processing.core.PVector;
 
 
public class Detector {
	/** The parent PApplet */
	private PApplet p5;

	/** The brightness value ( 0 - 255 ) of the blobs searched. */
	private int threshold;

	/** The label's buffer. */
	private int[][] MyLabels;

	/** The pixel group buffer (blob or not-blob pixels). */
	private int[][] MyGroup;

	/** Holds the total number of blob pixels. */
	private int countBlobPixel;

	/** Neighbours x coordinates for labelling kernel. */
	private final int[] dx = { -1, 0, 1, -1 };

	/** Neighbours y coordinates for labelling kernel. */
	private final int[] dy = { -1, -1, -1, 0 };

	/** Kernel of neighbours x coordinates used for blob's edge detection. */
	private final int xkern[] = { 1, 0, -1, 0, 1, -1, -1, 1 };

	/** Kernel of neighbours y coordinates used for blob's edge detection. */
	private final int ykern[] = { 0, -1, 0, 1, -1, -1, 1, 1 };

	/** Holds the blobs labels, one for each blob. */
	private int labelTab2[];

	/** The label */
	private int Label;

	/** Holds the x coordinates of the blob's edge pixels */
	private int[] edgeX;

	/** Holds the y coordinates of the blob's edge pixels */
	private int[] edgeY;

	/** The width of the rectangular area scanned for blobs. *///TODO change name 
	private int w;

	/** The height of the rectangular area scanned for blobs. */
	private int h;

	/**
	 * The x coordinate of the top left corner of the rectangular area scanned
	 * for blobs.
	 */
	private int sx;

	/**
	 * The y coordinate of the top left corner of the rectangular area scanned
	 * for blobs.
	 */
	private int sy;

	/** The total number of blobs for the current frame. */
	private int blobNumber;
	 
	/** Region of interest variables @Since 0.1-a*/

 	private int sroix;  

	private int sroiy;

	private boolean roiIsSet = false;

	/** Holds the list of the weight of each blob in the current frame. */
	private int blobWeightList[];

	/**
	 * The lists of coordinates of the four corners of the blob's bounding box.
	 * 
	 *  A---B 
	 *  |   |
	 *  C---D
	 */
	private PVector[] A;//top left
	private PVector[] B;//top right
	private PVector[] C;//lower left
	private PVector[] D;//lower right
	
 
	/** The list of x coordinates of the blobs center of mass. */
	private float[] CenterOfMX;

	/** The list of y coordinates of the blobs center of mass. */
	private float[] CenterOfMY;
	
	@Deprecated
	/**
	 * The list of coordinates of the the mid point on the blob's bounding box
	 * side.
	 */
	private PVector[][] crosspoints;
	
	/** Variables used mainly to save  blob's bounding box. */
	private int Ax;
	private int Ay;
	private int Bx;
	private int By;
	private int Cx;
	private int Cy;
	private int Dx;
	private int Dy;
	
	private boolean blobWeighted = false;

	public final static String VERSION = "v. 0.1-a";

	/**
	 * <code> Detector </code> class constructor.
	 * This has been kept for backward compatibility.It'll get
	 * deprecated in future versions.
	 *  
	 * @param p5 Reference to register with the host PApplet.
	 * 
	 * @param sx The x coordinate of the top left corner of the rectangular area
	 * scanned for blobs.
	 * 
	 * @param sy The y coordinate of the top left corner of the rectangular area
	 * scanned for blobs.
	 * 
	 * @param w The width of the rectangular area scanned for blobs.
	 * 
	 * @param h The height of the rectangular area scanned for blobs.
	 * 
	 * @param threshold Blobscanner will search for blobs which brightness is
	 * equals to this parameter's value.  
	 */

	public Detector(PApplet p5, int sx, int sy, int w, int h, int threshold) {

		this.threshold = threshold;
		this.p5 = p5;
		this.sx = sx;
		this.sy = sy;
		this.w = sx + w;
		this.h = sy + h;
		MyLabels = new int[h][w];
		MyGroup = new int[h][w];
	}

	/**
	 * <code> Detector </code> class new constructor.
	 * Initialise the starting point and size of the area 
	 * scanned for blobs to a default corresponding to the 
	 * parent PApplet size. Use <code>setRoi()</code> to modify
	 * this parameters.
	 * @link #setRoi
	 * @Since 0.1-alpha  
	 * @param p5
	 *            Reference to register with the host PApplet.
	 * @param threshold
	 *            Blobscanner will search for blobs which brightness is equals
	 *            to this parameter's value.
	 */
	
	public Detector(PApplet p5, int threshold) {

		this.threshold = threshold;
		this.p5 = p5;
		this.sx = 0;
		this.sy = 0;
		this.w = p5.width;
		this.h = p5.height;
		MyLabels = new int[h][w];
		MyGroup = new int[h][w];

	}
	
	/**
	 * <code> Detector </code> class new constructor.
	 * Initialise the starting point and size of the area 
	 * scanned for blobs to a default corresponding to the 
	 * parent PApplet size. Use <code>setRoi()</code> to modify
	 * this parameters.
	 * @link #setRoi
	 * @Since 0.1-alpha  
	 * @param p5
	 *            Reference to register with the host PApplet.
     */
	
	public Detector(PApplet p5) {
		
		this.p5 = p5;
		this.sx = 0;
		this.sy = 0;
		this.w = p5.width;
		this.h = p5.height;
		MyLabels = new int[h][w];
		MyGroup = new int[h][w];
    }
	
	/**
	 * Sets the brightness value of the blobs to be detected.
         * Use this method only on objects created with the 
	 * new constructor Detector(PApplet).
	 * 
	 * @param
	 * 			threshold The brightness value of the blobs to be detected. 
	 */
	public void setThreshold(int val){
		threshold = val;
	}
	
	/**
	 * Returns the brightness value of the blobs searched.
	 * 
	 * @return
	 * 		 The brightness value of the blobs to be detected. 
	 */
	public int getThreshold(){
		return threshold ;
	}
 
	/**
	 * Defines a Region Of Interest where Blobscanner will search for blobs.
	 * Use this method only on objects created with the 
	 * new constructor Detector(PApplet, int).
	 * 
	 * @Since 0.1-alpha
	 * @param sroix Coordinate x of ROI starting point.
	 * 
	 * @param sroiy Coordinate y of ROI starting point.
	 * 
	 * @param roiw Width of ROI.
	 * 
	 * @param roih Height of ROI.
	 */
 
  	public void setRoi(int sroix,int sroiy,int roiw,int roih)
	{
		try
		{
		 	 this.sroix = sroix;
			 this.sroiy = sroiy;
			 sx = 0;
			 sy = 0;
			 w =  roiw;
			 h =  roih;
			 MyLabels = new int[h][w];
			 MyGroup = new int[h][w];
			 roiIsSet = true;
		}
		catch(NegativeArraySizeException e)
		{
			 
			 PApplet.println("Error!:" +
					 "The first two arguments of setRoi must represent\n" +
					 "the top-left corner of the region of interest." +
					   version());
			 
		}
		 
	}
	
	/**
	 * Resets all global parameters and buffers.
	 */
	public void unsetRoi(){  
		if(roiIsSet){
			sx = 0;
			sy = 0;
			w = p5.width; 
			h = p5.height;
			MyLabels = new int[h][w];
			MyGroup = new int[h][w];
			roiIsSet = false;
		}
		else
		{
			PApplet.println("Roi is not set\nunset operation failed.\n" +
						version());
		}
	}
	
	/**
	 * Returns the four parameters of ROI
	 * 
	 * @return An array of int filled with the values of the roi parameters.
	 */
	public int[] getRoiParameters()
	{
		int []parameters = new int[4];
		if(roiIsSet){
				 
				parameters[0] = sroix;
				parameters[1] = sroiy;
				parameters[2] = w;
				parameters[3] = h;
		}
		else 
		{
			PApplet.println("Error!:\nRoi is not set. Please use first setRoi\n" +
							"to define a region of interest,\n" +
							"before calling getRoiParameters\n" +
					version());
		}
		return parameters;
	 }
	 
	
	/**
	 * Initialises the labels buffer.
	 */
	
	private void initBuffers() {

		for (int y = sy; y < h; y++) {
			for (int x = sx; x < w; x++) {
				MyLabels[y][x] = 0;
				 MyGroup[y][x] = 0;
			}
		}
	}
	
	/**
	 * Compute the connected components and fills the label buffer.
	 */
	
	private void doLabel() {

            // start labelling from 1
            Label = 1; 
	    int sumTest = 0;

	    // The smallest label found locally
            // when dx[] and dy[] kernels are applied.
	    int smallLabel = 0;

		for (int y = sy; y < h; y++) {
			for (int x = sx; x < w; x++) {

				 
					// if is a blob's pixel apply kernel
					if (MyGroup[y][x] == 1) {
						for (int i = 0; i < 4; i++) {
							if(inside(x + dx[i],y + dy[i]))
							sumTest += MyLabels[y + dy[i]][x + dx[i]];
						}
						// if the local sum is 0
						if (sumTest == 0) {
							// assigns new label
							MyLabels[y][x] = Label;
							// increment label
							Label++;
						} else {
							// reset for next pixel
							sumTest = 0;
							// find the smallest label...
							int labelLocalList[] = new int[4];

							for (int i = 0; i < 4; i++) {
								if(inside(x + dx[i],y + dy[i]))
								labelLocalList[i] = MyLabels[y + dy[i]][x
								                                        + dx[i]];
							}
							labelLocalList = PApplet.sort(labelLocalList);
						 
							 smallLabel = res4(labelLocalList);
							// label the matrix space...
							for (int i = 0; i < 4; i++) {
								if(inside(x + dx[i],y + dy[i]))
								if (MyGroup[y + dy[i]][x + dx[i]] == 1) {
									MyLabels[y + dy[i]][x + dx[i]] = smallLabel;
								}
							} 
							// ..and finally assign the label
							// to the pixel @ (x,y) position
							MyLabels[y][x] = smallLabel;
						}
					}
				 }
			}
			
		// repeat in inverse direction to merge
		// blobs with more than a label
		for (int y = h-1; y >= sy; y--) {
			for (int x = w-1; x >= sx; x--) {

				 
				    if (MyLabels[y][x] > 0) {
						int labelLocallList_2[] = new int[4];
						for (int i = 0; i < 4; i++) {
							if(inside(x + dx[i],y + dy[i]))
							labelLocallList_2[i] = MyLabels[y + dy[i]][x + dx[i]];
						}
						labelLocallList_2 = PApplet.sort(labelLocallList_2); 
				       	        smallLabel = res4(labelLocallList_2);
				        
						for (int i = 0; i < 4; i++) {
							if(inside(x + dx[i],y + dy[i]))
							if (MyLabels[y + dy[i]][x + dx[i]] != 0
									&& MyGroup[y][x] == 1) {
								MyLabels[y + dy[i]][x + dx[i]] = smallLabel;
							}
						}
					}	
				}
			}
		}

	/**
	 * Creates a buffer to hold a label for each blob.
	 */
	 
	private void createLabelTab() {
		 
	    int labelTab[];
		int index = 0;
		blobNumber = 0;
		 
		 if (countBlobPixel > 0) {
	      labelTab = new int[countBlobPixel];
	      labelTab2 = new int[countBlobPixel]; 
			
			for (int y = sy; y < h; y++) {
				for (int x = sx; x < w; x++) {
					if (MyGroup[y][x] == 1 && MyLabels[y][x] > 0) {

						labelTab[index] = MyLabels[y][x];
						index++;

					}

				}
			}
		  

			 labelTab = PApplet.sort(labelTab);
			 
			 labelTab2 = res3(labelTab);
			 
			 blobNumber = labelTab2 .length;

		 }
	}
	
	
	/**
	 * Check if inside image's or roi's boundaries.
	 * @param x
	 * 			X coordinate of the point to check.
	 * @param y
	 * 			Y coordinate of the point to check.
	 */
	
	private boolean inside(int x, int y) {
 
			if(x < w && x >=  0 && y < h && y >=  0)
			return true;
		else
			return false;
	}
	
	/**
	 * Removes all 0s values from <code> arra </code> condensing the rest of the
	 * values.
	 * 
	 * @param arra
	 *            The array to process.
	 * @return a The new array with the condensed values.
	 */

   	private int[] res3(int[] arra) {
		int[] ar = new int[arra.length];
		int index = 0;

		for (int i = 0; i < arra.length; i++) {

			int t = arra[i];
			if (i != arra.length - 1) {
				if (arra[i] != 0 && arra[i + 1] != t) {
					ar[index] = arra[i];
					index++;
				}
			}
			ar[index] = arra[arra.length - 1];
		}

		int[] a = new int[1];
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] != 0) {
				a = new int[i + 1];
				// Need to copy manually.
				for (int j = 0; j < a.length; j++) {
					a[j] = ar[j];
				}

			}
		}
		return a;
	}  
	
 /* private int[] res3 ( int [] args ) {//TODO using this causes to generate  
	  				//Array Index Out Of Bounds Exception in
	 				//the array which receive the return. WHY??
	    int previous = 0;
	    int count = 0;
	    int[]result = new int[args.length] ;
	    for ( int i = 0; i < args.length; i++ )
	    {
	      if (   args[i]!=  previous && args[i]!=0 )
	      {
	         result[count] = args[i] ;
	        previous = args[i];
	        count++;
	      }
	    } 
	    int []b = new int[count];
	    for(int i=0; i<count; i++){
	      b[i] = result[i];
	    }
	    return  b;
	  } */
    
	/**
	 *  Returns the smallest non 0 value
	 *  from the argument (sorted) array.
	 *  @param arra The array to search.
	 *  @return The smallest non 0 value in <code><b>arra</b></code>.
	 */
	
	private int  res4(int arra[]) {
		  
	    int r = 0;
	   
	   for (int i = 0; i < arra.length; i++) {
	      
	      if(arra[i]!=0){
	        r=i;
	    
	        break;
	      }
	    }
	     
	     return (arra[r]);
	  }
	
 
	/**
	 * Finds all the blob's pixels and saves them into the blob pixel buffer
	 * <code>MyGroup</code>.
	 * 
	 * @param pix_array
	 *            The screen buffer to scan for blobs.
	 * @param trueWidth
	 *            The width of the image or video's frame to search for blobs.
	 *
	 */

	public void findBlobs(int[] pix_array, int trueWidth, int trueHeight) {

		countBlobPixel = 0;
		initBuffers(); 
		
		if (threshold > 255)
			threshold = 255;
		if (threshold < 0)
			threshold = 0;

		if (roiIsSet == false) {
 
			for (int y = sy; y <  trueHeight; y++) {
				for (int x = sx; x < trueWidth; x++) {
					
					// if is blob label with 1
					if (threshold == p5
							.brightness(pix_array[x + y * trueWidth])) {
						MyGroup[y][x] = 1;
						
						// total blob's pixels 
						countBlobPixel++; 
					} else {
						MyGroup[y][x] = 0;
					}
				}
			}
 
		} 
		else {//reads from inside the roi area

			for (int y = sy; y < h; y++) {//[10]
				for (int x = sx; x < w; x++) {
					 
					// if is blob label with 1
					if (threshold == p5
							.brightness(pix_array[(sroix+x) + ((sroiy+y) * trueWidth)])) {
						MyGroup[y][x] = 1;
						
						// total blob's pixels
						countBlobPixel++; 
					} else {
						MyGroup[y][x] = 0;
					}
				}
			}
		}

		doLabel();
		createLabelTab();

	}

    /**
	 * Like <code>findBlobs(int[],int,int)</code> but to be used with a PImage
	 * object.
	 * 
	 * @param img
	 *            PImage to scan for blobs.
	 */
	
	public void imageFindBlobs(PImage img) {

		countBlobPixel = 0;
		initBuffers();
		
		if (threshold > 255)
			threshold = 255;
		if (threshold < 0)
			threshold = 0;

		if (roiIsSet == false) {
 
			for (int y = sy; y < h; y++) {
				for (int x = sx; x < w; x++) {
					
					// if is blob label with 1
					if (threshold == p5.brightness(img.get(x, y))) {
						MyGroup[y][x] = 1;
						countBlobPixel++;// total blob's pixels
					} else {
						MyGroup[y][x] = 0;
					}
				}
			}
 
		}
		else {//reads from inside the roi area
 
			for (int y = sy; y < h; y++) {//[13]
				for (int x = sx; x < w; x++) {
					// if is blob label with 1
					if (threshold == p5.brightness(img.get(sroix+x, sroiy+y))) {
						
					    	MyGroup[y][x] = 1;
					    	countBlobPixel++;// total blob's pixels
					
					} else { 
							MyGroup[y][x] = 0;
					}
				}
			} 
		 }

		doLabel();
		createLabelTab();
 }

	/**
	 * Calculates the single blob's weight (mass) for each frame or image.
	 * This method must be called before calling the following methods:<br>
	 * <code>getBlobWeight()</code> <br>
	 * <code>getBlobWeightLabel()</code><br>
	 * <code>drawSelectBox() </code><br> 
	 * <code>drawSelectContours()</code><br>
	 * <code>findBlobs()</code><br>
	 * <code>imageFindBlobs()</code>
	 * 
	 * @param printConsoleIfNoBlobs
	 *            If <code> true,</code> the method prints a message to the
	 *            console when zero blobs are found.
	 */
	
	public void weightBlobs(boolean printConsoleIfNoBlobs) {

		int bPix = 0;

		if (countBlobPixel > 0) {

			blobWeightList = new int[blobNumber];

		 	boolean cerca = true; 

			for (int i = 0; i < blobNumber; i++) {

				for (int y = sy  ; y < h  ; y++) { 
					for (int x = sx  ; x < w  ; x++) {
						 
						 if (labelTab2[i] == MyLabels[y][x]) { 
							 
							bPix++;
							cerca = true;
						
						 } else if (cerca) {
							
							blobWeightList[i] += bPix;
							cerca = false;
							bPix = 0;
						}
					}
				}
			}
		} 
		else {											  
			if (printConsoleIfNoBlobs)						 
				PApplet.println(" !!! zero blobs found !!!"); 
		}
		blobWeighted = true;
	}

	/**
	 * Draws the edge of each blob.
	 * 
	 * @param contoursColor
	 *            The edges color. Here can be passed <code>color( r, g, b)</code>.
	 * @param thickness
	 *            The edges thickness. 
	 *            <code>findBlobs()</code> or
	 *            <code>imageFindBlobs(PImage)</code> must be called first to
	 *            call this method.
	 */

	public void drawContours(int contoursColor, float thickness) {
		if(blobNumber > 0)
		if(roiIsSet)
		for (int y = sy  ; y < h ; y++) { 
			for (int x = sx  ; x < w  ; x++) {

				if (MyLabels[y][x] > 0 && is_edge(x, y)) {
 
					p5.stroke(contoursColor);
					p5.strokeWeight(thickness);

					p5.point(sroix+x, sroiy+y);

				}
			}
		}
		else  
			for (int y = sy  ; y < h ; y++) { 
				for (int x = sx  ; x < w  ; x++) {

					if (MyLabels[y][x] > 0 && is_edge(x, y)) {
	 
						p5.stroke(contoursColor);
						p5.strokeWeight(thickness);

						p5.point( x,  y);

					}
				}
			}
 
		// reset to default strokeWeight and stroke
		p5.stroke(0);
		p5.strokeWeight(1);
	}

	/**
	 * Draws the contour only for blobs which weight is bigger than or equals to
	 * <code> minimumWeight</code> parameter value. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code> weightBlobs(boolean) </code>
	 * and <code>loadBlobsFeatures() </code>must be called before to
	 * call this method.
	 * 
	 * @param minimumWeight
	 *            The minimum weight of the blobs for which the contour is drawn
	 * @param contoursColor
	 *            The pixel's edge color. 
	 * @param thickness
	 *            The edge thickness.
	 * @see loadBlobsFeatures()
	 * @see weightBlobs(boolean)
	 **/
	
	public void drawSelectContours(int minimumWeight, int contoursColor, float thickness) {
	
		if(blobWeighted && blobNumber > 0){
		if(roiIsSet)
	 
		for (int y = sy  ; y < h  ; y++) {
			for (int x = sx ; x < w  ; x++) {
	
				if (MyLabels[y][x] > 0 && is_edge(x, y)
						&& getBlobWeightLabel(get_label(x, y)) >= minimumWeight) {
						
						p5.stroke(contoursColor);
						p5.strokeWeight(thickness);
						p5.point(sroix+x,sroiy+y);
	
				}
			}
		}
		else  
		for (int y = sy  ; y < h  ; y++) {
			for (int x = sx ; x < w  ; x++) {
	
					if (MyLabels[y][x] > 0 && is_edge(x, y)
						&& getBlobWeightLabel(get_label(x, y)) >= minimumWeight) {
	
							p5.stroke(contoursColor);
							p5.strokeWeight(thickness);
							p5.point(x, y);
	
						}
					}
				}
	 
			p5.stroke(0);
			p5.strokeWeight(1);
		}
		else if(!blobWeighted){
			PApplet.println("Blobscanner error! :\n" +
					"before using drawSelectContours you must call weigthBlobs");
		}
	}

	/**
	 * Draws the blob contour for the blob specified by <code> blobnumber</code>.
	 *  <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code> and <code>loadBlobsFeatures() </code>
	 * must be called before to call this method.
	 * 
	 * @param blobnumber
	 *            The blob which contours are drawn
	 * @param contourColor
	 *            The contour's color. 
	 * @param thickness
	 *            The contour thickness. 
	 * @see  loadBlobsFeatures()
	 **/
	
	public void drawBlobContour(int blobnumber, int contourColor, float thickness) { 

		p5.stroke(contourColor);
		p5.strokeWeight(thickness);

		PVector[] edge = getEdgePoints(blobnumber);
 
		for (int i = 0; i < edge.length; i++) {
			p5.point(edge[i].x ,edge[i].y ); 
		}											  
  
		p5.stroke(0);
		p5.strokeWeight(1);
	}

	/**
	 * Draws the blob bounding box.
	 * 
	 * @param boxColor
	 *            The bounding box color. Here it can be passed color( r, g, b)
	 *            Processing function. This parameter is passed to
	 *            <code>stroke</code> Processing's method.
	 *            <code>findBlobs()</code> or
	 *            <code>imageFindBlobs(PImage)</code>,
	 *            <code>loadBlobsFeatures() </code>must be called
	 *            before to call this method.
	 * @param thickness
	 *            The bounding box thickness.
	 */

	public void drawBox(int boxColor, float thickness) {
		p5.stroke(boxColor);
		p5.strokeWeight(thickness);
		
		if (blobNumber > 0)   
		if(roiIsSet) {
		 
			 for (int i = 0; i < blobNumber; i++) {
				p5.line(sroix+A[i].x,sroiy+A[i].y,sroix+B[i].x,sroiy+B[i].y);
				p5.line(sroix+B[i].x,sroiy+B[i].y,sroix+D[i].x,sroiy+D[i].y);
				p5.line(sroix+A[i].x,sroiy+A[i].y,sroix+C[i].x,sroiy+C[i].y);
				p5.line(sroix+C[i].x,sroiy+C[i].y,sroix+D[i].x,sroiy+D[i].y);
			}
		}
		else  {
			 
				 
				for (int i = 0; i < blobNumber; i++) {
					p5.line( A[i].x, A[i].y, B[i].x, B[i].y);
					p5.line( B[i].x, B[i].y, D[i].x, D[i].y);
					p5.line( A[i].x, A[i].y, C[i].x, C[i].y);
					p5.line( C[i].x, C[i].y, D[i].x, D[i].y);
				}
 			 }
		p5.stroke(0);
		p5.strokeWeight(1);
	}

	/**
	 * Draws the blob's bounding box only for blobs which weight is bigger than
	 * or equals to <code> minimumWeight</code>.
	 * <code> imageFindBlobs(PImage)</code>,<code>findBlobs()</code> or
	 * <code>weightBlobs(boolean) </code>, <code>loadBlobsFeatures() </code>
	 * must be called before to call this method.
	 * 
	 * @param minimumWeight
	 *            The minimum weight of the blob for which the bounding box is
	 *            drawn
	 * @param boxColor
	 *            The bounding box color.
	 * @param thickness
	 *            The edges thickness.
	 * @see loadBlobsFeatures()
	 * @see weightBlobs(boolean)
	 */

	public void drawSelectBox(int minimumWeight, int boxColor, float thickness) {
		if(blobNumber > 0 && blobWeighted){
			p5.stroke(boxColor);
			p5.strokeWeight(thickness);
 
			if(roiIsSet){
				 
					for (int i = 0; i < blobNumber; i++) {
						if (getBlobWeight(i) >= minimumWeight) {
							p5.line(sroix+A[i].x,sroiy+A[i].y,sroix+B[i].x,sroiy+B[i].y);
							p5.line(sroix+B[i].x,sroiy+B[i].y,sroix+D[i].x,sroiy+D[i].y);
							p5.line(sroix+A[i].x,sroiy+A[i].y,sroix+C[i].x,sroiy+C[i].y);
							p5.line(sroix+C[i].x,sroiy+C[i].y,sroix+D[i].x,sroiy+D[i].y);
						}
					}
			}
			else {
				 
					for (int i = 0; i < blobNumber; i++) {
						if (getBlobWeight(i) >= minimumWeight) {
							p5.line( A[i].x, A[i].y, B[i].x, B[i].y);
							p5.line( B[i].x, B[i].y, D[i].x, D[i].y);
							p5.line( A[i].x, A[i].y, C[i].x, C[i].y);
							p5.line( C[i].x, C[i].y, D[i].x, D[i].y);
						}
					}
			 }
 
			// reset to default strokeWeight and stroke
			p5.stroke(0);
			p5.strokeWeight(1);
		}
		else if(!blobWeighted){
			PApplet.println("Blobscanner error! :\n" +
					"before using drawSelectBox you must call weigthBlobs");
		}
	}

	 

	/**
	 * Returns true if at x y location finds a blob pixel.
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code> methods
	 * must be called before to call this method.
	 * 
	 * @param x
	 *            The x location of the pixel to check.
	 * @param y
	 *            The y location of the pixel to check.
	 * @return Returns true if at x y location finds a blob pixels.
	 */
		 
	public boolean isBlob(int x, int y) { 
		if (roiIsSet && inside(x-sroix, y-sroix) && MyLabels[y-sroiy][x-sroix] > 0) {
			return true;
		} else if(!roiIsSet && inside(x, y) && MyLabels[y][x] > 0){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if the pixel at x y coordinates is a blob edge pixel.
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code> must be
	 * called first to call this method.
	 * 
	 * @param x
	 *            The screen x coordinate to check.
	 * @param y
	 *            The screen y coordinate to check.
	 * @return Returns true if the pixel at x y coordinates is a blob edge
	 *         pixel.
	 */

	public boolean isEdge(int x, int y) { 

		int count = 0;
		 if(inside(x-sroix,y-sroiy))
		 if (MyLabels[y-sroiy][x-sroix] > 0 )
		 
				for (int s = 0; s < 8; s++) {
					 if (inside((x-sroix) + xkern[s], (y-sroiy) + ykern[s])  &&
						 MyLabels[(y-sroiy) + ykern[s]][(x-sroix) + xkern[s]] == 0
 
									 || x-sroix == 0 || x-sroix  == (w - 1) 
									 || y-sroiy == 0 || y-sroiy  == (h - 1))  {

							count++;
							break;  
						}
				}
		if (count > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Returns true if the pixel at x y coordinates is a blob edge pixel.
	 * @param x
	 *            The screen x coordinate to check.
	 * @param y
	 *            The screen y coordinate to check.
	 * @return Returns true if the pixel at x y coordinates is a blob edge
	 *         pixel.
	 */
	
	
	private boolean is_edge(int x, int y) { 

		int count = 0;
		 if (MyLabels[y][x] > 0 )
			 
				for (int s = 0; s < 8; s++) {
					 if( inside(x + xkern[s], y + ykern[s])  &&
							 MyLabels[(y) + ykern[s]][(x) + xkern[s]] == 0
							 
							 || x == 0 || x  == (w-1) 
							 || y == 0 || y  == (h-1))  {

					count++;
					break;  
				}
				}
		if (count > 0)
			return true;
		else
			return false;
	}
 
	/**
	 * Returns true only if the pixel at the coordinates x y is inside the blob
	 * represented by the parameter <code>blobToMatch</code>.
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code> must be
	 * called first to call this method.
	 * 
	 * @param x
	 *            The X coordinate of the pixel to test.
	 * @param y
	 *            The Y coordinate of the pixel to test.
	 * @param blobToMatch
	 *            The blob to match
	 * @return Returns true only if the pixel at the coordinates x y is inside
	 *         the blob represented by the parameter<code>blobToMatch</code>.
	 */
	
	public boolean isMatch(int x, int y, int blobToMatch) {
 
		if (roiIsSet && inside(x-sroix, y-sroix) && 
				MyLabels[y-sroiy][x-sroix] == getLabel(blobToMatch)) {
			return true;
		} else if(!roiIsSet && inside(x, y) && MyLabels[y][x] == getLabel(blobToMatch)){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Initialises four PVector object arrays made by four elements each to hold
	 * the corners coordinates vectors of the blob's bounding box.
	 */

	private void initCornersVectors() {
		 
		 
				A = new PVector[getBlobsNumber()];
				B = new PVector[getBlobsNumber()];
				C = new PVector[getBlobsNumber()];
				D = new PVector[getBlobsNumber()];
    }

	/**
	 * Computes some of the blob's main features.
	 */
	
	private void computeBlobsFeatures() {

		initCornersVectors(); 
		
		crosspoints = new PVector[getBlobsNumber()][4];
		// If we have blobs...
	 	if (blobNumber > 0) { 
	 	 
			// For each blob... 
			for (int k = 0; k < blobNumber; k++) {
				int i = 0; 
				// Initialise two arrays to hold the blob's edges point
				// coordinates.
				 				//assume edge eq coutBlobPixel
				edgeX = new int[countBlobPixel]; //TODO not safe !
				edgeY = new int[countBlobPixel];
				
			 
				// For each pixel
				for (int y = sy ; y < h ; y++) { 

					for (int x = sx ; x < w  ; x++) {

						// If a pixel is labelled...
						if (MyLabels[y][x] > 0) { 
						 
							// If the label is the same as the current blob's
							// label..			 
							if (MyLabels[y][x] == labelTab2[k] && is_edge(x, y)) { 
						 
								// Save its coordinates
								edgeY[i] =  y;
								edgeX[i] =  x;
 
								// ..and increment for the next edge point..
								i++;
							}
						}
					}
				}


				edgeX = PApplet.sort(edgeX);
				edgeY = PApplet.sort(edgeY);

				edgeX = res3(edgeX);
				edgeY = res3(edgeY);

				int minimx = edgeX[0];
				int maximx = edgeX[edgeX.length - 1];
				int minimy = edgeY[0];
				int maximy = edgeY[edgeY.length - 1];

				Ax = minimx-1;
				Ay = minimy-1;
				Bx = maximx+1;
				By = minimy-1;
				Cx = minimx-1;
				Cy = maximy+1;
				Dx = maximx+1;
				Dy = maximy+1;
				 
 
				A[k] = new PVector(Ax, Ay);
				B[k] = new PVector(Bx, By);
				C[k] = new PVector(Cx, Cy);
				D[k] = new PVector(Dx, Dy);
 

				crosspoints[k][0] = new PVector(((Bx - Ax) / 2) + Ax, Ay);
				crosspoints[k][1] = new PVector(((Bx - Ax) / 2) + Ax, Cy);
				crosspoints[k][2] = new PVector(Ax, ((Cy - Ay) / 2) + Ay);
				crosspoints[k][3] = new PVector(Bx, ((Cy - Ay) / 2) + Ay);
			}
		 }
	}

	/**
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code> methods
	 * must be called before to call this method. This method must be called
	 * before any call to one of the followings methods :
	 * <code>getA, getB, getC, getD, getBlobWidth, getBlobHeight, getBoxCentX, getBoxCentY,
	 * getCentroidX, getCentroidY, getcrossPoints, getEdgeX, getEdgeY, getEdgeXY,
	 * findCentroids.</code>
	 * 
	 * @see getA()
	 * @see getB()
	 * @see getC()
	 * @see getD()
	 * @see getBoxCentX(int)
	 * @see getBoxCentY(int)
	 * @see getBlobWidth(int)
	 * @see getBlobHeight(int)
	 * @see findCentroids()
	 * @see getCentroidX(int)
	 * @see getCentroidY(int)
     */
	
	public void loadBlobsFeatures() {

		computeBlobsFeatures();

	}
	
	 
	/**
	 * Calculates the blob centroids. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>weightBlobs(boolean)</code>
	 * and <code>loadBlobsFeatures()</code> methods must be called first to call
	 * this method. Also this method must be called always before calling
	 * <code>getCentroidX(int)<,/code> and
	 * <code>getCentroidY(int)</code>.
     */

   private PVector edgePoints[][];//TODO move up

 
	private void loadEdgePoints() {

		edgePoints = new PVector[blobNumber][];
		if (blobNumber > 0)
			for (int i = 0; i < blobNumber; i++)
				edgePoints[i] = getEdgePoints(i);
	}
	
 
	/**
	 * Computes the blob centroid. This method is much more simple and fast than
	 * it was in the previous release because, to compute the centroid, it takes
	 * in consideration only the edges points of the blob and not its internals,
	 * like the old method used to do it. The following methods are dependent on
	 * this: <code>getCentroidX(int)</code> and <code>getCentroidY(int)</code>.
	 */

	public void findCentroids() {
		if (blobNumber > 0) {
			loadEdgePoints();
			CenterOfMX = new float[blobNumber];
			CenterOfMY = new float[blobNumber];

			for (int i = 0; i < blobNumber; i++) {

				int n = 0;

				float[] tmpX = new float[blobNumber];
				float[] tmpY = new float[blobNumber];

				for (int j = 0; j < edgePoints[i].length; j++) {
					if (edgePoints[i][j] != null) {

						tmpX[i] += edgePoints[i][j].x;
						tmpY[i] += edgePoints[i][j].y;
						n++;
					}
				}
				CenterOfMX[i] = (tmpX[i] / n);
				CenterOfMY[i] = (tmpY[i] / n);
			}
		}
	}
	
	/**
	 * Returns the blob centroid x coordinate. Depends on <code>findBlobs()</code> or
	 * <code>imageFindBlobs()</code>,<code>findCentroids()</code>,
	 * <code>loadBlobsFeatures()</code> and
	 * <code>findCentroids()</code>methods.
	 * 
	 * @param blobnumber
	 *            The blob for which the centroid is returned.
	 * @return #CenterOfMX the blob's centroid x coordinates.
	 * @see #findCentroids(boolean,boolean)
	 */

	public float getCentroidX(int blobnumber) {
	 
			return CenterOfMX[blobnumber];
 
    }

	/**
	 * Returns the blob centroid y coordinate. Depends on <code>findBlobs()</code> or
	 * <code>imageFindBlobs()</code>,<code>findCentroids()</code>,
	 * <code>loadBlobsFeatures()</code> and
	 * <code>findCentroids()</code>methods.
	 * 
	 * @param blobnumber
	 *            The blob for which the centroid is returned.
	 * @return #CenterOfMY the blob centroid y coordinates.
	 * @see #findCentroids(boolean,boolean)
	 */

	public float getCentroidY(int blobnumber) {
	 
			return CenterOfMY[blobnumber];
 
	}

	/**
	 * Returns the blob width.
	 * Depends on
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>,
	 * <code>loadBlobsFeatures()</code> methods.
	 * 
	 * @param blobnumber
	 * @return the width of the blob referred by the parameter.
	 * @see #loadBlobsFeatures()
	 */
	
	public int getBlobWidth(int blobnumber) {
		if (blobNumber > 0)
			return  (int)  (B[blobnumber].x - A[blobnumber].x)    ;
		else
			return 0;

	}

	/**
	 * Returns the blob height.
	 * Depends on
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>,
	 * <code>loadBlobsFeatures()</code> methods. 
	 * 
	 * @param blobnumber
	 * @return the height of the blob referred by the parameter.
	 * @see #loadBlobsFeatures()
	 * @see #findBlobs(int[],int,int)
	 * @see #imageFindBlobs(PImage)
	 */

	public int getBlobHeight(int blobnumber) {
		if (blobNumber > 0)
			return (int)   (C[blobnumber].y - A[blobnumber].y) ; 
		else
			return 0;
	}

	/**
	 * The total number of blobs in the current frame or image. Depends on
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code> methods.
	 * 
	 * @return The total number of blobs in the current frame or image.
	 */

	public int getBlobsNumber() {
		return blobNumber;
	}

	/**
	 * Get the total weight of all the blobs in the current frame or image. Depends
	 * on <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code> methods.
	 * 
	 * @return countBlobPixel The total weight for all the blobs in the current
	 *         frame or image.
	 */

	public int getGlobalWeight() {
		return countBlobPixel;
	}

	/**
	 * Returns the weight of the blob specified. Depends on <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>weightBlobs(boolean)</code>, methods.
	 * 
	 * @param blobNum
	 *            The number of the blob for which the weight is returned. The
	 *            first blob is numbered with 0.
	 * @return The weight of the blob specified by the parameter blobNum.
	 */
	
	public int getBlobWeight(int blobNum) {
		if(blobWeighted)
		return blobWeightList[blobNum];
		else
		{
			PApplet.println("Error!:\nPlease call weightBlobs before\n"+
							"calling getBlobWeight.\n" +
							version());
			return -1;
		}
	}

	/**
	 * Returns the weight of the blob with the specified label. The first label
	 * is numbered with 1. The labels are not consecutive. You can use
	 * <code>get_label(int blobnumber) </code> or
	 * <code> get_label(int x, int y)</code> to find the label to
	 * pass to this method. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>weightBlobs(boolean)</code>,
	 * and <code>loadBlobsFeatures()</code> must be called before to
	 * call this method.
	 * 
	 * @param label
	 *            The label of the blob for which the weight is returned. The
	 *            labels start from 1, not 0.
	 * @return The weight of the blob labelled with the parameter
	 *         <code>label</code>.
	 * @see weightBlobs(boolean)
	 * @see getLabel(int)
	 * @see getLabel(int,int)
	 */
	
	public int getBlobWeightLabel(int label) { 
		
		int cnt = 0;
		while (labelTab2[cnt] != label) {
			cnt++;
		}

		return blobWeightList[cnt];

	}

	/**
	 * Returns an array of PVectors which contains all the left upper corners of
	 * the blobs bounding boxes. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>loadBlobsFeatures()</code>
	 * methods must be called first to call this method.
	 * 
	 * @return A An array of PVectors. Each vector represents the left upper
	 *         corner of a blob's bounding box.
	 */
	
	public PVector[] getA() {
		PVector a[] = new PVector[blobNumber];
		if(roiIsSet){
			for(int i=0; i<A.length; i++){
				a[i]= new PVector (A[i].x+sroix,A[i].y+sroiy);
		    }
			return a;
		}
		else{
			return A;
		}
	}

	/**
	 * Returns an array of PVectors which contains all the right upper corners
	 * of the blobs bounding boxes. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>loadBlobsFeatures()</code>
	 * methods must be called first to call this method.
	 * 
	 * @return B An array of PVectors. Each vector represents the right upper
	 *         corner of a blob's bounding box .
	 */
	
	public PVector[] getB() {
		PVector b[] = new PVector[blobNumber];
		if(roiIsSet){
			for(int i=0; i<B.length; i++){
				b[i]= new PVector(B[i].x+sroix,B[i].y+sroiy);
		    }
			return b;
		}
		else{
			return B;
		}
	}

	/**
	 * Returns an array of PVectors which contains all the left lower corners of
	 * the blobs bounding boxes. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>loadBlobsFeatures()</code>
	 * methods must be called first to call this method.
	 * 
	 * @return C An array of PVectors. Each vector represents the left lower
	 *         corner of a blob's bounding box .
	 */
	
	public PVector[] getC() {
		PVector c[] = new PVector[blobNumber];
		if(roiIsSet){
			for(int i=0; i<C.length; i++){
				c[i]= new PVector(C[i].x+sroix,C[i].y+sroiy);
		    }
			return c;
		}
		else{
			return C;
		}
	}

	/**
	 * Returns an array of PVectors which contains all the right lower corners
	 * of the blobs bounding boxes. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>loadBlobsFeatures()</code>
	 * methods must be called first to call this method.
	 * 
	 * @return D An array of PVectors. Each vector represents the right lower
	 *         corner of a blob's bounding box .
	 */
	
	public PVector[] getD() {
		PVector d[] = new PVector[blobNumber];
		if(roiIsSet){
			for(int i=0; i<D.length; i++){
				d[i]= new PVector(D[i].x+sroix,D[i].y+sroiy);
		    }
			return d;
		}
		else{
			return D;
		}
	}

	/**
	 * Returns the blob number at the x y coordinates. If at these coordinate
	 * there isn't a blob returns -1; <code>findBlobs()</code> or
	 * <code>imageFindBlobs()</code> must be called first to call this
	 * method.
	 * 
	 * @param x
	 *            The X coordinate to check for blob presence.
	 * @param y
	 *            The Y coordinate to check for blob presence.
	 * @return Returns the blob number at the x y coordinates. If at these
	 *         coordinate there isn't a blob returns -1;
	 */

	public int getBlobNumberAt(int x, int y) { //  ?-?
	 
			 if(roiIsSet){
			  
				 for (int i = 0; i < blobNumber; i++) {
					 if (get_label(x -sroix , y-sroiy ) == getLabel(i)) 
						 return i;
				 }
			}else if(!roiIsSet){
				for (int i = 0; i < blobNumber; i++) {
					if (get_label( x, y) == getLabel(i))
						 return i;
				}
				
			}
			 PApplet.println("Error!\nNo blob here.\n");
	  return -1;	  
		 
	}
	 

	/**
	 * 
	 * Returns the blob label at <code> x, y </code> screen coordinates. If at
	 * that location there is no blob returns 0. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code> must be called first to call this
	 * method.
	 * 
	 * @param x
	 *            The x screen coordinates.
	 * @param y
	 *            The y screen coordinates.
	 * @return The value of the label at x y;
	 * 
	 */
	
	private int get_label(int x, int y) {
 
		if(inside(x,y))
		return MyLabels[y][x];
		else 
		return 0;
		}
	
	/**
	 * 
	 * Returns the blob label at <code> x, y </code> screen coordinates. If at
	 * that location there is no blob returns 0. <code>findBlobs()</code> or
	 * <code>imageFindBlobs(PImage)</code> must be called first to call this
	 * method.
	 * 
	 * @param x
	 *            The x screen coordinates.
	 * @param y
	 *            The y screen coordinates.
	 * @return The value of the label at x y;
	 * 
	 */
	
	public int getLabel(int x, int y) {
		if(roiIsSet){
    	x = x - sroix;
		y = y - sroiy;
		}
		 
		if(inside(x,y))
		return MyLabels[y][x];
		else 
		return 0;
		}
	
    /**
	 * Returns the label of the blob indicated by the  method's
	 * parameter. <code>blobnumber</code> can be from 0 to
	 * <code>getBlobsNumber()</code>
	 * 
	 * If in an image there are 5 blobs, passing <code>blobnumber</code> = 4 
	 * will return the label of the 5th blob in the image, counting from the 
	 * top left corner of the screen.
	 * This method depends on <code>findBlobs()</code> or <code>imageFindBlobs()</code>.
	 * 
	 * @param <code>blobnumber</code>
	 *            The blob for which the label is returned.
	 * @return The value of the label for the blob refered by 
	 * 			the parameter <code>blobnumber</code>.
     */
	
	public int getLabel(int blobnumber) {
		if (blobNumber > 0) {
			return labelTab2[blobnumber];
		} else {
			return 0;
		}
	}

	/**
	 * Computes the coordinates of the edge's pixels for the specified blob.
	 * <code>findBlobs(int[], int, int)</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>loadBlobsFeatures()</code>
	 * must be called first to call this method.
	 * 
	 * @param blobnumber
	 *            The blob for which the edge's pixels coordinates are computed
	 * @return edgeCoordinates A PVector array containing the coordinates of the
	 *         specified blob's edge points
	 * 
	 */

	public PVector[] getEdgePoints(int blobnumber) {  

		PVector[] edgeCoordinates = new PVector[getEdgeSize(blobnumber)];
		int count = 0;
		for (int y = (int)A[blobnumber].y; y <(int) A[blobnumber].y 
				+  getBlobHeight(blobnumber)  ; y++) {
			for (int x = (int)A[blobnumber].x; x <(int) A[blobnumber].x
					+  getBlobWidth(blobnumber)  ; x++) {
				if (is_edge(x, y) && get_label(x, y) == getLabel(blobnumber)) {
					 
					if(roiIsSet==false){
						edgeCoordinates[count] = new PVector(x , y  ); 
					}
					else if(roiIsSet){
						edgeCoordinates[count] = new PVector(x+sroix, y+sroiy  );
					}
					count++; 

				}
			}
		}

		return edgeCoordinates;
	}

	/**
	 * Computes the number of edge pixels for the specified blob.
	 * <code>findBlobs(int[], int, int)</code> or
	 * <code>imageFindBlobs(PImage)</code>, <code>loadBlobsFeatures()</code>
	 * must be called first to call this method.
	 * 
	 * @param blobnumber
	 *            The blob for which the number of edge pixels is computed.
	 * @return countEdgePoints The number of pixels in the edge.
	 * 
	 */

	public int getEdgeSize(int blobnumber) { 

		int countEdgePoints = 0;

		for (int y = (int) A[blobnumber].y; y <(int) A[blobnumber].y
				+  getBlobHeight(blobnumber) ; y++) {
			for (int x = (int) A[blobnumber].x; x < (int)A[blobnumber].x
					+  getBlobWidth(blobnumber) ; x++) {
				if (is_edge(x , y ) && get_label(x  , y  ) == getLabel(blobnumber)) {
					countEdgePoints++;

				}
			}
		}

		return countEdgePoints;
	}

	/**
	 * Computes and returns the coordinates of all the pixels in the specified
	 * blob. <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>,
	 * <code>weightBlobs(boolean)</code>, and <code>loadBlobsFeatures()</code>
	 * methods must be called before to call this method.
	 * 
	 * @param blobnumber
	 *            The blob for which the pixels coordinates are computed.
	 * @return pixelsCoordinates The array of PVector objects containing the
	 *         blob pixels coordinates.
	 */

	public PVector[] getBlobPixelsLocation(int blobnumber) {
	 

			PVector[] pixelsCoordinates = new PVector[getBlobWeight(blobnumber)];

			int count = 0;

			for (int y = (int) A[blobnumber].y; y < (int) A[blobnumber].y
					+ getBlobHeight(blobnumber); y++) {

				for (int x = (int) A[blobnumber].x; x < (int) A[blobnumber].x
						+ getBlobWidth(blobnumber); x++) {

					if (get_label(x, y) == getLabel(blobnumber)) { 
						if (roiIsSet)
							pixelsCoordinates[count] = new PVector(x + sroix, y
									+ sroiy);
						else
							pixelsCoordinates[count] = new PVector(x, y);
						count++;

					}
				}
			}
			return pixelsCoordinates;
	}

	/**
	 * Returns the x coordinates of the bounding box center.
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>,
	 * <code>loadBlobsFeatures()</code> must be called first to call this
	 * method.
	 * 
	 * @param blobnumber
	 *            The blob number ( starts from 0 ).
	 * @return x the x coordinates of the bounding box center.
	 * @see #loadBlobsFeatures()
	 */

	public float getBoxCentX(int blobnumber) {
		float w_ = B[blobnumber].x - A[blobnumber].x;
		float x_ = w_ / 2 + A[blobnumber].x;
		if(roiIsSet)
		return sroix+x_;
		else 
			return x_;
	}

	/**
	 * Returns the y coordinates of the bounding box center.
	 * <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>,
	 * <code>loadBlobsFeatures()</code> must be called first to call this
	 * method.
	 * 
	 * @param blobnumber
	 *            The blob number ( starts from 0 ).
	 * @return y the y coordinates of the bounding box center.
	 * 
	 * @see #loadBlobsFeatures()
	 * 
	 */

	public float getBoxCentY(int blobnumber) {
		float h_ = C[blobnumber].y - A[blobnumber].y;
		float y_ = h_ / 2 + A[blobnumber].y;
		if(roiIsSet)
		return sroiy+y_;
		else 
			return y_;
	}
	
	 
	/**
	 * I removed this because it's a waste of CPU cycles. The "cross points"
	 * can be very easily computed by the users using getA(), getB() etc...
	 * 
	 * Finds the coordinates of the middle point of the blob's bounding box
	 * side. <code>findBlobs()</code> or <code>imageFindBlobs(PImage)</code>,
	 * <code>loadBlobsFeatures()</code> and <code>weightBlobs(boolean)</code>
	 * methods must be called before to call this method.
	 * 
	 * @param blobnumber
	 *            The blob you need to compute the points for.
	 * @param pointVector
	 *            The point you need extract( 0 | 1 | 2 | 3 ). The order of the
	 *            vectors in the array is : 0 = top, 1 = down, 2 = left, 3 =
	 *            right.
	 * @param draw_them
	 *            If true draws the four points.
	 * @return The PVector containing the point coordinates (x, y).
	 * @see #loadBlobsFeatures()
	 * @see #weightBlobs(boolean)
	 */
	@Deprecated
	public PVector getCrossPoints(int blobnumber, int pointVector,
			boolean draw_them) {

		if (blobNumber > 0 && draw_them) {

			for (int k = 0; k < blobNumber; k++) {

				p5.point(((B[k].x - A[k].x) / 2) + A[k].x, A[k].y);// top
				p5.point(((B[k].x - A[k].x) / 2) + A[k].x, C[k].y);// down
				p5.point(A[k].x, ((C[k].y - A[k].y) / 2) + A[k].y);// left
				p5.point(B[k].x, ((D[k].y - B[k].y) / 2) + B[k].y);// right
			}
		}

		return crosspoints[blobnumber][pointVector];
	}
	
	/** 
	 * Hello message.
	*/
	public String sayHello() {
		return "Welcome and happy hacking with Blobscanner.\n"+
				"If you have any question or you wish to put a link\n"+
				"to a project on the blobscanner's web site,\n  "+
				"send me an email to blobdetector.info@gmail.com. \n";
	}

	/**
	 * return the version of the library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

}//18/01/14 15:26:48 

