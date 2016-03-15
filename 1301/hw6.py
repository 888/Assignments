#Alan Chiang, 903129489, achiang31@gatech.edu, Section B, Justin Ashtiani, 902956429, jashtiani@gatech.edu
#We worked on the homework assignment together, using only this semester's course materials. 
from Myro import*
#init("COM10")
def colorRecognition():
    ts = takePicture()
    w = getWidth(ts)
    h = getHeight(ts) 
    iw = []
    ih = []
    for x in range(w): 
        p1 = getPixel(ts, x, 160)   
        r1 = getRed(p1)
        b1 = getBlue(p1)
        g1 = getGreen(p1)
        if r1 > 125 and b1 < 175 and g1 < 175:        
            iw.append(x)    
        elif b1 > 110 and r1 < 125 and g1 < 150:       
            iw.append(x)
        else:
            pass             
    for y in range(h): 
        p2 = getPixel(ts, 80, y)
        r2 = getRed(p2)
        b2 = getBlue(p2)
        g2 = getGreen(p2)
        if r2 > 125 and b2 < 175 and g2 < 175:            
            ih.append(y)    
        elif b2 > 110 and r2 < 125 and g2 < 150:      
            ih.append(y)
        else:
            pass
    if len(ih) > len(iw):
        motors(1,-1)
        wait(3)
        motors(-1,1)
        wait(3)
        stop()
    elif len(iw) > len(ih):
        forward(1,3)
        backward(1,3) 
    else:
        pass
 
    pix = getPixels(ts) 
    hr = []
    vr = []
    hb = []
    vb = []
    for p in pix: 
        r3 = getRed(p)
        b3 = getBlue(p)
        g3 = getGreen(p)

        if r3 > 135 and b3 < 100 and g3 < 100:       
            hr.append(p) 
            vr.append(p)
        elif b3 > 135 and r3 < 100 and g3 < 100:     
            hb.append(p) 
            vb.append(p)
        else: 
            pass   
    if len(hr) > len(hb) or len(vr) > len(vb):        
        beep(2,800)
    elif len(hb) > len(hr) or len(vb) > len(hb):     
        beep(2,500)
        wait(2)
        beep(2,500) 
    else:
        pass