package com.github.hexocraft.random.items;

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

import com.github.hexocraftapi.util.PlayerUtil;
import com.github.hexocraft.random.items.radomitem.RandomItem;
import com.github.hexocraft.random.items.radomitem.RandomPool;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class RandomItemsApi
{
	private static final RandomItemsPlugin plugin = RandomItemsPlugin.instance;


	public static int Count()
	{
		return RandomItemsPlugin.items.size();
	}

	public static RandomPool get(String pool)
	{
		return RandomItemsPlugin.items.get(pool);
	}

	public static RandomItem get(String pool, int index)
	{
		RandomPool randomPool = get(pool);
		if(randomPool == null) return null;
		return randomPool.get(index);
	}

	public static RandomPool add(String pool)
	{
		RandomPool randomPool = get(pool);
		if(randomPool != null) return randomPool;

		randomPool = new RandomPool(RandomItemsPlugin.instance, pool);
		return RandomItemsPlugin.items.add(randomPool);
	}

	public static RandomPool add(String pool, List<RandomItem> items)
	{
		RandomPool randomPool = get(pool);
		randomPool = randomPool != null ? randomPool : new RandomPool(RandomItemsPlugin.instance, pool);

		for(RandomItem item : items)
			randomPool.add(item);

		return randomPool;
	}

	public static RandomPool add(String pool, RandomItem item)
	{
		RandomPool randomPool = get(pool);
		randomPool = randomPool != null ? randomPool : new RandomPool(RandomItemsPlugin.instance, pool);

		return randomPool.add(item) ? randomPool : null;
	}

	public static RandomPool add(String pool, String name, ItemStack itemStack, int weight)
	{
		return add(pool, new RandomItem(RandomItemsPlugin.instance, name, itemStack, weight));
	}

	public static RandomPool add(String pool, ItemStack itemStack, int weight)
	{
		return add(pool, new RandomItem(RandomItemsPlugin.instance, null, itemStack, weight));
	}

	public static RandomPool add(String pool, String name, String command, int weight)
	{
		return add(pool, new RandomItem(RandomItemsPlugin.instance, name, command, weight));
	}

	public static RandomPool add(String pool, String command, int weight)
	{
		return add(pool, new RandomItem(RandomItemsPlugin.instance, null, command, weight));
	}

	public static boolean remove(String pool)
	{
		RandomPool randomPool = get(pool);
		if(randomPool == null) return true;

		return RandomItemsPlugin.items.remove(randomPool) != null;
	}

	public static boolean remove(String pool, RandomItem randomItem)
	{
		RandomPool randomPool = get(pool);
		if(randomPool == null) return true;

		return RandomItemsPlugin.items.remove(randomPool) != null;
	}

	public static Map<String, RandomPool> getList()
	{
		return RandomItemsPlugin.items.getMap();
	}

	public static List<RandomItem> getList(String pool)
	{
		RandomPool randomPool = get(pool);
		if(randomPool == null) return null;
		return randomPool.getList();
	}

	public static boolean save()
	{
		return RandomItemsPlugin.items.save();
	}

	public static boolean contains(String pool)
	{
		return get(pool) != null;
	}

	public static void give(Player player, RandomItem random)
	{
		if(random.getItemStack() != null)
			PlayerUtil.give(player, random.getItemStack());

		else if(random.getCommand() != null && !random.getCommand().isEmpty())
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), random.getCommand().replace("{PLAYER}", player.getName()));
	}

	public static void spawn(Location location, RandomItem random)
	{
		if(random.getItemStack() != null)
			location.getWorld().dropItem(location, random.getItemStack());

		else if(random.getCommand() != null && !random.getCommand().isEmpty())
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), random.getCommand());
	}

	public static void spawn(World world, double x, double y, double z, RandomItem random)
	{
		spawn(new Location(world, x, y, z), random);
	}
}
