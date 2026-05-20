package admissions.student_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "xt_tohop")
public class ToHop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtohop")
    private Integer id;

    @Column(name = "matohop")
    private String maToHop;

    @Column(name = "mon1")
    private String mon1;

    @Column(name = "mon2")
    private String mon2;

    @Column(name = "mon3")
    private String mon3;

    @Column(name = "tentohop")
    private String tenToHop;

    // getter setter
}