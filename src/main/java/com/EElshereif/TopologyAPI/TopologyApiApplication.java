package com.EElshereif.TopologyAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@RestController
@OpenAPIDefinition(info = @Info(title = "Topologies API", version = "1.0",
        description = " access, manage and store electrical device topologies"))
public class TopologyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopologyApiApplication.class, args);
    }

    @GetMapping("/")
    public String greeting() {
        return "Welcome";
    }

//	@GetMapping("/TryWriting")
//	public String wTestPoint (){
//		JacksonTest JTest= new JacksonTest();
//		JTest.WriteTest();
//		return "Done";
//	}
//
//	@GetMapping("/TryReading")
//	public Topology rTestPoint(){
//		JacksonTest JTest= new JacksonTest();
//		return JTest.ReadTest();
//	}

    @Operation(summary = "Load a topology into memory by its filename.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the topology",
                    content = {@Content(mediaType = "string")}),
            @ApiResponse(responseCode = "404", description = "Topology File not found",
                    content = @Content)})
    @GetMapping("/LoadJSON")
    public String _load(@RequestParam(value = "filename", defaultValue = "topology") String fileName) {
        TopologyController.loadTopology(fileName);
        return "Loaded";
    }

    @Operation(summary = "Write a topology from memory into disk.")
    @ApiResponse(responseCode = "200", description = "Topology is stored successfully.",
            content = {@Content(mediaType = "string")})
    @GetMapping("/WriteJSON")
    public String _write(@RequestParam(value = "topologyid", defaultValue = "top1") String topologyID) {
        TopologyController.writeTopology(topologyID);
        return "File Written to Disk Successfully";
    }

    @Operation(summary = "View the topologies loaded in memory.")
    @ApiResponse(responseCode = "200", description = "",
            content = {@Content(mediaType = "", schema = @Schema(implementation = Topology.class))})
    @GetMapping("/QueryTopologies")
    public HashMap<String, Topology> _queryTopologies() {
        return TopologyController.getLoadedTopologies();
    }

    @Operation(summary = "Unload a Topology from memory.")
    @GetMapping("/Delete")
    public String _delete(@RequestParam(value = "topologyid", defaultValue = "top1") String topologyID) {
        TopologyController.unloadTopology(topologyID);
        return "deleted";
    }

    @Operation(summary = "View Device list in a topology.")
    @GetMapping("/QueryDevices")
    public List<Component> _queryDevices(@RequestParam(value = "topologyid", defaultValue = "top1") String topologyID) {
        return TopologyController.getDevices(topologyID);
    }

    @Operation(summary = "View the connections of a specific device in a topology.")
    @GetMapping("/GetConnections")
    public HashMap<String, String> _getConnections(
            @RequestParam(value = "topologyid", defaultValue = "top1") String topologyID,
            @RequestParam(value = "componentid", defaultValue = "res1") String componentID) {
        return TopologyController.getConnections(topologyID, componentID);

    }
}
