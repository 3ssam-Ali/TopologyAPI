package com.EElshereif.TopologyAPI;

import java.util.HashMap;

public record Component(String id,String type, HashMap<String, String> netlist) {
}
