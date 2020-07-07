package cn.allene.yun.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import cn.allene.yun.pojo.User;

@Repository
public interface UserDao {
	User findUser(User user) throws Exception;

	void addUser(User user) throws Exception;

	void reSize(@Param("username") String username, @Param("formatSize") String formatSize) throws Exception;

	User findUserByUserName(String username) throws Exception;

	@Select("select * from user where username = #{username} and github_id = #{githubId}")
	User findUserByUserNameAndGithubId(@Param("username") String username,@Param("githubId") Integer githubId) throws Exception;

	String getCountSize(String username) throws Exception;
	
	void updateUser(User user);
	
	void deleteUser(String username); 
	
	void upperUser(String username);
}
