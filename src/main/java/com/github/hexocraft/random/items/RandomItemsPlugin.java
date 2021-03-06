package com.github.hexocraft.random.items;

/*
 * Copyright 2015 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexocraft.random.items.command.RiCommands;
import com.github.hexocraft.random.items.configuration.Config;
import com.github.hexocraft.random.items.configuration.Items;
import com.github.hexocraft.random.items.configuration.Messages;
import com.github.hexocraft.random.items.integrations.langUtilsHooker;
import com.github.hexocraftapi.integration.Hook;
import com.github.hexocraftapi.message.Line;
import com.github.hexocraftapi.message.predifined.message.PluginMessage;
import com.github.hexocraftapi.message.predifined.message.PluginTitleMessage;
import com.github.hexocraftapi.plugin.Plugin;
import com.github.hexocraftapi.updater.BukkitUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class RandomItemsPlugin extends Plugin
{
	public static RandomItemsPlugin instance = null;
	public static Config            config   = null;
	public static Messages          messages = null;
	public static Items             items    = null;

	/* Plugins */
	public static langUtilsHooker 	langUtils = null;

	@Override
	public void onEnable()
	{
        /* Instance du plugin */
		instance = this;

		/* Chargement de la configuration */
		config = new Config(this, "config.yml", true);
		messages = new Messages(this, config.messages, true);
		items = new Items(this, "items.yml", true);

		/* Enregistrement des commandes */
		registerCommands(new RiCommands(this));

		/* LangUtils */
		langUtils = (langUtilsHooker) new Hook(langUtilsHooker.class, "LangUtils", "com.meowj.langutils.LangUtils").get();

		/* Enable message */
		PluginTitleMessage titleMessage = new PluginTitleMessage(this, "RandomItems is enable ...", ChatColor.YELLOW);
		if(langUtils != null) titleMessage.add("Integration with " + ChatColor.YELLOW + langUtils.get().getName());
		titleMessage.send(Bukkit.getConsoleSender());

        /* Updater */
		runUpdater(getServer().getConsoleSender(), 20 * 10);

        /* Metrics */
		runMetrics(20 * 2);
	}

	@Override
	public void onDisable()
	{
		super.onDisable();

		PluginMessage.toConsole(this, "Disabled", ChatColor.RED, new Line("SoundEffect is now disabled", ChatColor.DARK_RED));
	}


	public void runUpdater(final CommandSender sender, int delay)
	{
		if(config.useUpdater)
			super.runUpdater(new BukkitUpdater(this, "278925"), sender, config.downloadUpdate ,delay);
	}

	private void runMetrics(int delay)
	{
		if(config.useMetrics)
			super.RunMetrics(delay);
	}
}
