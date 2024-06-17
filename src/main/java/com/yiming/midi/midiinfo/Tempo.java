package com.yiming.midi.midiinfo;

public class Tempo extends BaseData {
    int tempo;
    double bpm;


    public Tempo(long tick, byte[] bytes) {
        super(tick);
        tempo = ((int) bytes[0]) & 0x000000ff;
        tempo = (((int) bytes[1]) & 0x000000ff) | tempo << 8;
        tempo = (((int) bytes[2]) & 0x000000ff) | tempo << 8;
        tempo2bpm();
    }

    public void tempo2bpm() {
        bpm = 60 * 1000000.0 / tempo;
    }

    public void printInfo() {
        System.out.printf("Tempo.class:\t");
        System.out.printf("Tick: %d\t", tick);
        System.out.printf("tempo: %d\t", tempo);
        System.out.printf("bpm: %f\n", bpm);
    }
}
