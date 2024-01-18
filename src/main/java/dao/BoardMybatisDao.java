package dao;

import java.io.UnsupportedEncodingException;



import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Board;
import model.Comment;



@Component
public class BoardMybatisDao {
	
	 @Autowired
	 SqlSessionTemplate sqlSession ;
	 private static final String NS="board.";
	 
	 public int insertBoard(Board board) throws UnsupportedEncodingException, SQLException {
	      	
	   	        
	      
	         int num = sqlSession.insert(NS+"insertBoard",board);
	         sqlSession.commit();
	         return num;
	                  
	   }
	 
	 public List<Board> boardList(int pageInt, int limit, String boardid) throws UnsupportedEncodingException, SQLException {
		 
		 	Map map = new HashMap();
		 	map.put("boardid", boardid);
		 	map.put("start",(pageInt-1)*limit +1);
		 	map.put("end",pageInt * limit);
		 	return sqlSession.selectList(NS + "boardList",map);
         
      
		}
		
	 

public int boardCount(String boardid) throws UnsupportedEncodingException, SQLException {
		 
	return sqlSession.selectOne(NS + "boardCount",boardid);
}




	 public Board oneBoard(int num) throws UnsupportedEncodingException, SQLException {
		 
		 return sqlSession.selectOne(NS+"oneBoard",num);
	 }

	 
	  public int updateBoard(Board board) throws UnsupportedEncodingException, SQLException {
	      	
	         return sqlSession.update(NS+"updateBoard",board);
	                  
	   }
	  
	  public int boardDelete(int num) throws UnsupportedEncodingException, SQLException {
		  
		  return sqlSession.update(NS+ "boardDelete",num);
		  
	
         
       }
	  
	  public int insertComment(String comment, int num) throws UnsupportedEncodingException, SQLException {
	      Map map = new HashMap();
	      map.put("comment", comment);
	      map.put("num", num);

	      return sqlSession.insert(NS+"insertComment", map);
	  }

	public List<Comment> commentList(int num) throws SQLException {
		return sqlSession.selectList(NS + "commentList",num);

}
}