package com.github.hexocraft.random.items.command.ArgType;

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

import com.github.hexocraft.random.items.RandomItemsApi;
import com.github.hexocraft.random.items.RandomItemsPlugin;
import com.github.hexocraft.random.items.radomitem.RandomPool;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.command.type.ArgType;
import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ArgTypeRandomPool implements ArgType<String>
{
	private ArgTypeRandomPool() {};
	private static ArgTypeRandomPool t = new ArgTypeRandomPool();
	public static ArgTypeRandomPool get() { return t; }

	@Override
	public boolean check(String name)
	{
		return RandomItemsApi.contains(name);
	}

	@Override
	public String get(String name)
	{
		try
		{
			return check(name) ? name : "";
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public List<String> tabComplete(CommandInfo commandInfo)
	{
		// Last word
		String lastWord = commandInfo.numArgs() == 0 ? "" : commandInfo.getArgs().get(commandInfo.numArgs()-1);

		// List of events
		Map<String, RandomPool> randomItemMap = RandomItemsPlugin.instance.items.getMap();
		if(randomItemMap == null)
			return ImmutableList.of();

		ArrayList<String> matchedEvents = new ArrayList<String>();
		for(final Map.Entry<String,RandomPool> entry : randomItemMap.entrySet())
		{
			final String name = entry.getKey();

			if(StringUtil.startsWithIgnoreCase(name, lastWord))
				matchedEvents.add(name);
		}

		return matchedEvents;
	}
}
