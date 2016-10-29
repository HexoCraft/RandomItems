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

import com.github.hexocraftapi.chat.MessageBuilder;
import com.github.hexocraftapi.chat.event.ClickEvent;
import com.github.hexocraftapi.chat.event.HoverEvent;
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandArgument;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.command.predifined.CommandHelp;
import com.github.hexocraftapi.command.predifined.CommandReload;
import com.github.hexocraftapi.command.type.ArgTypeDouble;
import com.github.hexocraftapi.command.type.ArgTypeInteger;
import com.github.hexocraftapi.command.type.ArgTypePlayer;
import com.github.hexocraftapi.command.type.ArgTypeString;
import com.github.hexocraftapi.command.type.ArgTypeWorld;
import com.github.hexocraftapi.message.Line;
import com.github.hexocraftapi.message.Message;
import com.github.hexocraftapi.message.Prefix;
import com.github.hexocraftapi.message.Sentence;
import com.github.hexocraftapi.message.predifined.MessageColor;
import com.github.hexocraftapi.message.predifined.line.Title;
import com.github.hexocraftapi.message.predifined.message.*;
import com.github.hexocraftapi.util.PlayerUtil;
import com.github.hexocraft.random.items.RandomItemsApi;
import com.github.hexocraft.random.items.RandomItemsPlugin;
import com.github.hexocraft.random.items.command.ArgType.ArgTypeRandomPool;
import com.github.hexocraft.random.items.configuration.Messages;
import com.github.hexocraft.random.items.configuration.Permissions;
import com.github.hexocraft.random.items.radomitem.RandomItem;
import com.github.hexocraft.random.items.radomitem.RandomPool;
import com.google.common.collect.Lists;
import com.meowj.langutils.lang.LanguageHelper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static com.github.hexocraft.random.items.RandomItemsPlugin.config;
import static com.github.hexocraft.random.items.RandomItemsPlugin.langUtils;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class RiCommands extends Command<RandomItemsPlugin>
{
	private final Prefix prefix;
	private final ChatColor messageColor = ChatColor.GOLD;

	enum cmd
	{
		create, c,
		delete, d,
		add, a,
		addc, ac,
		give, g,
		giveall, ga,
		spawn, s,
		list, l,
	}

	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public RiCommands(RandomItemsPlugin plugin)
	{
		super("RandomItems", plugin);
		this.setAliases(Lists.newArrayList("ri"));

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




	public class RiCommandCreate extends Command<RandomItemsPlugin>
	{
		public RiCommandCreate(RandomItemsPlugin plugin)
		{
			super(cmd.create.toString(), plugin);
			this.setAliases(Lists.newArrayList(cmd.c.toString()));
			this.setDescription(StringUtils.join(plugin.messages.cCreate,"\n"));
			this.setPermission(Permissions.CREATE.toString());

			this.addArgument(new CommandArgument<String>("name", ArgTypeString.get(), true, true, plugin.messages.cCreateArgName));
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

			//
			RandomItemsApi.add(name);

			//
			if(RandomItemsApi.contains(name))
			{
				SimplePrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.sCreate.replace("{NAME}",name), messageColor);
				RandomItemsApi.save();
				return true;
			}
			else
			{
				ErrorPrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.eCreate.replace("{NAME}",name));
				return false;
			}
		}
	}




	public class RiCommandDelete extends Command<RandomItemsPlugin>
	{
		public RiCommandDelete(RandomItemsPlugin plugin)
		{
			super(cmd.delete.toString(), plugin);
			this.setAliases(Lists.newArrayList(cmd.d.toString()));
			this.setDescription(StringUtils.join(plugin.messages.cDelete,"\n"));
			this.setPermission(Permissions.CREATE.toString());

			this.addArgument(new CommandArgument<String>("name", ArgTypeRandomPool.get(), true, true, plugin.messages.cDeleteArgName));
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

			//
			if(RandomItemsApi.remove(name)) {
				SimplePrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.sDelete.replace("{NAME}",name), messageColor);
				RandomItemsApi.save();
				return true;
			}
			else {
				ErrorPrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.eDelete.replace("{NAME}",name));
				return false;
			}
		}
	}




	public class RiCommandAdd extends Command<RandomItemsPlugin>
	{
		public RiCommandAdd(RandomItemsPlugin plugin)
		{
			super(cmd.add.toString(), plugin);
			this.setAliases(Lists.newArrayList(cmd.a.toString()));
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
				SimplePrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.sAdd.replace("{NAME}",name), messageColor);
				RandomItemsApi.save();
				return true;
			}
			else {
				ErrorPrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.eAdd.replace("{NAME}",name));
				return false;
			}
		}
	}




	public class RiCommandAddCommand extends Command<RandomItemsPlugin>
	{
		public RiCommandAddCommand(RandomItemsPlugin plugin)
		{
			super(cmd.addc.toString(), plugin);
			this.setAliases(Lists.newArrayList(cmd.ac.toString()));
			this.setDescription(StringUtils.join(plugin.messages.cAddc,"\n"));
			this.setPermission(Permissions.CREATE.toString());

			this.addArgument(new CommandArgument<String>("name", ArgTypeRandomPool.get(), true, true, plugin.messages.cAddcArgName));
			this.addArgument(new CommandArgument<String>("command", ArgTypeString.get(), true, true, plugin.messages.cAddcArgCmd));
			this.addArgument(new CommandArgument<Integer>("weight", ArgTypeInteger.get(), true, true, plugin.messages.cAddcArgWeight));
			this.addArgument(new CommandArgument<String>("Description", ArgTypeString.get(), false, false, plugin.messages.cAddcArgDesc));
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
			String command = commandInfo.getNamedArg("command");
			Integer weight = Integer.parseInt(commandInfo.getNamedArg("weight"));
			String description = commandInfo.getNamedArg("Description") != null ? commandInfo.getNamedArg("Description") : null;

			//
			if(!RandomItemsApi.contains(name)) return false;

			//
			if(RandomItemsApi.add(name, description, command, weight) != null){
				SimplePrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.sAddc.replace("{NAME}",name), messageColor);
				RandomItemsApi.save();
				return true;
			}
			else {
				ErrorPrefixedMessage.toPlayer(commandInfo.getPlayer(), prefix, plugin.messages.eAddc.replace("{NAME}",name));
				return false;
			}
		}
	}




	public class RiCommandGive extends Command<RandomItemsPlugin>
	{
		public RiCommandGive(RandomItemsPlugin plugin)
		{
			super(cmd.give.toString(), plugin);
			this.setAliases(Lists.newArrayList(cmd.g.toString()));
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




	public class RiCommandGiveAll extends Command<RandomItemsPlugin>
	{
		public RiCommandGiveAll(RandomItemsPlugin plugin)
		{
			super(cmd.giveall.toString(), plugin);
			this.setAliases(Lists.newArrayList(cmd.ga.toString()));
			this.setDescription(plugin.messages.cGiveAll);
			this.setPermission(Permissions.CREATE.toString());

			this.addArgument(new CommandArgument<String>("name", ArgTypeRandomPool.get(), true, true, plugin.messages.cGiveAllArgName));
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

			//
			if(!RandomItemsApi.contains(name)) return false;

			//
			for(Player player : PlayerUtil.getOnlinePlayers())
				RandomItemsApi.give(player, RandomItemsApi.get(name).getRandom());
			return true;
		}
	}




	public class RiCommandSpawn extends Command<RandomItemsPlugin>
	{
		public RiCommandSpawn(RandomItemsPlugin plugin)
		{
			super(cmd.spawn.toString(), plugin);
			this.setAliases(Lists.newArrayList(cmd.s.toString()));
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



	public class RiCommandList extends Command<RandomItemsPlugin>
	{
		public RiCommandList(RandomItemsPlugin plugin)
		{
			super(cmd.list.toString(), plugin);
			this.setAliases(Lists.newArrayList(cmd.l.toString()));
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
						ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ri  " + entry.getValue().getName());
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
							if(item.getItemStack() != null && item.getItemStack().getItemMeta() != null && item.getItemStack().getItemMeta().getDisplayName() != null)
								itemName = item.getItemStack().getItemMeta().getDisplayName() + "(" + item.getItemStack().getAmount() + ")";
							else if(item.getItemStack() != null)
							{
								if(langUtils.enabled())
									itemName = LanguageHelper.getItemName(item.getItemStack(), plugin.config.locale);
								else
									itemName = "" + item.getItemStack().getData().getItemType();
								itemName += " (" + item.getItemStack().getAmount() + ")";
							}
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




	public class RiCommandHelp extends CommandHelp<RandomItemsPlugin>
	{
		public RiCommandHelp(RandomItemsPlugin plugin)
		{
			super(plugin);
			this.setDescription(StringUtils.join(plugin.messages.cHelp,"\n"));
			this.setDisplayArguments(false);
			this.setDisplayInlineDescription(true);
		}
	}
}
