package com.github.apuex.cmcc.cint3.utility;

import java.util.Map;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	public static void launch(final Map<String, String> params) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
								.addLast(new ByteToCInt3MessageDecoder())
								.addLast(new CInt3MessageToByteEncoder())
								.addLast(new ServerHandler(params));
						}
					}).option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_REUSEADDR, true)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			ChannelFuture f = bootstrap.bind(params.get("server-host"), Integer.parseInt(params.get("server-port")))
					.sync();

			// Wait until the server socket is closed,
			// and shut down the server.
			f.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
