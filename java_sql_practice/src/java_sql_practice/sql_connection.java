package java_sql_practice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class sql_connection {

public sql_connection (){

}

public int testconnection_mysql (int offset) {        
String connection_host = "localhost";
Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs;
        int flag = 0;
   
try {
     // This will load the MySQL driver, each DB has its own driver
     Class.forName("com.mysql.cj.jdbc.Driver");
     // Setup the connection with the DB
     connect = DriverManager
             .getConnection("jdbc:mysql://"+connection_host+ "/new_schema?"+ "user=Jun"+  "&password=sanggyu2383396198");
     
     String qry1a = "select course_name,course_id from course where course_name like ? or course_name like ? " ;
 
     preparedStatement = connect.prepareStatement(qry1a);
     preparedStatement.setString(1, "cs%");
     preparedStatement.setString(2, "%advance%");
     
     ResultSet r1=preparedStatement.executeQuery();
     
     List<course> L_course = new ArrayList<>();
     List<List<user>> L_user = new ArrayList<>();
     List<List<posting>> L_posting  = new ArrayList<>();
     List<List<posting_user>> L_posting_user = new ArrayList<>();
       
           while (r1.next())
           {
        	 course temp = new course(r1.getString("course_name"),r1.getString("course_id"));
        	 L_course.add(temp);
           }
           
           for(course t : L_course) {
        	   qry1a =" select user.name, user.phone from user,course_user " + 
        	   		" where user.role_id=1 and course_id=? " + " and course_user.phone = user.phone; ";
        	   preparedStatement = connect.prepareStatement(qry1a);
        	   preparedStatement.setString(1, t.getId());
        	   r1=preparedStatement.executeQuery();
        	   List<user> user_for_each_course = new ArrayList<>();
        	   	while(r1.next()) {
        	   		if(r1.wasNull()) {
        	   			user temp = new user();
        	   			user_for_each_course.add(temp);
        	   		}
        	   		else {
        	   			user temp = new user(r1.getString("name"),r1.getString("phone"));
        	   			user_for_each_course.add(temp);
        	   		}
        	   	}
        	   	L_user.add(user_for_each_course);
           }
           
           for(course t : L_course) {
        	   qry1a ="select course_id, author_phone from posting where course_id =?";
        	   preparedStatement = connect.prepareStatement(qry1a);
        	   preparedStatement.setString(1, t.getId());
        	   r1=preparedStatement.executeQuery();
        	   List<posting> post_for_each_course = new ArrayList<>();
        	   while(r1.next()) {
        		   if(r1.wasNull()) {
        			   posting temp = new posting();
        			   post_for_each_course.add(temp);
        		   }
        		   else {
        			   posting temp = new posting(r1.getString("course_id"),r1.getString("author_phone"));
        			   post_for_each_course.add(temp);
        		   }
        	   }
        	   L_posting.add(post_for_each_course);
           }
           
           
           int index_for_each_course =0;
           for(course t : L_course) {
        	   List<posting_user> subarray = new ArrayList<>();
        	   for(posting k : L_posting.get(index_for_each_course)) {
        		   if(k != null) {
        			   qry1a = "select user.name, user.phone, count(post_id) p_count from user,posting where course_id =? and "
        					   +" author_phone =? and phone =? ";
                	   preparedStatement = connect.prepareStatement(qry1a);
                	   preparedStatement.setString(1, t.getId());
                	   preparedStatement.setString(2, k.getPhone());
                	   preparedStatement.setString(3, k.getPhone());
                	   r1=preparedStatement.executeQuery();
                	   if(r1.next()) {
                		   if(r1.wasNull()) {
                			   posting_user temp1 = new posting_user();
                			   subarray.add(temp1);
                		   }
                		   posting_user temp2 = new posting_user(r1.getString("name"),r1.getString("phone"),r1.getInt("p_count"));
                		   subarray.add(temp2);
                	   }
        		   }
        	   }
    		   L_posting_user.add(subarray);
    		   index_for_each_course++;
           }
           
           
           int index_for_user_phone=0;
         
           int size = L_course.size();
           
           for(int i =0;i<size;++i) {
            System.out.println("course name : "+L_course.get(i).getName()+" course id :"+L_course.get(i).getId()+
              " \t number of student : "+L_user.get(i).size()+
              " number of posting : "+L_posting.get(i).size());
            int size_enroll = L_user.get(i).size();
            for(int j =0;j<size_enroll;++j)
            System.out.println("\t enrolled student name :"+L_user.get(i).get(j).getName()+
                      " \tphone number : "+L_user.get(i).get(j).getPhone());
            
            System.out.print("\n");

            int size_posting = L_posting_user.get(i).size();
            for(int j =0;j<size_posting;++j)
            	System.out.println("\t posting : student name :"+  L_posting_user.get(i).get(j).getUser_name()+
            		  "\t phone number :"+ L_posting_user.get(i).get(j).getAuthor_phone()+"\t number of posting :"+L_posting_user.get(i).get(j).getPost_count());
            
            System.out.print("\n\n");
           }
           
           //here is answer for the final project question 
           List<List<answer>> L_answer = new ArrayList<>();
           int X =20; // 20%
           //String courseinput = "cs%"; assume course name like cs something in this project 
           
           for(course t : L_course) {
        	   qry1a ="select user.name,user.phone, count(post_id) p_count from user,posting"
        			   + " where course_id =? and user.phone = author_phone group by name order by p_count desc ";
        	   preparedStatement = connect.prepareStatement(qry1a);
        	   preparedStatement.setString(1, t.getId());
           	   r1=preparedStatement.executeQuery();
           	   List<answer> answer_for_each_course = new ArrayList<>();
           	   while(r1.next()) {
           		   if(r1.wasNull()) {
           			   answer temp = new answer();
           			   answer_for_each_course.add(temp);
           		   }
           		   else{
           			   answer temp = new answer(r1.getString("name"),r1.getString("phone"),r1.getInt("p_count"));
           			   answer_for_each_course.add(temp);
           		   }
           	   }
           	   L_answer.add(answer_for_each_course);
           }
           index_for_each_course =0;
           for(course t : L_course) {
        	   int post_size = L_answer.get(index_for_each_course).size();
        	   double k = post_size * X ;
        	   k /=100;
        	   int top_X_percent = (int)Math.ceil(k);
        	   System.out.print(t.getName() +" : ");
        	   for(int j=0;j<top_X_percent;++j) {
        		   System.out.print(L_answer.get(index_for_each_course).get(j).getStudent_name()+"  "+L_answer.get(index_for_each_course).get(j).getStudent_phone());
        	   }
        	   index_for_each_course++;
        	   System.out.print("\n");
           }
           
           r1.close();
           preparedStatement.close();
     
    } catch (Exception e) {
    try {
throw e;
} catch (Exception e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
  } finally {
     if (preparedStatement != null) {
       try {
preparedStatement.close();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
 }      

     if (connect != null) {
       try {
connect.close();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
     }
   }
return flag;
}
       
private void writeResultSet(ResultSet r1) {
// TODO Auto-generated method stub

}

public static void main(String[] args)
{
try
{
if (args.length != 1) {
System.out.println("Usage: java -jar DBTest_Demo.jar <number_of_offset>");
System.out.println("Success returns errorlevel 0. Error return greater than zero.");
System.exit(1);
}

    System.out.println("\n");
   
    sql_connection DBConnect_instance = new sql_connection();
   
    if (DBConnect_instance.testconnection_mysql(Integer.parseInt(args[0])) == 0) {
           System.out.println("MYSQL Remote Connection Successful Completion");
       } else {
           System.out.println("mysql DB connection fail");
       }
   
      //DBConnect_instance.testconnection_mysql(Integer.parseInt(args[0]));
       
}
catch (Exception e){
// probably error in input
System.out.println("Hmmm... Looks like input error ....");
}
  }


}


