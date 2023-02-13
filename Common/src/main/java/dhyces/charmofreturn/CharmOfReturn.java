package dhyces.charmofreturn;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharmOfReturn {
    public static final String MODID = "charmofreturn";

    public static final Logger LOGGER = LogManager.getLogger(CharmOfReturn.class);

    public static void init() {

    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }
}
