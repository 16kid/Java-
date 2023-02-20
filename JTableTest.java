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
	// ���
	JPanel panel,panel2;
	JLabel label,label2;
	JTextField textField,textField2; // �����޸ĵ�ѧ�źͳɼ�
	JButton button,button2,button3; // �޸ĺ��˳��޸İ�ť
	
	String[] titles = {"Sno","Cname","Grade","Credit"};
	Object[][] userInfo = {};
	DefaultTableModel model = new DefaultTableModel(userInfo, titles);
	JTable table = new JTable(model) 
	{
		/*public boolean isCellEditable(int row, int column) 
		{ 
		   return false;
		}//���ÿ���ѡ�б���ǲ����޸�*/
	};	
	JScrollPane scroll = new JScrollPane(table);
	
	Connection ct;
	Statement sql;
	ResultSet rs;
	
	// ����������������ʱ�����ã����ڲ��ԣ�	
	public static void main(String[] args)
	{		
		JTableTest stu = new JTableTest();
	}
	
	public JTableTest()
	{
		String uri ="jdbc:mysql://localhost:3306/user?"+"user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
    	//�������ݿ⣨�����������������ӣ�
		try 
		{
		   Class.forName("com.mysql.cj.jdbc.Driver");//����JDBC-MySQL��������
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
		
		
		// ��ǩ
		label = new JLabel("�������޸ĸ�ѧ����ѧ��");
		label.setFont(new Font("����", 1, 20));
		label.setBounds(0, 0, 250, 50);
		this.add(label);
		label2 = new JLabel("�������޸ĺ�ɼ�");
		label2.setFont(new Font("����", 1, 20));
		label2.setBounds(70, 200, 200, 50);
		//this.add(label2);
		
		// �ı���
		textField = new JTextField(10);
		textField.setBounds(350, 10, 200, 50);
		textField2 = new JTextField(10);
		textField2.setBounds(350, 200, 200, 50);
		this.add(textField);
		//this.add(textField2);
		
		// ������ť
		//button = new JButton("ȷ���޸�");
		button2 = new JButton("����");
		button3 = new JButton("�˳��޸�");
		//button.setFont(new Font("����", 1, 20));
		button2.setFont(new Font("����", 1, 20));
		button3.setFont(new Font("����", 1, 20));
		//button.setBounds(70, 300, 150, 50);
		button2.setBounds(250, 300, 150, 50);
		button3.setBounds(430, 300, 150, 50);
		//this.add(button);
		//this.add(button2);
		//this.add(button3);
		button = new JButton("��ѯ");
		button.setFont(new Font("����", 1, 20));
		button.setBounds(100, 200, 150, 50);
		this.add(button);
		button.addActionListener(this);
		// ��Ӽ���
		/*button.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);*/
		
		// ���ø����ֲ�
		this.setLayout(null);
		
		// ��������ӱ���
		this.setTitle("�޸�ѧ���ɼ�");
						
		// ���ô��ڴ�С
		this.setSize(650, 500);
						
		// ���ô��ڵ���ʼλ��
		this.setLocationRelativeTo(null);//����Ļ�м���ʾ(������ʾ)
						
		// ���õ��رմ���ʱ������Ҳ����
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						
		// ��ʾ����
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
				//����׷�ӣ���userInfo���鸴�Ƶ�������newArr�����ҳ���+1��Ȼ�󸲸�ԭ������userInfo
				int length = userInfo.length;
	            Object newArr[][] = Arrays.copyOf(userInfo, length + 1);
	            newArr[length] = new Object[]{Sno,cname,grade,credit};
	            userInfo = newArr; //����ԭ�����飬�Ա��´������µ����������	            		
	    	}
			//final DefaultTableModel model = new DefaultTableModel(userInfo, titles);
			model.setDataVector(userInfo, titles);//JTableˢ��
			JTable table = new JTable(model) ;
    		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//��֤ÿ��ֻѡ��һ��
    		table .getTableHeader().setReorderingAllowed(false);//���ñ�ͷ�����϶�
    		table.setRowHeight(30);//�����п�
    		
    		scroll = new JScrollPane(table);//��JScrollPaneװ��JTable
    		table.setFont(new Font("����",Font.PLAIN,16));
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
					     if(e.getType()==TableModelEvent.UPDATE&&e.getFirstRow()!=-1)//������ݸ��µ�ʱ��
					     {
						      String number0=(String)model.getValueAt(e.getFirstRow(), 0);//������0��ʼ����0��ʼ
						      String number1=(String)model.getValueAt(e.getFirstRow(), 1);//1Ϊ�ڶ���
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