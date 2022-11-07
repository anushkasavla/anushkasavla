# Project 3 Prep

**For tessellating pluses, one of the hardest parts is figuring out where to place each plus/how to easily place plus on screen in an algorithmic way.
If you did not implement tesselation, please try to come up with an algorithmic approach to place pluses on the screen. This means a strategy, pseudocode, or even actual code! 
Consider the `HexWorld` implementation provided near the end of the lab (within the lab video). Note that `HexWorld` was the lab assignment from a previous semester (in which students drew hexagons instead of pluses). 
How did your proposed implementation/algorithm differ from the given one (aside from obviously hexagons versus pluses) ? What lessons can be learned from it?**

Answer:
My proposed implementation did not use recursion since the shape of the squares is consistent n x n grid and the size stays consistent throughout the design. However, the recursive strategy made it much simpler to code and work through the steps. 

**Can you think of an analogy between the process of tessellating pluses and randomly generating a world using rooms and hallways?
What is the plus and what is the tesselation on the Project 3 side?**

Answer:
The plus is like a flower, while the tesselation is like a garden. 
-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating pluses.**

Answer:I was thinking of simply building the squares and then adding them together to build the plus, but thinking of it using recursion was much simpler and easier than doing it manually. 

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: A hallways would be longer and have a narrower shape, while the room would be shaped closer to a square rather than a thin rectangle. They are similar in that their shape can be composed by squares and rectangles. 
