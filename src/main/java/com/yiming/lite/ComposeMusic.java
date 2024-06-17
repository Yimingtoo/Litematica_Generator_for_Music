package com.yiming.lite;

//import net.minecraft.item.ItemStack;
//import net.minecraft.server.network.ServerPlayerEntity;

import clojure.lang.Compiler;
import com.yiming.Soutput;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import static com.mojang.text2speech.Narrator.LOGGER;

public class ComposeMusic {

    class NbtArrayList {
        public ArrayList<NbtCompound> noteNbt;
        public ArrayList<NbtCompound> toneNbt;

        NbtArrayList() {
            noteNbt = new ArrayList<>();
            toneNbt = new ArrayList<>();
        }
    }

    public final String CHEST_BOX_ID = "minecraft:chest";
    public final String SHULKER_BOX_ID = "minecraft:shulker_box";

    public String Name = "untitled";
    public int Speed = -1;
    public float SpeedF = -1;
    public int Tone;

    public float LSpeedLeft;
    public float RSpeedLeft;
    public int fileRow;
    public int cycleCount;

    public Map<Integer, NbtCompound> NoteMap = new HashMap<Integer, NbtCompound>();
    public Map<String, Integer> TNoteMap = new HashMap<String, Integer>();
    public Map<Integer, NbtCompound> ToneMap = new HashMap<Integer, NbtCompound>();

    public ArrayList<NbtArrayList> LeftNbt = new ArrayList<>();
    public ArrayList<NbtArrayList> RightNbt = new ArrayList<>();

    public void showErrorDialog(String text, String cell) {
        Dialog.errorDialog(text + "\n 错误信息 @第" + (fileRow + 1) + "行 -> 单元格: " + cell);
    }

    public void showWarningDialog(String text, String cell) {
        Dialog.warningDialog(text + "\n 警告信息 @第" + (fileRow + 1) + "行 -> 单元格: " + cell);

    }

    public ComposeMusic() {
        // 初始化模板
        this.initHashMap();
        // 初始化剩余速度
        LSpeedLeft = 0;
        RSpeedLeft = 0;
        cycleCount = 0;
        for (int i = 0; i < 4; i++) {
            LeftNbt.add(new NbtArrayList());
            RightNbt.add(new NbtArrayList());
        }
    }

    public ComposeMusic(ArrayList<ArrayList<Integer>> LeftNotes, ArrayList<ArrayList<Integer>> RightNotes,String fileName) {
        this();
        this.Tone = 0;
        this.Speed = 1;
        this.SpeedF = 1;
        writeNote(LeftNotes,RightNotes);
        System.out.printf("");
    }


