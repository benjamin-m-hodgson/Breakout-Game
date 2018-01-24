game - Breakout Frenzy
====

First project for CompSci 308 Fall 2017

* Contributors: Benjamin Hodgson
* Dates worked and time spent: 

   Start date: 1/14/2018  
   End date: 1/23/2018     
   Hours worked: ~30  
   
* Project roles: 

   Sole creator and contributor. 
   
* Resources used:

[What is the "Game Loop"](https://gamedevelopment.tutsplus.com/articles/gamedev-glossary-what-is-the-game-loop--gamedev-2469)  
[JavaFX GameTutorial](https://carlfx.wordpress.com/2012/03/29/javafx-2-gametutorial-part-1/)  
[JavaFX Tutorial](http://tutorials.jenkov.com/javafx/index.html)  
[Getting Started with JavaFX](https://docs.oracle.com/javafx/2/get_started/jfxpub-get_started.htm)  

* Files used to start the project:

Breakout.java

* Files used to test the project:

The testing performed was done by running the game with Breakout.java and 
observing behavior.

*  Data or resource files required by the project:

The text file BlockPositions.txt contains the Block Positions in the game and is located
in the res resource folder.

* Information about using the program:

   'Cheat codes':  
   '1' - Generates the first level  
   '2' - Generates the second level   
   '3' - Generates the third level  
   '4' - Generates the fourth level  
   '5' - Generates the fifth level  
   'B' - Give the player an extra ball  
   'L' - Give the player an extra life  
   
* Design decisions, assumptions, or simplifications:

The game consists of 5 levels, each increasing in difficulty by
1. Decreasing the paddle width
2. Increasing the ball speed 
3. Changing the block configuration

The game has 4 block types, blocks that require more hits to break are more likely
to spawn with each level
1. Standard block: the default block that disappears after one hit.
2. Two-hit block: a block that disappears after two hits.
3. Three-hit block: a block that disappears after three hits.
4. Speed-Boost block: a block that increases the speed of the ball for the duration 
of the level and disappears after one hit.

The paddle will have 3 possible abilities. Each ability consumes 1 ability coin. 
1. Catch and Throw: When a key is pressed the paddle turns red and the next time it hits the ball the ball will be returned to the player. Only one ball can be caught at a time.
2. Longer Paddle: When a key is pressed the paddle turns purple and stretches to a longer width. After a short time the paddle returns to its normal color and size.   
3. Boost Paddle: When a key is pressed the paddle turns green and moves quicker. After a short time the paddle returns to its normal color and movement speed.

Blocks have the probability of dropping a power up. The power up falls to the bottom of the 
screen and must be 'caught' with the paddle to be consumed. 
1. Extra ball: Gives the player an extra ball that can be fired from the paddle by pressing a key
2. Extra life: Gives the player an extra life
3. Ability coin: Gives the player an ability coin that can be consumed to activate a 
paddle ability. 

Each power up is represented by a colored circle with the Blur effect 

* Known bugs, crashes, or problems

Below are the bugs I found while testing the game
1. After finishing a level, whether by beating or losing the level, the following screen does not correctly replay the same level or generate the following level. Selecting either option loads the correct level and displays the initial splash screen successfully, but when the player hits space to begin playing the screen reverts back to the previous screen that was displayed after the end of the last level. I struggled for hours unsuccessfully trying to figure out the source of this problem. Using the cheat keys to skip to another level works perfectly fine which leads me to believe the problem is related to loading another scene before trying to generate the level. When testing out the different levels use the cheat keys to view all of the levels.
2. Occassionally if two balls collide perfectly they can become stuck inside each other. This is extremely rare and I've implemented a method to step the balls positions backwards several frames upon collision to try to reduce this but the bug could still occur.

The only other 'problem' or concern I have with the program is the unattractiveness of some of the menu screens.

* Extra features

If there are more than 3 balls in play the balls all begin to change color.

* Assignment impressions

Overall I really enjoyed this assignment and think it's a perfect way to introduce people to the course. My only wish is that we could implement changes addressed in the analysis before the analysis is due. When I devote this much time to something I hate to stop and not see a fully finished product, especially when I have to spend a good amount of time analyzing flaws/imperfections in the code. Not being able to perfect this is eating me alive. 

