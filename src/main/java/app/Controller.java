package app;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

// On utilise  l'API Jackson pour LIRE le JSON et convertir XML -> JSON
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.List;
import java.util.Map;

public class Controller {

    @FXML private TextArea inputArea;
    @FXML private TextArea outputArea;

    // --- Action 1 : XML vers JSON  ---
    @FXML
    protected void convertXmlToJson() {
        String xmlText = inputArea.getText();
        if (xmlText.isEmpty()) { showAlert("Erreur", "Zone vide !"); return; }

        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(xmlText.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            String jsonOutput = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
            outputArea.setText(jsonOutput);
        } catch (Exception e) {
            showAlert("Erreur XML", e.getMessage());
        }
    }

    // --- Action 2 : JSON vers XML (VERSION MANUELLE ) ---
    @FXML
    protected void convertJsonToXml() {
        String jsonText = inputArea.getText();
        if (jsonText.isEmpty()) { showAlert("Erreur", "Zone vide !"); return; }

        try {
            // Étape 1 : On lit le JSON pour le transformer en objets Java simples (Map ou List)
            // Note : On utilise ObjectMapper juste pour le parsing (lecture), pas pour la conversion.
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(jsonText, Object.class);

            // Étape 2 : On construit le XML manuellement
            StringBuilder xmlBuilder = new StringBuilder();

            // On ajoute une racine car le XML l'exige
            xmlBuilder.append("<root>\n");

            // Appel de notre fonction récursive manuelle
            construireXmlRecursif(jsonObject, xmlBuilder, "  ");

            xmlBuilder.append("</root>");

            outputArea.setText(xmlBuilder.toString());

        } catch (Exception e) {
            showAlert("Erreur JSON", "Format invalide ou trop complexe.\n" + e.getMessage());
        }
    }

    // --- NOTRE FONCTION MANUELLE (Le "moteur" maison) ---
    private void construireXmlRecursif(Object obj, StringBuilder xml, String indent) {

        // CAS 1 : C'est un Objet JSON { "clé": "valeur" } -> Map en Java
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;

            // On parcourt chaque clé du dictionnaire
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                // Si la valeur est simple (texte/nombre), on l'écrit direct
                if (isSimple(value)) {
                    xml.append(indent)
                            .append("<").append(key).append(">")
                            .append(value)
                            .append("</").append(key).append(">\n");
                }
                // Si c'est complexe (objet ou liste), on ouvre la balise et on rappelle la fonction (récursivité)
                else {
                    xml.append(indent).append("<").append(key).append(">\n");
                    construireXmlRecursif(value, xml, indent + "  ");
                    xml.append(indent).append("</").append(key).append(">\n");
                }
            }
        }

        // CAS 2 : C'est une Liste JSON [ "a", "b" ] -> List en Java
        else if (obj instanceof List) {
            List<Object> list = (List<Object>) obj;
            for (Object item : list) {
                // Pour les listes, on crée une balise générique <item>
                xml.append(indent).append("<item>\n");
                construireXmlRecursif(item, xml, indent + "  ");
                xml.append(indent).append("</item>\n");
            }
        }

        // CAS 3 : C'est juste une valeur simple (String, Int, Boolean)
        else if (obj != null) {
            xml.append(indent).append(obj.toString()).append("\n");
        }
    }

    // Petite aide pour savoir si c'est un type simple
    private boolean isSimple(Object obj) {
        return obj instanceof String || obj instanceof Number || obj instanceof Boolean;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}