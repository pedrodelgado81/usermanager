package com.druid.usermanager.backbeans;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "manejadorIndex")
@ViewScoped
public class IndexBackbean {
	
	private String prueba = "asd";

	/**
	 * @return the prueba
	 */
	public String getPrueba() {
		return prueba;
	}

	/**
	 * @param prueba the prueba to set
	 */
	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}
	
	

}
