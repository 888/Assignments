# Object Oriented Simulation World Example
#
# Using Calico Graphics and Python
#
# Jay Summet
#

WORLDSIZE = 250

from Graphics import *
from Myro import *
from random import randint

class Animal:
  MOVESPEED = 3

  def  __init__(self,x,y,window):
     self.x = x
     self.y = y
     self.win = window
     self.icon = Circle( (self.x, self.y), 4)
     self.icon.draw(self.win)
     self.setColor()

  def erase(self):
     self.icon.undraw()

  def setColor(self):
     pass

  def action(self):
     self.move()
     self.eat()

  def identifyFood(self,animal):
     return False

  def eat(self):
     global animals
     for ani in animals:
        if self.identifyFood(ani) == True:
           if ani.x == self.x and ani.y == self.y:
              print("I found some food at", self.x, self.y)
              ani.erase()
              animals.remove(ani)


  def move(self):
     from random import choice

     moveX = choice( [ self.MOVESPEED, 0, -self.MOVESPEED] )
     moveY = choice( [ self.MOVESPEED, 0, -self.MOVESPEED] )


     #Check to make sure we don't move off of the world!
     if self.x + moveX > WORLDSIZE-1:
        moveX  = 0

     if self.y + moveY > WORLDSIZE -1:
        moveY = 0

     if self.x + moveX < 0:
        moveX = 0

     if self.y + moveY < 0:
        moveY = 0

     self.x = self.x + moveX
     self.y = self.y + moveY

     self.icon.move(moveX, moveY)


class Rabbit(Animal):

   def setColor(self):
     co = Color("white")
     self.icon.color = co

class Fox(Animal):
   MOVESPEED = 10

   def identifyFood(self,animal):
      if "Rabbit" in str(animal):
         return True
      else:
         return False

   def setColor(self):
     co = Color("red")
     self.icon.color = co

class Bear(Animal):
   MOVESPEED = 10

   def identifyFood(self,animal):
      if "Fox" in str(animal):
         return True
      else:
         return False

   def setColor(self):
     co = Color("brown")
     self.icon.color = co


win = Window("Animals", WORLDSIZE, WORLDSIZE)

animals = []

for x in range(15):
   randX = randint(0, WORLDSIZE-1)
   randY = randint(0, WORLDSIZE-1)
   ani = Rabbit(randX,randY,win)
   animals.append(ani)

for x in range(5):
   randX = randint(0, WORLDSIZE-1)
   randY = randint(0, WORLDSIZE-1)
   ani = Fox(randX,randY,win)
   animals.append(ani)

for x in range(5):
   randX = randint(0, WORLDSIZE-1)
   randY = randint(0, WORLDSIZE-1)
   ani = Bear(randX,randY,win)
   animals.append(ani)

for t in timer(250):
  for ani in animals:
    ani.action()
  wait(0.10)

