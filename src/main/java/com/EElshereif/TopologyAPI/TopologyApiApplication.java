package com.EElshereif.TopologyAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@RestController
public class TopologyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopologyApiApplication.class, args);
	}

	@GetMapping("/")
	public String greeting() {
		return "Welcome";
	}

	@GetMapping("/TryWriting")
	public String wTestPoint (){
		JacksonTest JTest= new JacksonTest();
		JTest.WriteTest();
		return "Done";
	}

	@GetMapping("/TryReading")
	public Topology rTestPoint(){
		JacksonTest JTest= new JacksonTest();
		return JTest.ReadTest();
	}

	@GetMapping("/LoadJSON")
	public String _load(@RequestParam(value = "filename", defaultValue = "topology") String fileName){
		TopologyController.loadTopology(fileName);
		return "Loaded";
	}

	@GetMapping("/WriteJSON")
	public String _write(@RequestParam(value = "topologyid", defaultValue = "top1") String topologyID){
		TopologyController.writeTopology(topologyID);
		return "File Written to Disk Successfully";
	}

	@GetMapping("/QueryTopologies")
	public HashMap<String, Topology> _queryTopologies(){
		return TopologyController.getLoadedTopologies();
	}

	@GetMapping("/Delete")
	public String _delete(@RequestParam(value = "topologyid", defaultValue = "top1") String topologyID){
		TopologyController.unloadTopology(topologyID);
		return "deleted";
	}

	@GetMapping("/QueryDevices")
	public List<Component> _queryDevices(@RequestParam(value = "topologyid", defaultValue = "top1") String topologyID){
		return TopologyController.getDevices(topologyID);
	}

	@GetMapping("/GetConnections")
	public HashMap<String, String> _getConnections(
			@RequestParam(value = "topologyid", defaultValue = "top1") String topologyID ,
			@RequestParam(value = "componentid", defaultValue = "res1") String componentID){
		return TopologyController.getConnections(topologyID,componentID);

	}



}
