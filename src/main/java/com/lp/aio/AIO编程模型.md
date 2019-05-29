AsynchronousIO: 异步非阻塞的编程方式 

与NIO不同，当进行读写操作时，只须直接调用API的read或write方法即可。这两种方法均为异步的，对于读操作而言，当有流可读取时，操作系统会将可读的流传入read方法的缓冲区，并通知应用程序;对于写操作而言，当操作系统将write方法传递的流写入完毕时，操作系统主动通知应用程序。即可以理解为，read/write方法都是异步的，完成后会主动调用回调函数。在JDK1.7中，这部分内容被称作NIO.2，主要在java.nio.channels包下增加了下面四个异步通道:

- AsynchronousSocketChannel
- AsynchronousServerSocketChannel 
- AsynchronousFileChannel
- AsynchronousDatagramChannel 

异步非阻塞，服务器实现模式为一个有效请求一个线程，客户端的I/O请求都是由OS先完成了再通知服务器应用去启动线程进行处理。
AIO方式使用于连接数目多且连接比较长(重操作)的架构，比如相册服务器，充分调

用 OS 参与并发操作，编程比较复杂，JDK7 开始支持。 
![](/pic/WX20190527-191712@2x.png)

