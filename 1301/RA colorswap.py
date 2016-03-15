#Alan Chiang, 903129489, achiang31@gatech.edu 
#I worked on this assignment alone, using only this semester's course materials.

from Myro import*

def colorswap(pic):
    pict = makePicture(pic)
    pix = getPixels(pict) 
    for p in pix:
        red = getRed(p)
        blue = getBlue(p)
        green = getGreen(p)
        if red > blue and red > green:
            setRGB(p,(0,255,0)) 
        elif blue > red and blue > green:
            setRGB(p,(255,0,0))
        elif green > red and green > blue:
            setRGB(p,(0,0,255))  
    show(pict) 
colorswap("RA_colorswap_source.jpg")           