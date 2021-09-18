package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.UpLoadFileDTO;

public interface UpLoadFileDAO {
	/**
	 * 업로드 파일추가
	 * @param list
	 */
	void addFile(UpLoadFileDTO uploadFileDTO);
	void addFiles(List<UpLoadFileDTO> list);

	/**
	 * 업로드 파일 조회
	 * @param rid
	 * @return
	 */
	List<UpLoadFileDTO> getFiles(String rid);
	List<UpLoadFileDTO> getFiles(String rid,String code);
	UpLoadFileDTO getFileByFid(String fid);
	UpLoadFileDTO getFileByRid(String rid);
	UpLoadFileDTO getFileBySfname(String sfname);
	
	
}
