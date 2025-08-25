package dev.scanysses.auto_1e7_stepping.client;

public class PositioningStep implements Runnable {
    private final int delay;
    private final Runnable action;

    public PositioningStep(int delay, Runnable action) {
        this.delay = delay;
        this.action = action;
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public void run() {
        action.run();
    }

}
