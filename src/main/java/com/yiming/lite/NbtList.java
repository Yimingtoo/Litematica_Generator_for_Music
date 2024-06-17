package com.yiming.lite;

import java.util.ArrayList;

public class NbtList {

    byte id;
    ArrayList<byte[]> listByteArray = new ArrayList<>();
//    public NbtList(){
//
//    }

    public NbtList(byte _id) {
        id = _id;
    }

    public NbtList(byte _id, ArrayList<byte[]> arrayList) {
        id = _id;
        this.listByteArray = new ArrayList<>(arrayList);
    }


    public byte getId() {
        return id;
    }

    public void add(NbtElement nbt) {
        listByteArray.add(nbt.getNbtBytes());
    }

    public void add(float value) {
        listByteArray.add(NbtElement.floatToByteArray(value));
    }

    public void add(double value) {
        listByteArray.add(NbtElement.doubleToByteArray(value));
    }

    public void clear() {
        listByteArray.clear();
    }

    public NbtList copy() {
        return new NbtList(this.id, this.listByteArray);
    }

    public byte[] getNbtList() {
        byte[] bytes = {id};
        int length = listByteArray.size();
//        bytes = NbtElement.byteMerger(bytes, NbtElement.intToByteArray(length));
        byte[] content = NbtElement.byteMergerAll(listByteArray);
        return NbtElement.byteMergerAll(bytes, NbtElement.intToByteArray(length), content);
//        return NbtElement.byteMerger(bytes, content);

    }


}
