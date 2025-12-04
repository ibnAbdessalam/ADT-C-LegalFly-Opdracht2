package be.odisee.legalfly.Service;

import java.util.ArrayList;
import java.util.List;

// maakt deel uit van COMPOSITE DESIGN PATTERN
public class CompositeReport implements ReportComponent {

    private final List<ReportComponent> components = new ArrayList<>();

    public void addComponent(ReportComponent component) {
        components.add(component);
    }

    @Override
    public void validate() {
        for (ReportComponent component : components) {
            component.validate(); // indien één faalt → exception → rollback
        }
    }

    @Override
    public String summarize() {
        StringBuilder sb = new StringBuilder("Samenvatting van rapport:\n");
        for (ReportComponent component : components) {
            sb.append("- ").append(component.summarize()).append("\n");
        }
        return sb.toString();
    }

    public List<ReportComponent> getComponents() {
        return components;
    }
}