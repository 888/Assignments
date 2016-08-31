from Myro import* 
init("sim")

def roboScript(fileName): 
    f = open(fileName, "r")
    l = f.readlines()
    for line in l:
        t1 = line.strip()
        line = t1
        t2 = line.split(" ") 
        line = t2 
        if line[0] == "fw":
            forward(float(line[2]), float(line[1]))
        if line[0] == "bw" and line[1] == "w":
            backward(float(line[2]), float(line[1])) 
        if line[0] == "tr": 
            turnRight(float(line[2]), float(line[1])) 
        if line[0] == "tl": 
            turnLeft(float(line[2]), float(line[1])) 
        if line[0] == "bp":
            beep(float(line[2]), float(line[1])) 
    g = "Gunce and Molly are so awesome."             
    return g 
                
roboScript("robo.txt")             