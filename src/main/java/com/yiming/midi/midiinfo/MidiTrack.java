package com.yiming.midi.midiinfo;

import org.apache.commons.math3.analysis.function.Min;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class MidiTrack {
    ArrayList<MidiNote> noteArrayList = new ArrayList<>();
    int trackNumber;
    String trackName = "";

    public MidiTrack(int index) {
        trackNumber = index;
    }

    public void addNote(MidiNote note) {
        noteArrayList.add(note);
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackName() {
        return "[Track-" + trackNumber + "] " + trackName;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public ArrayList<MidiNote> getNoteArrayList() {
        return noteArrayList;
    }

    public HashSet<Integer> getUsedChannel() {
        HashSet<Integer> channelHashSet = new HashSet<>();
        for (MidiNote midiNote : noteArrayList) {
            channelHashSet.add(midiNote.channel);
        }
//        System.out.println(channelHashSet.size());
        return channelHashSet;
    }

    public long[] getTickRange() {
        if (noteArrayList.isEmpty()) {
            return null;
        }
        Collections.sort(noteArrayList);
        return new long[]{noteArrayList.get(0).tick, noteArrayList.get(noteArrayList.size() - 1).tick};
    }

    public void printInfo() {
        System.out.printf("MidiTrack.class:\t");
        System.out.printf("trackNumber: %d\t", trackNumber);
        System.out.printf("noteArrayListLength: %d\t", noteArrayList.size());
        System.out.printf("trackName: %s\n", getTrackName());
    }

}
