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
import com.github.hexocraftapi.configuration.annotation.ConfigValue;
import com.github.hexocraftapi.configuration.collection.ConfigurationMap;
import com.github.hexocraft.random.items.radomitem.RandomPool;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */

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
public class Items extends Configuration
{
	@ConfigValue(path = "items", comment ="List of random items !")
	private ConfigurationMap<RandomPool> items = new ConfigurationMap();


	public Items(JavaPlugin plugin, String fileName, boolean load)
	{
		super(plugin, fileName);

		if(load) load();
	}

	/**
	 * Returns the list of sound effect
	 *
	 * @return the list of sound effect
	 */
	public ConfigurationMap<RandomPool> getMap()
	{
		return items;
	}

	/**
	 * Returns the number of sound effect in this list
	 *
	 * @return the number of sound effect in this list
	 */
	public int size()
	{
		return items.size();
	}

	/**
	 * Returns <tt>true</tt> if this list contains no sound effect.
	 *
	 * @return <tt>true</tt> if this list contains no sound effect
	 */
	public boolean isEmpty()
	{
		return items.isEmpty();
	}

	/**
	 * Appends the specified sound effect to the end of this list
	 *
	 * @param pool
	 * @return <tt>true</tt> (as specified by {@link Collection#add})
	 */
	public RandomPool add(RandomPool pool)
	{
		return items.put(pool.getName(), pool);
	}

	/**
	 * Removes the first occurrence of the specified sound effect from this list
	 *
	 * @param name
	 * @return <tt>true</tt> if this list contained the specified element
	 */
	public RandomPool remove(String name)
	{
		return this.items.remove(name);
	}

	/**
	 * Removes the first occurrence of the specified sound effect from this list
	 *
	 * @param pool
	 * @return <tt>true</tt> if this list contained the specified element
	 */
	public RandomPool remove(RandomPool pool)
	{
		return items.remove(pool.getName());
	}

	/**
	 * Removes all of the sound effect from this list.
	 * The list will be empty after this call returns.
	 */
	public void clear()
	{
		this.items.clear();
	}

	/**
	 * Returns the sound effect at the specified position in this list.
	 *
	 * @param name
	 * @return the element at the specified position in this list
	 */
	public RandomPool get(String name)
	{
		return items.get(name);
	}
}
