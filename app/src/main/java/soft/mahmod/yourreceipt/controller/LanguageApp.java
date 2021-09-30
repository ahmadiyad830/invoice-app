package soft.mahmod.yourreceipt.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LanguageApp {
    public static final String  SELECTED_LANGUAGE = "language.user.selectd";
    public static final String  FILE_LANGUAGE = "files.language.user";
    public static Context onAttach(Context context){
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        return setLocal(context,lang);
    }
    public static Context onAttach(Context context,String defaultLanguage){
        String lang = getPersistedData(context, defaultLanguage);
        return setLocal(context,lang);
    }

    private static Context setLocal(Context context, String lang) {
        persist(context,lang);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            return updateResources(context,lang);
        }
       return updateResourcesLegacy(context,lang);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String lang) {
        Locale locale = new Locale(lang);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }
    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String lang) {
        Locale locale = new Locale(lang);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLayoutDirection(locale);
        }
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        return context;
    }

    private static void persist(Context context, String lang) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_LANGUAGE,lang);
        editor.apply();
    }

    private static String getPersistedData(Context context, String language) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SELECTED_LANGUAGE,language);
    }
}













