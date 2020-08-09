package com.eazyftw.ucvaultaddon;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import me.TechsCode.UltraCustomizer.tpl.Tools;
import org.bukkit.entity.Player;

public class PlayerAddToBalance extends Element {

    public PlayerAddToBalance(final UltraCustomizer plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Player Add to Balance";
    }

    @Override
    public String getInternalName() {
        return "player-add-balance";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public String getRequiredPlugin() {
        return "Vault";
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.GOLD_INGOT;
    }

    @Override
    public String[] getDescription() {
        return new String[] { "Add money to a player's balance" };
    }

    @Override
    public Argument[] getArguments(final ElementInfo elementInfo) {
        return new Argument[] { new Argument("player", "Player", DataType.PLAYER, elementInfo), new Argument("amount", "Amount", DataType.NUMBER, elementInfo),
        };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(final ElementInfo elementInfo) {
        return new OutcomingVariable[0];
    }

    @Override
    public Child[] getConnectors(final ElementInfo elementInfo) {
        return new Child[] { new DefaultChild(elementInfo, "next") };
    }
    @Override
    public void run(final ElementInfo info, final ScriptInstance instance) {
        final Player player = (Player)this.getArguments(info)[0].getValue(instance);
        final int amount = (int)(long)this.getArguments(info)[1].getValue(instance);
        if(!VaultUtil.hasVault()) {
            plugin.log(Tools.c("%prefix% &cVault is needed to use the PlayerAddToBalance element."));
            return;
        }
        if(VaultUtil.getEconomy() == null) {
            plugin.log(Tools.c("&cThe Economy has to be setup to use the PlayerAddToBalance element. Do you have an Economy plugin installed?"));
            this.getConnectors(info)[1].run(instance);
            return;
        }
        VaultUtil.getEconomy().depositPlayer(player, amount);
        this.getConnectors(info)[0].run(instance);
    }
}
