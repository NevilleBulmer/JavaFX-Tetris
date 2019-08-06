# JavaFX-Tetris
Tetris built using JavaFX, no third party libraries needed.

Tetris, the classic game in which the player moves blocks down the screen with the aim of completing lines.

This version os very basic in terms of the user interface, with that said it has been built in such a way that within the code each block of the tetrino, I.e. four blocks per tetrino, is minipulatable.

Game menu has been added along with the beingings of a re-written architecture, redesign of the game menu and the game view.

Created a custom animation timer class to replace the older method of using a timer in conjunction with a timer task, this allows the ability to speed up the game at certain intervals I.e. level 5 - 10 - 15 - 20.

The custom animation class allow us to use the AnimationTimer built in to JavaFX in hand with cotrollable time in milliseconds.
