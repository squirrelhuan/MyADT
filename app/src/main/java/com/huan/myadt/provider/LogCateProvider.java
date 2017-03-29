package com.huan.myadt.provider;

import java.util.ArrayList;
import java.util.List;

import com.huan.myadt.bean.CGQ_log.LogCate;
import com.huan.myadt.bean.CGQ_log.LogCate_Tag;
import com.huan.myadt.event.LogCateEvent.LogCateManager;

/**
 * logcate内容提供者
 * 
 * @author CGQ
 *
 */
public class LogCateProvider {
	private static boolean state = true;
	private static LogList logs = new LogList();
	private List<LogCate_Tag> tags = new ArrayList<LogCate_Tag>();
	private LogCate_Tag tags_current;
	private int TAG_Model = 0;// 0代表全部
	static LogCateProvider logProvider = new LogCateProvider();

	public static LogCateProvider getInstance() {
		return logProvider;
	}

	private LogCateProvider() {
		LogCate_Tag logCate_Tag0 = new LogCate_Tag(1, 0, "All", "all", "all application");
		this.tags.add(logCate_Tag0);
		tags_current = logCate_Tag0;
	}

	public void clear() {
		logs.clear();
	}

	public static boolean isState() {
		return state;
	}

	public static void setState(boolean state) {
		LogCateProvider.state = state;
	}

	public LogList getLogs() {
		if (tags_current == null || tags_current.getId() == 1) {
			return logs;
		} else {
			LogList logs_c = new LogList();
			for (LogCate mtag : logs) {
				if (mtag.getLevel() == 0) {
					if (mtag.getTag().trim().equals(tags_current.getTag().trim())) {
						logs_c.add(mtag);
					}
				} else if (mtag.getLevel() == tags_current.getLevel()) {
					if (mtag.getTag().trim().equals(tags_current.getTag().trim())) {
						logs_c.add(mtag);
					}
				}
			}
			return logs_c;
		}
	}

	/**
	 * 根据level类型查找log集合
	 * 
	 * @param level
	 * @return
	 */
	public ArrayList<LogCate> getLogsByLevel(int level) {
		if (level == LEVEL_Log.All) {
			return logs;
		}
		ArrayList<LogCate> logs_c = new ArrayList<LogCate>();
		for (LogCate mtag : logs) {
			if (mtag.getLevel() == level) {
				logs_c.add(mtag);
			}
		}
		return logs_c;
	}

	/**
	 * 根据level and tag类型查找log集合
	 * 
	 * @param level
	 * @return
	 */
	public ArrayList<LogCate> getLogsByLevelAndTag(int level, List<String> tags) {
		ArrayList<LogCate> logs_c = new ArrayList<LogCate>();
		if (level == LEVEL_Log.All) {
			for (LogCate mlog : logs) {
				if (tags.contains("All")) {
					logs_c.add(mlog);
				} else {
					for (String tag : tags) {
						if (mlog.getTag().equalsIgnoreCase("All")) {
							logs_c.add(mlog);
						} else if (mlog.getTag().equalsIgnoreCase(tag)) {
							logs_c.add(mlog);
						}
					}
				}
			}
			return logs_c;
		}
		for (LogCate mtag : logs) {
			if (mtag.getLevel() == level) {
				if (logs.contains("All")) {
					logs_c.add(mtag);
				} else {
					for (String tag : tags) {
						if (mtag.getTag().equalsIgnoreCase("All")) {
							logs_c.add(mtag);
						} else if (mtag.getTag().equalsIgnoreCase(tag)) {
							logs_c.add(mtag);
						}
					}
				}
			}
		}
		return logs_c;
	}

	public void setLogs(LogList logs) {
		if (logs.size() > 1000) {
			logs.clear();
		}
		this.logs = logs;
	}

	public List<LogCate_Tag> getTags() {
		return tags;
	}

	public void setTags(List<LogCate_Tag> tags) {
		this.tags = tags;
	}

	public List<LogCate> getLogsByTag(LogCate_Tag logCate_Tag) {
		List<LogCate> tags_c = new ArrayList<LogCate>();
		for (LogCate mtag : logs) {
			if (mtag.getTag().trim().equals(logCate_Tag.getTag().trim())) {
				tags_c.add(mtag);
			}
		}
		return tags_c;
	}

	public void addTag(LogCate_Tag tag) {
		if (TAG_Model == 0) {
			tags.add(tag);
		} else if (tag.getTag().trim().equals(tags_current.getTag().trim())) {
			tags.add(tag);
		}
		LogCateManager.getInstance().onTagsChanged();
	}

	public LogCate_Tag getTags_current() {
		return tags_current;
	}

	public void setTags_current(LogCate_Tag tags_current) {
		this.tags_current = tags_current;
		LogCateManager.getInstance().onLogsChanged();
	}

	public static class LogList extends ArrayList<LogCate> {

		@Override
		public void add(int index, LogCate element) {
			// TODO Auto-generated method stub
			if (state == true) {
				super.add(index, element);
				LogCateManager.getInstance().onLogsChanged();
			}
		}

		@Override
		public boolean add(LogCate e) {
			// TODO Auto-generated method stub
			if (state == true) {
				super.add(e);
				LogCateManager.getInstance().onLogsChanged();
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			super.clear();
			LogCateManager.getInstance().onLogsChanged();
		}

	}

	public interface LEVEL_Log {
		int All = 0;
		int Verbose = 1;
		int Debug = 2;
		int Info = 3;
		int Warn = 4;
		int Error = 5;
		int Assert = 6;
	}

	public static String getLevel(int i) {
		String str = null;
		switch (i) {

		case 1:
			str = "verbose";
			break;
		case 2:
			str = "debug";
			break;
		case 3:
			str = "info";
			break;
		case 4:
			str = "warn";
			break;
		case 5:
			str = "error";
			break;
		case 6:
			str = "exception";
			break;
		default:
			str = "unknow";
			break;
		}
		return str;
	}

	public static int getLevel(String name) {
		int level = 0;
		switch (name) {
		case "all":
			level = 0;
		case "verbose":
			level = 1;
			break;
		case "debug":
			level = 2;
			break;
		case "info":
			level = 3;
			break;
		case "warn":
			level = 4;
			break;
		case "error":
			level = 5;
			break;
		case "exception":
			level = 6;
			break;
		default:
			level = 0;
			break;
		}
		return level;
	}

}
