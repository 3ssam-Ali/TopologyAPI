package com.EElshereif.TopologyAPI;

import com.EElshereif.TopologyAPI.Exceptions.JsonNotFoundException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Class Contains Static Methods to Manage and Query Topologies.
 *
 * @author Essam
 */
public class TopologyController {
    public static HashMap<String, Topology> loadedTopologies = new HashMap<>();

    /**
     * Load a topology from a JSON file from the disk
     *
     * @param filename : the name of the file to be loaded
     */
    public static void loadTopology(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String path = String.format("file:src/%s.json", filename);
        try {
            URL filePath = new URL(path);
            Topology topology = objectMapper.readValue(filePath, Topology.class);
            loadedTopologies.put(topology.id, topology);
        } catch (IOException e) {
            throw new JsonNotFoundException(filename);
        }
    }

    /**
     * Write a loaded topology into the disk as a JSON file
     *
     * @param topologyID : the id for the topology to write
     */
    public static void writeTopology(String topologyID) {
        if (!loadedTopologies.containsKey(topologyID)) {
            // should throw an error or return a message
            return;
        }
        Topology topology = loadedTopologies.get(topologyID);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String filePath = String.format("target/%s.json", topologyID);
            objectMapper.writeValue(new File(filePath), topology);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * return all topologies loaded in the memory
     */
    public static HashMap<String, Topology> getLoadedTopologies() {
        return loadedTopologies;
    }

    /**
     * delete a topology from the memory
     *
     * @param topologyID : the id for the topology to remove
     */
    public static void unloadTopology(String topologyID) {
        loadedTopologies.remove(topologyID);
    }

    /**
     * return list of devices in the selected topology
     *
     * @param topologyID String : the id for the topology to inquire about
     */
    public static List<Component> getDevices(String topologyID) {
        Topology topology = loadedTopologies.get(topologyID);
        return topology.components;
    }

    /**
     * Return the net-list of a specific component
     *
     * @param topologyID  : the id for the topology
     * @param componentID : the id for the specific component
     */
    public static HashMap<String, String> getConnections(String topologyID, String componentID) {
        Topology topology = loadedTopologies.get(topologyID);
        for (Component component : topology.components) {
            if (component.id().equals(componentID)) {
                return component.netlist();
            }
        }
        return null;
    }


}
