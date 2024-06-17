package com.yiming.midi.midiinfo;

public class BaseData implements Comparable<BaseData>  {
    protected long tick;

    public BaseData(long tick) {
        this.tick = tick;
    }

    public long getTick() {
        return tick;
    }

    @Override
    public int compareTo(BaseData o) {
        return (int) (this.tick - o.tick);
    }
}
