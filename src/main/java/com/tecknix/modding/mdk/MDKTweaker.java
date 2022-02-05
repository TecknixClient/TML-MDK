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

import com.google.common.collect.Lists;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MDKTweaker implements ITweaker {

    private final List<String> launchArguments = Lists.newArrayList();

    @Override
    public final void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.launchArguments.addAll(args);

        if (!args.contains("--version") && profile != null) {
            this.launchArguments.add("--version");
            this.launchArguments.add(profile);
        }
        if (!args.contains("--assetDir") && assetsDir != null) {
            this.launchArguments.add("--assetDir");
            this.launchArguments.add(assetsDir.getAbsolutePath());
        }
        if (!args.contains("--gameDir") && gameDir != null) {
            this.launchArguments.add("--gameDir");
            this.launchArguments.add(gameDir.getAbsolutePath());
        }
    }

    @Override
    public final void injectIntoClassLoader(LaunchClassLoader classLoader) {
        //Load development environment.
        try {
            MDKDevLoader.loadDev();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Failed to determine or load mod main class.");
        }

        //Register MDK Transformer.
        classLoader.registerTransformer(MDKTransformer.class.getName());

        //Mixins.
        MixinBootstrap.init();

        final MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();

        /* Add the mod's mixin configuration. (This is optional if you wish to rely on events)... */
        Mixins.addConfiguration("mixins.mod.json");
        //Add the modding api's mixin configuration.
        Mixins.addConfiguration("mixins.moddingapi.json");

        if (environment.getObfuscationContext() == null) {
            environment.setObfuscationContext("notch");
        }

        environment.setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String getLaunchTarget() {
        return MixinBootstrap.getPlatform().getLaunchTarget();
    }

    @Override
    public String[] getLaunchArguments() {
        return this.launchArguments.toArray(new String[0]);
    }
}