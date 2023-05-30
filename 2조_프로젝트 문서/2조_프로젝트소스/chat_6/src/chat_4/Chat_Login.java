package chat_4;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chat_4.Chat_Login;
import chat_4.Chat_Main;
import chat_4.Chat_Signup;
import chat_DB.ChatDAO;
import muti_network.MultiClient;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPasswordField;

public class Chat_Login extends JFrame {

	private JPanel contentPane;
	private JTextField idtxt; // 아이디 입력
	private JPasswordField pwtxt;// 비밀번호 입력

	private Chat_Signup chs;
	private Chat_Login chl;
	private Chat_Main chm;
	private ChatDAO chd;
	private MultiClient mc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat_Login frame = new Chat_Login();
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
	public Chat_Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(chl);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titlelbl = new JLabel("\uC0AC\uB0B4\uB9DD \uBA54\uC2E0\uC800");
		titlelbl.setFont(new Font("����ü", Font.BOLD, 25));
		titlelbl.setBounds(87, 34, 173, 59);
		contentPane.add(titlelbl);

		idtxt = new JTextField(); //ID 입력칸
		idtxt.setBounds(97, 134, 137, 25);
		contentPane.add(idtxt);
		idtxt.setColumns(10);

		JButton okbtn = new JButton("\uB85C\uADF8\uC778");
		okbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String id = idtxt.getText();// TextField의 idtxt 부분을 가져온다
				String pw = pwtxt.getText();// TextField의 pwtxt 부분을 가져온다

				try {
					chd = new ChatDAO();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] maindata = chd.mainlbl(id).split(" "); // chd.mainlbl(Login한 직원의 이름): 선택한 직원의 정보를 DB에서 받아
																// split(" ")을 사용하여 maindata 배열에 넣어준다.

				try {
					chd = new ChatDAO(); // ChatDAO로 이동해서 로그인을 확인한다.
					if (chd.login(id, pw)) {// 로그인이 성공하면 열려있는 창을 닫고 메인화면을 띄운다
						dispose();
						chm = new Chat_Main(maindata[0], maindata[1], maindata[2], maindata[3]); // maindata[0]: login한
																									// 직원의 이름,
																									// maindata[1]:
																									// login한 직원의 부서,
																									// maindata[2]:
																									// login한 직원의 직책,
																									// maindata[3]:
																									// login한 직원의 연락처
						chm.setUndecorated(false);
						chm.setLocationRelativeTo(chl);
						chm.setVisible(true);

					}

				} catch (Exception error) {
					error.printStackTrace();
				}

			}
		});
		okbtn.setFont(new Font("�������", Font.PLAIN, 14));
		okbtn.setBounds(79, 232, 77, 23);
		contentPane.add(okbtn);

		JButton signbtn = new JButton("\uD68C\uC6D0\uAC00\uC785"); //회원가입 버튼
		signbtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});
		signbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose(); // 현재 화면을 닫고 회원가입 창으로 이동
				chs = new Chat_Signup(chl);
				chs.setResizable(false);
				chs.setLocationRelativeTo(chl);
				chs.setVisible(true);
			}
		});
		signbtn.setFont(new Font("�������", Font.PLAIN, 14));
		signbtn.setBounds(165, 232, 95, 23);
		contentPane.add(signbtn);

		pwtxt = new JPasswordField(); //PW 입쳑칸
		pwtxt.setBounds(97, 169, 137, 25);
		contentPane.add(pwtxt);

		JLabel idlbl = new JLabel("ID");
		idlbl.setFont(new Font("�������", Font.PLAIN, 14));
		idlbl.setBounds(55, 137, 46, 26);
		contentPane.add(idlbl);

		JLabel pwlbl = new JLabel("PW");
		pwlbl.setFont(new Font("�������", Font.PLAIN, 14));
		pwlbl.setBounds(55, 168, 46, 26);
		contentPane.add(pwlbl);
	}
}
