import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.RescaleOp;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

//this class is used to pass the resultset from mainframe about the list of places/hotel/restaurants to addData of panel1 
//and pass reviews about a selected entity from centerpanel to addreviewdata of panel3
class passData      
{                   
	ResultSet mytemp;
	String stemp;
	String sdist;
	
	void setDataDb(ResultSet temp,String stemp,String sdist)
	{

		System.out.println("in passdata111");
		this.mytemp=temp;
		this.stemp=stemp;
		this.sdist=sdist;
		System.out.println("in passdata222 : "+mytemp);
		
	}
	ResultSet getDataDb()
	{
		System.out.println("\n IN GET DATA :: "+mytemp);
		return mytemp;
	}
	String getTable()
	{
		System.out.println("place/hotel/restuarant");
		return stemp;
	}
	String getDistrict()
	{
		System.out.println("district name:"+sdist);
		
		return sdist;
	}
}


//this the class of the slideshow whose object is created in the mainframe and is displayed in midpanel before login
//and after logout
class SlideShow extends JPanel
{
	JLabel pic;
	Timer tm;
	int x=0;
	 String[] list = {
             "Z:/java_work/TripAdisor/Images/BackGround/th (4).jpg",//0
             "Z:/java_work/TripAdisor/Images/BackGround/th (3).jpg",//1
             "Z:/java_work/TripAdisor/Images/BackGround/th (2).jpg",//2
             "Z:/java_work/TripAdisor/Images/BackGround/th (5).jpg",//3
             "Z:/java_work/TripAdisor/Images/BackGround/th (6).jpg",//4
             "Z:/java_work/TripAdisor/Images/BackGround/th (7).jpg",//5
             "Z:/java_work/TripAdisor/Images/BackGround/th (8).jpg"//6
           };
	
	 float[] opacity = {0.5f};
	
	 float[] offsets = new float[1];
	 RescaleOp rop = new RescaleOp(opacity, offsets, null);

	
	 public void SetImageSize(int i){
	        ImageIcon icon = new ImageIcon(list[i]);
	        Image img = icon.getImage();
	        Image newImg = img.getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_DEFAULT);
	       // Graphics g = newImg.getGraphics();
	        //((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	      // void Graphics2D.drawImage(img,rop,0,0);
	        ImageIcon newImc = new ImageIcon(newImg);
	        pic.setIcon(newImc);
	    }
	 
	 	SlideShow()
	 	{
	 		setLayout(null);
	 		 pic = new JLabel();
	        pic.setBounds(0,0,850,650);

		SetImageSize(6);
        //set a timer
 tm = new Timer(1500,new ActionListener() {

     @Override
     public void actionPerformed(ActionEvent e) {
         SetImageSize(x);
         x += 1;
         if(x >= list.length )
             x = 0; 
     }
 });
 add(pic);
 tm.start();

 
 setSize(1000,1000);
 
 
 setVisible(true);
	 	}
	 
}

//the mainframe of the application which has actionperformed on signin,signup,signout,login,register,
//ask for hotel/place/restaurant
class MainFrame extends JFrame implements ActionListener
{
	Container cont;
	MidPanel Midp;
	TopPanel Topp;
	BottomPanel Botp;
	 passData pd;
	
	
	
