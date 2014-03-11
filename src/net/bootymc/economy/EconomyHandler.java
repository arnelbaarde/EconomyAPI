package net.bootymc.economy;

import org.bukkit.entity.Player;

public interface EconomyHandler 
{
	public int getCurrency(Player player);
    
    public int addCurrency(Player player, int amount);
    
    public int remCurrency(Player player, int amount);
    
    public int setCurrency(Player player, int amount);
    
    public int clearCurrency(Player player);
    
    public int checkCurrency(Player player);
}
