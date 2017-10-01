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

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */



@ConfigHeader(comment = {
"# ===--- RandomItems ---------------------------------------------------------------------------------------------=== #",
"#                                                                                                                      ",
"# RandomItems is a plugin and an API which make easy to drop or give to players a random item                          ",
"# or even send a command from a pool of items depending on the weight associated to those items.                       ",
"#                                                                                                                      ",
"# ===------------------------------------------------------------------------------------------ © 2017 Hexosse ---=== #"
})
@ConfigFooter(comment = {
" ",
"# ===--- Enjoy -------------------------------------------------------------------------------- © 2017 Hexosse ---=== #"
})
public class Config extends Configuration
{
	/* Plugin */
	@ConfigPath(path = "plugin")
	@ConfigValue(path = "plugin.useMetrics", comment = "Enable metrics")        public boolean useMetrics = (boolean) true;
	@ConfigValue(path = "plugin.useUpdater", comment = "Enable updater")        public boolean useUpdater = (boolean) true;
	@ConfigValue(path = "plugin.downloadUpdate", comment = "Download update")   public boolean downloadUpdate = (boolean) true;

	/* Localization */
	@ConfigValue(path = "locale")								public String locale = "en_US";

	/* Messages */
	@ConfigValue(path = "messages", comment = "Messages file")	public String messages = "Messages.yml";


	public Config(JavaPlugin plugin, String fileName, boolean load)
	{
		super(plugin, fileName);

		if(load) load();
	}
}
