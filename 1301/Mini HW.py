#Alan Chiang, 903129489, achiang31@gatech.edu 
#I worked on this assignment alone, using only this semester's course materials.

from Myro import*
init("sim")

def avoidWalls():
    for t in timer(60): 
        if getObstacle('left') > 5000:
            turnRight(1,1)
            forward(.5)
        else:
            forward(.5)
        if getObstacle('right') > 5000:
            turnLeft(1,1)
            forward(.5)
        else:
            forward(.5)
        if getObstacle('center') > 5000:
            backward(1)
            turnRight(1,1)
        else:
            forward(.5)
        if getIR('left') == 0:
            turnRight(1,1)
            forward(.5)
        else:
            forward(.5)
        if getIR('right') == 0:
            turnLeft(1,1)
            forward(.5)    
        else:
            forward(.5)
    stop()
    turnRight(1,3)
    beep(1,3520); beep(1,1760); beep(1,880); beep(1,440); beep(1,220); beep(1,110); beep(1,55); beep(1, 27.5)
    forward(1,.5)
    backward(1,.5)
    
    

avoidWalls()
       


