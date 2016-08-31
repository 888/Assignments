#Alan Chiang, 903129489, achiang31@gatech.edu 
#I worked on this assignment alone, using only this semester's course materials.

def machToFPS(machList):
    o = machList
    n = map(lambda x: x*1116.4370079, machList) 
    for val in range(len(o)):
        print(o[val], "mach is equivalent to", n[val], "feet per second.") 


#returns a list of floats instead of ints; is that alright?    
def sqPyramidVolume(baseHeightList, volumeList): 
    vol = map(lambda x: x[0]*x[0]*x[1]/3, baseHeightList) 
    correctList = filter(lambda a: a in volumeList, vol) 
    return correctList 


def makeChange(changeList): 
    changeList[0] = changeList[0] * 100 
    changeList[1] = changeList[1] * 25 
    changeList[2] = changeList[2] * 10
    changeList[3] = changeList[3] * 5 
    changeList[4] = changeList[4] * 1
    totalValue = reduce(lambda x,y: x + y, changeList)  
    return totalValue 
