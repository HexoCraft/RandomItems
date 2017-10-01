package com.github.hexocraft.random.items.command;

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

import com.github.hexocraft.random.items.RandomItemsPlugin;
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.message.Prefix;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;

import static com.github.hexocraft.random.items.RandomItemsPlugin.instance;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class RiCommands extends Command<RandomItemsPlugin>
{
	public static Prefix prefix = new Prefix(instance.messages.chatPrefix);
	public static ChatColor commandMessageColor = ChatColor.GOLD;


	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public RiCommands(RandomItemsPlugin plugin)
	{
		super("RandomItems", plugin);
		this.setAliases(Lists.newArrayList("ri", "ris"));

		// Sub commands
		this.addSubCommand(new RiCommandCreate(plugin));		// Create a list (pool)
		this.addSubCommand(new RiCommandDelete(plugin));		// Delete a list
		this.addSubCommand(new RiCommandAdd(plugin));			// Add an item to the list
		this.addSubCommand(new RiCommandAddCommand(plugin));	// Add an item to the list
		this.addSubCommand(new RiCommandGive(plugin));			// Give an item to the player
		this.addSubCommand(new RiCommandGiveAll(plugin));		// Give an item to all players
		this.addSubCommand(new RiCommandSpawn(plugin));			// spawn an item to location
		this.addSubCommand(new RiCommandList(plugin));			// List
		this.addSubCommand(new RiCommandReload(plugin));		// Reload the plugin
		this.addSubCommand(new RiCommandHelp(plugin));			// Display help

		//
		prefix = new Prefix(plugin.messages.chatPrefix);
	}

	/**
	 * Executes the given command, returning its success
	 *
	 * @param commandInfo Info about the command
	 *
	 * @return true if a valid command, otherwise false
	 */
	@Override
	public boolean onCommand(CommandInfo commandInfo)
	{
		plugin.getServer().dispatchCommand(commandInfo.getSender(), "RandomItems help");

		return true;
	}
}
