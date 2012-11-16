package com.getlocalization.api;

/**
 * Presentation of project in Get Localization. Project is determined using project name that should be exactly same
 * as your Get Localization project name in the url. 
 * 
 * Example:
 * 
 * http://www.getlocalization.com/{yourProjectName}
 *
 */
public class GLProject {
	/**
	 * Creates new GLProject instance with given project name.
	 * 
	 * @param projectName
	 */
	public GLProject(String projectName, String username, String password)
	{
		this.projectName = projectName;
		this.setUsername(username);
		this.setPassword(password);
		this.setLanguageId("en");
	}
	
	/**
	 * 
	 * Returns the current language id of master files. 
	 *   
	 * @return IANA formatted language code.
	 */
	public String getLanguageId() {
		return languageId;
	}
	
	/**
	 * 
	 * Set the current language id of master files. 
	 *   
	 * @param languageId IANA formatted language code.
	 */
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns project name.
	 * 
	 * @return the project name.
	 */
	public String getProjectName()
	{
		return projectName;
	}
	
	private String projectName;
	private String languageId;
	private String username;
	private String password;
}
