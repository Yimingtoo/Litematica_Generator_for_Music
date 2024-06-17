package test2;

import javax.sound.midi.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class test2 {
    public enum Enum1 {
        ONE_TRACK, SYNC_MULTI_TRACK, ASYNC_MULTI_TRACK
    }

    public static void main(String[] args) throws IOException, MidiUnavailableException, InvalidMidiDataException {
        Enum1 enum1 = null;
        int i = 1;
        enum1 = switch (i) {
            case 0 -> Enum1.ONE_TRACK;
            case 1 -> Enum1.SYNC_MULTI_TRACK;
            case 2 -> Enum1.ASYNC_MULTI_TRACK;
            default -> enum1;
        };
        System.out.println(enum1);
        System.out.println("\033[31;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("Hello,Akina!");

        byte b = (byte) 0xff;
        int a = b;
        System.out.println(String.format("%x",a));

    }
}