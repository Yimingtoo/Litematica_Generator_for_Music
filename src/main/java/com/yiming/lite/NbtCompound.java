package com.yiming.lite;

import java.util.ArrayList;


public class NbtCompound implements NbtElement {

    //    private Map<String, byte[]> entries = new HashMap<>();
    private ArrayList<byte[]> entries;

    NbtCompound() {
        this.entries = new ArrayList<>();
    }

    NbtCompound(ArrayList<byte[]> entries1) {
        this.entries = new ArrayList<>(entries1);
    }

    public NbtCompound copy() {
        return new NbtCompound(this.entries);
    }

    public byte[] getEntry(int i) {
        return this.entries.get(i);
    }


    @Override
    public byte[] getNbtBytes() {
        byte[] bytes = NbtElement.byteMergerAll(this.entries);
        byte[] bytes1 = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, bytes1, 0, bytes.length);
        bytes1[bytes.length] = 0;
        return bytes1;
    }

    public void put(String key, NbtElement nbtElement) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_COMPOUND, key);
        this.entries.add(NbtElement.byteMerger(bytes, nbtElement.getNbtBytes()));
//        this.entries.put(key, value);
    }

    public void put(String key, NbtList nbtList) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_LIST, key);
        this.entries.add(NbtElement.byteMerger(bytes, nbtList.getNbtList()));
    }

    public void putByte(String key, byte value) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_BYTE, key);
        byte[] bytes1 = {value};
        this.entries.add(NbtElement.byteMerger(bytes, bytes1));
    }

    public void putShort(String key, short value) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_SHORT, key);
        byte[] bytes1 = NbtElement.shortToByteArray(value);
        this.entries.add(NbtElement.byteMerger(bytes, bytes1));
    }


    public void putInt(String key, int value) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_INT, key);
        byte[] bytes1 = NbtElement.intToByteArray(value);
        this.entries.add(NbtElement.byteMerger(bytes, bytes1));
    }

    public void putLong(String key, long value) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_LONG, key);
        byte[] bytes1 = NbtElement.longToByteArray(value);
        this.entries.add(NbtElement.byteMerger(bytes, bytes1));
    }

    public void putString(String key, String value) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_STRING, key);

        int str_length = value.getBytes().length;
        byte[] temp = NbtElement.intToByteArray(str_length);
        byte[] bytes_length = new byte[2];
        bytes_length[0] = temp[2];
        bytes_length[1] = temp[3];
        byte[] bytes1 = NbtElement.byteMerger(bytes_length, value.getBytes());
        this.entries.add(NbtElement.byteMerger(bytes, bytes1));
    }

    public void putFloat(String key, float value) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_FLOAT, key);
        byte[] bytes1 = NbtElement.floatToByteArray(value);
        this.entries.add(NbtElement.byteMerger(bytes, bytes1));
    }

    public void putDouble(String key, double value) {
        byte[] bytes = NbtElement.getKeyBytes(NbtElement.TAG_DOUBLE, key);
        byte[] bytes1 = NbtElement.doubleToByteArray(value);
        this.entries.add(NbtElement.byteMerger(bytes, bytes1));
    }

    public void putByteArray(String key, byte[] value) {
        //TODO：intToByteArray是bug
        byte[] bytes = NbtElement.byteMerger(NbtElement.intToByteArray(value.length), value);
        byte[] bytes0 = NbtElement.getKeyBytes(NbtElement.TAG_INT_ARRAY, key);
        this.entries.add(NbtElement.byteMerger(bytes0, bytes));
    }

    public void putIntArray(String key, int[] value) {
        byte[] bytes = {};
        for (int i : value) {
            bytes = NbtElement.byteMerger(bytes, NbtElement.intToByteArray(i));
        }
        //TODO：intToByteArray是bug
        bytes = NbtElement.byteMerger(NbtElement.intToByteArray(value.length), bytes);
        byte[] bytes0 = NbtElement.getKeyBytes(NbtElement.TAG_INT_ARRAY, key);
        this.entries.add(NbtElement.byteMerger(bytes0, bytes));
    }

    public void putLongArray(String key, long[] value) {
        byte[] bytes = {};
        for (long i : value) {
            bytes = NbtElement.byteMerger(bytes, NbtElement.longToByteArray(i));
        }
        //TODO：intToByteArray是bug
        bytes = NbtElement.byteMerger(NbtElement.intToByteArray(value.length), bytes);
        byte[] bytes0 = NbtElement.getKeyBytes(NbtElement.TAG_LONG_ARRAY, key);
        this.entries.add(NbtElement.byteMerger(bytes0, bytes));
    }


}
