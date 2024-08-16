package org.eclms.socket.serialization;

import org.eclms.common.constants.RpcSerialization;

import java.util.HashMap;
import java.util.Map;

public class SerializationFactory {
    private static Map<RpcSerialization, org.eclms.socket.serialization.RpcSerialization> serializationMap
            = new HashMap<RpcSerialization, org.eclms.socket.serialization.RpcSerialization>();

    static {
        serializationMap.put(RpcSerialization.JSON, new JsonSerialization());
    }

    public static org.eclms.socket.serialization.RpcSerialization get(RpcSerialization serialization) {
        return serializationMap.get(serialization);
    }
}
