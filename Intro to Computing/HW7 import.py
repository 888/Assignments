from Myro import *


#overlay and crossfade

def overlayPic():
    originalPic = loadPicture("image0001.jpg")
    show(originalPic)
    for y in range( getHeight(originalPic)):
        pixel = getPixel(originalPic,25,y)
        setRed(pixel,255)
        setGreen(pixel,0)
        setBlue(pixel,0)
    for y in range( getHeight(originalPic)):
        pixel = getPixel(originalPic,50,y)
        setRed(pixel,0)
        setGreen(pixel,0)
        setBlue(pixel,255)
#overlayPic()


p1 = loadPicture("image0001.jpg")
p2 = loadPicture("image0002.jpg")
def crossFade(p1,p2):
    aList = []
    aList.append(p1)
    picChange = copyPicture(p1)
    for d in range(4):
        for x in range(getWidth(p1)):
            for y in range(getHeight(p1)):
                pix1 = getPixel(p1,x,y)
                pix2 = getPixel(p2,x,y)
                pix3 = getPixel(picChange,x,y)
                rChange = getRed(pix2)- getRed(pix1)
                gChange = getGreen(pix2)- getGreen(pix1)
                bChange = getBlue(pix2)- getBlue(pix1)
                rChange = rChange/4
                gChange = gChange/4
                bChange = bChange/4
                r = getRed(pix3)
                g = getGreen(pix3)
                b = getBlue(pix3)
                setRed(pix3, r + rChange)
                setGreen(pix3, g + gChange)
                setBlue(pix3, b +bChange)
        aList.append(copyPicture(picChange))
    aList.append(p2)
    savePicture(aList,"crossfade.gif")
crossFade(p1,p2)