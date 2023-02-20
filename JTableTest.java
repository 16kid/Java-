package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.util.Arrays;

public class JTableTest extends JFrame implements ActionListener 
{
	String Sno;
	// 组件
	JPanel panel,panel2;
	JLabel label,label2;
	JTextField textField,textField2; // 输入修改的学号和成绩
	JButton button,button2,button3; // 修改和退出修改按钮
	
	String[] titles = {"Sno","Cname","Grade","Credit"};
	Object[][] userInfo = {};
	DefaultTableModel model = new DefaultTableModel(userInfo, titles);
	JTable table = new JTable(model) 
	{
		/*public boolean isCellEditable(int row, int column) 
		{ 
		   return false;
		}//设置可以选中表格但是不能修改*/
	};	
	JScrollPane scroll = new JScrollPane(table);
	
	Connection ct;
	Statement sql;
	ResultSet rs;
	
	// 主函数，单独运行时有作用（用于测试）	
	public static void main(String[] args)
	{		
		JTableTest stu = new JTableTest();
	}
	
	public JTableTest()
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
			ct = DriverManager.getConnection(uri);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		
		// 标签
		label = new JLabel("请输入修改该学生的学号");
		label.setFont(new Font("楷书", 1, 20));
		label.setBounds(0, 0, 250, 50);
		this.add(label);
		label2 = new JLabel("请输入修改后成绩");
		label2.setFont(new Font("楷书", 1, 20));
		label2.setBounds(70, 200, 200, 50);
		//this.add(label2);
		
		// 文本框
		textField = new JTextField(10);
		textField.setBounds(350, 10, 200, 50);
		textField2 = new JTextField(10);
		textField2.setBounds(350, 200, 200, 50);
		this.add(textField);
		//this.add(textField2);
		
		// 单击按钮
		//button = new JButton("确认修改");
		button2 = new JButton("重置");
		button3 = new JButton("退出修改");
		//button.setFont(new Font("楷书", 1, 20));
		button2.setFont(new Font("楷书", 1, 20));
		button3.setFont(new Font("楷书", 1, 20));
		//button.setBounds(70, 300, 150, 50);
		button2.setBounds(250, 300, 150, 50);
		button3.setBounds(430, 300, 150, 50);
		//this.add(button);
		//this.add(button2);
		//this.add(button3);
		button = new JButton("查询");
		button.setFont(new Font("楷书", 1, 20));
		button.setBounds(100, 200, 150, 50);
		this.add(button);
		button.addActionListener(this);
		// 添加监听
		/*button.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);*/
		
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
	public void actionPerformed(ActionEvent e) 
	{
		JFrame win7=new JFrame();
		win7.setSize(700,500);
		win7.setLayout(null);
		win7.setResizable(false);
		win7.setLocationRelativeTo(null);
		win7.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win7.setVisible(true);
		
		Sno=textField.getText();
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
			//final DefaultTableModel model = new DefaultTableModel(userInfo, titles);
			model.setDataVector(userInfo, titles);//JTable刷新
			JTable table = new JTable(model) ;
    		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//保证每次只选中一行
    		table .getTableHeader().setReorderingAllowed(false);//设置表头不可拖动
    		table.setRowHeight(30);//设置行宽
    		
    		scroll = new JScrollPane(table);//用JScrollPane装载JTable
    		table.setFont(new Font("宋体",Font.PLAIN,16));
    		scroll.setBounds(30, 100, 450, 250);
    		win7.add(scroll);
    		upDate();
    		//panel.setSize(650, 500);
    		//add(panel);
    		//setVisible(true);
		}
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}	
	}
	public void upDate() 
	{
		  model.addTableModelListener(new TableModelListener() 
		  {
			   public void tableChanged(TableModelEvent e) 
			   {
				    try 
				    {
					     sql=ct.createStatement();
					     if(e.getType()==TableModelEvent.UPDATE&&e.getFirstRow()!=-1)//表的数据更新的时候
					     {
						      String number0=(String)model.getValueAt(e.getFirstRow(), 0);//索引从0开始！从0开始
						      String number1=(String)model.getValueAt(e.getFirstRow(), 1);//1为第二列
						      System.out.println(5);
						      int ok=sql.executeUpdate("update sc set "+titles[e.getColumn()]+"='"+table.getValueAt(e.getFirstRow(),e.getColumn())+"' where Sno='"+number0+"' and Cname='"+number1+"'");
					     }
				    }
				    catch(SQLException e0)
				    {
				    	System.out.println(e0);
				    }
				    System.out.println(5);
			   }
		  });
		 }
}