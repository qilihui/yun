package cn.allene.yun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import cn.allene.yun.pojo.Share;

@Repository
public interface ShareDao {
	List<Share> findShare(Share share) throws Exception;
	
	@Select("select * from share where shareUrl = #{shareUrl} and status = 2")
	List<Share> findPrivateShare(Share share) throws Exception;
	
	List<Share> findShareByName(@Param("username") String username,@Param("status")  int status) throws Exception;

	void shareFile(Share share) throws Exception;

	void cancelShare(@Param("url") String url, @Param("filePath")  String filePath, @Param("status") int status) throws Exception;

}
