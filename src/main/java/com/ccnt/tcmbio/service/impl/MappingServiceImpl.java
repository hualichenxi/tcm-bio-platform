/**
 * MappingServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ccnt.tcmbio.dao.MappingDAO;
import com.ccnt.tcmbio.dao.OntologyDAO;
import com.ccnt.tcmbio.data.MappingData;
import com.ccnt.tcmbio.data.MappingSyncData;
import com.ccnt.tcmbio.data.OntologyData;
import com.ccnt.tcmbio.data.graph.Data;
import com.ccnt.tcmbio.data.graph.Edge;
import com.ccnt.tcmbio.data.graph.Graph;
import com.ccnt.tcmbio.data.graph.Graphml;
import com.ccnt.tcmbio.data.graph.Node;
import com.ccnt.tcmbio.enumdata.MappingSync;
import com.ccnt.tcmbio.service.MappingService;

public class MappingServiceImpl implements MappingService{

    private MappingDAO mappingDAO;
    private OntologyDAO ontologyDAO;
    private final String graphName = "http://localhost:8890/graph_mapping_relations";
    private final Object lockObject = new Object();

    private static final Logger LOGGER = LogManager.getLogger(MappingServiceImpl.class.getName());

    public void setMappingDAO(final MappingDAO mappingDAO) {
        this.mappingDAO = mappingDAO;
    }

    public void setOntologyDAO(final OntologyDAO ontologyDAO) {
        this.ontologyDAO = ontologyDAO;
    }

    @Override
    public Integer syncMappingGraph(){

        LOGGER.debug("synchronization process begins");
        try {

            synchronized (lockObject) {
                if (MappingSync.INSTANCE.getStatus() == 1) {
                    return -1;
                }
                MappingSync.INSTANCE.setStatus(1);
            }

            if (!mappingDAO.ifExistMappingGraph(graphName)) {
                mappingDAO.newMappingGraph(graphName);
            }

            final ArrayList<OntologyData> ontologyDatas = ontologyDAO.findAllCachedOntologies("http://localhost:8890/graph-resource");
            final Integer ontologyDatasLength = ontologyDatas.size();
            Integer ontologyItemSum = 1;
            Integer ontologyDatasLengthCurr = 0;
            Integer ontologyItemCurr = 1;

            Iterator<OntologyData> ontoIterator = ontologyDatas.iterator();
            while (ontoIterator.hasNext()) {
                final OntologyData ontologyData = ontoIterator.next();
                ontologyItemSum += ontologyData.getItemnum();
            }
            LOGGER.debug("ontology item sum is: {}", ontologyItemSum);

            final long startTime =  System.currentTimeMillis();

            ontoIterator = ontologyDatas.iterator();
            while (ontoIterator.hasNext()) {
                final OntologyData ontologyData = ontoIterator.next();
                int mappingcount = 0;
                final String graph1 = ontologyData.getName();

                LOGGER.debug("synchronization process @ graph: {}", graph1);

                final Iterator<OntologyData> inOntoIterator = ontologyDatas.iterator();
                while (inOntoIterator.hasNext()) {
                    final OntologyData inOntologyData = inOntoIterator.next();
                    final String graph2 = inOntologyData.getName();
                    if (!graph1.equals(graph2)) {

                        LOGGER.debug("mapping test of the two graphs: {} & {}", graph1, graph2);

                        final Integer count = mappingDAO.askIfMapping(graph1, graph2);
                        if (count > 0) {
                            mappingDAO.insertMappingDetailTriple(graphName, graph1, graph2, count);
                            mappingcount++;
                        }
                    }
                }

                final long middleTime = System.currentTimeMillis();
                final long elapasedTime = middleTime - startTime;
                MappingSync.INSTANCE.setPassedTime(elapasedTimeToString(elapasedTime));

                final long estimateTotalTime = elapasedTime * ontologyItemSum / ontologyItemCurr;
                MappingSync.INSTANCE.setEstimateTime(elapasedTimeToString(estimateTotalTime));

                ontologyDatasLengthCurr++;
                ontologyItemCurr += ontologyData.getItemnum();
                MappingSync.INSTANCE.setOntologyPercentF(ontologyDatasLengthCurr.toString() + '/' + ontologyDatasLength.toString());
                MappingSync.INSTANCE.setOntologyPercent(ontologyDatasLengthCurr*100/ontologyDatasLength);
                MappingSync.INSTANCE.setItemPercentF(ontologyItemCurr.toString() + '/' + ontologyItemSum.toString());
                MappingSync.INSTANCE.setItemPercent(ontologyItemCurr*100/ontologyItemSum);

                mappingDAO.insertMappingTriple(graphName, graph1, mappingcount);
            }
            MappingSync.INSTANCE.setStatus(0);
            return 1;
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            MappingSync.INSTANCE.setStatus(-1);
            e.printStackTrace();
        }
        MappingSync.INSTANCE.setStatus(0);
        return -1;
    }

    @Override
    public String elapasedTimeToString(final long elapasedTIme){

        LOGGER.debug("Mark the time: {}", elapasedTIme);

        final long hour = elapasedTIme/(3600*1000);
        final long minute = (elapasedTIme - hour * 3600) / (60*1000);
        final long second = (elapasedTIme - hour * 3600 - minute * 60)/1000;
        final String hourString = Long.toString(hour);
        final String minuString = Long.toString(minute);
        final String secoString = Long.toString(second);
        return hourString + "h " + minuString + "m " + secoString + "s";
    }

    @Override
    public MappingSyncData syncMappingProgress(){
        final MappingSyncData mappingSyncData = new MappingSyncData();
        mappingSyncData.setStatus(MappingSync.INSTANCE.getStatus());
        mappingSyncData.setEstimateTime(MappingSync.INSTANCE.getEstimateTime());
        mappingSyncData.setItemPercent(MappingSync.INSTANCE.getItemPercent());
        mappingSyncData.setItemPercentF(MappingSync.INSTANCE.getItemPercentF());
        mappingSyncData.setOntologyPercent(MappingSync.INSTANCE.getOntologyPercent());
        mappingSyncData.setOntologyPercentF(MappingSync.INSTANCE.getOntologyPercentF());
        mappingSyncData.setPassedTime(MappingSync.INSTANCE.getPassedTime());
        return mappingSyncData;
    }

    @Override
    public boolean clearMappingGraph(){

        LOGGER.debug("Drop the mapping graph");

        return mappingDAO.dropMappingGraph(graphName);
    }

    @Override
    public ArrayList<MappingData> getMappings(){

        LOGGER.debug("Get mapping relation of all graphs");

        if (!mappingDAO.ifExistMappingGraph(graphName)) {
            LOGGER.debug("No mapping graph exits, start the synchonise job");
            syncMappingGraph();
        } else {
            // UNDO
//            mappingDAO.dropMappingGraph(graphName);
//            syncMappingGraph();
        }
        return mappingDAO.getMappings();
    }

    @Override
    public ArrayList<MappingData> searchMappings(final String keyword){

        LOGGER.debug("Search the mapping relations");

        return null;
    }

    @Override
    public ArrayList<MappingData> getMappingDetails(final String graphName){

        LOGGER.debug("Get the mapping details of graph: {}", graphName);

        return mappingDAO.getMappingDetails(graphName);
    }
    
    @Override
    public Graphml getMappingGraph(){
    	
    	LOGGER.debug("Get Mapping Graph");
    	
    	final ArrayList<Node> nodes = new ArrayList<Node>();
        final ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<MappingData> list=this.getMappings();
        HashMap<String, String> nodeMap=new HashMap<String, String>();
        int nodeId=0;
        for(final MappingData data: list){
        	final String name=data.getOntoName();
        	final String id="node#"+nodeId;
        	nodeMap.put(name, id);
        	final Data[] nodeData = new Data[2];
            nodeData[0] = new Data("k-node", name);
            nodeData[1] = new Data("label", name);
            final Node node = new Node(id, nodeData);
            nodes.add(node);
        	nodeId++;
        }
        int edgeId=0;
        Set<String> keyset=nodeMap.keySet();
        Iterator<String> iter=keyset.iterator();
        HashMap<String, String> notFoundNodeMap=new HashMap<String, String>();
        while(iter.hasNext()){
        	String curNodeName=iter.next();
        	String curNodeId=nodeMap.get(curNodeName);
        	ArrayList<String> toNodes = mappingDAO.getRelativeGraph(curNodeName);
        	for(final String mappingNode : toNodes ){
        		String toName = mappingNode;
        		String toId = null;
        		if(!nodeMap.containsKey(toName) && !nodeMap.containsKey(toName)){
        			String id="node#"+nodeId;
        			notFoundNodeMap.put(toName, id);
        			final Data[] nodeData = new Data[2];
                    nodeData[0] = new Data("k-node", toName);
                    nodeData[1] = new Data("label", toName);
                    final Node node = new Node(id, nodeData);
                    nodes.add(node);
                	nodeId++;
                	toId=id;
        		}
        		else if(!nodeMap.containsKey(toName)){
        			toId=notFoundNodeMap.get(toName);
        		}
        		else{
        			toId=nodeMap.get(toName);
        		}
        		final Data[] edgeData= new Data[2];
                edgeData[0] = new Data("k-edge", curNodeName+"-----"+toName);
                edgeData[1] = new Data("label", "map");
                final Edge edge = new Edge(curNodeId, toId, "edge#"+edgeId, edgeData);
                edges.add(edge);
                edgeId++;
        	}
        }
        final Graph graph = new Graph(nodes, edges, "G#0", "undirected");
    	return new Graphml(graph);
    }
}
