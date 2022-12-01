package top.davidon.ironware.events

import com.darkmagician6.eventapi.events.callables.EventCancellable
import com.darkmagician6.eventapi.types.EventType
import net.minecraft.client.gui.ScaledResolution

class EventCrosshair(val type: EventType, var sr: ScaledResolution) :
    EventCancellable()