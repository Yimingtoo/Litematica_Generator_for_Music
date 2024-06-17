package com.yiming.midi.midiinfo;

import org.apache.poi.ss.formula.functions.T;

//时间签名
class TimeSignature extends BaseData {
    int numerator; // 分子
    int denominator; //分母
    int clocks_per_click;
    int notated_32nd_notes_per_beat;

    public TimeSignature(long tick, byte[] bytes) {
        super(tick);
        numerator = bytes[0];
        denominator = 1;
        for (int i = 0; i < (int) bytes[1]; i++) {
            denominator *= 2;
        }
        clocks_per_click = bytes[2];
        notated_32nd_notes_per_beat = bytes[3];
    }

    public void printInfo() {
        System.out.printf("TimeSignature.class:\t");
        System.out.printf("Tick: %d\t", tick);
        System.out.printf("分子: %d\t", numerator);
        System.out.printf("分母: %d\t", denominator);
        System.out.printf("cpc: %d\t", clocks_per_click);
        System.out.printf("32npt: %d\n", notated_32nd_notes_per_beat);
    }

}