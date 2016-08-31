def test(x):
    print("y")
    return x 
print(test(3))


from Myro import *

def scene1():
    for x in range(15):
        forward(0.1,5)
        backward(0.1,5)
        
def splitscreen(): 
    t1 = loadPictures("fadetoblack.gif")
    t2 = loadPictures("crossfade.gif")
    anew = []
    for n1 in t1:
        for n2 in t2:
            W = getWidth(n1)
            H = getHeight(n2)
            new = makePicture(W, H) 
            for x in range(W//2):
                for y in range(H):
                    pix1 = getPixel(n1, x, y)
                    r1, g1, b1 = getRGB(pix1) 
                    pix2 = getPixel(new, x,y)
                    setRGB(pix2, (r1,g1,b1)) 
            for x in range(W//2,W):
                for y in range(H):
                    pix3 = getPixel(n2, x, y)
                    r3, g3, b3 = getRGB(pix3) 
                    pix4 = getPixel(new, x,y)
                    setRGB(pix4, (r3,g3,b3)) 
            anew.append(new)         
    savePicture(anew, "splitscreen.gif")


def scene2():
    splitscreen() 
    forward(.1,10)
    for x in range(40):
        turnLeft(0.1,0.8)
        turnRight(0.1,0.8)
        
        

def scene3():
    turnRight(0.1,3)
    forward(0.1,60)
                    
def scene4():
    forward(0.1,5)
    turnLeft(0.1,3)
    wait(7)
    turnRight(0.1,3)
    forward(0.1,20)
    
                    