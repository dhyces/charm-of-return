package dhyces.charmofreturn.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dhyces.charmofreturn.client.ConfigEditBox;
import dhyces.charmofreturn.services.ConfigHelper;
import dhyces.charmofreturn.services.Services;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {

    private Screen parent;
    private ConfigHolder holder;

    public ConfigScreen(Screen parent) {
        super(Component.translatable("config_screen.charmofreturn.title"));
        this.parent = parent;
        this.holder = new ConfigHolder(Services.CONFIG_HELPER);
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(
                Button.builder(Component.translatable("config_screen.charmofreturn.save"), button -> holder.save()).pos((width / 2) - 75, height - 50).size(50, 20).build()
        );
        addRenderableWidget(
                Button.builder(Component.translatable("config_screen.charmofreturn.save"), button -> this.minecraft.setScreen(parent)).pos((width / 2), height - 50).size(50, 20).build()
        );

        addRenderableWidget(new ConfigEditBox(font, 50, 50, 200, 20, Component.translatable("config_screen.charmofreturn.level_cost_expression"),
                box1 -> {
                    box1.parseExpression();
                    this.holder.levelCostStr = box1.getValue();
                }
                , "0.8x")
        );
        addRenderableWidget(new ConfigEditBox(font, 50, 80, 200, 20, Component.translatable("config_screen.charmofreturn.durability"),
                box1 -> this.holder.durability = box1.parseIntValue()
                , "0")
        );
        addRenderableWidget(new ConfigEditBox(font, 50, 110, 200, 20, Component.translatable("config_screen.charmofreturn.charge_ticks"),
                box1 -> this.holder.charge = box1.parseIntValue()
                , "200")
        );
        addRenderableWidget(new ConfigEditBox(font, 50, 140, 200, 20, Component.translatable("config_screen.charmofreturn.cooldown_ticks"),
                box1 -> this.holder.cooldown = box1.parseIntValue()
                , "1200")
        );
        addRenderableWidget(new ConfigEditBox(font, 50, 170, 200, 20, Component.translatable("config_screen.charmofreturn.is_client_only_particles"),
                box1 -> this.holder.isClientParticles = box1.parseBooleanValue()
                , "false")
        );
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (GuiEventListener listener : this.children()) {
            if (listener.mouseClicked(mouseX, mouseY, button)) {
                setFocused(listener);
                return true;
            }
        }
        return false;
    }

    private void unfocus() {
        if (getFocused() != null) {
            if (getFocused() instanceof ConfigEditBox box) {
                box.setFocus(false);
            }
            getFocused().changeFocus(false);
            setFocused(null);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(0, 0, 0, 1);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        builder.vertex(0, 0, 0).color(0, 0, 0, 255).endVertex();
        builder.vertex(0, 10, 0).color(0, 0, 0, 255).endVertex();
        builder.vertex(10, 10, 0).color(0, 0, 0, 255).endVertex();
        builder.vertex(10, 0, 0).color(0, 0, 0, 255).endVertex();
        Tesselator.getInstance().end();
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        super.renderBackground(poseStack);
    }

    private class ConfigHolder {
        public String levelCostStr;
        public int durability;
        public int charge;
        public int cooldown;
        public boolean isClientParticles;
        private ConfigHelper configHelper;
        private ConfigHolder(ConfigHelper configHelper) {
            this.levelCostStr = configHelper.getExpressionString();
            this.durability = configHelper.getDurability();
            this.charge = configHelper.getCharge();
            this.cooldown = configHelper.getCooldown();
            this.isClientParticles = configHelper.isClientParticles();
            this.configHelper = configHelper;
        }

        public void save() {
            configHelper.setExpressionString(levelCostStr);
            configHelper.setDurability(durability);
            configHelper.setCharge(charge);
            configHelper.setCooldown(cooldown);
            configHelper.setClientParticles(isClientParticles);
            configHelper.save();
        }
    }
}
