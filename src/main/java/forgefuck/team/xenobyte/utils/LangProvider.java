package forgefuck.team.xenobyte.utils;

import net.minecraft.client.Minecraft;

public class LangProvider {
    
    public String get(String eng, String rus) {
        switch (Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode()) {
        case "ru_RU":
            return rus;
        default:
            return eng;
        }
    }

}