		BLOBSCANNER PROCESSING LIBRARY VERSION 0.2-alpha

Blobscanner is a library for the [Processing](http://processing.org) programming 
environment. It's used for blob detection and analysis in image and video.

CHANGES

The first thing you need to know, especially if you have already used a previous
versions, is that the import name has changed from Blobscanner to blobscanner, 
to be more in line with the Java guidelines. The second thing is about the 
constructor of the Detector class, which also (for the new users) is the only
class: two new constructors have been added:

		Detector(PApplet parent)	(1)  

and  
		
		Detector(PApplet parent,int threshold)	(2)

if you use the #1 you need to call the method to set the blob's threshold value 
at least one time:
	        	
		setThreshold(int threshold)	(3)

The new constructors set automatically the area searched for blobs to the 
entire image, by default, but this behaviour can be altered by calling the 
following method:

		setRoi(int startx,int starty,int roiwidth,int roiheight)	(4)

 #4 defines a Region Of Interest to be searched for blobs, minimizing the 
amount of pixels to be scanned, with a consequent increasing in the speed of 
execution. The method is paired with another one which revert to default the size
of the ROI :

		unsetRoi()	(5)

after calling #5 the entire image is once again scanned for blob, as it was before 
calling #4.

The old constructor was left to provide some backward compatibility for users who 
decide to install the new version. It will be removed in a future release. 
Visit the project's [site](https://sites.google.com/site/blobscanner/home) for 
informations and tutorials. Also from there you can download the zip archive
containing executable jar file, docs and examples. 

