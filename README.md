# RandomItems
RandomItems is a plugin and an API which make easy to drop or give to players a random item or even send a command from a pool of items depending on the weight associated with those items.

To determine the right item depending on its weight, I've ported [WeightedSelector.NET](https://github.com/kinetiq/Ether.WeightedSelector) to java which makes this plugin one of the best random item selectors.
<br>
<br>

#### Commands:
* /RandomItems create \<name> : Create a new random items list
* /RandomItems delete \<name> [delay] : Delete a random items list
* /RandomItems add \<name> \<weight> [Description] : Add item in **hand** to the list
* /RandomItems addc \<name> \<command> \<weight> [Description] [delay] : Add command to the list
* /RandomItems give \<name> [player] : Give an item from the random list
* /RandomItems giveall \<name> : Give an item to all players
* /RandomItems spawn \<name> \<world> [x] [y] [z] : Spawn an item to location
* /RandomItems list [name] : List random list or items
* /RandomItems reload : Reload RandomItems
* /RandomItems help : Display help
<br>
<br>

#### Permissions:
* random.items.admin : Give all access, default to OP
* random.items.create : Create random list
<br>
<br>

#### Dependencies:
To display the items name in your language, you can use [Language Utils](https://www.spigotmc.org/resources/1-10-x-1-9-x-1-8-x-1-7-10-language-utils.8859/)
Be aware, that you have to choose this right version depending on your server version.
Then just change the locale value in config.yml
<br>
<br>

#### Messages:
All the messages sent by RandomItems are configurable in the message.yml file.
You can also create your own message file and change it in the config file.
Feel free to send me any translation and I'll add it into RandomItems.
<br>
<br>

#### Config:
The plugin use metrics and an integrated updater.<br>
Both can be disable in config.yml
<br>
<br>

#### Tab completion:
All commands have a full tab completion with a detailed help.
<br>
<br>

#### API:
As it is designed as an API, you can add RandomItems to your plugin using maven

```
    <repositories>
        <repository>
           <id>hexosse-repo</id>
           <url>https://raw.github.com/hexosse/maven-repo/master/</url>
       </repository>
    </repositories>
```

```
   <dependencies>
        <dependency>
            <groupId>com.github.hexocraft</groupId>
            <artifactId>random-items</artifactId>
            <version>1.0.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```

The API can be found in RandomItemsApi class.
<br>
<br>


#### Ressources:
Releases : https://github.com/hexocraft/RandomItems/releases
