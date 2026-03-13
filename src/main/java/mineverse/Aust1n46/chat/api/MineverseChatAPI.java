package mineverse.Aust1n46.chat.api;

import java.util.*;

import org.bukkit.entity.Player;

import mineverse.Aust1n46.chat.MineverseChat;

/**
 * API class for looking up wrapped {@link MineverseChatPlayer} objects from
 * {@link Player}, {@link UUID}, or {@link String} user names.
 *
 * @author Aust1n46
 */
public final class MineverseChatAPI {
    private static final Map<UUID, MineverseChatPlayer> PLAYER_MAP = new HashMap<>();
    private static final Map<String, UUID> NAMES_MAP = new HashMap<String, UUID>();
    private static final Map<UUID, MineverseChatPlayer> ONLINE_PLAYER_MAP = new HashMap<>();
    private static final List<String> NETWORK_PLAYER_NAMES = new ArrayList<>();
    private static final Map<UUID, SynchronizedMineverseChatPlayer> PROXY_PLAYER_MAP = new HashMap<>();

    public static List<String> getNetworkPlayerNames() {
        return NETWORK_PLAYER_NAMES;
    }

    public static void clearNetworkPlayerNames() {
        NETWORK_PLAYER_NAMES.clear();
    }

    public static void addNetworkPlayerName(String name) {
        NETWORK_PLAYER_NAMES.add(name);
    }

    public static void addSynchronizedMineverseChatPlayerToMap(SynchronizedMineverseChatPlayer smcp) {
        PROXY_PLAYER_MAP.put(smcp.getUUID(), smcp);
    }
    
    public static void clearProxyPlayerMap() {
        PROXY_PLAYER_MAP.clear();
    }

    public static Collection<SynchronizedMineverseChatPlayer> getSynchronizedMineverseChatPlayers() {
        return PROXY_PLAYER_MAP.values();
    }

    public static void addNameToMap(MineverseChatPlayer mcp) {
        NAMES_MAP.put(mcp.getName(), mcp.getUUID());
    }

    public static void removeNameFromMap(String name) {
        NAMES_MAP.remove(name);
    }

    public static void clearNameMap() {
        NAMES_MAP.clear();
    }

    @SuppressWarnings("deprecation")
    public static void addMineverseChatPlayerToMap(MineverseChatPlayer mcp) {
        PLAYER_MAP.put(mcp.getUUID(), mcp);
        MineverseChat.players.add(mcp);
    }

    @SuppressWarnings("deprecation")
    public static void clearMineverseChatPlayerMap() {
        PLAYER_MAP.clear();
        MineverseChat.players.clear();
    }

    public static Collection<MineverseChatPlayer> getMineverseChatPlayers() {
        return PLAYER_MAP.values();
    }

    @SuppressWarnings("deprecation")
    public static void addMineverseChatOnlinePlayerToMap(MineverseChatPlayer mcp) {
        ONLINE_PLAYER_MAP.put(mcp.getUUID(), mcp);
        MineverseChat.onlinePlayers.add(mcp);
    }

    @SuppressWarnings("deprecation")
    public static void removeMineverseChatOnlinePlayerToMap(MineverseChatPlayer mcp) {
        ONLINE_PLAYER_MAP.remove(mcp.getUUID());
        MineverseChat.onlinePlayers.remove(mcp);
    }

    @SuppressWarnings("deprecation")
    public static void clearOnlineMineverseChatPlayerMap() {
        ONLINE_PLAYER_MAP.clear();
        MineverseChat.onlinePlayers.clear();
    }

    public static Collection<MineverseChatPlayer> getOnlineMineverseChatPlayers() {
        return ONLINE_PLAYER_MAP.values();
    }

    /**
     * Get a MineverseChatPlayer wrapper from a Bukkit Player instance.
     *
     * @param player {@link Player} object.
     * @return {@link MineverseChatPlayer}
     */
    public static MineverseChatPlayer getMineverseChatPlayer(Player player) {
        return getMineverseChatPlayer(player.getUniqueId());
    }

    /**
     * Get a MineverseChatPlayer wrapper from a UUID.
     *
     * @param uuid {@link UUID}.
     * @return {@link MineverseChatPlayer}
     */
    public static MineverseChatPlayer getMineverseChatPlayer(UUID uuid) {
        return PLAYER_MAP.get(uuid);
    }

    /**
     * Get a MineverseChatPlayer wrapper from a user name.
     *
     * @param name {@link String}.
     * @return {@link MineverseChatPlayer}
     */
    public static MineverseChatPlayer getMineverseChatPlayer(String name) {
        return getMineverseChatPlayer(NAMES_MAP.get(name));
    }

    /**
     * Get a MineverseChatPlayer wrapper from a Bukkit Player instance. Only checks
     * current online players. Much more efficient!
     *
     * @param player {@link Player} object.
     * @return {@link MineverseChatPlayer}
     */
    public static MineverseChatPlayer getOnlineMineverseChatPlayer(Player player) {
        return getOnlineMineverseChatPlayer(player.getUniqueId());
    }

    /**
     * Get a MineverseChatPlayer wrapper from a UUID. Only checks current online
     * players. Much more efficient!
     *
     * @param uuid {@link UUID}.
     * @return {@link MineverseChatPlayer}
     */
    public static MineverseChatPlayer getOnlineMineverseChatPlayer(UUID uuid) {
        return ONLINE_PLAYER_MAP.get(uuid);
    }

    /**
     * Get a MineverseChatPlayer wrapper from a user name. Only checks current
     * online players. Much more efficient!
     *
     * @param name {@link String}.
     * @return {@link MineverseChatPlayer}
     */
    public static MineverseChatPlayer getOnlineMineverseChatPlayer(String name) {
        return getOnlineMineverseChatPlayer(NAMES_MAP.get(name));
    }

    /**
     * Get a SynchronizedMineverseChatPlayer from a UUID.
     *
     * @param uuid {@link UUID}
     * @return {@link SynchronizedMineverseChatPlayer}
     */
    public static SynchronizedMineverseChatPlayer getSynchronizedMineverseChatPlayer(UUID uuid) {
        return PROXY_PLAYER_MAP.get(uuid);
    }
}
