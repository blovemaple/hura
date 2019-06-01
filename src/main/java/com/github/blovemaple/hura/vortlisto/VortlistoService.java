package com.github.blovemaple.hura.vortlisto;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.blovemaple.hura.programeto.LoginInfo;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@Component
public class VortlistoService {
	public VortlistoModel addVortlisto(LoginInfo loginInfo, VortlistoModel vortlisto) {
		// TODO
		return null;
	}

	public VortlistoModel modVortlisto(LoginInfo loginInfo, VortlistoModel vortlisto) {
		// TODO
		return null;
	}

	public VortlistoModel getVortlisto(LoginInfo loginInfo, Long vortlistoId) {
		// TODO
		return null;
	}

	public List<VortlistoModel> getVortlistojByUser(LoginInfo loginInfo) {
		// TODO
		return null;
	}

	public void delVortlisto(LoginInfo loginInfo, Long vortlistoId) {
		// TODO
	}

	public List<VortlistoVortoModel> addVortlistoVortoj(LoginInfo loginInfo, Collection<VortlistoVortoModel> vortoj) {
		// TODO
		return null;
	}

	public VortlistoVortoModel addVortlistoVorto(LoginInfo loginInfo, VortlistoVortoModel vorto) {
		return addVortlistoVortoj(loginInfo, List.of(vorto)).get(0);
	}

	public VortlistoVortoModel modVortlistoVorto(LoginInfo loginInfo, VortlistoVortoModel vorto) {
		// TODO
		return null;
	}

	public List<VortlistoVortoModel> getVortlistoVortojByListoId(LoginInfo loginInfo, Long vortlistoId) {
		// TODO
		return null;
	}

	public VortlistoVortoModel getVortlistoVorto(LoginInfo loginInfo, Long vortlistoId, String vorto) {
		// TODO
		return null;
	}

	public void delVortlistoVorto(LoginInfo loginInfo, Collection<Long> vortoIds) {
		// TODO
	}
}
