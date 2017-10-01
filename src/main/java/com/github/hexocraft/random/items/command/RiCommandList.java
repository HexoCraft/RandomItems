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
import com.github.hexocraft.random.items.radomitem.RandomItem;
import com.github.hexocraft.random.items.radomitem.RandomPool;
import com.github.hexocraftapi.chat.MessageBuilder;
import com.github.hexocraftapi.chat.event.ClickEvent;
import com.github.hexocraftapi.chat.event.HoverEvent;
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandArgument;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.message.Line;
import com.github.hexocraftapi.message.Message;
import com.github.hexocraftapi.message.Sentence;
import com.github.hexocraftapi.message.predifined.MessageColor;
import com.github.hexocraftapi.message.predifined.line.Title;
import com.github.hexocraftapi.message.predifined.message.EmptyMessage;
import com.github.hexocraftapi.message.predifined.message.TitleMessage;
import com.github.hexocraftapi.message.predifined.message.WarningMessage;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.util.Map;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class RiCommandList extends Command<RandomItemsPlugin>
{
	public RiCommandList(RandomItemsPlugin plugin)
	{
		super("list", plugin);
		this.setAliases(Lists.newArrayList("l"));
		this.setDescription(StringUtils.join(plugin.messages.cList,"\n"));
		this.setPermission(Permissions.CREATE.toString());

		this.addArgument(new CommandArgument<String>("name", ArgTypeRandomPool.get(), false, false, plugin.messages.cListArgName));
	}

	@Override
	public boolean onCommand(CommandInfo commandInfo)
	{
		String name = commandInfo.getNamedArg("name");

		if(RandomItemsApi.Count() == 0)
		{
			WarningMessage.toPlayer(commandInfo.getPlayer(), plugin.messages.eListEmpty);
		}
		else
		{
			if(name == null)
			{
				// Empty line
				EmptyMessage.toPlayer(commandInfo.getPlayer());

				// Title line
				Title title = new Title('-', ChatColor.AQUA, new Sentence(plugin.messages.mList, ChatColor.YELLOW));
				TitleMessage.toPlayer(commandInfo.getPlayer(), title);

				// Pools
				for(Map.Entry<String, RandomPool> entry : RandomItemsApi.getList().entrySet())
				{
					// Create the clickable sentence
					Sentence poolName = new Sentence(entry.getValue().getName() + (entry.getValue().getItemCount() > 0 ? " : " : ""));

					MessageBuilder hoverText = new MessageBuilder("");
					hoverText.append(plugin.messages.mListClick).color(MessageColor.SUBCOMMAND.color());
					ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ri " + "l" + " " + entry.getValue().getName());
					HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText.create());
					poolName.color(MessageColor.COMMAND.color()).event(clickEvent).event(hoverEvent);

					// Line
					Line poolLine = new Line();
					poolLine.add(new Sentence(Character.toString('\u00BB') + " ").color(MessageColor.COMMAND.color()));
					// - Pool
					poolLine.add(poolName);
					// - Number of items
					if(entry.getValue().getItemCount() > 0)
						poolLine.add(new Sentence(""+entry.getValue().getItemCount() + " items").color(MessageColor.MANDATORY_ARGUMENT.color()));

					new Message(poolLine).send(commandInfo.getPlayer());
				}
			}
			else
			{
				if(RandomItemsApi.get(name)==null)
				{
					WarningMessage.toPlayer(commandInfo.getPlayer(), plugin.messages.eListEmpty);
				}
				else
				{
					RandomPool pool= RandomItemsApi.get(name);

					// Empty line
					EmptyMessage.toPlayer(commandInfo.getPlayer());

					// Title line
					Title title = new Title('-', ChatColor.AQUA, new Sentence(plugin.messages.mList + " : " + pool.getName(), ChatColor.YELLOW));
					TitleMessage.toPlayer(commandInfo.getPlayer(), title);

					// Items
					for(RandomItem item : pool.getList())
					{
						// Item name / desc / ...
						String itemName = "";
						if(item.getItemStack() != null)
							itemName = item.getName() + " (" + item.getItemStack().getAmount() + ")";
						else if(item.getCommand() != null)
							itemName = "Command : " + item.getCommand();
						if(itemName.isEmpty()==false)
							itemName +=  " : " + item.getWeight();

						// Create the clickable sentence
						Sentence itemInfo = new Sentence(itemName);

						// Line
						Line itemLine = new Line();
						itemLine.add(new Sentence(Character.toString('\u00BB') + " ").color(MessageColor.COMMAND.color()));
						// - Pool
						itemLine.add(itemInfo);

						new Message(itemLine).send(commandInfo.getPlayer());
					}
				}
			}
		}

		return true;
	}
}