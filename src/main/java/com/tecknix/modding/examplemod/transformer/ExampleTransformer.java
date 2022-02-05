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

package com.tecknix.modding.examplemod.transformer;

import com.tecknix.modding.api.transform.IModTransformer;

/**
 * Example transformer...
 * This transformer can be used to manipulate classes with raw asm or alternative frameworks!
 *
 * @author Tecknix Software
 */
public class ExampleTransformer implements IModTransformer {

    @Override
    public byte[] transform(String s, byte[] bytes) {
        return bytes;
    }
}
