package net.mslivo.pixelui.utils.persistence.highscore;

public record SaveScoreResult(String table, String name, long score, int place, boolean isHighScore){
}