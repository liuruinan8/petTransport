package com.pet.transport.uc.wechat.common.emoji;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author zimok
 * @Title: EmojiFilter
 * @ProjectName petTransport
 * @Description: TODO
 * @date 2019/2/139:19
 */
public class EmojiFilter {
    public static Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    public static String filterEmoji(String source) {
        if (source == null) {
            return source;
        }

        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            source = emojiMatcher.replaceAll("*");
            return source;
        }
        return source;
    }

}
