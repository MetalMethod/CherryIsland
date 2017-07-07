# CherryIsland
A multiplayer survival game. Group project for bootcamp @ AcademiaDeCodigo.org. 
by: João Faustino, João Portela, Joel Almeida, Igor Busquets, Diogo Costa.

Game story:

Once upon a time...

...a happy vacation trip started
In a yatch, full of happy people.

Bad luck hit the fan, a storm hit the boat, 
it sinks and guess what...

You wake up in a beach. Along your friends. 

There's cherries everywhere. That's the good part.

The bad part is that there's no one else around to help. You soon realize you're all on a desert island.

Now You must work together to find a way to get out. 

Don't forget to drink water, eat, and build a shelter, so you and your friends can safelly sleep.

Game goal:
Gatter collectable items to build a boat and get out of the island. Wood, rope, fabric and food are required for that.

1) Premise

Survival game: stay alive and gather resources to build a boat and escape the island.


2) Modes

Competitive network multiplayer (2 players, first to escape wins).

3) Rules (for MVP)

Player views a part of the island, view is centered on his position. Player needs to search and gather wood from trees to be able to build a boat. Other trees provide cherries(food). 

Player controls: move up, left, down, right, drink water( only works when close to lake and health < 50% ), eat cherries, build boat.

How to stay alive: Drink water (present in lakes) and eat. Health lowers with time, goes up when drinking water (up to 50% health) or eating food(cherries). Number of cherries the player can hold at any given time is limited (very important for game balance).

How to build boat: Needs a set amount of each resource. Option to build boat appears on coastline.
Tree resources are found randomly on the island, they appear randomly and disappear after a set amount of time. A given resource can only spawn if the number of instances of that resource currently available on the island is < max.
Resources are more likely to spawn far from lakes (sources of water).

graphics(MVP):
- splash screen.
- 2 unique characters, male and female, 4 sprites each.
- one color variant for each character (just to add variation of choice).
- first collectable items:
	cherry, wood, rope, fabric, water
- map tiles (visual only):
	grass(3 colors for variation), rocks (2 different), flowers, big trees(3 versions for variations), beach(ocean, waves, sand)
- shelter tiles.
- boat tiles.
- game succesfully finished / end picture.
- game over/player died screen.

Later development features (second sprint):
- features for later implementation:
	admin players, chat/game kick for spammers, remove idle players, bigger and updated map. 

- items to be implemented later on second sprint:
	 rock, wheat, cotton, seeds, tools(axe, shovel, fork) 





