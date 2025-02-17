package nc.gui.processor;

import java.util.List;

import com.google.common.collect.Lists;

import nc.Global;
import nc.gui.NCGui;
import nc.tile.energy.ITileEnergy;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.Lang;
import nc.util.NCMath;
import nc.util.UnitHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public abstract class GuiItemFluidProcessor extends NCGui {
	
	protected final EntityPlayer player;
	protected final TileItemFluidProcessor tile;
	protected final ResourceLocation gui_textures;

	public GuiItemFluidProcessor(String name, EntityPlayer player, TileItemFluidProcessor tile, Container inventory) {
		super(inventory);
		this.player = player;
		this.tile = tile;
		gui_textures = new ResourceLocation(Global.MOD_ID + ":textures/gui/container/" + name + ".png");
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = tile.getDisplayName().getUnformattedText();
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	protected int getCookProgressScaled(double pixels) {
		double i = tile.time;
		double j = tile.baseProcessTime;
		return j != 0D ? (int) Math.round(i * pixels / j) : 0;
	}
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		/*if (tile.getWorld().isRemote) {
			if (guiButton != null) if (guiButton instanceof NCButton) {
				
			}
		}*/
	}
	
	@Override
	public void drawEnergyTooltip(ITileEnergy tile, int mouseX, int mouseY, int x, int y, int width, int height) {
		if (this.tile.defaultProcessPower != 0) super.drawEnergyTooltip(tile, mouseX, mouseY, x, y, width, height);
		else drawNoEnergyTooltip(mouseX, mouseY, x, y, width, height);
	}
	
	@Override
	public List<String> energyInfo(ITileEnergy tile) {
		String energy = UnitHelper.prefix(tile.getEnergyStorage().getEnergyStored(), tile.getEnergyStorage().getMaxEnergyStored(), 5, "RF");
		String power = UnitHelper.prefix(this.tile.getProcessPower(), 5, "RF/t");
		
		String speedMultiplier = "x" + NCMath.decimalPlaces(this.tile.getSpeedMultiplier(), 2);
		String powerMultiplier = "x" + NCMath.decimalPlaces(this.tile.getPowerMultiplier(), 2);
		
		return Lists.newArrayList(TextFormatting.LIGHT_PURPLE + Lang.localise("gui.container.energy_stored") + TextFormatting.WHITE + " " + energy, TextFormatting.LIGHT_PURPLE + Lang.localise("gui.container.process_power") + TextFormatting.WHITE + " " + power, TextFormatting.AQUA + Lang.localise("gui.container.speed_multiplier") + TextFormatting.WHITE + " " + speedMultiplier, TextFormatting.AQUA + Lang.localise("gui.container.power_multiplier") + TextFormatting.WHITE + " " + powerMultiplier);
	}
}
