# 使用netty实现单聊和群聊的即时通讯系统

## Channel.write()与ChannelHandlerContext.write()区别
ChannelHandlerContext.Channel.write():从 tail 到 head 调用每一个outbound 的 ChannelHandlerContext.write
ChannelHandlerContext.write():从当前的Context, 向head节点传播outbound事件
