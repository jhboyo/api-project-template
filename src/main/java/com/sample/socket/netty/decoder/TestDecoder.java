package com.sample.socket.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDecoder extends ByteToMessageDecoder {

    private int DATA_LENGTH = 2048;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // in
        if (byteBuf.readableBytes() < DATA_LENGTH) {
            return;
        }
        //out
        list.add(byteBuf.readBytes(DATA_LENGTH));
    }
}
