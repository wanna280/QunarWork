package com.wanna.qunar.work.main.work5.entity;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Socket传输的协议包，包括魔数，packageSize，以及content等几个部分组成，支持被Codec去进行编解码
 *
 * @see com.wanna.qunar.work.main.work5.codec.Codec
 * @see com.wanna.qunar.work.main.work5.codec.DefaultCodec
 * @author wanna
 */
public class TransferPackage {

    public static final int DEFAULT_MAGIC_NUMBER = 0xcafebabe;

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    private int magicNumber;
    private int packageSize;

    private byte[] content = EMPTY_BYTE_ARRAY;

    public TransferPackage(int magicNumber, int packageSize, byte[] content) {
        this.magicNumber = magicNumber;
        this.packageSize = packageSize;
        this.content = content;
    }

    public TransferPackage(byte[] content) {
        this(DEFAULT_MAGIC_NUMBER, content.length, content);
    }

    public TransferPackage() {
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentString() {
        return getContentString(StandardCharsets.UTF_8);
    }

    public String getContentString(Charset charset) {
        return new String(content, charset);
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(int packageSize) {
        this.packageSize = packageSize;
    }

    @Override
    public String toString() {
        return "TransferPackage{" +
                "magicNumber=" + Integer.toHexString(magicNumber) +
                ", packageSize=" + packageSize +
                '}';
    }
}
