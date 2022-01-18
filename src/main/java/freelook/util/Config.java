package freelook.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {
    public static Configuration config;

    public static boolean viewClamping = false;

    public static void init(File file) {
        config = new Configuration(new File("config/Freelook.cfg"));
        config.load();
        viewClamping = config.getBoolean("view_clamping", "Clamping", viewClamping, "When TRUE, your view will be clamped. Which means it will mimic vision just in real life.");
        config.save();
    }
}