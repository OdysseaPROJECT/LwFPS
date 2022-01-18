package freelook;

import freelook.util.Config;
import freelook.util.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        clientSideOnly = true,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS
)
public class Freelook {
    public static KeyBinding keyFreelook;

    public static KeyBinding toggleMode;

    @Mod.Instance
    public static Freelook _inst;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        Config.init(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        keyFreelook = new KeyBinding("key.freelook.desc", 56, "key.freelook.category");
        toggleMode = new KeyBinding("key.togglemode.desc", 184, "key.togglemode.category");
        ClientRegistry.registerKeyBinding(keyFreelook);
        ClientRegistry.registerKeyBinding(toggleMode);
    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent event) {

    }
}
