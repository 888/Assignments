#Alan Chiang, Section B, 903129489/achiang31@gatech.edu
#I worked on the homework assignment alone, using only this semester's course materials.
def machToFPS ():
    speedi = float(input("Enter Speed in Mach"))
    speedf = speedi*1116.4370079
    print("Your speed is", speedf, "feet per second.")


def sqPyramidVolume ():
    l = float(input("Enter Base Length in Inches"))
    h = float(input("Enter Height in Inches"))
    v = l*l*h/3
    print("Volume of the Square Pyramid is", v, "in^3.")
   

def makeChange ():
    c = int(input("Enter Number of Cents"))
    dol = c//100
    qua = (c-100*dol)//25
    dim = (c-(100*dol+25*qua))//10
    nic = (c-(100*dol+25*qua+10*dim))//5
    pen = c%5
    print("You have", dol, "dollars,", qua, "quarters,", dim, "dimes,", nic, "nickels, and", pen, "pennies.")


def PPIIndex ():
    w = float(input("Enter Weight in Pounds"))
    h = float(input("Enter Height in Inches"))
    PPI = (w/h)*1.125
    print("Your corrected PPI is {:.1f}.".format(PPI))
