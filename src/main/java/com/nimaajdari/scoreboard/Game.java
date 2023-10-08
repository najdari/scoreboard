package com.nimaajdari.scoreboard;

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

  public Game setHomeTeamScore(int homeTeamScore) {
    this.homeTeamScore = homeTeamScore;
    return this;
  }

  public Game setAwayTeamScore(int awayTeamScore) {
    this.awayTeamScore = awayTeamScore;
    return this;
  }
}
