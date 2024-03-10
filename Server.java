package SocketServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static int num = 1;// 客户端计数
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServerSocket serverSocket = null;
        Socket client = null;
        ExecutorService pool = Executors.newFixedThreadPool(3);

        while (true) {
            try {
                serverSocket = new ServerSocket(8888);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                 client = serverSocket.accept();
            } catch (Exception e) {
                System.out.println("请求失败");
            }


            //线程池
            ServerThread st = new ServerThread(client,num);
            pool.execute(st);


            try {
                serverSocket.close();
            } catch (Exception e) {
                System.out.println("关闭失败！");
            }

            num++;

        }
    }


}