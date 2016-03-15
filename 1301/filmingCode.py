#Alan Chiang, 903129489, achiang31@gatech.edu, Section B, Shobit S, Nineesha Koshy
#We worked on the homework assignment together, using only this semester's course materials. 

from Myro import *


def scene1():
    aList = []
    for x in range(60):
        aList.append(takePicture())
    savePicture(aList,"scene 1.gif")
    
def scene2():
    aList = []
    for x in range(50):
        aList.append(takePicture())
    savePicture(aList,"scene 2 part 1.gif")    

def scene3():
    aList = []
    for x in range(100):
        aList.append(takePicture())
    savePicture(aList,"scene 3.gif")                      
    
def scene4():
    aList = []
    for x in range(70):
        aList.append(takePicture())
    savePicture(aList,"scene 4.gif")    