package java_sql_practice;


public class answer {

	private String student_name;
	private String student_phone;
	private int post_count;
	
	public answer() {
		
	}
	public answer(String sn,String sp,int pc) {
		student_name =sn;
		student_phone =sp;
		post_count=pc;
	}
	public String getStudent_name() {
		return student_name;
	}
	public String getStudent_phone() {
		return student_phone;
	}
	public int getPost_count() {
		return post_count;
	}
}
