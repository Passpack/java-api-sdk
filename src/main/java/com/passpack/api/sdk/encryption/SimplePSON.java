package com.passpack.api.sdk.encryption;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SimplePSON {

    // PSON Type Constants
    private static final int TYPE_NULL = 0xF0;
    private static final int TYPE_STRING = 0xFC;
    private static final int TYPE_ESTRING = 0xF5;
    private static final int TYPE_OBJECT = 0xF6;


    // Encoding method
    public static byte[] encode(String input) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteOut);

        encodeString(input, out);
        return byteOut.toByteArray();
    }

    private static void encodeString(String input, DataOutputStream out) throws IOException {
        // The passpack system always creates an object of {value:"something"}  which translates to an object of size 1.
        // keys is size 1

        out.writeByte(TYPE_OBJECT);
        // Write the number of keys
        writeVarint32(1, out);
        // The plain string  for "value"
        // type first, then the vstring
        out.writeByte(TYPE_STRING);
        writeVString("value", out);

        // write the input string
        if (input == null) {
            out.writeByte(TYPE_NULL);
        } else if (input.length() == 0) {
            out.writeByte(TYPE_ESTRING);
        } else {
            out.writeByte(TYPE_STRING);
            writeVString(input, out);
        }
    }

    private static void writeVString(String key, DataOutputStream out) throws IOException {
        writeVarint32(key.length(), out);
        out.writeBytes(key);
    }
    private static String readVString(DataInputStream in) throws IOException {
        int length = readVarint32(in);
        byte[] bytes = new byte[length];
        in.readFully(bytes);
        return new String(bytes);
    }

    private static void writeVarint32(int i, DataOutputStream out) throws IOException {
        /**
         * This is the javascript version
         * function writeVarint32(buffer, value, offset = 0) {
         *   while (value > 127) {
         *     buffer.writeByte((value & 127) | 128, offset++);
         *     value >>>= 7;
         *   }
         *   buffer.writeByte(value, offset);
         *   return offset + 1;
         * }
         */
        while (i > 127) {
            out.writeByte((i & 127) | 128);
            i >>>= 7;
        }
        out.writeByte(i);
    }
    private static int readVarint32(DataInputStream in) throws IOException {
        int result = 0;
        int shift = 0;
        int b;
        do {
            b = in.read();
            if (b == -1) {
                throw new IOException("Unexpected end of stream");
            }
            result |= (b & 0x7f) << shift;
            shift += 7;
        } while ((b & 0x80) != 0);
        return result;
    }


    // Decoding method
    public static String decode(byte[] data) throws IOException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
        DataInputStream in = new DataInputStream(byteIn);

        return decodeString(in);
    }

    private static String decodeString(DataInputStream in) throws IOException {
String decodedString = null;
        // pull off the TYPE_OBJECT
        byte typeObjectByte = in.readByte();
        // Read in the number of keys
        int keyCount = readVarint32(in);
        // Read in the key
        byte typeStringForKey = in.readByte();
        String key = readVString(in);

        // Now get the content
        int type = (int)in.readByte();
        if (type < 0) {
            type = 256 + type;
        }
        switch (type) {
            case TYPE_NULL:
                decodedString =  null;
            case TYPE_ESTRING:
                decodedString =  "";
            case TYPE_STRING:
                decodedString =  readVString(in);
        }

        return decodedString;


    }
}