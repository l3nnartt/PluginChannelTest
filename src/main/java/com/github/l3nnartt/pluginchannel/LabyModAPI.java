package com.github.l3nnartt.pluginchannel;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

public class LabyModAPI {

  private static final LabyModAPI instance = new LabyModAPI();

  /**
   * @param player     who should receive the packet
   * @param key        key of the message
   * @param jsonObject json object to send
   */
   public void sendLabyModMessage(Player player, String key, final Object jsonObject) {
    byte[] bytes = LabyModMessageApi.getInstance().getBytes(key, jsonObject.toString());
    player.sendPluginMessage(PluginChannelTest.getInstance(), LabyModMessageApi.CHANNEL_NAME, bytes);
    System.out.println("#sendLabyModMessage Sending message to " + player.getName() + " with key " + key + " and content " + jsonObject);
  }

  /**
   * @param player       the { {@link Player} } to which the packet should be sent
   * @param visible      show gamemode
   * @param gameModeName the gamemode
   */
  public void sendCurrentPlayingGamemode(Player player, boolean visible, String gameModeName) {
    JsonObject object = new JsonObject();
    object.addProperty("show_gamemode", visible); // Gamemode visible for everyone
    object.addProperty("gamemode_name", gameModeName); // Name of the current playing gamemode

    // Send to LabyMod using the API
    sendLabyModMessage(player, "server_gamemode", object);
    System.out.println("#sendCurrentPlayingGamemode Sending message to " + player.getName() + " with key " + "server_gamemode" + " and content " + object);
  }

  public static LabyModAPI getInstance() {
    return instance;
  }
}