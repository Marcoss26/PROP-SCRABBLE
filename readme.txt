Scrabble Game Management System

University project implementing a Scrabble game developed in Java. The project follows a three-layer architecture pattern comprising:

- Persistence Layer: For data storage and retrieval 
- Domain Layer: for game logic (game rules, scoring, AI, ranking)
- Presentation Layer: With an interactive GUI using Java Swing

The system enables users to play Scrabble games, manage profiles, view rankings, and persist all relevant data.



Features

Game Management

- Create new games with customizable settings (board size, dictionary, players)
- Play games against human or AI opponents
- Save and load ongoing games
- Automatic scoring and validation of moves

Profile Management

- Create and manage user profiles
- Track games played, games won, win rate, and score statistics

Ranking System

- View global rankings based on wins and performance
- Rankings respect profile privacy settings

Dictionary Management

- Manage multiple dictionaries (Spanish, English, Catalan)
- Validate words during gameplay

User Interface

- GUI built with Java Swing
- Interactive board and rack for tile placement
- Dialogs for game actions, errors, and confirmations

Technical Details

- Built with: Java 17+
- GUI: Java Swing
- Architecture: Three-layer (Persistence, Domain, Presentation)
- Data Persistence: JSON files (using json-simple)
- Build System: Makefile 

Building and Running

To build the project different commands are required depending OS:

 - Windows: make run-Presentation-Windows
 - Linux and Mac: make run-Presentation-LinMac 

