package com.wisdom.project.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 本地偏好配置管理类 1.具有缓存 2.能配置多个不同的编好配置文件
 * 
 * @author longway
 * 
 */
public class PrefsConfig {
	private static final String TAG = "PrefsConfig";
	private static final Map<String, PrefsConfig> P_MAP = new LinkedHashMap<String, PrefsConfig>(
			2, 0.75f);
	private static int MAX_VALUE = 10;
	private static final Object MLOCK = new Object();
	private SharedPreferences sharedPreferences;
	private Config config;
	private static final List<ValueOnchangeListener> LISTENERS = new LinkedList<ValueOnchangeListener>();

	public Config getConfig() {
		return config;
	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public void putInt(String key, int value) {
		int oldValue = sharedPreferences.getInt(key, -1);
		sharedPreferences.edit().putInt(key, value).commit();
		notifyListener(int.class, key, oldValue, value);
	}

	public void putLong(String key, long value) {
		long oldValue = sharedPreferences.getLong(key, -1);
		sharedPreferences.edit().putLong(key, value).commit();
		notifyListener(int.class, key, oldValue, value);
	}

	public void putString(String key, String value) {
		String oldValue = sharedPreferences.getString(key, "unknown");
		sharedPreferences.edit().putString(key, value).commit();
		notifyListener(String.class, key, oldValue, value);
	}

	public void putBoolean(String key, boolean value) {
		boolean oldValue = sharedPreferences.getBoolean(key, false);
		sharedPreferences.edit().putBoolean(key, value).commit();
		notifyListener(String.class, key, oldValue, value);
	}

	public void putSerializable(String key, Serializable value){
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(value);
			// 将字节流编码成base64的字符窜
			String base64Str = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
			Serializable oldValue = getSerializable(key);
			sharedPreferences.edit().putString(key, base64Str).commit();
			notifyListener(String.class, key, oldValue, value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void putSerializable(String key, Object value){
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(value);
			// 将字节流编码成base64的字符窜
			String base64Str = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
			Serializable oldValue = getSerializable(key);
			sharedPreferences.edit().putString(key, base64Str).commit();
			notifyListener(String.class, key, oldValue, value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 public  void clearSharePrefernence(){
	 sharedPreferences.edit().clear().commit();
 }
	public Serializable getSerializable(String key){

		String value=sharedPreferences.getString(key,null);
		if(value!=null) {
			byte[] base64 = Base64.decode(value.getBytes(), Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(base64);
			try {
				ObjectInputStream bis = new ObjectInputStream(bais);
				try {
					return (Serializable) bis.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void notifyListener(Class<?> clz, String key, Object oldValue,
                                Object newValue) {
		for (ValueOnchangeListener listener : LISTENERS) {
			listener.onChange(this, clz, key, oldValue, newValue);
		}
	}

	public static void dumpPrefs() {
		Log.i(TAG, P_MAP.toString());
	}

	public interface ValueOnchangeListener {
		void onChange(PrefsConfig prefsConfig, Class<?> clz, String key,
                      Object oldValue, Object newValue);
	}

	public void addListener(ValueOnchangeListener listener) {
		if (!LISTENERS.contains(listener)) {
			LISTENERS.add(listener);
		}
	}

	public void removeListener(ValueOnchangeListener listener) {
		LISTENERS.remove(listener);
	}

	public static void clearListener() {
		LISTENERS.clear();
	}

	public PrefsConfig(SharedPreferences sharedPreferences, Config config) {
		this.sharedPreferences = sharedPreferences;
		this.config = config;
	}

	public static void setMaxSize(int maxSize) {
		if (maxSize > 0 && maxSize != MAX_VALUE) {
			PrefsConfig.MAX_VALUE = maxSize;
		}
	}

	public static PrefsConfig getPrefsConfig(Config config, Context context) {
		synchronized (MLOCK) {
			if (P_MAP.containsKey(config.mSharePrefsName)) {
				PrefsConfig c = P_MAP.get(config.mSharePrefsName);
				P_MAP.remove(config.mSharePrefsName);
				return c;
			}
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					config.mSharePrefsName, config.mode);
			PrefsConfig prefsConfig = new PrefsConfig(sharedPreferences, config);
			return prefsConfig;
		}
	}

	public void recycle() {
		if (P_MAP.size() < MAX_VALUE) {
			P_MAP.put(config.mSharePrefsName, this);
		}
	}

	public void removeSelf() {
		if (P_MAP.containsKey(config.mSharePrefsName))
			P_MAP.remove(config.mSharePrefsName);
	}

	public static void clear() {
		P_MAP.clear();
	}

	public static boolean isFull() {
		return P_MAP.size() >= MAX_VALUE;
	}

	public int getSize() {
		return P_MAP.size();
	}

	public static class Config {
		private String mSharePrefsName;
		private int mode;

		public Config(String mSharePrefsName, int mode) {
			this.mSharePrefsName = mSharePrefsName;
			this.mode = mode;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((mSharePrefsName == null) ? 0 : mSharePrefsName
							.hashCode());
			result = prime * result + mode;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Config other = (Config) obj;
			if (mSharePrefsName == null) {
				if (other.mSharePrefsName != null)
					return false;
			} else if (!mSharePrefsName.equals(other.mSharePrefsName))
				return false;
			if (mode != other.mode)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Config [mSharePrefsName=" + mSharePrefsName + ", mode="
					+ mode + "]";
		}

	}
}