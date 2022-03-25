/*
 *     Copyright Tecknix Software 2022.
 *
 *     This is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.tecknix.modding.examplemod.listener;

import com.tecknix.modding.api.event.TMSubscription;
import com.tecknix.modding.api.event.type.TMChatEvent;

/*
 * This listener is not registered. Ensure you register the listeners on game startup and not on mod startup!
 */
public class ExampleListener {

    @TMSubscription
    public void onChat(TMChatEvent event) {
        System.err.println("CHAT: " + event.getFormattedText());
    }
}
