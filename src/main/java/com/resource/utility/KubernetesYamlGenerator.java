package com.resource.utility;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class KubernetesYamlGenerator {
    public static void generateDeploymentYaml(String microserviceName, String nodeName) {
        String yamlContent =
                "apiVersion: apps/v1\n" +
                        "kind: Deployment\n" +
                        "metadata:\n" +
                        "  name: " + microserviceName + "\n" +
                        "spec:\n" +
                        "  replicas: 1\n" +
                        "  selector:\n" +
                        "    matchLabels:\n" +
                        "      app: " + microserviceName + "\n" +
                        "  template:\n" +
                        "    metadata:\n" +
                        "      labels:\n" +
                        "        app: " + microserviceName + "\n" +
                        "    spec:\n" +
                        "      nodeSelector:\n" +
                        "        kubernetes.io/hostname: " + nodeName + "\n" +
                        "      containers:\n" +
                        "      - name: " + microserviceName + "\n" +
                        "        image: myregistry/" + microserviceName + ":latest\n" +
                        "        ports:\n" +
                        "        - containerPort: 8080\n";

        try {
            File file = new File(microserviceName + "-deployment.yaml");
            FileWriter writer = new FileWriter(file);
            writer.write(yamlContent);
            writer.close();
            System.out.println(" Deployment YAML created: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
