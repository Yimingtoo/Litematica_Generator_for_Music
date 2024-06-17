package com.yiming.lite;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Gzip {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";
    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";


    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] compress(String str) throws IOException {
        return compress(str, GZIP_ENCODE_UTF_8);
    }

    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static String uncompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void GZipUtil(String sourceFile, String gzipFile) {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             GZIPOutputStream gzipOut = new GZIPOutputStream(new FileOutputStream(gzipFile))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                gzipOut.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Deprecated
    public static void GZipUtil1(String inputFile, String outputFile) {
        File target = new File(inputFile);
        File target1 = new File(outputFile);
        System.out.println(target1.getAbsolutePath());
        try {
            FileInputStream fileInputStream = new FileInputStream(target.getAbsoluteFile());
            byte[] bytes = new byte[fileInputStream.available()];
            System.out.println("gzip length" + bytes.length);
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
//                    String str = new String(bytes, 0, len);
                System.out.print("len\t" + len);
            }

            GZIPOutputStream gzip;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
            gzip.close();

            FileOutputStream fileOutputStream = new FileOutputStream(target1.getAbsoluteFile());

            if (target1.exists() && target1.isFile()) {
                boolean flag = target1.delete();
            }

//            for (byte i : out.toByteArray()) {
//                System.out.println(i);
//            }

            fileOutputStream.write(out.toByteArray());
            fileOutputStream.close();

            fileInputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
    }

    public static void main(String[] args) throws IOException {
//        GZipUtil("./Res/aaaa3","./Res/aaaa3.litematic");
    }
}
