package online.comfyplace.qbusmqtt;

import online.comfyplace.qbusmqtt.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
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
                            .setFunctionBlocks(new FunctionBlock[]{
                                    new FunctionBlock.OnOffFunctionBlock()
                                            .setActions(new HashMap<>() {{
                                                put("off", null);
                                                put("on", null);
                                            }})
                                            .setId("outputId")
                                            .setLocation("")
                                            .setLocationId(0)
                                            .setName("outputName")
                                            .setOriginalName("originalOutputName")
                                            .setProperties(new FunctionBlockProperties<FunctionBlockValue.OnOffValue>()
                                                    .setValue(new FunctionBlockValue.OnOffValue()
                                                            .setRead(true)
                                                            .setType("boolean")
                                                            .setWrite(true))
                                            )
                                            .setRefId("outputRefId")
                            })
                            .setId("controllerId")
                            .setIp("127.0.0.1")
                            .setLocations(new Location[]{})
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
                            .setVersion("controllerVersion")
            })
            .setVersion("configVersion");

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
                            "locations", new JSONArray(),
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
                            "type", "Qbus",
                            "version", "controllerVersion"
                    ))
            },
            "version", "configVersion"
    )).toString();
}
