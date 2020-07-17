package com.example.lc.materialuitest.view;

import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;

public class TextUtil {

    /**
     * 获取带有上标的spannableString
     * @param text 底文本
     * @param superScript 上标文本
     * @return
     */
    public static SpannableString getSuperScriptText(String text, String superScript){
        SpannableString ss = new SpannableString(text + superScript);
        int startIndex = text.length(), endIndex = startIndex + superScript.length();

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(18, true);
        ss.setSpan(sizeSpan, startIndex, endIndex, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        ss.setSpan(superscriptSpan, startIndex, endIndex, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        return ss;
    }

    /**
     * 获取带有下标的spannableString
     * @param text 底文本
     * @param subScript 下标文本
     * @return
     */
    public static SpannableString getSubScriptText(String text, String subScript){
        SpannableString ss = new SpannableString(text + subScript);
        int startIndex = text.length(), endIndex = startIndex + subScript.length();

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(18, true);
        ss.setSpan(sizeSpan, startIndex, endIndex, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        SubscriptSpan subscriptSpan = new SubscriptSpan();
        ss.setSpan(subscriptSpan, startIndex, endIndex, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        return ss;
    }

}
