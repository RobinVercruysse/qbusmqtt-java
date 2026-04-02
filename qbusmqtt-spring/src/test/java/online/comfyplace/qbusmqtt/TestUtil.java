package online.comfyplace.qbusmqtt;

import online.comfyplace.qbusmqtt.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

class TestUtil {
    static final Configuration CONFIGURATION_ONLY_CONTROLLER = new Configuration()
            .setApp("myApp")
            .setDevices(new Device[]{
                    new Device()
                            .setFunctionBlocks(new FunctionBlock[]{})
                            .setId("controllerId")
                            .setIp("127.0.0.1")
                            .setMac("controllerMac")
                            .setName("controllerName")
                            .setProperties(new DeviceProperties()
                                .setConnectable(new DeviceProperty()
                                        .setRead(true)
                                        .setType("boolean")
                                        .setWrite(false))
                                .setConnected(new DeviceProperty()
                                        .setRead(true)
                                        .setType("boolean")
                                        .setWrite(false)))
                            .setSerialNr("controllerSerialNr")
                            .setType("Qbus")
            })
            .setVersion("myVersion");

    static final Configuration CONFIGURATION_WITH_FUNCTIONBLOCKS = new Configuration()
            .setApp("myApp")
            .setDevices(new Device[]{
                    new Device()
                            .setFunctionBlocks(new FunctionBlock[]{})
                            .setId("myId1")
                            .setIp("192.168.0.1")
                            .setLocations(new Location[]{
                                    new Location()
                                            .setId(1)
                                            .setName("location1")
                                            .setLocations(new Location[]{
                                                    new Location()
                                                            .setId(2)
                                                            .setName("location2")
                                            }
                                    ),
                                    new Location()
                                            .setId(3)
                                            .setName("location3")
                            })
                            .setMac("123456789012")
                            .setName("myDeviceName1")
                            .setProperties(new DeviceProperties()
                                    .setConnectable(new DeviceProperty()
                                            .setRead(true)
                                            .setType("connectableType1")
                                            .setWrite(true))
                                    .setConnected(new DeviceProperty()
                                            .setRead(false)
                                            .setType("connectedType1")
                                            .setWrite(false)))
                            .setSerialNr("123456")
                            .setType("myType1")
                            .setVersion("1.2.3"),
                    new Device()
                            .setFunctionBlocks(new FunctionBlock[]{})
                            .setId("myId2")
                            .setIp("192.168.0.2")
                            .setLocations(new Location[]{})
                            .setMac("234567890123")
                            .setName("myDeviceName2")
                            .setProperties(new DeviceProperties()
                                    .setConnectable(new DeviceProperty()
                                            .setRead(false)
                                            .setType("connectableType2")
                                            .setWrite(false))
                                    .setConnected(new DeviceProperty()
                                            .setRead(true)
                                            .setType("connectedType2")
                                            .setWrite(true)))
                            .setSerialNr("234567")
                            .setType("myType2")
                            .setVersion("2.3.4")
            })
            .setVersion("myVersion");

    static final String CONFIGURATION_ONLY_CONTROLLER_JSON = new JSONObject(Map.of(
            "app", "myApp",
            "devices", new JSONObject[]{
                    new JSONObject(Map.of(
                            "functionBlocks", new JSONArray(),
                            "id", "controllerId",
                            "ip", "127.0.0.1",
                            "mac", "controllerMac",
                            "name", "controllerName",
                            "properties", new JSONObject(Map.of(
                                    "connectable", new JSONObject(Map.of(
                                            "read", true,
                                            "type", "boolean",
                                            "write", false
                                    )),
                                    "connected", new JSONObject(Map.of(
                                            "read", true,
                                            "type", "boolean",
                                            "write", false
                                    ))
                            )),
                            "serialNr", "controllerSerialNr",
                            "type", "Qbus"
                    ))
            },
            "version", "myVersion"
    )).toString();

    static final String CONFIGURATION_WITH_FUNCTIONBLOCKS_JSON = new JSONObject(Map.of(
            "app", "myApp",
            "devices", new JSONObject[]{
                    new JSONObject(Map.of(
                            "functionBlocks", new JSONArray(List.of(
                                    new JSONObject(Map.of(
                                            "actions", new JSONObject(Map.of(
                                                    "off", JSONObject.NULL,
                                                    "on", JSONObject.NULL
                                            )),
                                            "id", "outputId",
                                            "location", "",
                                            "locationId", 0,
                                            "name", "outputName",
                                            "originalName", "originalOutputName",
                                            "properties", new JSONObject(Map.of(
                                                    "value", new JSONObject(Map.of(
                                                            "read", true,
                                                            "type", "boolean",
                                                            "write", true
                                                    ))
                                            )),
                                            "refId", "outputRefId",
                                            "type", "onoff"
                                    ))
                            )),
                            "id", "controllerId",
                            "ip", "127.0.0.1",
                            "mac", "controllerMac",
                            "name", "controllerName",
                            "properties", new JSONObject(Map.of(
                                    "connectable", new JSONObject(Map.of(
                                            "read", true,
                                            "type", "boolean",
                                            "write", false
                                    )),
                                    "connected", new JSONObject(Map.of(
                                            "read", true,
                                            "type", "boolean",
                                            "write", false
                                    ))
                            )),
                            "serialNr", "controllerSerialNr",
                            "type", "Qbus"
                    ))
            },
            "version", "myVersion"
    )).toString();
}
