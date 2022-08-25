package com.java.sharepointintegrationapi.util;

import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderList {

    public static Node getNode(File node) {
        if (node.isDirectory()) {
            return new Node(node.getName(), "directory", getDirList(node));
        } else {
            return new Node(node.getName(), "file", null);
        }
    }

    public static List<Node> getDirList(File node) {
        List<Node> nodeList = new ArrayList<>();
        for (File n : node.listFiles()) {
            nodeList.add(getNode(n));
        }
        return nodeList;
    }

    @Data
    public static class Node {
        private String name;
        private String type;
        private List<Node> nodeList;

        public Node() {
        }

        public Node(String name, String type, List<Node> nodeList) {
            this.name = name;
            this.type = type;
            this.nodeList = nodeList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Node> getNodeList() {
            return nodeList;
        }

        public void setNodeList(List<Node> nodeList) {
            this.nodeList = nodeList;
        }
    }

}