package test3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class test {
    public static void main(String[] args) {
        KeySignature keySignature = new KeySignature(100, new byte[3]);
        KeySignature keySignature1 = new KeySignature(23, new byte[3]);
        KeySignature keySignature2 = new KeySignature(176, new byte[3]);
        ArrayList<KeySignature> keySignatures = new ArrayList<>();
        keySignatures.add(keySignature);
        keySignatures.add(keySignature1);
        keySignatures.add(keySignature2);
        Collections.sort(keySignatures);
        for (KeySignature keySignature3 : keySignatures) {
            System.out.println(keySignature3.tick);

        }
    }
}