	MainFrame(String s)
	{
		super(s);
		cont = getContentPane();
		cont.setLayout(null);
		
		
		
		pd=new passData();
		Midp =new MidPanel(pd);
		Topp =new TopPanel();
		Botp= new BottomPanel();
		
		Topp.setBackground(Color.pink);
		Midp.setBackground(Color.white);
		Botp.setBackground(Color.black);
		
		
		Topp.setBounds(10,10,1900,190);
		Midp.setBounds(10,210,1900,690);
		Botp.setBounds(10,910,1900,80);
		
		Topp.login.addActionListener(this);
		Topp.register.addActionListener(this);
		Topp.logout.addActionListener(this);
		Midp.lgin.sub.addActionListener(this);
		Midp.lgin.can.addActionListener(this);
		Midp.regis.sub.addActionListener(this);
		Midp.regis.can.addActionListener(this);
	    Midp.centerp.upPan.h.askHotel.addActionListener(this);
		Midp.centerp.upPan.p.askPlace.addActionListener(this);
		Midp.centerp.upPan.r.askRestaurant.addActionListener(this);
		
		cont.add(Topp);cont.add(Midp);cont.add(Botp);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		String s=ae.getActionCommand();
		String us,pa;
		
		if(s.equals("SIGNIN"))
		{
			Midp.lgin.setVisible(true);
			Midp.regis.setVisible(false);
			Midp.lgout.setVisible(false);
			Midp.ss.setVisible(false);
			
		}
		else if(s.equals("SIGNUP"))
		{
			Midp.lgin.setVisible(false);
			Midp.regis.setVisible(true);
			Midp.lgout.setVisible(false);
			Midp.ss.setVisible(false);
			
		}
		else if(s.equals("SIGNOUT"))
		{
			Midp.centerp.setVisible(false);
			Midp.regis.setVisible(false);
			Midp.lgout.setVisible(false);
			Midp.ss.setVisible(true);
			Topp.logout.setVisible(false);
			Topp.register.setVisible(true);
			Topp.login.setVisible(true);

		}
		
	
		if(s.equals("LOGIN"))
		{
		    us=Midp.lgin.tus.getText();
		    pa=Midp.lgin.tpa.getText();
		    System.out.println(""+us+""+pa);
		    int flag=0;
		    String user="";
		    String pass="";
			
			try
			{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
		    Statement st=con.createStatement();
		    ResultSet rs=st.executeQuery("select * from PROJECT1.USERTABLE");
		    while(rs.next())
		    {
		    	System.out.println("in while");
		    	user=rs.getString(2);
		    	System.out.println(user);
		    	pass=rs.getString(3);
		    	System.out.println(pass);
		    	if(user.equals(us)&& pass.equals(pa))
		    	{
		    		flag=1;
		    		break;
		    	}	
		    }
		    
		    if(flag==1)
		    {
		    	setBackground(Color.cyan);
		    	Midp.lgin.tpa.setText("");
		    	Midp.lgin.tus.setText("");
		    	Midp.lgin.setVisible(false);
				Topp.login.setVisible(false);
				Topp.register.setVisible(false);
				Topp.logout.setVisible(true);
				Midp.centerp.setVisible(true);
		    	//lgin.setVisible(false);
		    	 
		    }
		    else if(flag==0)
		    {
		    	Midp.lgin.invalid.setVisible(true);
		    }
		    st.close();
		    con.close();
			}
			catch(Exception e)
			{
				System.out.println(""+e.getMessage());
			}
		}
		else if(s.equals("RESET"))
		{
			Midp.lgin.tus.setText("");
			Midp.lgin.tpa.setText("");
		}
		else if(s.equals("REGISTER"))
		{
			us=Midp.regis.tus.getText();
			pa=Midp.regis.tpa.getText();
			
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");	
			    Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
		        Statement st=con.createStatement();
		        ResultSet rs=st.executeQuery("select count(*) from PROJECT1.USERTABLE");
		        rs.next();
		        int count=rs.getInt(1);
			     count++;
			st.executeUpdate("insert into PROJECT1.USERTABLE values("+count+",'"+us+"','"+pa+"',null)");
			Midp.regis.tus.setText("");
			Midp.regis.tpa.setText(" ");
			st.close();
			con.close();
			
			Midp.regis.setVisible(false);
			Midp.lgin.setVisible(true);
			}
			catch(Exception e)
			{
				System.out.println("ERROR IN INSERT:"+e.getMessage());
			}
		}
		else if(s.equals("CANCEL"))
		{
			Midp.regis.tus.setText("");
			Midp.regis.tpa.setText("");
		}
		//lowerpanel of the centerpanel is set visible true
		if(s.equals("Ask For a Hotel"))
		{
			Midp.centerp.lowPan.setVisible(true);
			Midp.centerp.lowPan.HotelJsp.setVisible(true);
			Midp.centerp.lowPan.setBackground(Color.blue);
			//Midp.centerp.lowPan.HotelJsp.setVisible(true);
			Midp.centerp.reviewPan.setVisible(false);
			
			try
			{
				String s1;int flag1=0;String d_name="";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
			System.out.println("..........");
		    Statement st=con.createStatement();
		    ResultSet rs=st.executeQuery("select DIST_NAME from PROJECT1.HOTELTABLE");
		    System.out.println("./////////");
		    s1=Midp.centerp.upPan.h.textHotel.getText();	
		    s1=s1.toUpperCase();
		    System.out.println("ahinyaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaan            "+s1);
		    while(rs.next())
		    {
		    	String in=rs.getString(1);
		    	in=in.toUpperCase();
		      if(s1.contains(in))
		      {
		    	  d_name=rs.getString(1);
		    	  flag1=1;
		    	  break;
		      }
		     
		    }
		    System.out.println("distname:"+d_name);
		    rs.close();
		    ResultSet rs1=st.executeQuery("select * from PROJECT1.HOTELTABLE where DIST_NAME='"+d_name+"'");
		    
		   // rs1.next();
		   // System.out.println(rs1.getString(1));
		    
		  
		    
		    	//Midp.centerp.lowPan.p.pa[0].name.setText("hello");
		    	//Midp.centerp.lowPan.p.pa[i].address.setText((rs1.getString(5)));
		    	//Midp.centerp.lowPan.p.pa[i].avgRating.setText((rs1.getString(6)));
		  System.out.println("exception");
		  System.out.println(d_name);
		   pd.setDataDb(rs1,"Hotel",d_name);
		   System.out.println("here");
		   Midp.centerp.lowPan.p1.addData();
		    st.close();
		    con.close();
			}
			catch(Exception e)
			{
				System.out.println("\n\n DIST_NAME ERRROR : "+e.getMessage());
			}
			
			
			
			
			
		}
		else if(s.equals("Ask For a Place"))
		{
			Midp.centerp.lowPan.setVisible(true);
			Midp.centerp.lowPan.PlaceJsp.setVisible(true);
			Midp.centerp.lowPan.setBackground(Color.black);
			//Midp.centerp.lowPan.PlaceJsp.setVisible(true);
			Midp.centerp.reviewPan.setVisible(false);
			
			
			try
			{
				String s1;int flag1=0;String d_name="";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
			System.out.println("..........");
		    Statement st=con.createStatement();
		    ResultSet rs=st.executeQuery("select DIST_NAME from PROJECT1.PLACETABLE");
		    System.out.println("./////////");
		    s1=Midp.centerp.upPan.p.textPlace.getText();	
			s1=s1.toUpperCase();
		    while(rs.next())
		    {
		    	String in=rs.getString(1);
		    	in=in.toUpperCase();
		      if(s1.contains(in))
		      {
		    	  d_name=rs.getString(1);
		    	  flag1=1;
		    	  break;
		      }
		     
		    }
		    System.out.println("distname:"+d_name);
		    rs.close();
		    ResultSet rs1=st.executeQuery("select * from PROJECT1.PLACETABLE where DIST_NAME='"+d_name+"'");
		    
		   // rs1.next();
		   // System.out.println(rs1.getString(1));
		    
		  
		    
		    	//Midp.centerp.lowPan.p.pa[0].name.setText("hello");
		    	//Midp.centerp.lowPan.p.pa[i].address.setText((rs1.getString(5)));
		    	//Midp.centerp.lowPan.p.pa[i].avgRating.setText((rs1.getString(6)));
		  System.out.println("exception");
		  System.out.println(d_name);
		   pd.setDataDb(rs1,"Place",d_name);
		   System.out.println("here");
		   Midp.centerp.lowPan.p2.addData();
		    st.close();
		    con.close();
			}
			catch(Exception e)
			{
				System.out.println("\n\n DIST_NAME ERRROR : "+e.getMessage());
			}
			
			
			
			

		}
		else if(s.equals("Ask For a Restaurant"))
		{
			Midp.centerp.lowPan.setVisible(true);
			Midp.centerp.lowPan.RestaurantJsp.setVisible(true);
			Midp.centerp.lowPan.setBackground(Color.yellow);
		 	//Midp.centerp.lowPan.RestaurantJsp.setVisible(true);
			Midp.centerp.reviewPan.setVisible(false);
			
			
			try
			{
				String s1;int flag1=0;String d_name="";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
			System.out.println("..........");
		    Statement st=con.createStatement();
		    ResultSet rs=st.executeQuery("select DIST_NAME from PROJECT1.RESTUARANTTABLE");
		    System.out.println("./////////");
		    s1=Midp.centerp.upPan.r.textRestaurant.getText();
			s1=s1.toUpperCase();
		    while(rs.next())
		    {
		    	String in=rs.getString(1);
		    	in=in.toUpperCase();
		      if(s1.contains(in))
		      {
		    	  d_name=rs.getString(1);
		    	  flag1=1;
		    	  break;
		      }
		     
		    }
		    System.out.println("distname:"+d_name);
		    rs.close();
		    ResultSet rs1=st.executeQuery("select * from PROJECT1.RESTUARANTTABLE where DIST_NAME='"+d_name+"'");
		    
		   // rs1.next();
		   // System.out.println(rs1.getString(1));
		    
		  
		    
		    	//Midp.centerp.lowPan.p.pa[0].name.setText("hello");
		    	//Midp.centerp.lowPan.p.pa[i].address.setText((rs1.getString(5)));
		    	//Midp.centerp.lowPan.p.pa[i].avgRating.setText((rs1.getString(6)));
		  System.out.println("exception");
		  System.out.println(d_name);
		   pd.setDataDb(rs1,"Restuarant",d_name);
		   System.out.println("here");
		   Midp.centerp.lowPan.p3.addData();
		    st.close();
		    con.close();
			}
			catch(Exception e)
			{
				System.out.println("\n\n DIST_NAME ERRROR : "+e.getMessage());
			}
			
			
			
			
		}
	 
	}
}


