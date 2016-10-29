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
import com.github.hexocraftapi.configuration.collection.ConfigurationObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class RandomItem extends ConfigurationObject
{
	@ConfigValue(path = "*.name")    private String    name;
	@ConfigValue(path = "*.item")    private ItemStack itemStack;
	@ConfigValue(path = "*.command") private String    command;
	@ConfigValue(path = "*.weight")  private int       weight;

	// Constructors
	protected RandomItem(JavaPlugin plugin)
	{
		super(plugin);
	}

	public RandomItem(JavaPlugin plugin, RandomItem randomItem)
	{
		super(plugin);
		setRandomItem(randomItem);
	}

	public RandomItem(JavaPlugin plugin, String name, ItemStack itemStack, int weight)
	{
		super(plugin);
		setRandomItem(itemStack, weight);
		setName(name);
	}

	public RandomItem(JavaPlugin plugin, String name, String command, int weight)
	{
		super(plugin);
		setRandomItem(command, weight);
		setName(name);
	}


	// Accessors
	public ItemStack getItemStack() { return this.itemStack; }
	public int getWeight() { return this.weight; }
	public String getCommand() { return command; }

	public void setName(String name) { this.name = name; }

	public void setRandomItem(RandomItem randomItem)
	{
		setRandomItem(randomItem.getItemStack(), randomItem.getWeight());
	}

	public void setRandomItem(ItemStack itemStack, int weight)
	{
		this.itemStack = itemStack;
		this.weight = weight;
		this.command = null;
	}

	public void setRandomItem(String command, int weight)
	{
		this.itemStack = null;
		this.weight = weight;
		this.command = command;

		if(this.weight > 100)
			this.weight = 100;
		if(this.weight < 0)
			this.weight = 0;
	}
}
