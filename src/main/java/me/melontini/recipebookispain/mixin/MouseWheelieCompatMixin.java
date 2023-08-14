package me.melontini.recipebookispain.mixin;

import de.siphalor.mousewheelie.client.mixin.gui.other.MixinRecipeBookWidget;
import de.siphalor.mousewheelie.client.util.ScrollAction;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = RecipeBookWidget.class, priority = 1001)
public abstract class MouseWheelieCompatMixin {

    @Shadow
    @Nullable
    private RecipeGroupButtonWidget currentTab;

    @Shadow @Final private RecipeBookResults recipesArea;

    @SuppressWarnings("ReferenceToMixin")
    @Dynamic(mixin = MixinRecipeBookWidget.class)
    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screen/recipebook/RecipeGroupButtonWidget.setToggled (Z)V", ordinal = 1, shift = At.Shift.AFTER), method = "mouseWheelie_scrollRecipeBook", require = 0)
    private void rbip$scrollPages(double mouseX, double mouseY, double scrollAmount, CallbackInfoReturnable<ScrollAction> cir) {
        if (this.currentTab == null) return;//how tho?
        this.recipesArea.hideAlternates();

        RecipeBookWidget bookWidget = (RecipeBookWidget) (Object) this;
        if (bookWidget.dm$getPage() != currentTab.dm$getPage()) {
            bookWidget.dm$setPage(currentTab.dm$getPage());
        }
    }

    @SuppressWarnings("ReferenceToMixin")
    @Dynamic(mixin = MixinRecipeBookWidget.class)
    @Inject(at = @At(value = "INVOKE", target = "de/siphalor/mousewheelie/client/util/accessors/IRecipeBookResults.mouseWheelie_refreshResultButtons ()V", shift = At.Shift.BEFORE), method = "mouseWheelie_scrollRecipeBook", require = 0)
    private void rbip$hideAlternatives(double mouseX, double mouseY, double scrollAmount, CallbackInfoReturnable<ScrollAction> cir) {
        this.recipesArea.hideAlternates();
    }

}