    public ComposeMusic(String fileName) throws IOException {
        this();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(fileName));
        //获取工作簿下第一个sheet
        Sheet sheet = xssfWorkbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.forEachRemaining(row -> {
            fileRow = row.getRowNum();
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell cell = cellIterator.next();
            cell.setCellType(CellType.STRING);
            switch (cell.toString()) {
                case "Name":
                    cell = cellIterator.next();
                    cell.setCellType(CellType.STRING);
                    this.Name = cell.toString();
                    System.out.println("name = " + this.Name);
                    break;
                case "Speed":
                    cell = cellIterator.next();
                    cell.setCellType(CellType.STRING);
                    this.SpeedF = Float.parseFloat(cell.toString());
                    this.Speed = (int) SpeedF;

                    if (SpeedF < 1) {
//                        Dialog.errorDialog("Speed不能小于1！！！");
                        showErrorDialog("Speed不能小于1！！！", cell.toString());

                    }

                    System.out.println("Speed = " + SpeedF);
                    break;
                case "1=":
                    cell = cellIterator.next();
                    cell.setCellType(CellType.STRING);
                    String tone = cell.toString();
                    this.Tone = 0;
                    switch (tone) {
                        case "Ab":
                            this.Tone = -4;
                            break;
                        case "A":
                            this.Tone = -3;
                            break;
                        case "Bb":
                            this.Tone = -2;
                            break;
                        case "B":
                        case "Cb":
                            this.Tone = -1;
                            break;
                        case "C":
                            this.Tone = 0;
                            break;
                        case "C#":
                        case "Db":
                            this.Tone = 1;
                            break;
                        case "D":
                            this.Tone = 2;
                            break;
                        case "Eb":
                            this.Tone = 3;
                            break;
                        case "E":
                            this.Tone = 4;
                            break;
                        case "F":
                            this.Tone = 5;
                            break;
                        case "F#":
                        case "Gb":
                            this.Tone = 6;
                            break;
                        case "G":
                            this.Tone = 7;
                            break;
                        default:
                            showErrorDialog("请设置正确的原调!!!", cell.toString());

                            break;
                    }
                    System.out.println("Tone = " + tone + this.Tone);
                    break;
                case "L":
                    System.out.println("Left" + this.LeftNbt.get(0).noteNbt.size() + "\tRight" + this.RightNbt.get(0).noteNbt.size());
                    if (this.LeftNbt.get(0).noteNbt.size() != this.RightNbt.get(0).noteNbt.size()) {
//                        showWarningDialog("总体的音符和曲调的数量不相同，是否继续？", "null");
                    }
                case "R":
                    // 填写乐谱
                    readNote(row);
                    break;
                default:

            }
            //TODO: DEBUG
            String str = row.getRowNum() + "\t";
            Iterator<Cell> cellIterator1 = row.cellIterator();
            Cell cell1;
            while (cellIterator1.hasNext()) {
                cell1 = cellIterator1.next();
                cell1.setCellType(CellType.STRING);
                str = str + "|\t" + cell1.toString();
            }
            System.out.println(str);

        });
    }

    private void initHashMap() {
        TNoteMap.put("0", 16);
        TNoteMap.put("1", 1);
        TNoteMap.put("1#", 2);
        TNoteMap.put("2b", 2);
        TNoteMap.put("2", 3);
        TNoteMap.put("2#", 4);
        TNoteMap.put("3b", 4);
        TNoteMap.put("3", 5);
        TNoteMap.put("4", 6);
        TNoteMap.put("4#", 7);
        TNoteMap.put("5b", 7);
        TNoteMap.put("5", 8);
        TNoteMap.put("5#", 9);
        TNoteMap.put("6b", 9);
        TNoteMap.put("6", 10);
        TNoteMap.put("6#", 11);
        TNoteMap.put("7b", 11);
        TNoteMap.put("7", 12);

        NoteMap.put(0, this.generateBaseNote("minecraft:music_disc_pigstep", "--"));
        NoteMap.put(1, this.generateBaseNote("minecraft:music_disc_13", "C"));
        NoteMap.put(2, this.generateBaseNote("minecraft:music_disc_cat", "C#"));
        NoteMap.put(3, this.generateBaseNote("minecraft:music_disc_blocks", "D"));
        NoteMap.put(4, this.generateBaseNote("minecraft:music_disc_chirp", "D#"));
        NoteMap.put(5, this.generateBaseNote("minecraft:music_disc_far", "E"));
        NoteMap.put(6, this.generateBaseNote("minecraft:music_disc_mall", "F"));
        NoteMap.put(7, this.generateBaseNote("minecraft:music_disc_mellohi", "F#"));
        NoteMap.put(8, this.generateBaseNote("minecraft:music_disc_stal", "G"));
        NoteMap.put(9, this.generateBaseNote("minecraft:music_disc_strad", "G#"));
        NoteMap.put(10, this.generateBaseNote("minecraft:music_disc_ward", "A"));
        NoteMap.put(11, this.generateBaseNote("minecraft:music_disc_11", "A#"));
        NoteMap.put(12, this.generateBaseNote("minecraft:music_disc_wait", "B"));

        ToneMap.put(16, this.generateBaseNote("minecraft:music_disc_pigstep", "--"));
        ToneMap.put(-3, this.generateBaseNote("minecraft:music_disc_13", "L3"));
        ToneMap.put(-2, this.generateBaseNote("minecraft:music_disc_cat", "L2"));
        ToneMap.put(-1, this.generateBaseNote("minecraft:music_disc_blocks", "L1"));
        ToneMap.put(0, this.generateBaseNote("minecraft:music_disc_chirp", "0"));
        ToneMap.put(1, this.generateBaseNote("minecraft:music_disc_far", "H1"));
        ToneMap.put(2, this.generateBaseNote("minecraft:music_disc_mall", "H2"));
        ToneMap.put(3, this.generateBaseNote("minecraft:music_disc_mellohi", "H3"));


    }

    public NbtCompound generateBaseNote(String id, String text) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("Name", "{\"text\":\"" + text + "\"}");

        NbtCompound nbtCompound1 = new NbtCompound();
        nbtCompound1.putInt("RepairCost", 0);
        nbtCompound1.put("display", nbtCompound);

        NbtCompound nbtCompound2 = new NbtCompound();
        nbtCompound2.putByte("Count", (byte) 1);
        nbtCompound2.putString("id", id);
        nbtCompound2.put("tag", nbtCompound1);

        return nbtCompound2;
    }

    public void readNote(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        Cell cell = cellIterator.next();
        cell.setCellType(CellType.STRING);
        // 判断左右手
        char hand = cell.toString().charAt(0);
        if (!(hand == 'R' || hand == 'L')) {
            System.out.format("\33[31;43;4mError: There is wrong hand!!!\33[0m");
            return;
        }
        while (cellIterator.hasNext()) {
            cell = cellIterator.next();
            cell.setCellType(CellType.STRING);
            String[] split_strs = cell.getRichStringCellValue().toString().split("\\|");

            //String[] split_strs = cell.getRichStringCellValue().toString().split(" ");
            writeNote(hand, split_strs);
//            if (split_strs.length > 1) {
//                // 音符组 或者 含有多余空格的元素
//            } else if (!cell.toString().isEmpty()) {
//                writeNote(hand, cell.toString());
//            }

        }
    }

    public void writeNote(char hand, String[] _str) {
        int count = 0;
        if (hand == 'L') {
            LSpeedLeft += SpeedF - Speed;
        } else if (hand == 'R') {
            RSpeedLeft += SpeedF - Speed;
        } else {
            System.out.format("\33[31;43;4mError: @ writeNote(char hand, String[] str) \33[0m");
            return;
        }

        for (String str_i : _str) {
            if (str_i.isEmpty()) {
                continue;
            }
            count++;
            if (count > Speed) {
                showErrorDialog("每个单元格内的音符数量不能超过Speed!!! Speed=" + SpeedF, str_i);
                System.out.format("\33[31;43;4mError: out of Speed!\33[0m");
                return;
            }

            String[] strs = str_i.split(" ");


            for (int i = 0; i < 4; i++) {
                if (i < strs.length) {
                    if ((strs[i]).isEmpty()) {
                        i--;
                        continue;
                    }
                    this.writeOneNote(hand, strs[i], i);
                } else {
                    this.writeOneNote(hand, "0", i);
                }

            }
        }
        if (count < Speed) {
            for (int i = 0; i < Speed - count; i++) {
                // 填充空白音符
                for (int j = 0; j < 4; j++) {
                    this.writeOneNote(hand, "0", j);
                }
            }
        }
        // 填补多余的字符
        // addExtraNote(hand);
        if (hand == 'L') {
            if (this.LSpeedLeft > 1.0F) {
                for (int j = 0; j < 4; j++) {
                    this.writeOneNote(hand, "0", j);
                }
                --this.LSpeedLeft;
            }
        } else if (hand == 'R' && this.RSpeedLeft > 1.0F) {
            for (int j = 0; j < 4; j++) {
                this.writeOneNote(hand, "0", j);
            }
            --this.RSpeedLeft;
        }
    }

    public void writeNote(ArrayList<ArrayList<Integer>> leftNotes, ArrayList<ArrayList<Integer>> rightNotes) {
        for (int i = 0; i < leftNotes.size(); i++) {
            for (int j = 0; j < 4; j++) {
                this.writeOneNote('L', leftNotes.get(i).get(j), j);
                this.writeOneNote('R', rightNotes.get(i).get(j), j);
            }
        }
    }

    public void writeOneNote(char hand, String str, int index) {
        String str2 = str;
        if (str.charAt(0) == '#' || str.charAt(0) == 'b') {
            // #号音符
            str2 = str.substring(1);
        }
        int note = 0;
        try {
            note = Integer.parseInt(str2);
        } catch (Exception e) {
            showErrorDialog("请确保单元格音符格式正确!!!", str);
        }
        int tone = note / 10;
        if (note != 0) {
            note = Math.abs(note) % 10;

            String strNote;
            if (str.charAt(0) == '#') {
                strNote = String.valueOf(note) + "#";
            } else if (str.charAt(0) == 'b') {
                strNote = String.valueOf(note) + "b";
            } else {
                strNote = String.valueOf(note);
            }
            if (!TNoteMap.containsKey(strNote)) {
                showErrorDialog("无法找到对应的音符，请检查单元格!!!", str);
            }
            note = TNoteMap.get(strNote);


            note = note + this.Tone;
            if (note < 1) {
                note = note + 12;
                tone--;
            } else if (note > 12) {
                note = note - 12;
                tone++;
            }
            if (tone > 3 || tone < -3) {
                showWarningDialog("超出可使用的音域范围，是否继续？", str);
                System.out.format("\33[31;43;4mError: 音符文件有问题，音调超出范围！！！ tone = " + tone + "\t str:" + str + "\33[0m");
                note = 0;
                tone = 16;
//                return;
            }
        } else {
            tone = 16;
        }

        NbtCompound nbtCompound = NoteMap.get(note).copy();
        NbtCompound nbtCompound1 = ToneMap.get(tone).copy();


        if (hand == 'L') {
            this.LeftNbt.get(index).noteNbt.add(nbtCompound.copy());
            this.LeftNbt.get(index).toneNbt.add(nbtCompound1.copy());
        } else if (hand == 'R') {
            this.RightNbt.get(index).noteNbt.add(nbtCompound.copy());
            this.RightNbt.get(index).toneNbt.add(nbtCompound1.copy());
        }
    }

    public void writeOneNote(char hand, int key, int index) {
        int note;
        int tone;
//        System.out.println(key);
        if (key < 0) {
            note = 0;
            tone = 16;
        } else {
            note = key % 12;
            tone = key / 12 - 5;
            if (tone > 3 || tone < -3) {
//            showWarningDialog("超出可使用的音域范围，是否继续？","null");
                System.out.format("\33[31;43;4mError: 音符文件有问题，音调超出范围！！！ tone = " + tone + "\33[0m");
                note = 0;
                tone = 16;
            }
        }

        NbtCompound nbtCompound = NoteMap.get(note).copy();
        NbtCompound nbtCompound1 = ToneMap.get(tone).copy();

        if (hand == 'L') {
            this.LeftNbt.get(index).noteNbt.add(nbtCompound.copy());
            this.LeftNbt.get(index).toneNbt.add(nbtCompound1.copy());
        } else if (hand == 'R') {
            this.RightNbt.get(index).noteNbt.add(nbtCompound.copy());
            this.RightNbt.get(index).toneNbt.add(nbtCompound1.copy());
        }
    }

    public NbtCompound containerNbt(String id, NbtList nbt, String display) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("id", id);
        nbtCompound.put("Items", nbt);

        NbtCompound nbtCompound1 = new NbtCompound();
        nbtCompound1.put("BlockEntityTag", nbtCompound);
        if (!display.isEmpty()) {
            NbtCompound tempNbt = new NbtCompound();
            tempNbt.putString("Name", "{\"text\":\"" + display + "\"}");
            nbtCompound1.put("display", tempNbt);
        }

        NbtCompound nbtCompound2 = new NbtCompound();
        if (id.equals(SHULKER_BOX_ID)) {
            nbtCompound2.putString("id", "minecraft:white_shulker_box");
        } else {
            nbtCompound2.putString("id", id);
        }
        nbtCompound2.putByte("Count", (byte) 1);
        nbtCompound2.put("tag", nbtCompound1);

        return nbtCompound2;
    }

    public ArrayList<NbtCompound> packNotes(ArrayList<NbtCompound> nbtCompoundArrayList) {
        int nbtCount = 0;//用于统计潜影盒的数量是否超过27*4个

        NbtList shulkerInList = new NbtList(NbtElement.TAG_COMPOUND);
        ArrayList<NbtCompound> outputNbtList = new ArrayList<NbtCompound>();

        for (int i = 0; i < nbtCompoundArrayList.size(); i++) {
            NbtCompound nbtCompound = nbtCompoundArrayList.get(i).copy();
            nbtCompound.putByte("Slot", (byte) nbtCount);

            shulkerInList.add(nbtCompound.copy());

            if (i % 27 == 0 && i != 0) {
                // 装盒
                NbtCompound shulkerNbt = containerNbt(SHULKER_BOX_ID, shulkerInList, "").copy();
                // 添加一个潜影盒
                outputNbtList.add(shulkerNbt.copy());
                //重置 shulkerInList
                shulkerInList.clear();
                nbtCount = 0;
            } else {
                nbtCount++;
            }
        }
        // 处理剩余不足27个的部分
        if (nbtCount != 0) {
            // 装盒
            NbtCompound shulkerNbt = containerNbt(SHULKER_BOX_ID, shulkerInList, "").copy();
            // 添加一个潜影盒
            outputNbtList.add(shulkerNbt.copy());
        }

        return outputNbtList;

    }

    public ArrayList<NbtCompound> getShulkerBoxes(char hand, int index, int noteOrTone) {
        if (hand == 'L') {
            if (noteOrTone == 0) {
                return packNotes(LeftNbt.get(index).noteNbt);
            } else {
                return packNotes(LeftNbt.get(index).toneNbt);

            }
        } else {
            if (noteOrTone == 0) {
                return packNotes(RightNbt.get(index).noteNbt);
            } else {
                return packNotes(RightNbt.get(index).toneNbt);

            }
        }
    }

    @Deprecated
    public ArrayList<NbtCompound> packContainer(ArrayList<NbtCompound> nbtCompoundArrayList, String name) {
        int shulkerCount = 0;
        int chestCount = 0;
        NbtList shulkerNbtList = new NbtList(NbtElement.TAG_COMPOUND);
        ArrayList<NbtCompound> outputArrayList = new ArrayList<NbtCompound>();
        for (int i = 0; i < nbtCompoundArrayList.size(); i++) {
            NbtCompound nbtCompound = nbtCompoundArrayList.get(i).copy();
            nbtCompound.putByte("Slot", (byte) shulkerCount);
            shulkerNbtList.add(nbtCompound.copy());


            if ((i + 1) % 27 == 0) {
                NbtCompound outputNbt = containerNbt(CHEST_BOX_ID, shulkerNbtList, name + ": " + chestCount).copy();
                outputArrayList.add(outputNbt.copy());

                chestCount++;
                shulkerNbtList.clear();
                shulkerCount = 0;
            } else {
                shulkerCount++;
            }
        }
        // 剩余不到27的部分
        if (shulkerCount != 0) {
            NbtCompound outputNbt = containerNbt(CHEST_BOX_ID, shulkerNbtList, name + ": " + chestCount).copy();
            outputArrayList.add(outputNbt.copy());
        }
        return outputArrayList;

    }

    @Deprecated
    public ArrayList<NbtCompound> giveItem(ArrayList<NbtCompound> list, String name) {
        ArrayList<NbtCompound> leftNoteList0 = packContainer(list, name);
        ArrayList<NbtCompound> leftNoteList00 = packContainer(leftNoteList0, "总体" + name);
        for (int j = 0; j < leftNoteList00.size(); j++) {

        }
        return leftNoteList00;
    }


}