class TopPanel extends JPanel
{
	JLabel name;
	JButton login;
	JButton register;
	JButton logout;
	JButton j;
	Font f;
	TopPanel()
	{
		setLayout(null);
		f=new Font("COPPERPLATE GOTHIC BOLD",Font.PLAIN,80);
		
		name=new JLabel("TRIPLINGS");
		name.setFont(f);
		name.setForeground(new Color(154,8,66));
		login=new JButton("SIGNIN");
		register=new JButton("SIGNUP");
		logout=new JButton("SIGNOUT");
		
		name.setBounds(50,20,600,200);
		login.setBounds(1600,75,100,50);
		register.setBounds(1750,75,100,50);
		logout.setBounds(1750,75,100,50);
		//j.setBounds(1000,75,100,50);
		//j.setComponentZOrder(j, 5);
		
		add(login);add(register);add(logout);
		add(name);
		
	}
}
class MidPanel extends JPanel 
{
	Login lgin;
	Register regis;
	Logout lgout;
	CenterPanel centerp;
	SlideShow ss;
	passData pd;
	
	MidPanel(passData pd)
	{   
		
		setLayout(null);
		this.pd=pd;
		
	
		lgin=new Login();
		lgin.setBounds(500,100,800,400);
		lgin.setVisible(false);
		
		regis=new Register();
		regis.setBounds(500,100,800,400);
		regis.setVisible(false);
		
		lgout=new Logout();
		lgout.setVisible(false);
		
		centerp = new CenterPanel(pd);
		centerp.setBounds(0, 0, 1950,690);
		centerp.setVisible(false);
		
		ss=new SlideShow();
		ss.setBounds(500,20,850,650);
		//ss.setVisible(true);
	
		
		
		add(lgin);add(regis);add(lgout); add(centerp); add(ss);
	}
	
	
}
class BottomPanel extends JPanel
{
	JLabel img;
	BottomPanel()
	{
		setLayout(null);	
		img=new JLabel();
		img.setIcon(new ImageIcon("Z:\\java_work\\TripAdisor\\Images\\AboutUs.png"));
		img.setBounds(50,5,20,20);
		
		add(img);
	}
}

class Login extends JPanel
{
	JLabel usname,passname;
	JLabel formName,invalid;
	JTextField tus,tpa;
	JButton sub,can;
	Font f;
	Login()
	{
		setLayout(null);
		setBackground(Color.pink);
		usname=new JLabel("UserName: ");
		passname=new JLabel("PassWord: ");
		formName=new JLabel("SIGN IN FORM");
		invalid=new JLabel("InValid Credentials");
		invalid.setVisible(false);
		
		f=new Font("CANDARA",Font.PLAIN,20);
		
		tus=new JTextField();
		tpa=new JTextField();
		
		sub=new JButton("LOGIN");
		can=new JButton("RESET");
		
		usname.setForeground(new Color(154,8,66));
		passname.setForeground(new Color(154,8,66));
		formName.setForeground(new Color(154,8,66));
		invalid.setForeground(new Color(154,8,66));
		
		usname.setFont(f);
		passname.setFont(f);
		tus.setFont(f);
		tpa.setFont(f);
		sub.setFont(f);
		can.setFont(f);
		
		f=new Font("COPPERPLATE GOTHIC BOLD",Font.BOLD,25);
		formName.setFont(f);
		
		usname.setBounds(160,100,130,50);
		passname.setBounds(160,170,130,50);
		formName.setBounds(270,30,300,50);
		invalid.setBounds(350,370,150,30);
		
		tus.setBounds(280,100,250,50);
		tpa.setBounds(280,170,250,50);
		
		sub.setBounds(250,300,100,50);
		can.setBounds(400,300,100,50);
		
		
		add(usname);add(passname);add(tus);add(tpa);add(sub);add(can);add(formName);add(invalid);
	}
}
class Register extends JPanel
{

	JLabel usname,passname;
	JLabel formName;
	JTextField tus,tpa;
	JButton sub,can;
	Font f;
	Register()
	{
		setLayout(null);
		setBackground(Color.pink);
		
		usname=new JLabel("UserName: ");
		passname=new JLabel("PassWord: ");
		formName=new JLabel("SIGN UP FORM");
		
		f=new Font("CANDARA",Font.PLAIN,20);
		
		tus=new JTextField();
		tpa=new JTextField();
		
		sub=new JButton("REGISTER");
		can=new JButton("CANCEL");
		
		
		usname.setForeground(new Color(154,8,66));
		passname.setForeground(new Color(154,8,66));
		formName.setForeground(new Color(154,8,66));
		
		usname.setFont(f);
		passname.setFont(f);
		tus.setFont(f);
		tpa.setFont(f);
		sub.setFont(f);
		can.setFont(f);
		

		f=new Font("COPPERPLATE GOTHIC BOLD",Font.BOLD,25);
		formName.setFont(f);
		
		
		usname.setBounds(160,100,130,50);
		passname.setBounds(160,170,130,50);
		formName.setBounds(270,30,300,50);
		
		tus.setBounds(280,100,250,50);
		tpa.setBounds(280,170,250,50);
		sub.setBounds(200,300,200,50);
		can.setBounds(450,300,200,50);
		
		add(usname);add(passname);add(tus);add(tpa);add(sub);add(can);add(formName);
	}
}
class Logout extends JPanel
{
	Logout()
	{
		setLayout(null);
	}
}






