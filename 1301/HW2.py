#Alan Chiang, 903129489, achiang31@gatech.edu 
#I worked on this assignment alone, using only this semester's course materials.

def square(base): 
    result = base*base
    return(result)
    

def taylorSwift(numFans):
    if numFans <= 20 and numFans < 40:
        hour = 10
    elif numFans > 40:
        hour = 11
    else: 
        hour = 9
    tfin = (numFans%20)*3
    print("Taylor Swift will be done at {}:{} PM.".format(hour, tfin))
    
    
def girlScoutCookies():
    buy = float(input("How many boxes of cookies would you like to purchase"))
    funds = float(input("How much money do you have"))
    d = abs(funds-buy*4)
    print("You need another ${:.2f}.".format(d))



def conversionTime(meterPerSecond):
    mph = (meterPerSecond*3.28084*3600)/5280
    ftps = (meterPerSecond*3.28084)
    kmph = (meterPerSecond*3600)/1000
    print("That is equivalent to {:.2f}".format(mph),"mph, {:.2f}".format(ftps),"ft/s, and {:.2f}".format(kmph),"km/h.")
    #conversions differ from example by 0.01. Important?


import math 
def tipCalculator():
    bill = float(input("Enter bill amount")) 
    ptip = float(input("Enter percent value of tip"))
    coupon = float(input("Enter value of coupon, if any"))
    tip = math.ceil(bill*(ptip/100))
    tax = 0.08
    totalCost = (bill-coupon)*1.08+tip
    return("The total cost of dinner is ${:.2f}.".format(totalCost))
    
    
def falafel(falafelBalls, hummus, pitaBread):
    fB = falafelBalls//6
    h = hummus//2
    p = pitaBread
    sandwich = min(fB, h, p)
    print("With {0}".format(falafelBalls),"falafel balls, {0}".format(pitaBread),"pitas and {0}".format(hummus),"hummus, you can make a maximum of {0} falafels.".format(sandwich))

    
   
    
