package com.eazyftw.ucvaultaddon;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import me.TechsCode.UltraCustomizer.tpl.Tools;
import org.bukkit.entity.Player;

public class PlayerHasMoney extends Element {

    public PlayerHasMoney(final UltraCustomizer plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Player Has Money";
    }

    @Override
    public String getRequiredPlugin() {
        return "Vault";
    }

    @Override
    public String getInternalName() {
        return "player-has-money";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.GOLD_INGOT;
    }

    @Override
    public String[] getDescription() {
        return new String[] { "Runs one of two actions depending", "if a player is in a certain amount of money"};
    }

    @Override
    public Argument[] getArguments(final ElementInfo elementInfo) {
        return new Argument[] { new Argument("player", "Player", DataType.PLAYER, elementInfo), new Argument("amount", "Amount", DataType.NUMBER, elementInfo) };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(final ElementInfo elementInfo) {
        return new OutcomingVariable[0];
    }

    @Override
    public Child[] getConnectors(final ElementInfo elementInfo) {
        return new Child[] { new Child(elementInfo, "yes") {
            @Override
            public String getName() {
                return "Has the Money";
            }

            @Override
            public String[] getDescription() {
                return new String[] { "Will be executed if the player", "has the specified amount of money" };
            }

            @Override
            public XMaterial getIcon() {
                return XMaterial.LIME_STAINED_GLASS_PANE;
            }
        }, new Child(elementInfo, "no") {
            @Override
            public String getName() {
                return "Doesn't have the Money";
            }

            @Override
            public String[] getDescription() {
                return new String[] { "Will be executed if the player", "doesn't have the specified amount of money" };
            }

            @Override
            public XMaterial getIcon() {
                return XMaterial.RED_STAINED_GLASS_PANE;
            }
        } };
    }

    @Override
    public void run(final ElementInfo info, final ScriptInstance instance) {
        final Player player = (Player)this.getArguments(info)[0].getValue(instance);
        long amount;
        try {
            amount = (long)this.getArguments(info)[1].getValue(instance);
        } catch (NullPointerException ex) {
            plugin.log(Tools.c("%prefix% &cAn issue occurred while trying to get the amount in the Vault Addon (PlayerHasMoney Element)."));
            this.getConnectors(info)[1].run(instance);
            return;
        }
        if(!VaultUtil.hasVault()) {
            plugin.log(Tools.c("%prefix% &cVault is needed to use the PlayerHasMoney element."));
            this.getConnectors(info)[1].run(instance);
            return;
        }
        if(VaultUtil.getEconomy() == null) {
            plugin.log(Tools.c("%prefix% &cThe Economy has to be setup to use the PlayerHasMoney element. Do you have an Economy plugin installed?"));
            this.getConnectors(info)[1].run(instance);
            return;
        }
        if (VaultUtil.getEconomy().getBalance(player) >= amount) {
            this.getConnectors(info)[0].run(instance);
        } else {
            this.getConnectors(info)[1].run(instance);
        }
    }
}
