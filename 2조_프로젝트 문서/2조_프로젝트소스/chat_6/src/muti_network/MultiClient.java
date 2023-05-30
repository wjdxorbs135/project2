package muti_network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat_4.Chat_Info;
import chat_4.Chat_Main;

public class MultiClient implements ActionListener {
	private Socket socket;//소켓
	private ObjectInputStream ois;//받는스트림
	private ObjectOutputStream oos;//보내는스트림
	private JFrame jframe; // 창
	private JTextField jtf;// 메세지 입력란
	private JTextArea jta, jlo;// 로그인창 타자
	private JLabel jlb1, jlb2;// 부서명과 이름을나타내는 라벨
	private JPanel jp1, jp2, jp3, jp4;// 버튼등등 담아서 프레임에 붙이는 바구니
	private String department;// 부서명
	private String name;// 이름
	private JButton jbtn;// 전송버튼
	public boolean changepower = false;
	public boolean saypower = false;
	private boolean login = false;
	
	private String logname; //로그인한 사람의 이름
	private String logdepartment;// 로그인한 사람의  부서

	public MultiClient(String logname, String logdepartment) {//인자가 있는 생성자 Chat_main에서 가져옴
		this.logname = logname;                                 
		this.logdepartment = logdepartment;
		
		jframe = new JFrame("KG Company");

		jtf = new JTextField(20);

		jta = new JTextArea(43, 43) {
			{
				setOpaque(false);
			}

		};
		jlo = new JTextArea(30, 30);
		jlb1 = new JLabel("**회사 채팅창**") {
			{
				setOpaque(false);
			}
		};
		jlb2 = new JLabel("") {
			{
				setOpaque(true);
			}
		};

		jbtn = new JButton("Enter"); // 채팅전송 버튼

		jp1 = new JPanel(); // 바구니
		jp2 = new JPanel();

		jbtn.setFont(new Font("HY엽서L", Font.PLAIN, (int) 20));
		jlb1.setFont(new Font("HY엽서L", Font.PLAIN, (int) 15));

		jlb2.setBackground(Color.GREEN);
		jlb2.setFont(new Font("HY엽서L", Font.PLAIN, (int) 15));

		jbtn.setBackground(Color.WHITE);
		jlo.setBackground(Color.WHITE);

		jp1.setLayout(new BorderLayout());
		jp2.setLayout(new BorderLayout());

		jp1.add(jbtn, BorderLayout.EAST);
		jp1.add(jtf, BorderLayout.CENTER);
		jp2.add(jlb1, BorderLayout.CENTER);
		jp2.add(jlb2, BorderLayout.EAST);

		jp1.setBackground(Color.WHITE);
		jp2.setBackground(Color.WHITE);

		jframe.getContentPane().add(jp1, BorderLayout.SOUTH);
		jframe.getContentPane().add(jp2, BorderLayout.NORTH);

		JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jframe.getContentPane().add(jsp, BorderLayout.CENTER);
		JScrollPane jsp1 = new JScrollPane(jlo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		jtf.addActionListener(this); 
		jbtn.addActionListener(this);

		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					oos.writeObject(logname + "#exit.");//종료시 이름출력
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				dispose();
			}

			private void dispose() { 
				// TODO Auto-generated method stub
				
			}

			public void windowOpened(WindowEvent e) {
				jtf.requestFocus();
			}
		});

		jta.setEditable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;

		jframe.pack();
		jframe.setLocation((screenWidth - jframe.getWidth()) / 2, (screenHeight - jframe.getHeight()) / 2);
		jframe.setResizable(false);
		jframe.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String msg = jtf.getText();   //메세지
		String depart = logdepartment;//부서명

		

		if (obj == jtf && changepower == true) {
			changepower = false;
			if (msg == null || msg.length() == 0) {
				JOptionPane.showMessageDialog(jframe, "글을쓰세요", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				name = logname;
				jtf.setText("");
				                
			}
		} else if (obj == jtf && saypower == true) {
			saypower = false;
			if (msg == null || msg.length() == 0) {
				JOptionPane.showMessageDialog(jframe, "글을쓰세요", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				name = logname;
				jtf.setText("");
			}
		}

		if (obj == jtf) {
			if (msg == null || msg.length() == 0) {//채팅의길이가 0일때 아래메시지 출력
				JOptionPane.showMessageDialog(jframe, "글을쓰세요", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					oos.writeObject("[" + logdepartment + "] " + logname + "#" + msg);//채팅을 입력하고 엔터누를시 [부서명] +이름+입력한 메시지출력
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				jtf.setText("");
			}
		} else if (obj == jbtn) {
			try {
				oos.writeObject("[" + logdepartment + "] " + logname + "#" + msg);//채팅을 입력하고 Enter 버튼을 마우스클릭시 
//				                                                                       [부서명] +이름+입력한 메시지출력
			} catch (IOException ee) {
				ee.printStackTrace();
			}

			jtf.setText("");// 채팅창초기화
		}
	}

	public void exit() {
		System.exit(0);
		
	}

	public void init() throws IOException {
		socket = new Socket("localhost", 2111);//new Socket("접속할 ip주소 ", 포트번호)<여기로 접속한다
		System.out.println("connected...");
		oos = new ObjectOutputStream(socket.getOutputStream());//소켓에 출력스트림생성
		ois = new ObjectInputStream(socket.getInputStream());  // 소켓에 받는 스트림출력
		MultiClientThread ct = new MultiClientThread(this); // new MultiClientThread(this)<클래스를 init()에 가져옴
		Thread t = new Thread(ct);                         //.java 에있는 기본클래스 Thread를 가져옴
		t.start();
	}

	public void main(String args[]) throws IOException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MultiClient mc = new MultiClient(logname, logdepartment);
		mc.init();
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public JTextArea getJta() {
		return jta;
	}

	public String getId() {
		return logname;
	}

	public void SetName(String a) {
		logname = a;
	}

	public void Clear() {
		jta.setText(""); // 초기화되고
		jtf.requestFocus();
	}


}