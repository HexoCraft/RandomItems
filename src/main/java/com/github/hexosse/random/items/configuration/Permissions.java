package com.github.hexosse.random.items.configuration;

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

import org.bukkit.command.CommandSender;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public enum Permissions
{
	ADMIN("random.items.admin"),
	CREATE("random.items.create");

	private final String permission;

	// Constructeur
	Permissions(String permission)
	{
		this.permission = permission;
	}

	// Test si le sender à la permission
	public static boolean has(CommandSender sender, Permissions permission)
	{
		return has(sender, permission.permission);
	}

	// Test si le sender à la permission
	public static boolean has(CommandSender sender, String node)
	{
		return sender.hasPermission(node) || sender.hasPermission(node.toLowerCase());
	}

	// Permission to string
	public String toString()
	{
		return permission;
	}
}
