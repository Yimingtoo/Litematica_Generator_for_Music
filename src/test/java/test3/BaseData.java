package test3;

public class BaseData implements Comparable<BaseData> {
    protected long tick;
    public BaseData(long tick) {
        this.tick = tick;
    }

    @Override
    public int compareTo(BaseData o) {
        return (int) (this.tick - o.tick);
    }
}
