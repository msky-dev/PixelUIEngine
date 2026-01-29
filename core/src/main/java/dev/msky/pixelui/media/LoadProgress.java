package dev.msky.pixelui.media;

public interface LoadProgress {

    void onLoadStep(String name, int step, int stepsMax);

}
