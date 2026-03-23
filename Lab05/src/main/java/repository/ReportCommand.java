package repository;

import exception.CatalogException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand implements Command{
    private Catalog catalog;

    public ReportCommand(Catalog catalog){
        this.catalog = catalog;
    }


    @Override
    public void execute() throws CatalogException{

        try{
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);

            cfg.setDirectoryForTemplateLoading(new File("templates"));
            cfg.setDefaultEncoding("UTF-8");

            Template template = cfg.getTemplate("report.ftl");

            Map<String, Object> templateData = new HashMap<>();

            templateData.put("resources", catalog.getResources());

            File htmlFile = new File("catalog_report.html");

            try (Writer fileWriter = new FileWriter(htmlFile)) {
                template.process(templateData,fileWriter);
            }

            if(Desktop.isDesktopSupported()){
                Desktop.getDesktop().open(htmlFile);
                System.out.println("Report Opened successfully.");
            }
            else {
                System.out.println("Report Opening failed, but it was saved at: " + htmlFile.getAbsolutePath());
            }

        } catch (IOException | TemplateException e) {
            throw new CatalogException("Error opening report.ftl",e);
        }
    }
}
