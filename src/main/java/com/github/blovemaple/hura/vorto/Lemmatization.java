package com.github.blovemaple.hura.vorto;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;
import com.github.blovemaple.hura.util.Language;
import com.github.blovemaple.hura.vorto.Tabelvortoj.FirstPart;
import com.github.blovemaple.hura.vorto.Tabelvortoj.LastPart;

/**
 * 单词解析。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Lemmatization implements VortaroSource {
	private String original;
	private VortetoType vortetoType;
	private Pair<FirstPart, LastPart> tabelParts;
	private List<Flection> flections;
	private String baseForm;
	private WordEnding ending;

	public static Lemmatization parse(String vorto) {
		vorto = vorto.toLowerCase();

		Lemmatization result = new Lemmatization(vorto);

		if (!Language.isEsperantoWord(vorto))
			return result;

		if (vorto.matches(".*\\s.*"))
			return result;

		boolean specialSucc = parseSpecialWords(vorto, result);
		if (specialSucc) {
			return result;
		}

		boolean flectionSucc = parseFlections(vorto, result);
		if (flectionSucc) {
			return result;
		}

		WordEnding ending = WordEnding.parseEnding(vorto);
		if (ending != null) {
			result.setEnding(ending);
			return result;
		}

		return result;
	}

	private static boolean parseSpecialWords(String vorto, Lemmatization result) {
		VortetoType vortetoType = VortetoType.typeOfVorto(vorto);
		if (vortetoType != null) {
			result.setVortetoType(vortetoType);
			return true;
		}

		Pair<FirstPart, LastPart> tabelParts = Tabelvortoj.parseParts(vorto);
		if (tabelParts != null) {
			result.setTabelParts(tabelParts);
			return true;
		}

		return false;
	}

	private static boolean parseFlections(String vorto, Lemmatization result) {
		List<Flection> flections = new ArrayList<>();
		String remaining = vorto;
		boolean added;
		do {
			added = false;
			for (Flection flection : Flection.values()) {
				if (remaining.endsWith(flection.getEnding())) {
					String tryRemaining = remaining.replaceFirst(flection.getEnding() + "$",
							flection.getBaseFormEnding());
					if (flection.getLegalPrevious().isEmpty()
							|| flection.getLegalPrevious().stream().anyMatch(tryRemaining::endsWith)) {
						flections.add(0, flection);
						remaining = tryRemaining;
						added = true;
						break;
					}
				}
			}
			if (parseSpecialWords(remaining, new Lemmatization(remaining))) {
				break;
			}
		} while (added);

		if (flections.isEmpty())
			return false;
		if (remaining.length() <= 2)
			return false;
		if (parse(remaining).isEmpty())
			return false;

		result.setFlections(flections);
		result.setBaseForm(remaining);
		return true;
	}

	public Lemmatization(String original) {
		this.original = original;
		this.baseForm = original;
	}

	public Lemmatization() {
	}

	@Override
	public String name() {
		return "单词解析";
	}

	@Override
	public List<VortaroSourceResult> query(String vorto, Language language) throws IOException {
		if (language != Language.ESPERANTO)
			return null;

		Lemmatization lemma = parse(vorto);
		String content = null;
		if (lemma.getVortetoType() != null) {
			content = vorto + "是一个" + lemma.getVortetoType().getName() + "。";
		} else if (lemma.getTabelParts() != null) {
			content = vorto + "是由" + lemma.getTabelParts().getLeft().getPart() + "-和-"
					+ lemma.getTabelParts().getRight().getPart() + "组成的表解词。";
		} else if (lemma.getFlections() != null) {
			List<String> flectionStrs = lemma.getFlections().stream().map(Flection::getName).collect(toList());
			content = vorto + "是" + lemma.getBaseForm() + "的" + String.join("、", flectionStrs) + "。";
		} else if (lemma.getEnding() != null) {
			content = vorto + "是一个" + lemma.getEnding().getType() + "。";
		}

		if (content == null)
			return null;

		return Collections.singletonList(new VortaroSourceResult(content));
	}

	private boolean isEmpty() {
		if (getVortetoType() != null)
			return false;
		if (getTabelParts() != null)
			return false;
		if (getFlections() != null)
			return false;
		if (getEnding() != null)
			return false;
		return true;
	}

	public String getOriginal() {
		return original;
	}

	public VortetoType getVortetoType() {
		return vortetoType;
	}

	public void setVortetoType(VortetoType vortetoType) {
		this.vortetoType = vortetoType;
	}

	public Pair<FirstPart, LastPart> getTabelParts() {
		return tabelParts;
	}

	public void setTabelParts(Pair<FirstPart, LastPart> tabelParts) {
		this.tabelParts = tabelParts;
	}

	public List<Flection> getFlections() {
		return flections;
	}

	public void setFlections(List<Flection> flections) {
		this.flections = flections;
	}

	public String getBaseForm() {
		return baseForm;
	}

	public void setBaseForm(String baseForm) {
		this.baseForm = baseForm;
	}

	public WordEnding getEnding() {
		return ending;
	}

	public void setEnding(WordEnding ending) {
		this.ending = ending;
	}

	@Override
	public String toString() {
		return "Lemmatization [\noriginal=" + original + ",\nvortetoType=" + vortetoType + ",\ntabelParts=" + tabelParts
				+ ",\nflections=" + flections + ",\nbaseForm=" + baseForm + ",\nending=" + ending + "\n]\n";
	}

}