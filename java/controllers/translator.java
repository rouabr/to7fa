package controllers;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class translator {



    private Translate translate;

    public translator(String apiKey) {
        // Initialize Google Cloud Translation API with API Key
        translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();
    }

    public String translateText(String text) {
        // Translate text from English to French
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("fr"));
        return translation.getTranslatedText();
    }
}
