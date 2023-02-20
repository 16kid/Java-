package Model;


/*
 * ��ʦ����
 * 
 * ����1����ʾ�����ͽ̹���
 * 
 * ����2����ѯ������Ϣ
 * 
 * ����3������ѧ�Ų�ѯѧ���ɼ�
 * 
 * ����4���޸Ķ�Ӧѧ��ѧ���ɼ�
 * 
 * ���ߣ�Сľͬѧ
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Teacher extends JFrame implements ActionListener
{
	
	// �������
	JPanel panel = null;
	JLabel label, label2, label3, label4 = null;
	JButton button, button2, button3, button4 = null;
	JTextField jtf;
	JTable jtable_1, jtable_2 = null;                                     
	DefaultTableModel model_1, model_2 = null;                            
	JScrollPane jsp_1, jsp_2 = null;                                  
	
	String tno; // �洢��ʦ�̹���
	String tname; // ��ʦ����
	String sexual;  // ��ʦ�Ա�
	String age; // ����
	String salary; // ����
	String position; // ְ��
	String subject; // ��Ŀ
	String tcourse;//���ڿ����Ƿ����Ŀ�ϲ�
	
	String sno; // ѧ��ѧ��
	String stu_name; //ѧ������
	String grade; // ����
	
	// ���ݿ�����
	static String uri = "jdbc:mysql://localhost:3306/user?"+
		    "user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
		
	// ���������ݿ����ӵĶ���sql��䡢��ѯ�Ľ����
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public static void main(String args[])
	{
		Teacher tea = new Teacher("Сľͬѧ", "123456789");
	}
	
	public Teacher(String name, String tno)
	{			
		this.tno = tno;//��¼�̹���
		tname=name;//��¼�̹�����
		
		// �������ݿ⣨�����������������ӣ�
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");			
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
				
		// ���
		panel = new JPanel();
		panel.setBounds(125, 45, 650, 350);
		//panel.setLayout(null);
		panel.setBackground(Color.white);
		
		// ��ǩ
		// ��ǩ		
		label = new JLabel("WELCOME");
		label.setBounds(100, 10, 500, 200);
		label2 = new JLabel(name);
		label2.setBounds(35, 5, 125, 50);
		label3 = new JLabel("��ӭ��½���������γ�ϵͳ");
		label3.setBounds(100, 60, 500, 200);
		label4 = new JLabel(tno);
		label4.setBounds(150, 5, 125, 50);
				
		label.setFont(new Font("Times New Roman", 1, 25));
		label2.setFont(new Font("����", 1, 25));
		label3.setFont(new Font("����", 1, 30));
		label4.setFont(new Font("����", 1, 25));
		
		// ������ť
		button = new JButton("�ҵ���Ϣ");
		button2 = new JButton("�޸ĳɼ�");
		button3 = new JButton("�޸�����");
		button4 = new JButton("�˳�ϵͳ");
		
		//���һ������γ̵İ�ť
		button.setFont(new Font("����", 1, 20));
		button2.setFont(new Font("����", 1, 20));
		button3.setFont(new Font("����", 1, 20));
		button4.setFont(new Font("����", 1, 20));
		
		// ��Ӽ�����
		button.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		
		this.add(button);
		button.setBounds(150, 400, 150, 50);
		this.add(button2);
		button2.setBounds(150, 500, 150, 50);
		this.add(button3);
		button3.setBounds(550, 400, 150, 50);
		this.add(button4);
		button4.setBounds(550, 500, 150, 50);
		
		// �ı���
		jtf = new JTextField(10);		
		
		// �������
		this.add(label2);		
		this.add(label4);
		panel.add(label);
		panel.add(label3);
		
		// �������봰��
		this.add(panel);
		
		// ���ò��ֹ��������ղ��֣�
		this.setLayout(null);
		
		// ���ô��ڱ���
		this.setTitle("��ʦҳ��");
				
		// ���ô��ڴ�С
		this.setSize(900, 700);
							
		// ���ô��ڵ���ʼλ��
		this.setLocationRelativeTo(null);//����Ļ�м���ʾ(������ʾ)
				
		// ���õ��رմ���ʱ������Ҳ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);//��ʾ���ɵĴ���������ɸı��С					
	}
	
	// ������Ӧ����
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "�ҵ���Ϣ")//��ʦ���������ν̿γ�
		{					
			teacherInfo teaInfo = new teacherInfo(tname,tno);
		}
		else if(e.getActionCommand() == "�޸ĳɼ�")
		{  
			JTableTest	cg = new JTableTest();		
		}
		else if(e.getActionCommand() == "�޸�����")
		{			
			ChangeGrade cg = new ChangeGrade(sno, subject);   // �����޸ĳɼ��Ľ���
		}
		else if(e.getActionCommand() == "�˳�ϵͳ")
		{
			System.exit(0);
		}
	}
}


