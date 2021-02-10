package me.jellysquid.mods.sodium.mixin.features.cheats;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.mixin.features.options.MixinOptionsScreen;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Block.class)
public abstract class MixinBlock {
    @Shadow
    public abstract float getSlipperiness();

    private SodiumGameOptions opts = SodiumClientMod.options();

    @Inject(method = "getSlipperiness", at = @At("HEAD"), cancellable = true)
    private void getSlipperiness(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(booleanSlip());
        cir.cancel();
    }

    private float booleanSlip() {
        if (opts.cheat.slippy) {
            return 0.99f;
        } else {
            return getSlipperiness();
        }
    }
}
