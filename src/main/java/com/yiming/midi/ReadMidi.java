package com.yiming.midi;

import javax.sound.midi.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class ReadMidi {

    public String filePath = "";
    public Track[] tracks;

    public void test() {
        //read midi file
        try {
//            File mid = new File("D:\\SystemFile\\Desktop\\music\\jp6_28.mid");
//            File mid = new File("D:\\Data\\IntelliJ_IDEA\\MCPrj\\LitematicaFile3\\res\\sakura.mid");
            File mid = new File("D:\\SystemFile\\Desktop\\music\\架子鼓.mid");
            FileInputStream fis = new FileInputStream(mid);
            Sequencer sequencer = MidiSystem.getSequencer();
            Sequence sequence = MidiSystem.getSequence(fis);


            this.tracks = sequence.getTracks();
            Track track = this.tracks[0];

//            modifyMidiEvent(tracks[0], 0xc0, 0x49, 0x00);
//            modifyMidiEvent(tracks[1], 0xc0, 0x50, 0x00);

//            MidiEvent event1 = new MidiEvent(new ShortMessage(0xc0, 1, 0x49, 0), 292800/4);
//            this.tracks[1].add(event1);
            modifyMidiEvent(this.tracks[0], 0x0, 0x9);
//            modifyMidiEvent(this.tracks[1], 0x0, 0x9);
            modifyMidiEvent(this.tracks[0],0xc0,0,0);
//            modifyMidiEvent(this.tracks[1],0xc0,8,0);
            System.out.println(mid.length());
            fis.skip(fis.available() - mid.length());
            System.out.println(fis.available());
            sequencer.open();
            sequencer.setSequence(sequence);
//            sequencer.setTrackSolo(0,true);
//            sequencer.setTrackSolo(1,true);
            sequencer.setTempoInBPM(150);
            sequencer.start();
            getTrackInfo(this.tracks);
            //获取midi文件播放的总时长
            System.out.println("总时长: " + sequencer.getMicrosecondLength() / 1000000.0 + "秒");

//            sequencer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void modifyMidiEvent(Track track, int replaced_channel, int new_channel) throws InvalidMidiDataException {
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            if (event.getMessage() instanceof ShortMessage shortMessage) {
                if (replaced_channel == shortMessage.getChannel()) {
                    if (!track.remove(event)) {
                        System.out.println("Fail to remove event !!!");
                        System.out.println("Fail to remove event !!!");
                        return;
                    }
                    ShortMessage shortMessage1 = (ShortMessage) event.getMessage();
                    ShortMessage shortMessage2 = new ShortMessage(shortMessage1.getCommand(),new_channel,shortMessage1.getData1(),shortMessage1.getData2());
                    MidiEvent event1 = new MidiEvent(shortMessage2,event.getTick());

                    track.add(event1);
                }
            }
        }
    }

    public void modifyMidiEvent(Track track, int status, int changeData1, int changeData2) throws InvalidMidiDataException {
        long tick1 = 0;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            if (event.getMessage() instanceof ShortMessage shortMessage) {
                if (status == shortMessage.getCommand()) {
                    if (!track.remove(event)) {
                        System.out.println("Fail to remove event !!!");
                        System.out.println("Fail to remove event !!!");
                    } else {
                        tick1 = event.getTick();
                    }
                }

            }

        }
        MidiEvent event1 = new MidiEvent(new ShortMessage(status, changeData1, changeData2), tick1);
        track.add(event1);

    }

    public void getTrackInfo(Track[] tracks) {
        for (Track track : tracks) {
            //read event
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                System.out.printf("tick:0xx%x " + event.getTick() + "  " + event.getMessage() + "\n", event.getTick());
                if (event.getMessage() instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) event.getMessage();
                    byte[] bytes = metaMessage.getData();
                    String string = new String(bytes.toString());
                    System.out.printf("MetaMessage.Type:0x%x %d\n", metaMessage.getType(), metaMessage.getType());

                    System.out.printf("MetaMessage.Data:");
                    for (byte bytes1 : bytes) {
                        System.out.printf("%2x ", bytes1);
                    }
                    System.out.println("");
//                        metaMessage.getType();

                }
                if (event.getMessage() instanceof ShortMessage) {
                    ShortMessage shortMessage = (ShortMessage) event.getMessage();
                    System.out.println("Channel:" + shortMessage.getChannel());
                    System.out.printf("Command:0x%x %d\n", shortMessage.getCommand(), shortMessage.getCommand());
                    System.out.printf("Data1:0x%x %d\n", shortMessage.getData1(), shortMessage.getData1());
                    System.out.printf("Data2:0x%x %d\n", shortMessage.getData2(), shortMessage.getData2());

                }
            }
        }
    }

    public void playOneTrack() {
        try {
            String fileName = "res\\sakura.mid";
            File mid = new File("res\\sakura.mid");
            FileInputStream fis = new FileInputStream(mid);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(fis);
            sequencer.open();
            sequencer.setTickPosition(0x28678);
            sequencer.setTrackSolo(0, true);
            sequencer.setTrackSolo(1, true);


//            sequencer.setTempoFactor((float) 1.25);
            sequencer.setTempoInBPM(150);
            System.out.println("sequencer.getLoopStartPoint()：" + sequencer.getTickPosition());
            System.out.println("sequencer.getLoopStartPoint()：" + sequencer.getLoopStartPoint());
            System.out.println("sequencer.getLoopEndPoint()：" + sequencer.getLoopEndPoint());
            sequencer.start();


        } catch (Exception e) {
        }

    }

    public void readSequence() {
        try {
//            String fileName = "D:\\Data\\IntelliJ_IDEA\\MCPrj\\LitematicaFile3\\res\\sakura.mid";
            String fileName = "D:\\Data\\IntelliJ_IDEA\\MCPrj\\LitematicaFile3\\res\\music1.mid";
            File mid = new File(fileName);
            FileInputStream fis = new FileInputStream(mid);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(fis);
            System.out.println("Tempo:" + sequencer.getTempoInMPQ());
            //read sequence

            FileInputStream fis1 = new FileInputStream(mid);
            Sequence sequence = MidiSystem.getSequence(fis1);


            //read track
            Track[] tracks = sequence.getTracks();


            System.out.println("Length:" + tracks.length);
            int count = -1;
            for (Track track : tracks) {
                count++;
                //read event
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent event = track.get(i);
                    System.out.printf("tick:0xx%x " + event.getTick() + "  " + event.getMessage() + "\n", event.getTick());
                    if (event.getMessage() instanceof MetaMessage) {
                        MetaMessage metaMessage = (MetaMessage) event.getMessage();
                        byte[] bytes = metaMessage.getData();
                        String string = new String(bytes.toString());
                        System.out.printf("MetaMessage.Type:0x%x %d\n", metaMessage.getType(), metaMessage.getType());

                        System.out.printf("MetaMessage.Data:");
                        for (byte bytes1 : bytes) {
                            System.out.printf("%2x ", bytes1);
                        }
                        System.out.println("");
//                        metaMessage.getType();

                    }
                    if (event.getMessage() instanceof ShortMessage) {
                        ShortMessage shortMessage = (ShortMessage) event.getMessage();
                        System.out.println("Channel:" + shortMessage.getChannel());
                        System.out.printf("status:0x%x\n" , shortMessage.getStatus());
                        System.out.printf("Command:0x%x %d" + " track:" + count + "\n", shortMessage.getCommand(), shortMessage.getCommand());
                        System.out.printf("Data1:0x%x %d\n", shortMessage.getData1(), shortMessage.getData1());
                        System.out.printf("Data2:0x%x %d\n", shortMessage.getData2(), shortMessage.getData2());

                    }
                }
            }
        } catch (Exception e) {

        }


    }

    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        ReadMidi readMidi = new ReadMidi();
        readMidi.test();
//        readMidi.readSequence();
//        readMidi.playOneTrack();
//        MidiInfo midiInfo = new MidiInfo("D:\\Data\\IntelliJ_IDEA\\MCPrj\\LitematicaFile3\\res\\sakura.mid");
//        MidiInfo midiInfo = new MidiInfo("D:\\Data\\IntelliJ_IDEA\\MCPrj\\LitematicaFile3\\res\\music1.mid");
//        midiInfo.getInstruments();
    }
}
