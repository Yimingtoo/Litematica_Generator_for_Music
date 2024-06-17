package com.yiming.lite;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public interface NbtElement {
    byte TAG_END = (byte) 0x00;
    byte TAG_NULL = (byte) 0x00;
    byte TAG_BYTE = (byte) 0x01;
    byte TAG_SHORT = (byte) 0x02;
    byte TAG_INT = (byte) 0x03;
    byte TAG_LONG = (byte) 0x04;
    byte TAG_FLOAT = (byte) 0x05;
    byte TAG_DOUBLE = (byte) 0x06;
    byte TAG_BYTE_ARRAY = (byte) 0x07;
    byte TAG_STRING = (byte) 0x08;
    byte TAG_LIST = (byte) 0x09;
    byte TAG_COMPOUND = (byte) 0x0A;
    byte TAG_INT_ARRAY = (byte) 0x0B;
    byte TAG_LONG_ARRAY = (byte) 0x0C;
    public static byte[] getKeyBytes(byte id, String key) {
        byte[] bytes_head = new byte[3];
        bytes_head[0] = id;
        byte[] temp = intToByteArray(key.length());
        bytes_head[1] = temp[2];
        bytes_head[2] = temp[3];
        return byteMerger(bytes_head, key.getBytes());
    }

    public static byte[] longToByteArray(long i) {
        byte[] result = new byte[8];
        result[0] = (byte) ((i >> 56) & 0xFF);
        result[1] = (byte) ((i >> 48) & 0xFF);
        result[2] = (byte) ((i >> 40) & 0xFF);
        result[3] = (byte) ((i >> 32) & 0xFF);
        result[4] = (byte) ((i >> 24) & 0xFF);
        result[5] = (byte) ((i >> 16) & 0xFF);
        result[6] = (byte) ((i >> 8) & 0xFF);
        result[7] = (byte) (i & 0xFF);
        return result;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static byte[] shortToByteArray(short i) {
        byte[] result = new byte[2];
        result[0] = (byte) ((i >> 8) & 0xFF);
        result[1] = (byte) (i & 0xFF);
        return result;
    }

    public static byte[] floatToByteArray(float floats) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        FloatBuffer floatBuffer = buffer.asFloatBuffer();
        floatBuffer.put(floats);
        return buffer.array();
    }

    public static byte[] doubleToByteArray(double d) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        DoubleBuffer doubleBuffer = buffer.asDoubleBuffer();
        doubleBuffer.put(d);
        return buffer.array();
    }

    //System.arraycopy()方法
    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    public static byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    public static byte[] byteMergerAll(ArrayList<byte[]> byteArray) {
        int length_byte = 0;
        for (int i = 0; i < byteArray.size(); i++) {
            length_byte += byteArray.get(i).length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < byteArray.size(); i++) {
            byte[] b = byteArray.get(i);
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    public byte[] getNbtBytes();

}
