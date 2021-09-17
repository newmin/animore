package com.proj.animore.common.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaOfUploadFile {
	  private String store_fname;
	  private String upload_fname; 	
	  private String fsize;					
	  private String ftype;	
}
