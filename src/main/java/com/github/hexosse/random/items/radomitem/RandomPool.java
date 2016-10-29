package com.github.hexosse.random.items.radomitem;

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

import com.github.hexocraftapi.configuration.annotation.ConfigValue;
import com.github.hexocraftapi.configuration.collection.ConfigurationList;
import com.github.hexocraftapi.configuration.collection.ConfigurationMapObject;
import com.github.hexocraftapi.weighted.selector.WeightedSelector;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class RandomPool extends ConfigurationMapObject
{
	@ConfigValue(path = "*.name") private String                         name  = null;
	@ConfigValue(path = "*.items") private ConfigurationList<RandomItem> items = null;

	private WeightedSelector<RandomItem> Selector = null;


	// Constructors
	protected RandomPool(JavaPlugin plugin)
	{
		super(plugin);
	}

	public RandomPool(JavaPlugin plugin, String name)
	{
		super(plugin);
		this.name = name;
		this.items = new ConfigurationList();
	}

	public RandomPool(JavaPlugin plugin, String name, List<RandomItem> items)
	{
		super(plugin);
		this.name = name;
		this.items = new ConfigurationList(items);
	}


	// Accessors
	@Override
	public String getName() { return name; }
	@Override
	public void setName(String name)  { this.name = name; }

	public int getItemCount() { return items.size(); }
	public List<RandomItem> getList()  { return items; }

	public RandomItem get(int index)
	{
		return items.get(index);
	}

	public RandomItem getRandom()
	{
		if(items.size() == 0)  return null;

		if(Selector == null)
		{
			Selector = new WeightedSelector<>();

			for(RandomItem randomItem : items)
				Selector.Add(randomItem, randomItem.getWeight());
		}

		return Selector.Select();
	}

	public boolean add(RandomItem item)
	{
		Selector = null;
		return items.add(item);
	}
}
