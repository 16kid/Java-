package Model;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class addCourse extends JFrame implements ActionListener
{
	JLabel label,label2,label3;
	JTextField textField1,textField2,textField3;
	JButton button;
	
	Connection con;
	Statement sql;
	
	// 主函数，单独运行时有作用（用于测试）	
	public static void main(String[] args)
	{		
		addCourse stu = new addCourse();
	}
	
	addCourse()
	{
		String uri ="jdbc:mysql://localhost:3306/user?"+"user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
    	//连接数据库（加载驱动、建立连接）
		try 
		{
		   Class.forName("com.mysql.cj.jdbc.Driver");//加载JDBC-MySQL驱动程序
		}
		catch(Exception e) {}
		try 
		{
			con = DriverManager.getConnection(uri);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
		
		//标签
		label = new JLabel("需要添加的课程号");
		label.setBounds(100, 100, 200, 50);
		label2 = new JLabel("需要添加的课程名");
		label2.setBounds(100, 200, 200, 50);
		label3 = new JLabel("需要添加的课程学分");
		label3.setBounds(100, 300, 200, 50);
		
		label.setFont(new Font("楷书", 1, 20));
		label2.setFont(new Font("楷书", 1, 20));
		label3.setFont(new Font("楷书", 1, 20));
		
		this.add(label);
		this.add(label2);
		this.add(label3);

		
		//文本框
		textField1 = new JTextField();
		textField2 = new JTextField();
		textField3 = new JTextField();		
		
		this.add(textField1);
		textField1.setBounds(300, 100, 150, 50);		
		this.add(textField2);
		textField2.setBounds(300, 200, 150, 50);
		this.add(textField3);
		textField3.setBounds(300, 300, 150, 50);
		
		//按钮
		button = new JButton("添加");
		button.setBounds(225, 400, 100, 50);	
		button.setFont(new Font("楷书", 1, 25));
		this.add(button);
		button.addActionListener(this);
		
		// 设置布局管理器（空布局）
		this.setLayout(null);
		
		// 设置窗口标题
		this.setTitle("添加课程");
		
		// 设置窗口大小
		this.setSize(550, 600);
				
		// 设置窗口的起始位置
		this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
		
		// 设置隐藏当前窗口，并释放窗体占有的其他资源
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// 显示窗口
		this.setVisible(true);
		this.setResizable(true);//表示生成的窗体可以自由改变大小	
	}	
		
	public void actionPerformed(ActionEvent e) 
	{
		String addCno = textField1.getText();
        String addCname = textField2.getText();
        String addCredit = textField3.getText();	     

        try 
        {
           sql=con.createStatement();            
                     
           String  strSQL="insert into course values('"+addCno+"','"+addCname+"','"+addCredit+"')";
    	   String  strSQL1="(select * from  course where cname='"+addCname+"' )";

    	   if(!addCname.trim().equals("")&&!addCno.trim().equals("")&&!addCredit.trim().equals(""))
    	   {
    		   ResultSet rs1=sql.executeQuery(strSQL1);
    		 
        	   if(rs1.next())	 
        	   {
        		   JOptionPane.showMessageDialog(null,"该课程已存在");     
        	   }
        	   else 
        	   {
	        	   int rs=sql.executeUpdate(strSQL);
	        		  
	        	   if(rs==1) 
	        	   {
	        		   JOptionPane.showMessageDialog(null,"课程添加成功");
	        	   }
	        	   else
	        	   {
	        		   JOptionPane.showMessageDialog(null,"课程添加失败");
	        	   }
        	   }
    	    }
		    else
		    { 
			    JOptionPane.showMessageDialog(null,"课程信息输入有误，请重新输入！");
		    }	  	  
        }
        catch(Exception e1) 
        {
        	e1.printStackTrace();
    	}
     }	
}
