package com.wanna.qunar.work.main.work5.codec;

import com.wanna.qunar.work.main.work5.entity.TransferPackage;

import java.nio.ByteBuffer;

/**
 * 编解码器，负责将TCP的协议包去进行编码/解码
 *
 * @see DefaultCodec
 */
public interface Codec {

    /**
     * 解码，从ByteBuffer当中解析出来传输的协议包(别去进行flip, Codec应该自动去进行flip)
     *
     * @param buffer ByteBuffer
     * @return 解析完成的传输协议包
     */
    TransferPackage decode(ByteBuffer buffer);

    /**
     * 编码，将给定的协议包去进行编码
     *
     * @param transferPackage 要去进行编码的协议包
     * @return 编码完成的ByteBuffer(已经完成flip, Channel可以直接将数据写给对方)
     */
    ByteBuffer encode(TransferPackage transferPackage);
}
