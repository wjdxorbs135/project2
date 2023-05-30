package chat_4;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chat_DB.ChatDAO;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Chat_Info extends JFrame {

	private JPanel contentPane;
	private Chat_Main chm;
	private Chat_Login chl;
	private ChatDAO chd;
	private Chat_Info chi;
	
	
	public String infoname;   //Chat_Main에서 넘겨준 JTree에서 선택한 직원의 이름
	public String department; //Chat_Main에서 넘겨준 JTree에서 선택한 직원의 부서
	public String position;   //Chat_Main에서 넘겨준 JTree에서 선택한 직원의 직책
	public String tel;        //Chat_Main에서 넘겨준 JTree에서 선택한 직원의 연락처
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat_Info frame = new Chat_Info();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Chat_Info(String infoname, String department, String position, String tel) {
		// Chat_Main에서 넘겨준 JTree에서 선택한 직원의 이름, 부서, 직책, 연락처를 받는다.
		this.infoname = infoname;
		this.department = department;
		this.position = position;
		this.tel = tel;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel namelbl = new JLabel("이름: " + infoname);
		namelbl.setBounds(43, 15, 100, 20);
		namelbl.setFont(new Font("나눔고딕", Font.BOLD, 14));
		contentPane.add(namelbl);
		
		JButton canbtn = new JButton("확인");
		canbtn.setBounds(129, 111, 120, 40);
		canbtn.setFont(new Font("나눔고딕", Font.BOLD, 14));
		canbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(canbtn);
		
		JLabel tellbl = new JLabel("연락처: " + tel);
		tellbl.setBounds(202, 65, 170, 20);
		tellbl.setFont(new Font("나눔고딕", Font.BOLD, 14));
		contentPane.add(tellbl);
		
		JLabel posilbl = new JLabel("직급: " + position);
		posilbl.setFont(new Font("나눔고딕", Font.BOLD, 14));
		posilbl.setBounds(202, 15, 100, 20);
		contentPane.add(posilbl);
		
		JLabel departlbl = new JLabel("부서: " + department);
		departlbl.setFont(new Font("나눔고딕", Font.BOLD, 14));
		departlbl.setBounds(43, 65, 100, 20);
		contentPane.add(departlbl);
	
	}

	public Chat_Info() {
		// TODO Auto-generated constructor stub
	}

	
}
