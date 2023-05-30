package chat_DB;

public class ChatVO {
	private String id;
	private String pw;
	private String name;
	private String department;
	private String position;
	private String tel;
	private String gender;
	
	public ChatVO() {}
	
	public ChatVO(String id, String name, String department, String position, String tel, String gender) {
		this.id = id;
		this.name = name;
		this.department = department;
		this.position = position;
		this.tel = tel;
		this.gender = gender;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}

