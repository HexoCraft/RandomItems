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

import com.github.hexocraft.random.items.RandomItemsApi;
import com.github.hexocraft.random.items.RandomItemsPlugin;
import com.github.hexocraft.random.items.command.ArgType.ArgTypeRandomPool;
import com.github.hexocraft.random.items.configuration.Permissions;
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandArgument;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.command.type.ArgTypeInteger;
import com.github.hexocraftapi.command.type.ArgTypeString;
import com.github.hexocraftapi.message.predifined.message.ErrorPrefixedMessage;
import com.github.hexocraftapi.message.predifined.message.SimplePrefixedMessage;
import com.github.hexocraftapi.util.PlayerUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.github.hexocraft.random.items.command.RiCommands.commandMessageColor;
import static com.github.hexocraft.random.items.command.RiCommands.prefix;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class RiCommandAdd extends Command<RandomItemsPlugin>
{
	public RiCommandAdd(RandomItemsPlugin plugin)
	{
		super("add", plugin);
		this.setAliases(Lists.newArrayList("a"));
		this.setDescription(StringUtils.join(plugin.messages.cAdd,"\n"));
		this.setPermission(Permissions.CREATE.toString());

		this.addArgument(new CommandArgument<String>("name", ArgTypeRandomPool.get(), true, true, plugin.messages.cAddArgName));
		this.addArgument(new CommandArgument<Integer>("weight", ArgTypeInteger.get(), true, true, plugin.messages.cAddArgWeight));
		this.addArgument(new CommandArgument<String>("Description", ArgTypeString.get(), false, false, plugin.messages.cAddArgDesc));
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
		String name = commandInfo.getNamedArg("name");
		Integer weight = Integer.parseInt(commandInfo.getNamedArg("weight"));
		String description = commandInfo.getNamedArg("Description") != null ? commandInfo.getNamedArg("Description") : null;

		//
		if(!RandomItemsApi.contains(name)) return false;

		// Get item in hand
		Player player = commandInfo.getPlayer();
		ItemStack itemStack = PlayerUtil.getItemInHand(player);

		//
		if(RandomItemsApi.add(name, description, itemStack, weight) != null){
			SimplePrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.sAdd.replace("{NAME}",name), commandMessageColor);
			RandomItemsApi.save();
			return true;
		}
		else {
			ErrorPrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.eAdd.replace("{NAME}",name));
			return false;
		}
	}
}
