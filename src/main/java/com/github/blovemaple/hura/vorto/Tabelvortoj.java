package com.github.blovemaple.hura.vorto;

import org.apache.commons.lang3.tuple.Pair;

/**
 * 表解词。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Tabelvortoj {
	public enum FirstPart {
		KI("ki", ""), //
		TI("ti", ""), //
		I("i", ""), //
		CXI("ĉi", ""), //
		NENI("neni", ""), //

		;

		private String part;
		private String desc;

		private FirstPart(String part, String desc) {
			this.part = part;
			this.desc = desc;
		}

		public String getPart() {
			return part;
		}

		public String getDesc() {
			return desc;
		}

	}

	public enum LastPart {
		U("u", ""), //
		O("o", ""), //
		A("u", ""), //
		ES("es", ""), //
		E("e", ""), //
		AM("am", ""), //
		AL("al", ""), //
		EL("el", ""), //
		OM("om", ""), //

		;

		private String part;
		private String desc;

		private LastPart(String part, String desc) {
			this.part = part;
			this.desc = desc;
		}

		public String getPart() {
			return part;
		}

		public String getDesc() {
			return desc;
		}

	}

	public static Pair<FirstPart, LastPart> parseParts(String vorto) {
		FirstPart firstPart = null;
		LastPart lastPart = null;
		for (FirstPart part : FirstPart.values()) {
			if (vorto.startsWith(part.part)) {
				firstPart = part;
				break;
			}
		}
		if (firstPart == null)
			return null;
		for (LastPart part : LastPart.values()) {
			if (vorto.endsWith(part.part)) {
				lastPart = part;
				break;
			}
		}
		if (lastPart == null)
			return null;
		if (!vorto.equals(firstPart.part + lastPart.part))
			return null;
		return Pair.of(firstPart, lastPart);
	}

}
