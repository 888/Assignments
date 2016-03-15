#Alan Chiang, Section B, 903129489/achiang31@gatech.edu
#I worked on the homework assignment alone, using only this semester's course materials.

from Graphics import *

def magnumopus ():
    tabula_rasa = Window("Tabula Rasa")
    
    l1 = Line(Point(100,100),Point(110,150))
    l2 = Line(Point(100,100),Point(150,110))
    l3 = Line(Point(90,90),Point(60,100))
    l4 = Line(Point(90,90),Point(100,60))
    l5 = Line(Point(95,95),Point(140,100))
    l6 = Line(Point(95,95),Point(100,140))
    a1 = Line(Point(100,100),Point(130,138))
    a2 = Line(Point(100,100),Point(138,130))
    l1.draw(tabula_rasa)
    l2.draw(tabula_rasa)
    l3.draw(tabula_rasa)
    l4.draw(tabula_rasa)
    l5.draw(tabula_rasa)
    l6.draw(tabula_rasa)
    a1.draw(tabula_rasa)
    a2.draw(tabula_rasa)
    
    O1 = Oval(Point(80,120),25,12.5)
    O1.fill = Color("gray")
    O2 = Oval(Point(95,95),40,12.5)
    O2.rotate(135)
    O2.fill = Color("black")
    O3 = Oval(Point(120,80),25,12.5)
    O3.fill = Color("gray")
    O3.rotate(90)
    O1.draw(tabula_rasa)
    O2.draw(tabula_rasa)
    O3.draw(tabula_rasa)
    
    c1 = Circle(Point(120,120),15)
    c1.fill = Color("green")
    c2 = Circle(Point(130,120),5)
    c2.fill = Color("orange") 
    c3 = Circle(Point(120,130),5)
    c3.fill = Color("orange")
    c1.draw(tabula_rasa)
    c2.draw(tabula_rasa)
    c3.draw(tabula_rasa)
    
    r1 = Rectangle(Point(140,140),Point(250,200))
    r1.fill = Color("brown")
    r1.draw(tabula_rasa)
    
    T1 = SpeechBubble((80,0),(280,25),"Om nom nom nom.",(120,120))
    T1.draw(tabula_rasa)
    T2 = SpeechBubble((145,155),(300,300),"Hershey's",(200,200))
    T2.fill = Color("black")
    T2.draw(tabula_rasa)
    
magnumopus()