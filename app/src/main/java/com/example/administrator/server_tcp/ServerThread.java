package com.example.administrator.server_tcp;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerThread implements Runnable
{
    public static ArrayList<Socket> socketDelList=new ArrayList<Socket>();
    //定义
    Socket socket=null;
    BufferedReader reader=null;
    public ServerThread(Socket socket) throws IOException
    {
        this.socket=socket;
        //传参
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public void run()
    {
        try
        {
            String content=null;
            while ((content=readFromClient())!=null)
            {
                System.out.println("有收到内容：");
                System.out.println(content);
                for(Socket socket:Server.socketArrayList)
                {
                    try
                    {
                        socket.sendUrgentData(0xFF);
                        //测试客户端连接是否断开
                    }catch(IOException exc)
                    {
                        socketDelList.add(socket);
                        //废弃的socket加入删除列表
                    }
                    PrintStream printStream=new PrintStream(socket.getOutputStream());
                    printStream.println(content);
                }
                //遍历发送
                Server.socketArrayList.removeAll(socketDelList);
                //更新列表，此操作不能在遍历过程中完成
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public String readFromClient()
    {
        try
        {
            return reader.readLine();
        }catch(IOException ex)
        {
            Server.socketArrayList.remove(socket);
            //删除
        }
        return null;
    }
}
