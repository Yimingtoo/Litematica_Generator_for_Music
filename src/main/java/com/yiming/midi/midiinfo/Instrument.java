package com.yiming.midi.midiinfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Instrument extends BaseData {
    static public int getInstrumentTypeFromChannel(HashSet<Instrument> instrumentArrayList, int channel) {
        for (Instrument instrument : instrumentArrayList) {
            if (instrument.channel == channel) {
                return instrument.instrumentType;
            }
        }
        return -2;
    }



    int channel;
    int instrumentType;

    public Instrument(long tick, int channel, int data1) {
        super(tick);
        this.channel = channel;
        instrumentType = data1;
    }

    public boolean isEqual(Instrument instrument) {
        return this.channel == instrument.channel && this.tick == instrument.tick && this.instrumentType == instrument.instrumentType;
    }

    public void printInfo() {
        System.out.printf("Instrument.class:\t");
        System.out.printf("Tick: %d\t", tick);
        System.out.printf("channel: %d\t", channel);
        System.out.printf("instrumentType: %d\n", instrumentType);
//        System.out.printf("keyName: %s\n", keyName);
    }

    @Override
    public boolean equals(Object o) {
        return isEqual((Instrument) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel, instrumentType, tick);
    }

}
