package com.huan.myadt.bean.CGQ_exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author CGQ
 * @Time 2016.12.01
 */
public class ExceptionMoel implements Serializable {

	public String parentName;
	public String simpleName;
	public String description;
	public String suggestion;
	public String name;

	public String getName(List<ExceptionMoel> exceptionMoels ) {
		String text = simpleName;
		if(parentName!=null){
			for(ExceptionMoel eMoel : exceptionMoels){
				if(!eMoel.getSimpleName().isEmpty()&&eMoel.getSimpleName().equals(parentName)){
					text = eMoel.getName(exceptionMoels) +"." + text ; 
					return text;
				}
			}
		}
		return text;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getSuggestion() {
		if(suggestion==null){
			return "暂无建议";
		}
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

}
