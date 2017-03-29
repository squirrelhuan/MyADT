package com.huan.myadt.bean.CGQ_log;
import java.io.Serializable;
import java.util.Date;

/**
 * Model class for a logcate
 *
 * @author CGQ
 */
public class LogCate implements Serializable {
    
    private  int level;
    private  String levelname;
    private  Date time;
    private String packagename;
	private int versioncode;
	private String versionname;
    private  String tag;
    private  String text;
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public int getVersioncode() {
		return versioncode;
	}
	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}
	public String getVersionname() {
		return versionname;
	}
	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}
    
   
}