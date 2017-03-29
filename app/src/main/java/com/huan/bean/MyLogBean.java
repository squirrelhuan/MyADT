package com.huan.bean;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class MyLogBean implements Parcelable ,Serializable{

	private int level;
	private String tag;
	private String content;
	private String packagename;
	private int versioncode;
	private String versionname;

	public MyLogBean() {
	}

	/**
	 * 自定义log数据传输模型
	 * @param level 日志级别
	 * @param tag 日志标签
	 * @param content 日志内容
	 * @param application 日志来源包名
	 */
	public MyLogBean(int level,String tag, String content,String packagename,int versioncode,String versionname) {
		this.level = level;
		this.tag = tag;
		this.content = content;
		this.packagename = packagename;
		this.versioncode = versioncode;
		this.versionname = versionname;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {// 把javanbean中的数据写到Parcel
		// TODO Auto-generated method stub
		// dest.writeInt(this.id);
		dest.writeInt(this.level);
		dest.writeString(this.tag);
		dest.writeString(this.content);
		dest.writeString(this.packagename);
		dest.writeInt(this.versioncode);
		dest.writeString(this.versionname);
	}

	// 添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
	public static final Parcelable.Creator<MyLogBean> CREATOR = new Parcelable.Creator<MyLogBean>() {
		@Override
		public MyLogBean createFromParcel(Parcel source) {// 从Parcel中读取数据，返回person对象
			return new MyLogBean(source.readInt(),source.readString(), source.readString(), source.readString(),source.readInt(),source.readString());
		}

		@Override
		public MyLogBean[] newArray(int size) {
			return new MyLogBean[size];
		}
	};
}
