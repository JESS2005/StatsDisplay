package net.jess.coordsmod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jess.coordsmod.CoordsMod;
import net.minecraft.Util;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.entity.player.Player;

import javax.swing.text.JTextComponent;
import java.awt.*;
//import net.minecraft.client.player.en;

public class DisconnectCommand {
    public DisconnectCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("SD").then(Commands.literal("disconnect").executes((command) -> {
            return disconnect(command);
        })));

    }

    private int disconnect(CommandContext<CommandSourceStack> command)  {
        if(command.getSource().getEntity() instanceof Player) {
            Player player = command.getSource().getPlayer();
            //player.sendSystemMessage(new TextComponent("Test"), Util.NIL_UUID);
            int Val=CoordsMod.TryDisconnect();
            String Str;
            if (Val==1) {
                Str="Device disconnected";
            }else if (Val==2) {
                Str="Failed to disconnect";
            }else {
                Str="No device connected";
            }
            command.getSource().sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal(Str)), true, ChatType.bind(ChatType.CHAT, player));
        }
        return Command.SINGLE_SUCCESS;
    }

}
