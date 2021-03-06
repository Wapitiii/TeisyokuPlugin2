package net.jp.minecraft.plugins.teisyokuplugin2.module;

import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2<br />
 * パーミッションを定義する列挙型クラス
 *
 * @author syokkendesuyo
 */
public enum Permission {

    /**
     * ユーザーがデフォルトで使用可能な機能に対して付与されているパーミッション<br />
     * デフォルトで全ユーザーに付与されています。
     */
    USER("teisyoku.user"),

    /**
     * 管理者が上位機能にアクセスするためのパーミッション。<br />
     * デフォルトでオペレータに対して付与されています。
     */
    ADMIN("teisyoku.admin"),

    AD("teisyoku.ad"),
    AD_COOLTIME("teisyoku.ad.cooltime"),

    CALL("teisyoku.call"),

    CART("teisyoku.cart"),
    CART_GIVE("teisyoku.cart.give"),

    COLOR("teisyoku.color"),

    DAUNII_USE("teisyoku.daunii.use"),
    DAUNII_SUMMON("teisyoku.daunii.summon"),

    FLY_ME("teisyoku.fly.me"),
    FLY_OTHERS("teisyoku.fly.others"),

    GIFT("teisyoku.gift"),

    HORSE("teisyoku.horse"),
    HORSE_ADMIN("teisyoku.horse.admin"),
    HORSE_BYPASS_RIDE("teisyoku.horse.bypass.ride"),
    HORSE_BYPASS_DAMAGE("teisyoku.horse.bypass.damage"),

    NICKNAME("teisyoku.nickname"),
    NICKNAME_ADMIN("teisyoku.nickname.admin"),

    PLAYER_DATA("teisyoku.playerdata"),

    PLAYERS("teisyoku.players"),

    RAILWAYS("teisyoku.railways"),

    SIGNEDIT("teisyoku.signedit"),

    TABNAME("teisyoku.tabname"),

    TEISYOKU_MENU("teisyoku.menu"),

    TFLAG("teisyoku.tflag"),
    TFLAG_SEARCH("teisyoku.tflag.search"),

    TPOINT("teisyoku.tpoint"),
    TPOINT_ADMIN("teisyoku.tpoint.admin"),

    TPS("teisyoku.tps"),

    TRASH("teisyoku.trash"),

    PORTAL_BYPASS_END("teisyoku.portal.bypass.end"),
    PORTAL_BYPASS_NETHER("teisyoku.portal.bypass.nether"),

    VILLAGER_BYPASS_DAMAGE("teisyoku.villager.bypass.damage"),

    ;

    /**
     * パーミッション
     */
    private final String perm;

    /**
     * パーミッションを定義
     *
     * @param permission パーミッション
     */
    Permission(String permission) {
        this.perm = permission;
    }

    /**
     * 複数のパーミッションを同時にチェックし、1件でも保有しているパーミッションがあった場合、trueを返します。
     *
     * @param sender      コマンド
     * @param permissions パーミッション
     * @return パーミッションの状態
     */
    public static boolean hasPermission(CommandSender sender, Permission... permissions) {
        for (Permission permission : permissions) {
            if (sender.hasPermission(permission.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * パーミッションを返却します。
     *
     * @return パーミッション
     */
    public String toString() {
        return this.perm;
    }
}
