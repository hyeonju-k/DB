package db_project;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;


public class GetResult {
   static /*
    * public static void saveDB(List<XmlData> xmlList){
    * 
    * }
    */
   
   Connection conn = null;
   
    public static void main(String[] args) throws SQLException {
       String keyword = null;
       
       File file = new File("전국공중화장실표준데이터.xml");
       XmlParser xmlParser = new XmlParser(file);
       List<XmlData> tmp = xmlParser.parse("record");
       
       ConnectDB connectDB = new ConnectDB();                                  // db
       conn = ConnectDB.getConnection();                              // db
       String sql = "insert into restroom values(?,?,?,?,?,?,?,?)";                  // db
     
       int numOfInst = 0;
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
             System.out.println("sucess to save(restroom)["+(++numOfInst)+"]");
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
   private ButtonGroup genderGroup = new ButtonGroup();
   private JRadioButton mButton = new JRadioButton("남성", false);
   private JRadioButton fButton = new JRadioButton("여성", false);
   private JRadioButton xButton = new JRadioButton("무관", false);
   
   
   private JPanel buttonPanel;
   private JButton button;
   private JButton[] list;
   
   private JPanel disPanel;
   private JCheckBox dis;
   
   private JPanel listPanel;
   
   private JTextArea nearText; 
   private JScrollPane scrollPane;
   

   
   
   
   queryResult result;
   
   public DBFrame() {
      
      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screenSize = kit.getScreenSize();
      int screenHeight = screenSize.height;
      int screenWidth = screenSize.width;
      
      setSize(screenWidth/3, screenHeight/2);
      setLocation(screenWidth/3, screenHeight/4);
      
      
      mButton.setEnabled(false);
      fButton.setEnabled(false);
      xButton.setEnabled(false);
      
      
      contentPane = getContentPane();
      contentPane.setLayout(new GridLayout(5,1));
      
      busPanel = new JPanel();
      JLabel stationLabel = new JLabel("버스정류장 이름 (필수)  ");
      stationName = new JTextField("버스정류장 이름을 정확하게 써주세요", 20);
      stationLabel.setFont(new Font("휴먼편지체", Font.BOLD, 26));
      busPanel.add(stationLabel);
      busPanel.add(stationName);
      contentPane.add(busPanel);
      
      
      
      disPanel = new JPanel();
      JLabel disLabel = new JLabel("장애유무 (선택)    ");
      disLabel.setFont(new Font("휴먼편지체", Font.BOLD, 26));
      dis = new JCheckBox("장애인");
      disPanel.add(disLabel);
      disPanel.add(dis);
      contentPane.add(disPanel);
      dis.addItemListener(new ItemListener() {
         @Override
         public void itemStateChanged(ItemEvent arg0) {
            // TODO Auto-generated method stub
            if(arg0.getStateChange()==1) { 
               mButton.setEnabled(true);
               fButton.setEnabled(true);
               xButton.setEnabled(true);
               }else {
                  mButton.setEnabled(false);
                  fButton.setEnabled(false);
                  xButton.setEnabled(false);
               }
         }
      });
      
      genderPanel = new JPanel();
      JLabel genderLabel = new JLabel("성별 (장애 선택시)  ");
      genderLabel.setFont(new Font("휴먼편지체", Font.BOLD, 26));
      genderPanel.add(genderLabel);
      genderGroup.add(mButton);
      genderGroup.add(fButton);
      genderGroup.add(xButton);
      genderPanel.add(mButton);
      genderPanel.add(fButton);
      genderPanel.add(xButton);
      contentPane.add(genderPanel);
      
   
      listPanel = new JPanel(); 
      listPanel.setLayout(new FlowLayout());
      nearText = new JTextArea();
      scrollPane = new JScrollPane(listPanel);
      //listPanel.add(nearText);
      contentPane.add(scrollPane);
      //contentPane.add(listPanel);
      
//listPanel.setLayout(new GridLayout());

BoxLayout lp = new BoxLayout(listPanel, BoxLayout.Y_AXIS);
listPanel.setLayout(lp);

      
      
      buttonPanel = new JPanel();
      button = new JButton("화장실찾기");
      button.setBackground(Color.PINK);
      buttonPanel.add(button);
      contentPane.add(buttonPanel);
      list = new JButton[50];
      
      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
        	Component[] componentList = listPanel.getComponents();
        	for(Component c : componentList) {
        		if(c instanceof JButton) {
        			System.out.println("zzzzz");
        			
        			listPanel.remove(c);
        			
        			contentPane.revalidate();
        			contentPane.repaint();
        			}
        		
        	} 
        	
            String keyword = stationName.getText();
            List<busData> bus = null;
             try {
                bus = ApiExplorer.getXmlString(keyword);
             } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
             }
             
             String sql = "delete from near; delete from bus_stop; insert into bus_stop values(?,?,?,?) ON CONFLICT DO NOTHING";
             int numOfInst = 0;
             try {
                for(int i = 0; i < bus.size(); i++) {
                   PreparedStatement stmtBus = GetResult.conn.prepareStatement(sql);
                   stmtBus.setInt(1, bus.get(i).getStationId());
                   stmtBus.setString(2, bus.get(i).getStationName());
                   stmtBus.setFloat(3, bus.get(i).getLongi());
                   stmtBus.setFloat(4, bus.get(i).getLat());
                   stmtBus.executeUpdate();
                   System.out.println("sucess to save(bus_stop)["+(++numOfInst)+"]");
                }
             } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
             } 
             
