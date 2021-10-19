package freelook.util;

import freelook.Freelook;
import freelook.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@EventBusSubscriber
public class EventHandler {
    public static boolean toggleEnabled = false;

    public static boolean initialPress = false;

    public static boolean freelookEnabled = false;

    public static boolean disabled = true;

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        initialPress = false;
        toggleEnabled = false;
        freelookEnabled = false;
        disabled = true;
        Camera.setCamera();
    }

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
        if (entityPlayerSP == null)
            return;
        // entityPlayerSP.isRiding() to break camera while riding (it will be returning to origin pos)
        // FP-001B
        if(!entityPlayerSP.isRiding() && Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
            if(!toggleEnabled) {
                if(Freelook.keyFreelook.isKeyDown() && !initialPress) {
                    Camera.setCamera();
                    initialPress = true;
                }
                if(Freelook.keyFreelook.isKeyDown() && initialPress)
                    Camera.update((event.phase == TickEvent.Phase.START));
                if(!Freelook.keyFreelook.isKeyDown() && initialPress) {
                    Camera.resetCamera();
                    initialPress = false;
                }
            } else if (freelookEnabled) {
                Camera.cameraEnabled((event.phase == TickEvent.Phase.START));
            } else {
                Camera.resetCamera();
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft().player);
        if(Freelook.toggleMode.isPressed()) {
            if(!toggleEnabled) {
                toggleEnabled = true;
                (Minecraft.getMinecraft()).player.sendChatMessage(new TextComponentString(TextFormatting.GREEN + "FreeLook Mode: Toggle").toString());
            } else {
                toggleEnabled = false;
                (Minecraft.getMinecraft()).player.sendChatMessage(new TextComponentString(TextFormatting.GREEN + "FreeLook Mode: Hold").toString());
            }
        }
        if(entityPlayerSP == null)
            return;
        // entityPlayerSP.isRiding() to break camera while riding (it will be returning to origin pos)
        // FP-001B
        if(!entityPlayerSP.isRiding() && Minecraft.getMinecraft().gameSettings.thirdPersonView == 1)
            if(Freelook.keyFreelook.isKeyDown() && toggleEnabled)
                if(!freelookEnabled) {
                    freelookEnabled = true;
                } else {
                    freelookEnabled = false;
                }
    }
}
