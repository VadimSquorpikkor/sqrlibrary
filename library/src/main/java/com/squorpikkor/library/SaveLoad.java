package com.squorpikkor.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**Версия 3.05*/
@SuppressWarnings("unused")
public class SaveLoad {

    private final Context context;
    private final SharedPreferences mPrefPrivate;
    private final SharedPreferences mPrefManager;

    public SaveLoad(Context context) {
        this.context = context;
        mPrefPrivate = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        mPrefManager = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**Сохранение по ключу*/
    public void save(String key, String param, Context context) {
        mPrefPrivate.edit().putString(key, param).apply();
    }
    /**Сохранение по ключу*/
    public void save(String key, int param) {
        mPrefPrivate.edit().putInt(key, param).apply();
    }
    /**Сохранение по ключу*/
    public void save(String key, float param) {
        mPrefPrivate.edit().putFloat(key, param).apply();
    }
    /**Сохранение по ключу*/
    public void save(String key, boolean param) {
        mPrefPrivate.edit().putBoolean(key, param).apply();
    }
    /**Загрузка String по ключу*/
    public String loadString(String key) {
        if (mPrefPrivate.contains(key)) return mPrefPrivate.getString(key, "");
        return "";
    }
    /**Загрузка int по ключу*/
    public int loadInt(String key) {
        if (mPrefPrivate.contains(key)) return mPrefPrivate.getInt(key, 0);
        return 0;
    }
    /**Загрузка float по ключу*/
    public float loadFloat(String key) {
        if (mPrefPrivate.contains(key)) return mPrefPrivate.getFloat(key, 0);
        return 0;
    }
    /**Загрузка float по ключу. Задать значение по умолчанию*/
    public float loadFloat(String key, float defValue) {
        if (mPrefPrivate.contains(key)) return mPrefPrivate.getFloat(key, defValue);
        return defValue;
    }
    /**Загрузка boolean по ключу*/
    public boolean loadBoolean(String key) {
        if (mPrefPrivate.contains(key)) return mPrefPrivate.getBoolean(key, false);
        return false;
    }
    /**Загрузка boolean по ключу. Задать значение по умолчанию*/
    public boolean loadBoolean(String key, boolean defValue) {
        if (mPrefPrivate.contains(key)) return mPrefPrivate.getBoolean(key, defValue);
        return defValue;
    }
    /**Сохранение массива по ключу*/
    public void saveArray(String key, ArrayList<String> list) {
        SharedPreferences.Editor editor = mPrefPrivate.edit();

        //очистить. если не очищать, то в случае, когда размер массива меньше сохраненного ранее, будет оставаться "хвост" предыдущего массива
        int count = 0;
        while (mPrefPrivate.contains(key + count)) {
            editor.remove(key + count);
            count++;
        }

        for (int i = 0; i < list.size(); i++) {
            editor.putString(key + i, list.get(i));
        }
        editor.apply();
    }
    /**Загрузка ArrayList<String> по ключу*/
    public ArrayList<String> loadStringArray(String key) {
        ArrayList<String> list = new ArrayList<>();
        int count = 0;
        while (mPrefPrivate.contains(key + count)) {
            String s = mPrefPrivate.getString(key + count, "");
            list.add(s);
            count++;
        }
        return list;
    }
    /**Сохранение Set по ключу*/
    public void saveSet(String key, Set<String> set) {
        SharedPreferences.Editor editor = mPrefPrivate.edit();
        //очистить. если не очищать, то в случае, когда размер массива меньше сохраненного ранее, будет оставаться "хвост" предыдущего массива
        int count = 0;
        while (mPrefPrivate.contains(key + count)) {
            editor.remove(key + count);
            count++;
        }

        int i=0;
        for (String s:set) {
            editor.putString(key + i, s);
            i++;
        }
        editor.apply();
    }

    /**Загрузка Set<String> по ключу*/
    public Set<String> loadStringSet(String key) {
        Set<String> set = new HashSet<>();
        int count = 0;
        while (mPrefPrivate.contains(key + count)) {
            String s = mPrefPrivate.getString(key + count, "");
            set.add(s);
            count++;
        }
        return set;
    }

//--------------------------------------------------------------------------------------------------
    /**Сохранение/загрузка из настроек приложения (PreferencesActivity).
     * Все значения сохраняются как String (кроме boolean) */



    public SharedPreferences getPreferences() {
        return mPrefManager;
    }


    public void savePref(int keyId, String param) {
        String key = context.getString(keyId);
        mPrefManager.edit().putString(key, param).apply();
    }

    public void savePref(int keyId, boolean param) {
        String key = context.getString(keyId);
        mPrefManager.edit().putBoolean(key, param).apply();
    }
    /**Загрузка String по ключу*/
    public void savePref(int keyId, int param) {
        String key = context.getString(keyId);
        mPrefManager.edit().putString(key, String.valueOf(param)).apply();
    }

//--------------------------------------------------------------------------------------------------

    /**Загрузка настройки boolean, заданной через preferenceActivity, по ключу из resId*/
    public boolean getPrefBoolean(int resId) {
        String key = context.getString(resId);
        if (mPrefManager.contains(key)) return mPrefManager.getBoolean(key, false);
        return false;
    }
    /**Загрузка настройки integer, заданной через preferenceActivity, по ключу из resId,
     * с возможность задать значение по умолчанию. Значение по умолчанию — это тоже resId*/
    public boolean getPrefBoolean(int resId, int defValueId) {
        String key = context.getString(resId);
        boolean defValue = context.getString(defValueId).equals("true");
        if (mPrefManager.contains(key)) return mPrefManager.getBoolean(key, defValue);
        return defValue;
    }
    /**Загрузка настройки integer, заданной через preferenceActivity, по ключу из resId*/
    public int getPrefInt(int resId) {
        String key = context.getString(resId);
        if (mPrefManager.contains(key)) return Integer.parseInt(mPrefManager.getString(key, "0"));
        return 0;
    }
    /**Загрузка настройки integer, заданной через preferenceActivity, по ключу из resId,
     * с возможность задать значение по умолчанию. Значение по умолчанию — это тоже resId,
     * а НЕ САМО ЗНАЧЕНИЕ!*/
    public int getPrefInt(int resId, int defValueId) {
        String key = context.getString(resId);
        String defValue = context.getString(defValueId);
        if (mPrefManager.contains(key)) return Integer.parseInt(mPrefManager.getString(key, defValue));
        return Integer.parseInt(defValue);
    }
    /**Загрузка настройки String, заданной через preferenceActivity, по ключу из resId*/
    public String getPrefString(int resId) {
        String key = context.getString(resId);
        if (mPrefManager.contains(key)) return mPrefManager.getString(key, "");
        return "";
    }

    /**Загрузка настройки String, заданной через preferenceActivity, по ключу из resId
     * с возможность задать значение по умолчанию. Значение по умолчанию — это тоже resId,
     * а НЕ САМО ЗНАЧЕНИЕ!*/
    public String getPrefString(int resId, int defValueId) {
        String key = context.getString(resId);
        String defValue = context.getString(defValueId);
        if (mPrefManager.contains(key)) return mPrefManager.getString(key, defValue);
        return defValue;
    }
}
