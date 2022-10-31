package me.melontini.recipebookispain.mixin;

import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraftforge.client.RecipeBookRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(RecipeBookRegistry.class) //cope
public interface ForgeRecipeBookRegistryMixin {
    @Accessor("MUTABLE_AGGREGATE_CATEGORIES")
    static Map<RecipeBookGroup, List<RecipeBookGroup>> MUTABLE_AGGREGATE_CATEGORIES() {
        throw new AssertionError();
    }
}
