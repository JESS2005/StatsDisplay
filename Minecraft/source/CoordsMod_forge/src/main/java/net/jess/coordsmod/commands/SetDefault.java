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




public class SetDefault {
    public SetDefault(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("SD").then(Commands.literal("setdefault").then(Commands.argument("Port", StringArgumentType.string()).executes((command -> {
            return set(command);
        })))));

    }

    private int set(CommandContext<CommandSourceStack> command)  {
        if(command.getSource().getEntity() instanceof Player) {
            String Str = StringArgumentType.getString(command,"Port");
            CoordsMod.SetDefaultfunc(Str);
            //CoordsModCommonConfigs.DEFAULT_PORT.set(Str);
        }
        return Command.SINGLE_SUCCESS;
    }

}