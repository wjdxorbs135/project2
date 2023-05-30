package chat_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import chat_DB.ChatVO;
import chat_DB.ChatDAO;
import chat_DBConn.DBconn;
import chat_4.Chat_Login;
import chat_4.Chat_Main;

public class ChatDAO {
	private Chat_Login chl;
	private Chat_Main chm;
	private ChatDAO chd;
	public String mainname;
	public String logid;

	private static Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public ChatDAO() throws ClassNotFoundException, SQLException {
		con = new DBconn().getConnection();
	}

	public ArrayList<String> department() {

		ArrayList<String> department = new ArrayList<String>();

		String sql = "select distinct department from chatTable";
		try {
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				department.add(rs.getString(1));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return department;
	}

	public ArrayList<String> employ(String depart) {

		ArrayList<String> employ = new ArrayList<String>();

		String sql = "select name, position from chatTable where department = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, depart);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				employ.add(rs.getString(1) + " " + rs.getString(2));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employ;
	}

	public boolean login(String id, String pw) {

		String sql = "select pw from chatTable where id=? and pw=?";
		try {// 아이디 칸이 빈칸 이면 id를 입력해주세요, 비밀번호칸이 빈칸이면 pw 를 입력해 주세요
			if (id.length() == 0) {
				JOptionPane.showMessageDialog(null, "id를 입력해주세요.");
			}
			if (pw.length() == 0) {
				JOptionPane.showMessageDialog(null, "pw를 입력해주세요.");
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next() == true) { // 쿼리문을 돌려서 아이디 비밀번호가 맞다면 로그인 , 아니라면 "id/pw가 일치하지 않습니다" 팝업창이 뜬다
				JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
				con.close();
				pstmt.close();

				return true;
				// new mainFrame();
			} else {
				JOptionPane.showMessageDialog(null, "id/ pw가 일치하지 않습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	public boolean id_check(String id) {// 중복확인을 위한 boolean 생성자
		boolean result = true;

		try {
			pstmt = con.prepareStatement("SELECT * FROM chatTable where id=?");
			pstmt.setString(1, id.trim());
			rs = pstmt.executeQuery();
			if (rs.next() == false) {// 아이디가 중복되지 않으면
				JOptionPane.showMessageDialog(null, "아이디 사용 가능합니다.");
			} else { // 아이디 중복시
				JOptionPane.showMessageDialog(null, "아이디가 중복됩니다.");
				result = false;
			}

		} catch (SQLException e) {

		} finally {

		}
		return result;

	}

	public boolean insertMember(ChatVO chv) {// 회원가입을 위한 생성자 DB에 회원가입 정보를 insert해 저장
		String sql = "insert into chatTable values(?, ?, ?, ?, ?, ?, ?)";
		boolean flag = false;
		try {
			pstmt = con.prepareStatement(sql); // sql에연결하여 insert해줌
			pstmt.setString(1, chv.getId()); // 이름
			pstmt.setString(2, chv.getPw()); // 비밀번호
			pstmt.setString(3, chv.getName()); // 이름
			pstmt.setString(4, chv.getDepartment());// 부서명
			pstmt.setString(5, chv.getPosition()); // 직책
			pstmt.setString(6, chv.getTel()); // 핸드폰번호
			pstmt.setString(7, chv.getGender()); // 성별
			int cnt = pstmt.executeUpdate(); ////////////////////////////////////
			if (cnt == 1)
				flag = true; /// 실행문 값이 1이면 저장하고 아니면 저장하지 않음
			else
				flag = false;////////////////////////////////////////////////////
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return flag;
	}

	

	public boolean infoconn(String mainname) {  //Chat_Main의 JTree에서 선택한 노드가 직원인지 판별하는 메서드
		this.mainname = mainname;

		String sql = "select * from chatTable where name = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mainname);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public String infolbl(String mainname) { //Chat_Main의 JTree에서 직원 노드를 선택했을때 해당 직원의 정보를 DB에서 추출하는 메서드 
		this.mainname = mainname;

		String infolbl = "";

		String sql = "select department, position, tel from chatTable where name = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mainname);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String department = rs.getString("department");
				String position = rs.getString("position");
				String tel = rs.getString("tel");

				infolbl = department + " " + position + " " + tel;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return infolbl;

	}

	public String mainlbl(String logid) { //Chat_Login에서 사용한 id를 가져와 해당 id를 가진 직원의 정보를 DB에서 추출하는 메서드
		this.logid = logid;

		String mainlbl = "";

		String sql = "select name, department, position, tel from chatTable where id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, logid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String department = rs.getString("department");
				String position = rs.getString("position");
				String tel = rs.getString("tel");

				mainlbl = name + " " + department + " " + position + " " + tel;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mainlbl;

	}
}
