package com.netiskra;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHolder {
	
	final Logger logger = LoggerFactory.getLogger(FileHolder.class);
	
	Map<String, List<File>> fileMapByDate = null;
		
	/**
	 * file이 단일 파일인 경우 fileList에 file 객체를 넣는다. 
	 * file이 디렉토리인 경우, 
	 *   - 디렉토리는 directoryList에 삽입
	 *   - 파일은 fileList에 삽입
	 * 한다.
	 * 
	 * @param file
	 */
	public FileHolder(File file) {
		
		this.classifyFile(file);

	}
	
	/**
	 * file이 단일 파일인 경우 fileList에 file 객체를 넣는다. 
	 * file이 디렉토리인 경우, 
	 *   - 디렉토리는 directoryList에 삽입
	 *   - 파일은 fileList에 삽입
	 * 한다. 
	 * 
	 * @param srcDir
	 */
	private void classifyFile(File srcDir) {
		
		File backupDir = new File(srcDir.getAbsolutePath() + File.separator + "Backup_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		
		List<File> files = new ArrayList<File>();
		List<File> directories = new ArrayList<File>();
		
		fileMapByDate = new HashMap<String, List<File>>();
		
		File[] _files = srcDir.listFiles();
		File _file = null;
		for (int i=0,j=_files.length; i<j; i++) {
			_file = _files[i];
			if (_file.isDirectory()) {
				directories.add(_file);
			} else {
				files.add(_file);
				logger.info("- {}", _file.lastModified());
				logger.info("  {}", new Date(_file.lastModified()));
				logger.info("  {}", DateFormatUtils.format(_file.lastModified(), "yyyy-MM-dd"));
				
				String fileDate = DateFormatUtils.format(_file.lastModified(), "yyyy-MM-dd");
				
				if ( fileMapByDate.containsKey(fileDate) ) {
					((List<File>)fileMapByDate.get(fileDate)).add(_file);
				} else {
					List<File> _tmp = new ArrayList<File>();
					_tmp.add(_file);
					
					fileMapByDate.put(fileDate, _tmp);
				}
			}
		}
		logger.info("Directory {}", directories);
		logger.info("File {}", files);
		logger.info("fileMapByDate {}", fileMapByDate);
		
		Iterator<String> dateIter = fileMapByDate.keySet().iterator();
		File destDir = null;
		while (dateIter.hasNext()) {
			String fileDate = (String)dateIter.next();
			destDir = new File(srcDir.getAbsolutePath() + File.separator + fileDate);
			
			List<File> _tmp = (List<File>)fileMapByDate.get(fileDate);
			
			Iterator<File> fileIter = _tmp.iterator();
			while (fileIter.hasNext()) {
				File _tmpFile = (File)fileIter.next();
				try {
					FileUtils.copyFileToDirectory(_tmpFile, destDir, true);
					FileUtils.moveFileToDirectory(_tmpFile, backupDir, !backupDir.exists());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		String[] args2 = {"D:\\사진 임시\\새 폴더 (2)"};
		args = args2;
		
		if (args.length < 1) {
			System.out.println("폴더 경로를 입력하시오. ");
			System.out.println("Usage : java FileHolder \"C:\\사진\\폴더 경로\"");
			System.exit(0);
		}
		
		File destDir = new File(args[0]);
		
		if (destDir.exists() && destDir.isDirectory()) {
			FileHolder fh = new FileHolder(destDir);
		} else {
			System.out.println("유효한 폴더 경로가 아니오. ");
			System.exit(0);
		}
		
	}

}
