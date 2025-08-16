package com.practice.practice.model.enums;

import lombok.Getter;

@Getter
public enum SkillLevel {
  BEGINNER("Beginner"),
  AVERAGE("Average"),
  SKILLED("Skilled"),
  SPECIALIST("Specialist"),
  EXPERT("Expert");

  private String levelLabel;

  SkillLevel(String levelLabel) {
    this.levelLabel = levelLabel;
  }
  public static SkillLevel getLevel(String levelName) {
    for (SkillLevel level : SkillLevel.values()) {
      if (level.name().equalsIgnoreCase(levelName)) {
        return level;
      }
    }
    throw new IllegalArgumentException("No such skill level: " + levelName);
  }
}
