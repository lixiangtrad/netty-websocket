package com.lp.bio.threadpool;

import com.lp.bio.Handler;
import com.lp.netty.utils.CommonUtils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p/>
 * <li>Description: BIO编程解决1个client需要开启一个线程的问题</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-17 15:24</li>
 */
public class BioThreadPoolServer {
    public static void main(String[] args) {

        int port = CommonUtils.genPort(args);

        ServerSocket server = null;
        //创建一个固定的线程池
        ExecutorService service = Executors.newFixedThreadPool(50);

        try {
            //开启服务
            server = new ServerSocket(port);
            System.out.println("server started!");
            while (true) {
                Socket socket = server.accept();
                //调用handler处理类
                service.execute(new Handler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//关闭流
            CommonUtils.closeServer(server);
        }
    }

}
