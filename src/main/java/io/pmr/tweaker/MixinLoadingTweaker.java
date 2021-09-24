package io.pmr.tweaker;

import io.pmr.Perspective;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.List;

import static net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.*;

@SortingIndex(1)
public class MixinLoadingTweaker implements ITweaker {
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}

    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();
        try {
            Class<?> mixinsClass = Class.forName("org.spongepowered.asm.mixin.Mixins");
            Method addConfigurationMethod = mixinsClass.getMethod("addConfiguration", new Class[]{ String.class} );
            addConfigurationMethod.invoke(null, new Object[] { "mixins.pmr.json" });
        } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        }
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
        CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();
        try {
            Class<?> _class = Class.forName("net.minecraftforge.fml.relauncher.CoreModManager");
            Method getIgnoredMods = null;
            try {
                getIgnoredMods = _class.getDeclaredMethod("getIgnoredMods", new Class[0]);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            // #region
            try {
                if(getIgnoredMods == null)
                    getIgnoredMods = _class.getDeclaredMethod("getLoadedCoremods", new Class[0]);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            // #endregion
            // #region
            if(codeSource != null) {
                URL location = codeSource.getLocation();
                try {
                    File file = new File(location.toURI());
                    if ((file.isFile()))
                        try {
                            if(getIgnoredMods != null)
                                ((List)getIgnoredMods.invoke(null, new Object[0])).remove(file.getName());
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                Perspective.LOGGER.error("No CodeSource, if this is not a development environment we might run into problems!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return new String[0];
    }
}
