package Model;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;              // 动作事件
//import java.awt.event.ActionListener;           // 动作监听器
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener
{
	
	// 定义组件	
	JPanel jp_1,jp_2,jp_3,jp_4;       // 设置4个面板	
	JLabel jlb_1,jlb_2,jlb_3;          // 设置3个标签（用户、密码、身份）
	JTextField jtf;	                    // 设置1个普通文本框，用来输入用户账号	
	JPasswordField jpf;                  // 设置一个密码文本框，用来输入密码
	JRadioButton jrb_1,jrb_2,jrb_3;           // 设置两个按钮，用来选择身份（学生或者老师）
	ButtonGroup bg;				        // 添加一个按钮组（只能选择其中的一个）
	JButton jb_1,jb_2,jb_3;            // 设置三个单击按钮（登录、重置、退出）
	 
		
	// 管理员工号和密码
	String Ano;
	String Apassword;
	
	// 学生姓名、学号和密码
	String stu_name;
	String sno;
	String stu_password;
	
	// 老师教工号、姓名、所教授课程和密码
	String tea_name;
	String tno;
	String tcourse;
	String tea_password;
	
		
	// 数据库连接、sql语句、结果集等对象
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
		
	public static void main(String[] args)
	{
		// 创建登录界面
		Login login = new Login();	
		
		// 进行数据库的连接
		try 
		  {
		     Class.forName("com.mysql.cj.jdbc.Driver");//加载JDBC-MySQL驱动程序
		  }
		  catch(Exception e) {}
		  String uri=
		  "jdbc:mysql://localhost:3306/user?"+
		  "user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
		  try 
		  {
		   ct=DriverManager.getConnection(uri);
		  }
		  catch(SQLException e) 
		  {
		   System.out.println(e);
		  }
	}
	
	// 构造函数
	public Login()
	{
		// 创建组件
		
		// 面板（JPanel）
		jp_1 = new JPanel();
		jp_2 = new JPanel();
		jp_3 = new JPanel();
		jp_4 = new JPanel();
		
		// 标签（JLabel）
		jlb_1 = new JLabel("用户名");
		jlb_2 = new JLabel("密   码");
		jlb_3 = new JLabel("身   份");
		
		// 普通文本框（JTextField）		
		jtf = new JTextField(10);              // 用于输入用户名
		
		// 密码文本框（JPasswordField）		
		jpf = new JPasswordField(10);          // 用于输入登录密码
		
		// 单选按钮（JRadioButton）
		jrb_1 = new JRadioButton("教师");      // 用于选择身份
		jrb_2 = new JRadioButton("学生");	
		jrb_3 = new JRadioButton("管理员");
		
		//按钮组（ButtonGroup）只能选择其中一个
		bg = new ButtonGroup();
		bg.add(jrb_1);
		bg.add(jrb_2);
		bg.add(jrb_3);
		//jrb_2.setSelected(true);               // 默认为学生
		
		//单击按钮（JButton）
		jb_1 = new JButton("登录");
		jb_2 = new JButton("重置");
		jb_3 = new JButton("退出");	
		// 设置监听
		jb_1.addActionListener(this);
		jb_2.addActionListener(this);
		jb_3.addActionListener(this);
		
		
		// 将个组件加入到面板
		jp_1.add(jlb_1);
		jp_1.add(jtf);
		
		jp_2.add(jlb_2);
		jp_2.add(jpf);
		
		jp_3.add(jlb_3);
		jp_3.add(jrb_1);
		jp_3.add(jrb_2);
		jp_3.add(jrb_3);
		
		jp_4.add(jb_1);
		jp_4.add(jb_2);
		jp_4.add(jb_3);
		
		// 将面板加入到框架
		this.add(jp_1);
		this.add(jp_2);
		this.add(jp_3);
		this.add(jp_4);
		
				
		// 设置布局管理器（格网布局）
		this.setLayout(new GridLayout(4,1));
		
		// 给窗口添加标题
		this.setTitle("信息管理系统");
		
		// 设置窗口大小
		this.setSize(500, 300);
		
		// 设置窗口的起始位置
		this.setLocationRelativeTo(null);       //在屏幕中间显示(居中显示)
		
		// 设置当关闭窗口时，程序也结束
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//EXIT_ON_CLOSE:结束窗口所在的应用程序。
		
		// 显示窗口
		this.setVisible(true);
		this.setResizable(true);//表示生成的窗体可以自由改变大小	
	}
	
	
	// 将两个文本框的内容清空，点击重置按钮时调用
	public void clear()
	{
		jtf.setText("");
		jpf.setText("");
	}
		
	// 动作事件监听函数
	public void actionPerformed(ActionEvent e)
	{	
		if(e.getActionCommand() == "登录")
		{
			// 教师登录
			if(jrb_1.isSelected())
			{
				try 
				{
					ps = ct.prepareStatement("select * from teachers where Identity=? and Tname=?");
					// 给?赋值（按照身份为教师、用户名为输入的用户名查询）
					ps.setString(1, "教师");					
					ps.setString(2, jtf.getText());//用来获取用户账号

					// ResultSet结果集
					rs = ps.executeQuery();
					if(rs.next())
					{					
						//将教师的工号、姓名、密码、所授课程从数据库取出
						tno = rs.getString(2);
						tea_name = rs.getString(3);
						tea_password = rs.getString(4);	
						tcourse = rs.getString(5);
						tea_login();
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "没有此用户或用户名为空！\n请重新输入", "提示消息", JOptionPane.WARNING_MESSAGE);
					}
				}catch (SQLException e1) 
				{
					e1.printStackTrace();
				}							
			}
			else if(jrb_2.isSelected()) 
			{
				// 学生登录
				try 
				{
					ps = ct.prepareStatement("select * from students where Identity=? and Sname=?");
					//给?赋值（按照身份为学生、用户名为输入的用户名查询）
					ps.setString(1, "学生");
					ps.setString(2, jtf.getText());//用来获取姓名

					//ResultSet结果集
					rs=ps.executeQuery();
					if(rs.next())
					{
						//将学生的学号、姓名、密码取出
						sno = rs.getString(2);	
						stu_name = rs.getString(3);//姓名
						stu_password = rs.getString(4);					 						
						//System.out.printf("%s\t",sno);
						//System.out.printf("%s\t",stu_name);
						//System.out.printf("%s\t",stu_password);
						stu_login();
					}else 
					{
						JOptionPane.showMessageDialog(null, "没有此用户或用户名为空！\n请重新输入", "提示消息", JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e1) 
				{					
					e1.printStackTrace();
				}												
			}
			else if(jrb_3.isSelected())
			{
				//管理员登录
				try 
				{
					ps = ct.prepareStatement("select * from administrator where Identity=? and Ano=?");
					//给?赋值（按照身份为管理员、工号为输入的工号查询）
					ps.setString(1, "管理员");
					ps.setString(2, jtf.getText());//用来获取管理员工号

					//ResultSet结果集
					rs=ps.executeQuery();
					if(rs.next())
					{
						//将管理员的工号、密码取出
						Ano = rs.getString(2);//工号
						Apassword = rs.getString(3);//密码					 						
						//System.out.printf("%s\t",Ano);
						//System.out.printf("%s\t",Apassword);
						ad_login();
					}else 
					{
						JOptionPane.showMessageDialog(null, "没有此用户或用户名为空！\n请重新输入", "提示消息", JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e1) 
				{					
					e1.printStackTrace();
				}					
			}
		}
		else if(e.getActionCommand() == "重置")
		{	
			clear();	        // 清除文本框里的内容	
		}
		else if(e.getActionCommand() == "退出")
		{		
			System.exit(0);		// 结束程序，关闭所有窗口
		}	
		
	}
	
	
	// 学生登陆判断方法
	public void stu_login()
	{
		if(stu_name.equals(jtf.getText()) && stu_password.equals(String.valueOf(jpf.getPassword())))//后期看看需不需要改一下用学号做判断
		{
			JOptionPane.showMessageDialog(null, "登陆成功！", "提示消息", JOptionPane.WARNING_MESSAGE);		
			clear();		
			dispose();			// 关闭当前登录界面
			Student stu = new Student(stu_name, sno); // 创建学生界面并显示
		}
		else if(jtf.getText().isEmpty() && String.valueOf(jpf.getPassword()).isEmpty())
		{	
			JOptionPane.showMessageDialog(null, "请输入用户名和密码！", "提示消息", JOptionPane.WARNING_MESSAGE);	
		}
		else if(jtf.getText().isEmpty())
		{		
			JOptionPane.showMessageDialog(null, "请输入用户名！", "提示信息", JOptionPane.WARNING_MESSAGE);	
		}
		else if(String.valueOf(jpf.getPassword()).isEmpty())
		{		
			JOptionPane.showMessageDialog(null, "请输入密码！", "提示信息", JOptionPane.WARNING_MESSAGE);		
		}
		else
		{			
			JOptionPane.showMessageDialog(null, "用户名或者密码错误！\n请重新输入", "提示信息", JOptionPane.ERROR_MESSAGE);
			clear();			
		}
	}
	
	
	// 教师登陆判断方法
	public void tea_login() 
	{
		if(tea_name.equals(jtf.getText()) && tea_password.equals(String.valueOf(jpf.getPassword())))
		{		
			JOptionPane.showMessageDialog(null, "登录成功", "提示信息", JOptionPane.WARNING_MESSAGE);		
			clear();		
			dispose();		
			Teacher tea = new Teacher(tea_name, tno);			
		}
		else if(jtf.getText().isEmpty() && String.valueOf(jpf.getPassword()).isEmpty()) 
		{		
			JOptionPane.showMessageDialog(null, "请输入用户名和密码！", "提示信息", JOptionPane.WARNING_MESSAGE);		
		}
		else if(jtf.getText().isEmpty()) 
		{	
			JOptionPane.showMessageDialog(null, "请输入用户名！", "提示信息", JOptionPane.WARNING_MESSAGE);	
		}
		else if(String.valueOf(jpf.getPassword()).isEmpty()) 
		{		
			JOptionPane.showMessageDialog(null, "请输入密码！", "提示信息", JOptionPane.WARNING_MESSAGE);	
		}
		else 
		{	
			JOptionPane.showMessageDialog(null, "用户名或密码错误！\n请重新输入", "提示信息", JOptionPane.ERROR_MESSAGE);	
			clear();
		}		
	}
	
	
	// 管理员登陆判断方法
	public void ad_login() 
	{
		if(Ano.equals(jtf.getText()) && Apassword.equals(String.valueOf(jpf.getPassword()))) 
		{		
			JOptionPane.showMessageDialog(null, "登录成功", "提示信息", JOptionPane.WARNING_MESSAGE);		
			clear();		
			dispose();		
			Administrator ad = new Administrator(Ano);			
		}
		else if(jtf.getText().isEmpty() && String.valueOf(jpf.getPassword()).isEmpty()) 
		{		
			JOptionPane.showMessageDialog(null, "请输入用户名和密码！", "提示信息", JOptionPane.WARNING_MESSAGE);		
		}
		else if(jtf.getText().isEmpty()) 
		{	
			JOptionPane.showMessageDialog(null, "请输入用户名！", "提示信息", JOptionPane.WARNING_MESSAGE);	
		}
		else if(String.valueOf(jpf.getPassword()).isEmpty()) 
		{		
			JOptionPane.showMessageDialog(null, "请输入密码！", "提示信息", JOptionPane.WARNING_MESSAGE);	
		}
		else 
		{	
			JOptionPane.showMessageDialog(null, "用户名或密码错误！\n请重新输入", "提示信息", JOptionPane.ERROR_MESSAGE);	
			clear();
		}		
	}
}
