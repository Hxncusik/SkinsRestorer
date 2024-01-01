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
package net.skinsrestorer.bukkit.paper;

import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;

public class PaperUtil {
    public static YamlConfiguration getPaperConfig(Server server) {
        try {
            return (YamlConfiguration) Server.Spigot.class.getMethod("getPaperConfig").invoke(server.spigot());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
