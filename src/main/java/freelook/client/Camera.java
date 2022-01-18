package freelook.client;

import freelook.Freelook;
import freelook.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

public class Camera {
    public static boolean enabled = false;

    public static float cameraYaw = 0.0F;
    public static float cameraPitch = 0.0F;

    public static float playerYaw = 0.0F;
    public static float playerPitch = 0.0F;

    public static float originalYaw = 0.0F;
    public static float originalPitch = 0.0F;

    public static float cameraDistance = 0.0F;

    public static void setCamera() {
        Minecraft mc = Minecraft.getMinecraft();

        cameraYaw = playerYaw = originalYaw = mc.player.rotationYaw;
        cameraPitch = originalPitch = -mc.player.rotationPitch;
        playerPitch = cameraPitch;
        enabled = true;
    }

    private static void updateCamera() {
        Minecraft mc = Minecraft.getMinecraft();
        if(!mc.inGameHasFocus && !enabled)
            return;
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f1 = f*f*f*8.0F;
        double dx = (Mouse.getDX() * f1) * 0.15D;
        double dy = (Mouse.getDY() * f1) * 0.15D;
        if(Freelook.keyFreelook.isKeyDown()) {
            cameraYaw = (float)(cameraYaw + dx);
            cameraPitch = (float)(cameraPitch + dy);
            cameraPitch = MathHelper.clamp(cameraPitch, -90.0F, 90.0F);
            if(Config.viewClamping == true) {
                cameraYaw = MathHelper.clamp(cameraYaw, originalYaw + -100.0F, originalYaw + 100.0F);
            }
        }
    }

    public static void resetCamera() {
        cameraYaw = originalYaw;
        cameraPitch = originalPitch;
        playerYaw = originalYaw;
        playerPitch = originalPitch;
        enabled = false;
    }

    public static void update(boolean start) {
        Minecraft mc = Minecraft.getMinecraft();
        Entity player = mc.getRenderViewEntity();
        if (player == null)
            return;
        if (enabled) {
            updateCamera();
            if (start) {
                player.rotationYaw = player.prevRotationYaw = cameraYaw;
                player.rotationPitch = player.prevRotationPitch = -cameraPitch;
            } else {
                player.rotationYaw = mc.player.rotationYaw - cameraYaw + playerYaw;
                player.prevRotationYaw = mc.player.prevRotationYaw - cameraYaw + playerYaw;
                player.rotationPitch = -playerPitch;
                player.prevRotationPitch = -playerPitch;
            }
        }
    }

    public static void cameraEnabled(boolean start) {
        Minecraft mc = Minecraft.getMinecraft();
        Entity player = mc.getRenderViewEntity();
        if (player == null && !mc.inGameHasFocus)
            return;
        if (mc.inGameHasFocus) {
            if (!enabled) {
                setCamera();
                enabled = true;
            }
            updateCamera2();
            if (start) {
                player.rotationYaw = player.prevRotationYaw = cameraYaw;
                player.rotationPitch = player.prevRotationPitch = -cameraPitch;
            } else {
                player.rotationYaw = mc.player.rotationYaw - cameraYaw + playerYaw;
                player.prevRotationYaw = mc.player.prevRotationYaw - cameraYaw + playerYaw;
                player.rotationPitch = -playerPitch;
                player.prevRotationPitch = -playerPitch;
            }
        }
    }

    private static void updateCamera2() {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.inGameHasFocus && !enabled)
            return;
        if (mc.inGameHasFocus) {
            float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f1 = f * f * f * 8.0F;
            double dx = (Mouse.getDX() * f1) * 0.15D;
            double dy = (Mouse.getDY() * f1) * 0.15D;
            cameraYaw = (float)(cameraYaw + dx);
            cameraPitch = (float)(cameraPitch + dy);
            cameraPitch = MathHelper.clamp(cameraPitch, -90.0F, 90.0F);
            if (Config.viewClamping == true)
                cameraYaw = MathHelper.clamp(cameraYaw, originalYaw + -100.0F, originalYaw + 100.0F);
        }
    }
}