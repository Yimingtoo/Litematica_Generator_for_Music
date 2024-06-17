package com.yiming.midi.midiinfo;

import com.yiming.Soutput;

import javax.sound.midi.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class MidiInfo {
    File originMidiFile;
    Sequencer sequencer;
    Sequence sequence;
    Track[] tracks;

    //Midi源信息
    public enum TrackType {
        ONE_TRACK, SYNC_MULTI_TRACK, ASYNC_MULTI_TRACK
    }


    TrackType trackType = TrackType.ONE_TRACK;//轨道类型
    int trackNum; // track的数量
    int ppq; //一个四分音符的tick数量


    ArrayList<Tempo> tempoArrayList = new ArrayList<>();
    ArrayList<TimeSignature> timeSignatureArrayList = new ArrayList<>();
    ArrayList<KeySignature> keySignatureArrayList = new ArrayList<>();
    ArrayList<MidiTrack> trackArrayList = new ArrayList<>();
    ArrayList<Long> tickEndOfTrack = new ArrayList<>();
//    ArrayList<Instrument> instrumentArrayList = new ArrayList<>();
    HashSet<Instrument> instrumentHashSet = new HashSet<>();

    public MidiInfo(File midiFile) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        originMidiFile = midiFile;
        FileInputStream fis = new FileInputStream(originMidiFile);
        sequencer = MidiSystem.getSequencer();
        sequence = MidiSystem.getSequence(fis);
        fis.skip(fis.available() - midiFile.length());
        byte[] bytes = fis.readNBytes(14);
        tracks = sequence.getTracks();
        if (bytes[7] == (byte) 0x06) {
            trackNum = (int) bytes[11];
        } else {
            Soutput.println("Error: 轨道数目读取错误！");
            System.exit(0);
        }
        if (tracks.length != trackNum) {
            Soutput.println("Error: Midi文件轨道数目错误！\t" + trackNum + "(File)!=" + tracks.length + "(Java.midi)");
            System.exit(0);
        }

        if ((bytes[12] & 0x80) != 0x00) {
            Soutput.println("Error: 不支持的时间格式！Message:ppq");
            System.exit(0);
        }

        MidiFileFormat midiFileFormat = MidiSystem.getMidiFileFormat(originMidiFile);
        //读取一个四分音符的时间（微秒）
        ppq = midiFileFormat.getResolution();
        //读取轨道类型
        trackType = switch (midiFileFormat.getType()) {
            case 0 -> TrackType.ONE_TRACK;
            case 1 -> TrackType.SYNC_MULTI_TRACK;
            case 2 -> TrackType.ASYNC_MULTI_TRACK;
            default -> trackType;
        };
        if (trackType != TrackType.SYNC_MULTI_TRACK) {
            Soutput.println("Error: 当前不支持的轨道类型\t" + trackType);
            System.exit(0);
        }
        for (int i = 0; i < trackNum; i++) {
            trackArrayList.add(new MidiTrack(i));
        }
        getTrackInfo(this.tracks);
        printInfo();

    }

    public void getTrackInfo(Track[] tracks) {
        boolean ctrl = true;
        int trackCount = -1;
        for (Track track : tracks) {
            trackCount++;
            //read event
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                if (event.getMessage() instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) event.getMessage();
                    byte[] bytes = metaMessage.getData();
                    readMetaMessage(event.getTick(), trackCount, metaMessage);

                    // output
                    if (ctrl) {
                        System.out.printf("tick:0xx%x " + event.getTick() + "  " + event.getMessage() + "\n", event.getTick());
                        System.out.printf("MetaMessage.Type:0x%x %d\n", metaMessage.getType(), metaMessage.getType());
                        System.out.printf("MetaMessage.Data:");
                        for (byte bytes1 : bytes) {
                            System.out.printf("%2x ", bytes1);
                        }
                        System.out.println("");
                    }

                }
                if (event.getMessage() instanceof ShortMessage) {
                    ShortMessage shortMessage = (ShortMessage) event.getMessage();

                    readshortMessage(event.getTick(), trackCount, shortMessage);
                    // output
                    if (ctrl) {
                        System.out.printf("tick:0xx%x " + event.getTick() + "  " + event.getMessage() + "\n", event.getTick());
                        System.out.println("Channel:" + shortMessage.getChannel());
                        System.out.printf("Command:0x%x\n", shortMessage.getCommand());
                        System.out.printf("Data1:0x%x %d\n", shortMessage.getData1(), shortMessage.getData1());
                        System.out.printf("Data2:0x%x %d\n", shortMessage.getData2(), shortMessage.getData2());
                    }
                }
            }
        }
    }

    public void readMetaMessage(long tick, int trackNum, MetaMessage metaMessage) {
        byte[] bytes = metaMessage.getData();
        switch (metaMessage.getType()) {
            case 0x03:
                trackArrayList.get(trackNum).setTrackName(new String(metaMessage.getData()));
                break;
            case 0x2f:
                tickEndOfTrack.add(tick);
                break;
            case 0x51:
                tempoArrayList.add(new Tempo(tick, bytes));
//                tempoArrayList.add(new Tempo(tick, bytes));
                break;
            case 0x58: //Time signature
                timeSignatureArrayList.add(new TimeSignature(tick, bytes));
                break;
            case 0x59: //Key signature
                keySignatureArrayList.add(new KeySignature(tick, bytes));
                break;

        }
    }

    public void readshortMessage(long tick, int trackNum, ShortMessage shortMessage) {
        switch (shortMessage.getCommand()) {
            case 0x90:
                if (shortMessage.getData2() > 0) {
                    trackArrayList.get(trackNum).addNote(new MidiNote(tick,shortMessage.getChannel(), trackNum, shortMessage.getData1(), shortMessage.getData2()));
                }
                break;
            case 0xc0:
                instrumentHashSet.add(new Instrument(tick, shortMessage.getChannel(), shortMessage.getData1()));
                break;
        }
    }

