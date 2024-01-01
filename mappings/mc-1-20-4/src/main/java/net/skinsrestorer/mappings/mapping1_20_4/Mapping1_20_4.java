/*
 * SkinsRestorer
 * Copyright (C) 2024  SkinsRestorer Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.skinsrestorer.mappings.mapping1_20_4;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.effect.MobEffectInstance;
import net.skinsrestorer.mappings.shared.IMapping;
import net.skinsrestorer.mappings.shared.MappingReflection;
import net.skinsrestorer.mappings.shared.ViaPacketData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Mapping1_20_4 implements IMapping {
    private static void sendPacket(ServerPlayer player, Packet<?> packet) {
        player.connection.send(packet);
    }

    @Override
    public void accept(Player player, Predicate<ViaPacketData> viaFunction) {
        ServerPlayer entityPlayer = MappingReflection.getHandle(player, ServerPlayer.class);

        // Slowly getting from object to object till we get what is needed for
        // the respawn packet
        ServerLevel world = entityPlayer.serverLevel();

        CommonPlayerSpawnInfo spawnInfo = entityPlayer.createCommonSpawnInfo(world);
        ClientboundRespawnPacket respawn = new ClientboundRespawnPacket(
                spawnInfo,
                ClientboundRespawnPacket.KEEP_ALL_DATA
        );

        sendPacket(entityPlayer, new ClientboundPlayerInfoRemovePacket(List.of(player.getUniqueId())));
        sendPacket(entityPlayer, ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(List.of(entityPlayer)));

        if (viaFunction.test(new ViaPacketData(player, spawnInfo.seed(), spawnInfo.gameType().getId(), spawnInfo.isFlat()))) {
            sendPacket(entityPlayer, respawn);
        }

        entityPlayer.onUpdateAbilities();

        entityPlayer.connection.teleport(player.getLocation());

        // Send health, food, experience (food is sent together with health)
        entityPlayer.resetSentInfo();

        PlayerList playerList = entityPlayer.server.getPlayerList();
        playerList.sendPlayerPermissionLevel(entityPlayer);
        playerList.sendLevelInfo(entityPlayer, world);
        playerList.sendAllPlayerInfo(entityPlayer);

        // Resend their effects
        for (MobEffectInstance mobEffect : entityPlayer.getActiveEffects()) {
            ClientboundUpdateMobEffectPacket effect = new ClientboundUpdateMobEffectPacket(entityPlayer.getId(), mobEffect);
            sendPacket(entityPlayer, effect);
        }
    }

    @Override
    public Set<String> getSupportedVersions() {
        return Set.of(
                "60a2bb6bf2684dc61c56b90d7c41bddc" // 1.20.3 and 1.20.4
        );
    }
}
