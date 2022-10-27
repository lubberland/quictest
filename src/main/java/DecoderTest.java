import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;


/**
 * Created by ralin on 16/1/17.
 */
public class DecoderTest extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(DecoderTest.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf msg = in.readRetainedSlice(in.readableBytes());
        ByteBuf msg4print = msg.copy();
        byte[] decoded = new byte[msg4print.readableBytes()];
        msg4print.readBytes(decoded);
        logger.info("DecoderTest length:{}, bytes{}", decoded.length, Arrays.toString(decoded));
        System.out.printf("DecoderTest length:%d, bytes:%s", decoded.length, Arrays.toString(decoded));
        out.add(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("DecoderTest message from bytes failed! remote is {}, caused by: {}", ctx.channel().remoteAddress(), cause.getMessage(), cause);
        ctx.close();
    }
}
