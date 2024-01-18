package mybatis;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.KicMember;

public interface KicMemberAnno {

	@Insert("insert into kicmember values (#{id},#{pass},#{name},#{gender},"
			+ "	#{tel},#{email},#{picture})")
	int insertMember(KicMember kicmem);
	
	@Select("select*from kicmember where id =#{id}")
	KicMember oneMember(String id);
	
	@Update("update kicmember set name=#{name},gender=#{gender},tel=#{tel},email=#{email}, picture=#{picture} where id =#{id}")
    int updateMember(KicMember kicmem);
	
	@Delete("delete kicmember where id =#{id}")
	 int deleteMember(String id);
	
	@Update("update kicmember set pass=#{pass} where id =#{id}")
	 public int passMember(Map map);
	
	
}
