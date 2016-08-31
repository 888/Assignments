#Alan Chiang, 903129489, achiang31@gatech.edu, Section B, Justin Ashtiani, 902956429, jashtiani@gatech.edu
#We worked on the homework assignment together, using only this semester's course materials. 

from Myro import*
from Graphics import* 

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


def fadetoblack():
    n = 1
    piclist = []
    for x in range(15):
        ts = makePicture("compsci-meme.jpg") 
        piclist.append(ts) 
    for pic in piclist:
        pix = getPixels(pic)
        for p in pix:
            ro = getRed(p)
            bo = getBlue(p)
            go = getGreen(p)
            setRed(p, ro - 17*n)
            setBlue(p, bo - 17*n)
            setGreen(p, go - 17*n) 
        n = n+1
    savePicture(piclist, "fadetoblack.gif")  
  
  
   
    
def splitscreen(): 
    t1 = makePicture("compsci-meme.jpg")
    t2 = makePicture("shallnotpass.jpg")
    W = getWidth(t1)
    H = getHeight(t2)
    new = makePicture(W, H) 
    for x in range(W//2):
        for y in range(H):
            pix1 = getPixel(t1, x, y)
            r1, g1, b1 = getRGB(pix1) 
            pix2 = getPixel(new, x,y)
            setRGB(pix2, (r1,g1,b1)) 
    for x in range(W//2,W):
        for y in range(H):
            pix3 = getPixel(t2, x, y)
            r3, g3, b3 = getRGB(pix3) 
            pix4 = getPixel(new, x,y)
            setRGB(pix4, (r3,g3,b3)) 
    savePicture(new, "splitscreen.jpg")

