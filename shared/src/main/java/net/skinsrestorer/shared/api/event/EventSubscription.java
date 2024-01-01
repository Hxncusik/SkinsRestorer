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
package net.skinsrestorer.shared.api.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.skinsrestorer.api.event.SkinsRestorerEvent;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class EventSubscription<E extends SkinsRestorerEvent> {
    private final WeakReference<Object> plugin;
    private final Class<E> eventClass;
    private final WeakReference<Consumer<E>> listener;
}
