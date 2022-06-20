package com.wanna.qunar.work.main.work5.codec;

import com.wanna.qunar.work.main.work5.entity.TransferPackage;

import java.nio.ByteBuffer;

/**
 * 默认的编解码器的实现
 *
 * @see Codec
 */
public class DefaultCodec implements Codec {
    @Override
    public TransferPackage decode(ByteBuffer buffer) {
        // flip
        buffer.flip();
        final int magicNumber = buffer.getInt();
        final int size = buffer.getInt();
        byte[] content = new byte[size];
        buffer.get(content);
        return new TransferPackage(magicNumber, size, content);
    }

    @Override
    public ByteBuffer encode(TransferPackage transferPackage) {
        final int packageSize = transferPackage.getPackageSize();
        ByteBuffer buffer = ByteBuffer.allocate(packageSize + 8);
        buffer.putInt(transferPackage.getMagicNumber());
        buffer.putInt(transferPackage.getPackageSize());
        buffer.put(transferPackage.getContent());

        // flip非常重要，需要自己去调用，不然无法将数据发送给客户端...而且找不到存在的问题
        buffer.flip();
        return buffer;
    }
}
