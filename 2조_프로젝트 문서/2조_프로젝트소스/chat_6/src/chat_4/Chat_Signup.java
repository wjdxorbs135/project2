package chat_4;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import chat_DB.ChatDAO;
import chat_DB.ChatVO;

public class Chat_Signup extends JFrame {

	private Chat_Signup chs;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private JPanel contentPane;
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private JTextField idtxt; // 아이디입력창
	private JTextField pwtxt; // 비밀번호 입력창
	private JTextField nametxt; // 이름 입력창
	private JTextField departtxt; // 부서 입력창
	private JTextField positxt;// 직책 입력창
	private JTextField teltxt; // 전화번호 입력창
	private int idck; // id체크하기위한 선언

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat_Signup chs = new Chat_Signup();
					chs.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Chat_Signup(Chat_Login chl) { 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("회 원 가 입");
		label.setFont(new Font("돋움체", Font.BOLD, 25));
		label.setBounds(94, 33, 158, 59);
		contentPane.add(label);

		JLabel label_1 = new JLabel("빈칸에 정보를 입력해주세요.");
		label_1.setFont(new Font("돋움체", Font.BOLD, 20));
		label_1.setBounds(24, 78, 310, 59);
		contentPane.add(label_1);

		idtxt = new JTextField();
		idtxt.setBounds(111, 130, 141, 26);
		contentPane.add(idtxt);
		idtxt.setColumns(10);

		JLabel idlbl = new JLabel("ID");
		idlbl.setFont(new Font("�������", Font.PLAIN, 14));
		idlbl.setBounds(61, 130, 46, 26);
		contentPane.add(idlbl);

		JLabel pwlbl = new JLabel("PW");
		pwlbl.setFont(new Font("�������", Font.PLAIN, 14));
		pwlbl.setBounds(61, 160, 46, 26);
		contentPane.add(pwlbl);

		JLabel namelbl = new JLabel("이름");
		namelbl.setFont(new Font("�������", Font.PLAIN, 14));
		namelbl.setBounds(61, 190, 46, 26);
		contentPane.add(namelbl);

		JLabel departlbl = new JLabel("부서명");
		departlbl.setFont(new Font("�������", Font.PLAIN, 14));
		departlbl.setBounds(61, 220, 46, 26);
		contentPane.add(departlbl);

		JLabel posilbl = new JLabel("직책");
		posilbl.setFont(new Font("�������", Font.PLAIN, 14));
		posilbl.setBounds(61, 250, 46, 26);
		contentPane.add(posilbl);

		JLabel tellbl = new JLabel("전화번호");
		tellbl.setFont(new Font("�������", Font.PLAIN, 14));
		tellbl.setBounds(61, 280, 46, 26);
		contentPane.add(tellbl);

		JLabel genlbl = new JLabel("성별");
		genlbl.setFont(new Font("�������", Font.PLAIN, 14));
		genlbl.setBounds(61, 310, 46, 26);
		contentPane.add(genlbl);

		pwtxt = new JTextField();
		pwtxt.setColumns(10);
		pwtxt.setBounds(111, 160, 141, 26);
		contentPane.add(pwtxt);

		nametxt = new JTextField();
		nametxt.setColumns(10);
		nametxt.setBounds(111, 190, 141, 26);
		contentPane.add(nametxt);

		departtxt = new JTextField();
		departtxt.setColumns(10);
		departtxt.setBounds(111, 220, 141, 26);
		contentPane.add(departtxt);

		positxt = new JTextField();
		positxt.setColumns(10);
		positxt.setBounds(111, 250, 141, 26);
		contentPane.add(positxt);

		teltxt = new JTextField();
		teltxt.setColumns(10);
		teltxt.setBounds(111, 280, 141, 26);
		contentPane.add(teltxt);
		ButtonGroup grp = new ButtonGroup(); 

		JRadioButton manrabtn = new JRadioButton("남자");
		manrabtn.setFont(new Font("�������", Font.PLAIN, 14));
		manrabtn.setBounds(111, 312, 62, 23);
		contentPane.add(manrabtn);

		JRadioButton womanrabtn = new JRadioButton("여자");
		womanrabtn.setFont(new Font("�������", Font.PLAIN, 14));
		womanrabtn.setBounds(201, 312, 62, 23);
		contentPane.add(womanrabtn);

		grp.add(manrabtn); //버튼 그룹에 라디오 버튼(남자, 여자)을 추가함으로서 둘 중 하나의 버튼만 선택할 수 있도록 한다.
		grp.add(womanrabtn);

		JButton idckbtn = new JButton("중복"); // 중복체크를위해
		idckbtn.addActionListener(new ActionListener() {// 중복 체크를 누르면
			public void actionPerformed(ActionEvent e) {
				ChatDAO chd;
				try {
					chd = new ChatDAO();
					if (idtxt.getText().trim().equals("")) {// 빈칸이면 아래메세지출력
						JOptionPane.showMessageDialog(null, "ID를 입력하세요", "Message", JOptionPane.ERROR_MESSAGE);
					} else if (chd.id_check(idtxt.getText())) {// idtxt를 dao에있는 id_chek에 넣어 아이디가있는지 확인한다
						idck = 1;
					} else {
						idck = 0;
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		idckbtn.setBounds(254, 131, 80, 23);
		contentPane.add(idckbtn);

		JButton okbtn = new JButton("완료");
		okbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Chat_Signup chs = new Chat_Signup();

				try {

					insertMember();// 완료버튼을누르면 insertMember()로 찾아간다

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block

					e1.printStackTrace();
				}

			}

			private void insertMember() throws ClassNotFoundException, SQLException {
				// TODO Auto-generated method stub
				ChatVO chv = getViewData();
				ChatDAO chd = new ChatDAO();

				if (idtxt.getText().equals("")) { // 정보에 빈칸이 있으면 오류메세지 출력
					JOptionPane.showMessageDialog(null, "ID를 입력해주세요");

				} else if (pwtxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "PW를 입력해주세요");

				} else if (nametxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요");

				} else if (departtxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "부서를 입력해주세요");

				} else if (positxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "직책을 입력해주세요");

				} else if (teltxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요");

				} else if (!manrabtn.isSelected() && !womanrabtn.isSelected()) {// 라디오 버튼 미선택시 오류메세지출력
					JOptionPane.showMessageDialog(null, "성별을 선택해주세요");

				} else if (idck == 0) { // 중복체크안누를시 메세지출력
					JOptionPane.showMessageDialog(null, "중복체크를 확인해주세요 ");

				} else {
					chd.insertMember(chv); // ChatDAO에있는 insertMember 에 getViewData()를담는다.
					JOptionPane.showMessageDialog(null, "가입이 완료되었습니다.");
					dispose();
					Chat_Login chl = new Chat_Login();
					chl.setVisible(true);

				}

			}

			private ChatVO getViewData() { // 담을 생성자
				// TODO Auto-generated method stub
				ChatVO chv = new ChatVO();
				String id = idtxt.getText();
				String pw = pwtxt.getText();
				String name = nametxt.getText();
				String department = departtxt.getText();
				String position = positxt.getText();
				String tel = teltxt.getText();
				String gender = "";
				if (manrabtn.isSelected()) {

					gender = "남자";
				} else if (womanrabtn.isSelected()) {

					gender = "여자";
				}
				chv.setId(id); // Chatvo에 입력된정보를 저장한다.
				chv.setPw(pw);
				chv.setName(name);
				chv.setDepartment(department);
				chv.setPosition(position);
				chv.setTel(tel);
				chv.setGender(gender);
				return chv;
			}

		});
		okbtn.setFont(new Font("�������", Font.PLAIN, 14));
		okbtn.setBounds(193, 341, 62, 23);
		contentPane.add(okbtn);

		JButton canbtn = new JButton("취소");
		canbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				Chat_Login chl = new Chat_Login();// 취소버튼을누르면 로그인 창으로 되돌아간다.
				chl.setVisible(true);

			}
		});
		canbtn.setFont(new Font("�������", Font.PLAIN, 14));
		canbtn.setBounds(111, 341, 62, 23);
		contentPane.add(canbtn);

	}

	public Chat_Signup() {
	}

}
