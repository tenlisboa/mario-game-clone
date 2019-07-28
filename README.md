# mario-game-clone

This project was make for fun, is a clone of Super Mario game and ins't done, was created in Java without any game engine.

I will describe the structure of the project to make more easier to understand what was made.

Most classes have dwo methods that will do two different things:
```
public void tick() {}
```
and
```
public void render(Graphics g) {}
```
The `tick` will run every millisecond and update the game, like the player movement and etc...
The `render` will run every millisecond and render something as you wish, like the player sprite, or create the player running animation.

The `Game` class will contain the `main` method that will initialize the Game and run the `render/tick` methods of each class.

The `World` class will control how the world will be render, based on small sprite and the color of the pixels on the sprite the world will be rendering.

The class `Tile` is the parent of `WallTile` and `FloorTile`, the `FloorTile` despite the name don't indicate a Floor and if the tile not has a collision and the `WallTile` the opposite of this.

... Comming soon ...
