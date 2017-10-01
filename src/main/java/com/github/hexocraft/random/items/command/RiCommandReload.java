package com.github.hexocraft.random.items.command;

/*
 * Copyright 2017 hexosse
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

import com.github.hexocraft.random.items.RandomItemsPlugin;
import com.github.hexocraft.random.items.configuration.Messages;
import com.github.hexocraft.random.items.configuration.Permissions;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.command.predifined.CommandReload;
import com.github.hexocraftapi.message.predifined.message.PluginMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.hexocraft.random.items.RandomItemsPlugin.config;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class RiCommandReload extends CommandReload<RandomItemsPlugin>
{
	public RiCommandReload(RandomItemsPlugin plugin)
	{
		super(plugin, Permissions.ADMIN.toString());
		this.setDescription(StringUtils.join(plugin.messages.cReload,"\n"));
	}

	@Override
	public boolean onCommand(final CommandInfo commandInfo)
	{
		final Player player = commandInfo.getPlayer();

		super.onCommand(commandInfo);

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				// Reload config file
				plugin.config.load();
				// Reload message file
				plugin.messages = new Messages(plugin, config.messages, true);
				// Reload sounds file
				plugin.items.load();

				// Send message
				PluginMessage.toSenders(commandInfo.getSenders(),plugin, plugin.messages.sReload, ChatColor.YELLOW);
			}

		}.runTask(plugin);

		return true;
	}
}