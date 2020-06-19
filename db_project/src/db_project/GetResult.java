package db_project;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class GetResult {

	 public static void saveDB(List<XmlData> xmlList){
	  
	 }


	 public static void main(String[] args) throws SQLException {
	  // TODO Auto-generated method stub
	  File file = new File("��������ȭ���ǥ�ص�����.xml");
	  XmlParser xmlParser = new XmlParser(file);
	  List<XmlData> tmp = xmlParser.parse("record");
	  
	  ConnectDB connectDB = new ConnectDB(); 											// db
	  Connection conn = ConnectDB.getConnection();										// db
	  String sql = "insert into restroom values(?,?,?,?,?,?,?,?)";						// db
	  try {
	   for(int i=0; i<tmp.size() ; i++){
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1, tmp.get(i).getRestName());
	    stmt.setString(2, tmp.get(i).getRestAd());
	    stmt.setInt(3, tmp.get(i).getDisM_b());
	    stmt.setInt(4, tmp.get(i).getDisM_u());
	    stmt.setInt(5, tmp.get(i).getDisF());
	    stmt.setString(6, tmp.get(i).getOpen());
	    stmt.setFloat(7,  tmp.get(i).getLat());
	    stmt.setFloat(8,  tmp.get(i).getLongi());
	    stmt.executeUpdate();
	    System.out.println("sucess to save");
	   }
	  } catch (SQLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  
	  EventQueue.invokeLater(()->{
			DBFrame frame = new DBFrame();
			frame.setTitle("Toilet quick-search");
			frame.setDefaultCloseOperation(DBFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	 }

}


class DBFrame extends JFrame{
	
	private Container contentPane;
	
	
	private JPanel busPanel;
	private JTextField stationName;
	
	private JPanel genderPanel;
	private JCheckBox male;
	private JCheckBox female;
	
	private ButtonGroup genderGroup;
	
	
	private JPanel buttonPanel;
	private JButton button;
	
	private JPanel disPanel;
	private JCheckBox dis;
	
	queryResult result;
	
	public DBFrame() {
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		
		setSize(screenWidth/3, screenHeight/2);
		setLocation(screenWidth/3, screenHeight/4);
		
		
		contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(4,1));
		
		
		busPanel = new JPanel();
		JLabel stationLabel = new JLabel("���������� �̸� (�ʼ�)  ");
		stationName = new JTextField("���������� �̸� ", 20);
		stationLabel.setFont(new Font("�޸�����ü", Font.BOLD, 26));
		busPanel.add(stationLabel);
		busPanel.add(stationName);
		contentPane.add(busPanel);
		
		genderPanel = new JPanel();
		JLabel genderLabel = new JLabel("���� (����)  ");
		genderLabel.setFont(new Font("�޸�����ü", Font.BOLD, 26));
		genderPanel.add(genderLabel);
		genderGroup = new ButtonGroup();
		JRadioButton mButton = new JRadioButton("����", false);
		genderGroup.add(mButton);
		JRadioButton fButton = new JRadioButton("����", false);
		genderGroup.add(fButton);
		JRadioButton xButton = new JRadioButton("����", false);
		genderGroup.add(xButton);
		genderPanel.add(mButton);
		genderPanel.add(fButton);
		genderPanel.add(xButton);
		contentPane.add(genderPanel);
		
		
		/*
		male = new JCheckBox("����");
		female = new JCheckBox("����");
		genderPanel.add(genderLabel);
		genderPanel.add(male);
		genderPanel.add(female);
		contentPane.add(genderPanel);
	*/
		disPanel = new JPanel();
		JLabel disLabel = new JLabel("������� (����)    ");
		disLabel.setFont(new Font("�޸�����ü", Font.BOLD, 26));
		dis = new JCheckBox("�����");
		disPanel.add(disLabel);
		disPanel.add(dis);
		contentPane.add(disPanel);
				
		buttonPanel = new JPanel();
		button = new JButton("ȭ���ã��");
		button.setBackground(Color.PINK);
		buttonPanel.add(button);
		contentPane.add(buttonPanel);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���â ����
				String Name = stationName.getText();
				result = new queryResult(Name);
			}
		});
		
	}
	

	
}


// ���â
class queryResult extends JDialog{
	JLabel test = new JLabel("�׽�Ʈ");
	
	public queryResult(String str) {
		
		getContentPane().add(test);
		
		test.setText(str.toString());
		
		this.setSize(200,100);
		this.setModal(true);
		this.setVisible(true);
		
	}
}
