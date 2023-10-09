# Scoreboard

Simple score board implementation in Java.

To run the code you need Java 17 or higher.

In order to run the tests run:
```
./gradlew test
```

## Usage

There are two main classes in the library: `Board` and `Game`.


First you create an instance of the `Board` class. 
Then by calling `startGame` and `endGame` methods, you can add and remove games from the board.
You can call `updateScore` to change the score of the games. 
And to get the summary of the active games, you can call `gamesSummary`.

```java
Board board = new Board();

// start games
board.startGame("Germany", "Spain");
board.startGame("Brazil", "Mexico");

// update score
board.updateScore("Germany", "Spain", 1, 0);
board.updateScore("Brazil", "Mexico", 0, 2);

// get summary
// returns:
// 1. Brazil - Mexico (0 - 2)
// 2. Germany - Spain (1 - 0)
List<Game> games = board.gamesSummary();

// removes game from the board
board.endGame("Germany", "Spain");

```

## Assumptions

1. A team cannot participate in multiple active games simultaneously.
2. Scores can only increase and must remain non-negative.

