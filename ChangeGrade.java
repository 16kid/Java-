package Model;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ChangeGrade extends JFrame implements ActionListener//��ɾ��
{
	
	// ���
	JPanel panel,panel2;
	JLabel label,label2;
	JTextField textField,textField2; // �����޸ĵ�ѧ�źͳɼ�
	JButton button,button2,button3; // �޸ĺ��˳��޸İ�ť
	
	// �洢�޸ĳɼ�ѧ��ѧ�źͿγ�
	String Sno;
	String subject;
	
	// ���ݿ�����
	static String uri = "jdbc:mysql://localhost:3306/user?"+
    "user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";			
			
	// ���������ݿ����ӵĶ��󡢱�����sql��䡢��ѯ�Ľ����
	Connection ct;
	PreparedStatement ps;
	ResultSet rs;
	
	// ��������ʵ������ʱ����Ҫ��
	public static void main(String[] args[])
	{
		ChangeGrade cg = new ChangeGrade("123456789", "Ӣ��");
	}
	
	// ����ı����е�����
	public void clear()
	{
		textField.setText("");
	}
	
	// ���캯��
	public ChangeGrade(String sno, String sub)
	{
		// ѧ��ѧ���Լ���Ŀ
		Sno = sno;
		subject = sub;
		
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
		label.setBounds(70, 100, 250, 50);
		this.add(label);
		label2 = new JLabel("�������޸ĺ�ɼ�");
		label2.setFont(new Font("����", 1, 20));
		label2.setBounds(70, 200, 200, 50);
		this.add(label2);
		
		// �ı���
		textField = new JTextField(10);
		textField.setBounds(350, 100, 200, 50);
		textField2 = new JTextField(10);
		textField2.setBounds(350, 200, 200, 50);
		this.add(textField);
		this.add(textField2);
		
		// ������ť
		button = new JButton("ȷ���޸�");
		button2 = new JButton("����");
		button3 = new JButton("�˳��޸�");
		button.setFont(new Font("����", 1, 20));
		button2.setFont(new Font("����", 1, 20));
		button3.setFont(new Font("����", 1, 20));
		button.setBounds(70, 300, 150, 50);
		button2.setBounds(250, 300, 150, 50);
		button3.setBounds(430, 300, 150, 50);
		this.add(button);
		this.add(button2);
		this.add(button3);
		
		// ��Ӽ���
		button.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		
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
	
	// ������������
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "ȷ���޸�")
		{
			change_grade();
		}
		else if(e.getActionCommand() == "����")
		{
			clear();
		}
		else if(e.getActionCommand() == "�˳��޸�")
		{
			dispose();
		}
	}
	
	public void change_grade()
	{
		Sno = textField.getText();// ���ѯ�ĸ�ѧ���ĳɼ���������ѧ�ţ�
		try
		{
			ps = ct.prepareStatement("select * from sc where Sno =? ");		
			ps.setString(1, Sno);			
			
			rs = ps.executeQuery();					
			// ��ȡ��Ӧѧ�ŵ�ѧ���ɼ������ݴ˽�ʦ���̵Ŀ�Ŀ��
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
		String[] titles = {"ѧ��","�γ���","�ɼ�","ѧ��"};
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
				//����׷�ӣ���userInfo���鸴�Ƶ�������newArr�����ҳ���+1��Ȼ�󸲸�ԭ������userInfo
				int length = userInfo.length;
	            Object newArr[][] = Arrays.copyOf(userInfo, length + 1);
	            newArr[length] = new Object[]{Sno,cname,grade,credit};
	            userInfo = newArr; //����ԭ�����飬�Ա��´������µ����������	            		
	    	}
			final DefaultTableModel model = new DefaultTableModel(userInfo, titles);
    		JTable table = new JTable(model) 
    		{
    			public boolean isCellEditable(int row, int column) 
    			{ 
    			   return false;
    			}//���ÿ���ѡ�б���ǲ����޸�
    		};	
    		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//��֤ÿ��ֻѡ��һ��
    		table .getTableHeader().setReorderingAllowed(false);//���ñ�ͷ�����϶�
    		table.setRowHeight(30);//�����п�
    		JScrollPane scr = new JScrollPane(table);
    		table.setFont(new Font("����",Font.PLAIN,16));
    		scr.setBounds(30, 170, 630, 250);
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		String new_grade = String.valueOf(textField.getText());
		// ������ɼ������ַ���ת��Ϊ����
		double num_grade = 0;
		if(!new_grade.isEmpty())
			num_grade = Double.valueOf(new_grade);
		
		if(new_grade.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "�޸ĳɼ�Ϊ��\n���������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
		}else if(num_grade > 100 || num_grade < 0)
		{
			JOptionPane.showMessageDialog(null, "����������0~100֮��\n���������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
		}
		/*else 
		{
			try
			{
				String sql;
				if(subject.equals("����")) {
					sql = "update Student set ����=? where ѧ��=?";
					ps = ct.prepareStatement(sql);
				}else if(subject.equals("C����")) {
					sql = "update Student set C���� = ? where ѧ��=?";
					ps = ct.prepareStatement(sql);
				}else if(subject.equals("Java")) {
					sql = "update Student set java=? where ѧ��=?";
					ps = ct.prepareStatement(sql);
				} else if(subject.equals("Ӣ��")){
					sql = "update Student set Ӣ��=? where ѧ��=?";
					ps = ct.prepareStatement(sql);
				}
				ps.setString(1, new_grade);
				ps.setString(2, Sno);
				int count=ps.executeUpdate();//ִ��sql���		
				ct.close();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}	
			JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
			dispose(); // �޸ĳɹ���رմ˽���
		}*/
	}
}


