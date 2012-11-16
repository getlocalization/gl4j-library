package com.getlocalization.api.files;

/**
 * Supported File formats. 
 * See <a href="http://www.getlocalization.com/library/api/get-localization-file-management-api/">GetLocalization API</a> 
 */
public enum FileFormat {
  symbian("Symbian"),
  ios("iOS"),
  django("Django"),
  qt("Qt"),
  ruby("Ruby on Rails"),
  codeigniter("CodeIgniter"),
  symfony("Symfoni"),
  cakephp("CakePHP"),
  gettext("GNU Gettext"),
  js("Javascript / jQuery"),
  resx("Resx"),
  xliff("Xliff"),
  android("Android"),
  ini("INI file"),
  javaproperties("Java Properties file"),
  json("JSON file"),
  adobe("Adobe AIR"),
  windowsmobile("Windows Phone"),
  plain("Plain text"),
  phparray("PHP array"),
  mac("Mac OS X");

  private String displayName;

  FileFormat(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
