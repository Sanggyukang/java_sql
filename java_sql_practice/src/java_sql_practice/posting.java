package java_sql_practice;


public class posting {

	private String course_id;
	private String author_phone;
	public posting() {
		
	}
	public posting(String ci,String ap) {
		course_id =ci;
		author_phone =ap;
	}
	public String getName() {
		return course_id;
	}
	public String getPhone() {
		return author_phone;
	}
}
