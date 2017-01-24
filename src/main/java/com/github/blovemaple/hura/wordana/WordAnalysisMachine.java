package com.github.blovemaple.hura.wordana;

import static java.util.stream.Collectors.*;
import static com.github.blovemaple.hura.wordana.WordAnalysisConf.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.blovemaple.hura.wordana.wordcomp.WordComponent;
import com.github.blovemaple.hura.wordana.wordprop.WordProperties;

/**
 * 单词解析机器。依次输入一个单词的每个字母后可得出解析结果。结果包含单词属性和组成。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class WordAnalysisMachine {
	private StringBuilder word = new StringBuilder();
	private List<Branch> branches = new ArrayList<>();

	/**
	 * 单词解析过程的分支。
	 * 
	 * @author blovemaple <blovemaple2010(at)gmail.com>
	 */
	private class Branch {
		List<WordComponent> components = new ArrayList<>();
		StringBuilder recognizingPart = new StringBuilder();
	}

	/**
	 * 添加一个字符。
	 */
	public synchronized void add(char c) {
		if (branches.isEmpty())
			branches.add(new Branch());

		word.append(c);

		List<Branch> newBranches = new ArrayList<>();

		// 遍历所有分支
		branches.forEach(branch -> {
			branch.recognizingPart.append(c);
			// 取当前分支下加上新字符能构成的新部分
			getComponents(branch.recognizingPart.toString()).stream()
					// 校验合法性，只保留合法的
					.filter(comp -> comp.validate(branch.components))
					// 生成新分支
					.forEach(comp -> {
						Branch newBranch = new Branch();
						newBranch.components.addAll(branch.components);
						newBranch.components.add(comp);
						newBranch.recognizingPart = new StringBuilder();
						newBranches.add(newBranch);
					});
		});

		if (!newBranches.isEmpty())
			branches.addAll(newBranches);
	}

	/**
	 * 根据当前已添加的字符获取解析结果。
	 */
	public synchronized List<WordAnalysisResult> getResults() {
		return branches.stream().filter(branch -> branch.recognizingPart.length() == 0).map(branch -> {

			WordAnalysisResult result = new WordAnalysisResult();
			result.setComponents(new ArrayList<>(branch.components));
			result.setProperties(
					WordProperties.all().stream().filter(p -> p.match(branch.components)).collect(toList()));
			return result;

		}).collect(Collectors.toList());
	}

	/**
	 * 获取当前已添加的字符组成的单词。
	 */
	public synchronized String getWord() {
		return word.toString();
	}

}
