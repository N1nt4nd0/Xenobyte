package forgefuck.team.xenobyte.modules;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.utils.Reflections;

public class ThaumResearch extends CheatModule {
    
    @Cfg("aspectMode") private boolean aspectMode;
    
    public ThaumResearch() {
        super("ThaumResearch", Category.MODS, PerformMode.SINGLE);
    }
    
    private boolean isComplete(String research) throws Exception {
        return (Boolean) Class.forName("thaumcraft.common.lib.research.ResearchManager").getMethod("isResearchComplete", String.class, String.class).invoke(null, utils.myName(), research);
    }

    private Object[] getAspects(Object obj) throws Exception {
        return (Object[]) Class.forName("thaumcraft.api.aspects.AspectList").getMethod("getAspects").invoke(Reflections.findField(Class.forName("thaumcraft.api.research.ResearchItem"), "tags").get(obj));
    }
    
    private void getResearchs() {
        try {
            Field theResearch = Class.forName("thaumcraft.client.gui.GuiResearchPopup").getDeclaredField("theResearch");
            theResearch.setAccessible(true);
            ((List) theResearch.get(Class.forName("thaumcraft.client.lib.ClientTickEventsFML").getField("researchPopup").get(null))).clear();
            LinkedHashMap<String, Object> researchCategories = (LinkedHashMap<String, Object>) Reflections.findField(Class.forName("thaumcraft.api.research.ResearchCategories"), "researchCategories").get(null);
            for (Object listObj : researchCategories.values()) {
                Map<String, Object> research = (Map<String, Object>) Reflections.findField(Class.forName("thaumcraft.api.research.ResearchCategoryList"), "research").get(listObj);
                for (Object item : research.values()) {
                    String[] parents = (String[]) Reflections.findField(Class.forName("thaumcraft.api.research.ResearchItem"), "parents").get(item);
                    String[] parentsHidden = (String[]) Reflections.findField(Class.forName("thaumcraft.api.research.ResearchItem"), "parentsHidden").get(item);
                    String key = (String) Reflections.findField(Class.forName("thaumcraft.api.research.ResearchItem"), "key").get(item);
                    if (!isComplete(key)) {
                        boolean check = true;
                        if (parents != null) {
                            for (String parent : parents) {
                                if (!isComplete(parent)) {
                                    check = false;
                                    break;
                                }
                            }
                        }
                        if (!check) {
                            continue;
                        }
                        if (parentsHidden != null) {
                            for (String parent : parentsHidden) {
                                if (!isComplete(parent)) {
                                    check = false;
                                    break;
                                }
                            }
                        }
                        if (!check) {
                            continue;
                        }
                        for (Object aObj : getAspects(item)) {
                            if (aObj == null) {
                                check = false;
                                break;
                            }
                        }
                        if (!check) {
                            continue;
                        }
                        utils.sendPacket("thaumcraft", (byte) 14, key, utils.worldId(), utils.myName(), (byte) 0);
                    }
                }
            }
        } catch (Exception e) {}
    }
    
    private void getAspects() {
        try {
            List aspects = (List) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getCompoundAspects").invoke(null);
            for (Object aspect : aspects) {
                String a1 = (String) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getTag").invoke(((Object[]) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getComponents").invoke(aspect))[0]);
                String a2 = (String) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getTag").invoke(((Object[]) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getComponents").invoke(aspect))[1]);
                utils.sendPacket("thaumcraft", (byte) 13, utils.worldId(), utils.myId(), 0, 0, 0, a1, a2, true, true);
            }
        } catch (Exception e) {}
    }
    
    @Override public void onPerform(PerformSource src) {
        if (aspectMode) {
            getAspects();
        } else {
            getResearchs();
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("Thaumcraft");
    }
    
    @Override public String moduleDesc() {
        return lang.get("All aspects and researches are opened", "Магистр ёба");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("AspectMode", aspectMode) {
                @Override public void onLeftClick() {
                    buttonValue(aspectMode = !aspectMode);
                }
                @Override public String elementDesc() {
                    return lang.get("Gives out aspects, otherwise opens researches", "Выдаёт аспекты, иначе открывает изучения");
                }
            }
        );
    }

}
