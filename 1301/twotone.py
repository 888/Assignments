from Myro import*

def twotone(picture): 
    code = makePicture(picture)
    a = getPixels(code)
    for pix in a:
        if getRGB(pix) > (127, 127, 127): 
            setRed(pix, 255)
            setGreen(pix,255)
            setBlue(pix, 255) 
        else: 
            setRed(pix, 0)
            setGreen(pix, 0)
            setBlue(pix, 0)
    show(code)     
    w = getWidth(code) 
    c = []
    for pix in range(w): 
        b = getPixel(code, pix, 100) 
        if getRed(b) == 0:
            c.append("black")
        else: 
            c.append("white")  
    print(c)  
    return c    
  
twotone("barcode_color.gif") 