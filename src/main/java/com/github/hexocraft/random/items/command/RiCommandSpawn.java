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
import com.github.hexocraftapi.command.type.ArgTypeDouble;
import com.github.hexocraftapi.command.type.ArgTypeWorld;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class RiCommandSpawn extends Command<RandomItemsPlugin>
{
	public RiCommandSpawn(RandomItemsPlugin plugin)
	{
		super("spawn", plugin);
		this.setAliases(Lists.newArrayList("s"));
		this.setDescription(plugin.messages.cSpawn);
		this.setPermission(Permissions.CREATE.toString());

		this.addArgument(new CommandArgument<String>("name", ArgTypeRandomPool.get(), true, true, plugin.messages.cSpawnArgName));
		this.addArgument(new CommandArgument<World>("world", ArgTypeWorld.get(), true, true, plugin.messages.cSpawnWorld));
		this.addArgument(new CommandArgument<Double>("x", ArgTypeDouble.get(), true, true, plugin.messages.cSpawnX));
		this.addArgument(new CommandArgument<Double>("y", ArgTypeDouble.get(), true, true, plugin.messages.cSpawnY));
		this.addArgument(new CommandArgument<Double>("z", ArgTypeDouble.get(), true, true, plugin.messages.cSpawnZ));
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
		World world = Bukkit.getServer().getWorld(commandInfo.getNamedArg("world"));
		double x = Double.parseDouble(commandInfo.getNamedArg("x"));
		double y = Double.parseDouble(commandInfo.getNamedArg("y"));
		double z = Double.parseDouble(commandInfo.getNamedArg("z"));

		//
		if(!RandomItemsApi.contains(name)) return false;

		// Spawn
		RandomItemsApi.spawn(world, x, y ,z, RandomItemsApi.get(name).getRandom());

		return true;
	}
}