class CenterPanel extends JPanel implements ActionListener, MouseListener
{
    UpperPanel upPan;
	LowerPanel lowPan;
	ReviewPanel reviewPan;
	passData pd,ps2;
	int id,did;
	
	CenterPanel(passData pd)
	{
		  setLayout(null);
		this.pd=pd;
		
		 upPan=new UpperPanel(); 
		 lowPan=new LowerPanel(pd);
		 ps2=new passData();
		 reviewPan = new ReviewPanel(pd,ps2);
		 reviewPan.setVisible(false);
		 
		 upPan.setLayout(null);
		 lowPan.setLayout(null);
		 reviewPan.setLayout(null);
		 
		 upPan.setBounds(0,0,1950,130);
		 lowPan.setBounds(0,140,1950,560);
		 reviewPan.setBounds(0,155,1950,560);
		 
		 upPan.setBackground(Color.gray);
		// lowPan.setBackground(Color.red);
		 reviewPan.setBackground(Color.pink);
		 
		
		 add(upPan); 
		 add(lowPan);
		add(reviewPan);
			
		upPan.hotel.addActionListener(this);
		 upPan.place.addActionListener(this);
		 upPan.restaurant.addActionListener(this);
		 reviewPan.add.addActionListener(this);
		 for(int i=0;i<7;i++)
		 {
			 lowPan.p1.pa[i].name.addMouseListener(this);
		 }
		 for(int i=0;i<7;i++)
		 {
			 lowPan.p2.pa[i].name.addMouseListener(this);
		 }
		 for(int i=0;i<7;i++)
		 {
			 lowPan.p3.pa[i].name.addMouseListener(this);
		 }
		 for(int i=0;i<8;i++)
		 {
			 reviewPan.p3.pa[i].like.addMouseListener(this);
		 }
	}
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		String s=ae.getActionCommand();
		
			if(s.equals("HOTELS"))
			{
			upPan.h.setVisible(true);	
			upPan.p.setVisible(false);
			upPan.r.setVisible(false);
			lowPan.setVisible(true);
			lowPan.PlaceJsp.setVisible(false);
			lowPan.RestaurantJsp.setVisible(false);
		
			}
			else if(s.equals("PLACES"))
			{
				upPan.p.setVisible(true);
				upPan.h.setVisible(false);
				upPan.r.setVisible(false);
				lowPan.setVisible(true);
				lowPan.HotelJsp.setVisible(false);
				lowPan.RestaurantJsp.setVisible(false);
				
			}
			else if(s.equals("RESTAURANTS"))
			{
				upPan.r.setVisible(true);
				upPan.h.setVisible(false);
				upPan.p.setVisible(false);
				lowPan.setVisible(true);
				lowPan.PlaceJsp.setVisible(false);
				lowPan.HotelJsp.setVisible(false);
				
			}
		
	}
	@Override
	public void mouseClicked(MouseEvent me) {
	      JLabel temp=null;
	      temp=(JLabel)me.getSource();
	      String s=temp.getText();
	      
	      
	      
	      
	      lowPan.setVisible(false);
	      reviewPan.setVisible(true);
	      
	      System.out.println(s);
	      try {
	    	  Class.forName("oracle.jdbc.driver.OracleDriver");
	    	  System.out.println("driver connected");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
				System.out.println("database connected");
			    Statement st2=con.createStatement();
			    String tablename=pd.getTable();
			    System.out.println(tablename);
			    String DistName=pd.getDistrict();
			    System.out.println(DistName);
			    
			    if(tablename.equals("Hotel"))
			    {
			    	ResultSet rs1=st2.executeQuery("Select * from PROJECT1.HOTELTABLE where HOTEL_NAME='"+s+"' and DIST_NAME='"+DistName+"'");
			        System.out.println(rs1);
			        rs1.next();
			    	id=rs1.getInt(1);
			    	did=rs1.getInt(2);
			    	System.out.println("id "+id+"did "+did);
			    	reviewPan.nname.setText(s);
				    reviewPan.address.setText(rs1.getString(5));
				    
				    File f = new File(rs1.getString(7));
					 Image img1= ImageIO.read(f);
					 Image ri= img1.getScaledInstance(200,200,Image.SCALE_DEFAULT);
					 
				    reviewPan.imageLab.setIcon(new ImageIcon(ri));
				    reviewPan.avgRating.setText(""+rs1.getInt(6));
				    rs1.close();
				    reviewPan.RJsp.setVisible(true);
			    	
			   ResultSet rs2=st2.executeQuery("select * from PROJECT1.HOTELREVIEW where HOTEL_ID="+id+" and DIST_ID="+did+" order by LIKES desc");
			   System.out.println(rs2);
			  
			   ps2.setDataDb(rs2, "Hotel", "gnh");
			   System.out.println("call addreviewData");
			  reviewPan.p3.addReviewData();
			   System.out.println("reviews added");
			    rs2.close();
			    
			      
			   
			    }
			    else if(tablename.equals("Place"))
			    {
			    	ResultSet rs1=st2.executeQuery("Select * from PROJECT1.PLACETABLE where PLACE_NAME='"+s+"' and DIST_NAME='"+DistName+"'");
			        System.out.println(rs1);
			        rs1.next();
			    	id=rs1.getInt(1);
			    	did=rs1.getInt(2);
			    	System.out.println("id "+id+"did "+did);
			    	reviewPan.nname.setText(s);
				    reviewPan.address.setText(rs1.getString(5));
				    
				    File f = new File(rs1.getString(7));
					 Image img1= ImageIO.read(f);
					 Image ri= img1.getScaledInstance(200,200,Image.SCALE_DEFAULT);
				    reviewPan.imageLab.setIcon(new ImageIcon(ri));
				    
				    reviewPan.avgRating.setText(""+rs1.getInt(6));
				    rs1.close();
				    reviewPan.RJsp.setVisible(true);
			    	
			   ResultSet rs2=st2.executeQuery("select * from PROJECT1.PLACEREVIEW where PLACE_ID="+id+" and DIST_ID="+did);
			   System.out.println(rs2);
			  
			   ps2.setDataDb(rs2, "Place", "gnh");
			   System.out.println("call addreviewData");
			  reviewPan.p3.addReviewData();
			   System.out.println("reviews added");
			    rs2.close();
			    }
			    else if(tablename.equals("Restuarant"))
			    {
			    	ResultSet rs1=st2.executeQuery("Select * from PROJECT1.RESTUARANTTABLE where RESTUARANT_NAME='"+s+"' and DIST_NAME='"+DistName+"'");
			        System.out.println(rs1);
			        rs1.next();
			    	id=rs1.getInt(1);
			    	did=rs1.getInt(2);
			    	System.out.println("id "+id+"did "+did);
			    	reviewPan.nname.setText(s);
				    reviewPan.address.setText(rs1.getString(5));
				    
				    File f = new File(rs1.getString(7));
					 Image img1= ImageIO.read(f);
					 Image ri= img1.getScaledInstance(200,200,Image.SCALE_DEFAULT);
					 
				    reviewPan.imageLab.setIcon(new ImageIcon(ri));
				    reviewPan.avgRating.setText(""+rs1.getInt(6));
				    rs1.close();
				    reviewPan.RJsp.setVisible(true);
			    	
			   ResultSet rs2=st2.executeQuery("select * from PROJECT1.RESTUARANTREVIEW where RESTUARANT_ID="+id+" and DIST_ID="+did);
			   System.out.println(rs2);
			  
			   ps2.setDataDb(rs2, "Restuarant", "gnh");
			   System.out.println("call addreviewData");
			  reviewPan.p3.addReviewData();
			   System.out.println("reviews added");
			    rs2.close();
			    }
			    
			   st2.close();
			   con.close();
			    
			
			    
		     } 
	      
	      catch (Exception e) 
	      {
			e.printStackTrace();
		  } 
			
	      
	      
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		setForeground(Color.white);
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		setForeground(Color.black);
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	
		
	}
}

