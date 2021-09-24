package io.pmr;

import io.pmr.gui.ConfigGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "pmr", version = "1.0-SNAPSHOT", name = "Perspective", clientSideOnly = true, guiFactory = "io.pmr.gui.GUIFactory")
public class Perspective {
    public static final String MOD_ID = "pmr";
    public static final String MOD_NAME = "Perspective";
    public static final String Version = "1.0-SNAPSHOT";

    public static final Logger LOGGER = LogManager.getLogger("Perspective");

    public static Perspective INSTANCE;

    public static Configuration config;

    private Minecraft client;

    private KeyBinding toggleKey;

    private boolean showConfig = false;
    private static boolean holdToUse = false;

    public boolean perspectiveEnabled = false;

    public float cameraPitch;
    public float cameraYaw;

    private boolean held = false;

    public Perspective() {
        this.client = Minecraft.getMinecraft();
        INSTANCE = this;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand((ICommand)new ConfigCommand());
        ClientRegistry.registerKeyBinding(this.toggleKey = new KeyBinding("Toggle perspective:", 62, "PerspectiveMOD"));
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        saveConfig();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(config.hasChanged())
            config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equals("pmr"))
            saveConfig();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.showConfig) {
            this.client.displayGuiScreen((GuiScreen)new ConfigGUI(null));
            this.showConfig = false;
        }
        // field_71439_g
        if (this.client.player != null) {
            if(!this.perspectiveEnabled && this.held) {
                this.held = false;
                // field_71474_y.field_74320_O;
                this.client.gameSettings.thirdPersonView = 0;
            }
            if(this.perspectiveEnabled && this.client.gameSettings.thirdPersonView != 1)
                this.perspectiveEnabled = false;
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if(this.client.player != null)
            if(holdToUse) {
                // func_151470_d();
                this.perspectiveEnabled = this.toggleKey.isKeyDown();
                if (this.perspectiveEnabled && !this.held) {
                    this.held = true;
                    // field_71439_g.field_70125_A
                    this.cameraPitch = this.client.player.rotationPitch;
                    // field_71439_g.field_70177_z
                    this.cameraYaw = this.client.player.rotationYaw + 180.0F;

                    this.client.gameSettings.thirdPersonView = 1;
                }
                // func_151468_f
            } else if(this.toggleKey.isPressed()) {
                this.perspectiveEnabled = !this.perspectiveEnabled;

                this.cameraPitch = this.client.player.rotationPitch;
                this.cameraYaw = this.client.player.rotationYaw;

                this.client.gameSettings.thirdPersonView = this.perspectiveEnabled ? 1 : 0;
            }
    }

    @SubscribeEvent
    public void cameraSetup(EntityViewRenderEvent.CameraSetup event) {
        if(this.perspectiveEnabled) {
            event.setPitch(this.cameraPitch);
            event.setYaw(this.cameraYaw);
        }
    }

    public static void saveConfig() {
        holdToUse = config.getBoolean("Hold to Usage", "main", false, null);
        config.save();
    }

    public class ConfigCommand extends CommandBase {
        // func_71517_b()
        @Override
        public String getName() {
            return "perspective";
        }

        // func_71518_a()
        @Override
        public String getUsage(ICommandSender sender) {
            return "/perspective";
        }

        // func_184881_a()
        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            showConfig = true;
        }

        // func_82362_a()
        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }
    }
}
