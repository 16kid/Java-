package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class showData
{
	// ���������ݿ����ӵĶ���sql��䡢��ѯ�Ľ����
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	static String Sno;
	
	showData(String Sno)
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
        
		//���ص�����List
        LinkedList<Stu_info> list =new LinkedList<>();
        try 
        {
        	PreparedStatement ps = ct.prepareStatement("select * from sc where Sno =? ");
    		ps.setString(1, Sno);
    		ResultSet rs =ps.executeQuery();
            
            while (rs.next())
            {
                Stu_info stu_info =new Stu_info();
                stu_info.setSno(rs.getString("Sno"));
                stu_info.setCourse(rs.getString("Cname"));
                stu_info.setGrade(rs.getString("Grade"));
                stu_info.setCredit(rs.getString("Credit"));
                list.add(stu_info);
                System.out.println("id:"+rs.getObject("Sno")+" "+"Cname:"+rs.getObject("Cname")+" "
                        +"Grade:"+rs.getString("Grade")+" "+"Credit:"+rs.getString("Credit"));
            }
            ct.close(); 
        }
        catch(Exception e1) 
        {
        	e1.printStackTrace();
        }
        
      //1���趨����
        JFrame frame =new JFrame("��mysql�л�ȡ���ݲ�չʾ~");
        frame.setLocation(700,400);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        //2,���table
        JTable table =null;
        String [] index={"Sno","Course","Grade","Credit"};
        Object [][] data=new Object[list.size()][index.length];
        //3,��data���������
        for (int i = 0; i < list.size(); i++) 
        {
            Stu_info stu_info =list.get(i);
            data[i][0]=stu_info.getSno();
            data[i][1]=stu_info.getCourse();
            data[i][2]=stu_info.getGrade();
            data[i][3]=stu_info.getCredit();

        }
        //4,����һ��Ĭ�ϵı��ģ��
        DefaultTableModel defaultModel = new DefaultTableModel(data, index);
        table=new JTable(defaultModel);
        table.setBackground(Color.cyan);
        table.setPreferredScrollableViewportSize(new Dimension(100, 80));//JTable�ĸ߶ȺͿ�Ȱ����趨
        table.setFillsViewportHeight(true);

        //5����������ù�����
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);

        Font font = new Font("����", Font.BOLD, 13);

        //���button
        JButton button =new JButton("��ѯ");
        button.setBounds(50,10,50,30);

        //���label
        JLabel label =new JLabel("�����ť����ѯMySQL���ݿ��е����ݣ�");
        label.setFont(font);
        label.setBounds(1,10,240,30);

        //ͨ��panel���button��label
        JPanel panel =new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setSize(200,100);
        panel.add(label);
        panel.add(button);

        //6����ӱ�񡢹�������������
        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);


        button.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                frame.add(jScrollPane,BorderLayout.CENTER);
                frame.setVisible(true);

            }
        });
	}
		//��ȡ���ݿ��е����ݲ���list����
	    /*public static List<Stu_info> getDbData()  
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
	        
			//���ص�����List
	        LinkedList<Stu_info> list =new LinkedList<>();
	        try 
	        {
	        	PreparedStatement ps = ct.prepareStatement("select * from sc where Sno =? ");
	    		ps.setString(1, Sno);
	    		ResultSet rs =ps.executeQuery();
	            
	            while (rs.next())
	            {
	                Stu_info stu_info =new Stu_info();
	                stu_info.setSno(rs.getString("Sno"));
	                stu_info.setCourse(rs.getString("Course"));
	                stu_info.setGrade(rs.getString("Grade"));
	                stu_info.setCredit(rs.getString("Credit"));
	                list.add(stu_info);
	                System.out.println("id:"+rs.getObject("Sno")+" "+"Course:"+rs.getObject("course")+" "
	                        +"Grade:"+rs.getString("Grade")+" "+"Credit:"+rs.getString("Credit"));
	            }
	            ct.close(); 
	        }
	        catch(Exception e1) 
	        {
	        	e1.printStackTrace();
	        }
	        return list;
	    }*/
	
    
    //�������ڣ����б�չʾ�����ݿ��л�ȡ������
    public static void showFrame(List<Stu_info> list)
    {

        //1���趨����
        JFrame frame =new JFrame("��mysql�л�ȡ���ݲ�չʾ~");
        frame.setLocation(700,400);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        //2,���table
        JTable table =null;
        String [] index={"Sno","Course","Grade","Credit"};
        Object [][] data=new Object[list.size()][index.length];
        //3,��data���������
        for (int i = 0; i < list.size(); i++) 
        {
            Stu_info stu_info =list.get(i);
            data[i][0]=stu_info.getSno();
            data[i][1]=stu_info.getCourse();
            data[i][2]=stu_info.getGrade();
            data[i][3]=stu_info.getCredit();

        }
        //4,����һ��Ĭ�ϵı��ģ��
        DefaultTableModel defaultModel = new DefaultTableModel(data, index);
        table=new JTable(defaultModel);
        table.setBackground(Color.cyan);
        table.setPreferredScrollableViewportSize(new Dimension(100, 80));//JTable�ĸ߶ȺͿ�Ȱ����趨
        table.setFillsViewportHeight(true);

        //5����������ù�����
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);

        Font font = new Font("����", Font.BOLD, 13);

        //���button
        JButton button =new JButton("��ѯ");
        button.setBounds(50,10,50,30);

        //���label
        JLabel label =new JLabel("�����ť����ѯMySQL���ݿ��е����ݣ�");
        label.setFont(font);
        label.setBounds(1,10,240,30);

        //ͨ��panel���button��label
        JPanel panel =new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setSize(200,100);
        panel.add(label);
        panel.add(button);

        //6����ӱ�񡢹�������������
        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);


        button.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                frame.add(jScrollPane,BorderLayout.CENTER);
                frame.setVisible(true);

            }
        });

    }
    
 
   /* public static void main(String[] args) throws SQLException, ClassNotFoundException 
    {
//        getDbData();
        showFrame(getDbData( ));
    }*/
}


