package com.github.hexocraft.random.items.configuration;

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

import com.github.hexocraftapi.configuration.Configuration;
import com.github.hexocraftapi.configuration.annotation.ConfigFooter;
import com.github.hexocraftapi.configuration.annotation.ConfigHeader;
import com.github.hexocraftapi.configuration.annotation.ConfigPath;
import com.github.hexocraftapi.configuration.annotation.ConfigValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

@ConfigHeader(comment = {
"# ===--- RandomItems ---------------------------------------------------------------------------------------------=== #",
"#                                                                                                                      ",
"# RandomItems is a plugin and an API which make easy to drop or give to players a random item                          ",
"# or even send a command from a pool of items depending on the weight associated to those items.                       ",
"#                                                                                                                      ",
"# ===------------------------------------------------------------------------------------------ © 2016 Hexosse ---=== #"
})
@ConfigFooter(comment = {
" ",
"# ===--- Enjoy -------------------------------------------------------------------------------- © 2016 Hexosse ---=== #"
})
public class Messages extends Configuration
{
	/* Chat */
	@ConfigPath(path = "chat")
	@ConfigValue(path = "chat.prefix") public String chatPrefix = "§3[§bRandomItems§3]§r";

	/* Commands */
	@ConfigPath(path = "commands", 		comment = "List of Messages used in commands")
	@ConfigValue(path = "commands.create.cmd")		public List<String> cCreate        = Arrays.asList("Create a new random items list", "Use /RandomItems create NAME to create an new list.");
	@ConfigValue(path = "commands.create.arg_name")	public String       cCreateArgName = "NAME of the list";
	@ConfigValue(path = "commands.delete.cmd")		public List<String> cDelete        = Arrays.asList("Delete a random items list", "Use /RandomItems delete NAME to delete a list.");
	@ConfigValue(path = "commands.delete.arg_name")	public String       cDeleteArgName = "NAME of the list";
	@ConfigValue(path = "commands.add.cmd")		 	public List<String> cAdd           = Arrays.asList("Add item in hand to the list", "Use /RandomItems add NAME WEIGHT.");
	@ConfigValue(path = "commands.add.arg_name")	public String       cAddArgName    = "NAME of the list";
	@ConfigValue(path = "commands.add.arg_chance")  public String       cAddArgWeight  = "Chance to get the item";
	@ConfigValue(path = "commands.add.arg_desc")    public String       cAddArgDesc    = "Description for the item";
	@ConfigValue(path = "commands.addc.cmd")		public List<String> cAddc          = Arrays.asList("Add command to the list", "Use /RandomItems adds NAME CMD WEIGHT.");
	@ConfigValue(path = "commands.addc.arg_name")	public String       cAddcArgName   = "NAME of the list";
	@ConfigValue(path = "commands.addc.arg_cmd") 	public String       cAddcArgCmd    = "Chance to get the item";
	@ConfigValue(path = "commands.addc.arg_chance") public String       cAddcArgWeight = "Chance to get the item";
	@ConfigValue(path = "commands.addc.arg_desc")   public String       cAddcArgDesc   = "Description for the item";
	@ConfigValue(path = "commands.give.cmd")		public String		cGive          = "Give an item from the random list";
	@ConfigValue(path = "commands.give.arg_player")	public String       cGiveArgPlayer = "Player the will receive the item";
	@ConfigValue(path = "commands.give.arg_name")	public String       cGiveArgName   = "Random list name";
	@ConfigValue(path = "commands.give.all.cmd")	public String		cGiveAll       = "Give an item to all players";
	@ConfigValue(path = "commands.give.all.arg_name")public String      cGiveAllArgName= "Random list name";
	@ConfigValue(path = "commands.spawn.cmd")	    public String		cSpawn         = "Spawn an item to location";
	@ConfigValue(path = "commands.spawn.arg_name")  public String      cSpawnArgName  = "Random list name";
	@ConfigValue(path = "commands.spawn.world")		public String 		cSpawnWorld    = "The world";
	@ConfigValue(path = "commands.spawn.x")			public String 		cSpawnX 	   = "X coordinate";
	@ConfigValue(path = "commands.spawn.y")			public String 		cSpawnY 	   = "Y coordinate";
	@ConfigValue(path = "commands.spawn.z")			public String 		cSpawnZ 	   = "Z coordinate";
	@ConfigValue(path = "commands.list.cmd")		public List<String> cList          = Arrays.asList("List of items", "Use /RandomItems list", "or  /RandomItems list NAME.");
	@ConfigValue(path = "commands.list.arg_name")	public String       cListArgName   = "NAME of the list";
	@ConfigValue(path = "commands.help.cmd")		public List<String> cHelp          = Arrays.asList("Display plugin help");
	@ConfigValue(path = "commands.reload.cmd")		public List<String> cReload        = Arrays.asList("Reload RandomItems");

	/* Messages */
	@ConfigPath(path = "messages", 		comment = "List of messages")
	@ConfigValue(path = "messages.list.list")		public String mList      = "List of random items";
	@ConfigValue(path = "messages.list.click")		public String mListClick = "Click here to get list of items";

	/* Success */
	@ConfigPath(path = "success", 		comment = "List of Messages used after a sucess command")
	@ConfigValue(path = "success.add")			public String sAdd    = "Item sucessfuly added to {NAME}";
	@ConfigValue(path = "success.add")			public String sAddc   = "Command sucessfuly added to {NAME}";
	@ConfigValue(path = "success.create")		public String sCreate = "List {NAME} created";
	@ConfigValue(path = "success.delete")		public String sDelete = "List {NAME} has been removed";
	@ConfigValue(path = "success.relaod")		public String sReload = "RandomItems has been reloaded";

	/* Errors */
	@ConfigPath(path = "errors", 		comment = "List of Messages used after a error command")
	@ConfigValue(path = "errors.add") 			public String eAdd = "Error while adding item to {NAME}";
	@ConfigValue(path = "errors.add") 			public String eAddc = "Error while adding command to {NAME}";
	@ConfigValue(path = "errors.create")	   	public String eCreate = "Error while creating list : {NAME}";
	@ConfigValue(path = "errors.give")	   		public String eGive = "Error while giving an item from {NAME}";
	@ConfigValue(path = "errors.delete")	   	public String eDelete = "Error while deleting list : {NAME}";
	@ConfigValue(path = "errors.list.empty")	public String eListEmpty = "There is no random items";


	public Messages(JavaPlugin plugin, String fileName, boolean load)
	{
		super(plugin, fileName);

		if(load) load();
	}
}
