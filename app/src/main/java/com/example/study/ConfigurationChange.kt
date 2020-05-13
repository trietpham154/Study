package com.example.study

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import java.util.*

class ConfigurationChange {

    companion object {
        private var language: String = Constant.EN_LANGUAGE
        fun getLanguage() = language
        fun applyLanguage(context: Context) {
            context.resources.configuration.run {
                setLocale(Locale(language))
                if (Build.VERSION.SDK_INT == 25)
                    context.createConfigurationContext(this)
                else
                    context.resources.updateConfiguration(
                        this,
                        context.resources.displayMetrics
                    )
            }
        }

        fun changeLanguage(context: Context) {
            when (language) {
                Constant.EN_LANGUAGE -> {
                    context.resources.configuration.run {
                        setLocale(Locale(Constant.VN_LANGUAGE))
                        if (Build.VERSION.SDK_INT == 25)
                            context.createConfigurationContext(this)
                        else
                            context.resources.updateConfiguration(
                                this,
                                context.resources.displayMetrics
                            )
                    }
                    language = Constant.VN_LANGUAGE
                }
                Constant.VN_LANGUAGE -> {
                    context.resources.configuration.run {
                        setLocale(Locale(Constant.EN_LANGUAGE))
                        if (Build.VERSION.SDK_INT == 25)
                            context.createConfigurationContext(this)
                        else
                            context.resources.updateConfiguration(
                                this,
                                context.resources.displayMetrics
                            )
                    }
                    language = Constant.EN_LANGUAGE
                }
            }
        }
    }
}