package com.github.blovemaple.hura.ocr;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class OcrResult {
	private OcrResultType type;
	private String result;

	public OcrResult(OcrResultType type, String result) {
		this.type = type;
		this.result = result;
	}

	public OcrResultType getType() {
		return type;
	}

	public void setType(OcrResultType type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
