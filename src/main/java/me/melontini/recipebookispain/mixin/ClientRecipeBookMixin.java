package me.melontini.recipebookispain.mixin;

import me.melontini.recipebookispain.RecipeBookIsPain;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraftforge.client.RecipeBookRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(value = ClientRecipeBook.class, priority = 999)
public class ClientRecipeBookMixin {

    static {
        RecipeBookGroup.values();
        for (ItemGroup group : ItemGroup.GROUPS) {
            if (group != ItemGroup.HOTBAR && group != ItemGroup.INVENTORY && group != ItemGroup.SEARCH && group != null) {
                String name = "P_CRAFTING_" + group.getIndex();
                RecipeBookGroup.create(name, group.getIcon());
                var group1 = RecipeBookGroup.valueOf(RecipeBookGroup.class, name);
                RecipeBookIsPain.ADDED_GROUPS.put(name, group1);
                RecipeBookIsPain.AAAAAAAA.put(name, group);
            }
        }

        var groups = new ArrayList<>(Arrays.asList(RecipeBookGroup.values()));

        List<RecipeBookGroup> craftingMap = new ArrayList<>();
        List<RecipeBookGroup> craftingSearchMap = new ArrayList<>();
        craftingMap.add(RecipeBookGroup.CRAFTING_SEARCH);
        for (RecipeBookGroup bookGroup : groups) {
            if (bookGroup.toString().contains("P_CRAFTING")) {
                craftingMap.add(bookGroup);
                craftingSearchMap.add(bookGroup);
            }
        }

        RecipeBookIsPain.CRAFTING_SEARCH_MAP = craftingSearchMap;
        RecipeBookIsPain.CRAFTING_MAP = craftingMap;


        ForgeRecipeBookRegistryMixin.MUTABLE_AGGREGATE_CATEGORIES().remove(RecipeBookGroup.CRAFTING_SEARCH);
        ForgeRecipeBookRegistryMixin.MUTABLE_AGGREGATE_CATEGORIES().put(RecipeBookGroup.CRAFTING_SEARCH, RecipeBookIsPain.CRAFTING_SEARCH_MAP);
        RecipeBookRegistry.addCategoriesToType(RecipeBookCategory.CRAFTING, RecipeBookIsPain.CRAFTING_MAP);
        RecipeBookIsPain.LOGGER.info("recipe book init complete");
    }

    @Inject(at = @At("HEAD"), method = "getGroupForRecipe", cancellable = true)
    private static void recipe_book_is_pain$getGroupForRecipe(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        RecipeType<?> recipeType = recipe.getType();
        if (recipeType == RecipeType.CRAFTING) {
            ItemStack itemStack = recipe.getOutput();
            ItemGroup group = itemStack.getItem().getGroup();
            if (group != null) {
                if (group != ItemGroup.HOTBAR && group != ItemGroup.INVENTORY && group != ItemGroup.SEARCH)
                    if (RecipeBookIsPain.ADDED_GROUPS.get("P_CRAFTING_" + group.getIndex()) != null)
                        cir.setReturnValue(RecipeBookIsPain.ADDED_GROUPS.get("P_CRAFTING_" + group.getIndex()));
                    else
                        cir.setReturnValue(RecipeBookIsPain.ADDED_GROUPS.get("P_CRAFTING_" + ItemGroup.MISC.getIndex()));
            }
        }
    }
}
