import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.google.gson.reflect.TypeToken;
import javax.servlet.annotation.WebServlet;
@WebServlet(urlPatterns = "/CreateStudent")
public class CreateStudent extends HttpServlet{
	 final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	   final static String URL = "jdbc:mysql://124.71.165.81/linux_final";
	   final static String USER = "root";
	   final static String PASS = "Cxl250642#";
	   final static String SQL_INSERT_STUDENT = "INSERT INTO t_student(id,name,age) VALUES (?, ?, ?,)";

	   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      response.setContentType("application/json");
	      response.setCharacterEncoding("UTF-8");

	      Student req = getRequestBody(request);
	      getServletContext().log(req.toString());
	      PrintWriter out = response.getWriter();

	      out.println(createStudent(req));
	      out.flush();
	      out.close();
	   }

	   private Student getRequestBody(HttpServletRequest request) throws IOException {
	      Student note = new Student();
	      StringBuffer bodyJ = new StringBuffer();
	      String line = null;
	      BufferedReader reader = request.getReader();
	      while ((line = reader.readLine()) != null)
	         bodyJ.append(line);
	      Gson gson = new Gson();
	      note = gson.fromJson(bodyJ.toString(), new TypeToken<Student>() {
	      }.getType());
	      return note;
	   }

	   private int createStudent(Student req) {
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      int retcode = -1;
	      try {
	         Class.forName(JDBC_DRIVER);
	         conn = DriverManager.getConnection(URL, USER, PASS);
	         stmt = conn.prepareStatement(SQL_INSERT_STUDENT);

	         stmt.setInt(1, req.id);
	         stmt.setString(2, req.name);
	         stmt.setInt(3, req.age);
	        
	         
	         int row = stmt.executeUpdate();
	         if (row > 0)
	            retcode = 1;

	         stmt.close();
	         conn.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (stmt != null)
	               stmt.close();
	            if (conn != null)
	               conn.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
	      return retcode;
	   }
}
class Student {

int id;
String name;
int age;

public Student() {
	
}
public Student(int id, String name,int age) {
	super();
	this.id = id;
	this.name=name;
	this.age=age;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getAge(){
	return age;
}
public void setAge(int age){
	this.age=age;
}
@Override
public String toString() {
	return "Student [id=" + id + ", name=" + name + ", age=" + age + "]";
}

}
