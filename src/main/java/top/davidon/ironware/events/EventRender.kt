package top.davidon.ironware.events

import com.darkmagician6.eventapi.events.Event
import lombok.Getter
import lombok.Setter
import net.minecraft.client.gui.ScaledResolution

class EventRender(
    val partialTicks: Float,
    val sr: ScaledResolution,
    val mouseX: Int,
    val mouseY: Int,
    val type: Type
) : Event {
    enum class Type {
        RENDER_2D, RENDER_3D, FRAME, RENDER_SCREEN
    }
}