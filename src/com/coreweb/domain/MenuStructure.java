package com.coreweb.domain;

public class MenuStructure extends Domain {

	private String alias = "";
	private String label = "";
	private String description = "";
	private String includeUrl = "";
	private String type = "";
	private String aliasFather = "";

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIncludeUrl() {
		return includeUrl;
	}

	public void setIncludeUrl(String includeUrl) {
		this.includeUrl = includeUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAliasFather() {
		return aliasFather;
	}

	public void setAliasFather(String aliasFather) {
		this.aliasFather = aliasFather;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
