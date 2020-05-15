package com.michal.document.controller;

import com.michal.document.entities.Document;
import com.michal.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;

    @RequestMapping("/displayUpload")
    public String displayUpload(ModelMap modelMap) {
        List<Document> documents = documentRepository.findAll();
        modelMap.addAttribute("documents", documents);
        return "documentUpload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadDocument(@RequestParam(name = "document") MultipartFile multipartFile, @RequestParam long id, ModelMap modelMap) {
        Document document = new Document();
        document.setId(id);
        document.setName(multipartFile.getOriginalFilename());
        try {
            document.setData(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        documentRepository.save(document);
        List<Document> documents = documentRepository.findAll();
        modelMap.addAttribute("documents", documents);
        return "documentUpload";
    }

    @RequestMapping("/download")
    public StreamingResponseBody download(@RequestParam long id, HttpServletResponse response) {
        Document document = documentRepository.getOne(id);
        byte[] data = document.getData();
        response.setHeader("Content-Disposition", "attachment;filename=downloaded.jpg");
        return outputStream -> {
            outputStream.write(data);
        };
    }
}
