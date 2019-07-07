# VillageGame
This project was developed by a team of four people in three days' time, for the second round of group projects @ <Academia de Codigo_>'s Bootcamp#29, taking place by the end of the 7th week of code learning.

## Features
Multiplayer chat system across all connected terminals.
Menu command system to play and personalize some things.
Lounge chat system and when ready-to-play activation.
'Dead' players can only talk with 'dead' and 'not in-game' players.
'Dead' players can still watch the entire game as it unfolds.
'Not in-game' players cannot talk to 'in-game' players.

## Deployment
'ant build' the project (with the supplied build.xml)
Run the -jar file on the terminal and follow onScreen instructions.

## Usage
This project is meant to run on the computer that will act as the main server to host the game.
Once you run the server file on the computer it will ask for a Port Number.
The players should connect to the server with 'netcat <Server IPaddress><Server Port>'.

## Game rules
- If you are the wolf: Each night you chose a victim to kill
  
- If you are not the wolf: Your goal is to kill the wolf before it kills the entire players playing the game(aka the entire village)

## How-to play
- The game as a day-night cicle system.

- The game starts after every player as set the /ready command (when enough players are 'ready' the game will launch).

- At the start of the game, the Wolf will be chosen randomly amongst all players in the ready state.

#### Day stage
- Everyone must vote to kill someone that is allegadly the Wolf. (even the wolf gets to vote)
- (You can use the chat system to try to find out who the wolf is)
- After everyone as voted the person that got voted will 'die'. that player can no longer participate, but can comunicate with other 'dead' people.

#### Night stage
- In this stage only the wolf is allowed to use the console to "kill" someone he choses with the appropriated console command.
- Everyone else won't be able to chat in this stage.

- The game switches back to day and the cicle continues until the wolf is killed by the villagers or the wolf kills everyone.

##ScreenShots

## Credits
#### Jonathan Cuendet
#### Fernando Abreu
#### Edma Augusto
#### João Sanches
