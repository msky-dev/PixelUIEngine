package dev.msky.pixelui.engine.actions.common;

public abstract class UpdateAction {

    public final int intervalMS;

    public long timer;

    public UpdateAction() {
        this(0, false);
    }

    public UpdateAction(int intervalMS) {
        this(intervalMS, false);
    }

    public UpdateAction(int intervalMS, boolean updateOnInit) {
        this.intervalMS = intervalMS;
        this.timer = updateOnInit ? (System.currentTimeMillis()+intervalMS) : System.currentTimeMillis() ;
    }

    public void onUpdate() {
    }

}
