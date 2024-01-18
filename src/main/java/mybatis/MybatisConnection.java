package mybatis;

import java.io.IOException;
import java.io.Reader;
import java.util.ResourceBundle;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisConnection {
		private static SqlSessionFactory sqlmap;//SqlSessionFactory: MyBatis에서 데이터베이스와의 세션을 생성하기 위한 팩토리입니다.
												//mybatis-config.xml 파일을 읽어들여 설정하고, 이를 바탕으로 SqlSession을 만들어줍니다.
			
			
		static {
			
			Reader reader;
			try {
				reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
				sqlmap = new SqlSessionFactoryBuilder().build(reader);
				System.out.println("config ok");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		//getConnection(): SqlSessionFactory에서 SqlSession을 얻어오는 메소드입니다.
		//SqlSession은 실제 데이터베이스와의 연결을 나타내며, 이를 통해 쿼리를 실행하거나 트랜잭션을 관리할 수 있습니다.
	public static SqlSession getConnection() {
		return sqlmap.openSession();
	}
	public static void close(SqlSession session) {
		session.commit() ;session.close();
		
	}
	public static void main(String[] args) {
		SqlSession sqlsession = getConnection();
		System.out.println(sqlsession);
	}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}
