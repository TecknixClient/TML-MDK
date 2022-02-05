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

package com.tecknix.modding.examplemod;

import com.tecknix.modding.api.TecknixMod;
import com.tecknix.modding.examplemod.transformer.ExampleTransformer;

/**
 * ExampleMod main class. Feel free to develop off of this base!
 *
 * @author Tecknix Software
 */
public class ExampleMod extends TecknixMod {

    @Override
    public void onInitialize() {
        System.err.println("Example mod has been initialized.");
        this.registerModTransformers(new ExampleTransformer());
    }

    @Override
    public void onTerminate() {

    }
}
