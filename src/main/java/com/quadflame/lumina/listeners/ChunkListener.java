package com.quadflame.lumina.listeners;

import net.minecraft.server.v1_8_R3.ChunkSection;
import net.minecraft.server.v1_8_R3.NibbleArray;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.Arrays;

/**
 * Listens for chunk load events and force-applies full skylight data.
 *
 * <p>This listener bypasses vanilla lighting calculations by directly
 * overwriting the skylight nibble arrays of all chunk sections. This is
 * intended for environments where lighting accuracy is non-essential
 * and fast, deterministic lighting is preferred.</p>
 *
 * <p><strong>Implementation notes:</strong>
 * <ul>
 *   <li>Uses NMS internals and is therefore version-specific.</li>
 *   <li>Executes synchronously on the main server thread.</li>
 *   <li>Assumes chunks are fully initialized when the event fires.</li>
 * </ul>
 * </p>
 */
public class ChunkListener implements Listener {

    /**
     * Invoked when a chunk is loaded by the server.
     *
     * <p>Forces full skylight values on all chunk sections to eliminate
     * lighting calculations and prevent dark regions.</p>
     *
     * @param event the chunk load event
     */
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        relightChunk(event.getChunk());
    }

    /**
     * Applies maximum skylight values to all sections of the given chunk.
     *
     * <p>This method directly mutates the underlying NMS chunk data.</p>
     *
     * @param chunk the Bukkit chunk to relight
     */
    private void relightChunk(Chunk chunk) {
        net.minecraft.server.v1_8_R3.Chunk nmsChunk = ((CraftChunk) chunk).getHandle();
        for (ChunkSection section : nmsChunk.getSections()) {
            if (section != null) {
                section.b(createSkyLight());
            }
        }
    }

    /**
     * Creates a skylight nibble array filled with maximum light values.
     *
     * <p>Each nibble is set to {@code 0xF}, representing full skylight
     * for the entire chunk section.</p>
     *
     * @return a fully-initialized skylight array
     */
    private NibbleArray createSkyLight() {
        byte[] lightData = new byte[2048];
        Arrays.fill(lightData, (byte) 0xFF);
        return new NibbleArray(lightData);
    }
}
