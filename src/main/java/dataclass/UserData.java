package dataclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private String firstName;
    private String lastName;
    private String postalCode;

    public UserData getSampleUserData() {
        return new UserData("TestFirstName", "LastNameTest", "01579");
    }

    public static UserData generateRandomData() {
        DateTimeFormatter formatterFirst = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatterLast = DateTimeFormatter.ofPattern("HHmmss");
        String formattedFirst = LocalDateTime.now().format(formatterFirst);
        String formattedLast = LocalDateTime.now().format(formatterLast);
        return new UserData("TestFirst_" + formattedFirst,
                "TestLast_" + formattedLast,
                UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 5));
    }
}
