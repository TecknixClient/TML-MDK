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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tecknix.modding.api.TecknixMod;
import com.tecknix.modding.api.transform.IModTransformer;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link MDKDevLoader}
 * <p>
 * Used to load mods in the development environment
 *
 * @author Tecknix Software
 */
public class MDKDevLoader {

    @Setter @Getter private static TecknixMod instance = null;

    /**
     * loadDev()
     * <p>
     * Processes the mods' metadata json file and creates an instance of the mods main class.
     *
     * @author Tecknix Software
     */
    public static void loadDev() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        //Fetch the contents of the mod meta json.
        final InputStream is = MDKDevLoader.class.getClassLoader().getResourceAsStream("tecknix-mod.json");

        assert is != null;
        final Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

        final JsonElement element = new JsonParser().parse(reader);

        final JsonObject object = element.getAsJsonObject();

        //Parse the "mainClass" attribute.
        final String mainClass = object.get("mainClass").getAsString();

        //Fetch the class object from the class name.
        final Class<?> main = Class.forName(mainClass, true, MDKDevLoader.class.getClassLoader());

        final Class<? extends TecknixMod> mod = main.asSubclass(TecknixMod.class);

        //Create an instance of the mod.
        final Constructor<? extends TecknixMod> instance = mod.getConstructor();

        final TecknixMod tecknixMod = instance.newInstance();

        //Set the static instance to the mod class.
        setInstance(tecknixMod);

        //Call the mod's initialize method.
        tecknixMod.onInitialize();
    }

    public static List<IModTransformer> getTransformers() {
        List<IModTransformer> transformers = new ArrayList<>();
        final InputStream is = MDKDevLoader.class.getClassLoader().getResourceAsStream("tecknix-mod.json");

        assert is != null;
        final Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

        final JsonElement element = new JsonParser().parse(reader);

        final JsonObject object = element.getAsJsonObject();

        final JsonArray arr = object.get("modTransformers").getAsJsonArray();


        for (int i = 0; i < arr.size(); i++) {
            String s = arr.get(i).getAsString();


            try {
                final Class<?> main = Class.forName(s, true, MDKDevLoader.class.getClassLoader());

                final IModTransformer transformer = (IModTransformer) main.newInstance();

                transformers.add(transformer);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }

        return transformers;
    }
}
