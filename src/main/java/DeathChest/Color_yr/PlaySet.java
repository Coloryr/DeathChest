package DeathChest.Color_yr;

public class PlaySet {
    private int x;
    private int y;
    private int z;
    private int mode;

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getMode() {
        int b = 0;
        if(DeathChest.Config.getDisable().contains(mode)) {
            for (int a : DeathChest.Config.getDisable()) {
                if (a == b)
                    b++;
                else {
                    mode = b;
                    break;
                }
            }
        }
        return mode;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
