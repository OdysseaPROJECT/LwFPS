package io.pmr.mixin;

import io.pmr.Perspective;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({EntityRenderer.class})
public class MixinEntityRenderer {
    @Shadow
    private Minecraft field_78513_r;

    @Inject(method = {"updateCameraAndRender"}, at = {@At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.turn(FF)V", ordinal = 0)},
            cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION, remap = false)
    private void perspectiveCameraFixedUpdate(float partialTicks, long time, CallbackInfo info, boolean flag, float sens, float adjustedSens, float x, float y,
                                                 int invert, float delta) {
        if(Perspective.INSTANCE.perspectiveEnabled) {
            Perspective.INSTANCE.cameraYaw += x / 8.0F;
            if(Math.abs(Perspective.INSTANCE.cameraPitch) > 90.0F)
                Perspective.INSTANCE.cameraPitch = (Perspective.INSTANCE.cameraPitch > 0.0F) ? 90.0F: -90.0F;
        }
    }

    @Inject(method = {"updateCameraAndRender"}, at = {@At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.turn(FF)V", ordinal = 1)},
            cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION, remap = false)
    private void perspectiveCameraUpdate(float partialTicks, long time, CallbackInfo info, boolean flag, float sens, float adjustedSens, float x, float y, int invert) {
        if(Perspective.INSTANCE.perspectiveEnabled) {
            Perspective.INSTANCE.cameraYaw += x / 8.0F;
            if(Math.abs(Perspective.INSTANCE.cameraPitch) > 90.0F)
                Perspective.INSTANCE.cameraPitch = (Perspective.INSTANCE.cameraPitch > 0.0F) ? 90.0F: -90.0F;
        }
    }
}
