package dao;

import java.io.UnsupportedEncodingException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.KicMember;
import mybatis.KicMemberAnno;

@Component
public class MemberMybatisDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	private static final String NS = "kicmember.";

	public int insertMember(KicMember kicmem) throws UnsupportedEncodingException, SQLException {

		System.out.println("mybatis insertMember");

		return sqlSession.getMapper(KicMemberAnno.class).insertMember(kicmem);

	}

	public KicMember oneMember(String id) throws SQLException {
	
		return sqlSession.getMapper(KicMemberAnno.class).oneMember(id);

	}

	public int updateMember(KicMember kicmem) throws UnsupportedEncodingException, SQLException {

		return sqlSession.getMapper(KicMemberAnno.class).updateMember(kicmem);

	}

	public int deleteMember(String id) throws UnsupportedEncodingException, SQLException {

		return sqlSession.getMapper(KicMemberAnno.class).deleteMember(id);

	}

	public int passMember(String id, String chgpass) throws UnsupportedEncodingException, SQLException {

		Map map = new HashMap();
		map.put("id", id);
		map.put("pass", chgpass);

		return sqlSession.getMapper(KicMemberAnno.class).passMember(map);

	}

}// class end