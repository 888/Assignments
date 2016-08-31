#Alan Chiang, 903129489, achiang31@gatech.edu 
#I worked on this assignment alone, using only this semester's course materials.

def onlySixths(x):
    y = []
    for num in x:
        if num % 6 == 0:
            y.append(num) 
    return y


def union(a,b):
    a = set(a); b = set(b);
    c = set.union(a,b)
    d = sorted(c)
    d = list(d)
    return d
    
    
def multiplyNums(z):
    result = 1
    for num in z:
        if type(num) == list:
            i = multiplyNums(num)
            result = i * result
        elif type(num) == int:
            result = num * result
        elif type(num) == float:
            result = num * result
    return result 

                
def abbreviator(w):
    v = list(w)
    for letter in w:
        if letter in "ABCDEFGHIJKLMNOPQRSTUVWXYZ":
            pass
        elif letter in "0123456789":
            pass
        else:
            v.remove(letter)
    w = ""
    for letter in v:
        w = w + letter
    return w  
 
 
def parse(x,y):
    x1 = ""
    x2 = []
    for letter in x:
        if letter != y:
            x1 = x1 + letter      
        elif letter == y: 
            x2.append(x1)
            x1 = ""
    x2.append(x1)  
    return x2 


#test with real values, especially median, and make sure v is ok 

def lightStats():
    from Myro import*
    init("sim")
    o = getLight()
    print("Left:", o[0])
    print("Center:", o[1])
    print("Right:", o[2])
    s = sum(o) 
    d = len(o)
    mea = s/d 
    ran = max(o) - min(o)
    med = 5000.0
    for v in o:
        if v == min(o):
            pass
        elif v == max(o):
            pass
        else:
            med = v
    r = [mea,med,ran]
    return r 
lightStats()