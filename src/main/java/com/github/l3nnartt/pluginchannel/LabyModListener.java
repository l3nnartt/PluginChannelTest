package com.github.l3nnartt.pluginchannel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class LabyModListener implements PluginMessageListener, Listener {

  @Override
  public void onPluginMessageReceived(@NotNull String channel, Player player, byte[] bytes) {
    final ByteBuf buf = Unpooled.wrappedBuffer(bytes);
    final String key = LabyModMessageApi.getInstance().readString(buf);
    final String content = LabyModMessageApi.getInstance().readString(buf);

    System.out.println("Received message from " + player.getName() + " with key " + key + " and content " + content);
    LabyModAPI.getInstance().sendCurrentPlayingGamemode(player, true, "GAMEMODE");
  }

  //@EventHandler
  //public void onPlayerJoin(PlayerJoinEvent event) {
  //  System.out.println(event.getPlayer() + " hat den Server betreten!");
  //  System.out.println(event.getPlayer() + " hat den Server betreten!");
  //  System.out.println(event.getPlayer() + " hat den Server betreten!");
  //  String content = "Hallo Welt 123";
  //  System.out.println("Sending message to " + event.getPlayer().getName() + " with key " + "gamemode" + " and content " + content);
  //  LabyModAPI.getInstance().sendCurrentPlayingGamemode(event.getPlayer(), true, content);
  //}
}