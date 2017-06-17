package pl.edu.studia.zastinfwmed.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.studia.zastinfwmed.logic.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Alicja on 2017-05-27.
 */

@Controller
public class WebController {

    private final EcgService ecgService;

    private final StorageService storageService;
    private final List<DataFile> dataFileList = new ArrayList<>(SampleFiles.values().length);

    @Autowired
    public WebController(StorageService storageService, EcgService ecgService) {
        this.storageService = storageService;
        this.ecgService = ecgService;
    }

    private EcgData ecgData;

 /*   @RequestMapping(value = "/charts", method = RequestMethod.GET)
    public String drawChart(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) throws IOException {

        EcgData ecgData = ecgService.calculate(new DataFile(1, SampleFiles.SAMPLE1.name()));
        model.addAttribute("ecgData", ecgData);
        return "charts";
    }*/

    @RequestMapping(value = "/documentation", method = RequestMethod.GET)
    public String showDocumentation(Model model) throws IOException {
        return "documentation";
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showCommonInfo(Model model) {

        List<String> serveFiles = storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(WebController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList());
        model.addAttribute("files", serveFiles);

        List<Path> serveFilesNames = storageService.loadAll().collect(Collectors.toList());


        dataFileList.clear();
        long index = 0;
        for (SampleFiles sampleFile : SampleFiles.values()) {
            dataFileList.add(new DataFile(index++, sampleFile.toString()));
        }
        for (Path uploadedFile : serveFilesNames) {
            dataFileList.add(new DataFile(index++, uploadedFile.getFileName().toString(), "./upload-dir/" + uploadedFile.getFileName().getFileName(), false));
        }

        model.addAttribute("samplefiles", dataFileList);
        SelectedFile selectedFile = new SelectedFile();
        model.addAttribute(selectedFile);
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/loadChartData", method = RequestMethod.GET)
    public List<CharDataSample> loadChartData(@RequestParam(value = "filename", required = true) String filename) throws IOException {
        return JsonWritter.writeDataToSamples(ecgData);
    }

    @RequestMapping(value = "/processForm", method = RequestMethod.POST)
    public String processForm(@ModelAttribute(value = "selectedFile") SelectedFile selectedFile, Model model) throws IOException {

        ecgData = ecgService.calculate(dataFileList.stream().filter(dataFile -> dataFile.getId() == selectedFile.getDataFile().getId()).findFirst().get());
        model.addAttribute("ecgData", ecgData);
        return "charts";
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file != null && (!file.isEmpty() && !file.getName().isEmpty())) {
            if (storageService.loadAll().filter(path -> !path.getFileName().equals(file.getOriginalFilename())).collect(Collectors.toList()).isEmpty()) {
                storageService.store(file);
                redirectAttributes.addFlashAttribute("message",
                        "Dodano plik " + file.getOriginalFilename() + "!");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "Plik o podanej nazwie " + file.getOriginalFilename() + " został już dodany.");
            }
        }
        return "redirect:/index";
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity handleStorageFileNotFound(StorageException exc) {
        return ResponseEntity.notFound().build();
    }
}