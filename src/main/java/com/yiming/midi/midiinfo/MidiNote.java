package com.yiming.midi.midiinfo;

import org.apache.poi.ss.formula.functions.T;

import javax.sound.midi.Instrument;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;

public class MidiNote extends BaseData {
    final String[] musicalAlphabet = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    String keyName;
    String fixKeyName;

    int key;
    int force;
    int track;
    int channel;

    public MidiNote(long tick, int channel, int track, int data1, int data2) {
        super(tick);
        key = data1;
        force = data2;
        this.track = track;
        this.channel = channel;
        convertIntoMName();
    }

    public void convertIntoMName() {
        int mod = key % 12;
        int octave = key / 12;
        keyName = musicalAlphabet[mod] + (octave - 1);
    }

    public int getKey() {
        return key;
    }


    /**
     * 使用之前需要将tempos和ppq进行赋值。
     */


    public void printInfo() {
        System.out.printf("BaseNote.class:\t");
        System.out.printf("Tick: %d\t", tick);
        System.out.printf("track: %d\t", track);
        System.out.printf("key: %d\t", key);
        System.out.printf("force: %d\t", force);
        System.out.printf("channel: %d\t", channel);
//        System.out.printf("secondTime: %.4f\t", MidiNote.getSecondTime(this.tick));
        System.out.printf("keyName: %s\n", keyName);
    }
}