//    public void reInstrumentRepeat() {
//        for (int i = 0; i < instrumentArrayList.size(); i++) {
//            for (int j = i + 1; j < instrumentArrayList.size(); j++) {
//                boolean t = instrumentArrayList.get(i).isEqual(instrumentArrayList.get(j));
//                if (t) {
//                    instrumentArrayList.remove(j);
//                }
//            }
//        }
//    }

    public int getTrackNum() {
        return trackNum;
    }

    public ArrayList<MidiTrack> getTrackArrayList() {
        return trackArrayList;
    }

//    public ArrayList<Instrument> getInstrumentArrayList() {
//        return instrumentArrayList;
//    }

    public HashSet<Instrument> getInstrumentHashSet() {
        return instrumentHashSet;
    }

    public void printInfo() {
        System.out.println("====================================================================================");
        System.out.println("ppq:\t" + ppq);
//        Collections.sort(keySignatureArrayList);
        System.out.println();
        for (KeySignature keySignature : keySignatureArrayList) {
            keySignature.printInfo();
        }
        System.out.println();
//        Collections.sort(timeSignatureArrayList);
        for (TimeSignature timeSignature : timeSignatureArrayList) {
            timeSignature.printInfo();
        }
        System.out.println();
        for (Tempo tempo : tempoArrayList) {
            tempo.printInfo();
        }
        System.out.println();
        System.out.println(trackArrayList.size());
        for (int i = 0; i < trackArrayList.size(); i++) {
            System.out.println(trackArrayList.get(i).noteArrayList.size());
            for (int j = 0; j < trackArrayList.get(i).noteArrayList.size(); j++) {
                System.out.printf("times: %f\t", getSecondTime(trackArrayList.get(i).noteArrayList.get(j).tick)/1.25);
                trackArrayList.get(i).noteArrayList.get(j).printInfo();
            }
        }
        System.out.println();
        for (Instrument instrument : instrumentHashSet) {
            instrument.printInfo();
        }

        System.out.println();
        for (int i = 0; i < tickEndOfTrack.size(); i++) {
            System.out.println("tickEndOfTrack:\t" + tickEndOfTrack.get(i));
        }
        System.out.println("Max Tick: " + Collections.max(tickEndOfTrack) + " <=> " + getSecondTime(Collections.max(tickEndOfTrack)) + "s");
//        MidiNote.getSecondTime();

        System.out.println();
        for (int i = 0; i < trackArrayList.size(); i++) {
            trackArrayList.get(i).printInfo();
        }

    }

    public double getSecondTime(long t) {
        double secondTime = 0;
        double[] times = new double[tempoArrayList.size() - 1];
        for (int i = 0; i < tempoArrayList.size() - 1; i++) {
            times[i] = tempoArrayList.get(i).tempo / 1000000.0 / ppq * (tempoArrayList.get(i + 1).tick - tempoArrayList.get(i).tick);
        }
        boolean flag = true;
        for (int i = 0; i < tempoArrayList.size(); i++) {
            if (t < tempoArrayList.get(i).tick) {
                secondTime += tempoArrayList.get(i - 1).tempo / 1000000.0 / ppq * (t - tempoArrayList.get(i - 1).tick);
                flag = false;
                break;
            } else {
                if (i == 0) {
                    continue;
                }
                secondTime += times[i - 1];
            }
        }
        if (flag) {
            secondTime += tempoArrayList.get(tempoArrayList.size() - 1).tempo / 1000000.0 / ppq * (t - tempoArrayList.get(tempoArrayList.size() - 1).tick);
        }
        return secondTime;
    }

//    public ArrayList<Tempo> getTempoArrayList() {
//        return tempoArrayList;
//    }
//
//    public int getPpq() {
//        return ppq;
//    }

    public static void main(String[] args) throws IOException, MidiUnavailableException, InvalidMidiDataException {
//        File mid = new File("D:\\Data\\IntelliJ_IDEA\\MCPrj\\LitematicaFile3\\res\\sakura.mid");
//        File mid = new File("D:\\SystemFile\\Desktop\\music\\jp6_28.mid");
        File mid = new File("D:\\SystemFile\\Desktop\\music\\test.mid");
        MidiInfo midiInfo = new MidiInfo(mid);


    }

}
