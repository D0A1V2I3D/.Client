package top.davidon.ironware.events

import com.darkmagician6.eventapi.events.callables.EventCancellable
import com.darkmagician6.eventapi.types.EventType
import net.minecraft.network.Packet

class EventPacket(var eventType: EventType, var packet: Packet<*>) : EventCancellable()