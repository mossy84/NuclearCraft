package nc.item.tool;

import java.util.List;

import javax.annotation.Nullable;

import nc.util.InfoHelper;
import nc.util.OreDictHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NCShovel extends ItemSpade {
	
	String[] info;

	public NCShovel(ToolMaterial material, String... tooltip) {
		super(material);
		info = InfoHelper.buildInfo(getTranslationKey(), tooltip);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(itemStack, world, tooltip, flag);
		if (info.length > 0) InfoHelper.infoFull(tooltip, info);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		ItemStack mat = toolMaterial.getRepairItemStack();
		return mat != null && !mat.isEmpty() && OreDictHelper.isOreMatching(mat, repair);
	}
}
