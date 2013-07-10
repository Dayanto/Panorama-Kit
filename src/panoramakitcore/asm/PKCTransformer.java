package panoramakitcore.asm;

import java.util.Iterator;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import cpw.mods.fml.relauncher.IClassTransformer;
import static org.objectweb.asm.Opcodes.*;


/**
 * PKCTransformer
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class PKCTransformer implements IClassTransformer
{
	
	@Override
    public byte[] transform(String name,String transformedName, byte[] bytes)
    {
        if ("net.minecraft.client.renderer.EntityRenderer".equals(name))
        {
            ClassNode classNode = readBytes(bytes);
            doViewPositionTransform(classNode);
            bytes = writeBytes(classNode);
        }

        return bytes;
    }

	public ClassNode doViewPositionTransform(ClassNode classNode)
	{
		for(MethodNode mn : classNode.methods)
		{
			if(!"orientCamera".equals(mn.name))
			{
				continue;
			}
		
			InsnList instructions = mn.instructions;
			Iterator<AbstractInsnNode> instrItterator = instructions.iterator();
			while (instrItterator.hasNext()) 
			{
				AbstractInsnNode instr = instrItterator.next();
				if (instr instanceof LineNumberNode && ((LineNumberNode)instr).line == 536)
				{
					AbstractInsnNode nextLineNumInstr = instr.getNext();
					while(!(nextLineNumInstr instanceof LineNumberNode))
					{	
						nextLineNumInstr = nextLineNumInstr.getNext();
					}
					LabelNode jumpTo = ((LineNumberNode)nextLineNumInstr).start;
		
					InsnList customInstrList = new InsnList();
					customInstrList.add(new MethodInsnNode(INVOKESTATIC, "panoramakit/Toggles", "isRendering", "()Z"));
					customInstrList.add(new JumpInsnNode(IFEQ, jumpTo));
					instructions.insertBefore(instr, customInstrList);
					break;
				}
			}
			break;
		}
		return classNode;
	}
	
	private ClassNode readBytes(byte[] bytes)
	{
		ClassReader cr = new ClassReader(bytes);
        ClassNode cn = new ClassNode(Opcodes.ASM4);
        cr.accept(cn, ClassReader.EXPAND_FRAMES);
        return cn;
	}
	
	private byte[] writeBytes(ClassNode cn)
	{
		 ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
         cn.accept(cw);
         return cw.toByteArray();
	}
}
