package Model;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ChangeGrade extends JFrame implements ActionListener//可删除
{
	
	// 组件
	JPanel panel,panel2;
	JLabel label,label2;
	JTextField textField,textField2; // 输入修改的学号和成绩
	JButton button,button2,button3; // 修改和退出修改按钮
	
	// 存储修改成绩学生学号和课程
	String Sno;
	String subject;
	
	// 数据库驱动
	static String uri = "jdbc:mysql://localhost:3306/user?"+
    "user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";			
			
	// 设置与数据库连接的对象、编译后的sql语句、查询的结果集
	Connection ct;
	PreparedStatement ps;
	ResultSet rs;
	
	// 主函数（实际运行时不需要）
	public static void main(String[] args[])
	{
		ChangeGrade cg = new ChangeGrade("123456789", "英语");
	}
	
	// 清除文本框中的内容
	public void clear()
	{
		textField.setText("");
	}
	
	// 构造函数
	public ChangeGrade(String sno, String sub)
	{
		// 学生学号以及科目
		Sno = sno;
		subject = sub;
		
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
		
		
		
		// 标签
		label = new JLabel("请输入修改该学生的学号");
		label.setFont(new Font("楷书", 1, 20));
		label.setBounds(70, 100, 250, 50);
		this.add(label);
		label2 = new JLabel("请输入修改后成绩");
		label2.setFont(new Font("楷书", 1, 20));
		label2.setBounds(70, 200, 200, 50);
		this.add(label2);
		
		// 文本框
		textField = new JTextField(10);
		textField.setBounds(350, 100, 200, 50);
		textField2 = new JTextField(10);
		textField2.setBounds(350, 200, 200, 50);
		this.add(textField);
		this.add(textField2);
		
		// 单击按钮
		button = new JButton("确认修改");
		button2 = new JButton("重置");
		button3 = new JButton("退出修改");
		button.setFont(new Font("楷书", 1, 20));
		button2.setFont(new Font("楷书", 1, 20));
		button3.setFont(new Font("楷书", 1, 20));
		button.setBounds(70, 300, 150, 50);
		button2.setBounds(250, 300, 150, 50);
		button3.setBounds(430, 300, 150, 50);
		this.add(button);
		this.add(button2);
		this.add(button3);
		
		// 添加监听
		button.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		
		// 设置格网分布
		this.setLayout(null);
		
		// 给窗口添加标题
		this.setTitle("修改学生成绩");
						
		// 设置窗口大小
		this.setSize(650, 500);
						
		// 设置窗口的起始位置
		this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
						
		// 设置当关闭窗口时，程序也结束
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						
		// 显示窗口
		this.setVisible(true);
		this.setResizable(true);				
	}
	
	// 动作监听函数
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "确认修改")
		{
			change_grade();
		}
		else if(e.getActionCommand() == "重置")
		{
			clear();
		}
		else if(e.getActionCommand() == "退出修改")
		{
			dispose();
		}
	}
	
	public void change_grade()
	{
		Sno = textField.getText();// 想查询哪个学生的成绩（输入其学号）
		try
		{
			ps = ct.prepareStatement("select * from sc where Sno =? ");		
			ps.setString(1, Sno);			
			
			rs = ps.executeQuery();					
			// 获取对应学号的学生成绩（根据此教师所教的科目）
			while(rs.next())
			{						
				String cname = rs.getString(2);
				String Grade = rs.getString(3);
				String Credit = rs.getString(4);
			}				
		}
		catch (Exception e1) 
		{					
			e1.printStackTrace();
		}
		Statement sql;
		String[] titles = {"学号","课程名","成绩","学分"};
		Object[][] userInfo = {};
		try 
		{
			sql = ct.createStatement();
			rs = sql.executeQuery("select * from sc where Sno='"+Sno+"' ");
			while(rs.next())
			{
				String cname = rs.getString(2);
				String grade = rs.getString(3);
				String credit = rs.getString(4);
				//数据追加：把userInfo数组复制到新数组newArr，并且长度+1，然后覆盖原有数组userInfo
				int length = userInfo.length;
	            Object newArr[][] = Arrays.copyOf(userInfo, length + 1);
	            newArr[length] = new Object[]{Sno,cname,grade,credit};
	            userInfo = newArr; //覆盖原有数组，以便下次在最新的数据上添加	            		
	    	}
			final DefaultTableModel model = new DefaultTableModel(userInfo, titles);
    		JTable table = new JTable(model) 
    		{
    			public boolean isCellEditable(int row, int column) 
    			{ 
    			   return false;
    			}//设置可以选中表格但是不能修改
    		};	
    		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//保证每次只选中一行
    		table .getTableHeader().setReorderingAllowed(false);//设置表头不可拖动
    		table.setRowHeight(30);//设置行宽
    		JScrollPane scr = new JScrollPane(table);
    		table.setFont(new Font("宋体",Font.PLAIN,16));
    		scr.setBounds(30, 170, 630, 250);
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		String new_grade = String.valueOf(textField.getText());
		// 当输入成绩，将字符串转换为数字
		double num_grade = 0;
		if(!new_grade.isEmpty())
			num_grade = Double.valueOf(new_grade);
		
		if(new_grade.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "修改成绩为空\n请重新输入！", "提示信息", JOptionPane.WARNING_MESSAGE);
		}else if(num_grade > 100 || num_grade < 0)
		{
			JOptionPane.showMessageDialog(null, "分数必须在0~100之间\n请重新输入！", "提示信息", JOptionPane.WARNING_MESSAGE);
		}
		/*else 
		{
			try
			{
				String sql;
				if(subject.equals("高数")) {
					sql = "update Student set 高数=? where 学号=?";
					ps = ct.prepareStatement(sql);
				}else if(subject.equals("C语言")) {
					sql = "update Student set C语言 = ? where 学号=?";
					ps = ct.prepareStatement(sql);
				}else if(subject.equals("Java")) {
					sql = "update Student set java=? where 学号=?";
					ps = ct.prepareStatement(sql);
				} else if(subject.equals("英语")){
					sql = "update Student set 英语=? where 学号=?";
					ps = ct.prepareStatement(sql);
				}
				ps.setString(1, new_grade);
				ps.setString(2, Sno);
				int count=ps.executeUpdate();//执行sql语句		
				ct.close();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}	
			JOptionPane.showMessageDialog(null, "修改成功", "提示信息", JOptionPane.WARNING_MESSAGE);
			dispose(); // 修改成功后关闭此界面
		}*/
	}
}


