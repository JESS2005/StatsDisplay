package net.jess.coordsmod;


import com.google.common.base.Ticker;
import com.mojang.logging.LogUtils;
import net.jess.coordsmod.config.CoordsModCommonConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientPlayerChangeGameTypeEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.entity.*;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.level.ChunkTicketLevelUpdatedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.firmata4j.IODevice;
import org.firmata4j.firmata.FirmataDevice;


import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.util.Objects;

import static java.util.concurrent.TimeUnit.*;




// The value here should match an entry in the META-INF/mods.toml file
@Mod(CoordsMod.MOD_ID)
public class CoordsMod
{
    public static IODevice device = null;


    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "firmatacoords";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static int TryConnect(String Port)
    {
        if (device==null) {
            device= new FirmataDevice(Port);
            //System.out.println("DeviceName");
            System.out.println(device);

            try {
                device.start();
                //device.ensureInitializationIsDone();
                return 1;

            } catch (IOException e) {
                e.printStackTrace();
                device=null;
                return 2;
            }
        }
        return 3;
    }

    public static int TryDisconnect()
    {
        if (device!=null) {
            try {
                device.stop();
                device=null;
                return 1;
            } catch (IOException e) {
                device=null;
                e.printStackTrace();
                return 2;
            }
        }
        return 3;
    }

    public static void SetDefaultfunc(String port) {
        Config.DEFAULT_PORT.set(port);
    }

    public CoordsMod()
    {
        System.out.println("TestABCD1234");
        //TryConnect("COM3");

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        //modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC,"StatsDisplay-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);


        //CoordsModCommonConfigs.DEFAULT_PORT.set("Test");

    }





    // You can use SubscribeEvent and let the Event Bus discover methods to call
    /*@SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }*/
    @SubscribeEvent
    public void playerTick(TickEvent.ClientTickEvent event)
    {
        //System.out.println("YAY2");
        float Val=1;
        LocalPlayer Pl= Minecraft.getInstance().player;



        if ((Pl!=null)&&(device!=null)) {
            Vec3 A = Pl.getPosition(Val);
            try {
                String s;
                /*s="X"+A.x;
                //device.sendMessage(s.substring(0, Math.min(s.length(), 14)));
                s="Z"+A.z;
                device.sendMessage(s.substring(0, Math.min(s.length(), 14)));*/

                s="A"+Pl.getArmorValue();
                device.sendMessage(s);

                s="S"+Math.round(Pl.getFoodData().getSaturationLevel());
                device.sendMessage(s);

                device.sendMessage("X"+Math.round(A.x));
                device.sendMessage("Z"+Math.round(A.z));
                s="F"+Pl.getFoodData().getFoodLevel();
                device.sendMessage(s.substring(0, Math.min(s.length(), 14)));
                s="H"+Math.ceil(Pl.getHealth());
                device.sendMessage(s);



                // System.out.println(s.substring(0, Math.min(s.length(), 14)));

                //byte[] B = ByteBuffer.allocate(4).putInt(23).array();
                //device.sendMessage(B);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(A.x);
        }
    }

    /*@SubscribeEvent
    public void pickupItemc(ClientChatEvent event) {
        //TryConnect();
        System.out.println(Config.DEFAULT_PORT.get());
        Config.DEFAULT_PORT.set("Test");
    }*/

    /*@SubscribeEvent
    public void joined(ClientPlayerNetworkEvent.LoggingIn event) {
        if (device==null&&(!Objects.equals(Config.DEFAULT_PORT.get(), "None"))) {
            TryConnect(Config.DEFAULT_PORT.get());
            //System.out.println("PortName "+Config.DEFAULT_PORT.get());
        }
    }*/

    public static void connectDefault() {
        if (device==null&&(!Objects.equals(Config.DEFAULT_PORT.get(), "None"))) {
            TryConnect(Config.DEFAULT_PORT.get());
            //System.out.println("PortName "+Config.DEFAULT_PORT.get());
        }
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
