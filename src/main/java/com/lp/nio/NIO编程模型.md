nblockingIO(NewIO): 同步非阻塞的编程方式。 

NIO本身是基于事件驱动思想来完成的，其主要想解决的是BIO的大并发问题，NIO基于Reactor，当socket有流可读或可写入socket时，操作系统会相应的通知引用程序进行处理，应用再将流读取到缓冲区或写入操作系统。也就是说，这个时候，已经不是一个连接就要对应一个处理线程了，而是有效的请求，对应一个线程，当连接没有数据时，是没有工作线程来处理的。

**NIO的最重要的地方是当一个连接创建后，不需要对应一个线程，这个连接会被注册到多路复用器上面，所以所有的连接只需要一个线程就可以搞定，当这个线程中的多路复用器进行轮询的时候，发现连接上有请求的话，才开启一个线程进行处理，也就是一个请求一个线程模式。**

在NIO的处理方式中，当一个请求来的话，开启线程进行处理，可能会等待后端应用的资源(JDBC连接等)，其实这个线程就被阻塞了，当并发上来的话，还是会有BIO一样的问题

![](/pic/WX20190524-172147.png)

同步非阻塞，**服务器实现模式为一个请求一个通道**，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有I/O请求时才启动一个线程进行处理。

**NIO方式适用于连接数目多且连接比较短(轻操作)的架构**，比如聊天服务器，并发局限于应用中，编程复杂，JDK1.4开始支持。

- Buffer:ByteBuffer,CharBuffer,ShortBuffer,IntBuffer,LongBuffer,FloatBuffer,DoubleBuffer 。
- Channel:SocketChannel,ServerSocketChannel。
- Selector:Selector,AbstractSelector 。
- SelectionKey:OP_READ,OP_WRITE,OP_CONNECT,OP_ACCEPT 。