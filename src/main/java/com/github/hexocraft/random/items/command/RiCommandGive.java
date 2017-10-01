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
import com.github.hexocraftapi.command.type.ArgTypePlayer;
import com.github.hexocraftapi.util.PlayerUtil;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class RiCommandGive extends Command<RandomItemsPlugin>
{
	public RiCommandGive(RandomItemsPlugin plugin)
	{
		super("give", plugin);
		this.setAliases(Lists.newArrayList("g"));
		this.setDescription(plugin.messages.cGive);
		this.setPermission(Permissions.CREATE.toString());

		this.addArgument(new CommandArgument<String>("name", ArgTypeRandomPool.get(), true, true, plugin.messages.cGiveArgName));
		this.addArgument(new CommandArgument<Player>("player", ArgTypePlayer.get(), false, false, plugin.messages.cGiveArgPlayer));
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
		Player player = PlayerUtil.getPlayer(commandInfo.getNamedArg("player"));
		player = player != null ? player : commandInfo.getPlayer();

		//
		if(!RandomItemsApi.contains(name)) return false;
		if(player == null) return false;

		//
		RandomItemsApi.give(player, RandomItemsApi.get(name).getRandom());
		return true;
	}
}
