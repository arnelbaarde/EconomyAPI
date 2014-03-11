package net.bootymc.economy;

import net.bootymc.economy.data.Currency;
import net.bootymc.economy.data.MySQL;
import net.bootymc.economy.data.UpdateCurrency;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.permissions.exceptions.PermissionBackendException;

public class Economy extends JavaPlugin implements Listener
{
	private static Economy plugin;
	private static Currency currency;
	private static MySQL mysql;
	
	@SuppressWarnings("static-access")
	@Override
	public void onEnable()
	{
		this.plugin = this;
		
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
		
		String temp = this.getConfig().getString("mysql-database");
		String[] st = temp.split(";");
		
		currency = new Currency(this.getConfig().getString("currency-name"));
		mysql = new MySQL(st[0], st[1], st[2]);
		
		mysql.createTable();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new UpdateCurrency(), 100L, 100L);
	}
	
	public static EconomyHandler getHandler()
	{
		return new EconomyAPI();
	}
	
	public static Economy getPlugin()
	{
		return plugin;
	}
	
	public static Currency getCurrency()
	{
		return currency;
	}
	
	public static MySQL getSQL()
	{
		return mysql;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		try 
		{
			PermissionsEx.getPermissionManager().reset();
		} catch (PermissionBackendException e) 
		{
			e.printStackTrace();
		}
	}
}
