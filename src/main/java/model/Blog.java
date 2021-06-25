package model;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

  private int id;
  private String name;
  private int userId;

}
