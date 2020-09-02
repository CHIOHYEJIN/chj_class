package yogaClassReservation;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Class_table")
public class Class {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long reserveId;
    private Long classStock;
    private Long classId;

    @PostPersist
    public void onPostPersist(){
         ClassRegistered classRegistered = new ClassRegistered();
         BeanUtils.copyProperties(this, classRegistered);
         classRegistered.setClassStatus("ClassRegistered");
         classRegistered.publishAfterCommit();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getReserveId() {
        return reserveId;
    }

    public void setReserveId(Long reserveId) {
        this.reserveId = reserveId;
    }
    public Long getClassStock() {
        return classStock;
    }

    public void setClassStock(Long classStock) {
        this.classStock = classStock;
    }
    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
