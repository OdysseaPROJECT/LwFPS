package freelook;

import freelook.util.Config;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "freelook", name = "Freelook", version = "1.0-SNAPSHOT",
    clientSideOnly = true, acceptedMinecraftVersions = "[1.12.2]")
public class Freelook {
    public static KeyBinding keyFreelook;

    public static KeyBinding toggleMode;

    @Instance
    public static Freelook instance;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        Config.init(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        keyFreelook = new KeyBinding("key.freelook.desc", 56, "key.freelook.category");
        toggleMode = new KeyBinding("key.tooglemode.desc", 184, "key.freelook.category");
        ClientRegistry.registerKeyBinding(keyFreelook);
        ClientRegistry.registerKeyBinding(toggleMode);
    }

    @EventHandler
    public static void PostInit(FMLPostInitializationEvent event) {}
}
