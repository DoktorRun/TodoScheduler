import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.*;

public class SmallCalender extends GridPane {
    double BUTTON_SIZE = 30;
    YearMonth yearMonth;
    public SmallCalender()
    {
        this(YearMonth.now());
    }

    public SmallCalender(YearMonth month)
    {
        this.yearMonth = month;
        this.setHgap(2);
        this.setVgap(2);
        this.setPadding(new Insets(25,25,25,25));

        for(int iColumn = 0; iColumn < 7; iColumn++)
        {
            //TODO Camelcase für Wochentage
            String dayOfWeek = DayOfWeek.of(iColumn+1).toString().substring(0,2);
            Label headerLabel = new Label(dayOfWeek);
            this.add(headerLabel,iColumn,1);
        }

        Button left = new Button("<");
        left.setMinSize(BUTTON_SIZE,BUTTON_SIZE);
        left.setMaxSize(BUTTON_SIZE,BUTTON_SIZE);
        left.setOnAction(e-> changeMonth(yearMonth.minusMonths(1)));
        Button right = new Button(">");
        right.setMinSize(BUTTON_SIZE,BUTTON_SIZE);
        right.setMaxSize(BUTTON_SIZE,BUTTON_SIZE);
        right.setOnAction(e-> changeMonth(yearMonth.plusMonths(1)));
        this.add(left,0,0);
        this.add(right,6,0);

        Label selectedMonth = new Label(yearMonth.toString());
        selectedMonth.setBorder(new Border(new BorderStroke(Color.GREENYELLOW,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        //TODO: Label Centern
        this.add(selectedMonth,2,0,7,1);

        createMonthButtons();
    }

    private void createMonthButtons()
    {
        //firstDayToDisplay implementieren als first of Month - index of Weekday of first of Month
        LocalDate firstDayToDisplay = yearMonth.atDay(1);
        int daysToAdd = 0;
        for(int iWeek = 0; iWeek < 5; iWeek++)
        {
            for(int iDayOfWeek = 0; iDayOfWeek < 7; iDayOfWeek++)
            {
                LocalDate dateOfButton = firstDayToDisplay.plusDays(daysToAdd);
                Button dayButton = new Button(""+dateOfButton.getDayOfMonth());
                dayButton.setMinSize(BUTTON_SIZE,BUTTON_SIZE);
                dayButton.setMaxSize(BUTTON_SIZE,BUTTON_SIZE);
                dayButton.setOnAction(e -> System.out.println(dateOfButton));

                if(dateOfButton.equals(LocalDate.now()))
                {
                    //TODO: Resource-Management für Design etc.
                    Border border = new Border(new BorderStroke(
                            Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT));
                    dayButton.setBorder(border);
                }
                this.add(dayButton, iDayOfWeek, iWeek+2,1,1); //
                daysToAdd++;
            }
        }
    }

    public void changeMonth(YearMonth month)
    {
        yearMonth = month;
    }
}
