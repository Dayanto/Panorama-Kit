/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakitcore.asm;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import java.util.Iterator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import net.minecraft.launchwrapper.IClassTransformer;

/**
 * PKitCoreTransformer
 * 
 * @author dayanto
 */
public class CodeTransformer implements IClassTransformer
{
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
		if ("bll".equals(name)) { // "net.minecraft.client.renderer.EntityRenderer"
			ClassNode classNode = readBytes(bytes);
			applyViewPositionTransform(classNode);
			bytes = writeBytes(classNode);
		}
		
		return bytes;
	}
	
	/**
	 * Adds an if statement before line the line that adds an offset, skipping it whenever the mod is rendering.
	 */
	public ClassNode applyViewPositionTransform(ClassNode classNode)
	{
		for (MethodNode mn : classNode.methods) {
			if (!"g".equals(mn.name)) { //orientCamera
				continue;
			}

			Iterator<AbstractInsnNode> instructionIterator = mn.instructions.iterator();
			while (instructionIterator.hasNext()) {
				AbstractInsnNode instruction = instructionIterator.next();
				
				if (instruction instanceof LineNumberNode && ((LineNumberNode) instruction).line == 709) {
					AbstractInsnNode nextLineNumInstr = instruction.getNext();
					while (!(nextLineNumInstr instanceof LineNumberNode)) {
						nextLineNumInstr = nextLineNumInstr.getNext();
					}
					LabelNode jumpTo = ((LineNumberNode) nextLineNumInstr).start;
					
					InsnList customInstrList = new InsnList();
					customInstrList.add(new MethodInsnNode(INVOKESTATIC, "panoramakitcore/CoreStates", "isRendering", "()Z"));
					customInstrList.add(new JumpInsnNode(IFNE, jumpTo));
					mn.instructions.insertBefore(instruction, customInstrList);
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
		ClassNode cn = new ClassNode(ASM4);
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
