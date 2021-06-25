package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  private int id;
  private String firstName;
  private String lastName;
  private int age;
}
