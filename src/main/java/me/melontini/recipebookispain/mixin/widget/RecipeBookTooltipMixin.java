package me.melontini.recipebookispain.mixin.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

import static me.melontini.recipebookispain.RecipeBookIsPain.toItemGroup;

@Mixin(value = RecipeBookWidget.class, priority = 1001)
public class RecipeBookTooltipMixin {
    @Shadow protected MinecraftClient client;

    @Shadow @Final private List<RecipeGroupButtonWidget> tabButtons;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.BEFORE), method = "render")
    private void rbip$renderTooltip(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (client.currentScreen != null) {
            this.tabButtons.stream().filter(widget -> widget.visible && widget.isHovered()).forEach(widget -> {
                if (RecipeBookGroup.SEARCH_MAP.containsKey(widget.getCategory())) {
                    client.currentScreen.renderTooltip(matrices, ItemGroup.SEARCH.getDisplayName(), mouseX, mouseY);
                } else {
                    Optional.ofNullable(toItemGroup(widget.getCategory()))
                            .map(ItemGroup::getDisplayName)
                            .ifPresent(text -> client.currentScreen.renderTooltip(matrices, text, mouseX, mouseY));
                }
            });
        }
    }
}
