package top.dycstudy.pojo;

public class Scuser {

	private Integer id;
	private String uname;
	private String password;
	private String sex;
	private String addr;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Scuser(Integer id, String uname, String password, String sex, String addr) {
		super();
		this.id = id;
		this.uname = uname;
		this.password = password;
		this.sex = sex;
		this.addr = addr;
	}
	public Scuser() {
		super();
	}
	@Override
	public String toString() {
		return "Scuser [id=" + id + ", uname=" + uname + ", password=" + password + ", sex=" + sex + ", addr=" + addr
				+ "]";
	}
	
	
	
	
}
