## Shobhit Srivastava
## CS 1301 Section B01
## shobhit@gatech.edu
## I  worked on the homework assignment with Nineesha Babu Koshy and Alan Chiang, using only this semester's course materials.
from Myro import *

def combineGifs():
    finalGif = loadPictures("scene 1 after.gif") + loadPictures("scene 2 after.gif") + loadPictures("scene 3 after.gif") + loadPictures("scene 4 after.gif")
    savePicture(finalGif,"final gif.gif")
    
def duplicateFrames():
    aList = loadPictures("final gif.gif")
    for x in range(len(aList)+2):
        if x % 2 == 0:
            aList.insert(x+1,aList[x])
    savePicture(aList,"longer final gif.gif")    
        
a = loadPictures("longer final gif.gif")
for x in a:
    wait(0.1)
    show(x)              