package SocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket server = null;
        Scanner sc = new Scanner(System.in);
        String Jugement = "1";
        try {
            String inputString;
            server = new Socket("127.0.0.1", 8888);// 向本机4444端口发出客户请求

            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            // 由系统标准输入设备构造BufferedReadder对象
            PrintWriter os = new PrintWriter(server.getOutputStream());
            // 由Socket对象得到输出流，并构造PrintWriter对象
            BufferedReader is = new BufferedReader(new InputStreamReader(server.getInputStream()));
            // 由Socket对象得到输入流，并构造BufferedReader对象
            System.out.println("------------欢迎进入校园卡管理系统  请输入卡号---------------------");
            inputString = sin.readLine();// 从标准输入读入一字符串（输入卡号）
            os.println(inputString);// 向Server端输出该字符串
            os.flush();// 刷新输出流，使Server端马上收到该字符串
            System.out.println(is.readLine());//收到客户端发来的id

            while (Jugement.equals("1") || Jugement.equals("2")) {
                System.out.println("------请选择菜单---1.消费----2.充值----其他.退出----------");
                Jugement = sin.readLine();

                if (Jugement.equals("1") || Jugement.equals("2")) {


                    os.println(Jugement);// 向Server端输出选项
                    os.flush();// 刷新输出流，使Server端马上收到该字符串

                    //1或2 进行消费或者充值

                    System.out.println(is.readLine());//读取”请输入消费金额“
                    os.println(sin.readLine());//传送金额
                    os.flush();
                    System.out.println(is.readLine());//输出结果


                }


            }
            os.close();// 关闭Socket输出流
            is.close();// 关闭Socket输入流
            server.close();// 关闭ServerSocket
            System.out.println("聊天结束!");


        } catch (UnknownHostException e) {
            System.out.println("连接失败");
        } catch (IOException e) {
            System.out.println("连接失败");
        }
    }
}