class Hotel extends JPanel implements ActionListener
{
	JTextField textHotel;
	JButton askHotel,addHotel;
	Font f;
	
	Hotel()
	{
		setLayout(null);
		
		textHotel=new JTextField();
		
		if (textHotel.getText().length() == 0) {  
			textHotel.setText("Enter Text");  
			textHotel.setForeground(new Color(150, 150, 150));  
		}  

		textHotel.addFocusListener(new FocusListener() {  

		    @Override  
		    public void focusGained(FocusEvent e) {  
		    	textHotel.setText("");  
		    	textHotel.setForeground(new Color(50, 50, 50));  
		    }  

		    @Override  
		    public void focusLost(FocusEvent e) { 

		        if (textHotel.getText().length() == 0) {  
		        	textHotel.setText("Enter Text");  
		        	textHotel.setForeground(new Color(150, 150, 150));  
		        }  

		    }  
		});
		askHotel=new JButton("Ask For a Hotel");
		addHotel=new JButton("add Hotel");
		f=new Font("CANDARA",Font.BOLD,15);
		
		askHotel.setFont(f);
		addHotel.setFont(f);
		
		addHotel.setBounds(550,50,130,50);
		textHotel.setBounds(700,50,250,50);
		askHotel.setBounds(1000,50,150,50);
		addHotel.addActionListener(this);
		
		add(askHotel); add(textHotel);add(addHotel);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		 
		
	}
	
}

class Place extends JPanel
{
	JTextField textPlace;
	JButton askPlace;
	Font f;
	Place()
	{
	setLayout(null);
	
	textPlace=new JTextField();
	if (textPlace.getText().length() == 0) {  
		textPlace.setText("Enter Text");  
		textPlace.setForeground(new Color(150, 150, 150));  
	}  

	textPlace.addFocusListener(new FocusListener() {  

	    @Override  
	    public void focusGained(FocusEvent e) {  
	    	textPlace.setText("");  
	    	textPlace.setForeground(new Color(50, 50, 50));  
	    }  

	    @Override  
	    public void focusLost(FocusEvent e) { 

	        if (textPlace.getText().length() == 0) {  
	        	textPlace.setText("Enter Text");  
	        	textPlace.setForeground(new Color(150, 150, 150));  
	        }  

	    }  
	});
	askPlace=new JButton("Ask For a Place");
	f=new Font("CANDARA",Font.BOLD,15);
	
	askPlace.setFont(f);
	
	textPlace.setBounds(700,50,250,50);
	askPlace.setBounds(1000,50,150,50);
	add(askPlace);add(textPlace);
	}
}

class Restaurant extends JPanel
{
	JTextField textRestaurant;
	JButton askRestaurant;
	Font f;
	Restaurant()
	{
		setLayout(null);
		
		textRestaurant=new JTextField();
		
		if (textRestaurant.getText().length() == 0) {  
			textRestaurant.setText("Enter Text");  
			textRestaurant.setForeground(new Color(150, 150, 150));  
		}  

		textRestaurant.addFocusListener(new FocusListener() {  

		    @Override  
		    public void focusGained(FocusEvent e) {  
		    	textRestaurant.setText("");  
		    	textRestaurant.setForeground(new Color(50, 50, 50));  
		    }  

		    @Override  
		    public void focusLost(FocusEvent e) { 

		        if (textRestaurant.getText().length() == 0) {  
		        	textRestaurant.setText("Enter Text");  
		        	textRestaurant.setForeground(new Color(150, 150, 150));  
		        }  

		    }  
		});
		
		askRestaurant=new JButton("Ask For a Restaurant");
		f=new Font("CANDARA",Font.BOLD,15);
		
		askRestaurant.setFont(f);
		
		textRestaurant.setBounds(700,50,250,50);;
		askRestaurant.setBounds(1000,50,200,50);
		add(askRestaurant);add(textRestaurant);
	}
}

