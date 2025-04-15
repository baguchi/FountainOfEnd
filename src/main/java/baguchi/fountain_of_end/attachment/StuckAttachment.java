package baguchi.fountain_of_end.attachment;

public class StuckAttachment {
    public int stuckTick;

    public void tick() {
        if (this.stuckTick > 0) {
            --this.stuckTick;
        }
    }

    public boolean isStuck() {
        return stuckTick > 0;
    }
}
