package net.mslivo.pixelui.utils.systems.appengine;

public interface AppEngineOutputQueue {
    void addOutput(AppEngineIO appEngineIO);
    AppEngineIO newIO(int type);
}
