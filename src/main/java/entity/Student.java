package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @Column(nullable = false)
    int id;

    @Column(name = "full_name", nullable = false)
    @Min(5)
    @NotBlank(message = "tên không được để trống")
    String fullName;

    @Column(nullable = false)
    @NotBlank(message = "ngày sinh không được để trống")
    Date birthday;

    @Column(name = "class_name", nullable = false)
    @NotBlank(message = "class name không được để trống")
    String className;

    @Column(nullable = false)
    @NotBlank(message = "khoa không được để trông")
    String major;

    @Column(nullable = false)
    @NotBlank(message = "quê quán không được để trống")
    String hometown;

    @Column(nullable = false)
    @NotBlank(message = "giới tính không được để trông")
    String gender;

    @Column(name = "average_mark", nullable = false)
    @Min(0)
    @Max(10)
    Float averageMark;

}
