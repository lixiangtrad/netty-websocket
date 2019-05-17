# websocket
网络编程基础及websocket实现

BIO、NIO、AIO简单Demo及使用netty实现简单websocket应用。

└── com
    └── lp
        ├── aio
        │   ├── AioClient.java
        │   ├── AioServer.java
        │   └── AioServerHandler.java
        ├── bio
        │   ├── BioClient.java
        │   ├── BioServer.java
        │   ├── Handler.java
        │   └── threadpool
        │       └── BioThreadPoolServer.java
        ├── netty                   -----积极完善中
        ├── nio
        │   ├── NioClient.java
        │   ├── NioServer.java
        │   └── TestBuffer.java
        ├── utils
        │   └── CommonUtils.java
        └── websocket
            ├── Main.java
            ├── MyWebSocketChannelHandler.java
            ├── MyWebSocketHandler.java
            └── NettyConfig.java
