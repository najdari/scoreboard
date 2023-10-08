package com.nimaajdari.scoreboard;

import com.nimaajdari.scoreboard.exception.GameNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Board {

  private final List<Game> games = new ArrayList<>();

  public void startGame(String homeTeam, String awayTeam) {
    Game game = new Game(homeTeam, awayTeam);
    games.add(game);
  }

  public void endGame(String homeTeam, String awayTeam) {
    Game game = findGame(homeTeam, awayTeam)
        .orElseThrow(() -> GameNotFoundException.of(homeTeam, awayTeam));
    games.remove(game);
  }

  public void updateScore(String homeTeam, String awayTeam,
                          int homeTeamScore, int awayTeamScore) {
    Game game = findGame(homeTeam, awayTeam)
        .orElseThrow(() -> GameNotFoundException.of(homeTeam, awayTeam));
    game.setHomeTeamScore(homeTeamScore);
    game.setAwayTeamScore(awayTeamScore);
  }

  public List<Game> gamesSummary() {
    return games.stream()
        .sorted(Comparator.comparing(Game::getTotalScore).reversed()
            .thenComparing(Comparator.comparing(Game::getCreateDateTime).reversed()))
        .toList();
  }

  private Optional<Game> findGame(String homeTeam, String awayTeam) {
    return games.stream()
        .filter(game -> game.getHomeTeam().equals(homeTeam)
            && game.getAwayTeam().equals(awayTeam))
        .findFirst();
  }

}
