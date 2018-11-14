package com.github.blovemaple.hura.vorto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 小品词类型。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum VortetoType {
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
			Files.lines(Paths.get(VortetoType.class.getResource("/vortetoj.txt").toURI())).forEach(line -> {
				if (StringUtils.isBlank(line))
					return;
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
			});
		} catch (IOException | URISyntaxException e) {
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