             sql = "insert into near(select stationId, stationName, restName, restAd, "
                   + "earth_distance(ll_to_earth(bus_stop.lat, bus_stop.long), "
                   + "ll_to_earth(restroom.latitude, restroom.longitude)) as dist "
                   + "from bus_stop, restroom"
                   + ");"; 
             try {
               PreparedStatement stmt = GetResult.conn.prepareStatement(sql);
               stmt.executeUpdate();

             } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
             }            
             
             
             String query = null;
             
             if (xButton.isSelected()) {
                query = "select * from near, restroom where near.restName = restroom.restName and near.restAd = restroom.restAd"
                      + " and (disM_u >= 1 or disM_b >= 1 or disF >= 1) and dist < 200 order by dist;";
             }
             else if (mButton.isSelected()) {
                query = "select * from near, restroom where near.restName = restroom.restName and near.restAd = restroom.restAd" 
                     +" and disM_u > 0 and disM_b > 0 and dist < 200 order by dist;";
             }
             else if (fButton.isSelected()) {
                query = "select * from near, restroom where near.restName = restroom.restName and near.restAd = restroom.restAd" 
                      +" and disF > 0 and dist < 200 order by dist;";
             }
             else query = "select * from near where dist<200 order by dist";
             
             PreparedStatement stmt;
             ResultSet rs;
           
             //nearText.setText("");
             
            // list = new JButton[50];
             try {
                  stmt = GetResult.conn.prepareStatement(query);
                  rs = stmt.executeQuery(); 
                  int i =0;
                  while(rs.next()) {
                     int stationId = rs.getInt("stationId");
                     String stationName = rs.getString("stationName");
                     String restName = rs.getString("restName");
                     String restAd = rs.getString("restAd");
                     Float dist = rs.getFloat("dist");
                //nearText.append(stationId+" "+stationName+" "+restName+" "+restAd+" "+dist+"\n");   
                list[i] = new JButton(stationName+" / "+restName+", 주소: "+restAd+", 거리: "+dist+" m");
                list[i].setBackground(Color.white);
                list[i].setPreferredSize(new Dimension(600,35));
                list[i].addActionListener(new ActionListener() {

                  @Override
                  public void actionPerformed(ActionEvent e) {
                     // TODO Auto-generated method stub
                     String query = "select * from restroom where restName = ?";
                      PreparedStatement stmt;
                      ResultSet rs;
                      try {
                     stmt = GetResult.conn.prepareStatement(query);
                     
                     stmt.clearParameters();
                     stmt.setString(1,restName);
                     rs = stmt.executeQuery();
                     
                     rs.next();
                     
                     String restName = rs.getString("restName");
                     String restAd = rs.getString("restAd");
                     int disM_b = rs.getInt("disM_b");
                     int disM_u = rs.getInt("disM_u");
                     int disF = rs.getInt("disF");
                     String open = rs.getString("open");
                     
                     int gender=0;
                     if(mButton.isSelected())
                        gender = 1;
                     else if(fButton.isSelected())
                        gender = 2;
                     else gender = 3;
                     
                     result = new queryResult(restName, restAd, disM_b, disM_u, disF, open, gender);
                     }
                      
                      catch(SQLException ex){
                     }
                     
                  }
                   
                });
                System.out.println(i+"번쨰 버튼생성");
               listPanel.add(list[i]);
                i++;
                contentPane.revalidate();
                contentPane.repaint();
                  }
                  
                 
                  stmt = GetResult.conn.prepareStatement("delete from near;");
                  stmt.execute();
           
                } catch (SQLException e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
                }            
         }
      });
   }
}

// 결과창
class queryResult extends JDialog{
   

   JTextArea resultArea = new JTextArea();
   
   public queryResult(String restName, String restAd, int disM_b, int disM_u, int disF, String open, int gender) {
   
   
      resultArea.setFont(new Font("휴먼편지체", Font.BOLD, 26));
      
      resultArea.append("화장실명 :   "+restName+"\n");
      resultArea.append("주소 :   "+restAd+"\n");
      
      if(gender==1) {
         resultArea.append("장애남성용 대변기 수 :   "+disM_b+"\n");
         resultArea.append("장애남성용 소변기 수 :   "+disM_u+"\n");
      }else if(gender==2) {
         resultArea.append("장애여성용 대변기 수 :   "+disF+"\n");
      }else {
         resultArea.append("장애남성용 대변기 수 :   "+disM_b+"\n");
         resultArea.append("장애남성용 소변기 수 :   "+disM_u+"\n");
         resultArea.append("장애여성용 대변기 수 :   "+disF+"\n");
      }
   
      resultArea.append("개방시간 :   "+open);
      
      getContentPane().add(resultArea);
      
      
      
      this.setSize(700,500);
      //this.setModal(true);
      this.setVisible(true);
      setLocation(400, 300);
      this.setTitle("Restroom Information");
   }
}