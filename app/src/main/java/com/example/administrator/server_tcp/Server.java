package com.example.administrator.server_tcp;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
    public static ArrayList<Socket> socketArrayList=new ArrayList<Socket>();
    //定义
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket=new ServerSocket(30000);
        while (true)
        {
            Socket socket=serverSocket.accept();
            System.out.println("有一个新连接！");
            socketArrayList.add(socket);
            //添加
            new Thread(new ServerThread(socket)).start();
            //启动线程服务
        }
    }
}
