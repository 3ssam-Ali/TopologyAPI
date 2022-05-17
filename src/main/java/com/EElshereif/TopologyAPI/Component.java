package com.EElshereif.TopologyAPI;

import java.util.HashMap;
/**
 * A record representing a component Object in a topology
 * @param id : The unique ID of the component
 * @param type : The type of the component
 * @param netlist : Hashmap< String, String > holds the List of connected devices to this component
 */
public record Component(String id,String type, HashMap<String, String> netlist) {
}
