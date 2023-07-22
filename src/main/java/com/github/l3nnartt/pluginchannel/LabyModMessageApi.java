package com.github.l3nnartt.pluginchannel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.nio.charset.StandardCharsets;

public class LabyModMessageApi {

  public static final LabyModMessageApi instance = new LabyModMessageApi();
  public static final String CHANNEL_NAME = "labymod3:main";

  public byte[] getBytes(final String key, final String contents) {
    ByteBuf byteBuf = Unpooled.buffer();
    writeString(byteBuf, key);
    writeString(byteBuf, contents);
    byte[] bytes = new byte[byteBuf.readableBytes()];
    byteBuf.readBytes(bytes);
    return bytes;
  }

  public void writeVarIntToBuffer(ByteBuf byteBuf, int input) {
    while ((input & -128) != 0) {
      byteBuf.writeByte(input & 127 | 128);
      input >>>= 7;
    }
    byteBuf.writeByte(input);
  }

  public void writeString(ByteBuf byteBuf, String string) {
    byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

    if (bytes.length > Short.MAX_VALUE) {
      throw new EncoderException(
          String.format("String (%d encoded) is too big! Max: %d!", string.length(),
              Short.MAX_VALUE));
    } else {
      writeVarIntToBuffer(byteBuf, bytes.length);
      byteBuf.writeBytes(bytes);
    }
  }

  public int readVarIntFromBuffer(ByteBuf byteBuf) {
    int i = 0;
    int j = 0;

    byte b0;
    do {
      b0 = byteBuf.readByte();
      i |= (b0 & 127) << j++ * 7;
      if (j > 5) {
        throw new RuntimeException(String.format("VarInt (%d) is too big!", j));
      }
    } while ((b0 & 128) == 128);

    return i;
  }

  public String readString(ByteBuf buf) {
    int maxLength = Short.MAX_VALUE;
    int i = readVarIntFromBuffer(buf);

    if (i > maxLength * 4) {
      throw new DecoderException(
          String.format("Length of encoded string is longer than maximum allowed (%d > %d)", i,
              (maxLength * 4)));
    } else if (i < 0) {
      throw new DecoderException(
          String.format("The received encoded string buffer length is less than zero (%d)!", i));
    }
    byte[] bytes = new byte[i];
    buf.readBytes(bytes);

    final String s = new String(bytes, StandardCharsets.UTF_8);
    if (s.length() > maxLength) {
      throw new DecoderException(
          String.format("Length of string is longer than maximum allowed (%d > %d)", i, maxLength));
    }

    return s;
  }

  public static LabyModMessageApi getInstance() {
    return instance;
  }
}