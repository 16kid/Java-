package Model;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Administrator extends JFrame implements ActionListener
{
	// 定义组件	
	JPanel panel = null;                   
	JLabel label, label2, label3, label4 = null;                  
	JButton button1, button2, button3, button4 = null;
	
	//管理员工号
	String Ano;
	
	// 设置与数据库连接的对象、sql语句、查询的结果集
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	// 数据库驱动
	static String uri = "jdbc:mysql://localhost:3306/user?"+
    "user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
	
	// 主函数，单独运行时有作用（用于测试）	
	public static void main(String[] args)
	{		
		Administrator ad = new Administrator("10001");
	}
	
	Administrator(String Ano)
	{
		//连接数据库（加载驱动、建立连接）
		try 
		{
		   Class.forName("com.mysql.cj.jdbc.Driver");//加载JDBC-MySQL驱动程序
		}
		catch(Exception e) {}
		try 
		{
			ct = DriverManager.getConnection(uri);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// 创建组件		
		// 面板
		panel = new JPanel();
		panel.setBounds(125, 45, 650, 350);
		//panel.setLayout(null);
		panel.setBackground(Color.white);
		
		// 标签		
		label = new JLabel("WELCOME");
		label.setBounds(100, 10, 500, 200);
		//label2 = new JLabel(Aname);
		//label2.setBounds(35, 5, 125, 50);
		label3 = new JLabel("欢迎登陆教育机构课程系统");
		label3.setBounds(100, 60, 500, 200);
		label4 = new JLabel(Ano);
		label4.setBounds(150, 5, 125, 50);

		label.setFont(new Font("Times New Roman", 1, 25));
		//label2.setFont(new Font("楷书", 1, 25));
		label3.setFont(new Font("楷书", 1, 25));
		label4.setFont(new Font("楷书", 1, 25));
		
		// 单击按钮		
		button1 = new JButton("人员管理");
		button2 = new JButton("课程管理");
		button3 = new JButton("修改密码");
		button4 = new JButton("退出系统");
		button1.setFont(new Font("楷书", 1, 20));//protected Font(string name, int style, int size)  int BOLD == 1,加粗
		button2.setFont(new Font("楷书", 1, 20));
		button3.setFont(new Font("楷书", 1, 20));
		button4.setFont(new Font("楷书", 1, 20));
		
		// 设置动作监听		
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		
		this.add(button1);
		button1.setBounds(150, 400, 150, 50);
		this.add(button2);
		button2.setBounds(150, 500, 150, 50);
		this.add(button3);
		button3.setBounds(550, 400, 150, 50);
		this.add(button4);
		button4.setBounds(550, 500, 150, 50);
		
		// 加入面板		
		//this.add(label2);		
		this.add(label4);
		panel.add(label);
		panel.add(label3);
		
		// 将面板加入到窗口		
		this.add(panel);
		
		// 设置布局管理器（空布局）
		this.setLayout(null);
		
		// 设置窗口标题
		this.setTitle("管理员页面");
		
		// 设置窗口大小
		this.setSize(900, 700);
				
		// 设置窗口的起始位置
		this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
		
		// 设置当关闭窗口时，程序也结束
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 显示窗口
		this.setVisible(true);
		this.setResizable(true);//表示生成的窗体可以自由改变大小			
	}
	// 动作事件监听函数
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "人员管理")
		{
			peopleManagement pm = new peopleManagement();
		}
		else if(e.getActionCommand() == "课程管理")
		{		
			courseManagement cm = new courseManagement();
		}
		else if(e.getActionCommand() == "修改密码")
		{
			changepassword_1 cp = new changepassword_1();
		}
		else if(e.getActionCommand() == "退出系统")
		{
			System.exit(0);        // 结束程序，所有窗口关闭
		}	
	}
}
