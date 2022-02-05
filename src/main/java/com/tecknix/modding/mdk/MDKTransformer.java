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

package com.tecknix.modding.mdk;

import com.tecknix.modding.api.transform.IModTransformer;
import net.minecraft.launchwrapper.IClassTransformer;

public class MDKTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytesIn) {

        for (final IModTransformer modTransformer : MDKDevLoader.getInstance().getModTransformers()) {
            final byte[] transformed = modTransformer.transform(name, bytesIn);

            //This should allow you to transform a class with multiple transformers.
            if (transformed != bytesIn) {
                return transformed;
            }
        }

        return bytesIn;
    }
}
