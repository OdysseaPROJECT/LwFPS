package io.pmr.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.IModGuiFactory.RuntimeOptionCategoryElement;

import java.util.Set;

public class GUIFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft instance) {
    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ConfigGUI(parentScreen);
    }

    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigGUI.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    public Set<RuntimeOptionCategoryElement> getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
