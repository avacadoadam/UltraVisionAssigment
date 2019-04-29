package Conversions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public final class TimeConversions {

    private static final String TIME_FORMAT = "yyyy-MM-dd";

    public static String returnDate(int amountOfDays){
        LocalDateTime ldt = LocalDateTime.now().plusDays(amountOfDays);
        DateTimeFormatter formmat = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.ENGLISH);
        System.out.println(ldt);
        return formmat.format(ldt);
    }

    public static String returnTodayDate(){
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formmat = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.ENGLISH);
        System.out.println(ldt);
        return formmat.format(ldt);
    }

    public static long numberOfDaysLate(String dueDate){
        DateTimeFormatter formmat = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.ENGLISH);
        LocalDate now = LocalDate.now();
        LocalDate due = LocalDate.parse(dueDate,formmat);
        return ChronoUnit.DAYS.between(due,now);
    }
    public static long numberOfDaysLate(Date dueDate) {
        LocalDate now = LocalDate.now();
        LocalDate date = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(date, now);
    }





        public static Date ConvertDOB(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        dateFormat.setLenient(false);

            return dateFormat.parse(date.trim());

    }
}
