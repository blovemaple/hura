package com.github.blovemaple.hura.vorto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.github.blovemaple.hura.SpringContext;

/**
 * 小品词类型。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum VortetoType {
	DIFINILO("限定词"), //
	PREPOZICIO("介词"), //
	PRONOMO("代词"), //
	NOMBRA_VORTO("数词"), //
	KONJUNKCIO("连词"), //
	ADVERBECA_VORTETO("副词性小品词"), //
	INTERJEKCIO("感叹词"), //

	;

	private String name;
	private Set<String> vortoj;

	private VortetoType(String name) {
		this.name = name;
		this.vortoj = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	static {
		try {
			Pattern namePattern = Pattern.compile("^\\[(.+)\\]$");
			List<Set<String>> crtVortoj = new ArrayList<>(1);
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(
					SpringContext.getApplicationContext().getResource("classpath:vortetoj.txt").getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (StringUtils.isBlank(line))
						continue;
					Matcher matcher = namePattern.matcher(line);
					if (matcher.matches()) {
						String name = matcher.group(1).toUpperCase();
						crtVortoj.clear();
						VortetoType type = null;
						try {
							type = VortetoType.valueOf(name);
						} catch (Exception e) {
						}
						if (type != null)
							crtVortoj.add(type.vortoj);
					} else {
						if (!crtVortoj.isEmpty())
							crtVortoj.get(0).add(line);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static VortetoType typeOfVorto(String vorto) {
		for (VortetoType type : values()) {
			if (type.vortoj.contains(vorto))
				return type;
		}
		return null;
	}

}
