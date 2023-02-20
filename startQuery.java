package Model;

import java.sql.*;
public class startQuery//��ɾ��
{
	String Sno,Course,Grade,Credit;
	String colnames[] = {"ѧ��","�γ���","�ɼ�","ѧ��"};	
	String record[][];//��ѯ���ļ�¼
	
	// ���ݿ�����
	static String uri = "jdbc:mysql://localhost:3306/user?"+
    "user=root&password=123456&useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
	
	
	// ���������ݿ����ӵĶ���sql��䡢��ѯ�Ľ����
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public String []getColumnName()
	{
		if(colnames==null)
		{
			System.out.println("�Ȳ�ѯ��¼");
			return null;
		}
		return colnames;
	}
	public String [][]getRecord()
	{
		startQuery();
		return record;
	}
	public void startQuery()
	{
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
		
		try
		{		
			ps = ct.prepareStatement("select * from sc where Sno =? ",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);	// ������õ�SQL��丳ֵ��ps		
			ps.setString(1, Sno);			// ��?��ֵ����setString������SQL��䰴��ѧ��Sno��ѯ			
			rs = ps.executeQuery();	        // ResultSet�����,����Ӧѧ�ŵ�ÿһ����Ϣ����ResultSet��
			
			ResultSetMetaData metaData = rs.getMetaData();//rs����getMetaData()��������һ��ResultSetMetaData����
			int columnCount = metaData.getColumnCount();//�ֶ���Ŀ
			colnames = new String[columnCount];
			for(int i=1;i<=columnCount;i++)
			{
				colnames[i-1] = metaData.getColumnName(i);
			}
			rs.last();//���α��ƶ���ResultSet����rs�����һ��
			int recordAmount = rs.getRow();//������еļ�¼��Ŀ
			record = new String[recordAmount][columnCount];
			int i=0;
			rs.beforeFirst();//���α��ƶ���ResultSet�ĳ�ʼλ�ã����ڵ�һ��֮ǰ
			
			while(rs.next())
			{
				for(int j=1;j<=columnCount;j++)
				{
					record[i][j-1] = rs.getString(j);//����i����¼�����ά����ĵ�i��
				}
				i++;
			}
		}
		catch (Exception e1) 
		{					
			e1.printStackTrace();
		}
	}
}