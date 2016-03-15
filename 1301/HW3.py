#Alan Chiang, 903129489, achiang31@gatech.edu 
#I worked on this assignment alone, using only this semester's course materials.

def applyToTech(reading, math, writing): 
    total = reading+math+writing
    if 680 <= reading <= 800 and 690 <= math <= 800 and 650 <= writing <= 800 and 2060 <= total <= 2400:
        return("Congratulations, you have been admitted to Georgia Tech. Go Jackets!")        
    elif reading >= 800 or math >= 800 or writing >= 800:
        return("Sorry, but your score is invalid. Please enter a valid score.")
    else:
        return("I'm sorry to inform you we cannot offer you admission to Georgia Tech.")



def guessAge(age): 
    i = str(input("Guess the age"))
    try:
        g = int(i)
    except ValueError:
        g = -273
    if g == age:
        print("You got it! Great job!")
        return
    elif i == "quit": 
        print("The game isn't over yet, and I know you can beat it!")
        print("Well, thanks for playing.")
        return
    elif i == "Quit": 
        print("The game isn't over yet, and I know you can beat it!")
        print("Well, thanks for playing.")
        return
    elif i == "QUIT": 
        print("The game isn't over yet, and I know you can beat it!")
        print("Well, thanks for playing.")
        return
    else: 
        print("No luck. Try again.")
    for x in range(0,5): 
        i = str(input("Another guess"))
        try:
            g = int(i)
        except ValueError:
            pass
        if g == age:
            print("You did a great job! You only took {0} tries!".format(x+1)) 
            return
        elif i == "quit":
            print("The game isn't over yet, and I know you can beat it!")
            print("Well, thanks for playing.")
            return
        elif i == "Quit": 
            print("The game isn't over yet, and I know you can beat it!")
            print("Well, thanks for playing.")
            return
        elif i == "QUIT": 
            print("The game isn't over yet, and I know you can beat it!")
            print("Well, thanks for playing.")
            return
        else: 
            print("No luck. Try again.") 
        if x == 4:
            print("Sorry, you can no longer enter additional guesses.") 
            print("Well, thanks for playing.")
            return
        else: 
            pass



def encryptMessage(secretMsg):
    s = secretMsg
    caps = "ABCDEFGHIJKLMNOPQRSTUVWYZ"
    lower = "abcdefghijklmnopqrstuvwxyz"
    num = "123"
    other = '0456789!%&\()+,-./:;?[\]_`{|}~ \t\n\r\x0b\x0c\''
    for letter in s: 
        if letter in caps:
            s = (s.replace("A","a^").replace("B","b^").replace("C","c^").replace("D","d^").replace("E","e^")
            .replace("F","f^").replace("G","g^").replace("H","h^").replace("I","i^").replace("J","j^")
            .replace("K","k^").replace("L","l^").replace("M","m^").replace("N","n^").replace("O","o^")
            .replace("P","p^").replace("Q","q^").replace("R","r^").replace("S","s^").replace("T","t^")
            .replace("U","u^").replace("V","v^").replace("W","w^").replace("X","x^").replace("Y","y^").replace("Z","z^")) 
        elif letter in num:
            s = s.replace("1","@").replace("2","#").replace("3","$")
        elif letter in lower:
            pass
        else: 
            s = (s.replace("0", "*").replace("4","*").replace("5","*").replace("6","*").replace("7","*")
            .replace("8","*").replace("9","*").replace("!","*").replace(")","*").replace("\\","*")
            .replace("[","*").replace("]","*").replace("%","*").replace("&","*").replace("(","*")
            .replace("+","*").replace(",","*").replace("-","*").replace(".","*").replace("/","*")
            .replace(":","*").replace(";","*").replace("?","*").replace("_","*").replace("`","*")
            .replace("{","*").replace("|","*").replace("}","*").replace("~","*").replace("\t","*")
            .replace("\n","*").replace("\r","*").replace("\x0b","*").replace("\x0c","*"))
    return s
    
   

def numberPyramid(num):
    n = num
    for x in xrange(n, 0, -1):
        p1 = "{0}".format(n)*n
        p2 = "{0}".format(n)*n
        s = " "*(2*num-2*n)
        print(p1 + s + p2)
        n = n-1
                

def reverseMultiTable(n):
    m = n
    for row in xrange(n, 0, -1):
        for col in xrange(n, 0, -1):
            print("{:<3}".format(col*row), end = "")
        m = m-1
        print()  
     

def enoughFor():
    gw1 = str(input("What is your target letter grade")) #grade wanted
    gw = gw1
    if gw == "A":
        gw = 90.0
    elif gw == "a": 
        gw = 90.0
    elif gw == "B":
        gw = 80.0
    elif gw == "b":
        gw = 80.0
    elif gw == "C":
        gw = 70.0
    elif gw == "c":
        gw = 70.0
    elif gw == "D":
        gw = 60.0
    elif gw == "d":
        gw = 60.0
    else: 
        print("Invalid letter grade.") 
        return
    gc = float(input("What is your current grade in percent")) #grade current
    wf = float(input("How much is the final worth in percent")) #weight of final
    gn = (100*gw-(100-wf)*gc)/wf #grade needed
    if gn > 100:
        print("Sorry, but you have no way to get a", gw1, "in this class.")
        return
    elif gn in range(90,100):
        gn = "A"
    elif gn in range(80,90):
        gn = "B"    
    elif gn in range(70,80):
        gn = "C"    
    elif gn in range(60,70):
        gn = "D"     
    print("You need a {0}% or better on the final to get a".format(gn), gw1 + ".")

   
    
    
    

    