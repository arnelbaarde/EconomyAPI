package net.bootymc.economy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.bootymc.economy.data.MySQL;

import org.bukkit.entity.Player;

public class EconomyAPI implements EconomyHandler
{
	MySQL mysql = Economy.getSQL();
	public static Map<String, Integer> playerCurrency = new HashMap<String, Integer>();
	
	@Override
	public int getCurrency(Player player) {
		int currency = 0;
        if(playerCurrency.containsKey(player.getName()))
        {
            currency = playerCurrency.get(player.getName());
            
            return currency;
        }
        return currency;
	}

	@SuppressWarnings("static-access")
	@Override
	public int addCurrency(Player player, int amount) {
		int currency = checkCurrency(player);
        int newAmount = currency + amount;
        
        mysql.openConn();
        try
        {
            PreparedStatement sql = mysql.getConn()
                    .prepareStatement("UPDATE `economyapi` SET currency=? WHERE player=?;");
            sql.setInt(1, newAmount);
            sql.setString(2, player.getName());
            sql.executeUpdate();
            
            sql.close();
        }
        catch(SQLException e)
        {
            
        }
        mysql.closeConn();
        return newAmount;
	}

	@SuppressWarnings("static-access")
	@Override
	public int remCurrency(Player player, int amount) {
		int currency = checkCurrency(player);
        int newAmount = currency - amount;
        
        mysql.openConn();
        try
        {
            PreparedStatement sql = mysql.getConn()
                    .prepareStatement("UPDATE `economyapi` SET currency=? WHERE player=?;");
            sql.setInt(1, newAmount);
            sql.setString(2, player.getName());
            sql.executeUpdate();
            
            sql.close();
        }
        catch(SQLException e)
        {
            
        }
        mysql.closeConn();
        return newAmount;
	}

	@SuppressWarnings("static-access")
	@Override
	public int setCurrency(Player player, int amount) {
		mysql.openConn();
        try
        {
            PreparedStatement sql = mysql.getConn()
                    .prepareStatement("UPDATE `economyapi` SET currency=? WHERE player=?;");
            sql.setInt(1, amount);
            sql.setString(2, player.getName());
            sql.executeUpdate();
            
            sql.close();
        }
        catch(SQLException e)
        {
            
        }
        mysql.closeConn();
        return amount;
	}

	@SuppressWarnings("static-access")
	@Override
	public int clearCurrency(Player player) {
		mysql.openConn();
        try
        {
            PreparedStatement sql = mysql.getConn()
                    .prepareStatement("UPDATE `economyapi` SET currency=? WHERE player=?;");
            sql.setInt(1, 0);
            sql.setString(2, player.getName());
            sql.executeUpdate();
            
            sql.close();
        }
        catch(SQLException e)
        {
            
        }
        mysql.closeConn();
        return 0;
	}

	@SuppressWarnings("static-access")
	@Override
	public int checkCurrency(Player player) {
		int currency = 0;
        mysql.openConn();
        try
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
            currency = rs.getInt("currency");
            
            sql.close();
            rs.close();
        }
        catch(SQLException e)
        {
        }
        mysql.closeConn();
        return currency;
	}
	
}
