package Game.Helpers;

public class Timer {
    private int preferredFPS;
    private int preferredUPS;
    private int actualFPS = 0;
    private int actualUPS = 0;
    private float delta;
    private long lastUpdateTime;
    private long lastRenderTime;
    private long updateTimeElapsed;
    private long renderTimeElapsed;
    private long lastCalculationTime;
    public Timer(int FPS, int UPS) {
        this.preferredFPS = FPS;
        this.preferredUPS = UPS;
        this.lastUpdateTime = System.nanoTime();
        this.lastRenderTime = System.nanoTime();
        this.lastCalculationTime = System.nanoTime();
    }
    public void updateTimer() {
        long now = System.nanoTime();
        updateTimeElapsed = now  - lastUpdateTime;
        renderTimeElapsed = now  - lastRenderTime;
        delta = (float) (updateTimeElapsed / 1e9);
    }
    public boolean updateGame(boolean isPaused) {
        boolean doUpdate = updateTimeElapsed >= 1e9 / preferredUPS;
        if (doUpdate) {
            lastUpdateTime = System.nanoTime();
            if (!isPaused) {
                actualUPS++;
            }
        }
        return doUpdate;
    }
    public boolean updateRender() {
        boolean doUpdate = renderTimeElapsed >= 1e9 / preferredFPS;
        if (doUpdate) {
            lastRenderTime = System.nanoTime();
            actualFPS ++;
        }
        return doUpdate;
    }
    public void setPreferredFPS(int preferredFPS) {
        this.preferredFPS = preferredFPS;
    }


    public void setPreferredUPS(int preferredUPS) {
        this.preferredUPS = preferredUPS;
    }

    public float getDelta() {
        return delta;
    }

    public int getActualFPS() {
        return actualFPS;
    }

    public int getActualUPS() {
        return actualUPS;
    }
    public boolean calculationReady() {
        boolean isReady = false;
        if (System.nanoTime() - lastCalculationTime >= 1e9) {
            lastCalculationTime = System.nanoTime();
            isReady = true;
        }
        return isReady;
    }
    public void resetCalculation() {
        actualFPS = 0;
        actualUPS = 0;
    }
}
