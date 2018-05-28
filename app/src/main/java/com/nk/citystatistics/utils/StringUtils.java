package com.nk.citystatistics.utils;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by Noman Khan on 28/05/18.
 */

public class StringUtils {

        public static final int DEFAULT = 0;
        public static final int ASCII_TRIM = 1;
        public static final int FULL_TRIM = 2;

        public static boolean isEmpty(@Nullable CharSequence text) {
            return text == null || text.length() == 0 || isEmpty(text, DEFAULT);
        }

        public static boolean isEmpty(@Nullable CharSequence text, @EmptyType int type) {
            if (text == null || text.length() == 0) return true;

            if (type == DEFAULT) {
                return text.toString().isEmpty();
            } else if (type == ASCII_TRIM) {
                return text.toString().trim().isEmpty();
            } else {
                return trim(text.toString()).isEmpty();
            }
        }

        public static String trim(@Nullable String text) {
            if (text == null) return null;

            char[] val = text.toCharArray();
            int len = val.length;
            int st = 0;

            while ((st < len) && (val[len - 1] <= ' ' || val[len - 1] == '　')) {
                len--;
            }

            return ((len < val.length)) ? text.substring(st, len) : text;
        }

        public static boolean emptyLength(int length) {
            if (length == 0) {
                return true;
            } else if(length < 0){
                return true;
            } else {
                return false;
            }
        }
        @IntDef(value = {DEFAULT, ASCII_TRIM, FULL_TRIM})
        public @interface EmptyType {
        }
}

