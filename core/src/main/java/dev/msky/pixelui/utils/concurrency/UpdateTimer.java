package dev.msky.pixelui.utils.concurrency;

public class UpdateTimer {

    private float accumulator;
    private float timeStep;
    private float maxAccumulated;
    private static final int DEFAULT_FRAME_ACCUMULATE = 2;
    private long nanos;

    public UpdateTimer(int maxUpdatesPerSecond) {
        setTargetUpdates(maxUpdatesPerSecond, DEFAULT_FRAME_ACCUMULATE);
    }

    public UpdateTimer(int maxUpdatesPerSecond, int maxAccumulatedSteps) {
        setTargetUpdates(maxUpdatesPerSecond, maxAccumulatedSteps);
    }

    public void setTargetUpdates(int maxUpdatesPerSecond) {
        setTargetUpdates(maxUpdatesPerSecond, DEFAULT_FRAME_ACCUMULATE);
    }

    public void setTargetUpdates(int maxUpdatesPerSecond, int maxAccumulatedSteps) {
        maxUpdatesPerSecond = Math.max(maxUpdatesPerSecond, 1);
        maxAccumulatedSteps = Math.max(maxAccumulatedSteps, 1);
        timeStep = 1f / (float) maxUpdatesPerSecond;
        maxAccumulated = timeStep * maxAccumulatedSteps;
        accumulator = 0f;
        this.nanos = System.nanoTime();
    }

    public boolean shouldUpdate() {
        long now = System.nanoTime();
        float deltaTime = (now - nanos) * 1e-9f; // nanoseconds -> seconds
        nanos = now;
        accumulator = Math.min(accumulator + deltaTime, maxAccumulated);
        if (accumulator >= timeStep) {
            accumulator -= timeStep;
            return true;
        }

        return false;
    }

    public float delta() {
        return timeStep;
    }
}
