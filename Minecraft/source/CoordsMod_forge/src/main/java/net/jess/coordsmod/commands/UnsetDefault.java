package net.jess.coordsmod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.jess.coordsmod.CoordsMod;
import net.jess.coordsmod.config.CoordsModCommonConfigs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;




public class UnsetDefault {
    public UnsetDefault(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("SD").then(Commands.literal("unsetdefault").executes((command) -> {
            return unset(command);
        })));

    }

    private int unset(CommandContext<CommandSourceStack> command)  {
        if(command.getSource().getEntity() instanceof Player) {

            CoordsMod.SetDefaultfunc("None");
        }
        return Command.SINGLE_SUCCESS;
    }

}
