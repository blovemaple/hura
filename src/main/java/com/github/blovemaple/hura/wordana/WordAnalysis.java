package com.github.blovemaple.hura.wordana;

import java.io.IOException;
import java.util.List;

import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;
import com.github.blovemaple.hura.source.VortaroSourceType;

/**
 * Hura单词解析。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class WordAnalysis implements VortaroSource {

	@Override
	public String name() {
		return "Hura单词解析";
	}

	@Override
	public VortaroSourceType type() {
		return VortaroSourceType.EKSTRA;
	}

	@Override
	public List<VortaroSourceResult> query(String vorto) throws IOException {
		// 检查整个单词是否是特殊的整体词汇（连词、叹词）

		// 检查是否是数词、人称代词、特殊副词、介词

		// 检查是否是相关词

		// 识别词尾、词缀/词根

		// TODO Auto-generated method stub
		return null;
	}

}
