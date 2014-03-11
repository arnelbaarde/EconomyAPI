package net.bootymc.economy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.bootymc.economy.Economy;
import net.bootymc.economy.EconomyAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateCurrency extends BukkitRunnable
{
	MySQL mysql = Economy.getSQL();
	
	@SuppressWarnings("static-access")
	@Override
    public void run()
    {
        List<Player> players = Arrays.asList(Bukkit.getServer().getOnlinePlayers());
        mysql.openConn();
        try
        {
            for(Player player : players)
            {
                if(!mysql.containsPlayer(player))
                {
                	mysql.registerPlayer(player);
                }
                PreparedStatement sql = mysql.getConn()
                        .prepareStatement("SELECT currency FROM `economyapi` WHERE player=?;");
                sql.setString(1, player.getName());
                ResultSet rs = sql.executeQuery();
                rs.next();
                int currency = rs.getInt("currency");
                
                EconomyAPI.playerCurrency.put(player.getName(), currency);
                
                sql.close();
                rs.close();
            }
        }
        catch(SQLException e)
        {
            
        }
        mysql.closeConn();
    }
}
