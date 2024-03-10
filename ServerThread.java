package SocketServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServerThread implements Runnable {

    private Socket client;
    private int num;
    Card card = new Card();
    public ServerThread(Socket client, int num){
     this.client = client;
     this.num = num;
    }




    @Override
    public void run() {    try {

        //is传进来//从socket通道中得到输入流
        BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //os传出去
        PrintWriter os = new PrintWriter(client.getOutputStream());
        // 由Socket对象得到输出流，并构造PrintWriter对象

        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));//sin系统输入
        // 由系统标准输入设备构造BufferedReader对象
        System.out.println("Client【" + num + "】 登录...............");
        String client_id = is.readLine();

        String text = card.CreateCard(client_id);//查询卡号（创建卡号）
        os.println(text);// 向客户端输出信息已有或创建
        os.flush();
        String option = "1";

        do
        {
            option = is.readLine();
            switch (option) {

                case "1":
                    os.println("请输入消费金额:");// 向客户端输出信息已有或创建和菜单
                    os.flush();

                    int consume_money = Integer.parseInt(is.readLine());
                    System.out.println("Id:" + client_id +"收到一个消费数据，消费金额为" + consume_money);

                    text = card.Consume(client_id, consume_money);

                    os.println(text);// 向客户端输出信息已有或创建和菜单
                    os.flush();
                    break;
                case "2":
                    os.println("请输入充值金额:");

                    os.flush();

                    int charge_money = Integer.parseInt(is.readLine());
                    System.out.println("Id:" + client_id + "收到一个充值数据，充值金额为" + charge_money);

                    text = card.Charge(client_id, charge_money);

                    os.println(text);// 向客户端输出信息已有或创建和菜单
                    os.flush();
                    break;

                default:
                    os.close();// 关闭Socket输出流
                    is.close();// 关闭Socket输入流
                    client.close();// 关闭Socket
                    System.out.println("聊天结束!");
                    break;
            }


        } while(option.equals("1") || option.equals("2"));



    } catch (Exception e) {
        System.out.println("Client【" + num + "】连接断开");
    }





    }
}
