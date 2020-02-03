package report;

import javafx.scene.control.ComboBox;
import model.Engine;
import model.WorkingTimeEntry;
import model.maintenance;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.expression.AbstractComplexExpression;
import net.sf.dynamicreports.report.builder.style.*;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.*;
import net.sf.dynamicreports.report.definition.ReportParameters;


import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created on 23-12-2016 at 14:39.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class ReportMananger {

    public static JasperReportBuilder createWorkingTimeReport(List<WorkingTimeEntry> workingEntries) {
        JasperReportBuilder report = DynamicReports.report();

        // init Field


        // init Columns

        TextColumnBuilder<Engine> engineName = DynamicReports.col.column("Name", "engine", Engine.class)
                .setValueFormatter(new EngineExpressionFormatter());

        TextColumnBuilder<LocalDateTime> DateColumn = DynamicReports.col.column("Date", "dateTime", LocalDateTime.class)
                .setValueFormatter(new LocaleDateTimeFormatter());


        TextColumnBuilder<Integer> workingTimeColumn = col.column("Working Time", new WorkingTimeExpression());

        AggregationSubtotalBuilder<Integer> totalWorkingTime =
                sbt.sum(workingTimeColumn).setStyle(Templates.bold12CenteredStyle);


        report
                .setTemplate(Templates.reportTemplate)
                .setColumnTitleStyle(Styles.style()
                        .setBorder(Styles.pen1Point())
                        .setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE)
                        .setFontSize(20)
                )
                .setColumnHeaderStyle(DynamicReports.stl.style().setBackgroundColor(Color.green)
                        .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE))

                .setColumnStyle(DynamicReports.stl.style()
                        .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE)
                        .setBorder(Styles.pen1Point())

                )

                .columns(engineName, DateColumn, workingTimeColumn)
                .subtotalsAtSummary(sbt.text("Total working Time:", DateColumn).setStyle(Styles.style().setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), totalWorkingTime)
                .title(cmp.text("Working Time History"),
                        Components.currentDate(), Components.verticalGap(50),
                        Components.line())
                .addPageFooter(DynamicReports.cmp.pageXofY())
                .setDataSource(workingEntries);


        return report;
    }


    public static JasperReportBuilder createMaintenanceReport(List<maintenance> workingEntries) {
        JasperReportBuilder report = DynamicReports.report();

        // init Field


        // init Columns

        TextColumnBuilder<Engine> engineName = DynamicReports.col.column("Name", "engine", Engine.class)
                .setValueFormatter(new EngineExpressionFormatter());

        TextColumnBuilder<LocalDateTime> DateColumn = DynamicReports.col.column("Date", "maintDate", LocalDateTime.class)
                .setValueFormatter(new LocaleDateTimeFormatter());


        report
                .setTemplate(Templates.reportTemplate)
                .setColumnTitleStyle(Styles.style()
                        .setBorder(Styles.pen1Point())
                        .setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE)
                        .setFontSize(20)
                )
                .setColumnHeaderStyle(DynamicReports.stl.style().setBackgroundColor(Color.green)
                        .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE))

                .setColumnStyle(DynamicReports.stl.style()
                        .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE)
                        .setBorder(Styles.pen1Point())

                )

                .columns(engineName, DateColumn)
                .title(DynamicReports.cmp.text("Maintenance History").setStyle(Templates.bold22CenteredStyle),
                        Components.currentDate(), Components.verticalGap(50),
                        Components.line())
                .addPageFooter(DynamicReports.cmp.pageXofY())
                .setDataSource(workingEntries);

        return report;
    }

    private static class LocaleDateTimeFormatter extends AbstractValueFormatter<String, LocalDateTime> {

        public String format(LocalDateTime value, ReportParameters reportParameters) {
            return value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));


        }

    }

    private static class EngineExpressionFormatter extends AbstractValueFormatter<String, Engine> {


        @Override
        public String format(Engine engine, ReportParameters reportParameters) {

            return engine.getDesignation();
        }
    }


    private static class WorkingTimeExpression extends AbstractComplexExpression<Integer> {
        protected WorkingTimeExpression() {
            addExpression(field("wtHours", Integer.class));
            addExpression(field("wtMinutes", Integer.class));
            addExpression(field("wtSeconds", Integer.class));
        }

        @Override
        public Integer evaluate(List<?> list, ReportParameters reportParameters) {
            int wtHours = (Integer) list.get(0);
            int wtMinutes = (Integer) list.get(1);
            int wtSeconds = (Integer) list.get(2);

            if (wtSeconds > 60) {
                wtMinutes += wtSeconds / 60;

            }

            if (wtMinutes > 60) {
                wtHours += wtMinutes / 60;
            }
            return wtHours;
        }
    }

}
