# DDSE
Devil Daggers Spawnset Editor - built in Java
## Running DDSE
Running DDSE requires you to have Java 8 or higher installed. [Download Java](https://www.java.com/en/download/)

All you have to do to run DDSE is to open the .jar file (_DDSE.jar_). __Double-click__ or __Right-click -> Open with -> Java(TM) Platform SE Binary__.
## Using DDSE
Starting from the left, you'll see a list box where you can view, edit delays, and delete the enemy spawns in your spawnset. You can select multiple items in the list for deletion or delay editing.

In the middle is where you basically input all your spawnset values. You can add one or more enemy types at a time with the checkboxes, and all of them will be on the same spawn time (only first has delay, rest delay 0). Below that, you can edit the starting and finishing radius of the "shrink circle." (Shrink circle is an invisible circle that determines both what tiles need to fall and where enemies can spawn) You can also edit the rate at which the shrink circle gets smaller, and the brightness of the ambient light in the arena. Both radii and the shrink rate are in units and units/second respectively. (A unit is 1/4 of a tile)

Finally, on the right you'll see a 23x23 grid that resembles the default arena layout. Clicking on any one of the tiles in the grid will toggle whether that tile is enabled (able to be seen and stood on). Cannot change the height of the tiles (yet).

## Bugs and Issues
If you encounter any bugs, crashes, inconveniences, etc whilst using DDSE, please either [submit an issue](https://github.com/bowsr/DDSE/issues/new) or let me know on Twitter [@bowsrcs](https://twitter.com/bowsrcs).
