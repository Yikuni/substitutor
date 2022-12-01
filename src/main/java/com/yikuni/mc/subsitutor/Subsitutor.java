package com.yikuni.mc.subsitutor;

import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.reflect.context.ApplicationContext;
import com.yikuni.mc.subsitutor.utils.SubsitutorManager;
import com.yikuni.mc.subsitutor.utils.SubstitutorScoreboard;
import org.bukkit.plugin.java.JavaPlugin;

public final class Subsitutor extends JavaPlugin {

    private static Boolean started = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginLoader.run(Subsitutor.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void start(){
        started = true;
        ApplicationContext.getContext(SubstitutorScoreboard.class).initSidebar();
        SubsitutorManager.INSTANCE.initAllSubsitutor();
    }

    public static Boolean isStarted() {
        return started;
    }
}
