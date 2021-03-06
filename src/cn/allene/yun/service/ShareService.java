package cn.allene.yun.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.allene.yun.dao.ShareDao;
import cn.allene.yun.pojo.FileCustom;
import cn.allene.yun.pojo.Share;
import cn.allene.yun.pojo.ShareFile;
import cn.allene.yun.pojo.User;
import cn.allene.yun.utils.FileUtils;
import cn.allene.yun.utils.PrivateFileException;
import cn.allene.yun.utils.UserUtils;

@Service
public class ShareService {
	@Autowired
	private ShareDao shareDao;

	/**
	 * 查找分享
	 * 
	 * @param request
	 * @param shareUrl 分享url
	 * @return
	 * @throws Exception
	 */
	public List<ShareFile> findShare(HttpServletRequest request, String shareUrl)
			throws PrivateFileException, Exception {
		Share share = new Share();
		share.setShareUrl(shareUrl);
//		share.setStatus(Share.PUBLIC);
		List<Share> shares = shareDao.findShare(share);
		for (Share s : shares) {
			if (s.getStatus() == Share.PRIVATE) {
				throw new PrivateFileException("私有文件");
			}
		}
		return getShareFile(request, shares);
	}

	public List<ShareFile> findShare(HttpServletRequest request, String shareUrl, String command) throws Exception {
		Share share = new Share();
		share.setShareUrl(shareUrl);
		List<Share> shares = shareDao.findShare(share);
		for (Share s : shares) {
			if (!s.getCommand().equals(command)) {
				throw new Exception("密码错误");
			}
		}
		return getShareFile(request, shares);
	}

	/**
	 * 查找分享文件
	 * 
	 * @param request
	 * @param status  分享文件状态
	 * @return
	 * @throws Exception
	 */
	public List<ShareFile> findShareByName(HttpServletRequest request, int status) throws Exception {
		List<Share> shares = shareDao.findShareByName(UserUtils.getUsername(request), status);
		return getShareFile(request, shares);
	}

	/**
	 * 获取分享文件列表
	 * 
	 * @param request
	 * @param shares  分享文件
	 * @return
	 */
	private List<ShareFile> getShareFile(HttpServletRequest request, List<Share> shares) {
		List<ShareFile> files = null;
		if (shares != null) {
			files = new ArrayList<>();
			String rootPath = request.getSession().getServletContext().getRealPath("/") + FileService.PREFIX;
			for (Share share : shares) {
				File file = new File(rootPath + share.getShareUser(), share.getPath());
				ShareFile shareFile = new ShareFile();
				shareFile.setFileType(FileUtils.getFileType(file));
				shareFile.setFileName(file.getName());
				shareFile.setFileSize(file.isFile() ? FileUtils.getDataSize(file.length()) : "-");
				shareFile.setLastTime(FileUtils.formatTime(file.lastModified()));
				shareFile.setShareUser(share.getShareUser());
				shareFile.setUrl(share.getShareUrl());
				shareFile.setFilePath(share.getPath());
				shareFile.setCommand(share.getCommand());
				shareFile.setShareTime(share.getShareTime().substring(0, share.getShareTime().indexOf(".")));
				files.add(shareFile);
			}
		}
		return files;
	}

	/**
	 * 分享文件
	 * 
	 * @param request
	 * @param currentPath
	 * @param shareFile
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> shareFile(HttpServletRequest request, String currentPath, String[] shareFile, boolean privateFile)
			throws Exception {
		String username = (String) request.getSession().getAttribute(User.NAMESPACE);
		String shareUrl = FileUtils.getUrl8();
		String command = FileUtils.getUrl(Share.COMMANDBIT);
		for (String file : shareFile) {
			Share share = new Share();
			share.setPath(currentPath + File.separator + file);
			share.setShareUser(username);
			share.setShareUrl(shareUrl);
			if (privateFile == true) {
				share.setStatus(Share.PRIVATE);
				share.setCommand(command);
			}
			shareDao.shareFile(share);
		}
		Map<String, String> m = new HashMap();
		if(privateFile == true) {
			m.put("command", command);
		}
		m.put("shareUrl", shareUrl);
		return m;
	}

	/**
	 * 取消分享
	 * 
	 * @param url      分享url
	 * @param filePath 分享路径
	 * @param status   分享状态
	 * @return
	 * @throws Exception
	 */
	public String cancelShare(String url, String filePath, int status) throws Exception {
		if (Share.CANCEL == status) {
			shareDao.cancelShare(url, filePath, Share.DELETE);
			return "删除成功";
		} else {
			shareDao.cancelShare(url, filePath, Share.CANCEL);
			return "取消成功";
		}
	}
}
