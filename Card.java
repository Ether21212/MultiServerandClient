package SocketServer;

import java.sql.*;
import java.util.Scanner;

public class Card {

   public static String CreateCard(String client_id){
       Connection connection = StartDatabase();
       // 检查数据库中是否存在给定的ID
       String selectQuery = "SELECT id FROM card WHERE id = ?";
       try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
           selectStatement.setString(1, client_id);
           ResultSet resultSet = selectStatement.executeQuery();

           // 如果不存在该ID，则插入新记录
           if (!resultSet.next()) {
               String insertQuery = "INSERT INTO card (id, money) VALUES (?, 0)";
               try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                   insertStatement.setString(1, client_id);
                   insertStatement.executeUpdate();

                   return("未找到卡号，创建新卡号ID:"+ client_id + "，金额为: 0");
               } catch (SQLException e) {
                   e.printStackTrace();
                   System.out.println("插入新记录时发生错误");
                   return "插入新记录时发生错误";
               }
           } else {

               return "ID: " + client_id + " 欢迎登陆！";
           }
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println("查询ID时发生错误");
           return "查询ID时发生错误";
       }

   }




    public static String Consume(String client_id, int consume_money)  {
        //连接数据库
        Connection connection = StartDatabase();


        //创建执行环境
        String selectQuery = "SELECT money FROM card WHERE id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, client_id);

            // 执行查询
            ResultSet resultSet = selectStatement.executeQuery();

            // 移动到结果集的第一行
            if (resultSet.next()) {
                // 获取当前金额
                int currentMoney = resultSet.getInt("money");


                // 计算新的金额
                int newMoney = currentMoney - consume_money;
                if(newMoney >= 0){

                    // 更新金额
                    String updateQuery = "UPDATE card SET money = ? WHERE id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, newMoney);
                        updateStatement.setString(2, client_id);

                        // 执行更新
                        int rowsUpdated = updateStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Id:" + client_id +"消费后总金额为："+ newMoney);
                            return"消费成功！消费后金额为："+ newMoney;
                        } else {
                            System.out.println("未找到匹配的记录");
                            return "未找到匹配的记录";
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("更新失败");
                        return "更新失败";
                    }
                }
                else {
                    System.out.println("余额不足");
                    return "余额不足！";
                }


            } else {
                System.out.println("未找到匹配的记录");
                return "未找到匹配的记录";

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询失败");
            return "查询失败";
        }

    }

    public static String Charge(String client_id, int charge_money)  {
        //连接数据库
        Connection connection = StartDatabase();


        //创建执行环境
        String selectQuery = "SELECT money FROM card WHERE id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, client_id);

            // 执行查询
            ResultSet resultSet = selectStatement.executeQuery();

            // 移动到结果集的第一行
            if (resultSet.next()) {
                // 获取当前金额
                int currentMoney = resultSet.getInt("money");


                // 计算新的金额
                int newMoney = currentMoney + charge_money;


                    // 更新金额
                    String updateQuery = "UPDATE card SET money = ? WHERE id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, newMoney);
                        updateStatement.setString(2, client_id);

                        // 执行更新
                        int rowsUpdated = updateStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Id:" + client_id +"充值后总金额为：" + newMoney);
                            return "充值！充值后金额为：" + newMoney;
                        } else {
                            System.out.println("未找到匹配的记录");
                            return "未找到匹配的记录";
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("更新失败");
                        return "更新失败";
                    }




            } else {
                System.out.println("未找到匹配的记录");
                return "未找到匹配的记录";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询失败");
            return "查询失败";
        }

    }
    public static Connection StartDatabase (){
        //加载mysql驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败");
        }
        //连接数据库，获得连接对象
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "20021218");
        } catch (Exception e) {
            System.out.println("数据库连接失败");
        }
        return connection;

    }
}

