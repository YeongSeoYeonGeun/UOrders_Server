package com.example.uorders.util;

import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class Translator {

    public static String translate(String text, String LanguageCode) {

        String targetLanguage;

        switch (LanguageCode) {
            case "en":
                targetLanguage = "en";
                break;
            case "zh":
                targetLanguage = "zh";
                break;
            default:
                return text;
        }

        System.setProperty("GOOGLE_API_KEY", "AIzaSyD9-APDbhThUwPpqKx-HhNf_VbEmLmeaUY");
        com.google.cloud.translate.Translate translate = TranslateOptions.getDefaultInstance().getService();

        com.google.cloud.translate.Translation translation =
        translate.translate(text,
        com.google.cloud.translate.Translate.TranslateOption.sourceLanguage("ko"),
        com.google.cloud.translate.Translate.TranslateOption.targetLanguage(targetLanguage),
        // Use "base" for standard edition, "nmt" for the premium model.
        com.google.cloud.translate.Translate.TranslateOption.model("base"));

        return translation.getTranslatedText();
        }
}
