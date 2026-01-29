package dev.msky.pixelui.utils.highscore;

import java.math.BigInteger;

public record SaveScoreResult(String table, String name, BigInteger score, int place, boolean isHighScore){
}