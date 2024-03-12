package com.display.backoffice.bas.program.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgrmInfo {

	private String progrmFileNm;
	private String progrmStrePath;
	private String progrmKoreannm;
	private String progrmEngnm;
	private String progrmDc;
	private String url;
}
