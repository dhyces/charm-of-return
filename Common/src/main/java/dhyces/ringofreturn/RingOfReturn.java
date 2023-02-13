package dhyces.ringofreturn;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RingOfReturn {
    public static final String MODID = "ringofreturn";

    public static final Logger LOGGER = LogManager.getLogger(RingOfReturn.class);

    public static void init() {

    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }
}
