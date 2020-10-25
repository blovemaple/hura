package com.github.blovemaple.hura.source;

import java.util.Collections;
import java.util.List;

/**
 * 陈在伟老师词典接口的返回。也用作给陈在伟老师提供的词典接口的返回。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ChenQueryResult {
	public static int STATUS_SUCCESS = 1;
	public static int STATUS_FAILED = 0;

	private String search;
	private int status;
	private int sum;
	private List<ListItem> list;

	public String getSearch() {
		return search;
	}

	public static ChenQueryResult success(String search, List<ListItem> list) {
		ChenQueryResult result = new ChenQueryResult();
		result.setSearch(search);
		result.setStatus(STATUS_SUCCESS);
		result.setSum(list.size());
		result.setList(list);
		return result;
	}

	public static ChenQueryResult failed(String search) {
		ChenQueryResult result = new ChenQueryResult();
		result.setSearch(search);
		result.setStatus(STATUS_FAILED);
		result.setSum(0);
		result.setList(Collections.emptyList());
		return result;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public List<ListItem> getList() {
		return list;
	}

	public void setList(List<ListItem> list) {
		this.list = list;
	}

	public static class ListItem {
		private String radiko;
		private String signifo;

		public ListItem() {
		}

		public ListItem(String radiko, String signifo) {
			this.radiko = radiko;
			this.signifo = signifo;
		}

		public String getRadiko() {
			return radiko;
		}

		public void setRadiko(String radiko) {
			this.radiko = radiko;
		}

		public String getSignifo() {
			return signifo;
		}

		public void setSignifo(String signifo) {
			this.signifo = signifo;
		}

	}
}
