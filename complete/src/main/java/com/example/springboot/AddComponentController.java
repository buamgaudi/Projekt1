package com.example.springboot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inginf.Item;
import inginf.ItemInstance;

@Controller
public class AddComponentController {

    @Autowired
    private ApplicationContext context;

    private AppStore getAppStore() {
        return context.getBean(AppStore.class);
    }

    @GetMapping("/add-component")
    public String addComponentToAssemblyForm(Model model) {
        model.addAttribute("assemblies", getAppStore().getItemStore());
        return "addComponentToAssembly";
    }

    @PostMapping("/add-component")
    public String addComponentToAssembly(
        @RequestParam Map<String, String> body,
        Model model) 
    {
        int assemblyId = Integer.parseInt(body.get("assemblyId"));
        String componentName = body.get("componentName");
        String componentDescription = body.get("componentDescription");
        String componentMaterial = body.get("componentMaterial");
        String usageString = body.get("usageString");

        Item component = new Item(componentName, componentDescription, componentMaterial);
        ItemInstance componentInstance = new ItemInstance(usageString, component);

        for (Item assembly : getAppStore().getItemStore()) {
            if (assembly.getId() == assemblyId) {
                assembly.getUses().add(componentInstance);
                break;
            }
        }

        model.addAttribute("id", component.getId());
        return "itemCreated";
    }
}
