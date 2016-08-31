#Alan Chiang, 903129489, achiang31@gatech.edu 
#I worked on this assignment alone, using only this semester's course materials.

from Myro import*
#init("COM15")

def secondAbility(): #dodging to avoid being struck 
    translate(1)
    rotate(-0.5)
    wait(3)
    forward(1,2)
    translate(0.5)
    rotate(1)
    wait(3)
    backward(1,1)
    turnRight(1, 0.5)
    backward(1,0.5)
    translate(1)
    rotate(-1)
    wait(3)
    stop()
    
    
def thirdAbility():
    rotate(1)
    translate(.8)
    wait(5)
    motor(1,0)
    wait(5)
    rotate(-1)
    translate(.8)
    wait(5)
    motor(0,1)
    stop()
    

def scout():
    forward(1,1)
    beep(1,100)
    translate(.5)
    rotate(0.3)
    wait(2)
    stop()
    beep(1,400)
    translate(1)
    rotate(-.5)
    wait(3)
    stop()
    beep(1,800)
    backward(1,4)
    beep(3,1000)
    beep(3,1000)


def theme():
    beep(2,1000)
    beep(1,300)
    beep(1,500)
    beep(1,700) 
    beep(2,1000)
    beep(1,900)
    beep(1,800)
    beep(1,600)
    beep(2,1000)
    beep(1,400)
    beep(1,500)
    beep(1,400)
    beep(1,300)
    beep(1,400)
    beep(2,1000)
    beep(2,1000)
    
    
def signatureAbility():
    theme()
    scout()
    print(getName())
    
def battleMenu():
    while True:
        o = int(input("Welcome to the battleMenu! Your mission: Take down the rampaging supervillan using your RoboHero! \n  Choose your action: \n 1. Action \n 2. Double Action \n 3. Triple Action \n 4. Quit \n Your choice")) 
        if o == 1:
            signatureAbility()
            stop()
            print("Uh-oh. It looks like that only made the villan angry!")  
        elif o == 2:
            signatureAbility()
            secondAbility()
            stop()
            print("Supervillan counterattacked. Please advise: RoboHero approaching Critical Existence Failure.")  
        elif o == 3:
            signatureAbility()
            secondAbility()
            thirdAbility()
            stop()
            print("There is no kill like overkill.") 
        elif o == 4:
            print("Victory is yours! Unfortunately, the city was destroyed in the crossfire.")
            break
        else: 
            print("The RoboHero upgrade budget got cut before we could add that technique. Try another one.")

battleMenu()
    