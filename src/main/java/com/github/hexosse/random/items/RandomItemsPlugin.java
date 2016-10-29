package com.github.hexosse.random.items;

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

import com.github.hexocraftapi.message.Line;
import com.github.hexocraftapi.message.predifined.message.PluginMessage;
import com.github.hexocraftapi.message.predifined.message.PluginTitleMessage;
import com.github.hexocraftapi.plugin.Plugin;
import com.github.hexocraftapi.updater.GitHubUpdater;
import com.github.hexosse.random.items.command.RiCommands;
import com.github.hexosse.random.items.configuration.Config;
import com.github.hexosse.random.items.configuration.Items;
import com.github.hexosse.random.items.configuration.Messages;
import com.github.hexosse.random.items.integrations.LanguageUtils;
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

	public static LanguageUtils 	langUtils = null;

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
		langUtils = new LanguageUtils(this);

		/* Enable message */
		PluginTitleMessage titleMessage = new PluginTitleMessage(this, "ServerEvents is enable ...", ChatColor.YELLOW);
		if(langUtils.enabled()) titleMessage.add("Integration with " + ChatColor.YELLOW + langUtils.getName());
		titleMessage.send(Bukkit.getConsoleSender());

		/* Updater */
		if(config.useUpdater)
			runUpdater(getServer().getConsoleSender(), 20 * 10);

		/* Metrics */
		if(config.useMetrics)
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
		super.runUpdater(new GitHubUpdater(this, "HexoCraft/RandomItems"), sender, delay);
	}

	private void runMetrics(int delay)
	{
		super.RunMetrics(delay);
	}
}
