package me.jellysquid.mods.sodium.mixin.features.cheats;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.server.QueueingWorldGenerationProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class MixinBlock {
    @Inject(method = "getSlipperiness", at = @At("RETURN"), cancellable = true)
    private void getSlipperiness(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(booleanSlip());
        cir.cancel();
    }

    private float booleanSlip() {
        SodiumGameOptions opts = SodiumClientMod.options();
        if(!MinecraftClient.getInstance().isInSingleplayer()) {
            opts.cheat.slippy = false;
        }

        if (opts.cheat.slippy) {
            return opts.cheat.slippyScale/10.0f;
        } else {
            return 0.6F;
        }
    }
}
