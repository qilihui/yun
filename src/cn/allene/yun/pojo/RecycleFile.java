package cn.allene.yun.pojo;
/**
 * 回收站文件实体类
 * @author Frank
 *
 */
public class RecycleFile extends FileCustom {
	private Integer fileId;
	private String deleteTime;

	public Integer getFileId() {
		return fileId;
	}

	public String getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
}
