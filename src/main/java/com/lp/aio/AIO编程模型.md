AsynchronousIO: 异步非阻塞的编程方式 

与 NIO 不同，当进行读写操作时，只须直接调用 API 的 read 或 write 方法即可。这两种 方法均为异步的，对于读操作而言，当有流可读取时，操作系统会将可读的流传入 read 方 法的缓冲区，并通知应用程序;对于写操作而言，当操作系统将 write 方法传递的流写入完 毕时，操作系统主动通知应用程序。即可以理解为，read/write 方法都是异步的，完成后会 主动调用回调函数。在 JDK1.7 中，这部分内容被称作 NIO.2，主要在 java.nio.channels 包下 增加了下面四个异步通道: 

- AsynchronousSocketChannel
- AsynchronousServerSocketChannel 
- AsynchronousFileChannel
- AsynchronousDatagramChannel 

异步非阻塞，服务器实现模式为一个有效请求一个线程，客户端的 I/O 请求都是由 OS 

先完成了再通知服务器应用去启动线程进行处理。
 AIO 方式使用于连接数目多且连接比较长(重操作)的架构，比如相册服务器，充分调 

用 OS 参与并发操作，编程比较复杂，JDK7 开始支持。 
![](/pic/WX20190527-191712@2x.png)

