package net.bootymc.economy.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.bootymc.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MySQL 
{
	private static String database;
	private static String user;
	private static String password;
	private static Connection conn = null;
	
	@SuppressWarnings("static-access")
	public MySQL(String database, String user, String password)
	{
		this.database = database;
		this.user = user;
		this.password = password;
	}
	
	public static void createTable()
    {
        openConn();
        try
        {
            String query = "CREATE TABLE IF NOT EXISTS `economyapi` ("
                    + "`id` int(11) unsigned NOT NULL auto_increment,"
                    + "`player` varchar(16) NOT NULL,"
                    + "`currency` int(11) NOT NULL,"
                    + "PRIMARY KEY  (`id`),"
                    + "KEY `player` (`player`),"
                    + "KEY `currency` (`currency`)"
                    + ") ENGINE=InnoDB;";
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
        }
        catch(SQLException e)
        {
            Economy.getPlugin().getLogger().severe("Failed to create table, Disabling plugin");
            Bukkit.getServer().getPluginManager().disablePlugin(Economy.getPlugin());
        }
        closeConn();
    }
	
	public static void registerPlayer(Player player)
    {
        try
        {
            if(containsPlayer(player))
            {
                return;
            }
            PreparedStatement sql = conn
                    .prepareStatement("INSERT INTO `currency` (player, currency) VALUES (?, ?);");
            sql.setString(1, player.getName());
            sql.setInt(2, 0);
            sql.execute();
            sql.close();
        }
        catch(SQLException e)
        { 
        }
    }
    
    public static boolean containsPlayer(Player player)
    {
        try {
            PreparedStatement sql = conn
                    .prepareStatement("SELECT * FROM `currency` WHERE player=?;");
            sql.setString(1, player.getName());
            ResultSet rs = sql.executeQuery();
            boolean containsPlayer = rs.next();

            sql.close();
            rs.close();

            return containsPlayer;
        } catch (SQLException e) {
            return false;
        }
    }
	
	public static void openConn()
    {
        try
        {
            conn = DriverManager.getConnection(database, user, password);
        } 
        catch(SQLException e)
        {
            Economy.getPlugin().getLogger().severe("Failed to connect to the database. Disabling plugin.");
            Bukkit.getServer().getPluginManager().disablePlugin(Economy.getPlugin());
        }
    }
	
	public static void closeConn()
    {
        try
        {
            conn.close();
        }
        catch(SQLException e)
        {
        }
    }
	
	public static Connection getConn()
	{
		return conn;
	}
}
