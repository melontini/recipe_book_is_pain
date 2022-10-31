package me.melontini.recipebookispain;

import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod("recipe_book_is_pain")
public class RecipeBookIsPain {

    public static final Logger LOGGER = LogManager.getLogger("RBIP");
    public static Map<String, RecipeBookGroup> ADDED_GROUPS = new HashMap<>();
    public static Map<String, ItemGroup> AAAAAAAA = new HashMap<>();

    public static List<RecipeBookGroup> CRAFTING_SEARCH_MAP;
    public static List<RecipeBookGroup> CRAFTING_MAP;

    public RecipeBookIsPain() {
    }
}
