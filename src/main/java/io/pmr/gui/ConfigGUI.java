package io.pmr.gui;

import io.pmr.Perspective;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGUI extends GuiConfig {
    public ConfigGUI(GuiScreen parent) {
        super(parent,
                (new ConfigElement(Perspective.config.getCategory("main"))).getChildElements(),
                "pmr",
                false,
                false,
                GuiConfig.getAbridgedConfigPath(Perspective.config.getConfigFile().getPath()));
    }
}
