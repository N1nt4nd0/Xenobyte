package forgefuck.team.xenobyte.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.digest.DigestUtils;

public class Rand {
    
    private static String[] formatColors = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    private static final String[] splashes = {
        "Жизнь боль, когда дебажки ноль",
        "Хыыы ёпта бля",
        "За веранду и двор стреляю в упор",
        "МАЙНКРАФТ СУКА ЭТО МОЯ ЖИЗНЬ",
        "ЩАС ПОФИКШУ (с)4epB9Ik",
        "Иди уроки учи добоиб",
        "ВАМ БАН СЕР",
        "LiteLoader Fuck Team",
        "БУНД БЛЯТ!!1",
        "Интеллектуально (с)radioegor146",
        "EHacks потик",
        "Это не фикс - это заглушка",
        "Тупа бан",
        "Взлом жоп",
        "Продам EHack",
        "vk.com/forgefuck",
        "LUASUCC"
    };
    
    public static int num() {
        return num(-1337, 1337);
    }
    
    public static int num(int max) {
        return num(0, max);
    }
    public static int num(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    
    public static String str(String in) {
        return DigestUtils.md5Hex(in);
    }
    
    public static String str() {
        return str(UUID.randomUUID().toString());
    }
    
    public static boolean bool() {
        return ThreadLocalRandom.current().nextBoolean();
    }
    
    public static int[] coords(int[] c, int r) {
        return new int[] { num(c[0] - r, c[0] + r), num(c[1] - r, c[1] + r), num(c[2] - r, c[2] + r) };
    }
    
    public static String formatColor() {
        return "§" + formatColors[num(formatColors.length)];
    }
    
    public static String formatSplash() {
        return formatColor().concat(splash());
    }
    
    public static String splash() {
        return splashes[num(splashes.length)];
    }

}
