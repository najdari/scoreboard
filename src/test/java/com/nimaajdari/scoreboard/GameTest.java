package com.nimaajdari.scoreboard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nimaajdari.scoreboard.exception.InvalidScoreException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GameTest {

  @Test
  void gameInitialization() {
    LocalDateTime start = LocalDateTime.now();
    var game = new Game("a", "b");
    assertThat(game.getHomeTeam()).isEqualTo("a");
    assertThat(game.getAwayTeam()).isEqualTo("b");
    assertThat(game.getHomeTeamScore()).isEqualTo(0);
    assertThat(game.getAwayTeamScore()).isEqualTo(0);
    assertThat(game.getTotalScore()).isEqualTo(0);
    assertThat(game.getCreateDateTime()).isNotNull()
        .isAfter(start)
        .isBefore(LocalDateTime.now());
  }

  @Test
  void gameScoreUpdate() {
    var game = new Game("a", "b");
    game.setScore(1, 2);
    assertThat(game.getTotalScore()).isEqualTo(3);
    assertThat(game.getHomeTeamScore()).isEqualTo(1);
    assertThat(game.getAwayTeamScore()).isEqualTo(2);
  }

  @ParameterizedTest
  @MethodSource("com.nimaajdari.scoreboard.GameTest#invalidScoreSource")
  void invalidScoreUpdateThrowsException(int homeScoreInit, int awayScoreInit,
                                         int homeScoreNew, int awayScoreNew) {
    var game = new Game("a", "b");
    game.setScore(homeScoreInit, awayScoreInit);
    assertThatThrownBy(() -> game.setScore(homeScoreNew, awayScoreNew))
        .isInstanceOf(InvalidScoreException.class)
        .hasMessageContaining("a", "b");
  }

  static Stream<Arguments> invalidScoreSource() {
    return Stream.of(
        Arguments.of(0, 0, -1, 0),
        Arguments.of(0, 0, 0, -1),
        Arguments.of(2, 3, 1, 3),
        Arguments.of(2, 3, 2, 2)
    );
  }

}
