package com.lms.controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.service.BookService;
import com.lms.service.PersonService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/visualization")
public class VisualizationController {

    private static final String IMG_FOLDER = "img";
    private static final String BAR_CHART_FILE_NAME = "bar-chart.png";
    private static final String PIE_CHART_FILE_NAME = "pie-chart.png";
    private static final String LINE_CHART_FILE_NAME = "line-chart.png";

    @Autowired
    private BookService bookService;
    
    @Autowired
    private PersonService personService;

    @GetMapping("/barChart")
    public String generateBarChart() {
        try {
            CategoryDataset dataset = createBarDataset();
            JFreeChart barChart = createBarChart(dataset, "Books Borrowed vs People Registered");
            saveChart(barChart, BAR_CHART_FILE_NAME);
            return "Bar chart generated successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating bar chart: " + e.getMessage();
        }
    }

    @GetMapping("/pieChart")
    public String generatePieChart() {
        try {
            DefaultPieDataset dataset = createPieDataset();
            JFreeChart pieChart = createPieChart(dataset, "Books Borrowed vs People Registered");
            saveChart(pieChart, PIE_CHART_FILE_NAME);
            return "Pie chart generated successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating pie chart: " + e.getMessage();
        }
    }

    @GetMapping("/lineChart")
    public String generateLineChart() {
        try {
            CategoryDataset dataset = createLineDataset();
            JFreeChart lineChart = createLineChart(dataset, "Books Borrowed vs People Registered");
            saveChart(lineChart, LINE_CHART_FILE_NAME);
            return "Line chart generated successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating line chart: " + e.getMessage();
        }
    }

    private void saveChart(JFreeChart chart, String fileName) throws IOException {
        String currentWorkingDir = System.getProperty("user.dir");
        Path imgFolderPath = Paths.get(currentWorkingDir, IMG_FOLDER);

        if (!Files.exists(imgFolderPath)) {
            Files.createDirectories(imgFolderPath);
        }

        String filePath = imgFolderPath.resolve(fileName).toString();
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 800, 600);
        System.out.println("Chart generated successfully. Check " + filePath);
    }

    private CategoryDataset createBarDataset() {
        Long numberOfBooksBorrowed = bookService.getNumberOfBooksBorrowed();
        Long numberOfPeopleRegistered = personService.getNumberOfPeopleRegistered();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(numberOfBooksBorrowed, "Count", "Books Borrowed");
        dataset.addValue(numberOfPeopleRegistered, "Count", "People Registered");

        return dataset;
    }

    private DefaultPieDataset createPieDataset() {
        Long numberOfBooksBorrowed = bookService.getNumberOfBooksBorrowed();
        Long numberOfPeopleRegistered = personService.getNumberOfPeopleRegistered();

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Books Borrowed", numberOfBooksBorrowed);
        dataset.setValue("People Registered", numberOfPeopleRegistered);

        return dataset;
    }

    private CategoryDataset createLineDataset() {
        // Fetch actual data from your service
        Long numberOfBooksBorrowed = bookService.getNumberOfBooksBorrowed();
        Long numberOfPeopleRegistered = personService.getNumberOfPeopleRegistered();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(numberOfBooksBorrowed, "Count", "Books Borrowed");
        dataset.addValue(numberOfPeopleRegistered, "Count", "People Registered");

        return dataset;
    }

    private JFreeChart createBarChart(CategoryDataset dataset, String title) {
        return ChartFactory.createBarChart(
                title,
                "Category",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
    }

    private JFreeChart createPieChart(DefaultPieDataset dataset, String title) {
        return ChartFactory.createPieChart(
                title,
                dataset,
                true,
                true,
                false
        );
    }

    private JFreeChart createLineChart(CategoryDataset dataset, String title) {
        return ChartFactory.createLineChart(
                title,
                "Category",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }
}
