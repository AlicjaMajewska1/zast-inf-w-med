package pl.edu.studia.zastinfwmed.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.studia.zastinfwmed.logic.EcgData;
import pl.edu.studia.zastinfwmed.logic.EcgService;
import pl.edu.studia.zastinfwmed.logic.SampleFiles;
import pl.edu.studia.zastinfwmed.logic.JsWritter;

import java.io.IOException;

/**
 * Created by Alicja on 2017-05-27.
 */

@Controller
public class WebController {

    @Autowired
    private EcgService ecgService;

    @RequestMapping(value = "/charts", method = RequestMethod.GET)
    public String drawChart(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "charts";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showCommonInfo(Model model) {
        model.addAttribute("samplefiles", SampleFiles.values());
        SelectedFile selectedFile = new SelectedFile();
        model.addAttribute(selectedFile);
        return "index";
    }

    @RequestMapping(value = "/processForm", method=RequestMethod.POST)
    public String processForm(@ModelAttribute(value="selectedFile") SelectedFile selectedFile, Model model) throws IOException {
        EcgData ecgData = ecgService.calculate(selectedFile.getFileName());
        model.addAttribute("ecgData", ecgData);

        JsWritter.writeDataToJs(ecgData);
        JsWritter.writeDataToJsForAmCharts(ecgData);
        return "charts";
    }
}