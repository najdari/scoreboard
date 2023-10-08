package com.nimaajdari.scoreboard.exception;

import com.nimaajdari.scoreboard.Game;

public class TeamAlreadyInGameException extends RuntimeException {

  public TeamAlreadyInGameException(String message) {
    super(message);
  }

  public static TeamAlreadyInGameException of(String team, Game game) {
    String message = "team %s cannot be added since it is has another active game on the board: %s"
        .formatted(team, game.toString());
    return new TeamAlreadyInGameException(message);
  }

}
