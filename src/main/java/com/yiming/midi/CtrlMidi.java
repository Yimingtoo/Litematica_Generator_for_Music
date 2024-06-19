package com.yiming.midi;

import com.yiming.midi.midiinfo.MidiInfo;
import com.yiming.midi.midiinfo.MidiNote;
import com.yiming.midi.midiinfo.MidiTrack;

import javax.sound.midi.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CtrlMidi {
    Sequencer sequencer;
    Sequence sequence;
    MidiInfo midiInfo;
    float tempoInBPM = 120;
    float tempoFactor = 1;


    public CtrlMidi(String filePath) throws IOException, MidiUnavailableException, InvalidMidiDataException {
        File mid = new File(filePath);
        FileInputStream fis = new FileInputStream(mid);
        midiInfo = new MidiInfo(mid);
        sequencer = MidiSystem.getSequencer();
        sequence = MidiSystem.getSequence(fis);

        initData();

//        tempoInBPM = 150;
        sequencer.open();
        sequencer.setSequence(sequence);
        System.out.println(sequencer.getTickLength());
        System.out.println(midiInfo.getSecondTime(sequencer.getTickLength()));

    }

    //初始化数据程序
    public void initData() {
//        tempoInBPM = sequencer.getTempoInBPM();
//        System.out.println("tempoInBPM" + tempoInBPM);

    }


    public void startPlay() {
        if (!sequencer.isRunning()) {
            sequencer.start();
//            sequencer.setTempoInBPM(tempoInBPM);
            sequencer.setTempoFactor(tempoFactor);
        }
    }

    public boolean isPlaying() {
        return sequencer.isRunning();
    }

    public void stopPlay() {
        if (sequencer.isRunning()) {
            sequencer.stop();
        }
    }

    public long getTickPosition() {
        return sequencer.getTickPosition();
    }

    public long getTickLength() {
        return sequencer.getTickLength();
    }

    public double getSecondTime() {
        return midiInfo.getSecondTime(sequencer.getTickPosition());
    }

    public double getMusicTime() {
        return midiInfo.getSecondTime(sequencer.getTickLength());
    }

    public int getSecondLength() {
        return (int) Math.floor(sequencer.getMicrosecondLength() / 1000000.0);
    }

    public void setTickPosition(long tick) {
        sequencer.setTickPosition(tick);
    }

    public void setTickPosition(double tick) {
        sequencer.setTickPosition((long) tick);
    }


    public MidiInfo getMidiInfo() {
        return midiInfo;
    }

    public void setTempoFactor(float tempoFactor) {
        this.tempoFactor = tempoFactor;
        if (sequencer.isRunning()) {
            sequencer.stop();
            sequencer.setTempoFactor(this.tempoFactor);
            sequencer.start();
        }
    }

    public void setTrackMute(int trackIndex, boolean mute) {
        sequencer.setTrackMute(trackIndex, mute);
    }

    public void setAllTrackMute(boolean mute) {
        for (int i = 0; i < midiInfo.getTrackNum(); i++) {
            setTrackMute(i, mute);
        }
    }

    public String convertDisplayTime(int second) {
        return String.format("%d:%02d", second / 60, second % 60);
    }

    public ArrayList<ArrayList<ArrayList<Integer>>> getMidiNotes(ArrayList<Integer> mainTrackSeq, ArrayList<Integer> viceTrackSeq, float speed) {
        ArrayList<ArrayList<Integer>> leftNotes = new ArrayList<>();
        ArrayList<ArrayList<Integer>> rightNotes = new ArrayList<>();

        for (long i = 0; i < Math.ceil(getMusicTime() / 0.05 / speed); i++) {
            leftNotes.add(new ArrayList<>());
            rightNotes.add(new ArrayList<>());
        }

        for (int i = 0; i < mainTrackSeq.size(); i++) {
            ArrayList<MidiNote> tempMidiNotes = midiInfo.getTrackArrayList().get(mainTrackSeq.get(i)).getNoteArrayList();
            for (int j = 0; j < tempMidiNotes.size(); j++) {
                int pos = (int) Math.ceil(midiInfo.getSecondTime(tempMidiNotes.get(j).getTick()) / 0.05 / speed);
                leftNotes.get(pos).add(tempMidiNotes.get(j).getKey());
            }
        }
        for (int i = 0; i < viceTrackSeq.size(); i++) {
            ArrayList<MidiNote> tempMidiNotes = midiInfo.getTrackArrayList().get(viceTrackSeq.get(i)).getNoteArrayList();
            for (int j = 0; j < tempMidiNotes.size(); j++) {
                int pos = (int) Math.ceil(midiInfo.getSecondTime(tempMidiNotes.get(j).getTick()) / 0.05 / speed);
                rightNotes.get(pos).add(tempMidiNotes.get(j).getKey());
            }
        }
        int temp;
        for (int i = 0; i < leftNotes.size(); i++) {
            temp = leftNotes.get(i).size();
            if (temp < 4) {
                for (int j = 0; j < 4 - temp; j++) {
                    leftNotes.get(i).add(-1);
                }
            }
            // 这里4富裕
            temp = rightNotes.get(i).size();
            if (temp < 4) {
                for (int j = 0; j < 4 - temp; j++) {
                    rightNotes.get(i).add(-1);
                }
            }
        }
        ArrayList<ArrayList<ArrayList<Integer>>> ret = new ArrayList<>();
        ret.add(leftNotes);
        ret.add(rightNotes);
        return ret;
    }


    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        CtrlMidi ctrlMidi = new CtrlMidi("D:\\Data\\IntelliJ_IDEA\\MCPrj\\LitematicaFile3\\res\\sakura.mid");

        ctrlMidi.stopPlay();
    }
}
