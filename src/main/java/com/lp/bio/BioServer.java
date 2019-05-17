package com.lp.bio;

import com.lp.utils.CommonUtils;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p/>
 * <li>Description: BIO编程服务端</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-17 15:12</li>
 */
public class BioServer {
    public static void main(String[] args) {

        int port = CommonUtils.genPort(args);

        //socket server
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("server started!");
            while (true) {
                Socket socket = server.accept();
                //每建立一个连接需要开启一个新的线程
                new Thread(new Handler(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeServer(server);
        }

    }


}
