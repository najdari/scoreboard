package com.nimaajdari.scoreboard;

import com.nimaajdari.scoreboard.exception.InvalidScoreException;
import java.time.LocalDateTime;

public class Game {


  private final String homeTeam;
  private final String awayTeam;
  private int homeTeamScore;
  private int awayTeamScore;

  private final LocalDateTime createDateTime;

  public Game(String homeTeam, String awayTeam) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.createDateTime = LocalDateTime.now();
  }

  public String getHomeTeam() {
    return homeTeam;
  }

  public String getAwayTeam() {
    return awayTeam;
  }

  public int getHomeTeamScore() {
    return homeTeamScore;
  }

  public int getAwayTeamScore() {
    return awayTeamScore;
  }

  public int getTotalScore() {
    return homeTeamScore + awayTeamScore;
  }

  public LocalDateTime getCreateDateTime() {
    return createDateTime;
  }

  public Game setScore(int homeTeamScore, int awayTeamScore) {
    if (homeTeamScore < 0 || homeTeamScore < this.homeTeamScore) {
      throw new InvalidScoreException(
          "invalid home team score %d for game %s".formatted(homeTeamScore, this.toString()));
    }
    if (awayTeamScore < 0 || awayTeamScore < this.awayTeamScore) {
      throw new InvalidScoreException(
          "invalid away team score %d for game %s".formatted(awayTeamScore, this.toString()));
    }
    this.homeTeamScore = homeTeamScore;
    this.awayTeamScore = awayTeamScore;
    return this;
  }

  @Override
  public String toString() {
    return "Game{" +
        "homeTeam='" + homeTeam + '\'' +
        ", awayTeam='" + awayTeam + '\'' +
        ", homeTeamScore=" + homeTeamScore +
        ", awayTeamScore=" + awayTeamScore +
        ", createDateTime=" + createDateTime +
        '}';
  }
}
