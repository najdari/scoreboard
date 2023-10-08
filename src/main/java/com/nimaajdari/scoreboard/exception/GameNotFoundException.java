package com.nimaajdari.scoreboard.exception;

public class GameNotFoundException extends RuntimeException {

  public GameNotFoundException(String message) {
    super(message);
  }

  public static GameNotFoundException of(String homeTeam, String awayTeam) {
    String message = "no game for home team %s and away team %s found".formatted(homeTeam, awayTeam);
    return new GameNotFoundException(message);
  }

}
