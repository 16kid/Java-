package Model;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;              // �����¼�
//import java.awt.event.ActionListener;           // ����������
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener
{
	
	// �������	
	JPanel jp_1,jp_2,jp_3,jp_4;       // ����4�����	
	JLabel jlb_1,jlb_2,jlb_3;          // ����3����ǩ���û������롢��ݣ�
	JTextField jtf;	                    // ����1����ͨ�ı������������û��˺�	
	JPasswordField jpf;                  // ����һ�������ı���������������
	JRadioButton jrb_1,jrb_2,jrb_3;           // ����������ť������ѡ����ݣ�ѧ��������ʦ��
	ButtonGroup bg;				        // ���һ����ť�飨ֻ��ѡ�����е�һ����
	JButton jb_1,jb_2,jb_3;            // ��������������ť����¼�����á��˳���
	 
		
	// ����Ա���ź�����
	String Ano;
	String Apassword;
	
	// ѧ��������ѧ�ź�����
	String stu_name;
	String sno;
	String stu_password;
	
	// ��ʦ�̹��š������������ڿγ̺�����
	String tea_name;
	String tno;
	String tcourse;
	String tea_password;
	
		
	// ���ݿ����ӡ�sql��䡢������ȶ���
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
		
	public static void main(String[] args)
	{
		// ������¼����
		Login login = new Login();	
		
		// �������ݿ������
		try 
		  {
		     Class.forName("com.mysql.cj.jdbc.Driver");//����JDBC-MySQL��������
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
	
	// ���캯��
	public Login()
	{
		// �������
		
		// ��壨JPanel��
		jp_1 = new JPanel();
		jp_2 = new JPanel();
		jp_3 = new JPanel();
		jp_4 = new JPanel();
		
		// ��ǩ��JLabel��
		jlb_1 = new JLabel("�û���");
		jlb_2 = new JLabel("��   ��");
		jlb_3 = new JLabel("��   ��");
		
		// ��ͨ�ı���JTextField��		
		jtf = new JTextField(10);              // ���������û���
		
		// �����ı���JPasswordField��		
		jpf = new JPasswordField(10);          // ���������¼����
		
		// ��ѡ��ť��JRadioButton��
		jrb_1 = new JRadioButton("��ʦ");      // ����ѡ�����
		jrb_2 = new JRadioButton("ѧ��");	
		jrb_3 = new JRadioButton("����Ա");
		
		//��ť�飨ButtonGroup��ֻ��ѡ������һ��
		bg = new ButtonGroup();
		bg.add(jrb_1);
		bg.add(jrb_2);
		bg.add(jrb_3);
		//jrb_2.setSelected(true);               // Ĭ��Ϊѧ��
		
		//������ť��JButton��
		jb_1 = new JButton("��¼");
		jb_2 = new JButton("����");
		jb_3 = new JButton("�˳�");	
		// ���ü���
		jb_1.addActionListener(this);
		jb_2.addActionListener(this);
		jb_3.addActionListener(this);
		
		
		// ����������뵽���
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
		
		// �������뵽���
		this.add(jp_1);
		this.add(jp_2);
		this.add(jp_3);
		this.add(jp_4);
		
				
		// ���ò��ֹ��������������֣�
		this.setLayout(new GridLayout(4,1));
		
		// ��������ӱ���
		this.setTitle("��Ϣ����ϵͳ");
		
		// ���ô��ڴ�С
		this.setSize(500, 300);
		
		// ���ô��ڵ���ʼλ��
		this.setLocationRelativeTo(null);       //����Ļ�м���ʾ(������ʾ)
		
		// ���õ��رմ���ʱ������Ҳ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//EXIT_ON_CLOSE:�����������ڵ�Ӧ�ó���
		
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);//��ʾ���ɵĴ���������ɸı��С	
	}
	
	
	// �������ı����������գ�������ð�ťʱ����
	public void clear()
	{
		jtf.setText("");
		jpf.setText("");
	}
		
	// �����¼���������
	public void actionPerformed(ActionEvent e)
	{	
		if(e.getActionCommand() == "��¼")
		{
			// ��ʦ��¼
			if(jrb_1.isSelected())
			{
				try 
				{
					ps = ct.prepareStatement("select * from teachers where Identity=? and Tname=?");
					// ��?��ֵ���������Ϊ��ʦ���û���Ϊ������û�����ѯ��
					ps.setString(1, "��ʦ");					
					ps.setString(2, jtf.getText());//������ȡ�û��˺�

					// ResultSet�����
					rs = ps.executeQuery();
					if(rs.next())
					{					
						//����ʦ�Ĺ��š����������롢���ڿγ̴����ݿ�ȡ��
						tno = rs.getString(2);
						tea_name = rs.getString(3);
						tea_password = rs.getString(4);	
						tcourse = rs.getString(5);
						tea_login();
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "û�д��û����û���Ϊ�գ�\n����������", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
					}
				}catch (SQLException e1) 
				{
					e1.printStackTrace();
				}							
			}
			else if(jrb_2.isSelected()) 
			{
				// ѧ����¼
				try 
				{
					ps = ct.prepareStatement("select * from students where Identity=? and Sname=?");
					//��?��ֵ���������Ϊѧ�����û���Ϊ������û�����ѯ��
					ps.setString(1, "ѧ��");
					ps.setString(2, jtf.getText());//������ȡ����

					//ResultSet�����
					rs=ps.executeQuery();
					if(rs.next())
					{
						//��ѧ����ѧ�š�����������ȡ��
						sno = rs.getString(2);	
						stu_name = rs.getString(3);//����
						stu_password = rs.getString(4);					 						
						//System.out.printf("%s\t",sno);
						//System.out.printf("%s\t",stu_name);
						//System.out.printf("%s\t",stu_password);
						stu_login();
					}else 
					{
						JOptionPane.showMessageDialog(null, "û�д��û����û���Ϊ�գ�\n����������", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e1) 
				{					
					e1.printStackTrace();
				}												
			}
			else if(jrb_3.isSelected())
			{
				//����Ա��¼
				try 
				{
					ps = ct.prepareStatement("select * from administrator where Identity=? and Ano=?");
					//��?��ֵ���������Ϊ����Ա������Ϊ����Ĺ��Ų�ѯ��
					ps.setString(1, "����Ա");
					ps.setString(2, jtf.getText());//������ȡ����Ա����

					//ResultSet�����
					rs=ps.executeQuery();
					if(rs.next())
					{
						//������Ա�Ĺ��š�����ȡ��
						Ano = rs.getString(2);//����
						Apassword = rs.getString(3);//����					 						
						//System.out.printf("%s\t",Ano);
						//System.out.printf("%s\t",Apassword);
						ad_login();
					}else 
					{
						JOptionPane.showMessageDialog(null, "û�д��û����û���Ϊ�գ�\n����������", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e1) 
				{					
					e1.printStackTrace();
				}					
			}
		}
		else if(e.getActionCommand() == "����")
		{	
			clear();	        // ����ı����������	
		}
		else if(e.getActionCommand() == "�˳�")
		{		
			System.exit(0);		// �������򣬹ر����д���
		}	
		
	}
	
	
	// ѧ����½�жϷ���
	public void stu_login()
	{
		if(stu_name.equals(jtf.getText()) && stu_password.equals(String.valueOf(jpf.getPassword())))//���ڿ����費��Ҫ��һ����ѧ�����ж�
		{
			JOptionPane.showMessageDialog(null, "��½�ɹ���", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);		
			clear();		
			dispose();			// �رյ�ǰ��¼����
			Student stu = new Student(stu_name, sno); // ����ѧ�����沢��ʾ
		}
		else if(jtf.getText().isEmpty() && String.valueOf(jpf.getPassword()).isEmpty())
		{	
			JOptionPane.showMessageDialog(null, "�������û��������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);	
		}
		else if(jtf.getText().isEmpty())
		{		
			JOptionPane.showMessageDialog(null, "�������û�����", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);	
		}
		else if(String.valueOf(jpf.getPassword()).isEmpty())
		{		
			JOptionPane.showMessageDialog(null, "���������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);		
		}
		else
		{			
			JOptionPane.showMessageDialog(null, "�û��������������\n����������", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
			clear();			
		}
	}
	
	
	// ��ʦ��½�жϷ���
	public void tea_login() 
	{
		if(tea_name.equals(jtf.getText()) && tea_password.equals(String.valueOf(jpf.getPassword())))
		{		
			JOptionPane.showMessageDialog(null, "��¼�ɹ�", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);		
			clear();		
			dispose();		
			Teacher tea = new Teacher(tea_name, tno);			
		}
		else if(jtf.getText().isEmpty() && String.valueOf(jpf.getPassword()).isEmpty()) 
		{		
			JOptionPane.showMessageDialog(null, "�������û��������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);		
		}
		else if(jtf.getText().isEmpty()) 
		{	
			JOptionPane.showMessageDialog(null, "�������û�����", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);	
		}
		else if(String.valueOf(jpf.getPassword()).isEmpty()) 
		{		
			JOptionPane.showMessageDialog(null, "���������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);	
		}
		else 
		{	
			JOptionPane.showMessageDialog(null, "�û������������\n����������", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);	
			clear();
		}		
	}
	
	
	// ����Ա��½�жϷ���
	public void ad_login() 
	{
		if(Ano.equals(jtf.getText()) && Apassword.equals(String.valueOf(jpf.getPassword()))) 
		{		
			JOptionPane.showMessageDialog(null, "��¼�ɹ�", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);		
			clear();		
			dispose();		
			Administrator ad = new Administrator(Ano);			
		}
		else if(jtf.getText().isEmpty() && String.valueOf(jpf.getPassword()).isEmpty()) 
		{		
			JOptionPane.showMessageDialog(null, "�������û��������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);		
		}
		else if(jtf.getText().isEmpty()) 
		{	
			JOptionPane.showMessageDialog(null, "�������û�����", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);	
		}
		else if(String.valueOf(jpf.getPassword()).isEmpty()) 
		{		
			JOptionPane.showMessageDialog(null, "���������룡", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);	
		}
		else 
		{	
			JOptionPane.showMessageDialog(null, "�û������������\n����������", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);	
			clear();
		}		
	}
}
