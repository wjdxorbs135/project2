package chat_4;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import chat_DB.ChatDAO;
import muti_network.MultiClient;

public class Chat_Main extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;

	private Chat_Main chm;
	private Chat_Login chl;
	private ChatDAO chd = new ChatDAO();
	private Chat_Info chi;
	
	
	public String[] mainname; // JTree에서 선택한직원 Node의 Text를 가져오며 split(" ")을 사용하여 mainname 배열에 넣어주기 위해 선언
	public String logname;
	public String logdepartment;
	public String logposition;
	public String logtel;
	private JButton btnNewButton_1;

	public Chat_Main(String logname, String logdepartment, String logposition, String logtel)
			throws ClassNotFoundException, SQLException {

		this.logname = logname;
		this.logdepartment = logdepartment;
		this.logposition = logposition;
		this.logtel = logtel;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 334, 76);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("이름: " + logname); // new JLabel("이름:" + name);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel.setBounds(17, 5, 93, 30);
		panel.add(lblNewLabel);

		label = new JLabel("부서: " + logdepartment);
		label.setFont(new Font("굴림", Font.PLAIN, 14));
		label.setBounds(17, 40, 93, 30);
		panel.add(label);

		label_1 = new JLabel("직급: " + logposition);
		label_1.setFont(new Font("굴림", Font.PLAIN, 14));
		label_1.setBounds(118, 5, 93, 30);
		panel.add(label_1);

		label_2 = new JLabel("연락처: " + logtel);
		label_2.setFont(new Font("굴림", Font.PLAIN, 14));
		label_2.setBounds(118, 40, 187, 30);
		panel.add(label_2);

		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Kaja(주)") {
			{
				ArrayList<String> department = new ArrayList<String>();
				department = new ChatDAO().department();

				for (int i = 0; i < department.size(); i++) {
					DefaultMutableTreeNode node_i;
					node_i = new DefaultMutableTreeNode(department.get(i)); // node_0 : 인사부, node_1: 총무부, node_2: 재무부

					ArrayList<String> employ = new ArrayList<String>(); 
					employ = new ChatDAO().employ(department.get(i));

					for (int j = 0; j < employ.size(); j++) {
						node_i.add(new DefaultMutableTreeNode(employ.get(j))); //node_0.add(직원) => 각 부서별 직원 node 추가
					}
					add(node_i);
				}

				tree.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath() //JTree의 선택한 마지막 node의 경로를 selectedNode에 등록
								.getLastPathComponent();

						mainname = selectedNode.getUserObject().toString().split(" "); // 선택한Node의 Text를 가져오며 split(" ")을 사용하여 mainname 배열에 넣어준다
																						//mainname[0]: 이름, mainname[1]: 직책
						if (chd.infoconn(mainname[0])) {  // chd.infoconn(JTree에서 선택한 직원의 이름): JTree에 있는 직원 노드를 선택했을때만 Chat_Info와 연동

							String[] infodata = chd.infolbl(mainname[0]).split(" "); // chd.infolbl(JTree에서 선택한 직원의 이름): 선택한 직원의 정보를 DB에서 받아 split(" ")을 사용하여 infodata 배열에 넣어준다.
																					//infodata[0]: 부서, infodata[1]: 직책, infodata[2]: 연락처
							chi = new Chat_Info(mainname[0], infodata[0], infodata[1], infodata[2]); //받은 이름, 부서, 직책, 연락처를 Chat_Info 클래스로 넘겨준다.  
							chi.setUndecorated(false);
							chi.setLocation(1100, 250);
							chi.setVisible(true);
						}
					}
				});

			}
		}));
		tree.setBounds(10, 136, 312, 345);
		contentPane.add(tree);

		JButton btnNewButton = new JButton("로그아웃");
		btnNewButton.setBounds(196, 91, 94, 23);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("대화방");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultiClient mc = new MultiClient(logname, logdepartment);
				try {
					mc.init();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton_1.setBounds(46, 91, 97, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "로그아웃 합니다");
				dispose();
				chl = new Chat_Login();
				chl.setResizable(false);
				chl.setLocationRelativeTo(chm);
				chl.setVisible(true);
			}
		});

	}
}
