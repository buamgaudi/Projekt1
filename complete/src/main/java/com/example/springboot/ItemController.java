package com.example.springboot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import inginf.Item;
import inginf.ItemInstance;
import inginf.ItemInstance;

import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {

	@Autowired
	private ApplicationContext context;
    AppStore _AppStore;
    AppStore getAppStore() {
        if (_AppStore == null)
            _AppStore = context.getBean(AppStore.class);
        return _AppStore;
    }

    @GetMapping("/assemblies-gui")
    public String editAssemblies(Model model) {
        model.addAttribute("assemblies", getAppStore().getItemStore());
        return "editAssemblies";
    }

    @PostMapping("/assemblies-gui/addPart")
    public String addPartToAssembly(
        @RequestParam("assemblyId") int assemblyId,
        @RequestParam("partName") String partName,
        @RequestParam("partDescription") String partDescription,
        @RequestParam("partMaterial") String partMaterial,
        Model model) {
        
        Item assembly = getAppStore().getItemStore().stream()
            .filter(item -> item.getId() == assemblyId)
            .findFirst()
            .orElse(null);

        if (assembly != null) {
            Item part = new Item(partName, partDescription, partMaterial);
            ItemInstance partInstance = new ItemInstance(partName, part);
            assembly.getUses().add(partInstance);
        }

        model.addAttribute("assemblies", getAppStore().getItemStore());
        return "editAssemblies";
    }

    @PostMapping("/assemblies-gui/removePart")
    public String removePartFromAssembly(
        @RequestParam("assemblyId") int assemblyId,
        @RequestParam("partId") int partId,
        Model model) {
        
        Item assembly = getAppStore().getItemStore().stream()
            .filter(item -> item.getId() == assemblyId)
            .findFirst()
            .orElse(null);

        if (assembly != null) {
            assembly.getUses().removeIf(partInstance -> partInstance.getRepresents().getId() == partId);
        }

        model.addAttribute("assemblies", getAppStore().getItemStore());
        return "editAssemblies";
    }

    @GetMapping("/assemblies-gui")
    public String editAssemblies(Model model) {
        model.addAttribute("assemblies", getAppStore().getItemStore());
        return "editAssemblies";
    }

    @PostMapping("/assemblies-gui/addPart")
    public String addPartToAssembly(
        @RequestParam("assemblyId") int assemblyId,
        @RequestParam("partName") String partName,
        @RequestParam("partDescription") String partDescription,
        @RequestParam("partMaterial") String partMaterial,
        Model model) {
        
        Item assembly = getAppStore().getItemStore().stream()
            .filter(item -> item.getId() == assemblyId)
            .findFirst()
            .orElse(null);

        if (assembly != null) {
            Item part = new Item(partName, partDescription, partMaterial);
            ItemInstance partInstance = new ItemInstance(partName, part);
            assembly.getUses().add(partInstance);
        }

        model.addAttribute("assemblies", getAppStore().getItemStore());
        return "editAssemblies";
    }

    @PostMapping("/assemblies-gui/removePart")
    public String removePartFromAssembly(
        @RequestParam("assemblyId") int assemblyId,
        @RequestParam("partId") int partId,
        Model model) {
        
        Item assembly = getAppStore().getItemStore().stream()
            .filter(item -> item.getId() == assemblyId)
            .findFirst()
            .orElse(null);

        if (assembly != null) {
            assembly.getUses().removeIf(partInstance -> partInstance.getRepresents().getId() == partId);
        }

        model.addAttribute("assemblies", getAppStore().getItemStore());
        return "editAssemblies";
    }
    public String createItem(
        Model model,
        HttpSession session,
        @RequestParam Map<String, String> body ) 
    {        
        inginf.Item item = new inginf.Item(
            body.get("Nomenclature"),
            body.get("Description"),
            body.get("Material"));
        if (body.get("WeightedWeight") != null && body.get("WeightedWeight").length() > 0)
            item.setWeightedWeight(Integer.parseInt(body.get("WeightedWeight")));
        if (body.get("CalculatedWeight") != null && body.get("CalculatedWeight").length() > 0)
            item.setCalculatedWeight(Integer.parseInt(body.get("CalculatedWeight")));
        if (body.get("EstimatedWeight") != null && body.get("EstimatedWeight").length() > 0)
            item.setEstimatedWeight(Integer.parseInt(body.get("EstimatedWeight")));
        getAppStore().addNewItem(item);
        model.addAttribute(
                "id", item.Id);
        return "itemCreated";
    }

    @GetMapping("/items-gui")
    public String createItemDialog() {
        return "itemTemplate";
    } 
    
    @GetMapping("/items-gui/list")
    public String listItems(Model model) {
        model.addAttribute(
            "items", 
            getAppStore().getItemStore());
        return "listItems";
    }

    @GetMapping("/items-gui/{id}/delete")
    public String deleteItem(@PathVariable int id, Model model) {        
        model.addAttribute(
            "id", id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == id) {
                getAppStore().getItemStore().remove(item);
                break;
            }
        return "itemDeleted";
    }

    @GetMapping("/items-gui/{id}/show")
    public String showItem(@PathVariable int id, Model model) {        
        model.addAttribute(
            "id", id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == id) {
                model.addAttribute(
                    "item", item);
                break;
            }
        return "showItem";
    }
}