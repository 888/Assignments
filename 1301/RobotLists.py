#Alan Chiang, 903129489, achiang31@gatech.edu 
#I worked on this assignment alone, using only this semester's course materials.

from Myro import*
init("sim")

def getValues(numSamples):
    y = []
    for x in range(numSamples):
        a = getLight('center')
        y.append(a)
        turnLeft(1,.25)
    return y 

x = getValues(7)
print(x)
            
def printStatistics(numbers):
    s = sum(numbers) 
    d = len(numbers)
    a = s/d
    l = min(numbers)
    h = max(numbers)
    e = 0
    for n in numbers:
        if n%2 == 0: 
            e = e + 1
        else: 
            pass
    
    print("You entered {0} values. The mean of those values was {1}, the minimum was {2}, the maximum was {3}, and there were {4} even numbers.".format(d,a,l,h,e))

printStatistics(x)
                         