package com.lp.bio;

import com.lp.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * <p/>
 * <li>Description: BIO编程模型中处理器</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-17 15:28</li>
 */
public class Handler implements Runnable{
    Socket socket = null;
    public Handler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try{

            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));
            writer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            String readMessage = null;
            while(true){
                System.out.println("server reading... ");
                if((readMessage = reader.readLine()) == null){
                    break;
                }
                System.out.println(readMessage);
                //服务端回写消息
                writer.println("server recive : " + readMessage);
                writer.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            CommonUtils.close(socket,reader,writer);
        }
    }

}
