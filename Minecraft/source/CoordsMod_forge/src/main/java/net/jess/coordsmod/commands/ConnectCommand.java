package net.jess.coordsmod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.jess.coordsmod.CoordsMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.world.entity.player.Player;




public class ConnectCommand {
    public ConnectCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("SD").then(Commands.literal("connect").then(Commands.argument("Port", StringArgumentType.string()).executes((command -> {
            return connect(command);
        })))));

    }

    private int connect(CommandContext<CommandSourceStack> command)  {
        if(command.getSource().getEntity() instanceof Player) {
            Player player = command.getSource().getPlayer();
            String Str = StringArgumentType.getString(command,"Port");

            //player.sendSystemMessage(new TextComponent("Test"), Util.NIL_UUID);
            int Val=CoordsMod.TryConnect(Str);
            if (Val==1) {
                Str="Device connected";
            } else if (Val==2) {
                Str="Failed to connect";
            } else {
                Str="There already is a connected device";
            }
            command.getSource().sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal(Str)), false, ChatType.bind(ChatType.CHAT, player));
        }
        return Command.SINGLE_SUCCESS;
    }

}
