package com.lms.controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.BookCountByTitle;
import com.lms.service.LibraryService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/visualization")
public class VisualizationController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/chart")
    public String generateChart() {
        try {
            CategoryDataset dataset = createDataset();
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Library Statistics",
                    "Book Title",
                    "Count",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            File chartFile = new File("library-chart.png");
            ChartUtils.saveChartAsPNG(chartFile, barChart, 800, 600);

            return "Chart generated successfully. Check library-chart.png";
        } catch (IOException e) {
            return "Error generating chart: " + e.getMessage();
        }
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Fetch actual data from your service
        List<BookCountByTitle> bookCounts = libraryService.getCountsByTitle();

        for (BookCountByTitle bookCount : bookCounts) {
            // Use title for category axis, and count and unique authors for value axis
            dataset.addValue(bookCount.getCount(), "Books", bookCount.getTitle());
            dataset.addValue(bookCount.getUniqueAuthors(), "Authors", bookCount.getTitle());
        }

        return dataset;
    }
}
