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
package net.skinsrestorer.shared.storage.adapter.file.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.skinsrestorer.api.property.SkinIdentifier;
import net.skinsrestorer.api.property.SkinType;
import net.skinsrestorer.api.property.SkinVariant;
import net.skinsrestorer.shared.storage.model.player.PlayerData;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PlayerFile {
    private static final int CURRENT_DATA_VERSION = 1;
    private UUID uniqueId;
    private IdentifierFile skinIdentifier;
    private int dataVersion;

    public static PlayerFile fromPlayerData(PlayerData playerData) {
        PlayerFile playerFile = new PlayerFile();
        playerFile.uniqueId = playerData.getUniqueId();
        playerFile.skinIdentifier = IdentifierFile.of(playerData.getSkinIdentifier());
        playerFile.dataVersion = CURRENT_DATA_VERSION;
        return playerFile;
    }

    public PlayerData toPlayerData() {
        return PlayerData.of(uniqueId, skinIdentifier == null ? null : skinIdentifier.toIdentifier());
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class IdentifierFile {
        private String identifier;
        private SkinVariant skinVariant;
        private SkinType type;

        public static IdentifierFile of(SkinIdentifier identifier) {
            if (identifier == null) {
                return null;
            }

            return new IdentifierFile(identifier.getIdentifier(), identifier.getSkinVariant(), identifier.getSkinType());
        }

        public SkinIdentifier toIdentifier() {
            return SkinIdentifier.of(identifier, skinVariant, type);
        }
    }
}
