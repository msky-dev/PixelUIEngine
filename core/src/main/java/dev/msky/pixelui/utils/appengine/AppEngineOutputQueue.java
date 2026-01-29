package dev.msky.pixelui.utils.appengine;

public interface AppEngineOutputQueue {
    void addOutput(AppEngineIO appEngineIO);
    AppEngineIO newIO(int type);
}
