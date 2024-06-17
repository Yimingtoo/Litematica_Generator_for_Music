package test3;

class KeySignature extends BaseData {
    public KeySignature(long tick, byte[] bytes) {
        super(tick);
        origin[0] = bytes[0];
        origin[1] = bytes[1];
        originalTone = origin[0];
        m = origin[1];
    }

    //原始数据
    byte[] origin = new byte[2];
    //原调
    int originalTone;
    //大小键
    int m;

    String key;
}