package com.nimaajdari.scoreboard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nimaajdari.scoreboard.exception.GameNotFoundException;
import com.nimaajdari.scoreboard.exception.TeamAlreadyInGameException;
import org.junit.jupiter.api.Test;

public class BoardTest {

  private final static String GERMANY = "Germany";
  private final static String SPAIN = "Spain";
  private final static String ARGENTINA = "Argentina";
  private final static String USA = "USA";
  private final static String JAPAN = "Japan";
  private final static String PORTUGAL = "Portugal";

  final Board board = new Board();

  @Test
  void startingGamePutsItInTheScoreBoard() {
    board.startGame(GERMANY, SPAIN);
    var games = board.gamesSummary();

    assertThat(games).hasSize(1)
        .allMatch(game -> game.getHomeTeam().equals(GERMANY)
            && game.getAwayTeam().equals(SPAIN))
        .allMatch(game -> game.getAwayTeamScore() == 0)
        .allMatch(game -> game.getHomeTeamScore() == 0)
        .allMatch(game -> game.getTotalScore() == 0);
  }

  @Test
  void startingGameTwiceThrowsException() {
    board.startGame(GERMANY, SPAIN);
    assertThatThrownBy(() -> board.startGame(GERMANY, SPAIN))
        .isInstanceOf(TeamAlreadyInGameException.class)
        .hasMessageContaining(GERMANY, SPAIN);
  }

  @Test
  void startingGameWithTeamAlreadyInAnotherGameThrowsException() {
    board.startGame(GERMANY, SPAIN);
    assertThatThrownBy(() -> board.startGame(USA, SPAIN))
        .isInstanceOf(TeamAlreadyInGameException.class)
        .hasMessageContaining(GERMANY, SPAIN);
  }

  @Test
  void endingSingleGameOnBoardMakesBoardEmpty() {
    board.startGame(GERMANY, SPAIN);
    board.endGame(GERMANY, SPAIN);
    assertThat(board.gamesSummary()).isEmpty();
  }

  @Test
  void endingGameRemovesFromItBoard() {
    board.startGame(GERMANY, SPAIN);
    board.startGame(PORTUGAL, JAPAN);
    board.startGame(USA, ARGENTINA);

    board.endGame(PORTUGAL, JAPAN);

    assertThat(board.gamesSummary()).hasSize(2);
  }

  @Test
  void endingNonExistentGameThrowsException() {
    board.startGame(GERMANY, SPAIN);
    board.startGame(PORTUGAL, JAPAN);
    board.startGame(USA, ARGENTINA);

    assertThatThrownBy(() -> board.endGame("A", "B"))
        .isInstanceOf(GameNotFoundException.class)
        .hasMessageContaining("A", "B");
  }

  @Test
  void endingGameOnEmptyBoardThrowsException() {
    assertThatThrownBy(() -> board.endGame(ARGENTINA, SPAIN))
        .isInstanceOf(GameNotFoundException.class)
        .hasMessageContaining(ARGENTINA, SPAIN);
  }

  @Test
  void updatingScoreChangesScoreBoard() {
    board.startGame(GERMANY, SPAIN);
    board.updateScore(GERMANY, SPAIN, 2, 1);

    assertThat(board.gamesSummary()).hasSize(1)
        .allMatch(game -> game.getAwayTeamScore() == 1)
        .allMatch(game -> game.getHomeTeamScore() == 2)
        .allMatch(game -> game.getTotalScore() == 3);
  }

  @Test
  void updatingScoreForNonExistentGameThrowsException() {
    board.startGame(GERMANY, SPAIN);
    board.startGame(PORTUGAL, JAPAN);
    board.startGame(USA, ARGENTINA);

    assertThatThrownBy(
        () -> board.updateScore(ARGENTINA, SPAIN, 1 , 1))
        .isInstanceOf(GameNotFoundException.class)
        .hasMessageContaining(ARGENTINA, SPAIN);
  }

  @Test
  void updatingScoreOnEmptyBoardThrowsException() {
    assertThatThrownBy(
        () -> board.updateScore(ARGENTINA, SPAIN, 1 , 1))
        .isInstanceOf(GameNotFoundException.class)
        .hasMessageContaining(ARGENTINA, SPAIN);
  }

  @Test
  void boardSummaryIsSortedByTotalScoreAndCreateDate() {
    board.startGame(GERMANY, SPAIN);
    board.startGame(PORTUGAL, JAPAN);
    board.startGame(USA, ARGENTINA);
    board.updateScore(GERMANY, SPAIN, 3, 1);
    board.updateScore(PORTUGAL, JAPAN, 2, 0);
    board.updateScore(USA, ARGENTINA, 2, 2);

    assertThat(board.gamesSummary()).hasSize(3);
    assertThat(board.gamesSummary()).element(0)
        .matches(game -> game.getHomeTeam().equals(USA) && game.getAwayTeam().equals(ARGENTINA));
    assertThat(board.gamesSummary()).element(1)
        .matches(game -> game.getHomeTeam().equals(GERMANY) && game.getAwayTeam().equals(SPAIN));
    assertThat(board.gamesSummary()).element(2)
        .matches(game -> game.getHomeTeam().equals(PORTUGAL) && game.getAwayTeam().equals(JAPAN));
  }

}
