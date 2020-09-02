package yogaClassReservation;

public class ClassRegistered extends AbstractEvent {

    private Long id;
    private Long reserveId;
    private Long classStock;
    private Long classId;
    private String classStatus;

    public ClassRegistered(){
        super();
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
    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

}