class UpperPanel extends JPanel 
{
	JButton hotel,place,restaurant;
	Font f;
	Place p;
	Hotel h;
	Restaurant r;

	
	UpperPanel()
	{
		setLayout(null);
		
		f=new Font("CANDARA",Font.BOLD,25);
		hotel=new JButton("HOTELS");
		place=new JButton("PLACES");
		restaurant=new JButton("RESTAURANTS");
		
		hotel.setFont(f); place.setFont(f); restaurant.setFont(f);
		
		hotel.setBounds      (50, 50,150 ,60);
		place.setBounds     (220, 50,150 ,60);
		restaurant.setBounds(400, 50,250,60);
			
		p=new Place();
		p.setBackground(Color.gray);
		p.setBounds(600, 0, 1350, 200);
		p.setVisible(false);
		
		r=new Restaurant();
		r.setBackground(Color.gray);
		r.setBounds(600, 0, 1350, 200);
		r.setVisible(false);
		
		h=new Hotel();
		h.setBackground(Color.gray);
		h.setBounds(600, 0, 1350, 200);
		h.setVisible(false);
		
		
		add(hotel); add(place); add(restaurant); add(p); add(h); add(r);
		
	}

}

class LowerPanel extends JPanel 
{
	JTable HotelTable=null;
	JScrollPane HotelJsp=null;
	JTable PlaceTable=null;
	JScrollPane PlaceJsp=null;
	JTable RestaurantTable=null;
	JScrollPane RestaurantJsp=null;
	Panel1 p1,p2,p3;
	passData pd;
	
