package com.github.l3nnartt.pluginchannel;

import org.bukkit.plugin.java.JavaPlugin;

public final class PluginChannelTest extends JavaPlugin {

    private static PluginChannelTest instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);
        getServer().getMessenger().registerIncomingPluginChannel(this, LabyModMessageApi.CHANNEL_NAME, new LabyModListener());
        getServer().getMessenger().registerOutgoingPluginChannel(this, LabyModMessageApi.CHANNEL_NAME);
        // register listener
        getServer().getPluginManager().registerEvents(new LabyModListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PluginChannelTest getInstance() {
        return instance;
    }

    public static void setInstance(PluginChannelTest instance) {
        PluginChannelTest.instance = instance;
    }
}