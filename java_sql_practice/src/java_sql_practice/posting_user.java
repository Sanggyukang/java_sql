package java_sql_practice;


public class posting_user {
	private String user_name;
	private String author_phone;
	private int post_count;
	
	public posting_user() {
		
	}
	public posting_user(String un,String ap,int pc) {
		user_name = un;
		author_phone =ap;
		post_count = pc;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getAuthor_phone() {
		return author_phone;
	}
	public int getPost_count() {
		return post_count;
	}
}
