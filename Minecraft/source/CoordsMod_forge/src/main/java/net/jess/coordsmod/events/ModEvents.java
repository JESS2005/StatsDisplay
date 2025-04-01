package net.jess.coordsmod.events;
import net.jess.coordsmod.CoordsMod;
import net.jess.coordsmod.commands.ConnectCommand;
import net.jess.coordsmod.commands.DisconnectCommand;
import net.jess.coordsmod.commands.SetDefault;
import net.jess.coordsmod.commands.UnsetDefault;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = CoordsMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
    new DisconnectCommand(event.getDispatcher());
    new ConnectCommand(event.getDispatcher());
    new SetDefault(event.getDispatcher());
    new UnsetDefault(event.getDispatcher());

    ConfigCommand.register(event.getDispatcher());
    }
}