	LowerPanel(passData pd)
	{
		setLayout(null);
		setBackground(Color.red);
		HotelTable=new JTable();
		this.pd=pd;
		p1=new Panel1(pd);
		p2=new Panel1(pd);
		p3=new Panel1(pd);
		
		HotelJsp=new JScrollPane(p1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//HotelJsp=new JScrollPane(p);
		HotelJsp.setBounds(0,0,1900,550);
		HotelJsp.setVisible(false);
		//HotelJsp.getVerticalScrollBar();
		
		//HotelJsp.setViewportView(p);
		
		
		
		PlaceTable=new JTable();
		PlaceJsp=new JScrollPane(p2,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//PlaceJsp=new JScrollPane(PlaceTable);
		PlaceJsp.setBounds(0,0,1900,550);
		PlaceJsp.setVisible(false);
		
		RestaurantTable=new JTable();
		RestaurantJsp=new JScrollPane(p3,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//RestaurantJsp=new JScrollPane(RestaurantTable);
		RestaurantJsp.setBounds(0,0,1900,550);
		RestaurantJsp.setVisible(false);

		
		add(HotelJsp);/* add(HotelTable);*/ add(RestaurantJsp); add(RestaurantTable); add(PlaceJsp); add(PlaceTable);
	}
}
class Panel1 extends JPanel 
{
	Panel2 pa[];
	
	Font f;
    passData pd;
    ResultSet data;
	int no;
	int panel_hight;
	
	Panel1(passData pd)
	{ 
		no=7;
	    this.pd=pd;
	   
	   
	    panel_hight=(no*350)+150;
		pa=new Panel2[no];

	setPreferredSize(new Dimension(1950,panel_hight));
	System.out.println("cvvxv");
	for(int i=0;i<no;i++)
	{
		pa[i]=new Panel2();
		pa[i].setLayout(null);
		pa[i].setVisible(true);
	    pa[i].setPreferredSize(new Dimension(1600,300));		
        pa[i].setBackground(Color.pink);
       // pa[i].name.addMouseListener(this);
        
       

		add(pa[i]);   
	}
	}
	void addData()
	{
		
		System.out.println("reached here");
		data=pd.getDataDb();
		
		System.out.println("data recieved");
		int i=0;
		 try 
        {
			 System.out.println("innn111 : "+data);
			 while(data.next())
				{
				 File f = new File(data.getString(7));
				 Image img1= ImageIO.read(f);
				 Image ri= img1.getScaledInstance(480,280,Image.SCALE_DEFAULT);
				 pa[i].img.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
				 
				 System.out.println("in222");
				   pa[i].img.setIcon(new ImageIcon(ri));
				 	pa[i].name.setText(data.getString(4));
				 	pa[i].address.setText(data.getString(5));
				 	pa[i].avgRating.setText(data.getString(6));
				 	i++;
				}
		}
        catch (Exception e) 
		{
			
			e.printStackTrace();
		}
	}
	
}


class Panel2 extends JPanel
{

	JLabel img;
	JLabel name;
	JLabel avgRating;
	JLabel address;
	Font f;
	Panel2()
	{   
		img = new JLabel("");
		name= new JLabel(" HOTEL NAME ");
		avgRating = new JLabel(" * * * * * ");
		address = new JLabel("11,SAGAR ROW HOUSE,SATELIGHT ROAD,MOTA VARCHHA");
		
		 f=new Font("CANDARA",Font.BOLD,35);
		 img.setFont(f);
		 name.setFont(f);
		 f=new Font("CANDARA",Font.PLAIN,25);
		 avgRating.setFont(f);
		 address.setFont(f);
		 
	    img.setBounds(10,10,400,280);
	    name.setBounds(500,10,1000,80);
	    address.setBounds(500,100,1000,80);
	    avgRating.setBounds(500,200,1000,80 );
	        
	    add(img); add(name); add(address);  add(avgRating); 
	    
	}
}


class ReviewPanel extends JPanel implements ActionListener
{
   String	ReviewerName,R;
   JLabel  imageLab,nname,avgRating,address;
   JTextArea userReview;
   JButton add;
   Font f;
   JScrollPane RJsp=null;
   panel3 p3;
   passData ps2,pd;
   
	ReviewPanel(passData pd,passData ps2)
	{
		setLayout(null);
		this.ps2=ps2;
		this.pd=pd;
		imageLab= new JLabel( new ImageIcon("src\\images\\skypedp.jpg") );
		nname= new JLabel ("BARODA");
		avgRating= new JLabel (" * * * * * ");
		address = new JLabel("owiehjrfbndjsiwolskdjhfbvcnxmkswieurhfdjskwjejdekw");
		p3=new panel3(pd,ps2,this);
		RJsp=new JScrollPane(p3,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		RJsp.setVisible(false);
		
		userReview = new JTextArea();
		add = new JButton("ADD IT");
		
        f=new Font("CANDARA",Font.BOLD,15);
		
		imageLab.setBounds(10,80,200,200);
		nname.setBounds(10,20,500,50);
		avgRating.setBounds(10,280,50,50);
		address.setBounds(220,280,350,70);
		userReview.setBounds(30,360,600,100);
		add.setBounds(400,470,120,30);
		RJsp.setBounds(700,0,1200,560);
		
		
		
		imageLab.setFont(f);
		avgRating.setFont(f);
        userReview.setFont(f);
		add.setFont(f);
		
		f=new Font("CANDARA",Font.BOLD,25);
		nname.setFont(f);
		
		f=new Font("CANDARA",Font.BOLD,25);
		avgRating.setFont(f);
		
		add.addActionListener(this);
		
	
		add(imageLab);
		add(nname);
		add(avgRating);
		add(userReview);
		add(address);
		add(add);
		add(RJsp);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 System.out.println("driver connected of review Panel");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
				System.out.println("database connected");
			    Statement st2=con.createStatement();
			    String tablename=pd.getTable();
			    System.out.println(tablename);
			    String DistName=pd.getDistrict();
			    System.out.println(DistName);
			    int hid,did;
			    
			    if(tablename.equals("Hotel"))
			    {
			    	System.out.println("name ma hotel nu naam"+nname.getText());
				   String newreview=userReview.getText();
				   ResultSet rs1=st2.executeQuery("Select * from PROJECT1.HOTELTABLE where HOTEL_NAME='"+nname.getText()+"' and DIST_NAME='"+DistName+"'");
			        System.out.println(rs1);
			        rs1.next();
			    	hid=rs1.getInt(1);
			    	System.out.println(hid);
			    	did=rs1.getInt(2);
			    	System.out.println(did);
				   st2.executeUpdate("insert into PROJECT1.HOTELREVIEW values(1,"+hid+","+did+",'"+newreview+"',1)");
				   userReview.setText("");
				   ResultSet rs2=st2.executeQuery("select * from PROJECT1.HOTELREVIEW where HOTEL_ID="+hid+" and DIST_ID="+did+" order by LIKES desc");
				   System.out.println(rs2);
				  
				   ps2.setDataDb(rs2, "Hotel", "gnh");
				   System.out.println("call addreviewData");
				  p3.addReviewData();
				   System.out.println("reviews added");
				    rs2.close();
				   
				 
			    }
			    else if(tablename.equals("Place"))
			    {
			    	System.out.println("name ma hotel nu naam"+nname.getText());
					   String newreview=userReview.getText();
					   ResultSet rs1=st2.executeQuery("Select * from PROJECT1.PLACETABLE where PLACE_NAME='"+nname.getText()+"' and DIST_NAME='"+DistName+"'");
				        System.out.println(rs1);
				        rs1.next();
				    	hid=rs1.getInt(1);
				    	System.out.println(hid);
				    	did=rs1.getInt(2);
				    	System.out.println(did);
					   st2.executeUpdate("insert into PROJECT1.PLACEREVIEW values(1,"+hid+","+did+",'"+newreview+"',1)");
					   userReview.setText("");
					   
					   ResultSet rs2=st2.executeQuery("select * from PROJECT1.PLACEREVIEW where PLACE_ID="+hid+" and DIST_ID="+did+" order by LIKES desc");
					   System.out.println(rs2);
					   
					   ps2.setDataDb(rs2, "Place", "gnh");
					   System.out.println("call addreviewData");
					  p3.addReviewData();
					   System.out.println("reviews added");
					    rs2.close();
			    }
			    else if(tablename.equals("Restuarant"))
			    {
			    	System.out.println("name ma hotel nu naam"+nname.getText());
					   String newreview=userReview.getText();
					   ResultSet rs1=st2.executeQuery("Select * from PROJECT1.RESTUARANTTABLE where RESTUARANT_NAME='"+nname.getText()+"' and DIST_NAME='"+DistName+"'");
				        System.out.println(rs1);
				        rs1.next();
				    	hid=rs1.getInt(1);
				    	System.out.println(hid);
				    	did=rs1.getInt(2);
				    	System.out.println(did);
					   st2.executeUpdate("insert into PROJECT1.RESTUARANTREVIEW values(1,"+hid+","+did+",'"+newreview+"',1)");
					   userReview.setText("");
					   ResultSet rs2=st2.executeQuery("select * from PROJECT1.RESTUARANTREVIEW where RESTUARANT_ID="+hid+" and DIST_ID="+did+" order by LIKES desc");
					   System.out.println(rs2);
					  
					   ps2.setDataDb(rs2, "Restuarant", "gnh");
					   System.out.println("call addreviewData");
					   p3.addReviewData();
					   System.out.println("reviews added");
					    rs2.close();
					    
							
			    }
			    
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
		
	}

}


class panel3 extends JPanel
{
	Panel4 pa[];
	JButton dolike;
	JLabel totalLikes;
	passData ps2,pd;
	int no,k=20;
	int panel_hight;
	ReviewPanel rp;
	panel3(passData pd,passData ps2,ReviewPanel rp)
	{  setLayout(null);
		no=8;
		 this.ps2=ps2;  
		 this.pd=pd;
		 this.rp=rp;
	    //panel_hight=2500;
		pa=new Panel4[no];

	setPreferredSize(new Dimension(1200,1600));
	
	for(int i=0;i<no;i++)
	{
		pa[i]=new Panel4(pd,rp,ps2);
		
		pa[i].setVisible(true);
	    pa[i].setBounds(10,k,1200,180);	
        pa[i].setBackground(Color.pink);
        k=k+200;
		add(pa[i]);   
	}
	}
	 void addReviewData() 
	{
		 System.out.println("in addReviewData");
		ResultSet reviewtemp=ps2.getDataDb();
		int i=0,userid;
		 try 
       {
			 int count=0;
			 System.out.println(""+count+" innnreview111 : "+reviewtemp);
			
			 while(reviewtemp.next())
				{
				 System.out.println("inreview222");
				  userid=reviewtemp.getInt(1);
				  try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
				} catch (ClassNotFoundException e) {
					
					System.out.println("connecting usertable:"+e.getMessage());
				}
		    	  System.out.println("driver connected");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
					System.out.println("database connected");
				    Statement st2=con.createStatement();
				    ResultSet user=st2.executeQuery("select * from PROJECT1.USERTABLE where USER_ID="+userid);
				    user.next();
				    pa[i].userid=user.getInt(1);
				 	pa[i].userName.setText(user.getString(2));
				 	pa[i].ans.setText(reviewtemp.getString(4));
				 	pa[i].totalLikes.setText(reviewtemp.getString(5));
				 	pa[i].setVisible(true);
				 	i++;count++;
				}
			 for(int j=count;j<8;j++)
			 {
				 pa[j].setVisible(false);
			 }
		}
       catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
	}
}

class Panel4 extends JPanel implements ActionListener 
{

	JLabel userName;
	JLabel ans;
	JLabel totalLikes;
	JLabel tlikes;
	JButton like;
	passData pd,ps2;
	Font f;
	ReviewPanel rp;
	int userid;
	Panel4(passData pd,ReviewPanel rp,passData ps2)
	{   
		setLayout(null);
		this.pd=pd;
		this.ps2=ps2;
		this.rp=rp;
		userName = new JLabel(" DHAVAL ");
		totalLikes = new JLabel(" 123 ");
		tlikes=new JLabel("LIKES:");
		like = new JButton("");
		like.setIcon(new ImageIcon("Z:\\java_work\\TripAdisor\\Images\\black5050.png"));
		like.setBackground(Color.white);
		
		
		ans = new JLabel("");
		f=new Font("CANDARA",Font.BOLD,35);
		userName.setFont(f);
		
	    userName.setBounds(10,0,500,50);
	    tlikes.setBounds(850,0,150,50);
	    totalLikes.setBounds(1000,0,50,50);
	    like.setBounds(1100,0,60,60);
	    
	    f=new Font("CANDARA",Font.ITALIC,20);
	    ans.setFont(f);
	    tlikes.setFont(f);
	    ans.setBounds(10,40,1000,120);
	  like.addActionListener(this);
	        
	    add(userName); add(totalLikes); add(ans);  add(like); add(tlikes);
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 System.out.println("driver connected of review Panel mouse clicked");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
				System.out.println("database connected");
			    Statement st2=con.createStatement();
			    String tablename=pd.getTable();
			    System.out.println(tablename);
			    String DistName=pd.getDistrict();
			    System.out.println(DistName);
			    int hid,did;
			   
			    if(tablename.equals("Hotel"))
			    {
			    	System.out.println("name ma hotel nu naam"+rp.nname.getText());
				   
				   ResultSet rs1=st2.executeQuery("Select * from PROJECT1.HOTELTABLE where HOTEL_NAME='"+rp.nname.getText()+"' and DIST_NAME='"+DistName+"'");
			        System.out.println(rs1);
			        rs1.next();
			    	hid=rs1.getInt(1);
			    	System.out.println(hid);
			    	did=rs1.getInt(2);
			    	System.out.println(did);
				   st2.executeUpdate("update PROJECT1.HOTELREVIEW set LIKES=LIKES+1 where USER_ID="+userid+" and HOTEL_ID="+hid+" and DIST_ID="+did);
				   
				   ResultSet rs2=st2.executeQuery("select * from PROJECT1.HOTELREVIEW where HOTEL_ID="+hid+" and DIST_ID="+did+" order by LIKES desc");
				   System.out.println(rs2);
				  
				   ps2.setDataDb(rs2, "Hotel", "gnh");
				   System.out.println("call addreviewData");
				  rp.p3.addReviewData();
				   System.out.println("reviews added");
				    rs2.close();
				   
				      
			    }
			    else if(tablename.equals("Place"))
			    {
			    	System.out.println("name ma hotel nu naam"+rp.nname.getText());
					   
					   ResultSet rs1=st2.executeQuery("Select * from PROJECT1.PLACETABLE where PLACE_NAME='"+rp.nname.getText()+"' and DIST_NAME='"+DistName+"'");
				        System.out.println(rs1);
				        rs1.next();
				    	hid=rs1.getInt(1);
				    	System.out.println(hid);
				    	did=rs1.getInt(2);
				    	System.out.println(did);
				    	 st2.executeUpdate("update PROJECT1.PLACEREVIEW set LIKES=LIKES+1 where USER_ID="+userid+" and PLACE_ID="+hid+" and DIST_ID="+did);
					  
				    	 ResultSet rs2=st2.executeQuery("select * from PROJECT1.PLACEREVIEW where PLACE_ID="+hid+" and DIST_ID="+did+" order by LIKES desc");
						   System.out.println(rs2);
						  
						   ps2.setDataDb(rs2, "Place", "gnh");
						   System.out.println("call addreviewData");
						  rp.p3.addReviewData();
						   System.out.println("reviews added");
						    rs2.close();
				    	 
			    }
			    else if(tablename.equals("Restuarant"))
			    {
			    	System.out.println("name ma hotel nu naam"+rp.nname.getText());
					
					   ResultSet rs1=st2.executeQuery("Select * from PROJECT1.RESTUARANTTABLE where RESTUARANT_NAME='"+rp.nname.getText()+"' and DIST_NAME='"+DistName+"'");
				        System.out.println(rs1);
				        rs1.next();
				    	hid=rs1.getInt(1);
				    	System.out.println(hid);
				    	did=rs1.getInt(2);
				    	System.out.println(did);
				    	 st2.executeUpdate("update PROJECT1.RESTUARANTREVIEW set LIKES=LIKES+1 where USER_ID="+userid+" and RESTUARANT_ID="+hid+" and DIST_ID="+did);
					  
				    	 ResultSet rs2=st2.executeQuery("select * from PROJECT1.RESTUARANTREVIEW where RESTUARANT_ID="+hid+" and DIST_ID="+did+" order by LIKES desc");
						   System.out.println(rs2);
						  
						   ps2.setDataDb(rs2, "Restuarant", "gnh");
						   System.out.println("call addreviewData");
						  rp.p3.addReviewData();
						   System.out.println("reviews added");
						    rs2.close();
			    }
			    
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
		
	
	
}

 
public class MainTrip extends JFrame {

	public static void main(String[] args)
	{
		MainFrame mf=new MainFrame("XYZ");
		mf.setVisible(true);
		mf.setSize(1950,1000);
		mf.setLocation(0,0);

	}

}

