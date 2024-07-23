package com.github.bmTas.recedui.form.help;

import java.awt.Component;
import java.net.URI;
import java.net.URL;

public interface IHelpDisplay {

	void register(Component c);

	/**
	 * Define the Help URL
	 *
	 * @param helpUrl name of the Help URL
	 */
	void setHelpURL(URL helpUrl);

	/**
	 * @param helpURI the helpURI to set
	 */
	void setHelpURI(URI helpURI);

}