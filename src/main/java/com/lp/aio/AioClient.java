package com.lp.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;

/**
 * <p/>
 * <li>Description: AIO编程客户端</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-17 15:10</li>
 */
public class AioClient {
    private AsynchronousSocketChannel channel;

    public AioClient(String host, int port){
        init(host, port);
    }

    private void init(String host, int port){
        try {
            // 开启通道
            channel = AsynchronousSocketChannel.open();
            // 发起请求，建立连接。
            channel.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写
     * @param line
     */
    public void write(String line){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(line.getBytes("UTF-8"));
            buffer.flip();
            channel.write(buffer);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读
     */
    public void read(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            // read方法是异步方法，OS实现的。get方法是一个阻塞方法，会等待OS处理结束后再返回。
            channel.read(buffer).get();
            buffer.flip();
            System.out.println("from server : " + new String(buffer.array(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭channel
     */
    public void doDestory(){
        if(null != channel){
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        AioClient client = new AioClient("localhost", 9999);
        try{
            System.out.print("enter message send to server > ");
            Scanner s = new Scanner(System.in);
            String line = s.nextLine();
            client.write(line);
            client.read();
        }finally{
            client.doDestory();
        }
    }
}
