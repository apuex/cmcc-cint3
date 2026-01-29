package com.github.apuex.cmcc.cint3.utility;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;

public class DynAccess {
	public static void launch(final Map<String, String> params) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();

		try {
			bootstrap.group(workerGroup).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
								.addLast(new ByteToCInt3MessageDecoder())
								.addLast(new CInt3MessageToByteEncoder())
								.addLast(new DynAccessHandler(params));
						}
					});

			ChannelFuture f = bootstrap.connect(params.get("server-host"), Integer.parseInt(params.get("server-port")))
					.sync();
			
			f.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
