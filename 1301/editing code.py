#Alan Chiang, 903129489, achiang31@gatech.edu, Section B, Shobit S, Nineesha Koshy
#We worked on the homework assignment together, using only this semester's course materials. 

from Myro import *
def scene1():
    aList = loadPictures("scene 1.gif")
    bList = []
    for x in range(45):
        bList.append(aList[x])
    x = 45
    f = 1
    while x < 60:
       for pix in getPixels(aList[x]):
            setRGB(pix, getRed(pix) * f, getGreen(pix) * f, getBlue(pix) * f)
       if f > 0:
            f = f - 0.06667   
       bList.append(aList[x]) 
       x = x + 1 
    savePicture(bList,"scene 1 after.gif")   

def scene2():
    aList = loadPictures("scene 2 part 1.gif")
    bList = loadPictures("scene 2 part 2.gif")
    for x in range(len(aList)):
        pic = aList[x]
        pic2 = bList[x]
        for pix in getPixels(pic):
            if (getX(pix)>(getWidth(pic)//2)):
                pix2 = getPixel(pic2,getX(pix),getY(pix))
                setRGB(pix,getRed(pix2),getGreen(pix2),getBlue(pix2))
            elif (getX(pix)==getWidth(pic)//2):
                pix2 = getPixel(pic2,getX(pix),getY(pix))
                setRGB(pix,0,0,0)
    savePicture(aList,"scene 2 after.gif")            
           
def scene3():
    aList = loadPictures("scene 3.gif")
    for x in range(50):
        aList.pop()
    savePicture(aList, "scene 3 after.gif")                     

def scene4():
    aList = loadPictures("scene 4.gif")
    r = 0
    for x in aList:
        for pix in getPixels(x):
            setRed(pix, getRed(pix) + r)
        r = r + 1.7        
    savePicture(aList,"scene 4 after.gif")        