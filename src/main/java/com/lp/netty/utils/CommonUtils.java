package com.lp.netty.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p/>
 * <li>Description: 统一关闭</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-17 15:19</li>
 */
public class CommonUtils {

    /**
     * 关闭
     *
     * @param socket 套接字
     * @param reader 写入流
     * @param writer 写出流
     */
    public static void close(Socket socket, BufferedReader reader, PrintWriter writer) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (writer != null) {
            writer.close();
        }
    }

    /**
     * 关闭套接字server
     *
     * @param server
     */
    public static void closeServer(ServerSocket server) {
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取端口号
     *
     * @param args
     * @return
     */
    public static int genPort(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return 9999;
            }
        } else {
            return 9999;
        }
    }

}
